/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pricecomparison.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
/**
 *
 * @author David
 */

@Entity
@Table(name = "model")
public class Model implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String description;
    private String manufacturer;
    private String imageUrl;

    @Column(name = "created_at")
    private Timestamp createdAt;

    private String slug;

    @Column(columnDefinition = "jsonb")
    private String properties;

    @OneToMany(mappedBy = "model", cascade = CascadeType.ALL)
    private List<ModelVariation> variations;

    public Model() {
    }

    public Model(Integer id) {
        this.id = id;
    }
    
    

    public Model(Integer id, String name, String description, String manufacturer, String imageUrl, Timestamp createdAt, String slug, String properties, List<ModelVariation> variations) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.manufacturer = manufacturer;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
        this.slug = slug;
        this.properties = properties;
        this.variations = variations;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getProperties() {
        return properties;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }

    public List<ModelVariation> getVariations() {
        return variations;
    }

    public void setVariations(List<ModelVariation> variations) {
        this.variations = variations;
    }
}
