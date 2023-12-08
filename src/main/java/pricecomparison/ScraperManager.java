/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pricecomparison;

import pricecomparison.service.DataAggregator;
import pricecomparison.scheduler.PriceScrapingScheduler;

import java.util.List;
/**
 *
 * @author David
 */



public class ScraperManager {

    private final DataAggregator dataAggregator;
    private final PriceScrapingScheduler priceScheduler;

    public ScraperManager() {
        // Initialize DataAggregator
        this.dataAggregator = new DataAggregator();

        // Initialize PriceScrapingScheduler with DataAggregator
        this.priceScheduler = new PriceScrapingScheduler(dataAggregator);
    }

    // Method to scrape and store data for a list of URLs
    public void scrapeAndStoreData(List<String> urls) {
        // Call the scrapeAndStoreData method for each URL in the list
        for (String url : urls) {
            dataAggregator.scrapeAndStoreData(url);
        }

        // Schedule periodic scraping for pricing data using the same list of URLs
        priceScheduler.schedulePricingScraping(urls);
    }
}
