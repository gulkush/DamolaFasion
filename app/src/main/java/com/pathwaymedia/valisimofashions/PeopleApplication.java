/**
 * Copyright 2016 Erik Jhordan Rey.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pathwaymedia.valisimofashions;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.afrozaar.wp_api_v2_client_android.model.Post;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import io.paperdb.Paper;
import rx.Scheduler;
import rx.schedulers.Schedulers;

public class PeopleApplication extends Application {

public static boolean subscribed = false;
private Scheduler scheduler;
public static Post current_post;
public static final int MAKEUP_CODE = 69;
public static final int FASHION_ADVANCED_CODE = 130;
public static final int FASHION_BEGINNER_CODE = 128;
public static final int FASHION_INTERMEDIATE_CODE = 129;

public static final int PAID_CODE = 72;
public static final int FREE_CODE = 73;
public static final int ILLUSTRATIONS_CODE = 113;
public static boolean illus = false;

  @Override
  public void onCreate() {
    super.onCreate();
    //Toast.makeText(this, "Application create", Toast.LENGTH_SHORT).show();
    FacebookSdk.sdkInitialize(getApplicationContext());
    //FacebookSdk.sdkInitialize(getApplicationContext());
    AppEventsLogger.activateApp(this);
    //PaystackSdk.initialize(getApplicationContext());
    //PaystackSdk.setPublicKey(getString(R.string.paystack_public_key));
    Paper.init(this);
      checkSubscription();



  }

    private void checkSubscription() {
    }

    public static PeopleApplication get(Context context) {
    return (PeopleApplication) context.getApplicationContext();
  }

  public static PeopleApplication create(Context context) {
    return PeopleApplication.get(context);
  }


  public boolean isOnline() {
    ConnectivityManager cm =
            (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo netInfo = cm.getActiveNetworkInfo();
    boolean connected = netInfo != null && netInfo.isConnectedOrConnecting();
    if(!connected){
      Toast.makeText(getApplicationContext(), "Oops! Seems you might be offline. Check your network connection.", Toast.LENGTH_SHORT).show();
    }
    return connected;
  }


}
