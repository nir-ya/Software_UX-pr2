package com.example.myapplication;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import android.view.View;

public class ManaAdapter extends FirestoreRecyclerAdapter<Mana, ManaAdapter.ManotHolder> {


    private final Context context;



    public ManaAdapter(@NonNull FirestoreRecyclerOptions<Mana> options, Context context) {
        super(options);
        this.context = context;

    }

    @Override
    protected void onBindViewHolder(@NonNull final ManotHolder holder, final int position, @NonNull final Mana model) {
        holder.textViewTitle.setText(model.getOwner());//TODO - change to normal title
        holder.textViewPrice.setText(Integer.toString(model.getPrice()));
        if(model.paymentMethod==0){
            holder.payment.setImageDrawable(context.getDrawable(R.drawable.cash));
        }
        else {
            holder.payment.setImageDrawable(context.getDrawable(R.drawable.credit));
        }


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
        ImageView payment;


        public ManotHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_mana);
            textViewPrice = itemView.findViewById(R.id.price_mana);
            payment = itemView.findViewById(R.id.payment_image);
        }
    }


}
