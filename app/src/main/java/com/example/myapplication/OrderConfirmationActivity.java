package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class OrderConfirmationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);

        TextView orderDetails = findViewById(R.id.order_details_text);

        Bundle extras = getIntent().getExtras();

        orderDetails.setText("הזמנה לשעה " + extras.getString("order_time"));
    }
}
