package com.example.myapplication;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import android.view.View;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class MyBagAdapter extends FirestoreRecyclerAdapter<Mana, MyBagAdapter.MyBagHolder> {

    private final Context context;
    AlertDialog.Builder deleteBuilder;

    MyBagAdapter(@NonNull FirestoreRecyclerOptions<Mana> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull final MyBagHolder holder, final int position, @NonNull final Mana mana) {
        holder.textViewType.setText(mana.getHebType(mana.getType()));
        holder.textViewPrice.setText(Integer.toString(mana.getPrice()) + "₪");
        holder.tosafot.setText(mana.getTosafotString());
        holder.statusText.setText(mana.getStatus());

        if (mana.getStatus().equals("open")){
            holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popUpAlertDialog(position);

                }
            });
            holder.statusText.setText("הזמנה פתוחה");
        }
        else if (mana.getStatus().equals("canceled")){
            holder.deleteBtn.setText("הסר");
            holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteItem(position);

                }
            });
            holder.statusText.setText("הזמנה סגורה");
        }
        else{
            holder.deleteBtn.setVisibility(View.INVISIBLE);
        }
        DateFormat format = new SimpleDateFormat("HH:mm");
        format.setTimeZone(TimeZone.getTimeZone("GMT+2"));
        holder.hourText.setText(format.format(mana.getTimestamp()));

        setGraphics(holder, mana);


    }

    private void setGraphics(@NonNull MyBagHolder holder, @NonNull Mana mana) {
        if(mana.getPaymentMethod()==Mana.MEZUMAN){
            holder.paymentImg.setImageDrawable(context.getDrawable(R.drawable.cash));
        }
        else {
            holder.paymentImg.setImageDrawable(context.getDrawable(R.drawable.credit));
        }

        if(mana.getType().equals(Mana.PITA)){
            holder.manaImg.setImageDrawable(context.getDrawable(R.drawable.pita_full_no_margin));
        }
        else if(mana.getType().equals(Mana.LAFA)){
            holder.manaImg.setImageDrawable(context.getDrawable(R.drawable.lafa_full_no_margin));
        }
        else if(mana.getType().equals(Mana.HALF_LAFA)){
            holder.manaImg.setImageDrawable(context.getDrawable(R.drawable.half_lafa_full_no_margin));
        }
        else
            holder.manaImg.setImageDrawable(context.getDrawable(R.drawable.half_pita_full_no_margin));
    }


    @NonNull
    @Override
    public MyBagHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.mybag_row_item, parent, false);
        return new MyBagHolder(v);
    }

    public void deleteItem(final int position){
        getSnapshots().getSnapshot(position).getReference().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                notifyDataSetChanged();

            }
        });


    }

    void popUpAlertDialog(final int position){
        deleteBuilder = new AlertDialog.Builder(context);
        deleteBuilder.setMessage(R.string.cancel_order)
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogDelete, int id) {
                        deleteItem(position);
                        Toast.makeText(context,R.string.order_was_canceled,
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogDelete, int id) {
                        dialogDelete.cancel();
                    }
                });
        AlertDialog alertDelete = deleteBuilder.create();
        alertDelete.getWindow().setBackgroundDrawableResource(R.color.light_peach);
        alertDelete.show();
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
