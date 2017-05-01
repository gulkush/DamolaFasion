package com.pathwaymedia.valisimofashions.view;

import android.content.pm.PackageManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.pathwaymedia.valisimofashions.R;


public class LoginActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Toast.makeText(this, Profile.getCurrentProfile().getFirstName() + "facebook", Toast.LENGTH_SHORT).show();

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container, FacebookLoginButtonFragment.newInstance("","")).commit();
        if(appInstalledOrNot("com.facebook.katana")){
            Toast.makeText(this, "Facebook already installed.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Please install facebook and login.", Toast.LENGTH_SHORT).show();
        }


    }

    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }

        return false;
    }


}
