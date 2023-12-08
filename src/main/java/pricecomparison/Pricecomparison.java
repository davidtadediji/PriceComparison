/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package pricecomparison;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author David
 */


public class Pricecomparison {
    private static final Logger logger = Logger.getLogger(Pricecomparison.class.getName());

    public static void main(String[] args) {
        
        logger.log(Level.INFO, "Testing logs");
        
        // Load the Spring application context
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        // Retrieve the ScraperManager bean from the context
        ScraperManager scraperManager = context.getBean(ScraperManager.class);

        // Example list of URLs to scrape and store data
        List<String> urls = Arrays.asList(
                "https://www.amazon.com/sample-product-url1",
                "https://www.amazon.com/sample-product-url2",
                "https://www.aliexpress.com/sample-product-url3"
                // Add more URLs as needed
        );

        // Pass the list of URLs to the ScraperManager constructor
        scraperManager.scrapeAndStoreData(urls);
    }
}
