package com.example.myapplication;


import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import android.view.View;

import net.cachapa.expandablelayout.ExpandableLayout;

import static java.lang.Boolean.TRUE;

public class OrderListItemAdapter extends FirestoreRecyclerAdapter<OrderListItem, OrderListItemAdapter.OrderListItemHolder> {


    private static final int CRITICAL_PRICE = 45;
    private static final int MIN_ORDER = 70;
    private final Context context;
    static int mExpandedPosition = -1;
    private int previousExpandedPosition = -1;
    private RecyclerView recyclerView = null;


    public OrderListItemAdapter(@NonNull FirestoreRecyclerOptions<OrderListItem> options, Context context) {
        super(options);
        this.context = context;

    }

    @Override
    protected void onBindViewHolder(@NonNull final OrderListItemHolder holder, final int position, @NonNull final OrderListItem model) {
        holder.textViewTitle.setText(model.getSerial());//TODO - change to normal title
        //holder.expandableView.setVisibility(View.VISIBLE);
        progressBarHandler(holder, model);

        priceTextHandler(holder, model);

        statusTextHandler(holder, model);

        expandableLayoutHandler(holder);


    }

    private void expandableLayoutHandler(@NonNull final OrderListItemHolder holder) {
        holder.infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!holder.expandableView.isExpanded()) {
                    holder.expandableView.expand(true);
                } else {
                    holder.expandableView.collapse(true);
                }
            }
        });
    }


    /**
     * this function inserts the relevant text to the "order status" textView,
     * by the relevant order status
     *
     * @param holder - the RecyclerView item holder
     * @param model  - the "Order" object
     */
    private void statusTextHandler(OrderListItemHolder holder, OrderListItem model) {
        if (model.getStatus().equals("open")) {
            if (model.getPrice() > CRITICAL_PRICE) {
                holder.statusText.setText("סטטוס: מוכנה לשילוח!");
            } else {
                holder.statusText.setText("סטטוס: מחכה למשבחים...");
            }
        } else if (model.getStatus().equals("locked")) {
            lock_order(holder);

        }

    }

    /**
     * a function that change graphics to "locked" status graphics, if order is locked
     *
     * @param holder - the RecyclerView item holder
     */
    private void lock_order(OrderListItemHolder holder) {
        holder.statusText.setText("ההזמנה יצאה");
        holder.joinButton.setText("נעול");
        holder.joinButton.setBackgroundColor(context.getResources().getColor(R.color.grey));
        holder.progressBar.setProgressDrawable(context.getDrawable(R.drawable.progress_bar_locked));
    }

    /**
     * this function inserts the relevant text to the "order price" textView,
     * by the relevant order collected money
     *
     * @param holder - the RecyclerView item holder
     * @param model  - the "Order" object
     */
    private void priceTextHandler(OrderListItemHolder holder, OrderListItem model) {
        String priceStr = Integer.toString(model.getPrice());
        String priceTextInput = String.format("הכסף שנצבר: %s מתוך 70 שקלים", priceStr);
        holder.priceText.setText(priceTextInput);

    }

    /**
     * this function changes the status bar by the order price ration (price/MIN_ORDER)
     * by the relevant order collected money
     *
     * @param holder - the RecyclerView item holder
     * @param model  - the "Order" object
     */
    private void progressBarHandler(@NonNull final OrderListItemHolder holder, OrderListItem model) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.progressBar.setProgress(model.getPrice(), TRUE);
            if (model.getPrice() > MIN_ORDER) {
                holder.progressBar.setProgress(MIN_ORDER, TRUE);

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
        ExpandableLayout expandableView;
        CardView cardView;

        public OrderListItemHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.order_title);
            infoButton = itemView.findViewById(R.id.btn_info);
            joinButton = itemView.findViewById(R.id.btn_join);
            progressBar = itemView.findViewById(R.id.order_progress);
            priceText = itemView.findViewById(R.id.money_text);
            statusText = itemView.findViewById(R.id.status);
            expandableView = itemView.findViewById(R.id.expandable_layout);
            cardView = itemView.findViewById(R.id.card_layout);

        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        this.recyclerView = recyclerView;
    }

}
