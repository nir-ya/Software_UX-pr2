package com.example.myapplication;

public class ManaListItem {

    //CONSTANTS
    //-- Types of manot //TODO: enum
    static final String PITA = "pita";
    static final String LAFA = "lafa";
    static final String HALF_PITA = "half pita";
    static final String HALF_LAFA = "half lafa";


    //Variables
    private int manaImg;
    private String type;
    private String price; // TODO: Remove this.


    public ManaListItem(int manaImg, String type, String price) {
        this.manaImg = manaImg;
        this.type = type;
        this.price = price;
    }

    public String getPrice() {
        return price;
    }

    public int getManaImg() {
        return manaImg;
    }

    public String getType() {
        return type;
    }

}
