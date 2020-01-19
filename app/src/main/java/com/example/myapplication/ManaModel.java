package com.example.myapplication;


import java.util.HashMap;

/**
 * A class representing a Falafel Mana from Shevah.
 * Each Mana is devised from the culinary aspect and logistic aspect.
 * The culinary aspect is the type (namely Pita, Lafa etc), as well as Tosafot.
 * The logistic aspect consists of the order status, the user who ordered the Mana (owner),
 * the paymentMethod method, and a serial number,
 */
public class ManaModel {

    // Constants
    //-- Types of manot
    static final String PITA = "pita";
    static final String LAFA = "lafa";
    static final String HALF_PITA = "half pita";
    static final String HALF_LAFA = "half lafa";
    //-- Payment methods
    static final String MEZUMAN = "mezuman";
    static final String CREDIT = "credit card";
    //-- Order status options
    private static final String OPEN = "open";
    private static final String LOCKED = "locked";
    //-- Price Listing
    private static final int PITA_PRICE = 18;
    private static final int LAFA_PRICE = 22;
    private static final int HALF_PITA_PRICE = 0; //TODO
    private static final int HALF_LAFA_PRICE = 0; //TODO

    // Vars
    private String owner;
    private String status;
    private String type;
    private HashMap<String, Boolean> tosafot;
    private String paymentMethod;
    private String notes;
    private int price;


    /**
     * An empty constructor for Mana object. Required for FireStore integration.
     */
    public ManaModel() {

    }

    /**
     * Complete constructor of a Mana object
     * @param type - String representing the break type of the Mana
     * @param notes
     * @param paymentMethod - String representing the payment method for the Mana (Credit / Cash)
     * @param tosafot - A Hashmap of Tosafot: String->Boolean
     * @param owner - A String representing the user who ordered the Mana
     */
    public ManaModel(String type, String notes, String paymentMethod,
        HashMap<String, Boolean> tosafot, String owner) {
        this.owner = owner;
        this.status = OPEN;
        this.type = type;
        this.tosafot = tosafot;
        this.paymentMethod = paymentMethod;
        this.notes = notes;
        price = getPrice();
    }

    /**
     * A getter method for the price of the Mana
     * @return the price of the selected mana, based on the price list.
     */
    public int getPrice() {
        switch (type) {
            case PITA:
                return PITA_PRICE;
            case LAFA:
                return LAFA_PRICE;
            case HALF_PITA:
                return HALF_PITA_PRICE;
            case HALF_LAFA:
                return HALF_LAFA_PRICE;
            default:
                System.out.println("ooops2");
                return 0;
        }
    }

    /**
     * A static method for getting prices of dishes
     * @return the price of the selected mana, based on the price list.
     */
    public static int getPrice(String type) {
        switch (type) {
            case PITA:
                return PITA_PRICE;
            case LAFA:
                return LAFA_PRICE;
            case HALF_PITA:
                return HALF_PITA_PRICE;
            case HALF_LAFA:
                return HALF_LAFA_PRICE;
            default:
                System.out.println("ooops2");
                return 0;
        }
    }

    /**
     * A getter, as required by FireStore
     * @return the owner for the Mana
     */
    public String getOwner() {
        return owner;
    }

    /**
     * A getter, as required by FireStore
     * @return the status of the Mana
     */
    public String getStatus() {
        return status;
    }

    /**
     * A getter, as required by FireStore
     * @return the type of the Mana
     */
    public String getType() {
        return type;
    }

    /**
     * A getter, as required by FireStore
     * @return the payment method for the Mana
     */
    public String getPaymentMethod() {
        return paymentMethod;
    }

    /**
     * A getter, as required by FireStore
     * @return the Tosafot for the Mana, as HashMap
     */
    public HashMap<String, Boolean> getTosafot() {
        return tosafot;
    }

    /**
     * A getter, as required by FireStore
     * @return the notes for the Mana
     */
    public String getNotes() {
        return notes;
    }
}
