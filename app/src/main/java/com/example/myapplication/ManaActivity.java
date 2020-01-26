package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

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
    ImageView manaTypeImageVIew;

    CheckBox[] checkBoxes;

    TextView ownerText, dishDescription;

    String manatype;
    String orderTime;
    String orderId;
    Timestamp time;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mana);

        connectToxXML();

        isAllMarkedListener();

        manatype = getIntent().getStringExtra("mana_type");
        orderTime = getIntent().getStringExtra("order_time");
        orderId = getIntent().getStringExtra("order_id");
        time =  getIntent().getParcelableExtra("CALENDAR");

        updateTextViews();
    }


    private void updateTextViews() {
        ownerText.setText(getString(R.string.owner_text, user.getDisplayName()));

        switch (manatype) {
            case ManaListItem.HALF_PITA:
                dishDescription.setText(R.string.half_pita_description);
                manaTypeImageVIew.setImageResource(R.drawable.half_pita_full);
                break;
            case ManaListItem.PITA:
                dishDescription.setText(R.string.pita_description);
                manaTypeImageVIew.setImageResource(R.drawable.pita_full);

                break;
            case ManaListItem.LAFA:
                dishDescription.setText(R.string.lafa_description);
                manaTypeImageVIew.setImageResource(R.drawable.lafa_full);

                break;
            case ManaListItem.HALF_LAFA:
                dishDescription.setText(R.string.half_lafa_description);
                manaTypeImageVIew.setImageResource(R.drawable.half_lafa_full);

                break;
        }
    }

    private void setTosafot(HashMap tosafot) {
        tosafot.put(Constants.HUMMUS, humusView.isChecked());
        tosafot.put(Constants.THINA, tahiniView.isChecked());
        tosafot.put(Constants.HARIF, harifView.isChecked());
        tosafot.put(Constants.AMBA, ambaView.isChecked());
        tosafot.put(Constants.TOMATO, tomatoView.isChecked());
        tosafot.put(Constants.CUCUMBER, cucumberView.isChecked());
        tosafot.put(Constants.ONION, onionView.isChecked());
        tosafot.put(Constants.PICKELS, picklesView.isChecked());
        tosafot.put(Constants.CHIPS, chipsView.isChecked());
        tosafot.put(Constants.EGGPLAT, eggplantView.isChecked());
        tosafot.put(Constants.KRUV, false);
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


    private void connectToxXML() {
        ownerText = findViewById(R.id.owner_text);
        dishDescription = findViewById(R.id.dish_description);

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

        manaTypeImageVIew = findViewById(R.id.mana_type_picture);

        manaTypeImageVIew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        });
    }


    public void moveToConfirm(View view) {

        HashMap<String, Boolean> tosafot = new HashMap<>();
        setTosafot(tosafot);
        Intent intent = new Intent(getApplicationContext(), OrderConfirmationActivity.class);
        intent.putExtra("tosafot", tosafot);
        intent.putExtra("order_id", orderId);
        intent.putExtra("order_time", orderTime);
        intent.putExtra("CALENDAR", time);
        startActivity(intent);
    }

    public void cancelOrder(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
