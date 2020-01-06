package com.example.myapplication;



public class OrderListItem {
    private final static int MIN_ORDER = 70;
    private String orderTime;
    private int currentPrice;

    public OrderListItem(){
    }

    public OrderListItem(String orderTime, int price){
        this.currentPrice = price;
        this.orderTime = orderTime;
    }

    public int getCurrentPrice() {
        return currentPrice;
    }

    public String getOrderTime() {
        return orderTime;
    }


}
