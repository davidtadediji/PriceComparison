/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pricecomparison.transferobject;

/**
 *
 * @author David
 */

public class Property {
    
    private String propertyKey;
    private String propertyValue;

    // Constructors, getters, and setters

    public Property(String propertyKey, String propertyValue) {
        this.propertyKey = propertyKey;
        this.propertyValue = propertyValue;
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
