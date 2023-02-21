package com.example.recipe_research;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

class LoadingDialog {
    private AlertDialog dialog;

    private Activity activity;

    public LoadingDialog(Activity activity) {
        this.activity = activity;
    }

    void showLoading() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.alert_dialog, null));
        builder.setCancelable(true);

        dialog = builder.create();
        dialog.show();
    }

    void disMiss() {
        dialog.dismiss();
    }
}
