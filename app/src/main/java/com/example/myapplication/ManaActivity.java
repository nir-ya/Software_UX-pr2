package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.CheckBox;
import android.widget.GridLayout;
import android.widget.ImageView;

public class ManaActivity extends AppCompatActivity {

    CheckBox humusView;
    CheckBox harifView;
    CheckBox picklesView;
    CheckBox onionView;
    CheckBox tomatoView;
    CheckBox cucumberView;
    CheckBox ambaView;
    CheckBox tahiniView;
    CheckBox chipsView;
    CheckBox eggplantView;
    GridLayout gridView;
    CheckBox markAll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mana);

        connectToxXML();

    }

    private void connectToxXML() {

        humusView = findViewById(R.id.humus_image);
        harifView = findViewById(R.id.harif_image);
        picklesView = findViewById(R.id.pickles_image);
        onionView = findViewById(R.id.onion_image);
        tomatoView = findViewById(R.id.tomato_image);
        cucumberView = findViewById(R.id.cucumber_image);
        ambaView = findViewById(R.id.amba_image);
        tahiniView = findViewById(R.id.tahini_image);
        chipsView = findViewById(R.id.chips_image);
        eggplantView = findViewById(R.id.eggplant_image);

    }


}
