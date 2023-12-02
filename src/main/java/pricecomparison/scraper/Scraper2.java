/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pricecomparison.scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pricecomparison.dto.ScraperResponse;
import pricecomparison.dto.Product;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author David
 */

// Alixpress Scraper Implementation

public class Scraper2 implements ScraperInterface {

    private static final Logger logger = Logger.getLogger(Scraper2.class.getName());

    @Override
    public Product extractProductDetails(String html) {
        try {
            // Extract details using JSoup
            Document document = Jsoup.parse(html);

            // Extract title
            String title = extractProductTitle(document);

            // Extract description
            String description = extractProductDescription(document);

            // Extract price
            String price = extractProductPrice(document);

            // Extract image URL
            String imageUrl = extractProductImageUrl(document);

            return new Product(title, description, price, imageUrl);
        } catch (Exception e) {
            // Log the error for investigation
            logger.log(Level.SEVERE, "Error extracting product details from malformed HTML", e);

            // Return null or an empty product
            return null;
        }
    }

    private String extractProductTitle(Document document) {
        // Example: Extract the title of the page
        return document.title();
    }

    private String extractProductDescription(Document document) {
        // Example: Extract all paragraphs on the page
        Elements paragraphs = document.select("p");
        return paragraphs.text();
    }

    private String extractProductPrice(Document document) {
        // Example: Extract the first heading on the page
        Element heading = document.select("h1").first();
        return heading.text();
    }

    private String extractProductImageUrl(Document document) {
        // Example: Extract the first link on the page
        Elements links = document.select("a[href]");
        return links.first().attr("href");
    }

    @Override
    public ScraperResponse accessScrapingUrl(String url) {
        try {
            // Use JSoup to connect to the website and fetch the HTML content
            Document document = Jsoup.connect(url).get();

            // Convert the Document to HTML String
            String htmlContent = document.html();

            // Return the HTML content with a 200 status code (OK)
            return new ScraperResponse(htmlContent, 200);
        } catch (IOException e) {
            // Log the error for investigation
            logger.log(Level.SEVERE, "Error accessing website: " + url, e);

            // Return null with an appropriate status code (you may customize based on your requirements)
            return new ScraperResponse(null, 500); // Internal Server Error
        }
    }

    @Override
    public String getName() {
        return "Alixpress";
    }
}
