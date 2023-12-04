/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pricecomparison.dto;

/**
 *
 * @author David
 */


public class Variation {

    private String name; // Variation name, e.g., "Size", "Color"
    private String value; // Variation value, e.g., "Small", "Red"

    // Constructors, getters, and setters

    public Variation(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    
}
