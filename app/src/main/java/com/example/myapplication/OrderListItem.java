package com.example.myapplication;


import com.google.firebase.firestore.DocumentReference;
import java.util.Calendar;


public class OrderListItem {
    static final String LOCKED = "locked";
    static final String OPEN = "open";

    private final static int MIN_ORDER = 70;
    private int price;
    private String status;
    private DocumentReference ordRef;
    private int hour;
    private int minute;

    public OrderListItem() {
    }

    public OrderListItem(DocumentReference ref, Calendar cal){
        ordRef = ref;
        price = 0;
        status = "open";
        hour = cal.get(Calendar.HOUR_OF_DAY);
        minute = cal.get(Calendar.MINUTE);
    }

    public OrderListItem(int mPrice, String mStatus) {
        this.price = mPrice;
        this.status = mStatus;
    }

    public String getStatus() {
        return status.toLowerCase();
    }

    public int getPrice() {
        return price;
    }

    public DocumentReference getRef(){
        return ordRef;
    }

    public int getHour(){
        return hour;
    }

    public int getMinute(){
        return minute;
    }

    public String displayTime(){
        return getHour() + ":" + getMinute();
    }
}
