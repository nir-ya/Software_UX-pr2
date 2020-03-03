package com.example.myapplication;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

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


        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.off_white));
        }

        TextView orderDetails = findViewById(R.id.order_details_text);

        Bundle extras = getIntent().getExtras();

        manaType = extras.getString("mana_type");
        orderId = extras.getString("order_id");
        orderTime = extras.getString("order_time");
        time = extras.getParcelable("CALENDAR");
        
        orderDetails.setText(getString(R.string.order_details_text, orderTime));

        tosafot = (HashMap<String, Boolean>) extras.getSerializable("tosafot");
    }

    void addToDB() {
        final CollectionReference ordersCollection = db.collection(Constants.ORDERS);
        DocumentReference orderRef = ordersCollection.document(orderId);
        orderRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()) {
                    addManaToDB(ordersCollection);
                }
                else{
                    OrderListItem order = new OrderListItem(time);
                    orderId = Randomizer.randomString(18);
                    DocumentReference d = ordersCollection.document(orderId);
                    System.out.println("!!!!!!!!!");
                    System.out.println(orderId);
                    d.set(order);
                    addManaToDB(ordersCollection);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                    OrderListItem order = new OrderListItem(time);
                    orderId = Randomizer.randomString(18);
                    DocumentReference d = ordersCollection.document(orderId);
                System.out.println("!!!!!!!!!");
                System.out.println(orderId);
                    d.set(order);

                    addManaToDB(ordersCollection);
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
        Toast.makeText(this, getString(R.string.mana_success_toast), 3*Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

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

