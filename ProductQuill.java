package demo;

import java.io.FileWriter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class ProductQuill {
	public static void main(String[] args) {
		final String url="https://www.quill.com/hanging-file-folders/cbk/122567.html";
		final String url1="https://www.quill.com/quill-brand-standard-green-100-recycled-hanging-file-folders-1-5-cut-adjustable-tabs-letter-size-50-case/cbs/005880.html";
		
		String csvFilePath = "product_details.csv";
		
		try {
			FileWriter csvWriter = new FileWriter(csvFilePath);
            csvWriter.append("Product Name");
            csvWriter.append(",");
            csvWriter.append("Product Price");
            csvWriter.append(",");
            csvWriter.append("Item Number/SKU/Product Code");
            csvWriter.append(",");
            csvWriter.append("Model Number");
            csvWriter.append(",");
            csvWriter.append("Product Category");
            csvWriter.append(",");
            csvWriter.append("Product Description");
            csvWriter.append("\n");
			
			final Document document=Jsoup.connect(url).get();
			final Document document1=Jsoup.connect(url1).get();
			
			
			for (Element row : document.select(
					"div.col-7.py-0.product-name-wrap.js-productSection.col"
					)) {
				if (row.select(".search-product-name-wrap.d-block.body-xs").text().equals("")) {
					continue;
				} else {
					final String name=row.select(".search-product-name-wrap.d-block.body-xs").text();
//					System.out.println(name);
					csvWriter.append(name);
		            csvWriter.append("\n");
				}
			}
			csvWriter.append(",");
			for (Element row : document.select(
					"div.col-5.py-0.pricing-section-wrap.js-pricingSection.col"
					)) { 
				if (row.select(".text-black.mb-0.price-size").text().equals("")) {
					continue;
				} else {
					final String price=row.select(".text-black.mb-0.price-size").text();
//					System.out.println(price);
					csvWriter.append(price);
		            csvWriter.append("\n");
				}		
			}
			csvWriter.append(",");
			for (Element row : document.select(
					"div.col-7.py-0.product-name-wrap.js-productSection.col"
					)) {
				if (row.select(".pr-2.LEffortFindNum.fg-jet-tint.body-xxs.col-auto").text().equals("10")) {
					continue;
				} else {
					final String item=row.select(".pr-2.LEffortFindNum.fg-jet-tint.body-xxs.col-auto").text();
//					System.out.println(item);
					 csvWriter.append(item);
			         csvWriter.append("\n");
				}
			}
			csvWriter.append(",");
			for (Element row : document1.select(
					"div.col-xl-8.col-md-7.col-12"
					)) {
				if (row.select(".ml-md-2.mb-md-0.mb-2:nth-of-type(2)").text().equals("")) {
					continue;
				} else {
					final String model=row.select(".ml-md-2.mb-md-0.mb-2:nth-of-type(2)").text();
//					System.out.println(model);
					csvWriter.append(model);
		            csvWriter.append("\n");
				}
			}
			csvWriter.append(",");
			for (Element row : document.select(
					"div.container-lg.px-sm-5.px-3"
					)) {
				if (row.select("#searchResultsInfoContainer > div.row:nth-of-type(1) > .col").text().equals("10")) {
					continue;
				} else {
					final String category=row.select("#searchResultsInfoContainer > div.row:nth-of-type(1) > .col").text();
//					System.out.println(category);
					csvWriter.append(category);
		            csvWriter.append("\n");
				}
			}
			csvWriter.append(",");
			for (Element row : document.select(
					".col-7.py-0.product-name-wrap.js-productSection.col"
					)) {
				if (row.select(".pb-1.mb-2.body-xxs.js-skuBullets").text().equals("10")) {
					continue;
				} else {
					final String description=row.select(".pb-1.mb-2.body-xxs.js-skuBullets").text();
//					System.out.println(description);
					 csvWriter.append(description);
			         csvWriter.append("\n");
				}
			}
			csvWriter.flush();
            csvWriter.close();
            System.out.println("Product details have been exported to " + csvFilePath);
			
		}
			
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
