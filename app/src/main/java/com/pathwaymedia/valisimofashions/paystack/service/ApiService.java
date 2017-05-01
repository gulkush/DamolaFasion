package com.pathwaymedia.valisimofashions.paystack.service;

import com.pathwaymedia.valisimofashions.paystack.model.Customer;
import com.pathwaymedia.valisimofashions.paystack.model.GetSubscriptionResponse;
import com.pathwaymedia.valisimofashions.paystack.model.InitTransaction;
import com.pathwaymedia.valisimofashions.paystack.model.ListSubscriptionResponse;
import com.pathwaymedia.valisimofashions.paystack.model.PaystackResponse;
import com.pathwaymedia.valisimofashions.paystack.model.Subscription;

import java.util.HashMap;


import retrofit.Callback;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.QueryMap;

/**
 * ApiService
 */
public interface ApiService {

    /*@FormUrlEncoded
    @POST("/customer")
    void createCustomer(@FieldMap HashMap<String, String> fields, Callback<PaystackResponse<Customer>> callback);

    @FormUrlEncoded
    @POST("/subscription")
    void createSubscription(@FieldMap HashMap<String, String> fields, Callback<PaystackResponse<Subscription>> callback);

    @GET("/subscription")
    void listSubscription(@QueryMap HashMap<String, String> fields, Callback<ListSubscriptionResponse> callback);

    @FormUrlEncoded
    @POST("/transaction/initialize")
    void initTransaction(@FieldMap HashMap<String, String> fields, Callback<PaystackResponse<InitTransaction>> callback);*/

    @GET("/check")
    void checkSubscription(@QueryMap HashMap<String, String> fields, Callback<GetSubscriptionResponse> callback);

}
