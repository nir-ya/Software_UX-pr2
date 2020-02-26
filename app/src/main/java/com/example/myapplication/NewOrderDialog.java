package com.example.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;
import androidx.appcompat.app.AppCompatDialogFragment;
import android.content.Context;
import android.widget.Toast;

/**
 * This class builds the new order dialog fragment
 */
public class NewOrderDialog extends AppCompatDialogFragment {
    private TimePicker timePicker;
    private NewOrderDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View view = setViewToBuilder(builder);

        timePicker = view.findViewById(R.id.timepicker);
        timePicker.setIs24HourView(true);

        return builder.create();
    }

    private View setViewToBuilder(AlertDialog.Builder builder) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.new_order_dialog, null);

        builder.setView(view)
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int hour = timePicker.getCurrentHour();
                        int minute = timePicker.getCurrentMinute();
                        if (hour < 11 || hour >= 21 && minute > 0)
                        {
                            Toast.makeText(getContext(), "השליח ישן בשעה הזאת", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            listener.applyTime(hour, minute);
                        }
                    }
                });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (NewOrderDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");
        }
    }

    public interface NewOrderDialogListener {
        void applyTime(int hour, int minute);
    }
}



