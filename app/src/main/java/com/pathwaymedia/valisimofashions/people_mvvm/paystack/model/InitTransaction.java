package com.pathwaymedia.valisimofashions.people_mvvm.paystack.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gulshanbudhwani on 04/02/17.
 */

public class InitTransaction {

    @SerializedName("authorization_url")
    @Expose
    private String authorizationUrl;
    @SerializedName("access_code")
    @Expose
    private String accessCode;
    @SerializedName("reference")
    @Expose
    private String reference;

    public String getAuthorizationUrl() {
        return authorizationUrl;
    }

    public void setAuthorizationUrl(String authorizationUrl) {
        this.authorizationUrl = authorizationUrl;
    }

    public String getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

}
