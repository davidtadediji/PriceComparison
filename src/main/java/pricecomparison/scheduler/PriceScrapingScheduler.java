/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pricecomparison.scheduler;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pricecomparison.ModelConfig;
import pricecomparison.service.DataAggregator;


/**
 *
 * @author David
 */


@Component
public class PriceScrapingScheduler {

    @Autowired
    private DataAggregator dataAggregator;

    // Method to schedule periodic concurrent pricing scraping for a list of main URLs
    public void schedulePeriodicPricingScraping(List<ModelConfig> modelConfigs, long initialDelay, long period) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

        // Schedule tasks for each model configuration at fixed intervals
        for (ModelConfig modelConfig : modelConfigs) {
            for (String url : modelConfig.getStoreUrls()) {
                scheduler.scheduleAtFixedRate(() -> {
                    // You can call the original method here if needed
                    dataAggregator.scrapeAndStorePricingData(url);
                }, initialDelay, period, TimeUnit.SECONDS);
            }
        }

        // Shut down the scheduler when needed
        // Note: You may want to handle shutdown gracefully based on your application requirements
        // scheduler.shutdown();
    }
}
