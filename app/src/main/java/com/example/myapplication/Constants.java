package com.example.myapplication;

import java.util.HashMap;

class Constants {

    public static final int HALF_PITA_POSITION = 0;
    public static final int PITA_POSITION = 1;
    public static final int LAFA_POSITION = 2;
    public static final int HALF_LAFA_POSITION = 3;
    static final long SHORT_DELAY = 2000;
    static final String ORDERS = "OpenOrders";
    static final String MANOT_SUBCOLLECTION = "Manot";
    static final String MONEY_MADE = "הכסף שנצבר: %s מתוך 70 שקלים";
    static final String LOCKED_TEXT = "הזמן!";
    static final String JOIN_TEXT = "הצטרף!";
    static final String READY_TEXT = "מוכן לשילוח";
    public static final String ORDERED_TEXT = "ההזמנה בהכנה";
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
    static final long LONG_DELAY = 3000;

    //TOOLTIP
    static final String REC_USAGE_ID = "rec_intro";
    static final String FAB_USAGE_ID = "fab_intro";
    static final String BAG_USAGE_ID = "bag_intro";


    static HashMap<String, String> hebrewExtras = setHebrewExtrasMap();

    static HashMap<String,String> setHebrewExtrasMap() {
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
