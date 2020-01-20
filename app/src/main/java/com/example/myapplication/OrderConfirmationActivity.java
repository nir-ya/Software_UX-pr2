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



    void addToDB(){
        CollectionReference manotCollectionReference = db.collection(Constants.ORDERS).
            document(orderId).collection(Constants.MANOT_SUBCOLLECTION);
        Mana newMana = new Mana(manaType, "", 0, tosafot,"Avnush");

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
