package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.GridLayout;
import android.widget.ImageView;

public class ManaActivity extends AppCompatActivity {

    ImageView humusView;
    ImageView harifView;
    ImageView picklesView;
    ImageView onionView;
    ImageView tomatoView;
    ImageView cucumberView;
    ImageView ambaView;
    ImageView tahiniView;
    ImageView chipsView;
    ImageView eggplantView;
    GridLayout gridView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mana);

        connectToxXML();

    }

    private void connectToxXML() {

    }


}
