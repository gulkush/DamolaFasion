package com.pathwaymedia.valisimofashions.people_mvvm.paystack.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by gulshanbudhwani on 04/02/17.
 */

public class ListSubscriptionResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<SubscriptionDetail> data = null;


    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<SubscriptionDetail> getData() {
        return data;
    }

    public void setData(List<SubscriptionDetail> data) {
        this.data = data;
    }

}