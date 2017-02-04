package com.pathwaymedia.valisimofashions.people_mvvm.view;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.facebook.AccessToken;
import com.pathwaymedia.valisimofashions.people_mvvm.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

                AccessToken token;
                token = AccessToken.getCurrentAccessToken();

                if (token == null) {
                    //Means user is not logged in
                    Intent mainIntent = new Intent(SplashActivity.this,LoginActivity.class);
                    startActivity(mainIntent);
                    finish();
                }else{
                    Intent mainIntent = new Intent(SplashActivity.this,DrawerActivity.class);
                    startActivity(mainIntent);
                    finish();
                }
                /* Create an Intent that will start the Menu-Activity. */

            }
        }, 3000);
    }
}
