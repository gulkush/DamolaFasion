package com.pathwaymedia.valisimofashions.paystack.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by gulshanbudhwani on 04/02/17.
 */

public class FetchSubscription {

    @SerializedName("invoices")
    @Expose
    private List<Object> invoices = null;
    @SerializedName("customer")
    @Expose
    private Customer customer;
    @SerializedName("plan")
    @Expose
    private Plan plan;
    @SerializedName("integration")
    @Expose
    private Integer integration;
    @SerializedName("authorization")
    @Expose
    private Authorization authorization;
    @SerializedName("domain")
    @Expose
    private String domain;
    @SerializedName("start")
    @Expose
    private Integer start;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("subscription_code")
    @Expose
    private String subscriptionCode;
    @SerializedName("email_token")
    @Expose
    private String emailToken;
    @SerializedName("easy_cron_id")
    @Expose
    private Object easyCronId;
    @SerializedName("cron_expression")
    @Expose
    private String cronExpression;
    @SerializedName("next_payment_date")
    @Expose
    private String nextPaymentDate;
    @SerializedName("open_invoice")
    @Expose
    private Object openInvoice;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;

    public List<Object> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Object> invoices) {
        this.invoices = invoices;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public Integer getIntegration() {
        return integration;
    }

    public void setIntegration(Integer integration) {
        this.integration = integration;
    }

    public Authorization getAuthorization() {
        return authorization;
    }

    public void setAuthorization(Authorization authorization) {
        this.authorization = authorization;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getSubscriptionCode() {
        return subscriptionCode;
    }

    public void setSubscriptionCode(String subscriptionCode) {
        this.subscriptionCode = subscriptionCode;
    }

    public String getEmailToken() {
        return emailToken;
    }

    public void setEmailToken(String emailToken) {
        this.emailToken = emailToken;
    }

    public Object getEasyCronId() {
        return easyCronId;
    }

    public void setEasyCronId(Object easyCronId) {
        this.easyCronId = easyCronId;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getNextPaymentDate() {
        return nextPaymentDate;
    }

    public void setNextPaymentDate(String nextPaymentDate) {
        this.nextPaymentDate = nextPaymentDate;
    }

    public Object getOpenInvoice() {
        return openInvoice;
    }

    public void setOpenInvoice(Object openInvoice) {
        this.openInvoice = openInvoice;
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