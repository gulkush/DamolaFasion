package com.pathwaymedia.valisimofashions.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.afrozaar.wp_api_v2_client_android.model.Post;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.pathwaymedia.valisimofashions.PeopleApplication;
import com.pathwaymedia.valisimofashions.R;

import java.util.ArrayList;
import java.util.List;

import static com.pathwaymedia.valisimofashions.R.id.cancel_action;
import static com.pathwaymedia.valisimofashions.R.id.webview;


public class PostWebViewActivity extends AppCompatActivity {

    public static final String iframestr = "<iframe frameborder=\"0\" width=\"100%\" height=\"315\"\n" +
            "src=\"https://www.youtube.com/embed/#videoId?modestbranding=1\" allowfullscreen>\n" +
            "</iframe>";
    public static final String imagestr = "<img width=\"100%\" \n" +
            "src=\"#imageUrl\">\n" +
            "</img>";

    WebView webView;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_post_web_view);
        LinearLayout ll = (LinearLayout)findViewById(R.id.upperhalf);
        ll.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        //String data = getIntent().getStringExtra("content_data");
        //Log.d("data", data);
        int index = getIntent().getIntExtra("index", 0);
        webView = (WebView)findViewById(webview);
        webView.getSettings().setJavaScriptEnabled(true);

        //webview.loadDataWithBaseURL("", PeopleApplication.current_post.getContent().getRendered(), "text/html", "UTF-8", "");
        webView.setLongClickable(true);
        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });
        webView.setWebChromeClient(new WebChromeClient());
        WebSettings webSettings = webView.getSettings();
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(false);

        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        webSettings.setLoadWithOverviewMode(true);
        webView.loadDataWithBaseURL("", getContent(index), "text/html", "UTF-8", "");

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                //.addTestDevice("E956F2369AA0914866F39CB49EB755B6")
                .build();
        mAdView.loadAd(adRequest);
    }

    public String getContent(int index) {
        Post post = PeopleApplication.current_post;
        setTitle(post.getTitle().getRendered());
        List<String> videoIds = new ArrayList<>();
        String videoUrl = post.getVideoUrls().get(index);
        String content;
        if(PeopleApplication.illus){
            String videoId = videoUrl;
            content = imagestr.replace("#imageUrl", videoId);
        }else {
            String videoId = videoUrl.substring(videoUrl.lastIndexOf("/")+1);
            content = iframestr.replace("#videoId", videoId);
        }

        /*for(String videourl : post.getVideoUrls()){
            String videoid = videourl.substring(videourl.lastIndexOf("/")+1);
            Log.d("videoID", videoid);
            videoIds.add(videoid);
            content += iframestr.replace("#videoId", videoid);
        }*/

        return content;
    }

    @Override
    public void onPause() {
        super.onPause();


    }

    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    public void onBackPressed(){

        webView.onPause();
        webView.loadUrl("file:///android_asset/nonexistent.html");
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                webView.onPause();
                webView.loadUrl("file:///android_asset/nonexistent.html");
                finish();
                break;
            case R.id.action_zoomin:
                webView.zoomIn();
                break;
            case R.id.action_zoomout:
                webView.zoomOut();
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.postwebviewmenu, menu);
        return true;
    }


}
