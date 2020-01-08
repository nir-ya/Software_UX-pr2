package com.example.myapplication;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import android.view.View;

public class ManotAdapter extends FirestoreRecyclerAdapter<Manot, ManotAdapter.ManotHolder> {


    private final Context context;



    public ManotAdapter(@NonNull FirestoreRecyclerOptions<Manot> options, Context context) {
        super(options);
        this.context = context;

    }

    @Override
    protected void onBindViewHolder(@NonNull final ManotHolder holder, final int position, @NonNull final Manot model) {
        holder.textViewTitle.setText(model.getName());//TODO - change to normal title
        holder.textViewPrice.setText(Integer.toString(model.getPrice()));

    }



    @NonNull
    @Override
    public ManotHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.manot_item, parent, false);

        return new ManotHolder(v);
    }

    class ManotHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        TextView textViewPrice;


        public ManotHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_mana);
            textViewPrice = itemView.findViewById(R.id.price_mana);

        }
    }


}
