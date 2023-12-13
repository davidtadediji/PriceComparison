/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pricecomparison.util;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pricecomparison.transferobject.Variation;
import java.util.ArrayList;
import java.util.List;
import pricecomparison.transferobject.Price;
import pricecomparison.transferobject.Property;

/**
 *
 * @author David
 */
public class Scraper1Helper {

    public static String extractProductTitle(Document document) {
        return document != null ? document.title() : null;
    }

    public static String extractProductDescription(Document document) {
        if (document != null) {
            // Use a more specific selector to target the product description
            Element productDescriptionElement = document.select("h2:contains(Product Description) + div#productDescription").first();

            return productDescriptionElement != null ? productDescriptionElement.text() : null;
        }
        return null;
    }

    public static Price extractProductPrice(Document document) {
        if (document != null) {
            // Select the specific price element based on the class
            Element priceElement = document.select("span.a-price.a-text-price.header-price.a-size-base.a-text-normal[data-a-size='b'][data-a-color='price'] span.a-offscreen").first();

            if (priceElement != null) {
                // Extract the price string
                String priceString = priceElement.text();
                System.out.println(priceString); // string is $259.00

                // Extract the currency
                String currency = extractCurrency(priceString);

                // Remove non-numeric characters and parse to a double
                double price = Double.parseDouble(priceString.replaceAll("[^0-9.]", ""));

                return new Price(price, currency);
            }
        }

        return new Price(0.0, null);
    }

    private static String extractCurrency(String priceString) {
        // Assuming the currency symbol is the first character in the price string
        return priceString.substring(0, 1);
    }

    public static String extractProductImageUrl(Document document) {
        if (document != null) {
            // Select the specific image element based on its class
            Element imageElement = document.select("img.a-dynamic-image").first();

            // Check if the image element is found
            if (imageElement != null) {
                // Get the value of the 'src' attribute from the image element
                return imageElement.attr("src");
            }
        }
        return null;
    }

    public static String extractProductManufacturer(Document document) {
        if (document != null) {
            Element manufacturerElement = document.select("tr.po-brand td.a-span9 span.a-size-base").first();
            return manufacturerElement != null ? manufacturerElement.text() : null;
        }
        return null;
    }

    public static List<Variation> extractProductVariations(Document document) {
        List<Variation> variations = new ArrayList<>();
        if (document != null) {
            Elements variationElements = document.select(".variation-class");
            for (Element variationElement : variationElements) {
                String name = variationElement.select(".name-class").text();
                String value = variationElement.select(".value-class").text();
                variations.add(new Variation(name, value));
            }
        }
        return variations;
    }

    public static List<Property> extractProductProperties(Document document) {
        List<Property> properties = new ArrayList<>();
        if (document != null) {
            Elements propertyElements = document.select(".property-class");
            for (Element propertyElement : propertyElements) {
                String name = propertyElement.select(".name-class").text();
                String value = propertyElement.select(".value-class").text();
                properties.add(new Property(name, value));
            }
        }
        return properties;
    }
}
