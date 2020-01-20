package com.example.myapplication;

import static android.provider.MediaStore.MediaColumns.DOCUMENT_ID;

import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.Calendar;
import java.util.HashMap;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class OrderConfirmationActivity extends AppCompatActivity {

  String manaType, orderId, orderTime;
  int paymentMethod;
  HashMap<String, Boolean> tosafot;
  Calendar cal;
  FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

  private FirebaseFirestore db = FirebaseFirestore.getInstance();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_order_confirmation);

    TextView orderDetails = findViewById(R.id.order_details_text);

    Bundle extras = getIntent().getExtras();

    orderDetails.setText("הזמנה לשעה " + extras.getString("order_time"));



    manaType = extras.getString("mana_type");
    orderId = extras.getString("order_id");
    orderTime = extras.getString("order_time");
    cal = (Calendar) extras.getSerializable("CALENDAR");

    tosafot = (HashMap<String, Boolean>) extras.getSerializable("tosafot");
  }

  public void setCreditPayment(View view) {
    paymentMethod = 1;
  }


  public void setCashPayment(View view) {
    paymentMethod = 0;
  }


  void addToDB() {
    final CollectionReference ordersCollection = db.collection(Constants.ORDERS);
    DocumentReference orderRef = ordersCollection.document(orderId);


    orderRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
      @Override
      public void onComplete(@NonNull Task<DocumentSnapshot> task) {
        if (task.isSuccessful()) {
          DocumentSnapshot document = task.getResult();
          if (document.exists()) {
            addManaToDB(ordersCollection);
          } else {
            OrderListItem order = new OrderListItem(cal);
            DocumentReference d = ordersCollection.document(orderId);
            d.set(order);

            addManaToDB(ordersCollection);
          }
        } else {

        }
      }
    });

//    addManaToDB(ordersCollection);

  }

  private void addManaToDB(CollectionReference ordersCollection) {
    CollectionReference manotCollectionReference = ordersCollection.document(orderId)
        .collection(Constants.MANOT_SUBCOLLECTION);
    Mana newMana = new Mana(manaType, "", paymentMethod, tosafot, user.getDisplayName());
    manotCollectionReference.add(newMana);
  }

  public void confirmOrder(View view) {
    addToDB();
    finish();
  }

//    private void popToast(boolean success) {
//        int msgId = R.string.new_order_success;
//        if (success == false) {
//            msgId = R.string.new_order_fail;
//        }
//        Toast.makeText(MainActivity.this, getResources().getString(msgId), Toast.LENGTH_LONG).show();
//    }
}
