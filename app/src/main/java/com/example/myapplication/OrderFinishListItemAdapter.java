package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import android.view.View;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.List;

public class OrderFinishListItemAdapter extends FirestoreRecyclerAdapter<OrderFinishListItem,OrderFinishListItemAdapter.OrderFinishListItemHolder> {

    FirestoreRecyclerOptions<OrderFinishListItem> options;
    public OrderFinishListItemAdapter(@NonNull FirestoreRecyclerOptions<OrderFinishListItem> options) {
        super(options);
        options = options;
    }

    @Override
    protected void onBindViewHolder(@NonNull OrderFinishListItemHolder holder, int position, @NonNull OrderFinishListItem model) {
        holder.textViewName.setText(model.getOwner());
        holder.textViewDescription.setText(model.getOrderDescription());
        holder.textViewOrderType.setText(model.getType());
    }

    @NonNull
    @Override
    public OrderFinishListItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_finish_list_item,
                parent,false);
        return new OrderFinishListItemHolder(v);
    }

    class OrderFinishListItemHolder extends RecyclerView.ViewHolder{
        TextView textViewName;
        TextView textViewOrderType;
        TextView textViewDescription;


        public OrderFinishListItemHolder(View itemView){
            super(itemView);
            textViewName = itemView.findViewById(R.id.name);
            textViewOrderType = itemView.findViewById(R.id.orderType);
            textViewDescription = itemView.findViewById(R.id.description);
        }
    }
}
