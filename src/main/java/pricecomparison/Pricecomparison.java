/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package pricecomparison;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pricecomparison.service.DataAggregator;

import java.util.Arrays;
import java.util.List;
import pricecomparison.scheduler.PriceScheduler;

/**
 *
 * @author David
 */

public class Pricecomparison {

    public static void main(String[] args) {
        // Load the Spring application context
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        // Retrieve the DataAggregator bean from the context
        DataAggregator dataAggregator = context.getBean(DataAggregator.class);

        // Example list of URLs to scrape and store data
        List<String> urls = Arrays.asList(
                "https://www.amazon.com/sample-product-url1",
                "https://www.amazon.com/sample-product-url2",
                "https://www.aliexpress.com/sample-product-url3"
        // Add more URLs as needed
        );

        // Call the scrapeAndStoreData method for each URL in the list
        for (String url : urls) {
            dataAggregator.scrapeAndStoreData(url);
        }

        // Schedule periodic scraping for pricing data using the same list of URLs
        PriceScheduler priceScheduler = new PriceScheduler(dataAggregator);
        priceScheduler.schedulePricingScraping(urls);
    }
}
