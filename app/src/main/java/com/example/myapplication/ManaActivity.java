package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
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

    CheckBox[] checkBoxes;

    String orderId;
    Calendar cal;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mana);

        connectToxXML();


        isAllMarkedListener();

        orderId = getIntent().getStringExtra("ref");
        cal = (Calendar) getIntent().getSerializableExtra("CALENDAR");
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


    public void moveToConfirm(View view) {
        DocumentReference orderRef = db.collection(Constants.ORDERS).document(orderId);
        orderRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                OrderListItem order = documentSnapshot.toObject(OrderListItem.class);
                if (order != null)
                {
                    String orderTime = Randomizer.formatter.format(order.getTimestamp().toDate());

                    HashMap<String, Boolean> tosafot = new HashMap<>();
                    setTosafot(tosafot);
                    Intent intent = new Intent(getApplicationContext(), OrderConfirmationActivity.class);
                    intent.putExtra("tosafot", tosafot);
                    intent.putExtra("order_id", orderId);
                    intent.putExtra("order_time", orderTime);
                    intent.putExtra("CALENDAR",cal);
                    startActivity(intent);
                }
            }
        });
    }

    public void cancelOrder(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
