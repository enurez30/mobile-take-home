package com.straus.airports.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Button;

import com.straus.airports.R;

public class DialogUtils {

    private static Listener mListener;

    public static void showErrorDialog(Context context, StringBuilder message) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder
                    .setTitle(R.string.dialog_notice)
                    .setMessage(message)
                    .setCancelable(false)
                    .setPositiveButton(R.string.dialog_ok, null);
            final AlertDialog dialog = builder.create();
            dialog.show();
            Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            positiveButton.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void showExitDialog(Context context, Listener listener) {
        try {
            mListener = listener;
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder
                    .setTitle(R.string.dialog_exit)
                    .setMessage(R.string.dialog_exit_message)
                    .setCancelable(false)
                    .setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (mListener != null) {
                                mListener.onPositiveActionSelected();
                            }
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (mListener != null) {
                                mListener.onNegativeActionSelected();
                            }
                            dialog.dismiss();
                        }
                    });
            final AlertDialog dialog = builder.create();
            dialog.show();
            Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
            positiveButton.setTextColor(context.getResources().getColor(R.color.red));
            negativeButton.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static  void showTripErrorDialog(Context context) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder
                    .setTitle(R.string.dialog_trip_error_title)
                    .setMessage(R.string.dialog_trip_error)
                    .setCancelable(false)
                    .setPositiveButton(R.string.dialog_ok, null);
            final AlertDialog dialog = builder.create();
            dialog.show();
            Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            positiveButton.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
