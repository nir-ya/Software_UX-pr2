package com.example.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.firebase.Timestamp;

import org.joda.time.Instant;

import java.util.Calendar;

/**
 * This class builds the new order dialog fragment
 */
public class NewOrderDialog extends AppCompatDialogFragment {
    private static final int MIN_ORDER_HOUR = 11;
    private static final int MAX_ORDER_TIME = 21;
    private static final long MIN_ORDER_INTERVAL_MS = 600000;
    private int chosenHour, chosenMinute;
    private Button okButton, cancelButton;
    private TextView openingHoursText, orderTimeErrorText;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final View view = setViewToBuilder(builder);

        okButton = view.findViewById(R.id.ok_button);
        cancelButton = view.findViewById(R.id.cancel_button);

        openingHoursText = view.findViewById(R.id.opening_hours_text);
        orderTimeErrorText = view.findViewById(R.id.order_time_error_text);

        final TimePicker timePicker = view.findViewById(R.id.timepicker);
        timePicker.setIs24HourView(true);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int hour, int minute) {
                chosenHour = hour;
                chosenMinute = minute;
                updateOkButtonStatus(hour, minute);
                updateOrderInstructions(hour, minute);
            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applyTime(chosenHour, chosenMinute);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
            }
        });

        return builder.create();
    }

    private void cancel() {
        this.dismiss();
    }

    private void applyTime(int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        Timestamp timestamp = new Timestamp(calendar.getTime());
        startOrderProcedure(timestamp);
    }

    private void startOrderProcedure(Timestamp time) {

        Intent intent = new Intent(getActivity() , ManaPickerActivity.class);
        intent.putExtra(getResources().getString(R.string.calendar_extra_name), time);
        intent.putExtra(getResources().getString(R.string.order_id_extra_name), Randomizer.randomString(18));
        startActivity(intent);
        this.dismiss();
    }


    private void updateOkButtonStatus(int hour, int minute) {
        if (!isValidTime(hour, minute)) {
            okButton.setClickable(false);
            okButton.setTextColor(getResources().getColor(R.color.grey));
            okButton.setBackground(getResources().getDrawable(R.drawable.border_button));
        } else {
            okButton.setClickable(true);
            okButton.setTextColor(getResources().getColor(R.color.lightGrey));
            okButton.setBackground(getResources().getDrawable(R.drawable.full_button));
        }
    }

    private void updateOrderInstructions(int hour, int minute) {
        if (isBeforeNow(hour, minute)) {
            orderTimeErrorText.setText(R.string.time_travel_error);
        } else if (!isIntervalValid(hour, minute)) {
            orderTimeErrorText.setText(getResources().getString(R.string.order_interval_instruction));
        }
        else {
            orderTimeErrorText.setText("");
        }
        if (!isWithinOpeningHours(hour, minute)) {
            openingHoursText.setTextColor(getResources().getColor(R.color.red));
        } else {
            openingHoursText.setTextColor(getResources().getColor(R.color.textGreen));
        }
    }

    private boolean isValidTime(int hour, int minute) {
        return isIntervalValid(hour, minute) && isWithinOpeningHours(hour, minute);
    }

    private boolean isIntervalValid(int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);

        Instant orderTime = new Instant(calendar);

        return orderTime.minus(MIN_ORDER_INTERVAL_MS).isAfterNow();
    }

    private boolean isBeforeNow(int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);

        Instant orderTime = new Instant(calendar);

        return orderTime.isBeforeNow();
    }

    private boolean isWithinOpeningHours(int hour, int minute) {
        return hour >= MIN_ORDER_HOUR && hour < MAX_ORDER_TIME;
    }

    private View setViewToBuilder(AlertDialog.Builder builder) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.new_order_dialog, null);

        builder.setView(view);
        return view;
    }
}



