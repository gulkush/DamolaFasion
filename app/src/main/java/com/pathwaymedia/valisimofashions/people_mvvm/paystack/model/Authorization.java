package com.pathwaymedia.valisimofashions.people_mvvm.paystack.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gulshanbudhwani on 04/02/17.
 */

public class Authorization {

    @SerializedName("domain")
    @Expose
    private String domain;
    @SerializedName("authorization_code")
    @Expose
    private String authorizationCode;
    @SerializedName("bin")
    @Expose
    private Object bin;
    @SerializedName("brand")
    @Expose
    private Object brand;
    @SerializedName("card_type")
    @Expose
    private String cardType;
    @SerializedName("last4")
    @Expose
    private String last4;
    @SerializedName("bank")
    @Expose
    private Object bank;
    @SerializedName("country_code")
    @Expose
    private Object countryCode;
    @SerializedName("country_name")
    @Expose
    private Object countryName;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("mobile")
    @Expose
    private Boolean mobile;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("integration")
    @Expose
    private Integer integration;
    @SerializedName("customer")
    @Expose
    private Integer customer;
    @SerializedName("card")
    @Expose
    private Integer card;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getAuthorizationCode() {
        return authorizationCode;
    }

    public void setAuthorizationCode(String authorizationCode) {
        this.authorizationCode = authorizationCode;
    }

    public Object getBin() {
        return bin;
    }

    public void setBin(Object bin) {
        this.bin = bin;
    }

    public Object getBrand() {
        return brand;
    }

    public void setBrand(Object brand) {
        this.brand = brand;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getLast4() {
        return last4;
    }

    public void setLast4(String last4) {
        this.last4 = last4;
    }

    public Object getBank() {
        return bank;
    }

    public void setBank(Object bank) {
        this.bank = bank;
    }

    public Object getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(Object countryCode) {
        this.countryCode = countryCode;
    }

    public Object getCountryName() {
        return countryName;
    }

    public void setCountryName(Object countryName) {
        this.countryName = countryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getMobile() {
        return mobile;
    }

    public void setMobile(Boolean mobile) {
        this.mobile = mobile;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIntegration() {
        return integration;
    }

    public void setIntegration(Integer integration) {
        this.integration = integration;
    }

    public Integer getCustomer() {
        return customer;
    }

    public void setCustomer(Integer customer) {
        this.customer = customer;
    }

    public Integer getCard() {
        return card;
    }

    public void setCard(Integer card) {
        this.card = card;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}