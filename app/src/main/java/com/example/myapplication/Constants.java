package com.example.myapplication;

import java.util.HashMap;

class Constants {

    static final String ORDERS = "OpenOrders";
    static final String MANOT_SUBCOLLECTION = "Manot";
    static final String MONEY_MADE = "נאספו %s מתוך 70 ש\"ח";
    static final String LOCKED_TEXT = "הזמן!";
    static final String JOIN_TEXT = "הצטרף!";
    static final String READY_TEXT = "מוכן לשילוח";
    static final String ORDERED_TEXT = "ההזמנה בהכנה";
    static final String ORDER_OUT = "שעת ההזמנה הגיעה. מישהו צריך לקחת יוזמה";
    static final String ORDER_CANCELED = "שכחו מזה, לא הגענו למינימום";
    static final String WAITING = "מחכה למשבחים...";

    //Tosafot
    static final String NO_TOSAFOT = "בלי תוספות";
    static final String HUMMUS = "Hummus";
    static final String HARIF = "Harif";
    static final String THINA = "Thina";
    static final String AMBA = "Amba";
    static final String TOMATO = "Tomato";
    static final String CUCUMBER = "Cucumber";
    static final String ONION = "Onion";
    static final String KRUV = "Kruv";
    static final String PICKELS = "Pickels";
    static final String CHIPS = "Chips";
    static final String EGGPLAT = "Eggplant";


    static final int[] SAD_SOUNDS = new int[]{R.raw.basa_whitney, R.raw.basa_sound,
            R.raw.sad_trombone, R.raw.letitgoo, R.raw.believefly_basa, R.raw.titanic};

    static HashMap<String, String> hebrewExtras = setHebrewExtrasMap();

    private static HashMap<String,String> setHebrewExtrasMap() {
        hebrewExtras = new HashMap<>();
        hebrewExtras.put("Amba", "עמבה");
        hebrewExtras.put("Chips", "צ'יפס");
        hebrewExtras.put("Cucumber", "מלפפון");
        hebrewExtras.put("Eggplant", "חצילים");
        hebrewExtras.put("Harif", "חריף");
        hebrewExtras.put("Hummus", "חומוס");
        hebrewExtras.put("Kruv", "כרוב");
        hebrewExtras.put("Onion", "בצל");
        hebrewExtras.put("Pickels", "חמוצים");
        hebrewExtras.put("Thina", "טחינה");
        hebrewExtras.put("Tomato", "עגבניה");
        return hebrewExtras;
    }
}
