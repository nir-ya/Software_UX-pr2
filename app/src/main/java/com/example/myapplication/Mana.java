package com.example.myapplication;


import java.util.Date;
import java.util.HashMap;

/**
 * A class representing a Falafel Mana from Shevah.
 * Each Mana is devised from the culinary aspect and logistic aspect.
 * The culinary aspect is the type (namely Pita, Lafa etc), as well as Tosafot.
 * The logistic aspect consists of the order status, the user who ordered the Mana (owner),
 * the paymentMethod method, and a serial number,
 */
public class Mana {

    // Constants
    //-- Types of manot
    static final String PITA = "pita";
    static final String LAFA = "lafa";
    static final String HALF_PITA = "half pita";
    static final String HALF_LAFA = "half lafa";
    //-- Payment methods
    static final int MEZUMAN = 0;
    static final int CREDIT = 1;
    //-- Order status options
    private static final String OPEN = "open";
    private static final String LOCKED = "locked";
    //-- Price Listing
    private static final int PITA_PRICE = 18;
    private static final int LAFA_PRICE = 22;
    private static final int HALF_PITA_PRICE = 10; //TODO
    private static final int HALF_LAFA_PRICE = 12; //TODO


    // Vars
    private String owner;
    private String status;
    private String type;
    private HashMap<String, Boolean> tosafot;
    private int paymentMethod;
    private String notes;
    private int price;
    private String ownerUserId;

    public Date getTimestamp() {
        return timestamp;
    }

    private Date timestamp;


    /**
     * An empty constructor for Mana object. Required for FireStore integration.
     */
    public Mana() { }

    /**
     * Complete constructor of a Mana object
     * @param type - String representing the break type of the Mana
     * @param notes
     * @param paymentMethod - String representing the payment method for the Mana (Credit / Cash)
     * @param tosafot - A Hashmap of Tosafot: String->Boolean
     * @param owner - A String representing the user who ordered the Mana
     * @param ownerUserId
     */
    public Mana(String type, String notes, int paymentMethod,
                HashMap<String, Boolean> tosafot, String owner, String ownerUserId) {

        if (owner.contains(" ")) {
            owner = owner.split(" ")[0];
        }
        this.owner = owner;

        this.ownerUserId = ownerUserId;
        this.status = OPEN;
        this.type = type;
        this.tosafot = tosafot;
        this.paymentMethod = paymentMethod;
        this.price = getPrice(type);
    }

    /**
     * A getter method for the price of the Mana
     * @return the price of the selected mana, based on the price list.
     */
    public int getPrice() {
        return price;
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

    public static String getHebType(String type) {
        switch (type) {
            case PITA:
                return "פיתה";
            case LAFA:
                return "לאפה";
            case HALF_PITA:
                return "חצי פיתה";
            case HALF_LAFA:
                return "חצי לאפה";
            default:
                return "שגיאה";
        }
    }

    /**
     * A getter, as required by FireStore
     * @return the payment method for the Mana
     */
    public int getPaymentMethod() {
        return paymentMethod;
    }

    /**
     * A getter, as required by FireStore
     * @return the Tosafot for the Mana, as HashMap
     */
    public HashMap<String, Boolean> getTosafot() {
        return tosafot;
    }

    public String getTosafotString() {
        StringBuilder tosafotString = new StringBuilder("");
        for (String key : tosafot.keySet()) {
            if (tosafot.get(key)) {
                System.out.println(key);
                tosafotString.append(Constants.hebrewExtras.get(key)).append(", ");
            }
        }

        if(tosafotString.length()>1) {
            tosafotString.delete(tosafotString.length() - 2, tosafotString.length());

            return tosafotString.toString();
        }

       return Constants.NO_TOSAFOT;
    }


    public String getOwnerUserId() {
        return ownerUserId;
    }
}
