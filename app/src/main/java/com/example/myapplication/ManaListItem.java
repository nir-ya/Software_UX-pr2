package com.example.myapplication;

public class ManaListItem {

    private int manaImg;
    private String manaType;
    private String manaPrice;



    public ManaListItem(int manaImg, String manaType, String manaPrice) {
        this.manaImg = manaImg;
        this.manaType = manaType;
        this.manaPrice = manaPrice;
    }

    public int getManaImg() {
        return manaImg;
    }

    public void setManaImg(int manaImg) {
        this.manaImg = manaImg;
    }

    public String getManaType() {
        return manaType;
    }

    public void setManaType(String manaType) {
        this.manaType = manaType;
    }

    public String getManaPrice() {
        return manaPrice;
    }

    public void setManaPrice(String manaPrice) {
        this.manaPrice = manaPrice;
    }
}
