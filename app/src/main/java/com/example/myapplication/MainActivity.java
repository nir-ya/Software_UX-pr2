package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
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

    private  FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference ordersRef = db.collection(COLLECTION);

    private ImageView bag;
    private RecyclerView myBagRecView;

    ArrayList<String> mManaType = new ArrayList<>();
    ArrayList<String> mManaPrice = new ArrayList<>();
    ArrayList<String> mmTosafut = new ArrayList<>();





    private OrderListItemAdapter orderAdapter;
    private MyBagAdapter bagAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connectToXML();
        setUpRecyclerView();

        addFictiveData();
    }

    private void addFictiveData() {
        mManaType.add("Hi");
        mManaPrice.add("90");
        mmTosafut.add("bye");
        mManaType.add("wow");
        mManaPrice.add("100");
        mmTosafut.add("WOW");
    }


    private void setUpRecyclerView() {
        Query query = ordersRef.orderBy("price",Query.Direction.DESCENDING);

        FirestoreRecyclerOptions options = new FirestoreRecyclerOptions.Builder<OrderListItem>().setQuery(query,OrderListItem.class)
                .build();
        orderAdapter = new OrderListItemAdapter(options, this.getApplicationContext());

        RecyclerView recyclerView = findViewById(R.id.orders_recycler_view);
        LinearLayoutManager layout =new LinearLayoutManager(this);
        layout.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layout);
        recyclerView.setAdapter(orderAdapter);

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

    public void callDialog(View view) {
        Dialog myBagDialog = new Dialog(MainActivity.this);
        myBagDialog.setTitle("ההזמנה שלי");

        myBagDialog.setContentView(R.layout.mybag_dialog);
        myBagRecView = myBagDialog.findViewById(R.id.myBagRecyclerView);
        myBagRecView.setAdapter(bagAdapter);
        bagAdapter = new MyBagAdapter(mManaType,mManaPrice,mmTosafut,this);
        myBagRecView.setAdapter(bagAdapter);
        myBagRecView.setLayoutManager(new LinearLayoutManager(this));

        myBagDialog.show();
    }

    private void connectToXML() {
        bag = findViewById(R.id.bag);
    }
}

