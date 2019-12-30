package com.example.myapplication;

import android.util.Log;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A class representing a Falafel order.
 */
public class Order{

  // Constants
  private final FirebaseFirestore db = FirebaseFirestore.getInstance();
  private static final int MINIMUM = 70;
  private static final String OPEN = "Open";
  private static final String LOCKED = "Locked";
  private static final String DELIVERED = "Delivered";
  private static final String CANCELED = "Canceled";
  private static final String ORDER_COLLECTION = "OrderCollection";

  // Vars
  private String status;
  private String serial_no;
  private ArrayList<Mana> manot = new ArrayList<>();
  private int price;


  /**
   * A basic Order constructor
   */
  Order(){
    price = 0;
    status = OPEN;
    DocumentReference ordRef = db.collection(ORDER_COLLECTION).document();
    ordRef.set(this);
    serial_no = ordRef.getId();
  }

  public String getStatus() {
    return status;
  }

  public String getSerial_no() {
    return serial_no;
  }

  public int getPrice() {
    return price;
  }

  @NonNull
  @Override
  public String toString() {
    StringBuilder str_repr = new StringBuilder(
        "Order #"+serial_no+", Status: " + getStatusString()+"\n"
            + "Reached minimum: " + reachedMin());
    if(reachedMin()) {
      str_repr.append(" (").append(extraMoney()).append(" NIS Extra)");
    }else{
      str_repr.append(" (").append(missingMoney()).append(" NIS Missing)");
    }
    str_repr.append("\nManot:");
    int counter = 1;
    for(Mana m : manot){
      str_repr.append("\n\t").append(counter++).append(". ").append(m.toString());
    }
    return str_repr.toString() + "\n";
  }

  /**
   * returns a string representing the status of the order
   * @return the status as a string
   */
  private String getStatusString() { //TODO: switch-case
    switch(status){
      case OPEN:
        return "Open";
      case LOCKED:
        return "Locked";
      case DELIVERED:
        return "Delivered";
      case CANCELED:
        return "Canceled";
      default:
        return "ooops1";
    }
  }

  /**
   * Adds a Mana to the order, as long as the order is open and the Mana isn't in the order yet.
   * @param m The mana to add to the Order
   * @return true iff the Mana was added to the order
   */
  boolean addMana(Mana m){
    if(status.equals(OPEN ) && m.isReadyToOrder()){
      if(m.getPart_of_order().equals(Mana.INVALID)){
        return false;
      }
      price += m.getPrice();

      //TODO this should be a transaction
      DocumentReference ordRef = db.collection(ORDER_COLLECTION).document(serial_no);
      ordRef
          .update("price", price)
          .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
              Log.d(Mana.SHEVAH, "DocumentSnapshot successfully updated!");
            }
          })
          .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
              Log.w(Mana.SHEVAH, "Error updating document", e);
            }
          });

      m.addToOrder(serial_no);
      addManaToDB(m);
      return manot.add(m);
    }
    return false;
  }

  private void addManaToDB(final Mana m) {
    System.out.println("---"+serial_no);

    db.collection("OrderCollection").document("/"+serial_no).collection("Manot")
        .add(m)
        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
          @Override
          public void onSuccess(DocumentReference documentReference) {
            Log.d(Mana.SHEVAH, "*DocumentSnapshot added with ID: " + documentReference.getId());
            m.setSerial(documentReference.getId());
          }
        })
        .addOnFailureListener(new OnFailureListener() {
          @Override
          public void onFailure(@NonNull Exception e) {
            Log.w(Mana.SHEVAH, "Error adding document", e);
          }
        });
  }

  boolean addMana(String ordered_by, String type, String payment_method){
    if(status.equals(OPEN)){
      Mana m = new Mana(ordered_by, type, payment_method);
      price += m.getPrice();
      m.addToOrder(serial_no);
      return manot.add(m);
    }
    return false;
  }

  List<Mana> getManot(){
    return manot;
  }

  boolean removeMana(Mana m){
    if(status.equals(OPEN)){
      for(Mana mana : manot){
        if(mana == m){
          price -= mana.getPrice();
          manot.remove(mana);
          return true;
        }
      }
    }
    return false;
  }

  boolean reachedMin(){
    return missingMoney() == 0;
  }

  int missingMoney(){
    if(MINIMUM > price){
      return MINIMUM - price;
    }
    return 0;
  }

  int extraMoney(){
    if(reachedMin()){
      return price - MINIMUM;
    }
    return missingMoney() * (-1);
  }

  void lock() {
    for(Mana m : manot){
      m.lock();
    }
    status = LOCKED;
  }

  String getSerial() {
    return serial_no;
  }

  void setSerial(String st){
    serial_no = st;
  }

  void saveToDB(FirebaseFirestore db){
    db.collection("OrderTest")
        .add(this)
        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
          @Override
          public void onSuccess(DocumentReference documentReference) {
            Log.d(Mana.SHEVAH, "DocumentSnapshot added with ID: " + documentReference.getId());
            serial_no = documentReference.getId();
          }
        })
        .addOnFailureListener(new OnFailureListener() {
          @Override
          public void onFailure(@NonNull Exception e) {
            Log.w(Mana.SHEVAH, "Error adding document", e);
          }
        });

    Log.d(Mana.SHEVAH, "---"+db.collection("OrderTest").document("/"+serial_no));
    db.collection("OrderTest").document("/"+serial_no).collection("PPP")
        .add(manot)
        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
          @Override
          public void onSuccess(DocumentReference documentReference) {
            Log.d(Mana.SHEVAH, "DocumentSnapshot added with ID: " + documentReference.getId());
          }
        })
        .addOnFailureListener(new OnFailureListener() {
          @Override
          public void onFailure(@NonNull Exception e) {
            Log.w(Mana.SHEVAH, "Error adding document", e);
          }
        });
  }
}
