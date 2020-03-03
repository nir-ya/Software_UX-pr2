package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Icon;
import android.media.MediaPlayer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.firestore.SnapshotParser;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.Calendar;
import java.util.HashMap;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;


public class
OrderListItemAdapter extends FirestoreRecyclerAdapter<OrderListItem, OrderListItemAdapter.OrderListItemHolder> {

    private Calendar cal = Calendar.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private View emptyView;

    static final int EMPTY_VIEW_DELAY = 2000;
    private static final int CRITICAL_PRICE = 60;
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
        if (order.getTimestamp().compareTo(Timestamp.now()) < 0 && order.getStatus().equals(OrderListItem.OPEN)) {
            DocumentReference orderRef = db.collection(Constants.ORDERS).document(documentId);
            if (order.reachedMinimum()) {
                orderRef.update("status", OrderListItem.READY); //todo make status const String
            } else {
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
                intent.putExtra("CALENDAR", order.getTimestamp());
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
                .setQuery(query, new SnapshotParser<Mana>() {
                    @NonNull
                    @Override
                    public Mana parseSnapshot(@NonNull DocumentSnapshot snapshot) {

                        String owner = snapshot.get("owner").toString();
                        String type = snapshot.get("type").toString();
                        int paymentMethod = snapshot.getLong("paymentMethod").intValue();
                        HashMap<String, Boolean> tosafot = (HashMap) snapshot.get("tosafot");
                        String ownerUserId = snapshot.get("ownerUserId").toString();
                        String notes = "";

                        if (owner.contains(" ")) {
                            owner = owner.split(" ")[0];
                        }

                        return new Mana(type, notes, paymentMethod, tosafot, owner, ownerUserId);
                    }
                })
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
        switch (model.getStatus()) {
            case OrderListItem.OPEN:
                setOpenItem(holder, documentId, model);
                break;
            case OrderListItem.READY:
                setReadyItem(holder, documentId, model);
                break;
            case OrderListItem.CANCELED:
                setCanceledItem(holder, documentId, model);
                break;
            case OrderListItem.ORDERED:
                setOrderedItem(holder, documentId, model);
                break;
        }
    }

    /**
     * a function that change graphics to "locked" status graphics, if order is locked
     *
     * @param holder        - the RecyclerView item holder
     * @param documentId
     * @param orderListItem
     */
    private void setReadyItem(OrderListItemHolder holder, String documentId, OrderListItem orderListItem) {
        holder.statusText.setText(Constants.ORDER_OUT);
        holder.orderButton.setText(Constants.LOCKED_TEXT);
        holder.orderButton.setBackgroundColor(context.getResources().getColor(R.color.grey));
        holder.progressBar.setProgressDrawable(context.getDrawable(R.drawable.progress_bar_green));

        setOrderButtonHandler(holder.orderButton, documentId);

        holder.statusText.setTextColor(context.getResources().getColor(R.color.textGreen));
        ViewGroup layout = (ViewGroup) holder.orderButton.getParent();
        if (null != layout) {
            holder.orderButton.setVisibility(VISIBLE);
            holder.infoButton.setVisibility(VISIBLE);
        }
    }

    /**
     * a function that graphics "open" orders
     *
     * @param holder     - the RecyclerView item holder
     * @param documentId
     * @param model      - the orderListItem relevant item
     */
    private void setOpenItem(OrderListItemHolder holder, String documentId, OrderListItem model) {
        holder.orderButton.setText(Constants.JOIN_TEXT);
        holder.orderButton.setBackgroundColor(context.getResources().getColor(R.color.dark_navy));

        setJoinButtonHandler(holder.orderButton, documentId, model);

        holder.statusText.setText(model.getPrice() >= MIN_ORDER ? Constants.READY_TEXT : Constants.WAITING);

        setProgressBar(holder, model);

        holder.statusText.setTextColor(context.getResources().getColor(R.color.textGreen));
        ViewGroup layout = (ViewGroup) holder.orderButton.getParent();
        if (null != layout) {
            holder.orderButton.setVisibility(VISIBLE);
            holder.infoButton.setVisibility(VISIBLE);
        }
    }

    /**
     * Sets the graphic for presenting a canceled order
     *
     * @param holder        - the RecyclerView item holder
     * @param documentId    - String representation of the document ID
     * @param orderListItem order object
     */
    private void setCanceledItem(OrderListItemHolder holder, String documentId, OrderListItem orderListItem) {
        holder.statusText.setText(Constants.ORDER_CANCELED);
        holder.orderButton.setText("התבאס");
        holder.orderButton.setBackgroundColor(context.getResources().getColor(R.color.red));
        holder.progressBar.setProgressDrawable(context.getDrawable(R.drawable.progress_bar_locked));
        ViewGroup layout = (ViewGroup) holder.orderButton.getParent();

        holder.statusText.setTextColor(context.getResources().getColor(R.color.cancelled_order_text));
        if (null != layout) {
//            holder.orderButton.setVisibility(View.GONE);
            holder.infoButton.setVisibility(View.GONE);
        }

        holder.orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bassaAlert();
            }
        });
    }

    /**
     * This function pop-up a sad massage. א
     * helps the user to vent his feelings in case of cancellation
     */
    private void bassaAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setMessage(context.getResources().getString(R.string.sad_emoji_trio))
                .setTitle(context.getResources().getString(R.string.bassa_text))
                .setNeutralButton(context.getResources().getText(R.string.yalla_got_it),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        //set content size
        TextView dialogText = (TextView) alertDialog.findViewById(android.R.id.message);
        dialogText.setTextSize(40);

        alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL).
                setTextColor(context.getResources().getColor(R.color.dark_green));
        alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL).setTypeface(Typeface.DEFAULT, Typeface.BOLD);


        final MediaPlayer mp = MediaPlayer.create(context, Randomizer.randomSadSound());

        mp.start();


        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                mp.stop();
            }
        });
    }


    private void setOrderedItem(OrderListItemHolder holder, String documentId, OrderListItem model) {
        holder.statusText.setText(Constants.ORDERED_TEXT);
        holder.progressBar.setProgressDrawable(context.getDrawable(R.drawable.progress_bar_locked));
        ViewGroup layout = (ViewGroup) holder.orderButton.getParent();

        holder.statusText.setTextColor(context.getResources().getColor(R.color.textGreen));
        if (layout != null) {
            holder.orderButton.setVisibility(View.GONE);
            holder.infoButton.setVisibility(View.GONE);
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
            }, EMPTY_VIEW_DELAY);
        }
    }

    void setEmptyView(View view) {
        this.emptyView = view;
        initEmptyView();
    }


}
