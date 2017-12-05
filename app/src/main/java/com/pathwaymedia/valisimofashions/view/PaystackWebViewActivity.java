package com.pathwaymedia.valisimofashions.view;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.pathwaymedia.valisimofashions.R;


public class PaystackWebViewActivity extends AppCompatActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paystack_web_view);
        setTitle("Paystack Subscription");
        Toast.makeText(this, "Loading paystack page. Please wait..", Toast.LENGTH_LONG).show();
        webView = (WebView)findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        //webView.setWebChromeClient(new WebChromeClient());
        if(getIntent().hasExtra("url")){
            webView.loadUrl(getIntent().getStringExtra("url"));
        }
    }

    @Override
    public void onBackPressed(){
        finish();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.activity_paystack_web_view);

    }

}
