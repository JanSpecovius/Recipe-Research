package com.example.recipe_research;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

class LoadingDialog {
    private AlertDialog dialog;
    private final Activity activity;

    public LoadingDialog(Activity activity) {
        this.activity = activity;
    }

    /*  Creates a new ContentView for the alert_dialog layout,
        sets the view to the builder,
        sets the dialog to be cancelable and shows the dialog to the user
    */
    @SuppressLint("InflateParams")
    void showLoading() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.alert_dialog, null));
        builder.setCancelable(true);

        dialog = builder.create();
        dialog.show();
    }

    // Dismisses the dialog from the user view when called
    void disMiss() {
        dialog.dismiss();
    }
}