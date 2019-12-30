package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import android.view.View;

public class OrderListItemAdapter extends FirestoreRecyclerAdapter <OrderListItem, OrderListItemAdapter.OrderListItemHolder> {


    public OrderListItemAdapter(@NonNull FirestoreRecyclerOptions<OrderListItem> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull OrderListItemHolder holder, int position, @NonNull OrderListItem model) {
        holder.textViewTitle.setText(model.getOrderTime());//TODO - change to normal title
    }

    @NonNull
    @Override
    public OrderListItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_list_item,parent,false);

        return new OrderListItemHolder(v);
    }

    class OrderListItemHolder extends RecyclerView.ViewHolder{
        TextView textViewTitle;

        public OrderListItemHolder(View itemView){
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.order_title);
        }
    }


}
