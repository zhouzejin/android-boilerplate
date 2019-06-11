package com.sunny.hybrid;

import android.os.Bundle;
import android.os.Handler;

import com.sunny.hybrid.cordova.HybridCordovaActivity;

import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CordovaWebViewImpl;
import org.apache.cordova.engine.SystemWebView;
import org.apache.cordova.engine.SystemWebViewEngine;

public class HybridActivity extends HybridCordovaActivity {

    private Handler mHandler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.init();
        // enable Cordova apps to be started in the background
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.getBoolean("cdvStartInBackground", false)) {
                moveTaskToBack(true);
            }
            mLaunchUrl = extras.getString("startUrl", "index.html");
        }
        // Load your application
        // Set by <content src="index.html" /> in config.xml
        loadUrl(mLaunchUrl);
    }

    @Override
    protected CordovaWebView createWebView() {
        setContentView(R.layout.activity_hybrid);
        SystemWebView webView = findViewById(R.id.cordova_webview);
        return new CordovaWebViewImpl(new SystemWebViewEngine(webView));
    }
}
