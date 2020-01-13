package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;


import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    static final String COLLECTION = "OpenOrders";

    //fireBase Objects
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference ordersRef = db.collection(COLLECTION);

    //adapters
    private OrderListItemAdapter orderAdapter;
    private MyBagAdapter bagAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connectToXML();
        setUpOrdersRecyclerView();

    }

    /**
     * this function is setting up the orders recycler view
     */
    private void setUpOrdersRecyclerView() {
        Query query = ordersRef.orderBy("price", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions options = new FirestoreRecyclerOptions.Builder<OrderListItem>().setQuery(query, OrderListItem.class)
                .build();
        orderAdapter = new OrderListItemAdapter(options, this.getApplicationContext());

        RecyclerView ordersRecyclerView = findViewById(R.id.orders_recycler_view);
        LinearLayoutManager layout = new LinearLayoutManager(this);
        layout.setOrientation(RecyclerView.VERTICAL);
        ordersRecyclerView.setLayoutManager(layout);
        ordersRecyclerView.setAdapter(orderAdapter);

    }

    /**
     * this function is calling the dialog
     * @param view - the button that his onClick call the function
     */
    public void callDialog(View view) {
        Dialog myBagDialog = new Dialog(MainActivity.this);
        myBagDialog.setTitle("ההזמנה שלי");
        myBagDialog.setContentView(R.layout.mybag_dialog);
        myBagDialog.show();

        setUpMyBag(myBagDialog);

    }

    /**
     * this application starts the myBag Recycler View
     * @param myBagDialog parent dialog popup windows
     */
    private void setUpMyBag(Dialog myBagDialog) {
        Query query = db.collectionGroup("Manot").whereEqualTo("owner","luli");

        FirestoreRecyclerOptions options = new FirestoreRecyclerOptions.Builder<Mana>()
                .setQuery(query, Mana.class)
                .build();

        final MyBagAdapter myBagAdapter = new MyBagAdapter(options, this);
        RecyclerView myBagRecView = myBagDialog.findViewById(R.id.myBagRecyclerView);
        LinearLayoutManager layout = new LinearLayoutManager(this.getApplicationContext());
        myBagRecView.setLayoutManager(layout);
        myBagRecView.setAdapter(myBagAdapter);
        //start listening
        myBagAdapter.startListening();

        myBagDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                myBagAdapter.stopListening();
            }
        });
    }

    /**
     * connect views to xml shapes
     */
    private void connectToXML() {
        //views
        ImageView bag = findViewById(R.id.bag);
    }

    @Override
    protected void onStart() {
        super.onStart();
        orderAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        orderAdapter.stopListening();
    }
}

