/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pricecomparison.entity;

import java.io.Serializable;
import javax.persistence.*;
/**
 *
 * @author David
 */


@Entity
@Table(name = "model_properties")
public class ModelProperty implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "model_id", nullable = false)
    private Model model;

    @Column(name = "property_key")
    private String propertyKey;

    @Column(name = "property_value")
    private String propertyValue;

    public ModelProperty() {
    }

    public ModelProperty(Integer id, Model model, String propertyKey, String propertyValue) {
        this.id = id;
        this.model = model;
        this.propertyKey = propertyKey;
        this.propertyValue = propertyValue;
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

    public String getPropertyKey() {
        return propertyKey;
    }

    public void setPropertyKey(String propertyKey) {
        this.propertyKey = propertyKey;
    }

    public String getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }
}
