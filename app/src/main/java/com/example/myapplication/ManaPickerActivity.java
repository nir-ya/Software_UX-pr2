package com.example.myapplication;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ManaPickerActivity extends AppCompatActivity {


    String orderTime;


    ViewPager viewPager;  // TODO: change to a more informative names
    ManaPickerAdapter adapter;
    List<ManaListItem> cards;
    private String orderId;
    Timestamp time;

    String breadType = ManaListItem.PITA;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.light_peach));
        }

        setContentView(R.layout.activity_mana_picker);

        orderId = getIntent().getStringExtra("order_id");
        time = getIntent().getParcelableExtra("CALENDAR");
        orderTime = Randomizer.formatter.format(new Date(time.toDate().toString()));

        setupViewPager();
    }


    private void setupViewPager() {

        initData();


        adapter = new ManaPickerAdapter(cards, this);


        viewPager = findViewById(R.id.manaPager);


        viewPager.setAdapter(adapter);


        viewPager.setClipToPadding(false);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;

        int paddingToSet = width / 6;
        viewPager.setPadding(paddingToSet, 0, paddingToSet, 0);

        viewPager.setCurrentItem(Constants.PITA_POSITION);
        ManaPickerAdapter.setSelectedPosition(Constants.PITA_POSITION);


    }

    private void initData() {
        cards = new ArrayList<>();
        cards.add(new ManaListItem(R.drawable.half_pita_full, getString(R.string.half_pita_text), getString(R.string.half_pita_price)));
        cards.add(new ManaListItem(R.drawable.pita_full, getString(R.string.pita_text), getString(R.string.pita_price)));
        cards.add(new ManaListItem(R.drawable.lafa_full, getString(R.string.lafa_text), getString(R.string.lafa_price)));
        cards.add(new ManaListItem(R.drawable.half_lafa_full, getString(R.string.half_lafa_text), getString(R.string.half_lafa_price)));
    }

    public void startManaActivity(View view) {

        String selectedType = getSelectedTypeByPosition();

        System.out.println("!!!!!!!!!!!!!!!!!!!!!\n"+selectedType);
        Intent intent = new Intent(this, ManaActivity.class);
        intent.putExtra("mana_type", selectedType);
        intent.putExtra("order_id", orderId);
        intent.putExtra("order_time", orderTime);
        intent.putExtra("CALENDAR", time);
        startActivity(intent);
    }

    private String getSelectedTypeByPosition() {
        switch (ManaPickerAdapter.getSelectedPosition()) {
            case 0:
                breadType = ManaListItem.HALF_PITA;
                break;
            case 1:
                breadType = ManaListItem.PITA;
                break;
            case 2:
                breadType = ManaListItem.LAFA;
                break;
            case 3:
                breadType = ManaListItem.HALF_LAFA;
                break;
        }

        return breadType;
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

        String manaType = getSelectedTypeByPosition();
        HashMap tosafot = new HashMap<String, Boolean>(); // TODO: there are better ways to do this
        setTosafot(tosafot);

        Intent intent = new Intent(this, OrderConfirmationActivity.class);

        intent.putExtra("tosafot", tosafot);
        intent.putExtra("mana_type", manaType);
        intent.putExtra("order_id", orderId);
        intent.putExtra("order_time", orderTime);
        intent.putExtra("CALENDAR", time);
        startActivity(intent);
    }

}
