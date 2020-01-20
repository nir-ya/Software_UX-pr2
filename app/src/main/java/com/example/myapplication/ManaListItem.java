package com.example.myapplication;

import java.util.HashMap;

public class ManaListItem {

    //CONSTANTS
    //-- Types of manot //TODO: enum
    static final String PITA = "pita";
    static final String LAFA = "lafa";
    static final String HALF_PITA = "half pita";
    static final String HALF_LAFA = "half lafa";
    //-- Price Listing
    private static final int PITA_PRICE = 18;
    private static final int LAFA_PRICE = 22;
    private static final int HALF_PITA_PRICE = 0; //TODO
    private static final int HALF_LAFA_PRICE = 0; //TODO


    //Variables
    private int manaImg;
    private String type;
    private String owner;
    private String status;
    private HashMap<String, Boolean> tosafot;
    private String paymentMethod;
    private String notes;
    private String price; // TODO: Remove this.
    private int realPrice;



    public ManaListItem(String owner, String type, int price, HashMap<String, Boolean> tosafot,
                        String paymentMethod) {
        this.paymentMethod = paymentMethod;
        this.owner = owner;
        this.type = type;
        this.realPrice = price;
        this.status = "open"; // TODO: remove, or at least put as a constant
        this.tosafot = tosafot;
        //TODO: no payment method, no option to add notes.
    }

    public ManaListItem(int manaImg, String type, String price) {
        this.manaImg = manaImg;
        this.type = type;
        this.price = price;
    }

    public String getOwner() {
        return owner;
    }

    public String getStatus() {
        return status;
    }

    public HashMap<String, Boolean> getTosafot() {
        return tosafot;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getNotes() {
        return notes;
    }

    public String getPrice() {
        return price;
    }

    public int getRealPrice() {
        return realPrice;
    }

    public int getManaImg() {
        return manaImg;
    }

    public void setManaImg(int manaImg) {
        this.manaImg = manaImg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
