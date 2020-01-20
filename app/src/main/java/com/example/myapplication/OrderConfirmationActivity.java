package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;

public class OrderConfirmationActivity extends AppCompatActivity {

    String paymentMethod, manaType, orderId, orderTime;
    HashMap<String, Boolean> tosafot;

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



}
