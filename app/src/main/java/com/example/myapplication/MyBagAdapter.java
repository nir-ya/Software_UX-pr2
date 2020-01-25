package com.example.myapplication;


import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import android.view.View;

import org.w3c.dom.Text;

public class MyBagAdapter extends FirestoreRecyclerAdapter<Mana, MyBagAdapter.MyBagHolder> {


    private final Context context;




    MyBagAdapter(@NonNull FirestoreRecyclerOptions<Mana> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull final MyBagHolder holder, final int position, @NonNull final Mana mana) {
        System.out.println(position); // todo: delete
        holder.textViewType.setText(mana.getHebType(mana.getType()));
        holder.textViewPrice.setText(Integer.toString(mana.getPrice()));
        holder.tosafot.setText(mana.getTosafotString());
        holder.statusText.setText(mana.getStatus());
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Todo - alert dialog
                deleteItem(position);
            }
        });



        if(mana.getPaymentMethod()==Mana.MEZUMAN){
            holder.paymentImg.setImageDrawable(context.getDrawable(R.drawable.cash));
        }
        else {
            holder.paymentImg.setImageDrawable(context.getDrawable(R.drawable.credit));
        }

        if(mana.getType().equals(Mana.PITA)){
            holder.manaImg.setImageDrawable(context.getDrawable(R.drawable.pita_full));
        }
        else if(mana.getType().equals(Mana.LAFA)){
            holder.manaImg.setImageDrawable(context.getDrawable(R.drawable.lafa_full));
        }
        else
            holder.manaImg.setImageDrawable(context.getDrawable(R.drawable.half_pita_full));
    }


    @NonNull
    @Override
    public MyBagHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.mybag_row_item, parent, false);
        return new MyBagHolder(v);
    }

    public void deleteItem(int position){
        getSnapshots().getSnapshot(position).getReference().delete();
//        notifyItemRemoved(position);
    }


    class MyBagHolder extends RecyclerView.ViewHolder {
        TextView textViewType;
        TextView textViewPrice;
        TextView tosafot;
        TextView hourText;
        TextView statusText;
        ImageView paymentImg;
        ImageView manaImg;
        Button deleteBtn;

        public MyBagHolder(View itemView) {
            super(itemView);
            textViewType = itemView.findViewById(R.id.manaTypeTxt);
            textViewPrice = itemView.findViewById(R.id.manaPriceTxt);
            tosafot = itemView.findViewById(R.id.tosafutTxt);
            hourText = itemView.findViewById(R.id.hourTxt);
            statusText = itemView.findViewById(R.id.statusTxt);
            paymentImg = itemView.findViewById(R.id.paymentImg);
            manaImg = itemView.findViewById(R.id.manaImg);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
        }
    }

}
