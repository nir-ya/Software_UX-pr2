package com.example.myapplication;

import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.wooplr.spotlight.SpotlightConfig;
import com.wooplr.spotlight.utils.SpotlightSequence;

import org.joda.time.DateTime;

import java.util.Date;


public class MainActivity extends AppCompatActivity {


    //fireBase Objects
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference ordersRef = db.collection(getString(R.string.orders_db_collection));


    //adapters
    private OrderListItemAdapter orderAdapter;
    FloatingActionButton newOrderButton;
    ImageView myBagBtn;
    private TextView beFirstText;
    private FirebaseUser user;

    private RecyclerView ordersRecyclerView;

    static final long TOOLTIP_DELAY = 3000;
    static final String REC_USAGE_ID = "rec_intro";
    static final String FAB_USAGE_ID = "fab_intro";
    static final String BAG_USAGE_ID = "bag_intro";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.backgroundWhite));
        }

        connectToXML();
        setupOrdersRecyclerView();

        user = FirebaseAuth.getInstance().getCurrentUser();
        TextView greeting = findViewById(R.id.greeting);
        greeting.setText(
                String.format(getResources().getString(R.string.welcome_str), user.getDisplayName()));

        initializeTooltip();
    }


    /**
     * this function is setting up the orders recycler view
     */
    private void setupOrdersRecyclerView() {
        DateTime today = new DateTime().withTimeAtStartOfDay();
        DateTime tomorrow = today.plusDays(1).withTimeAtStartOfDay();

        Date tomorrowDate = tomorrow.toDate();
        Date todayDate = today.toDate();

        Query query = ordersRef.orderBy("timestamp", Query.Direction.ASCENDING).whereLessThan("timestamp", tomorrowDate)
                .whereGreaterThan("timestamp", todayDate);
        FirestoreRecyclerOptions options = new FirestoreRecyclerOptions.Builder<OrderListItem>()
                .setQuery(query, OrderListItem.class)
                .build();
        orderAdapter = new OrderListItemAdapter(options, this);
        orderAdapter.setEmptyView(beFirstText);
        ordersRecyclerView = findViewById(R.id.orders_recycler_view);
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
    public void launchMyBagDialog(View view) {
        MyBagDialog myBagDialog = new MyBagDialog();
        myBagDialog.show(getSupportFragmentManager(), "my bag dialog");
    }

    /**
     * connect views to xml shapes
     */
    private void connectToXML() {
        //views
        myBagBtn = findViewById(R.id.bag);
        newOrderButton = findViewById(R.id.new_order_button);
        beFirstText = findViewById(R.id.be_first);

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

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void popToast(boolean success) {
        int msgId = R.string.new_order_success;
        if (!success) {
            msgId = R.string.new_order_fail;
        }
        Toast.makeText(MainActivity.this, getResources().getString(msgId), Toast.LENGTH_LONG).show();
    }

    public void openNewOrderDialog(View view) {

        NewOrderDialog newOrderDialog = new NewOrderDialog();
        newOrderDialog.show(getSupportFragmentManager(), "order dialog");
    }


    /**
     * this function craete tooltip, if user first using the app
     */
    private void initializeTooltip() {

        //create a spotlight configuration
        final SpotlightConfig config = getSpotlightConfig();

        //handler is for the spotlight delay - 3 seconds from app onCreate
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                //create spotlight sequence
                runSpotlightSequence();
            }

            /**
             * this function create and run the spotlights sequence.
             */
            private void runSpotlightSequence() {
                SpotlightSequence.getInstance(MainActivity.this, config)
                        .addSpotlight(ordersRecyclerView, getString(R.string.join_order_tooltop),
                                getString(R.string.join_tooltip_subtext), REC_USAGE_ID)
                        .addSpotlight(newOrderButton, getString(R.string.create_order_tooltip)
                                , getString(R.string.create_tooltip_subtext), FAB_USAGE_ID)
                        .addSpotlight(myBagBtn, getString(R.string.mybag_tooltip)
                                , getString(R.string.mybag_tooltip_subtext), BAG_USAGE_ID)
                        .startSequence();
            }
        }, TOOLTIP_DELAY);
    }

    /**
     * this function creates a spotlight configuration
     * configuration sets the colors, animations, etc. of the spotlight
     *
     * @return SpotlightConfiguration object
     */
    private SpotlightConfig getSpotlightConfig() {

        final SpotlightConfig config = new SpotlightConfig();
        config.setIntroAnimationDuration(500);
        config.setRevealAnimationEnabled(true);
        config.setPerformClick(true);
        config.setFadingTextDuration(500);
        config.setHeadingTvColor(Color.parseColor("#ffffff"));
        config.setHeadingTvSize(32);
        config.setSubHeadingTvColor(Color.parseColor("#ffffff"));
        config.setSubHeadingTvSize(16);
        config.setMaskColor(Color.parseColor("#dc6faf9f"));
        config.setLineAnimationDuration(500);
        config.setLineAndArcColor(Color.parseColor("#f47d5c"));
        config.setDismissOnBackpress(true);
        config.setDismissOnTouch(true);
        return config;
    }
}






