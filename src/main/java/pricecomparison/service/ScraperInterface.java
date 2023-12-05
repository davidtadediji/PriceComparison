/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pricecomparison.service;

import pricecomparison.transferobject.Response;
import pricecomparison.transferobject.Product;

/**
 *
 * @author David
 */
public interface ScraperInterface {

    Product extractProductDetails(String html);

    Response accessScrapingUrl(String url);

    String getName();

    String getLogoUrl();
}
