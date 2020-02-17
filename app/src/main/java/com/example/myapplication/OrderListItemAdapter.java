package com.example.myapplication;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
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
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.Calendar;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;


public class OrderListItemAdapter extends FirestoreRecyclerAdapter<OrderListItem, OrderListItemAdapter.OrderListItemHolder> {

    private Calendar cal = Calendar.getInstance();
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

    protected void onBindViewHolder(@NonNull final OrderListItemHolder holder, final int position,
                                    @NonNull final OrderListItem order) {

        String documentId = getSnapshots().getSnapshot(position).getId();

        holder.textViewTitle.setText(getTimeTitle(order));

        setPriceTextView(holder, order);

        updateOrderItemByStatus(holder, order, documentId);

      checkIfOrderTimePassed(order, documentId);
      setCardExpansion(holder.orderCard, holder);
      setCardExpansion(holder.infoButton, holder);


      setOrderInfoRecyclerView(holder, documentId);

    }

    private void checkIfOrderTimePassed(@NonNull OrderListItem order, String documentId) {
        if(order.getTimestamp().compareTo(Timestamp.now()) < 0){
            DocumentReference orderRef = db.collection(Constants.ORDERS).document(documentId);
            if(order.reachedMinimum()){
              orderRef.update("status", OrderListItem.LOCKED); //todo make status const String
            }else{
              orderRef.update("status", OrderListItem.CANCELED);
            }
        }
    }

    private String getTimeTitle(OrderListItem order) {
        String title;
        if (order != null) {
            String time = Randomizer.formatter.format(order.getTimestamp().toDate());
            title = context.getResources().getString(R.string.order_list_item_title, time);
        } else {
            title = "";
        }
        return title;
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();
        initEmptyView();
    }

    private void setJoinButtonHandler(View joinButton, final String doc, final OrderListItem order) {
        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ManaPickerActivity.class);
  
                intent.putExtra("order_id", doc);
                intent.putExtra("CALENDAR",order.getTimestamp());
                intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    private void setOrderButtonHandler(View orderButton, final String documentId) {
        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, OrderFinishActivity.class);

                intent.putExtra("order_id", documentId);
                intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
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
                    holder.infoButton.setText("צמצם");
                } else {
                    holder.expandableView.collapse(true);
                    holder.infoButton.setText("מי שם?");
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
    private void updateOrderItemByStatus(OrderListItemHolder holder, OrderListItem model, String documentId) {
        if (model.getStatus().equals(OrderListItem.OPEN)) {
            openOrder(holder, documentId, model);
        } else if (model.getStatus().equals(OrderListItem.LOCKED)) {
            lockOrder(holder, documentId, model);
        } else if (model.getStatus().equals(OrderListItem.CANCELED)){
            cancelOrder(holder, documentId, model);
        }
    }

    /**
     * a function that change graphics to "locked" status graphics, if order is locked
     *
     * @param holder - the RecyclerView item holder
     * @param documentId
     * @param orderListItem
     */
    private void lockOrder(OrderListItemHolder holder, String documentId, OrderListItem orderListItem) {
        holder.statusText.setText(Constants.ORDER_OUT);
        holder.orderButton.setText(Constants.LOCKED_TEXT);
        holder.orderButton.setBackgroundColor(context.getResources().getColor(R.color.grey));
        holder.progressBar.setProgressDrawable(context.getDrawable(R.drawable.progress_bar_locked));

        setOrderButtonHandler(holder.orderButton, documentId);

        holder.statusText.setTextColor(context.getResources().getColor(R.color.TextGreen));
        ViewGroup layout = (ViewGroup) holder.orderButton.getParent();
        if(null!=layout) {
            holder.orderButton.setVisibility(VISIBLE);
            holder.infoButton.setVisibility(VISIBLE);
        }
    }

    /**
     * a function that graphics "open" orders
     *  @param holder - the RecyclerView item holder
     *
     * @param documentId
     * @param model  - the orderListItem relevant item
     */
    private void openOrder(OrderListItemHolder holder, String documentId, OrderListItem model) {
        holder.orderButton.setText(Constants.JOIN_TEXT);
        holder.orderButton.setBackgroundColor(context.getResources().getColor(R.color.dark_navy));

        setJoinButtonHandler(holder.orderButton, documentId, model);

        holder.statusText.setText(model.getPrice() >= MIN_ORDER ? Constants.READY_TEXT : Constants.WAITING);


        setProgressBar(holder, model);

        holder.statusText.setTextColor(context.getResources().getColor(R.color.TextGreen));
        ViewGroup layout = (ViewGroup) holder.orderButton.getParent();
        if(null!=layout) {
            holder.orderButton.setVisibility(VISIBLE);
            holder.infoButton.setVisibility(VISIBLE);
        }



    }

  /**
   * Sets the graphic for presenting a canceled order
   * @param holder - the RecyclerView item holder
   * @param documentId - String representation of the document ID
   * @param orderListItem order object
   */
  private void cancelOrder(OrderListItemHolder holder, String documentId, OrderListItem orderListItem) {
    holder.statusText.setText(Constants.ORDER_CANCELED);
    holder.orderButton.setText("התבאס");
    holder.orderButton.setBackgroundColor(context.getResources().getColor(R.color.red));
    holder.progressBar.setProgressDrawable(context.getDrawable(R.drawable.progress_bar_locked));
    ViewGroup layout = (ViewGroup) holder.orderButton.getParent();

    holder.statusText.setTextColor(Color.RED);
    if(null!=layout) {
        holder.orderButton.setVisibility(View.GONE);
        holder.infoButton.setVisibility(View.GONE);
        //layout.removeView(holder.orderButton);
        //layout.removeView(holder.infoButton);
    }
  }

    private void setProgressBar(OrderListItemHolder holder, OrderListItem model) {
        if (model.getPrice() >= CRITICAL_PRICE) {
            holder.progressBar.setProgressDrawable(context.getDrawable(R.drawable.progress_bar_green));
        } else {
            holder.progressBar.setProgressDrawable(context.getDrawable(R.drawable.progress_bar_orange));
        }
        holder.progressBar.setProgress(model.getPrice());
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


    @NonNull
    @Override
    public OrderListItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_list_item, parent, false);

        return new OrderListItemHolder(v);
    }

    class OrderListItemHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        Button infoButton;
        Button orderButton;
        ProgressBar progressBar;
        TextView statusText;
        TextView priceText;
        ExpandableLayout expandableView;
        CardView orderCard;
        RecyclerView manotList;

        OrderListItemHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.order_title);
            infoButton = itemView.findViewById(R.id.btn_info);
            orderButton = itemView.findViewById(R.id.btn_order);
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
                    emptyView.setVisibility(getItemCount() == 0 ? VISIBLE : GONE);
                }
            }, Constants.SHORT_DELAY);
        }
    }

    void setEmptyView(View view) {
        this.emptyView = view;
        initEmptyView();
    }




}
