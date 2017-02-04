package com.pathwaymedia.valisimofashions.people_mvvm.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afrozaar.wp_api_v2_client_android.model.Post;
import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.login.LoginManager;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.pathwaymedia.valisimofashions.people_mvvm.PeopleApplication;
import com.pathwaymedia.valisimofashions.people_mvvm.R;
import com.pathwaymedia.valisimofashions.people_mvvm.adapter.RxBus;

import io.paperdb.Paper;
import rx.Observer;

public class DrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView;
    DrawerLayout drawer;
    private AdView mAdView;
    TextView tv_fbname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        enableFb();



        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("E956F2369AA0914866F39CB49EB755B6").build();
        mAdView.loadAd(adRequest);



        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        setupHeader();
        //addFragment(AllPostsFragment.newInstance("", ""));

        RxBus.getSingleton().asObservable()
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        //Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(Object object) {
                        if(object instanceof Post){
                            //PeopleApplication.current_post = (Post) object;
                            //addFragment(PostWebViewFragment.newInstance("", ""));
                        }
                    }
                    //
                });


    }

    private void enableFb() {
        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken,
                                                       AccessToken currentAccessToken) {
                if (currentAccessToken == null) {
                    //write your code here what to do when user logout
                    startActivity(new Intent(DrawerActivity.this, LoginActivity.class));

                }
            }
        };
        accessTokenTracker.startTracking();



    }


    private void setupHeader() {
        View headerview = navigationView.getHeaderView(0);
        final TextView tv_fbname = (TextView)headerview.findViewById(R.id.tv_fbname);
        final TextView tv_email = (TextView)headerview.findViewById(R.id.tv_email);
        final ImageView iv_profile = (ImageView)headerview.findViewById(R.id.iv_profile);

        String firstName = Paper.book().read("firstName", "Hello");
        String lastName = Paper.book().read("lastName", "Guest");
        String email = Paper.book().read("email", "abc@test.com");
        String gender = Paper.book().read("gender", "male");
        String profilePic = Paper.book().read("profilePic", "null");

        tv_fbname.setText(firstName +" "+ lastName);
        tv_email.setText(email);

        Glide.with(this).load(profilePic).error(R.drawable.abc)
                .into(iv_profile);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_subscription) {
            checkSubscription();

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_logout) {
            LoginManager.getInstance().logOut();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void checkSubscription() {
    }

    public void addFragment(Fragment fragment){

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.content_drawer, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    public void onClickMakeup(View view) {
        addFragment(AllPostsFragment.newInstance("", ""));

    }

    public void onClickFashion(View view) {
        //addFragment(AllPostsFragment.newInstance("", ""));
        Toast.makeText(this, "onClickFashion", Toast.LENGTH_SHORT).show();
    }
}
