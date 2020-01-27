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
    private HashMap<String, Boolean> tosafot;
    private int paymentMethod;
    private String notes;
    private int price;



    public ManaListItem(){ }

    public ManaListItem(String owner, String type, int price, HashMap<String, Boolean> tosafot,
                        int paymentMethod) {
        this.paymentMethod = paymentMethod;
        this.owner = owner;
        this.type = type;
        this.price = price;
        this.status = "open"; // TODO: remove, or at least put as a constant
        this.tosafot = tosafot;
        //TODO: no payment method, no option to add notes.
    }

    private String price; // TODO: Remove this.



    public ManaListItem(int manaImg, String type, int price) {
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

    public int getPaymentMethod() {
        return paymentMethod;
    }

    public String getNotes() {
        return notes;
    }

    public int getPrice() {

        return price;
    }

    public int getManaImg() {
        return manaImg;
    }

    public String getType() {
        return type;
    }


    public void setType(String type) {
        this.type = type;
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
