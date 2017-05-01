package com.pathwaymedia.valisimofashions.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afrozaar.wp_api_v2_client_android.model.Post;
import com.bumptech.glide.Glide;
import com.pathwaymedia.valisimofashions.PeopleApplication;
import com.pathwaymedia.valisimofashions.R;
import com.pathwaymedia.valisimofashions.view.TutorialDetailActivity;

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
        if(post.getPayplans().get(0)==PeopleApplication.PAID_CODE) {
            holder.tv_free.setVisibility(View.GONE);
        }else{
            holder.tv_free.setVisibility(View.VISIBLE);
        }

        Glide.with(holder.imageView.getContext()).load(post.getFeaturedImageUrl()).error(R.drawable.valicon_331)
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView tv_title;
        TextView tv_free;

        public MyViewHolder(final View itemView) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.imageView);
            tv_title = (TextView)itemView.findViewById(R.id.tv_title);
            tv_free = (TextView)itemView.findViewById(R.id.tv_free);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(!PeopleApplication.get(activity).isOnline()){
                        return;
                    }

                    Post current_post = postList.get(getAdapterPosition());
                    if(current_post.getPayplans().get(0)==PeopleApplication.PAID_CODE){
                        if(!PeopleApplication.subscribed){//if user is not subscribed
                            Toast.makeText(activity, "Please subscribe to view this post", Toast.LENGTH_SHORT).show();
                            new AlertDialog.Builder(activity)
                                    .setTitle("You're Unsubscribed!")
                                    .setMessage("Please subscribe to view this content. Subscribe now?")
                                    .setIcon(R.drawable.valisimo_icon_new_24)
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            // continue with delete
                                            RxBus.getSingleton().send("SUB");
                                        }
                                    })
                                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            // do nothing
                                        }
                                    })
                                    .show();
                            return;
                        }
                    }
                    Intent intent = new Intent(activity, TutorialDetailActivity.class);

                    PeopleApplication.current_post = current_post;

                    activity.startActivity(intent);

                }
            });
        }
    }
}
