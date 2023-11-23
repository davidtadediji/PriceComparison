/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import pricecomparison.scraper.Scraper1;
import pricecomparison.scraper.Scraper2;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 *
 * @author David
 */
public class MalformedHtmlTest {

    @Test
    void testMalformedHtml() {
        Scraper1 scraper1 = new Scraper1();
        Scraper2 scraper2 = new Scraper2();

        // Test for Scraper1
        assertDoesNotThrow(() -> {
            scraper1.extractProductTitle("<malformed>HTML</malformed>");
            scraper1.extractProductDescription("<malformed>HTML</malformed>");
            scraper1.extractProductPrice("<malformed>HTML</malformed>");
            scraper1.extractProductImageUrl("<malformed>HTML</malformed>");
            // You can add more extraction functions as needed
        });

        // Test for Scraper2
        assertDoesNotThrow(() -> {
            scraper2.extractProductTitle("<malformed>HTML</malformed>");
            scraper2.extractProductDescription("<malformed>HTML</malformed>");
            scraper2.extractProductPrice("<malformed>HTML</malformed>");
            scraper2.extractProductImageUrl("<malformed>HTML</malformed>");
            // You can add more extraction functions as needed
        });
    }
}
