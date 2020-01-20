package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;

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

    CheckBox[] checkBoxes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mana);

        connectToxXML();


        isAllMarkedListener();


    }

    private void isAllMarkedListener() {
        markAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    for (CheckBox currentCheckBox : checkBoxes) {
                        currentCheckBox.setChecked(true);
                    }
                }
            }
        });
        for (CheckBox currentCheckBox : checkBoxes) {
            currentCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (!isChecked) {
                        markAll.setChecked(false);
                    }
                }
            });
        }
    }


        private void connectToxXML () {

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

            checkBoxes = new CheckBox[]{humusView, harifView, picklesView,
                    onionView, tomatoView, cucumberView,
                    ambaView, tahiniView, chipsView, eggplantView};

            markAll = findViewById(R.id.mark_all_checkbox);

        }


    }
