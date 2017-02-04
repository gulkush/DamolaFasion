package com.pathwaymedia.valisimofashions.people_mvvm.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.afrozaar.wp_api_v2_client_android.model.Post;
import com.bumptech.glide.Glide;
import com.pathwaymedia.valisimofashions.people_mvvm.PeopleApplication;
import com.pathwaymedia.valisimofashions.people_mvvm.R;
import com.pathwaymedia.valisimofashions.people_mvvm.view.PostWebViewActivity;
import com.pathwaymedia.valisimofashions.people_mvvm.view.TutorialDetailActivity;

import java.util.List;

/**
 * Created by gulshanbudhwani on 16/01/17.
 */

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.MyViewHolder> {

    List<Post> postList;
    Activity activity;

    public PostsAdapter(List<Post> postList, Activity activity){
        this.postList = postList;
        this.activity = activity;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_post_layout, parent, false);
        MyViewHolder mvh = new MyViewHolder(view);
        return mvh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Post post = postList.get(position);
        holder.tv_title.setText(post.getTitle().getRendered());
        //String imgurl = "https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcQimF5gsnL4G5ryB61f1yklONy9TZznLzB4WiNUlCQJErin0xEj";
        /*if(post.getBetterFeaturedMedia()!=null){
            Log.d("featured_url "+position, post.getBetterFeaturedMedia().getSourceUrl());
            imgurl = post.getBetterFeaturedMedia().getSourceUrl();
            //holder.imageView.getLayoutParams().height = (post.getBetterFeaturedMedia().getMediaDetails().getHeight()/post.getBetterFeaturedMedia().getMediaDetails().getWidth())*holder.imageView.getLayoutParams().width;

        }*/
        Glide.with(holder.imageView.getContext()).load(post.getFeaturedImageUrl()).error(R.drawable.abc)
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView tv_title;

        public MyViewHolder(final View itemView) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.imageView);
            tv_title = (TextView)itemView.findViewById(R.id.tv_title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Intent intent = new Intent(activity, TutorialDetailActivity.class);
                    //String data = postList.get(position).getContent().getRendered();
                    //data = (data!=null && data.length()>0)?data:"Hello World";
                    //intent.putExtra("content_data", data);
                    PeopleApplication.current_post = postList.get(position);
                    activity.startActivity(intent);

                }
            });
        }
    }
}
