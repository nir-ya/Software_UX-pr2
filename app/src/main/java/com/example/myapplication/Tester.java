package com.example.myapplication;

import java.util.ArrayList;
import java.util.List;

/**
 * A tester class to the Order and Mana classes
 */
public class Tester {

  /**
   * main driver of tests
   * @param args the arguments passed, at this point does not affect the tests
   */
  public static void main(String[] args){ //TODO: Give tester function informative names
    test1();
    test2();
    test3();
    test4();
    test5();
  }

  /**
   * Create an order
   * Create a mana
   * add the mana to the order
   * try to add the mana to the order again
   */
  private static void test5() {
    System.out.println("---Test 5 Initiated---\n");

    // Part I: Creating a Mana and adding the mana to the order
    Order order = new Order();
    Mana mana = new Mana("hungryCSstud", Mana.PITA, Mana.CREDIT);
    System.out.println("Part I: Creating an order and a Mana, addint the mana to the order using "
        + "addMana() returns: ");
    System.out.println(order.addMana(mana)+"\n");

    // Part II: Trying to add the same mana to the same order again
    System.out.println("Part II: Trying to add the same mana to the same order:");
    System.out.println(order.addMana(mana)+"\n");

    // Part III: Creating a new order and trying to add the same mana to the new order
    Order new_order = new Order();
    System.out.println("Part III: Creating a new order "
        + "and trying to add the same mana to the new order");
    System.out.println(new_order.addMana(mana));
  }

  /**
   * Create an order
   * Create enough orders to pass the minimum price
   * Lock the order ("send it to shevah")
   * move the order to an archive
   */
  private static void test4() {
    System.out.println("---Test 4 Initiated---\n");

    // Part I: Creating an order, filling it with Manot until reaching the
    // minimum, locking the order, and printing it.

    // Simulating initial status
    Order open_order;
    ArrayList<Order> orders_archive = new ArrayList<>();

    // Simulating the formation of an order
    open_order = new Order();
    while(!open_order.reachedMin()){
      Mana another_mana = new Mana("hungrystudent11");
      another_mana.setType(Mana.PITA);
      another_mana.setPaymentMethod(Mana.MEZUMAN);
      open_order.addMana(another_mana);
    }
    System.out.println("Part I: Creating an order, filling it with Manot until reaching the minimum:");
    System.out.println("The order has reached the minimum!");

    // Part II: Locking the order, and printing it.
    // Simulating the termination of an order
    open_order.lock();
    orders_archive.add(open_order);
    open_order = null;
    System.out.println("locking the order and printing it:\n");
    System.out.println(orders_archive+"\n");
  }

  /**
   * Creating an order,
   * creating a mana from basic constructor,
   * trying to add a partial mana to the order,
   * completing the mana,
   * adding the mana to the order
   */
  private static void test3() {
    System.out.println("---Test 3 Initiated---\n");

    // Part I: Creating a Mana only with type
    Order order = new Order();
    Mana mana1 = new Mana("Eyal");
    mana1.setType(Mana.PITA);
    System.out.println("Part I: Creating a Mana only with type");
    System.out.println(mana1+"\n");

    // Part II: Trying to add such a Mana to an Order, showing addMana() return value:
    System.out.println("Part II: Trying to add such a Mana to an Order, "
        + "showing addMana() return value");
    System.out.println(order.addMana(mana1)+"\n");

    // Part III: Adding to the mana all information required for ordering
    mana1.setPaymentMethod(Mana.CREDIT);
    System.out.println("Part III: Adding to the mana all information required for ordering");
    System.out.println(mana1+"\n");

    // Part IV: Trying to add such a Mana to an Order, showing addMana() return value:
    System.out.println("Part IV: Trying to add such a Mana to an Order, "
        + "showing addMana() return value");
    System.out.println(order.addMana(mana1)+"\n");

    // Part V: Now showing the order containing the Mana
    System.out.println("Part V: Now showing the order containing the Mana");
    System.out.println(order);
  }

  /**
   * Creating an order,
   * Creating mana from basic constructor and adding details lie expected from the flow
   */
  private static void test2() {
    System.out.println("---Test 2 Initiated---\n");

    // Part I: An empty Mana
    Mana mana1 = new Mana("Nir");
    System.out.println("Part I: An empty Mana");
    System.out.println(mana1+"\n");

    // Part II: A mana with type
    mana1.setType(Mana.PITA);
    System.out.println("Part II: A mana with type");
    System.out.println(mana1+"\n");

    // Part III: a mana with everything required
    mana1.setPaymentMethod(Mana.MEZUMAN);
    System.out.println("Part III: a mana with everything required");
    System.out.println(mana1+"\n");

    // Part IV: a mana with a comment from the user
    mana1.addNote("Thina ba tzad");
    System.out.println("Part IV: a mana with a comment from the user");
    System.out.println(mana1+"\n");
  }

  /**
   * Creating an order,
   * adding Manot directly,
   * removing Manot
   */
  private static void test1() {
    System.out.println("---Test 1 Initiated---\n");

    // Part I: print a non complete order
    Order order = new Order();
    order.addMana("Nir", Mana.PITA, Mana.MEZUMAN);
    order.addMana("Liav", Mana.PITA, Mana.CREDIT);
    order.addMana("Eyal", Mana.LAFA, Mana.MEZUMAN);
    System.out.println("Part I: print a non complete order");
    System.out.println(order);

    // Part II: print the order after removing Manot
    List<Mana> manot = order.getManot();
    Mana m = manot.get(2);
    order.removeMana(m);
    System.out.println("Part II: print the order after removing Manot");
    System.out.println(order);

    // Part III: print the complete order
    order.addMana("Yoni", Mana.HALF_PITA, Mana.CREDIT);
    order.addMana("Ran", Mana.LAFA, Mana.CREDIT);
    order.addMana("Eyal", Mana.PITA, Mana.MEZUMAN);
    System.out.println("Part III: print the complete order");
    System.out.println(order);
  }

}
