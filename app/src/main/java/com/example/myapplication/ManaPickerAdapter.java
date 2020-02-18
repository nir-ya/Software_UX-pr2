package com.example.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;

import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class ManaPickerAdapter extends PagerAdapter {


    private List<ManaListItem> typeCards;

    private LayoutInflater layoutInflater;
    private Context mContext;

    private static int selectedPosition = 1;

    ManaPickerAdapter(List<ManaListItem> typeCards, Context mContext) {
        this.typeCards = typeCards;
        this.mContext = mContext;
    }

    public static void setSelectedPosition(int i) {
        selectedPosition = i;
    }

    public static int getSelectedPosition() {
        return selectedPosition;
    }

    @Override
    public int getCount() {
        return typeCards.size();
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {

        layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.mana_pick_item,container,false);
        ImageView manaImg = view.findViewById(R.id.manaItemImg);
        TextView manaType = view.findViewById(R.id.manaItemTxt);
        TextView manaPrice = view.findViewById(R.id.manaItemPriceTxt);


        manaImg.setImageResource(typeCards.get(position).getManaImg());
        manaType.setText(typeCards.get(position).getType());
        manaPrice.setText(typeCards.get(position).getPrice());

        MaterialCardView cv = view.findViewById(R.id.manaItem);
        cv.setStrokeColor(selectedPosition==position?
                mContext.getResources().getColor(R.color.dark_green): Color.TRANSPARENT);



        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedPosition = position;
                notifyDataSetChanged();
            }
        });

        container.addView(view,0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

}
