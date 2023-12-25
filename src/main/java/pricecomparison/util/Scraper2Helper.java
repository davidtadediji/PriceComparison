/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pricecomparison.util;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pricecomparison.transferobject.Variation;
import pricecomparison.transferobject.Property;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import pricecomparison.transferobject.Price;

/**
 *
 * @author David
 */
public class Scraper2Helper {

    public static String extractProductTitle(Document document) {
        return document != null ? document.title() : null;
    }

    public static String extractProductDescription(Document document) {
        if (document != null) {
            Elements paragraphs = document.select("p");
            return paragraphs != null ? paragraphs.text() : null;
        }
        return null;
    }

    public static Price extractProductPrice(Document document) {
        if (document != null) {
            // Extract the content of the script tag containing window.runParams
            Element scriptElement = document.select("script:containsData(window.runParams)").first();

            if (scriptElement != null) {
                // Extract the content of the script
                String scriptContent = scriptElement.data();

                // Find the start and end index of window.runParams
                int startIndex = scriptContent.indexOf("{");
                int endIndex = scriptContent.lastIndexOf("}");

                if (startIndex != -1 && endIndex != -1 && endIndex > startIndex) {
                    // Extract the substring between { and }
                    String runParamsSubstring = scriptContent.substring(startIndex, endIndex + 1);

                    // Now you can parse runParamsSubstring or process it further
                    System.out.println("runParamsSubstring: " + runParamsSubstring);

                    // Process runParamsSubstring to extract relevant information
                    // For simplicity, you can use regular expressions or other string manipulation
                    // Example: Extract formattedAmount and currency using regex
                    String formattedAmount = extractValue(runParamsSubstring, "formattedAmount\":\"(.*?)\"");
                    String currency = extractValue(runParamsSubstring, "currency\":\"(.*?)\"");

                    // Convert formattedAmount to a double (remove non-numeric characters)
                    double price = Double.parseDouble(formattedAmount.replaceAll("[^0-9.]", ""));

                    return new Price(price, currency);
                }
            }
        }

        return new Price(0.0, null);
    }

    // Helper method to extract values using regex
    private static String extractValue(String input, String pattern) {
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(input);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    public static String extractProductImageUrl(Document document) {
        if (document != null) {
            Elements links = document.select("a[href]");
            return links != null && !links.isEmpty() ? links.first().attr("href") : null;
        }
        return null;
    }

    public static String extractProductManufacturer(Document document) {
        if (document != null) {
            Element manufacturerElement = document.select("div.myClass").first();
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
