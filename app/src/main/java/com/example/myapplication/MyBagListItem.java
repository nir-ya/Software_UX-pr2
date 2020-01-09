package com.example.myapplication;

public class MyBagListItem {

    private String manaType;
    private String manaPrice;
    private String tosafut;

    public MyBagListItem(String manaType, String manaPrice, String tosafut) {
        this.manaType = manaType;
        this.manaPrice = manaPrice;
        this.tosafut = tosafut;
    }

    public String getManaType() {
        return manaType;
    }

    public String getManaPrice() {
        return manaPrice;
    }

    public String getTosafut() {
        return tosafut;
    }

    public void setManaType(String manaType) {
        this.manaType = manaType;
    }

    public void setManaPrice(String manaPrice) {
        this.manaPrice = manaPrice;
    }

    public void setTosafut(String tosafut) {
        this.tosafut = tosafut;
    }
}
