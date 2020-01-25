package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class OrderFinishActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_finish);
    }

    public void dialShevach(View view) {
        Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:026528317"));
        startActivity(dialIntent);
    }
}
