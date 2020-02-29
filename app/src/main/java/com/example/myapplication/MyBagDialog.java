package com.example.myapplication;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import org.joda.time.DateTime;
import java.util.Date;

public class MyBagDialog extends DialogFragment {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser user;
    private RecyclerView myBagRecView;
    private TextView headerText;
    private Button backBtn;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mybag_dialog,container,false);
        setDialogLayout();
        user = FirebaseAuth.getInstance().getCurrentUser();
        backBtn =  view.findViewById(R.id.backBtn);
        headerText = view.findViewById(R.id.myBagTitle);
        myBagRecView = view.findViewById(R.id.myBagRecyclerView);
        setupRecyclerDialog();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });



        return view;
    }


    public void setDialogLayout() {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        getDialog().setContentView(R.layout.mybag_dialog);
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }


    private void setupRecyclerDialog() {

        DateTime today = new DateTime().withTimeAtStartOfDay();
        DateTime tomorrow = today.plusDays(1).withTimeAtStartOfDay();

        Date tomorrowDate = tomorrow.toDate();
        Date todayDate = today.toDate();

        Query query = db.collectionGroup(getString(R.string.manot_collection))
                .whereEqualTo(getString(R.string.owner_id), user.getUid())
                .whereLessThan("timestamp",tomorrowDate)
                .whereGreaterThan("timestamp", todayDate);

        FirestoreRecyclerOptions options = new FirestoreRecyclerOptions.Builder<Mana>()
                .setQuery(query, Mana.class)
                .build();

        final MyBagAdapter myBagAdapter = new MyBagAdapter(options,this.getContext());

        LinearLayoutManager layout = new LinearLayoutManager(this.getContext());
        myBagRecView.setLayoutManager(layout);
        myBagRecView.setAdapter(myBagAdapter);

        //start listening
        myBagAdapter.startListening();


        getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                myBagAdapter.stopListening();
            }
        });

    }

}
