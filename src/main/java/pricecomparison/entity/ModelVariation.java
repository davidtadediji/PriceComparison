/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pricecomparison.entity;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 *
 * @author David
 */
@Entity
@Table(name = "model_variation")
public class ModelVariation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "model_id")
    private Model model;

    @Column(name = "variation_name")
    private String variationName;

    @Column(name = "variation_value")
    private String variationValue;

    public ModelVariation() {
    }

    public ModelVariation(Long id) {
        this.id = id;
    }

    public ModelVariation(Long id, Model model, String variationName, String variationValue) {
        this.id = id;
        this.model = model;
        this.variationName = variationName;
        this.variationValue = variationValue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public String getVariationName() {
        return variationName;
    }

    public void setVariationName(String variationName) {
        this.variationName = variationName;
    }

    public String getVariationValue() {
        return variationValue;
    }

    public void setVariationValue(String variationValue) {
        this.variationValue = variationValue;
    }
}
