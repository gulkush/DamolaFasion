package com.pathwaymedia.valisimofashions.people_mvvm.paystack.service;

import com.pathwaymedia.valisimofashions.people_mvvm.paystack.model.Customer;
import com.pathwaymedia.valisimofashions.people_mvvm.paystack.model.InitTransaction;
import com.pathwaymedia.valisimofashions.people_mvvm.paystack.model.ListSubscriptionResponse;
import com.pathwaymedia.valisimofashions.people_mvvm.paystack.model.PaystackResponse;
import com.pathwaymedia.valisimofashions.people_mvvm.paystack.model.Subscription;

import java.util.HashMap;


import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * ApiService
 */
public interface ApiService {

    @FormUrlEncoded
    @POST("/customer")
    Call<PaystackResponse<Customer>> createCustomer(@FieldMap HashMap<String, String> fields);

    @FormUrlEncoded
    @POST("/subscription")
    Call<PaystackResponse<Subscription>> createSubscription(@FieldMap HashMap<String, String> fields);

    @GET("/subscription")
    Call<ListSubscriptionResponse> listSubscription(@QueryMap HashMap<String, String> fields);

    @FormUrlEncoded
    @POST("/transaction/initialize")
    Call<PaystackResponse<InitTransaction>> initTransaction(@FieldMap HashMap<String, String> fields);

}
