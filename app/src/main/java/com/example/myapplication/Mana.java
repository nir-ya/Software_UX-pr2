package com.example.myapplication;


import androidx.annotation.NonNull;
import java.util.HashMap;

/**
 * A class representing a Falafel Mana from Shevah.
 * Each Mana is devised from the culinary aspect and logistic aspect.
 * The culinary aspect is the type (namely Pita, Lafa etc), as well as Tosafot.
 * The logistic aspect consists of the order status, the user who ordered the Mana (ordered_by),
 * the payment method, and a serial number,
 */
public class Mana {

  // Constants
  //-- Types of manot
  static final int INVALID = 0;
  static final int PITA = 1;
  static final int LAFA = 2;
  static final int HALF_PITA = 3;
  static final int HALF_LAFA = 4;
  //-- Payment methods
  static final int MEZUMAN = 1;
  static final int CREDIT = 2;
  //-- Order status options
  private static final int OPEN = 1;
  private static final int LOCKED = 2;
  private static final int APP = 3;
  //-- Tosafot
  private static final int TOMATO = 1;
  private static final int CUCUMBER = 2;
  private static final int KRUV = 3;
  private static final int ONION = 4;
  private static final int THINA = 5;
  private static final int HUMUS = 6;
  private static final int HARIF = 7;
  private static final int AMBA = 8;
  //-- Tosafot quantifiers
  private static final int NO = 0;
  private static final int LITTLE = 1;
  private static final int YES = 2;
  private static final int A_LOT = 3;
  //-- Prices
  private static final int PITA_PRICE = 18;
  private static final int LAFA_PRICE = 22;
  private static final int HALF_PITA_PRICE = 0; //TODO
  private static final int HALF_LAFA_PRICE = 0; //TODO

  // Vars
  //-- Culinary related vars
  private int type;
  private int price;
  private int thina, amba, tomato, cucumber, onion, kruv, hamuz, eggplant;
  private HashMap<String, Integer> tosafot = new HashMap<>();
  private String costumer_notes;
  //-- Logistic related vars
  private int status;
  private int in_order;
  private String ordered_by;
  private int payment_method;
  private int serial_no;
  private static int counter = 1;

  /**
   * A basic Mana constructor with only the user that ordered it
   * @param ordered_by the user who ordered the Mana
   */
  Mana(String ordered_by){
    status = OPEN;
    in_order = 0;
    serial_no = counter++;
    this.ordered_by = ordered_by;
  }

  /**
   * A Mana constructor based on a previous mana previously ordered by the same user
   * @param source a Mana object to copy the content of
   */
  Mana(Mana source){
    status = OPEN;
    in_order = 0;
    serial_no = counter++;
    ordered_by = source.ordered_by;
    type = source.type;
    payment_method = source.payment_method;
    tosafot = source.tosafot;
    price = source.price;
  }

  /**
   * A Mana constructor with user, type and payment method
   * @param ordered_by the user who ordered the Mana
   * @param type the type of the Mana (Pita, Lafa etc.)
   * @param payment_method (Mezuman, Credit)
   */
  Mana(String ordered_by, int type, int payment_method){
    status = OPEN;
    in_order = 0;
    serial_no = counter++;
    this.ordered_by = ordered_by;
    this.type = type;
    this.payment_method = payment_method;
    this.price = getPrice();
  }

  @NonNull
  @Override
  public String toString() {
    String mana_string = ordered_by + ": " + getTypeStr(type) + ", "
        + paymentStr(payment_method);
    if(in_order == 0){
      mana_string += ", Ready for ordering: " + isReadyToOrder() + ".";
    } else{
      mana_string += ", Part of order serial number: " + in_order;
    }
    if(costumer_notes != null)
      mana_string += ". NOTE: " + costumer_notes;
    return mana_string + " (Mana serial number " + serial_no + ")";
  }

  // Getters
  int getSerial(){
    return serial_no;
  }

  private int getType(){
    return type;
  }

  /**
   * returns the type of the Mana represented as a String
   * @param type the type of the Mana (int)
   * @return String representation of the type of the Mana
   */
  private static String getTypeStr(int type){
    switch(type){
      case PITA:
        return "Pita";
      case LAFA:
        return "Lafa";
      case HALF_PITA:
        return "Half Pita";
      case HALF_LAFA:
        return "Half Lafa";
      default:
        return "Type not set";
    }
  }

  int getPrice(){
    return priceByType(type);
  }

  /**
   * Gets the payment method represeneted as a String
   * @param payment_method the payment method for the Mana, an int
   * @return a String representation of the payment method associated with the Mana
   */
  private String paymentStr(int payment_method) {
    switch(payment_method){
      case MEZUMAN:
        return "Mezuman";
      case CREDIT:
        return "Credit";
      case APP:
        return "Applicatio";
      default:
        return "ooops";
    }
  }

  /**
   * A static method that stores the information of the prices for each Mana
   * @param type the type of the Mana, as an int
   * @return the price associated with the type, an int
   */
  private static int priceByType(int type){
    switch(type){
      case PITA:
        return PITA_PRICE;
      case LAFA:
        return LAFA_PRICE;
      case HALF_PITA:
        return HALF_PITA_PRICE;
      case HALF_LAFA:
        return HALF_LAFA_PRICE;
      default:
        System.out.println("ooops2");
        return 0;
    }
  }

  // Setters
  boolean setType(int new_type){
    if(status == OPEN){
      type = new_type;
      price = getPrice();
      return true;
    }
    return false;
  }

  boolean setPaymentMethod(int new_method){
    if(status == OPEN){
      payment_method = new_method;
      return true;
    }
    return false;
  }

  boolean addNote(String note){
    if(status == OPEN){
      costumer_notes = note;
      return true;
    }
    return false;
  }

  boolean isReadyToOrder(){
    return (type != INVALID) && (payment_method != INVALID);
  }

  void lock() {
    status = LOCKED;
  }

  void addToOrder(int order_no){
    in_order = order_no;
  }

  int inOrder() {
    return in_order;
  }
}
