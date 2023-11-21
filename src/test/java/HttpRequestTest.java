/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import org.junit.jupiter.params.ParameterizedTest;

import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

import pricecomparison.Scraper;
import pricecomparison.Scraper1;
import pricecomparison.Scraper2;
import pricecomparison.ScraperResponse;


/**
 *
 * @author David
 */

public class HttpRequestTest {

    // Parameterized test for different scenarios
    @ParameterizedTest(name = "Testing {0}")
    @MethodSource("scrapersAndUrls")
    void testScenarios(Scraper scraper, String url) {
        // Make an HTTP request
        ScraperResponse response = scraper.accessScrapingUrl(url);

        // Check specific conditions based on the scenario
        if (url.contains("timeout")) {
            assertEquals(408, response.getStatusCode(), "Expected a 408 status code for the timeout with " + scraper.getName());
        } else if (url.contains("nonexistent")) {
            assertEquals(404, response.getStatusCode(), "Expected a 404 status code for the invalid URL with " + scraper.getName());
        } else if (url.contains("connection_error")) {
            // Simulate a connection error scenario
            assertTrue(response.getStatusCode() == 502 || response.getStatusCode() == 503,
                    "Expected a 502 or 503 status code for the connection error with " + scraper.getName());
        }else {
            // For a valid URL with status code 200, ensure content is not empty
            assertFalse(response.getContent().isEmpty(), "Response for a valid URL should not be empty");
            assertEquals(200, response.getStatusCode(), "Expected a 200 status code for the valid URL with " + scraper.getName());
            // Add more assertions based on the expected content of the response
        }
    }

    // Provide a stream of test data for the parameterized test
    private static Stream<Object[]> scrapersAndUrls() {
        return Stream.of(
                new Object[]{new Scraper1(), "https://example.com/product/123"},
                new Object[]{new Scraper2(), "https://example.com/product/456"},
                new Object[]{new Scraper1(), "https://example.com/nonexistent"},
                new Object[]{new Scraper2(), "https://example.com/timeout"},
                new Object[]{new Scraper1(), "https://example.com/connection_error"}
        // Add more scenarios as needed
        );
    }
}
