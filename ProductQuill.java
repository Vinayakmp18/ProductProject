package adapt;

import com.opencsv.CSVWriter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class ProductQuill2 {

    final static String BASE_URL = "https://www.quill.com";
    final static String URL = "https://www.quill.com/hanging-file-folders/cbk/122567.html";

//    final static String SEP_CHAR = ": ";

    public static void main(String[] args) {

        String csvFilePath = "product_details.csv";

        List<String[]> data = new ArrayList();

        try {
            final Document document= Jsoup.connect(URL).get();
            List<String> links = top10ProductLinks(document);
            String category = productCategory(document);
            String itemCode = "" , modelNumber = "", brand = "";
            for (String link: links) {
                Document productDocument = Jsoup.connect(link).get();
                Element productSku = productDocument.select("div#SkuMainContentDiv").first();
                String productName = productName(productSku);
                Element productElements = productSku.select("div.body-xs.d-md-flex.fg-jet-tint").first();
                for (Element productElement: productElements.children()) {
                    if (productElement.text().contains("Item")) {
                        itemCode = productItem(productElement);
                    } else if (productElement.text().contains("Model")) {
                        modelNumber = productModel(productElement);
                    } else if (productElement.text().contains("Brand")) {
                        brand = productBrand(productElement);
                    }
                }
                String price =  productPrice(productDocument);
                String description = productDescription(productDocument);
                data.add(new String[]{productName, price, itemCode, modelNumber, category, description});
            }
            writeToCSV(csvFilePath, data);
        } catch (Exception e) {
			e.printStackTrace();
		}

    }

    private static void writeToCSV(String csvFilePath, List<String[]> data) throws IOException {
        FileWriter outputfile = new FileWriter(csvFilePath);
        CSVWriter csvWriter = new CSVWriter(outputfile);
        String[] header = {"Product Name", "Product Price", "Item Number/SKU/Product Code", "Model Number",
                "Product Category", "Product Description"};
        csvWriter.writeNext(header);
        for (String[] row: data ) {
            csvWriter.writeNext(row);
        }
        csvWriter.close();
    }

    private static List<String> top10ProductLinks(Document document) {
        Elements elements = document.select("div.d-flex.flex-row.justify-content-center.product-image-wrap > a");
        int count = 0;
        List<String> urls = new ArrayList();
        for (Element element: elements) {
            String link = element.attr("href");
            urls.add(BASE_URL + link);
            count++;
            if (count > 9) {
                break;
            }
        }
        return urls;
    }

    private static String productName(Element productSku) {
        Element nameElement = productSku.select("h1.skuName").first();
        return nameElement.text();
    }

    private static String productBrand(Element productElement) {
        int sepIndex = productElement.text().indexOf(": ") + 1;
        return productElement.text().substring(sepIndex);
    }

    private static String productPrice(Document productDocument) {
        Element priceContainer = productDocument.select("div.pricing-wrap").first();
        Element priceElement = priceContainer.select("div > div > span").first();
        return priceElement.text();
    }

    private static String productModel(Element productElement) {
        int sepIndex = productElement.text().indexOf(": ") + 1;
        return productElement.text().substring(sepIndex);
    }

    private static String productItem(Element productElement) {
        int sepIndex = productElement.text().indexOf(": ") + 1;
        return productElement.text().substring(sepIndex);
    }

    private static String productCategory(Document document) {
        return document.select("div > h1#SearchTerm").first().text();
    }

    private static String productDescription(Document productDocument) {
        Elements desElement = productDocument.select("div#skuDescription > div > ul > li");
        StringJoiner joiner = new StringJoiner("\n");
        for (Element element: desElement) {
            joiner.add(element.text());
        }
        return joiner.toString();
    }
}
