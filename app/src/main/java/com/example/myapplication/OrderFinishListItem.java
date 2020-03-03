package com.example.myapplication;

public class OrderFinishListItem {
    private String owner;
    private String type;
    private String orderDescription;

    public OrderFinishListItem(String owner, String type, String orderDescription) {
        this.owner = owner;
        this.type = type;
        this.orderDescription = orderDescription;
    }

    public OrderFinishListItem() {

    }

    public String getOwner() {
        return owner;
    }

    public String getType() {
        return type;
    }

    public String getOrderDescription() {
        return orderDescription;
    }
}
