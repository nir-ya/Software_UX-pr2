package com.example.myapplication;

import androidx.annotation.NonNull;


/**
 * A class representing a Falafel order.
 */
public class Order {

  // Constants
  private static final int MINIMUM = 70;
  private static final int OPEN = 1;
  private static final int LOCKED = 2;
  private static final int DELIVERED = 3;
  private static final int CANCELED = 4;

  // Vars
  private static int status;
  private int price;

  /**
   * A basic Order constructor
   */
  Order(){
    price = 0;
    status = OPEN;
  }

  @NonNull
  @Override
  public String toString() {
    StringBuilder str_repr = new StringBuilder(
        "Status: " + getStatusString()+"\n"
            + "Reached minimum: " + reachedMin());
    if(reachedMin()) {
      str_repr.append(" (").append(extraMoney()).append(" NIS Extra)");
    }else{
      str_repr.append(" (").append(missingMoney()).append(" NIS Missing)");
    }
    str_repr.append("\nManot:");
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
    if(status == OPEN && m.isReadyToOrder()){
      if(m.inOrder() != 0){
        return false;
      }
      price += m.getPrice();
    }
    return false;
  }

  void addMana(String ordered_by, int type, int payment_method){
    if(status == OPEN){
      Mana m = new Mana(ordered_by, type, payment_method);
      price += m.getPrice();
    }
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
}
