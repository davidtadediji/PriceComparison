/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package pricecomparison;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author David
 */
public class Pricecomparison {

    private static final Logger logger = Logger.getLogger(Pricecomparison.class.getName());
    private static final long INITIAL_DELAY = 0;
    private static final long PERIOD = 3600;

    public static void main(String[] args) {
        logger.log(Level.INFO, "Testing logs");

        // Load the Spring application context
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        // Retrieve the ScraperManager bean from the context
        ScraperManager scraperManager = context.getBean(ScraperManager.class);

        List<ModelConfig> modelConfigs = loadUrlsFromConfig("store-config.properties");

        // Schedule concurrent scraping for product details for each store
        scraperManager.scheduleConcurrentScraping(modelConfigs, INITIAL_DELAY, PERIOD);

    }

    private static List<ModelConfig> loadUrlsFromConfig(String configFile) {
        List<ModelConfig> modelConfigs = new ArrayList<>();

        try ( InputStream input = Pricecomparison.class.getClassLoader().getResourceAsStream(configFile)) {
            Properties prop = new Properties();

            // Load the properties file
            if (input != null) {
                prop.load(input);

                // Read model configurations
                for (int i = 1;; i++) {
                    String modelName = prop.getProperty("model" + i + ".name");
                    if (modelName == null) {
                        break; // No more models in the configuration
                    }

                    String mainUrlPlaceholder = prop.getProperty("model" + i + ".mainUrl");
                    String mainUrl = resolvePlaceholder(mainUrlPlaceholder, prop);

                    List<String> storeUrls = new ArrayList<>();
                    for (int j = 1;; j++) {
                        String storeName = prop.getProperty("model" + i + ".store" + j + ".name");
                        if (storeName == null) {
                            break; // No more stores for the model
                        }

                        String storeUrlPlaceholder = prop.getProperty("model" + i + ".store" + j + ".url");
                        String storeUrl = resolvePlaceholder(storeUrlPlaceholder, prop);

                        // Exclude main URL from store URLs
                        if (!storeUrl.equals(mainUrl)) {
                            storeUrls.add(storeUrl);
                        }
                    }

                    ModelConfig modelConfig = new ModelConfig(mainUrl, modelName, storeUrls);
                    modelConfigs.add(modelConfig);
                }

                logger.log(Level.INFO, "Loaded model configurations");

            } else {
                logger.log(Level.SEVERE, "Unable to load configuration file: {0}", configFile);
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error reading configuration file", e);
        }

        return modelConfigs;
    }

    private static String resolvePlaceholder(String placeholder, Properties prop) {
        // Resolve placeholder recursively until no more placeholders are found
        while (placeholder != null && placeholder.contains("${")) {
            String key = placeholder.substring(2, placeholder.indexOf("}"));
            placeholder = prop.getProperty(key);
        }
        return placeholder;
    }

}
