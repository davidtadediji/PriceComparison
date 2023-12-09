/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package pricecomparison;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author David
 */
public class Pricecomparison {

    private static final Logger logger = Logger.getLogger(Pricecomparison.class.getName());
    private static final long initialDelay = 0;
    private static final long period = 3600;

    public static void main(String[] args) {
        logger.log(Level.INFO, "Testing logs");

        // Load the Spring application context
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        // Retrieve the ScraperManager bean from the context
        ScraperManager scraperManager = context.getBean(ScraperManager.class);

        // Load URLs from the configuration file for each store
        List<String> amazonUrls = loadUrlsFromConfig("store_config.properties", "Amazon");
        List<String> aliexpressUrls = loadUrlsFromConfig("store_config.properties", "Aliexpress");

        // Schedule concurrent scraping for product details for each store
        scraperManager.scheduleConcurrentScraping(amazonUrls, aliexpressUrls);

        scraperManager.schedulePeriodicPricingScraping(amazonUrls, aliexpressUrls, initialDelay, period);

    }

    private static List<String> loadUrlsFromConfig(String configFile, String storeName) {
        List<String> urls = new ArrayList<>();

        try ( InputStream input = Pricecomparison.class.getClassLoader().getResourceAsStream(configFile)) {
            Properties prop = new Properties();

            // Load the properties file
            if (input != null) {
                prop.load(input);

                // Read product URLs for the store
                for (int i = 1;; i++) {
                    String currentStoreName = prop.getProperty("store" + i + ".name");
                    if (currentStoreName == null) {
                        break; // No more stores in the configuration
                    }

                    if (currentStoreName.equalsIgnoreCase(storeName)) {
                        for (int j = 1;; j++) {
                            String productUrl = prop.getProperty("store" + i + ".product" + j + ".url");
                            if (productUrl == null) {
                                break; // No more products for the store
                            }
                            urls.add(productUrl);
                        }
                        break; // Found the desired store, no need to continue
                    }
                }
            } else {
                logger.log(Level.SEVERE, "Unable to load configuration file: {0}", configFile);
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error reading configuration file", e);
        }

        return urls;
    }
}
