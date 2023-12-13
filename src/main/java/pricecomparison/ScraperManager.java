/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pricecomparison;

import java.util.ArrayList;
import pricecomparison.service.DataAggregator;
import pricecomparison.scheduler.PriceScrapingScheduler;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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

    // Method to schedule concurrent scraping for product details and periodic pricing scraping for a list of main URLs
    public void scheduleConcurrentScraping(List<ModelConfig> modelConfigs, long initialDelay, long period) {
        logger.log(Level.INFO, "Initial product scraping begun");

        // Create a thread pool with a fixed number of threads
        int numThreads = Runtime.getRuntime().availableProcessors(); // Adjust the number as needed
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);

        try {
            // List to hold CompletableFuture instances
            List<CompletableFuture<Void>> futures = new ArrayList<>();

            // Submit tasks for each model configuration to the thread pool
            for (ModelConfig modelConfig : modelConfigs) {
                CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                    // Scraping and storing product details for the main URL
                    dataAggregator.scrapeAndStoreData(modelConfig.getMainUrl(), modelConfig.getModelName());

                    // If the main URL is scraped successfully, scrape and store comparison details for other URLs
                    for (String storeUrl : modelConfig.getStoreUrls()) {
                        // You can call the original method here if needed
                        dataAggregator.scrapeAndStoreData(storeUrl, modelConfig.getModelName());

                        // dataAggregator.scrapeAndStoreComparisonDetails(storeUrl, modelConfig.getMainUrl());
                        // For demonstration purposes, logging a message
                        logger.log(Level.INFO, "Comparison details for {0} after successful main URL scraping.", storeUrl);
                    }
                }, executorService);

                futures.add(future);
            }

            // Wait for all CompletableFuture instances to complete
            CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
            allOf.join();

            // If all tasks completed successfully, schedule periodic pricing scraping
            if (!allOf.isCompletedExceptionally()) {
                schedulePeriodicPricingScraping(modelConfigs, initialDelay, period);
            }

        } finally {
            // Always shut down the executor service
            executorService.shutdown();
        }
    }

    // Method to schedule periodic concurrent pricing scraping for a list of main URLs
    public void schedulePeriodicPricingScraping(List<ModelConfig> modelConfigs, long initialDelay, long period) {
//        priceScheduler.schedulePeriodicPricingScraping(modelConfigs, initialDelay, period);
    }
}
