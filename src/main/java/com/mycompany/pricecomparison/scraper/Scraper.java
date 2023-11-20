/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.pricecomparison.scraper;

/**
 *
 * @author David
 */

public interface Scraper {  
    Product extractProductDetails(String html);
    
    ScraperResponse accessScrapingUrl(String url);
    
    String getName();
    
}

