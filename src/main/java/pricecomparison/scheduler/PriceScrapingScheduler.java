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

    public void schedulePricingScraping(List<String> urls) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        // Schedule the task to run every 24 hours (adjust as needed)
        scheduler.scheduleAtFixedRate(() -> {
            for (String url : urls) {
                dataAggregator.scrapeAndStorePricingData(url);
            }
        }, 0, 24, TimeUnit.HOURS);
    }
}
