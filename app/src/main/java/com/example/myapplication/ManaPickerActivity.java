package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import org.w3c.dom.Document;

public class ManaPickerActivity extends AppCompatActivity {



    String orderTime;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    ViewPager viewPager;  // TODO: change to a more informative names
    ManaPickerAdapter adapter;
    List<ManaListItem> models;
    private String orderId;
    String manaType = "pita"; // TODO: this should be set according to the "model" presented

    Timestamp time;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mana_picker);

        orderId = getIntent().getStringExtra("ref");

        DocumentReference orderRef = db.collection(Constants.ORDERS).document(orderId);
        orderRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
               OrderListItem order = documentSnapshot.toObject(OrderListItem.class);
               if (order != null)
               {
                   orderTime = Randomizer.formatter.format(order.getTimestamp().toDate());
               }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                time = (Timestamp) getIntent().getParcelableExtra("CALENDAR");
                orderTime = Randomizer.formatter.format(time.toDate());
            }
        });

        models = new ArrayList<>();
        models.add(new ManaListItem(R.drawable.pita, "חצי פיתה","15 שקלים")); // TODO these should be consts
        models.add(new ManaListItem(R.drawable.pita, "פיתה","18 שקלים"));
        models.add(new ManaListItem(R.drawable.lafa, "לאפה","22 שקלים"));

        adapter = new ManaPickerAdapter(models,this);

        viewPager = findViewById(R.id.manaPager);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(0,0,0,0);
        viewPager.setCurrentItem(1);

        DepthTransformation depthTransformation = new DepthTransformation();
        viewPager.setPageTransformer(true, depthTransformation);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

            @Override
            public void onPageSelected(int position) { }

            @Override
            public void onPageScrollStateChanged(int state) { }
        });
    }

    public void startManaActivity(View view) {
        Intent intent = new Intent(this, ManaActivity.class);
        intent.putExtra("mana_type", manaType);
        intent.putExtra("order_id", orderId);
        intent.putExtra("order_time", orderTime);
        intent.putExtra("CALENDAR",time);
        startActivity(intent);
    }

    private void setTosafot(HashMap tosafot) {
        tosafot.put(Constants.HUMMUS, true);
        tosafot.put(Constants.THINA, true);
        tosafot.put(Constants.HARIF, true);
        tosafot.put(Constants.AMBA, true);
        tosafot.put(Constants.TOMATO, true);
        tosafot.put(Constants.CUCUMBER, true);
        tosafot.put(Constants.ONION, true);
        tosafot.put(Constants.KRUV, true);
        tosafot.put(Constants.PICKELS, true);
        tosafot.put(Constants.CHIPS, true);
        tosafot.put(Constants.EGGPLAT, true);
    }

    public void simHakol(View view) {
        HashMap tosafot = new HashMap<String, Boolean>(); // TODO: there are better ways to do this
        setTosafot(tosafot);

        Intent intent = new Intent(this, OrderConfirmationActivity.class);
        intent.putExtra("mana_type", manaType);
        intent.putExtra("tosafot", tosafot);
        intent.putExtra("order_id", orderId);
        intent.putExtra("order_time", orderTime);
        intent.putExtra("CALENDAR",time);
        startActivity(intent);
    }



}
