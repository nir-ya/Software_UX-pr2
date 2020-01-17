package com.example.myapplication;


import android.content.res.Resources;

import java.util.ArrayList;
import java.util.Collection;

public class OrderListItem {
    static final String LOCKED = "locked";
    static final String OPEN = "open";

    private final static int MIN_ORDER = 70;
    private String serial;
    private int price;
    private String status;

    public OrderListItem() {
    }

    public OrderListItem(String mSerial, int mPrice, String mStatus) {
        this.price = mPrice;
        this.serial = mSerial;
        this.status = mStatus;
    }

    public int getPrice() {
        return price;
    }

    public String getSerial() {
        return serial;
    }

    public String getStatus() {
        return status.toLowerCase();
    }

}
