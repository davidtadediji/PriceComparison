/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pricecomparison.transferobject;

/**
 *
 * @author David
 */

public class Price {

    private double amount;
    private String currency;

    public Price(double amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    // Getters and setters for each field

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
