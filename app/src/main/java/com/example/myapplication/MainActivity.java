package com.example.myapplication;

import android.content.Intent;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;


import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements NewOrderDialog.NewOrderDialogListener {


    //yalla

    //fireBase Objects
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference ordersRef = db.collection(Constants.ORDERS);


    //adapters
    private OrderListItemAdapter orderAdapter;

    FloatingActionButton newOrderButt;

    private TextView greeting;
    private TextView beFirst;
    private FirebaseUser user;

    Calendar cal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connectToXML();
        setUpOrdersRecyclerView();

        user = FirebaseAuth.getInstance().getCurrentUser();
        greeting = findViewById(R.id.greeting);
        greeting.setText(
                String.format(getResources().getString(R.string.welcome_str), user.getDisplayName()));

    }

    /**
     * this function is setting up the orders recycler view
     */
    private void setUpOrdersRecyclerView() {
        Query query = ordersRef.orderBy(getString(R.string.price), Query.Direction.DESCENDING);
        FirestoreRecyclerOptions options = new FirestoreRecyclerOptions.Builder<OrderListItem>()
                .setQuery(query, OrderListItem.class)
                .build();
        orderAdapter = new OrderListItemAdapter(options, this.getApplicationContext());
        orderAdapter.setEmptyView(beFirst);
        RecyclerView ordersRecyclerView = findViewById(R.id.orders_recycler_view);
        LinearLayoutManager layout = new LinearLayoutManager(this);
        layout.setOrientation(RecyclerView.VERTICAL);
        ordersRecyclerView.setLayoutManager(layout);
        ordersRecyclerView.setAdapter(orderAdapter);

    }

    /**
     * this function is calling the dialog
     *
     * @param view - the button that his onClick call the function
     */
    public void launchDialog(View view) {
        Dialog myBagDialog = new Dialog(MainActivity.this, R.style.Theme_Dialog);
        myBagDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        myBagDialog.setContentView(R.layout.mybag_dialog);
        myBagDialog.show();
        setUpMyBag(myBagDialog);
    }

    /**
     * this application starts the myBag Recycler View
     *
     * @param myBagDialog parent dialog popup windows
     */
    private void setUpMyBag(Dialog myBagDialog) {
        Query query = db.collectionGroup(getString(R.string.manot_collection))
                .whereEqualTo(getString(R.string.owner_id), user.getUid());


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
        newOrderButt = findViewById(R.id.new_order_button);
        beFirst = findViewById(R.id.be_first);

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

    public void createNewOrder(View view) {
        cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);

        openNewOrderDialog();

    }

    private void startOrderProcedure(Calendar cal) {

        Intent intent = new Intent(MainActivity.this, ManaPickerActivity.class);
        intent.putExtra("CALENDAR", cal);
        intent.putExtra("ref", Randomizer.randomString(18));
        startActivity(intent);

    }

    private void addOrderToServer(Calendar cal) {
        final DocumentReference ordRef = ordersRef.document();
        final OrderListItem order = new OrderListItem(cal);
        ordRef.set(order).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                popToast(true);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                popToast(false);
            }
        });


    }


    private void popToast(boolean success) {
        int msgId = R.string.new_order_success;
        if (success == false) {
            msgId = R.string.new_order_fail;
        }
        Toast.makeText(MainActivity.this, getResources().getString(msgId), Toast.LENGTH_LONG).show();
    }

    public void openNewOrderDialog() {

        NewOrderDialog newOrderDialog = new NewOrderDialog();
        newOrderDialog.show(getSupportFragmentManager(), "order dialog");
    }


    @Override
    public void applyTime(int hour, int minute) {
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        startOrderProcedure(cal);
    }
}


