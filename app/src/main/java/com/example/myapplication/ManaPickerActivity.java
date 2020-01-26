package com.example.myapplication;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

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
    List<ManaListItem> cards;
    private String orderId;
    Timestamp time;
    String orderTime;
    ManaPickListener manaPickListener;

    ArgbEvaluator argbEvaluator = new ArgbEvaluator();

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
        cards = new ArrayList<>();
        cards.add(new ManaListItem(R.drawable.pita, "חצי פיתה","10 שקלים")); // TODO these should be consts
        cards.add(new ManaListItem(R.drawable.pita, "פיתה","18 שקלים"));
        cards.add(new ManaListItem(R.drawable.lafa, "לאפה","22 שקלים"));
        cards.add(new ManaListItem(R.drawable.lafa, "חצי לאפה","12 שקלים"));

        adapter = new ManaPickerAdapter(cards,this);

        viewPager = findViewById(R.id.manaPager);

        viewPager.setAdapter(adapter);
        viewPager.setClipToPadding(false);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;

        int paddingToSet = width/6;
        viewPager.setPadding(paddingToSet,0,paddingToSet,0);

//        viewPager.setOffscreenPageLimit(3);
//        viewPager.setPageMargin(dpToPx(10));

        viewPager.setCurrentItem(1);

//        viewPager.setPageTransformer(true, depthTransformation);
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

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics =  getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}
