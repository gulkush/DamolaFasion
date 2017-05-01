package com.pathwaymedia.valisimofashions.view;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.TextView;
import android.widget.Toast;

import com.afrozaar.wp_api_v2_client_android.model.Post;
import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.login.LoginManager;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.messaging.FirebaseMessaging;
import com.pathwaymedia.valisimofashions.PeopleApplication;
import com.pathwaymedia.valisimofashions.R;
import com.pathwaymedia.valisimofashions.adapter.RxBus;
import com.pathwaymedia.valisimofashions.paystack.ApiClient;
import com.pathwaymedia.valisimofashions.paystack.model.Customer;
import com.pathwaymedia.valisimofashions.paystack.model.GetSubscriptionResponse;
import com.pathwaymedia.valisimofashions.paystack.model.ListSubscriptionResponse;
import com.pathwaymedia.valisimofashions.paystack.model.PaystackResponse;
import com.pathwaymedia.valisimofashions.paystack.model.SubscriptionDetail;
import com.pathwaymedia.valisimofashions.paystack.service.ApiService;

import java.util.HashMap;
import java.util.List;

import io.paperdb.Paper;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import rx.Observer;

public class DrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView;
    DrawerLayout drawer;
    private AdView mAdView;
    TextView tv_fbname;
    private boolean exit = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        //Toast.makeText(this, "Subscribing to topic", Toast.LENGTH_SHORT).show();
        FirebaseMessaging.getInstance().subscribeToTopic("news");


        setTitle("Valisimo");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        enableFb();
        String email = Paper.book().read("email", null);





        checkSubscription();


        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                //.addTestDevice("E956F2369AA0914866F39CB49EB755B6")
                .build();
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
        setupRXBus();





    }

    private void setupRXBus() {
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
                        if(object instanceof String){
                            String objectStr = (String) object;
                            if(objectStr.equalsIgnoreCase("SUB")) {
                                //addFragment(PostWebViewFragment.newInstance("", ""));
                                addFragment(SubscriptionFragment.newInstance("", ""));
                                setTitle("Subscription");
                            }
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
        if(PeopleApplication.subscribed){
            tv_email.setTextColor(getResources().getColor(android.R.color.holo_green_light));
        }

        Glide.with(this).load(profilePic).error(R.drawable.valicon_331)
                .into(iv_profile);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (getSupportFragmentManager().getBackStackEntryCount() == 0){
                if(exit){
                    super.onBackPressed();
                    return;
                }
                this.exit = true;
                Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        exit = false;
                    }
                }, 2000);
            }else{
                super.onBackPressed();
            }

        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_fashion) {
            // Handle the camera action
            onClickFashion(null);
        } else if (id == R.id.nav_makeup) {
            onClickMakeup(null);
        } else if (id == R.id.nav_illus) {
            onClickIllustrations(null);
        }else if (id == R.id.nav_subscription) {
            onClickSubscription(null);
        } else if (id == R.id.nav_rateus) {
            goToPlayStore();

        } else if (id == R.id.nav_share) {
            shareThisApp();

        } else if( id == R.id.nav_feedback){
            sendFeedback();
        }
        else if (id == R.id.nav_logout) {
            Paper.book().write("subscribed", false); // Primitive
            Paper.book().write("dontCheck", 0); // Primitive
            LoginManager.getInstance().logOut();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    private void sendFeedback() {
        Intent Email = new Intent(Intent.ACTION_SEND);
        Email.setType("text/email");
        Email.putExtra(Intent.EXTRA_EMAIL, new String[] { "mobile@valisimofashions.com" });
        Email.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
        //Email.putExtra(Intent.EXTRA_TEXT, "Dear ...," + "");
        startActivity(Intent.createChooser(Email, "Send Feedback:"));
    }

    private void shareThisApp() {
        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "Valismo Application");
            String sAux = "I have enjoyed using the Valisimo mobile app to learn a lot about Fashion.\n" +
                    "\n" +
                    "You need to see it for yourself to understand.\n" +
                    "\n" +
                    "Just install and thank me later.\n" +
                    "\n" +
                    "https://goo.gl/0bH9lO";
            //sAux = sAux + "https://play.google.com/store/apps/details?id="+getApplicationContext().getPackageName()+" \n\n";
            //sAux = sAux + "https://goo.gl/0bH9lO";
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            startActivity(Intent.createChooser(i, "Choose one"));
        } catch(Exception e) {
            //e.toString();
        }
    }

    private void goToPlayStore() {
        //Uri uri = Uri.parse("market://details?id=" + getApplicationContext().getPackageName());
        Uri uri = Uri.parse("https://goo.gl/0bH9lO");
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    uri));
        }
    }


    public void addFragment(Fragment fragment){

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.content_drawer, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    public void onClickMakeup(View view) {
        if(!PeopleApplication.get(this).isOnline()){
            return;
        }
        setTitle("Makeup");
        PeopleApplication.illus = false;
        addFragment(AllPostsFragment.newInstance(String.valueOf(PeopleApplication.MAKEUP_CODE), true));

    }

    public void onClickFashion(View view) {
        if(!PeopleApplication.get(this).isOnline()){
            return;
        }
        //addFragment(AllPostsFragment.newInstance("", ""));
        setTitle("Fashion");
        PeopleApplication.illus = false;
        addFragment(AllPostsFragment.newInstance(String.valueOf(PeopleApplication.FASHION_CODE), true));
    }

    public void onClickIllustrations(View view) {
        if(!PeopleApplication.get(this).isOnline()){
            return;
        }
        //addFragment(AllPostsFragment.newInstance("", ""));
        setTitle("Illustrations");
        PeopleApplication.illus = true;
        addFragment(AllPostsFragment.newInstance(String.valueOf(PeopleApplication.ILLUSTRATIONS_CODE), false));
    }

    public void onClickSubscription(View view) {
        if(PeopleApplication.get(this).isOnline()){
            addFragment(SubscriptionFragment.newInstance("", ""));
            setTitle("Subscription");
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        try{
            Intent intent = getIntent();
            String action = intent.getAction();
            Uri data = intent.getData();
            if(data.getScheme().toLowerCase().equals("valisimo") && data.getHost().toLowerCase().equals("subscription")){
                if(PeopleApplication.get(this).isOnline()){
                    addFragment(SubscriptionFragment.newInstance("", ""));
                    setTitle("Subscription");
                }
            }
        }catch (Exception e){

        }
    }

    @Override
    public void onNewIntent(Intent intent){
        super.onNewIntent(intent);
        setIntent(intent);

        //Toast.makeText(this, "New Intent", Toast.LENGTH_SHORT).show();
    }

    private void checkSubscription() {

        if(!PeopleApplication.get(this).isOnline()){
            return;
        }

        PeopleApplication.subscribed = Paper.book().read("subscribed", false);
        int dontCheck = Paper.book().read("dontCheck", 0);
        Paper.book().write("dontCheck", ((dontCheck+1)%10)); // Primitive

        if(dontCheck !=0 && PeopleApplication.subscribed){
            return;
        }



        try {
            String email = Paper.book().read("email", null);
            //Toast.makeText(getActivity(), "checking Subscription for "+ email, Toast.LENGTH_SHORT).show();
            Log.d("Check Subs", email);
            //email = "abc@softkoki.com";
            //create or fetch customer
            ApiService apiService = ApiClient.getService();
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("email", email);
            apiService.checkSubscription(params, new Callback<GetSubscriptionResponse>() {
                @Override
                public void success(GetSubscriptionResponse getSubscriptionResponse, Response response) {
                    if(getSubscriptionResponse.getStatus().equalsIgnoreCase("active")){
                        PeopleApplication.subscribed = true;
                        Paper.book().write("subscribed", true);
                    }else{
                        PeopleApplication.subscribed = false;
                        Paper.book().write("subscribed", false);
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    //Toast.makeText(DrawerActivity.this, "Error fetching subscription. Please try again after sometime.", Toast.LENGTH_SHORT).show();
                }
            });



        } catch (Exception e) {
            e.printStackTrace();
        }

    }




}
