package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.GridLayout;
import android.widget.ImageView;

public class ManaActivity extends AppCompatActivity {

    ImageView pitaView;
    ImageView lafaView;
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
        pitaView = findViewById(R.id.pita_image);
        lafaView = findViewById(R.id.lafa_image);

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

        gridView = findViewById(R.id.grid_view);

    }


}
