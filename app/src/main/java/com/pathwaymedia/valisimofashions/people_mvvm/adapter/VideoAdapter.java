package com.pathwaymedia.valisimofashions.people_mvvm.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.pathwaymedia.valisimofashions.people_mvvm.R;
import com.pathwaymedia.valisimofashions.people_mvvm.view.CustomLightboxActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by gulkush on 04/11/2016.
 */
public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MyViewHolder> implements YouTubeThumbnailView.OnInitializedListener {

    //App app;
    Activity activity;
    List<String> videoIds;
    String base_url="";
    public VideoAdapter(Activity activity, List<String> videoUrls){
        this.activity = activity;
        videoIds = new ArrayList<>();
        for(String videourl : videoUrls){
            String videoid = videourl.substring(videourl.lastIndexOf("/")+1);
            Log.d("videoID", videoid);
            videoIds.add(videoid);
        }
        //app = (App)activity.getApplication();
        //app = (App)activity.getApplication();
        //base_url = "http://image.tmdb.org/t/p/w300";
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_layout, parent, false);

        MyViewHolder entryViewHolder = new MyViewHolder(view);
        return entryViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //Video video = videos.get(position);
        holder.tv_name.setText(videoIds.get(position));

        //Initialise the thumbnail
        holder.iv_thumbnail.setTag(videoIds.get(position));
        holder.iv_thumbnail.initialize(activity.getString(R.string.youtube_api_key), this);
    }

    @Override
    public int getItemCount() {
        return videoIds.size();
    }



    @Override
    public void onInitializationSuccess(YouTubeThumbnailView view, YouTubeThumbnailLoader loader) {
        //mLoaders.put(view, loader);
        loader.setVideo((String) view.getTag());
    }

    @Override
    public void onInitializationFailure(YouTubeThumbnailView thumbnailView, YouTubeInitializationResult errorReason) {
        final String errorMessage = errorReason.toString();
        Toast.makeText(activity, errorMessage, Toast.LENGTH_LONG).show();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv_name;
        YouTubeThumbnailView iv_thumbnail;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_name = (TextView)itemView.findViewById(R.id.tv_name);
            iv_thumbnail = (YouTubeThumbnailView)itemView.findViewById(R.id.iv_thumbnail);
            iv_thumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //activity.startActivity(YouTubeStandalonePlayer.createVideoIntent(activity,
                            //activity.getString(R.string.youtube_api_key), videoIds.get(getAdapterPosition()), 0, true, true));
                    Intent lightboxIntent = new Intent(activity, CustomLightboxActivity.class);
                    lightboxIntent.putExtra(CustomLightboxActivity.KEY_VIDEO_ID, videoIds.get(getAdapterPosition()));
                    activity.startActivity(lightboxIntent);

                }
            });


        }




    }
}
