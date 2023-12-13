/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import pricecomparison.transferobject.Price;
import pricecomparison.util.Scraper1Helper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 * @author David
 */
class Scraper1HelperTest {

    private static final String HTML_FILE_PATH = "src/test/resources/amazon.html";

    @Test
    void testExtractProductDescription() throws IOException {
        String htmlContent = readHtmlFromFile(HTML_FILE_PATH);
        Document document = Jsoup.parse(htmlContent);
        String description = Scraper1Helper.extractProductDescription(document);
        assertEquals("The iPhone 11 features a 6.1-inch LCD display that Apple calls a \"Liquid Retina HD Display.\" It features a 1792 x 828 resolution at 326ppi, a 1400:1 contrast ratio, 625 nits max brightness, True Tone support for adjusting the white balance to the ambient lighting, and wide color support for true-to-life colors. The iPhone 11 is available in six different colors: White, Black, Yellow, (PRODUCT)RED, Purple, and Green.", description);
    }

    @Test
    void testExtractProductPrice() throws IOException {
        Document document = Jsoup.parse(readHtmlFromFile(HTML_FILE_PATH));
        Price price = Scraper1Helper.extractProductPrice(document);
        assertEquals(259.00, price.getAmount());
        assertEquals("$", price.getCurrency());
    }

    @Test
    void testExtractProductImageUrl() throws IOException {
        Document document = Jsoup.parse(readHtmlFromFile(HTML_FILE_PATH));
        String imageUrl = Scraper1Helper.extractProductImageUrl(document);
        // Replace "YourExpectedImageUrl" with the expected URL
        assertEquals("https://m.media-amazon.com/images/W/MEDIAX_792452-T1/images/I/61MG3m5FhIL.__AC_SX300_SY300_QL70_FMwebp_.jpg", imageUrl);
    }

    @Test
    void testExtractProductManufacturer() throws IOException {
        Document document = Jsoup.parse(readHtmlFromFile(HTML_FILE_PATH));
        String manufacturer = Scraper1Helper.extractProductManufacturer(document);
        assertEquals("Apple", manufacturer);
    }
    // Add tests for variations and properties as needed...

    private String readHtmlFromFile(String filePath) throws IOException {
        return Files.readString(Path.of(filePath));
    }
}
