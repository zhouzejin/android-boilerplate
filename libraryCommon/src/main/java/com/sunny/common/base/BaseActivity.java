package com.sunny.common.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Abstract activity that every other Activity in this application must implement.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
