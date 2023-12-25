/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import pricecomparison.transferobject.Price;
import pricecomparison.util.Scraper2Helper;

/**
 *
 * @author David
 */
public class Scraper2HelperTest {

    @Test
    void testExtractProductPrice() throws IOException {
        // Load properties from the store-config.properties file
        Properties properties = new Properties();
        try ( InputStream input = getClass().getClassLoader().getResourceAsStream("store-config.properties")) {
            properties.load(input);
        }

        // Retrieve the URL for model1.store2.url
        String model1Store2Url = properties.getProperty("model1.store2.url");
        
        // Document document = Jsoup.parse(readHtmlFromFile(HTML_FILE_PATH));

        Document document = Jsoup.connect(model1Store2Url).timeout(10000).get();
        Price price = Scraper2Helper.extractProductPrice(document);
        System.out.println(price.getCurrency() + price.getAmount());

        // Assert the result
        assertEquals(262.00, price.getAmount());
        assertEquals("$", price.getCurrency());
    }
}
