package com.pathwaymedia.valisimofashions.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.afrozaar.wp_api_v2_client_android.model.Post;
import com.bumptech.glide.Glide;
import com.pathwaymedia.valisimofashions.PeopleApplication;
import com.pathwaymedia.valisimofashions.R;
import com.pathwaymedia.valisimofashions.adapter.VideoAdapter;

import java.util.List;

public class TutorialDetailActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView tv_title;
    ImageView iv_featured;
    Post current_post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_detail);

        //init views
        recyclerView = (RecyclerView)findViewById(R.id.my_recycler_view);
        tv_title = (TextView)findViewById(R.id.tv_title);
        iv_featured = (ImageView)findViewById(R.id.imageView);

        current_post = PeopleApplication.current_post;
        List<String> video_urls = current_post.getVideoUrls();
        VideoAdapter adapter = new VideoAdapter(this, video_urls, current_post.getFeaturedImageUrl());
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        tv_title.setText(current_post.getTitle().getRendered());
        Glide.with(this).load(current_post.getFeaturedImageUrl()).error(R.drawable.valicon_331)
                .into(iv_featured);
    }
}
