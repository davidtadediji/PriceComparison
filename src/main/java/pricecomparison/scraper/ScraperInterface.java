/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pricecomparison.scraper;

import pricecomparison.dto.ScraperResponse;
import pricecomparison.dto.Product;

/**
 *
 * @author David
 */

public interface ScraperInterface {  
    Product extractProductDetails(String html);
    
    ScraperResponse accessScrapingUrl(String url);
    
    String getName();
    
}

