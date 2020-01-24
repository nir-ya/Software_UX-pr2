package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Calendar;
import java.util.HashMap;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class OrderConfirmationActivity extends AppCompatActivity {

    String manaType, orderId, orderTime;
    int paymentMethod;
    HashMap<String, Boolean> tosafot;
    Timestamp time;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);

        TextView orderDetails = findViewById(R.id.order_details_text);

        Bundle extras = getIntent().getExtras();

        manaType = extras.getString("mana_type");
        orderId = extras.getString("order_id");
        orderTime = extras.getString("order_time");
        time = (Timestamp) extras.getParcelable("CALENDAR");

        orderDetails.setText(getString(R.string.order_time_text, orderTime));

        tosafot = (HashMap<String, Boolean>) extras.getSerializable("tosafot");
    }

    void addToDB() {
        final CollectionReference ordersCollection = db.collection(Constants.ORDERS);
        DocumentReference orderRef = ordersCollection.document(orderId);
        orderRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    addManaToDB(ordersCollection);
                } else {
                    OrderListItem order = new OrderListItem(time);
                    DocumentReference d = ordersCollection.document(orderId);
                    d.set(order);
                    addManaToDB(ordersCollection);
                }
            }
        });
    }

    private void addManaToDB(CollectionReference ordersCollection) {
        CollectionReference manotCollectionReference = ordersCollection.document(orderId)
                .collection(Constants.MANOT_SUBCOLLECTION);
        if (manaType == null) {
            manaType = "pita";
        }
        Mana newMana = new Mana(manaType, "", paymentMethod, tosafot, user.getDisplayName(), user.getUid());
        manotCollectionReference.add(newMana);
    }

    public void confirmOrder(View view) {
        addToDB();
        Toast.makeText(this, getString(R.string.mana_success_toast), Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
//        finish();
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radio_cash:
                if (checked)
                    paymentMethod = 0;
                break;
            case R.id.radio_credit:
                if (checked)
                    paymentMethod = 1;
                break;
        }
    }
}

//    private void popToast(boolean success) {
//        int msgId = R.string.new_order_success;
//        if (success == false) {
//            msgId = R.string.new_order_fail;
//        }
//        Toast.makeText(MainActivity.this, getResources().getString(msgId), Toast.LENGTH_LONG).show();
//    }

