package com.pathwaymedia.valisimofashions.paystack.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gulshanbudhwani on 21/03/17.
 */

public class GetSubscriptionResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("start_date")
    @Expose
    private String start_date;
    @SerializedName("expiry_date")
    @Expose
    private String expiry_date;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(String expiry_date) {
        this.expiry_date = expiry_date;
    }
}
