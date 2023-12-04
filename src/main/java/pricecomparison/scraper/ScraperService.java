/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pricecomparison.scraper;

import java.sql.Timestamp;
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
        ScraperResponse response = scraper1.accessScrapingUrl(url);
        if (response != null && response.getStatusCode() == 200) {
            Product product = scraper1.extractProductDetails(response.getHtmlContent());
            if (product != null) {
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
            }
        } else {
            // Handle error or log appropriately
            System.out.println("Error accessing Amazon URL: " + url);
        }
    }

    private void scrapeAndStoreAliexpressData(String url) {
        ScraperResponse response = scraper2.accessScrapingUrl(url);
        if (response != null && response.getStatusCode() == 200) {
            Product product = scraper2.extractProductDetails(response.getHtmlContent());
            // Store product data in the database using Hibernate
            // ...
        } else {
            // Handle error or log appropriately
            System.out.println("Error accessing Aliexpress URL: " + url);
        }
    }

    // Add similar methods for other e-commerce websites...
}
