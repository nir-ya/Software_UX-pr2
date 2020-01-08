package com.example.myapplication;


public class Manot {
    private String name;
    private int price;



    public Manot() {
    }

    public Manot(String mName, int mPrice) {
        this.price = mPrice;
        this.name = mName;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

}
