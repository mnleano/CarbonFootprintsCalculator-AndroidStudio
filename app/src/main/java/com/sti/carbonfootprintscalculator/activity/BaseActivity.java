package com.sti.carbonfootprintscalculator.activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by mykelneds on 28/03/2017.
 */

public class BaseActivity extends AppCompatActivity {

    ProgressDialog pDialog;

    public void startProgressDialog(String message) {
        pDialog = new ProgressDialog(this);
        pDialog.setMessage(message);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    public void stopProgressDialog() {
        pDialog.dismiss();
    }
}
