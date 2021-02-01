package com.cubetalktest.cubetalk.models.common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Booking implements Serializable {

    @Expose
    @SerializedName("_id")
    private String mId;

    @Expose
    @SerializedName("createdAt")
    private String mCreatedAt;

    @Expose
    @SerializedName("booked_expert_id")
    private Expert mExpert;

    @Expose
    @SerializedName("booking_user_id")
    private User mUser;

    @Expose
    @SerializedName("topic_id")
    private Topic mTopic;

    @Expose
    @SerializedName("slot_type")
    private String mSlotType;

    @Expose
    @SerializedName("slot_date")
    private String mSlotDate;

    @Expose
    @SerializedName("slot_time")
    private String mSlotTime;

    @Expose
    @SerializedName("amount_paid")
    private String amount_paid;

    

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    @Expose
    @SerializedName("payment_mode")
    private String paymentMode;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Expose
    @SerializedName("status")
    private int status;


    public String getAmount_paid() {
        return amount_paid;
    }

    public void setAmount_paid(String amount_paid) {
        this.amount_paid = amount_paid;
    }

    public String getBooked_id() {
        return booked_id;
    }

    public void setBooked_id(String booked_id) {
        this.booked_id = booked_id;
    }

    @Expose
    @SerializedName("booked_id")
    private String booked_id;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }

    public Expert getExpert() {
        return mExpert;
    }

    public void setExpert(Expert expert) {
        mExpert = expert;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }

    public Topic getTopic() {
        return mTopic;
    }

    public void setTopic(Topic topic) {
        mTopic = topic;
    }

    public String getSlotType() {
        return mSlotType;
    }

    public void setSlotType(String slotType) {
        mSlotType = slotType;
    }

    public String getSlotDate() {
        return mSlotDate;
    }

    public void setSlotDate(String slotDate) {
        mSlotDate = slotDate;
    }

    public String getSlotTime() {
        return mSlotTime;
    }

    public void setSlotTime(String slotTime) {
        mSlotTime = slotTime;
    }

}
