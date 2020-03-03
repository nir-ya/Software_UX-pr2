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

    private String owner;
    private String status;
    private int paymentMethod;
    private String notes;
    private String price;


    public ManaListItem() {
    }


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
}
