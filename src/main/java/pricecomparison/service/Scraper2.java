/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pricecomparison.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import pricecomparison.transferobject.Response;
import pricecomparison.transferobject.Product;
import pricecomparison.util.Scraper2Helper;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;
import pricecomparison.transferobject.Property;
import pricecomparison.transferobject.Variation;
import pricecomparison.util.Scraper1Helper;

/**
 *
 * @author David
 */
// Alixpress Scraper Implementation
@Service
public class Scraper2 implements ScraperInterface {

    private static final Logger logger = Logger.getLogger(Scraper2.class.getName());

    @Override
    public Product extractProductDetails(String html) {
        try {
            // Extract details using JSoup
            Document document = Jsoup.parse(html);

            // Utilize utility functions for extraction
            String title = Scraper2Helper.extractProductTitle(document);
            String description = Scraper2Helper.extractProductDescription(document);
            int price = Scraper2Helper.extractProductPrice(document);
            String currency = Scraper2Helper.extractProductCurrency(document);
            String imageUrl = Scraper2Helper.extractProductImageUrl(document);
            String manufacturer = Scraper2Helper.extractProductManufacturer(document);
            List<Variation> variations = Scraper2Helper.extractProductVariations(document);
            List<Property> properties = Scraper1Helper.extractProductProperties(document);

            return new Product(title, description, price, imageUrl, manufacturer, variations, currency, properties);
        } catch (Exception e) {
            // Log the error for investigation
            logger.log(Level.SEVERE, "Error extracting product details from malformed HTML", e);

            // Return null or an empty product
            return null;
        }
    }

    @Override
    public Response accessScrapingUrl(String url) {
        try {
            // Use JSoup to connect to the website and fetch the HTML content
            Document document = Jsoup.connect(url).get();

            // Convert the Document to HTML String
            String htmlContent = document.html();

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
        return "Alixpress";
    }

    @Override
    public String getLogoUrl() {
        return "https://www.alixpress.com/logo.png";
    }
}
