/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pricecomparison.scraper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pricecomparison.dto.Product;
import pricecomparison.dto.ScraperResponse;
import pricecomparison.scraper.Scraper1;
import pricecomparison.scraper.Scraper2;
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

    public void scrapeAndStoreData(String url) {
        // Scrape data using scrapers and store it in the database
        ScraperResponse response1 = scraper1.accessScrapingUrl(url);
        if (response1 != null && response1.getStatusCode() == 200) {
            Product product1 = scraper1.extractProductDetails(response1.getHtmlContent());
            // Store product1 data in the database using Hibernate
            // ...
        }

        ScraperResponse response2 = scraper2.accessScrapingUrl(url);
        if (response2 != null && response2.getStatusCode() == 200) {
            Product product2 = scraper2.extractProductDetails(response2.getHtmlContent());
            // Store product2 data in the database using Hibernate
            // ...
        }

        // Additional business logic and database operations...
    }
}
