package com.example.myapplication;

import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class OrderConfirmationActivity extends AppCompatActivity {

    String paymentMethod, manaType, orderId, orderTime;
    HashMap<String, Boolean> tosafot;

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

        tosafot = (HashMap<String, Boolean>) extras.getSerializable("tosafot");
    }

    public void setCreditPayment(View view) {
        paymentMethod = "Credit";
    }


    public void setCashPayment(View view) {
        paymentMethod = "Cash";
    }




    void addToDB(String orderId){
        CollectionReference manotCollectionReference = db.collection(Constants.ORDERS).
            document(orderId).collection(Constants.MANOT_SUBCOLLECTION);
        int price = Mana.priceByType(type);
        ManaListItem newMana = new Mana("USER", type, tosafot);

//        manotCollectionReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                OrderListItem order = documentSnapshot.toObject(OrderListItem.class);
//                if (order != null)
//                {
//                    textHour.setText(order.displayTime());
//                }
//            }
//        });

//
//        manotCollectionReference.add(newMana).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
////                popToast(true);
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
////                popToast(false);
//            }
//        });
    }

//    private void popToast(boolean success) {
//        int msgId = R.string.new_order_success;
//        if (success == false) {
//            msgId = R.string.new_order_fail;
//        }
//        Toast.makeText(MainActivity.this, getResources().getString(msgId), Toast.LENGTH_LONG).show();
//    }
}
