package com.example.myapplication;

import android.content.Context;
import android.nfc.Tag;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class MyBagAdapter extends RecyclerView.Adapter<MyBagAdapter.MyBagHolder> {

    private ArrayList<String> mManaType;
    private ArrayList<String> mManaPrice;
    private ArrayList<String> mTosafut;
    private Context mContext;


    public MyBagAdapter(ArrayList<String> mManaType, ArrayList<String> mManaPrice, ArrayList<String> mTosafut, Context mContext) {
        this.mManaType = mManaType;
        this.mManaPrice = mManaPrice;
        this.mTosafut = mTosafut;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyBagHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mybag_row_item,parent,false);
        MyBagHolder holder = new MyBagHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyBagHolder holder, int position) {
        holder.manaType.setText(mManaType.get(position));
        holder.manaPrice.setText(mManaPrice.get(position));
        holder.tosafut.setText(mTosafut.get(position));


    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyBagHolder extends RecyclerView.ViewHolder{

        TextView manaType;
        TextView manaPrice;
        TextView tosafut;
        ConstraintLayout parentLayout;

        public MyBagHolder(@NonNull View itemView) {
            super(itemView);
            manaType = itemView.findViewById(R.id.manaTypeTxt);
            manaPrice = itemView.findViewById(R.id.manaPriceTxt);
            tosafut = itemView.findViewById(R.id.tosafutTxt);
            parentLayout = itemView.findViewById(R.id.parent_layout);

        }
    }
}
