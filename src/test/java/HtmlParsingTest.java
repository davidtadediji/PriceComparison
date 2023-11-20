
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import com.mycompany.pricecomparison.scraper.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 * @author David
 */




public class HtmlParsingTest {

    @ParameterizedTest
    @MethodSource("provideScrapersAndHtml")
    void testExtractProductDetails(Scraper scraper, String html) {
        Product product = scraper.extractProductDetails(html);
        assertCommonProductDetails(product);
    }

    // Common assertions to avoid code duplication
    private void assertCommonProductDetails(Product product) {
        assertEquals("Test Title", product.getTitle());
        assertEquals("Test Description", product.getDescription());
        assertEquals("Test Price", product.getPrice());
        assertEquals("https://www.imagelink.com", product.getImageUrl());
    }

    private static String readHtmlFromFile(String filePath) {
        try {
            return Files.readString(Path.of(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read HTML file: " + filePath, e);
        }
    }

    private static Stream<Arguments> provideScrapersAndHtml() {
        return Stream.of(
                Arguments.of(new Scraper1(), readHtmlFromFile("src/test/resources/Scraper1.html")),
                Arguments.of(new Scraper2(), readHtmlFromFile("src/test/resources/Scraper2.html"))
        );
    }
}
