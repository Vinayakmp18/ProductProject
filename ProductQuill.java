package demo;

import java.io.FileWriter;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ProductQuill {
	public static void main(String[] args) {
		final String url="https://www.quill.com/hanging-file-folders/cbk/122567.html";
		final String url1="https://www.quill.com/quill-brand-standard-green-100-recycled-hanging-file-folders-1-5-cut-adjustable-tabs-letter-size-50-case/cbs/005880.html";
		
		String csvFilePath = "product_details.csv";
		int count=0; int count1=0; int count2=0; int count3=0; int count4=0; int count5=0;
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
					final String name=row.select(".search-product-name-wrap.d-block.body-xs").text();
					if(count<10)
					{
						csvWriter.append(name);
			            csvWriter.append("\n");
						count++;
					}
			}
			csvWriter.append("\n");
			for (Element row : document.select(
					"div.col-5.py-0.pricing-section-wrap.js-pricingSection.col"
					)) { 
					final String price=row.select(".text-black.mb-0.price-size").text();
					if(count1<10)
					{
						csvWriter.append(price);
			            csvWriter.append("\n");
						count1++;
					}	
			}
			csvWriter.append(",");
			for (Element row : document.select(
					"div.col-7.py-0.product-name-wrap.js-productSection.col"
					)) {
					final String item=row.select(".pr-2.LEffortFindNum.fg-jet-tint.body-xxs.col-auto").text();
					if(count2<10)
					{
						csvWriter.append(item);
			            csvWriter.append("\n");
						count2++;
					}
			}
			csvWriter.append(",");
			for (Element row : document1.select(
					"div.col-xl-8.col-md-7.col-12"
					)) {
					final String model=row.select(".ml-md-2.mb-md-0.mb-2:nth-of-type(2)").text();
					if(count3<10)
					{
						csvWriter.append(model);
			            csvWriter.append("\n");
						count3++;
					}
			}
			csvWriter.append(",");
			for (Element row : document.select(
					"div.container-lg.px-sm-5.px-3"
					)) {
					final String category=row.select("#searchResultsInfoContainer > div.row:nth-of-type(1) > .col").text();
					if(count4<10)
					{
						csvWriter.append(category);
			            csvWriter.append("\n");
			            count4++;
					}	
			}
			csvWriter.append(",");
			for (Element row : document.select(
					".col-7.py-0.product-name-wrap.js-productSection.col"
					)) {
					final String description=row.select(".pb-1.mb-2.body-xxs.js-skuBullets").text();
					if(count5<10)
					{
						csvWriter.append(description);
						csvWriter.append("\n");
						count5++;
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

