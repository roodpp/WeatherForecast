package com.shagiev.konstantin.weatherforecast.screen;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.shagiev.konstantin.weatherforecast.R;

public class ForecastActivity extends AppCompatActivity {
    private ProgressDialog mProgressDialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void showProgress() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this, R.style.MyThemeForProgressBar);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage("");
            mProgressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
            mProgressDialog.show();
        } else {
            mProgressDialog.cancel();
            mProgressDialog = new ProgressDialog(this, R.style.MyThemeForProgressBar);
            mProgressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
            mProgressDialog.show();
        }
    }

    public void hideProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }
}
