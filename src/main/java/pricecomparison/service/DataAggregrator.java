/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pricecomparison.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pricecomparison.transferobject.Product;
import pricecomparison.transferobject.Response;
import pricecomparison.util.DataAggregatorHelper;

/**
 *
 * @author David
 */
@Service
public class DataAggregrator {

    private static final Logger logger = Logger.getLogger(DataAggregrator.class.getName());

    @Autowired
    private Scraper1 scraper1;

    @Autowired
    private Scraper2 scraper2;

    @Autowired
    private DataAggregatorHelper dataAggregatorHelper;

    public void scrapeAndStoreData(String url) {
        logger.log(Level.INFO, "Scraping and storing data for URL: {0}", url);

        if (url.contains("amazon")) {
            scrapeAndStoreAmazonData(url);
        } else if (url.contains("aliexpress")) {
            scrapeAndStoreAliexpressData(url);
        }
        // Add more conditions for other e-commerce websites...
        // Additional business logic and database operations...
    }

    private void scrapeAndStoreAmazonData(String url) {
        logger.log(Level.INFO, "Scraping and storing Amazon data for URL: {0}", url);

        Response response = scraper1.accessScrapingUrl(url);
        if (response != null && response.getStatusCode() == 200) {
            Product product = scraper1.extractProductDetails(response.getHtmlContent());
            if (product != null) {
                // Log product details...
                dataAggregatorHelper.storeDataCommonLogic(product, "Amazon", scraper1.getLogoUrl(), url);
                logger.info("Amazon data stored successfully.");
            }
        } else {
            // Handle error or log appropriately
            logger.log(Level.WARNING, "Error accessing Amazon URL: {0}", url);
        }
    }

    private void scrapeAndStoreAliexpressData(String url) {
        logger.log(Level.INFO, "Scraping and storing Aliexpress data for URL: {0}", url);

        Response response = scraper2.accessScrapingUrl(url);
        if (response != null && response.getStatusCode() == 200) {
            Product product = scraper2.extractProductDetails(response.getHtmlContent());
            if (product != null) {
                // Log product details...
                dataAggregatorHelper.storeDataCommonLogic(product, "Aliexpress", scraper2.getLogoUrl(), url);
                logger.info("Aliexpress data stored successfully.");
            }
        } else {
            // Handle error or log appropriately
            logger.log(Level.WARNING, "Error accessing Alixpress URL: {0}", url);
        }
    }
    // Additional methods and business logic...
}
