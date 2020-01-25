package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ManaPickerActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    ViewPager viewPager;  // TODO: change to a more informative names
    ManaPickerAdapter adapter;
    List<ManaListItem> models;
    private String orderId;
    Timestamp time;
    String orderTime;
    ManaPickListener manaPickListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mana_picker);

        orderId = getIntent().getStringExtra("order_id");
        time = getIntent().getParcelableExtra("CALENDAR");
        orderTime = Randomizer.formatter.format(new Date(time.toDate().toString()));

        setupViewPager();

        manaPickListener = new ManaPickListener();
        viewPager.addOnPageChangeListener(manaPickListener);
    }

    private void setupViewPager() {
        models = new ArrayList<>();
        models.add(new ManaListItem(R.drawable.pita, "חצי פיתה","11 שקלים")); // TODO these should be consts
        models.add(new ManaListItem(R.drawable.pita, "פיתה","20 שקלים"));
        models.add(new ManaListItem(R.drawable.lafa, "לאפה","24 שקלים"));

        adapter = new ManaPickerAdapter(models,this);

        viewPager = findViewById(R.id.manaPager);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(0,0,0,0);
        viewPager.setCurrentItem(1);

        DepthTransformation depthTransformation = new DepthTransformation();
        viewPager.setPageTransformer(true, depthTransformation);
    }

    public void startManaActivity(View view) {
        Intent intent = new Intent(this, ManaActivity.class);
        intent.putExtra("mana_type", manaPickListener.getSelectedType());
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
        String manaType = manaPickListener.getSelectedType();
        HashMap tosafot = new HashMap<String, Boolean>(); // TODO: there are better ways to do this
        setTosafot(tosafot);

        Intent intent = new Intent(this, OrderConfirmationActivity.class);

        intent.putExtra("tosafot", tosafot);
        intent.putExtra("mana_type", manaType);
        intent.putExtra("order_id", orderId);
        intent.putExtra("order_time", orderTime);
        intent.putExtra("CALENDAR",time);
        startActivity(intent);
    }

    private static class ManaPickListener implements ViewPager.OnPageChangeListener {

        String selectedType = ManaListItem.PITA;

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

        @Override
        public void onPageSelected(int position) {
            switch (position) {
                case 0:
                    selectedType = ManaListItem.HALF_PITA;
                    break;
                case 1:
                    selectedType = ManaListItem.PITA;
                    break;
                case 2:
                    selectedType = ManaListItem.LAFA;
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {}

        String getSelectedType() {
            return selectedType;
        }
    }
}
