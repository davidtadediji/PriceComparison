/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pricecomparison.entity;

import java.io.Serializable;
import jakarta.persistence.*;
import java.sql.Timestamp;

/**
 *
 * @author David
 */
@Entity
@Table(name = "comparison")
public class Comparison implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;
    private Double amount;
    private String currency;
    private String store;

    @Column(name = "store_logo_url")
    private String storeLogoUrl;

    @Column(name = "last_updated")
    private Timestamp lastUpdated;

    @ManyToOne
    @JoinColumn(name = "model_id")
    private Model model;

    @ManyToOne
    @JoinColumn(name = "model_variation_id")
    private ModelVariation modelVariation;

    public Comparison() {
    }

    public Comparison(Long id) {
        this.id = id;
    }

    public Comparison(Long id, String url, Double amount, String currency, String store, String storeLogoUrl, Timestamp lastUpdated, Model model, ModelVariation modelVariation) {
        this.id = id;
        this.url = url;
        this.amount = amount;
        this.currency = currency;
        this.store = store;
        this.storeLogoUrl = storeLogoUrl;
        this.lastUpdated = lastUpdated;
        this.model = model;
        this.modelVariation = modelVariation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getStoreLogoUrl() {
        return storeLogoUrl;
    }

    public void setStoreLogoUrl(String storeLogoUrl) {
        this.storeLogoUrl = storeLogoUrl;
    }

    public Timestamp getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public ModelVariation getModelVariation() {
        return modelVariation;
    }

    public void setModelVariation(ModelVariation modelVariation) {
        this.modelVariation = modelVariation;
    }
}
