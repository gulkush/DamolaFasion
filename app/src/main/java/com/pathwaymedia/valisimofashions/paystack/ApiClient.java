package com.pathwaymedia.valisimofashions.paystack;


import android.content.Context;

import com.pathwaymedia.valisimofashions.PeopleApplication;
import com.pathwaymedia.valisimofashions.paystack.service.ApiService;
import com.squareup.okhttp.ConnectionSpec;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.TlsVersion;

import java.io.IOException;
import java.util.Collections;
import java.util.logging.Logger;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.Client;
import retrofit.client.OkClient;

//import retrofit2.RestAdapter;
//import retrofit2.converter.GsonConverter;

/**
 * API Client Class
 * <p>
 * Provides a service by which we can make API calls</p>
 */
public class ApiClient {


    private static final String BASE_URL = "http://valisimofashions.com/api/";

    public static ApiService getService(){
        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestInterceptor.RequestFacade request) {
                //request.addHeader("Authorization", "Bearer sk_live_130f7491713cb3df831825b188a37a833760fbc5");
                //request.addHeader("Authorization", "Bearer sk_test_c7271b1e1b7ad402f26c43e969a3109e8d85493d");
                //request.addHeader("Accept", "application/json");
            }
        };

        /*ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                .tlsVersions(TlsVersion.TLS_1_2)
                .build();*/
        OkHttpClient okHttpClient = new OkHttpClient();
        //okHttpClient.setConnectionSpecs(Collections.singletonList(spec));

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setClient(new OkClient(okHttpClient))
                .setRequestInterceptor(requestInterceptor)
                .build();

        ApiService service = adapter.create(ApiService.class);
        return service;
    }



}
