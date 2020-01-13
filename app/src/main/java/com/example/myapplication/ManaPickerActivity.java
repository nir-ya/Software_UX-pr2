package com.example.myapplication;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class ManaPickerActivity extends AppCompatActivity {

    ViewPager viewPager;
    ManaPickerAdapter adapter;
    List<ManaListItem> models;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mana_picker);

        models = new ArrayList<>();
        models.add(new ManaListItem(R.drawable.pita, "פיתה","20 שקלים"));
        models.add(new ManaListItem(R.drawable.pita, "חצי פיתה","15 שקלים"));
        models.add(new ManaListItem(R.drawable.lafa, "לאפה","25 שקלים"));


        adapter = new ManaPickerAdapter(models,this);

        viewPager = findViewById(R.id.manaPager);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(10,0,10,0);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });




    }


}
