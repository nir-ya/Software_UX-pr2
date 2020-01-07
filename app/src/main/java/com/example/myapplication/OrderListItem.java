package com.example.myapplication;



public class OrderListItem {
    private final static int MIN_ORDER = 70;
    private String serial;
    private int price;

    public OrderListItem(){
    }

    public OrderListItem(String serial, int price){
        this.price = price;
        this.serial = serial;
    }

    public int getPrice() {
        return price;
    }

    public String getSerial() {
        return serial;
    }


}
