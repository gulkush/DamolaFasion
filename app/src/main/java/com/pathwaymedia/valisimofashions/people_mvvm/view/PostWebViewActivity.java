package com.pathwaymedia.valisimofashions.people_mvvm.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.pathwaymedia.valisimofashions.people_mvvm.R;

public class PostWebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_web_view);
        //String data = getIntent().getStringExtra("content_data");
        //Log.d("data", data);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.activity_post_web_view, PostWebViewFragment.newInstance("", ""))
                .commit();
    }
}
