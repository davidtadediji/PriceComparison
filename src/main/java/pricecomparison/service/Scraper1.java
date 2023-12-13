/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pricecomparison.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import pricecomparison.transferobject.Response;
import pricecomparison.transferobject.Product;
import pricecomparison.util.Scraper1Helper;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;
import pricecomparison.transferobject.Price;
import pricecomparison.transferobject.Property;
import pricecomparison.transferobject.Variation;

/**
 *
 * @author David
 */
// Amazon Scraper Implementation
@Service
public class Scraper1 implements ScraperInterface {

    private static final Logger logger = Logger.getLogger(Scraper1.class.getName());

    @Override
    public Product extractProductDetails(String html, String modelName) {
        try {
            // Extract details using JSoup
            Document document = Jsoup.parse(html);

            // Utilize utility functions for extraction
            String title = modelName;
            String description = Scraper1Helper.extractProductDescription(document);
            Price price = Scraper1Helper.extractProductPrice(document);
            String imageUrl = Scraper1Helper.extractProductImageUrl(document);
            String manufacturer = Scraper1Helper.extractProductManufacturer(document);
            List<Variation> variations = Scraper1Helper.extractProductVariations(document);
            List<Property> properties = Scraper1Helper.extractProductProperties(document);
            logger.log(Level.WARNING, "Extracted product details from {0} URL: {1}", getName());

            return new Product(title, description, price, imageUrl, manufacturer, variations, properties);
        } catch (Exception e) {
            // Log the error for investigation
            logger.log(Level.SEVERE, "Error extracting product details from malformed HTML", e);

            // Return null or an empty product
            return null;
        }
    }

    public Price extractProductPrice(String html) {
        try {
            // Extract only pricing information using JSoup
            Document document = Jsoup.parse(html);
            return Scraper1Helper.extractProductPrice(document);
        } catch (Exception e) {
            // Log the error for investigation
            logger.log(Level.SEVERE, "Error extracting pricing data from malformed HTML", e);

            // Return null or an empty Price
            return null;
        }
    }

    @Override
    public Response accessScrapingUrl(String url) {
        try {
            logger.log(Level.INFO, "Scraper url passed: {0}", url);
            java.util.logging.Logger.getLogger("org.jsoup").setLevel(java.util.logging.Level.ALL);

            // Use JSoup to connect to the website and fetch the HTML content
            Document document = Jsoup.connect(url).timeout(5000).get();

            // Convert the Document to HTML String
            String htmlContent = document.html();

            logger.log(Level.INFO, "Scraper url accessed: {0}", url);
            // Return the HTML content with a 200 status code (OK)
            return new Response(htmlContent, 200);
        } catch (IOException e) {
            // Log the error for investigation
            logger.log(Level.SEVERE, "Error accessing website: " + url, e);

            // Return null with an appropriate status code (you may customize based on your requirements)
            return new Response(null, 500); // Internal Server Error
        }
    }

    @Override
    public String getName() {
        return "Amazon";
    }

    @Override
    public String getLogoUrl() {
        return "https://www.amazon.com/logo.png";
    }
}
