package com.example.myapplication;


import androidx.annotation.NonNull;
import java.util.HashMap;

/**
 * A class representing a Falafel Mana from Shevah.
 * Each Mana is devised from the culinary aspect and logistic aspect.
 * The culinary aspect is the type (namely Pita, Lafa etc), as well as Tosafot.
 * The logistic aspect consists of the order status, the user who ordered the Mana (owner),
 * the payment method, and a serial number,
 */
public class Mana {

    // Constants
    //-- Types of manot
    static final int INVALID = 0;
    static final int PITA = 1;
    static final int LAFA = 2;
    static final int HALF_PITA = 3;
    static final int HALF_LAFA = 4;
    //-- Payment methods
    static final int MEZUMAN = 0;
    static final int CREDIT = 1;
    //-- Order status options
    private static final String OPEN = "open";
    private static final int LOCKED = 2;
    private static final int APP = 3;
    //-- Tosafot
    private static final int TOMATO = 1;
    private static final int CUCUMBER = 2;
    private static final int KRUV = 3;
    private static final int ONION = 4;
    private static final int THINA = 5;
    private static final int HUMUS = 6;
    private static final int HARIF = 7;
    private static final int AMBA = 8;
    //-- Tosafot quantifiers
    private static final int NO = 0;
    private static final int LITTLE = 1;
    private static final int YES = 2;
    private static final int A_LOT = 3;
    //-- Prices
    private static final int PITA_PRICE = 18;
    private static final int LAFA_PRICE = 22;
    private static final int HALF_PITA_PRICE = 0; //TODO
    private static final int HALF_LAFA_PRICE = 0; //TODO

    // Vars
    //-- Culinary related vars
    public int type;
    public int price;
    public int thina, amba, tomato, cucumber, onion, kruv, hamuz, eggplant;
    public HashMap<String, Integer> tosafot = new HashMap<>();
    public String costumer_notes;
    //-- Logistic related vars
    public String status;
    public int in_order;
    public String owner;
    public int paymentMethod;

    public int getPaymentMethod() {
        return paymentMethod;
    }


    public static int counter = 1;


    public Mana() {
        //empty constructor needed here
    }

    /**
     * A basic Mana constructor with only the user that ordered it
     *
     * @param owner the user who ordered the Mana
     */

    Mana(String owner, int paymentMethod, int price) {
        this.owner = owner;
        this.paymentMethod = paymentMethod;
        this.price = price;
    }


    // Getters

    public int getType() {
        return type;
    }

    /**
     * returns the type of the Mana represented as a String
     *
     * @param type the type of the Mana (int)
     * @return String representation of the type of the Mana
     */
    public static String getTypeStr(int type) {
        switch (type) {
            case PITA:
                return "Pita";
            case LAFA:
                return "Lafa";
            case HALF_PITA:
                return "Half Pita";
            case HALF_LAFA:
                return "Half Lafa";
            default:
                return "Type not set";
        }
    }

    int getPrice() {
        return price;
    }

    /**
     * Gets the payment method represeneted as a String
     *
     * @param payment_method the payment method for the Mana, an int
     * @return a String representation of the payment method associated with the Mana
     */
    public String paymentStr(int payment_method) {
        switch (payment_method) {
            case MEZUMAN:
                return "Mezuman";
            case CREDIT:
                return "Credit";
            case APP:
                return "Applicatio";
            default:
                return "ooops";
        }
    }

    /**
     * A static method that stores the information of the prices for each Mana
     *
     * @param type the type of the Mana, as an int
     * @return the price associated with the type, an int
     */
    public static int priceByType(int type) {
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

    // Setters
    boolean setType(int new_type) {
        if (status == OPEN) {
            type = new_type;
            price = getPrice();
            return true;
        }
        return false;
    }

    boolean setPaymentMethod(int new_method) {
        if (status == OPEN) {
            paymentMethod = new_method;
            return true;
        }
        return false;
    }

    boolean addNote(String note) {
        if (status == OPEN) {
            costumer_notes = note;
            return true;
        }
        return false;
    }

    boolean isReadyToOrder() {
        return (type != INVALID) && (paymentMethod != INVALID);
    }


    void addToOrder(int order_no) {
        in_order = order_no;
    }

    int inOrder() {
        return in_order;
    }

    String getOwner() {
        return this.owner;
    }

    void lock(){

    }
}