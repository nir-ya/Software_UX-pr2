package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;


import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private  FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference ordersRef = db.collection("Orders");

    private Button myBagBtn;
    ArrayList<String> mManaType = new ArrayList<>();
    ArrayList<String> mManaPrice = new ArrayList<>();
    ArrayList<String> mmTosafut = new ArrayList<>();


    private OrderListItemAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        Query query = ordersRef.orderBy("orderTime",Query.Direction.DESCENDING);

        FirestoreRecyclerOptions options = new FirestoreRecyclerOptions.Builder<OrderListItem>().setQuery(query,OrderListItem.class)
                .build();
        adapter = new OrderListItemAdapter(options, this.getApplicationContext());

        RecyclerView recyclerView = findViewById(R.id.orders_recycler_view);
        LinearLayoutManager layout =new LinearLayoutManager(this);
        layout.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layout);
        recyclerView.setAdapter(adapter);

    }

    private void setUpRecyclerViewBag() {
        RecyclerView recyclerMyBag = findViewById(R.id.myBagRecyclerView);
        MyBagAdapter myBagAdapter = new MyBagAdapter(mManaType,mManaPrice,mmTosafut,this);
        recyclerMyBag.setAdapter(myBagAdapter);
        recyclerMyBag.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}

