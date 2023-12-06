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

/**
 *
 * @author David
 */
public class Scraper2Helper {

    public static String extractProductCurrency(Document document) {
        Element currencyElement = document.select("div.myClass").first();
        return currencyElement != null ? currencyElement.text() : null;
    }

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

    public static int extractProductPrice(Document document) {
        if (document != null) {
            Element heading = document.select("h1").first();
            return heading != null ? Integer.parseInt(heading.text()) : 0;
        }
        return 0;
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
