/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pricecomparison.scraper;

import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pricecomparison.dao.ComparisonDao;
import pricecomparison.dao.ModelDao;
import pricecomparison.dao.ModelVariationDao;
import pricecomparison.dto.Product;
import pricecomparison.dto.ScraperResponse;
import pricecomparison.dto.Variation;
import pricecomparison.entity.Comparison;
import pricecomparison.entity.Model;
import pricecomparison.entity.ModelVariation;


/**
 *
 * @author David
 */

@Service
public class ScraperService {

    private static final Logger logger = Logger.getLogger(ScraperService.class.getName());

    @Autowired
    private Scraper1 scraper1;

    @Autowired
    private Scraper2 scraper2;

    @Autowired
    private ModelDao modelDao;

    @Autowired
    private ModelVariationDao modelVariationDao;

    @Autowired
    private ComparisonDao comparisonDao;

    public void scrapeAndStoreData(String url) {
        logger.log(Level.INFO, "Scraping and storing data for URL: {0}", url);

        // Determine which scraper to use based on the URL
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

        ScraperResponse response = scraper1.accessScrapingUrl(url);
        if (response != null && response.getStatusCode() == 200) {
            Product product = scraper1.extractProductDetails(response.getHtmlContent());
            if (product != null) {
                // Log product details
                logger.log(Level.INFO, "Amazon Product Details: {0}", product);

                // Create and save Model entity
                Model model = new Model();
                model.setName(product.getTitle());
                model.setDescription(product.getDescription());
                model.setImageUrl(product.getImageUrl());
                model.setManufacturer(product.getManufacturer());

                // Save the model
                modelDao.saveOrUpdateModel(model);

                // Create and save ModelVariation entities
                for (Variation variation : product.getVariations()) {
                    ModelVariation modelVariation = new ModelVariation();
                    modelVariation.setModel(model);
                    modelVariation.setVariationName(variation.getName());
                    modelVariation.setVariationValue(variation.getValue());

                    // Save the model variation
                    modelVariationDao.saveOrUpdateModelVariation(modelVariation);
                }

                // Create and save Comparison entity
                Comparison comparison = new Comparison();
                comparison.setUrl(url);
                comparison.setPrice(product.getPrice());
                comparison.setCurrency(product.getCurrency());
                comparison.setStore("Amazon"); // Set the appropriate store name
                comparison.setStoreLogoUrl("https://www.amazon.com/logo.png"); // Set the appropriate logo URL
                comparison.setLastUpdated(new Timestamp(System.currentTimeMillis()));
                comparison.setModel(model);

                // Save or update the comparison
                comparisonDao.saveOrUpdateComparison(comparison);

                logger.info("Amazon data stored successfully.");
            }
        } else {
            // Handle error or log appropriately
            logger.log(Level.WARNING, "Error accessing Amazon URL: {0}", url);
        }
    }

    private void scrapeAndStoreAliexpressData(String url) {
        logger.log(Level.INFO, "Scraping and storing Aliexpress data for URL: {0}", url);

        ScraperResponse response = scraper2.accessScrapingUrl(url);
        if (response != null && response.getStatusCode() == 200) {
            Product product = scraper2.extractProductDetails(response.getHtmlContent());
            if (product != null) {
                // Log product details
                logger.log(Level.INFO, "{0}Aliexpress Product Details: ", product);

                // Create and save Model entity
                Model model = new Model();
                model.setName(product.getTitle());
                model.setDescription(product.getDescription());
                model.setImageUrl(product.getImageUrl());
                model.setManufacturer(product.getManufacturer());

                // Save the model
                modelDao.saveOrUpdateModel(model);

                // Create and save ModelVariation entities
                for (Variation variation : product.getVariations()) {
                    ModelVariation modelVariation = new ModelVariation();
                    modelVariation.setModel(model);
                    modelVariation.setVariationName(variation.getName());
                    modelVariation.setVariationValue(variation.getValue());

                    // Save the model variation
                    modelVariationDao.saveOrUpdateModelVariation(modelVariation);
                }

                // Create and save Comparison entity
                Comparison comparison = new Comparison();
                comparison.setUrl(url);
                comparison.setPrice(product.getPrice());
                comparison.setCurrency(product.getCurrency());
                comparison.setStore("Aliexpress"); // Set the appropriate store name
                comparison.setStoreLogoUrl("https://www.alixpress.com/logo.png"); // Set the appropriate logo URL
                comparison.setLastUpdated(new Timestamp(System.currentTimeMillis()));
                comparison.setModel(model);

                // Save or update the comparison
                comparisonDao.saveOrUpdateComparison(comparison);

                logger.info("Aliexpress data stored successfully.");
            }
        } else {
            // Handle error or log appropriately
            logger.log(Level.WARNING, "Error accessing Aliexpress URL: {0}", url);
        }
    }

    // Add similar methods for other e-commerce websites...
}
