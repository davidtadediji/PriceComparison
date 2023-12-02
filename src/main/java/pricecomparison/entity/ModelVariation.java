/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pricecomparison.entity;

import javax.persistence.*;
/**
 *
 * @author David
 */

@Entity
@Table(name = "model_variations")
public class ModelVariation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "model_id")
    private Model model;

    @Column(name = "variation_name")
    private String variationName;

    @Column(name = "variation_value")
    private String variationValue;

    public ModelVariation() {
    }

    public ModelVariation(Integer id) {
        this.id = id;
    }

    public ModelVariation(Integer id, Model model, String variationName, String variationValue) {
        this.id = id;
        this.model = model;
        this.variationName = variationName;
        this.variationValue = variationValue;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
