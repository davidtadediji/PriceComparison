/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pricecomparison;

import java.util.List;

/**
 *
 * @author David
 */
public class ModelConfig {
    private String mainUrl;
    private String modelName;
    private List<String> storeUrls;

    // Constructors, getters, and setters...

    public ModelConfig(String mainUrl, String modelName, List<String> storeUrls) {
        this.mainUrl = mainUrl;
        this.modelName = modelName;
        this.storeUrls = storeUrls;
    }

    public String getMainUrl() {
        return mainUrl;
    }

    public void setMainUrl(String mainUrl) {
        this.mainUrl = mainUrl;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public List<String> getStoreUrls() {
        return storeUrls;
    }

    public void setStoreUrls(List<String> storeUrls) {
        this.storeUrls = storeUrls;
    }
}
