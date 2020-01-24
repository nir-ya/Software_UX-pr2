package com.example.myapplication;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import android.view.View;


import java.io.Serializable;
import java.util.Date;

import net.cachapa.expandablelayout.ExpandableLayout;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static java.lang.Boolean.TRUE;

public class OrderListItemAdapter extends FirestoreRecyclerAdapter<OrderListItem, OrderListItemAdapter.OrderListItemHolder> {


    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private View emptyView;

    private static final int CRITICAL_PRICE = 52;
    private static final int MIN_ORDER = 70;
    private final Context context;


    OrderListItemAdapter(@NonNull FirestoreRecyclerOptions<OrderListItem> options, Context context) {
        super(options);
        this.context = context;
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     * This method should update the contents of the itemView to reflect the item at the given position.
     * 
     * @param holder
     * @param position
     * @param order
     */
    @Override
    protected void onBindViewHolder(@NonNull final OrderListItemHolder holder, final int position, @NonNull final OrderListItem order) {
        String documentId = getSnapshots().getSnapshot(position).getId();

        holder.textViewTitle.setText(getTimeTitle(order));//TODO - change to normal title

        setProgressBar(holder, order);

        setPriceTextView(holder, order);

        setStatusTextView(holder, order);

        setCardExpansion(holder.orderCard, holder);
        setCardExpansion(holder.infoButton, holder);
        setJoinButtonHandler(holder.joinButton, documentId);

        setOrderInfoRecyclerView(holder, documentId);
    }

    private String getTimeTitle(OrderListItem order) {
        String title;
        try {
            String s = Randomizer.formatter.format(order.getTimestamp().toDate());
            title = "הזמנה לשעה: " + s;
        } catch (NullPointerException e) {
            title = "";
        }
        return title;
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();
        initEmptyView();
    }

    private void setJoinButtonHandler(View joinButton, final String doc) {
        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, ManaPickerActivity.class);

                i.putExtra("ref", doc);
                i.addFlags(FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);

            }
        });
    }

    private void setOrderInfoRecyclerView(@NonNull OrderListItemHolder holder, String documentId) {

        CollectionReference manotRef = db.collection(Constants.ORDERS)
                .document(documentId).collection(Constants.MANOT_SUBCOLLECTION);

        Query query = manotRef.orderBy("price", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions options = new FirestoreRecyclerOptions.Builder<Mana>()
                .setQuery(query, Mana.class)
                .build();
        ManaAdapter adapter = new ManaAdapter(options, holder.itemView.getContext());
        LinearLayoutManager layout = new LinearLayoutManager(holder.itemView.getContext());
        layout.setOrientation(RecyclerView.VERTICAL);
        holder.manotList.setLayoutManager(layout);
        holder.manotList.setAdapter(adapter);
        adapter.startListening();
    }


    /**
     * a function that s.et the expandableLayout on and off
     *
     * @param holder
     */
    private void setCardExpansion(View view, @NonNull final OrderListItemHolder holder) {
        view.setOnClickListener(new View.OnClickListener() {
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
    private void setStatusTextView(OrderListItemHolder holder, OrderListItem model) {
        if (model.getStatus().equals(OrderListItem.OPEN)) {
            openOrder(holder, model);
        } else if (model.getStatus().equals(OrderListItem.LOCKED)) {
            lockOrder(holder);
        }
//        setProgressBar(holder, model);
    }

    /**
     * a function that change graphics to "locked" status graphics, if order is locked
     *
     * @param holder - the RecyclerView item holder
     */
    private void lockOrder(OrderListItemHolder holder) {
        holder.statusText.setText(Constants.ORDER_OUT);
        holder.joinButton.setText(Constants.LOCKED_TEXT);
        holder.joinButton.setBackgroundColor(context.getResources().getColor(R.color.grey));
//        holder.progressBar.setProgressDrawable(context.getDrawable(R.drawable.progress_bar_locked));
    }

    /**
     * a function that graphics "open" orders
     *
     * @param holder - the RecyclerView item holder
     * @param model  - the orderListItem relevant item
     */
    private void openOrder(OrderListItemHolder holder, OrderListItem model) {
        holder.joinButton.setText(Constants.JOIN_TEXT);
        holder.joinButton.setBackgroundColor(context.getResources().getColor(R.color.dark_navy));
        if (model.getPrice() >= MIN_ORDER) {
            holder.statusText.setText(Constants.READY_TEXT);
            holder.progressBar.setProgressDrawable(context.getDrawable(R.drawable.progress_bar_green));
            holder.progressBar.setProgress(model.getPrice());
        } else {
            holder.statusText.setText(Constants.WAITING);
            holder.progressBar.setProgressDrawable(context.getDrawable(R.drawable.progress_bar_orange));
            holder.progressBar.setProgress(model.getPrice());
        }
    }

    /**
     * this function inserts the relevant text to the "order price" textView,
     * by the relevant order collected money
     *
     * @param holder - the RecyclerView item holder
     * @param model  - the "Order" object
     */
    private void setPriceTextView(OrderListItemHolder holder, OrderListItem model) {
        String priceStr = Integer.toString(model.getPrice());
        String priceTextInput = String.format(Constants.MONEY_MADE, priceStr);
        holder.priceText.setText(priceTextInput);
    }

    /**
     * this function changes the status bar by the order price ration (price/MIN_ORDER)
     * by the relevant order collected money
     *
     * @param holder - the RecyclerView item holder
     * @param model  - the "Order" object
     */
    private void setProgressBar(@NonNull OrderListItemHolder holder, OrderListItem model) {

        holder.progressBar.setProgress(40);

//
//        if (model.getPrice() >= CRITICAL_PRICE) {
//            holder.progressBar.setProgressDrawable(context.getDrawable(R.drawable.progress_bar_green));
//        } else {
//            holder.progressBar.setProgressDrawable(context.getDrawable(R.drawable.progress_bar_orange));
//        }

        holder.progressBar.setProgress(40);

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
        CardView orderCard;
        RecyclerView manotList;

        public OrderListItemHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.order_title);
            infoButton = itemView.findViewById(R.id.btn_info);
            joinButton = itemView.findViewById(R.id.btn_join);
            progressBar = itemView.findViewById(R.id.order_progress);
            priceText = itemView.findViewById(R.id.money_text);
            statusText = itemView.findViewById(R.id.status);
            expandableView = itemView.findViewById(R.id.expandable_layout);
            orderCard = itemView.findViewById(R.id.card_layout);
            manotList = itemView.findViewById(R.id.manot_list);


        }
    }

    /**
     * this function is important for the expandable view to work
     *
     * @param recyclerView
     */
    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

    }

    private void initEmptyView() {
        if (emptyView != null) {
            //handler is needed because load data takes time
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    emptyView.setVisibility(
                            getItemCount() == 0 ? VISIBLE : GONE);
                }
            }, Constants.SHORT_DELAY);
        }
    }

    public void setEmptyView(View view) {
        this.emptyView = view;
        initEmptyView();
    }


}
