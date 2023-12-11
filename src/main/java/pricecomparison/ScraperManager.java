/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pricecomparison;

import pricecomparison.service.DataAggregator;
import pricecomparison.scheduler.PriceScrapingScheduler;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author David
 */
/**
 *
 * @author David
 */


@Component
public class ScraperManager {

    @Autowired
    private DataAggregator dataAggregator;

    @Autowired
    private PriceScrapingScheduler priceScheduler;

    private static final Logger logger = Logger.getLogger(ScraperManager.class.getName());

    // Method to schedule concurrent scraping for product details for a list of URLs
    public void scheduleConcurrentScraping(List<String> amazonUrls, List<String> aliexpressUrls, long initialDelay, long period) {
        logger.log(Level.INFO, "Initial scraping begun");

        // Create a thread pool with a fixed number of threads
        int numThreads = Runtime.getRuntime().availableProcessors(); // Adjust the number as needed
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);

        try {
            // Submit tasks for each URL to the thread pool
            for (String url : amazonUrls) {
                executorService.submit(() -> dataAggregator.scrapeAndStoreAmazonData(url));
            }

            for (String url : aliexpressUrls) {
                executorService.submit(() -> dataAggregator.scrapeAndStoreAliexpressData(url));
            }

            // Shut down the executor service and wait for tasks to complete
            executorService.shutdown();
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

            // If all tasks completed successfully, schedule periodic pricing scraping
            if (!executorService.isTerminated()) {
                schedulePeriodicPricingScraping(amazonUrls, aliexpressUrls, initialDelay, period);
            }

        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, "Error waiting for product details scraping to complete", e);
        }
    }

    // Method to schedule periodic concurrent pricing scraping for a list of URLs
    public void schedulePeriodicPricingScraping(List<String> amazonUrls, List<String> aliexpressUrls, long initialDelay, long period) {
        priceScheduler.schedulePeriodicPricingScraping(amazonUrls, aliexpressUrls, initialDelay, period);
    }
}
