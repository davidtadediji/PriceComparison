/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pricecomparison.util;

import java.sql.Timestamp;
import com.github.slugify.Slugify;
import org.springframework.stereotype.Component;
import pricecomparison.transferobject.Product;
import pricecomparison.transferobject.Variation;
import pricecomparison.transferobject.Property;
import pricecomparison.entity.Comparison;
import pricecomparison.entity.Model;
import pricecomparison.entity.ModelVariation;
import pricecomparison.entity.ModelProperty;
import pricecomparison.repository.ComparisonRepository;
import pricecomparison.repository.ModelRepository;
import pricecomparison.repository.ModelVariationRepository;
import pricecomparison.repository.ModelPropertyRepository;
import pricecomparison.transferobject.Price;

/**
 *
 * @author David
 */
@Component
public class DataAggregatorHelper {

    private final ModelRepository modelDao;
    private final ModelVariationRepository modelVariationDao;
    private final ModelPropertyRepository modelPropertyDao;
    private final ComparisonRepository comparisonDao;

    public DataAggregatorHelper(ModelRepository modelDao, ModelVariationRepository modelVariationDao, ModelPropertyRepository modelPropertyDao, ComparisonRepository comparisonDao) {
        this.modelDao = modelDao;
        this.modelVariationDao = modelVariationDao;
        this.comparisonDao = comparisonDao;
        this.modelPropertyDao = modelPropertyDao;
    }

    public Model createAndSaveModel(Product product) {
        // Check if a model with the same name or URL exists
        Model existingModel = modelDao.getModelByName(product.getTitle());
        if (existingModel != null) {
            return existingModel;
        }
        // Create and save the new model
        Model model = new Model();
        model.setName(product.getTitle());
        model.setDescription(product.getDescription());
        model.setImageUrl(product.getImageUrl());
        model.setManufacturer(product.getManufacturer());
        model.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        model.setSlug(generateSlug(product.getTitle()));
        modelDao.saveOrUpdateModel(model);
        modelDao.saveOrUpdateModel(model);
        return model;
    }

    public void createAndSaveModelVariations(Product product, Model model) {
        // Iterate over the variations in the scraped product
        for (Variation variation : product.getVariations()) {
            // Check if a model variation with the same name and value already exists
            ModelVariation existingVariation = modelVariationDao.getModelVariationByNameAndValue(model.getId(), variation.getName(), variation.getValue());

            if (existingVariation != null) {
                // Model variation already exists, you may want to update it if needed
                // For simplicity, this example does not update existing model variations
            } else {
                // Model variation does not exist, create and save it
                ModelVariation modelVariation = new ModelVariation();
                modelVariation.setModel(model);
                modelVariation.setVariationName(variation.getName());
                modelVariation.setVariationValue(variation.getValue());
                modelVariationDao.saveOrUpdateModelVariation(modelVariation);
            }
        }
    }

    public void createAndSaveModelProperties(Product product, Model model) {
        for (Property property : product.getProperties()) {
            ModelProperty modelProperty = new ModelProperty();
            modelProperty.setModel(model);
            modelProperty.setPropertyKey(property.getPropertyKey());
            modelProperty.setPropertyValue(property.getPropertyValue());
            modelPropertyDao.saveOrUpdateModelProperty(modelProperty);
        }
    }

    public void createAndSaveComparison(Product product, Model model, String storeName, String logoUrl, String url) {
        Comparison comparison = new Comparison();
        comparison.setUrl(url);
        comparison.setAmount(product.getPrice().getAmount());
        comparison.setCurrency(product.getPrice().getCurrency());
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

    public void updateComparisonPrice(Price price, String storeName, String url) {
        Comparison existingComparison = comparisonDao.getComparisonByUrl(url);
        existingComparison.setAmount(price.getAmount());
        existingComparison.setCurrency(price.getCurrency());
        existingComparison.setLastUpdated(new Timestamp(System.currentTimeMillis()));
        comparisonDao.saveOrUpdateComparison(existingComparison);
    }

}
