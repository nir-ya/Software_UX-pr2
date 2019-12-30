package com.example.myapplication;


import android.util.Log;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;


/**
 * A class representing a Falafel Mana from Shevah.
 * Each Mana is devised from the culinary aspect and logistic aspect.
 * The culinary aspect is the type (namely Pita, Lafa etc), as well as Tosafot.
 * The logistic aspect consists of the order status, the user who ordered the Mana (ordered_by),
 * the payment method, and a serial number,
 */
public class Mana{

  // Constants
  static final String SHEVAH = "Shevah";
  //-- Types of manot
  static final String INVALID = "Not specified";
  static final String PITA = "Pita";
  static final String LAFA = "Lafa";
  static final String HALF_PITA = "Half Pita";
  static final String HALF_LAFA = "Half Lafa";
  //-- Payment methods
  static final String MEZUMAN = "Mezuman";
  static final String CREDIT = "Credit";
  static final String APP = "App";
  //-- Order status options
  private static final String OPEN = "Open";
  private static final String LOCKED = "Locked";
  //-- Tosafot
  private static final String TOMATO = "Tomato";
  private static final String CUCUMBER = "Cucumber";
  private static final String KRUV = "kruv";
  private static final String ONION = "Onion";
  private static final String THINA = "Thina";
  private static final String HUMUS = "Hummus";
  private static final String HARIF = "Harif";
  private static final String AMBA = "Amba";
  //-- Tosafot quantifiers
  private static final String NO = "None";
  private static final String LITTLE = "Little";
  private static final String YES = "Yes";
  private static final String A_LOT = "A lot";
  //-- Prices
  private static final int PITA_PRICE = 18;
  private static final int LAFA_PRICE = 22;
  private static final int HALF_PITA_PRICE = 0; //TODO
  private static final int HALF_LAFA_PRICE = 0; //TODO

  // Vars
  private final FirebaseFirestore db = FirebaseFirestore.getInstance();
  //-- Logistic related vars
  private String status;
  private String part_of_order = "None.";
  private String ordered_by;
  private String payment_method;
  private String serial_no;
  //-- Culinary related vars
  private String type;
  private int price;
  private HashMap<String, Integer> tosafot = new HashMap<>();
  private String costumer_notes;


  /**
   * A public no args constructor for FireStore
   */
  Mana(){
    // Public no args constructor necessary for FireStore
  }

  /**
   * A basic Mana constructor with only the user that ordered it
   * @param ordered_by the user who ordered the Mana
   */
  Mana(String ordered_by){
    status = OPEN;
    part_of_order = NO;
    this.ordered_by = ordered_by;
  }

  /**
   * A Mana constructor based on a previous mana previously ordered by the same user
   * @param source a Mana object to copy the content of
   */
  Mana(Mana source){
    status = OPEN;
    part_of_order = NO;
    ordered_by = source.ordered_by;
    type = source.type;
    payment_method = source.payment_method;
    tosafot = source.tosafot;
    price = source.price;
    serial_no = source.serial_no;
    costumer_notes = source.costumer_notes;
  }

  /**
   * A Mana constructor with user, type and payment method
   * @param ordered_by the user who ordered the Mana
   * @param type the type of the Mana (Pita, Lafa etc.)
   * @param payment_method (Mezuman, Credit)
   */
  Mana(String ordered_by, String type, String payment_method){
    status = OPEN;
    part_of_order = NO;
    this.ordered_by = ordered_by;
    this.type = type;
    this.payment_method = payment_method;
    this.price = getPrice();
  }

  public String getStatus() {
    return status;
  }

  private String getType(){
    return type;
  }

  public String getPart_of_order() {
    return part_of_order;
  }

  public String getOrdered_by() {
    return ordered_by;
  }

  public String getPayment_method() {
    return payment_method;
  }

  public String getSerial_no() {
    return serial_no;
  }

  public HashMap<String, Integer> getTosafot() {
    return tosafot;
  }

  public String getCostumer_notes() {
    return costumer_notes;
  }

  @NonNull
  @Override
  public String toString() {
    String mana_string = ordered_by + ": " + type + ", " + payment_method;
    if(!part_of_order.equals(NO)){
      mana_string += ", Ready for ordering: " + isReadyToOrder() + ".";
    } else{
      mana_string += ", Part of order serial number: " + part_of_order;
    }
    if(costumer_notes != null)
      mana_string += ". NOTE: " + costumer_notes;
    return mana_string + " (Mana serial number " + serial_no + ")";
  }

  int getPrice(){
    return priceByType(type);
  }

  /**
   * A static method that stores the information of the prices for each Mana
   * @param type the type of the Mana, as an int
   * @return the price associated with the type, an int
   */
  private static int priceByType(String type){
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
  void setSerial(String serial){
    this.serial_no = serial;
  }

  boolean setType(String new_type){
    if(status.equals(OPEN)){
      type = new_type;
      price = getPrice();
      return true;
    }
    return false;
  }

  boolean setPaymentMethod(String new_method){
    if(status.equals(OPEN)){
      payment_method = new_method;
      return true;
    }
    return false;
  }

  boolean addNote(String note){
    if(status.equals(OPEN)){
      costumer_notes = note;
      return true;
    }
    return false;
  }

  boolean isReadyToOrder(){
    return (!type.equals(INVALID)) && (!payment_method.equals(INVALID));
  }

  void lock() {
    status = LOCKED;
  }

  void addToOrder(String order_no){
    part_of_order = order_no;
  }

  void saveToDB(FirebaseFirestore db){
    db.collection("ManaTest")
        .add(this)
        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
          @Override
          public void onSuccess(DocumentReference documentReference) {
            Log.d(SHEVAH, "DocumentSnapshot added with ID: " + documentReference.getId());
            serial_no = documentReference.getId();
          }
        })
        .addOnFailureListener(new OnFailureListener() {
          @Override
          public void onFailure(@NonNull Exception e) {
            Log.w(SHEVAH, "Error adding document", e);
          }
        });
  }
}
