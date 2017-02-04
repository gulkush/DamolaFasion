package com.pathwaymedia.valisimofashions.people_mvvm.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afrozaar.wp_api_v2_client_android.model.Post;
import com.bumptech.glide.Glide;
import com.pathwaymedia.valisimofashions.people_mvvm.PeopleApplication;
import com.pathwaymedia.valisimofashions.people_mvvm.R;
import com.pathwaymedia.valisimofashions.people_mvvm.adapter.VideoAdapter;

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
        iv_featured = (ImageView)findViewById(R.id.iv_featured);

        current_post = PeopleApplication.current_post;
        List<String> video_urls = current_post.getVideoUrls();
        VideoAdapter adapter = new VideoAdapter(this, video_urls);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        tv_title.setText(current_post.getTitle().getRendered());
        Glide.with(this).load(current_post.getFeaturedImageUrl()).error(R.drawable.abc)
                .into(iv_featured);
    }
}
