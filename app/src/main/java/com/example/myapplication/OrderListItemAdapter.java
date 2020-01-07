package com.example.myapplication;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import android.view.View;

import static java.lang.Boolean.TRUE;

public class OrderListItemAdapter extends FirestoreRecyclerAdapter<OrderListItem, OrderListItemAdapter.OrderListItemHolder> {


    private static final int CRITICAL_PRICE = 45;
    private static final int MAX_PRICE = 70;
    private final Context context;

    public OrderListItemAdapter(@NonNull FirestoreRecyclerOptions<OrderListItem> options, Context context) {
        super(options);
        this.context = context;

    }

    @Override
    protected void onBindViewHolder(@NonNull final OrderListItemHolder holder, int position, @NonNull final OrderListItem model) {
        holder.textViewTitle.setText(model.getSerial());//TODO - change to normal title

        progressBarHandler(holder, model);

        priceTextHandler(holder, model);

        statusTextHandler(holder, model);

    }

    private void statusTextHandler(OrderListItemHolder holder, OrderListItem model) {
        if (model.getStatus().equals("open")) {
            if (model.getPrice() > CRITICAL_PRICE) {
                holder.statusText.setText("סטטוס: מוכנה לשילוח!");
            }
            else{
                holder.statusText.setText("סטטוס: מחכה למשבחים...");
            }
        }
        else if(model.getStatus().equals("locked")){
            lock_order(holder);

        }

    }

    private void lock_order(OrderListItemHolder holder) {
        holder.statusText.setText("ההזמנה יצאה");
        holder.joinButton.setText("נעול");
        holder.joinButton.setBackgroundColor(context.getResources().getColor(R.color.grey));
        holder.progressBar.setProgressDrawable(context.getDrawable(R.drawable.progress_bar_locked));    }

    private void priceTextHandler(OrderListItemHolder holder, OrderListItem model) {
        String priceStr = Integer.toString(model.getPrice());
        String priceTextInput = String.format("הכסף שנצבר: %s מתוך 70 שקלים", priceStr);
        holder.priceText.setText(priceTextInput);

    }

    private void progressBarHandler(@NonNull OrderListItemHolder holder, OrderListItem model) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.progressBar.setProgress(model.getPrice(), TRUE);
            if (model.getPrice() > 70) {
                holder.progressBar.setProgress(70, TRUE);

            }
        }
        if (model.getPrice() > CRITICAL_PRICE) {
            holder.progressBar.setProgressDrawable(context.getDrawable(R.drawable.progress_bar_green));

        } else {
            holder.progressBar.setProgressDrawable(context.getDrawable(R.drawable.progress_bar_orange));

        }
    }

    @NonNull
    @Override
    public OrderListItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_list_item, parent, false);

        return new OrderListItemHolder(v);
    }

    class OrderListItemHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        Button infoButton;
        Button joinButton;
        ProgressBar progressBar;
        TextView statusText;
        TextView priceText;


        public OrderListItemHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.order_title);
            infoButton = itemView.findViewById(R.id.btn_info);
            joinButton = itemView.findViewById(R.id.btn_join);
            progressBar = itemView.findViewById(R.id.order_progress);
            priceText = itemView.findViewById(R.id.money_text);
            statusText = itemView.findViewById(R.id.status);
        }
    }


}
