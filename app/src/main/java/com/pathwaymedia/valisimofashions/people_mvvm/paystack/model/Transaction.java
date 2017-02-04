package com.pathwaymedia.valisimofashions.people_mvvm.paystack.model;

/**
 * Created by i on 24/08/2016.
 */
public class Transaction extends PaystackModel {
    public final String reference;

    public Transaction(String reference) {
        this.reference = reference;
    }
}
