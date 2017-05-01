package com.pathwaymedia.valisimofashions.paystack.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by gulshanbudhwani on 03/02/17.
 */

public class Plan implements Serializable{

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("interval")
    @Expose
    private String interval;
    @SerializedName("integration")
    @Expose
    private Integer integration;
    @SerializedName("domain")
    @Expose
    private String domain;
    @SerializedName("plan_code")
    @Expose
    private String planCode;
    @SerializedName("send_invoices")
    @Expose
    private Boolean sendInvoices;
    @SerializedName("send_sms")
    @Expose
    private Boolean sendSms;
    @SerializedName("hosted_page")
    @Expose
    private Boolean hostedPage;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public Integer getIntegration() {
        return integration;
    }

    public void setIntegration(Integer integration) {
        this.integration = integration;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getPlanCode() {
        return planCode;
    }

    public void setPlanCode(String planCode) {
        this.planCode = planCode;
    }

    public Boolean getSendInvoices() {
        return sendInvoices;
    }

    public void setSendInvoices(Boolean sendInvoices) {
        this.sendInvoices = sendInvoices;
    }

    public Boolean getSendSms() {
        return sendSms;
    }

    public void setSendSms(Boolean sendSms) {
        this.sendSms = sendSms;
    }

    public Boolean getHostedPage() {
        return hostedPage;
    }

    public void setHostedPage(Boolean hostedPage) {
        this.hostedPage = hostedPage;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
