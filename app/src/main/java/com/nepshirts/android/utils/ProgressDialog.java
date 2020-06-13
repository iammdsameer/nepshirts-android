package com.nepshirts.android.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import com.nepshirts.android.R;

public class ProgressDialog {
    private static Activity activity;
    private static AlertDialog dialog;

    public ProgressDialog(Activity activity) {
        this.activity = activity;
    }

    public static void start(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading_dialog, null));
        builder.setCancelable(true);

        dialog = builder.create();
        dialog.show();
    }

    public static void stop() {
        dialog.dismiss();
    }
}
