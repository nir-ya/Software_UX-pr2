package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ManaPickerActivity extends AppCompatActivity {

    private static final String HUMMUS = "Hummus";
    private static final String HARIF = "Harif";
    private static final String THINA = "Thina";
    private static final String AMBA = "Amba";
    private static final String TOMATO = "Tomato";
    private static final String CUCUMBER = "Cucumber";
    private static final String ONION = "Onion";
    private static final String KRUV = "Kruv";
    private static final String PICKELS = "Pickels";
    private static final String CHIPS = "Chips";
    private static final String EGGPLAT = "Eggplant";

    ViewPager viewPager;  // TODO: find more informative variable names
    ManaPickerAdapter adapter;
    List<ManaListItem> models;
    private String orderReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mana_picker);

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

        orderReference = getIntent().getStringExtra("ref"); // todo const

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
        startActivity(intent);
    }

    public void simHakol(View view) {
        String owner = "John"; // TODO: this should be set to the real username
        String type = "pita"; // TODO: this should be set according to the "model" presented
        HashMap tosafot = new HashMap<String, Boolean>(); // TODO: there are better ways to do this
        tosafot.put(HUMMUS, true);
        tosafot.put(THINA, true);
        tosafot.put(HARIF, true);
        tosafot.put(AMBA, true);
        tosafot.put(TOMATO, true);
        tosafot.put(CUCUMBER, true);
        tosafot.put(ONION, true);
        tosafot.put(KRUV, true);
        tosafot.put(PICKELS, true);
        tosafot.put(CHIPS, true);
        tosafot.put(EGGPLAT, true);
        int price = ManaModel.getPrice(type);
        ManaListItem mana = new ManaListItem(owner, type, price, tosafot);
        
        //TODO: forward mana object to
        //TODO: need to know what order is associated with this mana to the next screen payment + notes

        System.out.println("I was just kidding!");
        Log.w("SHEVAH", "I was just kidding!!!");

        
    }
}
