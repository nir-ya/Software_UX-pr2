package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ManaActivity extends AppCompatActivity {

    ImageView mainBreadView;
    ImageView secondaryBreadView;
    TextView secondaryBreadText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mana);

        connectToxXML();
    }

    private void connectToxXML() {
        mainBreadView = findViewById(R.id.main_bread);
        secondaryBreadView = findViewById(R.id.secondary_bread);
        secondaryBreadText = findViewById(R.id.secondary_bread_text);
    }

    private void swipeBreadViews(){
        mainBreadView.setImageResource(R.drawable.pita);
        secondaryBreadView.setImageResource(R.drawable.lafa);

    }
}
