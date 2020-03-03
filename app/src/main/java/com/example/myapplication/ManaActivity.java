package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class ManaActivity extends AppCompatActivity {

    static final int HALF_PITA_POSITION = 0;
    static final int PITA_POSITION = 1;
    static final int LAFA_POSITION = 2;
    static final int HALF_LAFA_POSITION = 3;

    CheckBox humusView;
    CheckBox harifView;
    CheckBox picklesView;
    CheckBox onionView;
    CheckBox tomatoView;
    CheckBox cucumberView;
    CheckBox ambaView;
    CheckBox tahiniView;
    CheckBox chipsView;
    CheckBox eggplantView;
    CheckBox markAll;
    ImageView manaTypeImageVIew;

    CheckBox[] checkBoxes;

    TextView ownerText, dishDescription, clearTextView;

    String manatype;
    String orderTime;
    String orderId;
    Timestamp time;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mana);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.light_salmon));
        }

        mContext = this.getApplicationContext();

        connectToxXML();

        isAllMarkedListener();

        manatype = getIntent().getStringExtra("mana_type");
        orderTime = getIntent().getStringExtra("order_time");
        orderId = getIntent().getStringExtra("order_id");
        time = getIntent().getParcelableExtra("CALENDAR");

        updateTextViews();
    }


    private void updateTextViews() {
        ownerText.setText(getString(R.string.owner_text, user.getDisplayName()));

        switch (manatype) {
            case ManaListItem.HALF_PITA:
                dishDescription.setText(R.string.half_pita_description);
                manaTypeImageVIew.setImageResource(R.drawable.half_pita_full);
                ManaPickerAdapter.setSelectedPosition(HALF_PITA_POSITION);
                break;
            case ManaListItem.PITA:
                dishDescription.setText(R.string.pita_description);
                manaTypeImageVIew.setImageResource(R.drawable.pita_full);
                ManaPickerAdapter.setSelectedPosition(PITA_POSITION);
                break;
            case ManaListItem.LAFA:
                dishDescription.setText(R.string.lafa_description);
                manaTypeImageVIew.setImageResource(R.drawable.lafa_full);
                ManaPickerAdapter.setSelectedPosition(LAFA_POSITION);

                break;
            case ManaListItem.HALF_LAFA:
                dishDescription.setText(R.string.half_lafa_description);
                manaTypeImageVIew.setImageResource(R.drawable.half_lafa_full);
                ManaPickerAdapter.setSelectedPosition(HALF_LAFA_POSITION);

                break;
        }
    }

    private void setTosafot(HashMap tosafot) {
        tosafot.put(getString(R.string.hummus_key), humusView.isChecked());
        tosafot.put(getString(R.string.thina_key), tahiniView.isChecked());
        tosafot.put(getString(R.string.harif_key), harifView.isChecked());
        tosafot.put(getString(R.string.amba_key), ambaView.isChecked());
        tosafot.put(getString(R.string.tomato_key), tomatoView.isChecked());
        tosafot.put(getString(R.string.cucumber_key), cucumberView.isChecked());
        tosafot.put(getString(R.string.onion_key), onionView.isChecked());
        tosafot.put(getString(R.string.pickles_key), picklesView.isChecked());
        tosafot.put(getString(R.string.chips_key), chipsView.isChecked());
        tosafot.put(getString(R.string.eggplant_key), eggplantView.isChecked());
        tosafot.put(getString(R.string.kruv_key), false);
    }


    /***
     * this is a listener that checks if all marked
     * if so, mark checkbox
     */
    private void isAllMarkedListener() {
        markAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    for (CheckBox currentCheckBox : checkBoxes) {
                        currentCheckBox.setChecked(true);
                    }
                }
            }
        });
        for (CheckBox currentCheckBox : checkBoxes) {
            currentCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (!isChecked) {
                        markAll.setChecked(false);
                    }
                }
            });
        }
    }


    /**
     * function to set all XML connections
     */
    private void connectToxXML() {
        ownerText = findViewById(R.id.owner_text);
        dishDescription = findViewById(R.id.dish_description);

        clearTextView = findViewById(R.id.clear_tosafot);

        humusView = findViewById(R.id.humus_image);
        harifView = findViewById(R.id.harif_image);
        picklesView = findViewById(R.id.pickles_image);
        onionView = findViewById(R.id.onion_image);
        tomatoView = findViewById(R.id.tomato_image);
        cucumberView = findViewById(R.id.cucumber_image);
        ambaView = findViewById(R.id.amba_image);
        tahiniView = findViewById(R.id.tahini_image);
        chipsView = findViewById(R.id.chips_image);
        eggplantView = findViewById(R.id.eggplant_image);

        checkBoxes = new CheckBox[]{humusView, harifView, picklesView,
                onionView, tomatoView, cucumberView,
                ambaView, tahiniView, chipsView, eggplantView};

        markAll = findViewById(R.id.mark_all_checkbox);

        manaTypeImageVIew = findViewById(R.id.mana_type_picture);

        setManaTypeSwitchMenu();
    }


    /**
     * this function set the mana type image clickable
     * opens a menu where you can switch your mana type
     */
    private void setManaTypeSwitchMenu() {
        manaTypeImageVIew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(mContext, manaTypeImageVIew);
                MenuInflater inflater = getMenuInflater();
                inflater.inflate(R.menu.mana_type_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.menu_pita:
                                manatype = ManaListItem.PITA;
                                break;
                            case R.id.menu_half_pita:
                                manatype = ManaListItem.HALF_PITA;
                                break;
                            case R.id.menu_lafa:
                                manatype = ManaListItem.LAFA;
                                break;
                            case R.id.menu_half_lafa:
                                manatype = ManaListItem.HALF_LAFA;
                                break;
                            default:
                                return false;
                        }
                        updateTextViews();
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
    }


    /**
     * onCLick method that moves you to next screen
     *
     * @param view - button
     */
    public void moveToConfirm(View view) {

        HashMap<String, Boolean> tosafot = new HashMap<>();
        setTosafot(tosafot);
        Intent intent = new Intent(getApplicationContext(), OrderConfirmationActivity.class);
        intent.putExtra("tosafot", tosafot);
        intent.putExtra("order_id", orderId);
        intent.putExtra("mana_type", manatype);
        intent.putExtra("order_time", orderTime);
        intent.putExtra("CALENDAR", time);
        startActivity(intent);
    }

    /**
     * onClick method to return to main activity
     *
     * @param view - button
     */
    public void cancelOrder(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void clearTosafot(View view) {
        for (CheckBox checkBox : checkBoxes) {
            checkBox.setChecked(false);
        }
        markAll.setChecked(false);
    }
}
