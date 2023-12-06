/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pricecomparison.util;

import com.github.slugify.Slugify;
import pricecomparison.transferobject.Product;
import pricecomparison.transferobject.Variation;
import pricecomparison.entity.Comparison;
import pricecomparison.entity.Model;
import pricecomparison.entity.ModelVariation;
import pricecomparison.repository.ComparisonRepository;
import pricecomparison.repository.ModelRepository;
import pricecomparison.repository.ModelVariationRepository;

import java.sql.Timestamp;

/**
 *
 * @author David
 */
public class DataAggregatorHelper {

    private final ModelRepository modelDao;
    private final ModelVariationRepository modelVariationDao;
    private final ComparisonRepository comparisonDao;

    public DataAggregatorHelper(ModelRepository modelDao, ModelVariationRepository modelVariationDao, ComparisonRepository comparisonDao) {
        this.modelDao = modelDao;
        this.modelVariationDao = modelVariationDao;
        this.comparisonDao = comparisonDao;
    }

    public Model createAndSaveModel(Product product) {
        Model model = new Model();
        model.setName(product.getTitle());
        model.setDescription(product.getDescription());
        model.setImageUrl(product.getImageUrl());
        model.setManufacturer(product.getManufacturer());
        model.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        model.setSlug(generateSlug(product.getTitle()));
        modelDao.saveOrUpdateModel(model);
        return model;
    }

    public void createAndSaveModelVariations(Product product, Model model) {
        for (Variation variation : product.getVariations()) {
            ModelVariation modelVariation = new ModelVariation();
            modelVariation.setModel(model);
            modelVariation.setVariationName(variation.getName());
            modelVariation.setVariationValue(variation.getValue());
            modelVariationDao.saveOrUpdateModelVariation(modelVariation);
        }
    }

    public void createAndSaveComparison(Product product, Model model, String storeName, String logoUrl, String url) {
        Comparison comparison = new Comparison();
        comparison.setUrl(url);
        comparison.setAmount(product.getPrice());
        comparison.setCurrency(product.getCurrency());
        comparison.setStore(storeName);
        comparison.setStoreLogoUrl(logoUrl);
        comparison.setLastUpdated(new Timestamp(System.currentTimeMillis()));
        comparison.setModel(model);
        comparisonDao.saveOrUpdateComparison(comparison);
    }

    private String generateSlug(String title) {
        final Slugify slg = Slugify.builder().build();
        return slg.slugify(title);
    }

    public void storeDataCommonLogic(Product product, String storeName, String logoUrl, String url) {
        // Use the helper class to perform common logic
        Model model = createAndSaveModel(product);
        createAndSaveModelVariations(product, model);
        createAndSaveComparison(product, model, storeName, logoUrl, url);
    }
}
