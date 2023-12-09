/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pricecomparison;

import pricecomparison.service.DataAggregator;
import pricecomparison.scheduler.PriceScrapingScheduler;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 *
 * @author David
 */
/**
 *
 * @author David
 */
public class ScraperManager {

    private final DataAggregator dataAggregator;
    private final PriceScrapingScheduler priceScheduler;
    private static final Logger logger = Logger.getLogger(ScraperManager.class.getName());

    public ScraperManager() {
        this.dataAggregator = new DataAggregator();
        this.priceScheduler = new PriceScrapingScheduler(dataAggregator);
    }

    // Method to schedule concurrent scraping for product details for a list of URLs
    public void scheduleConcurrentScraping(List<String> amazonUrls, List<String> aliexpressUrls) {
        // Create a thread pool with a fixed number of threads
        int numThreads = Runtime.getRuntime().availableProcessors(); // Adjust the number as needed
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);

        // Create a CountDownLatch with the count equal to the total number of URLs to be scraped
        CountDownLatch productDetailsLatch = new CountDownLatch(amazonUrls.size() + aliexpressUrls.size());

        // Submit tasks for each URL to the thread pool
        for (String url : amazonUrls) {
            executorService.submit(() -> {
                dataAggregator.scrapeAndStoreAmazonData(url);
                productDetailsLatch.countDown(); // Decrease the latch count
            });
        }

        for (String url : aliexpressUrls) {
            executorService.submit(() -> {
                dataAggregator.scrapeAndStoreAliexpressData(url);
                productDetailsLatch.countDown(); // Decrease the latch count
            });
        }

        // Shut down the executor service when all product details tasks are completed
        executorService.shutdown();

        // Wait until all product details scraping is complete
        try {
            productDetailsLatch.await();
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, "Error waiting for product details scraping to complete", e);
        }
    }

    // Method to schedule periodic concurrent pricing scraping for a list of URLs
    public void schedulePeriodicPricingScraping(List<String> amazonUrls, List<String> aliexpressUrls, long initialDelay, long period) {
        priceScheduler.schedulePeriodicPricingScraping(amazonUrls, aliexpressUrls, initialDelay, period);
    }
}
