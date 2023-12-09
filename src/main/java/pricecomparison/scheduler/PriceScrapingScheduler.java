/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pricecomparison.scheduler;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import pricecomparison.service.DataAggregator;

/**
 *
 * @author David
 */
public class PriceScrapingScheduler {

    private final DataAggregator dataAggregator;

    public PriceScrapingScheduler(DataAggregator dataAggregator) {
        this.dataAggregator = dataAggregator;
    }

    // Method to schedule periodic concurrent pricing scraping for a list of URLs
    public void schedulePeriodicPricingScraping(List<String> amazonUrls, List<String> aliexpressUrls, long initialDelay, long period) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

        // Schedule tasks for each URL at fixed intervals
        for (String url : amazonUrls) {
            scheduler.scheduleAtFixedRate(() -> dataAggregator.scrapeAndStoreAmazonPricingData(url), initialDelay, period, TimeUnit.SECONDS);
        }

        for (String url : aliexpressUrls) {
            scheduler.scheduleAtFixedRate(() -> dataAggregator.scrapeAndStoreAliExpressPricingData(url), initialDelay, period, TimeUnit.SECONDS);
        }

        // Shut down the scheduler when needed
        // Note: You may want to handle shutdown gracefully based on your application requirements
        // scheduler.shutdown();
    }
}
