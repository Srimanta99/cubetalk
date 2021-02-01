package com.cubetalktest.cubetalk.models.reschedule_booking;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RescheduleBooking {
    public String getBookedid() {
        return bookedid;
    }

    public void setBookedid(String bookedid) {
        this.bookedid = bookedid;
    }

    public String getBookinguserId() {
        return bookinguserId;
    }

    public void setBookinguserId(String bookinguserId) {
        this.bookinguserId = bookinguserId;
    }

    public String getBookedexpertid() {
        return bookedexpertid;
    }

    public void setBookedexpertid(String bookedexpertid) {
        this.bookedexpertid = bookedexpertid;
    }

    public String getSlotTime() {
        return slotTime;
    }

    public void setSlotTime(String slotTime) {
        this.slotTime = slotTime;
    }

    public String getSlotDate() {
        return slotDate;
    }

    public void setSlotDate(String slotDate) {
        this.slotDate = slotDate;
    }

    public String getSlotType() {
        return slotType;
    }

    public void setSlotType(String slotType) {
        this.slotType = slotType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(String amountPaid) {
        this.amountPaid = amountPaid;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    @Expose
    @SerializedName("booked_id")
    private String bookedid;

    @Expose
    @SerializedName("booking_user_id")
    private String bookinguserId;

    @Expose
    @SerializedName("booked_expert_id")
    private String bookedexpertid;

    @Expose
    @SerializedName("slot_time")
    private String slotTime;

    @Expose
    @SerializedName("slot_date")
    private String slotDate;

    @Expose
    @SerializedName("slot_type")
    private String slotType;

    @Expose
    @SerializedName("user_name")
    private String userName;

    @Expose
    @SerializedName("user_email")
    private String useremail;

    @Expose
    @SerializedName("user_phone")
    private String userPhone;

    @Expose
    @SerializedName("payment_mode")
    private String paymentMode;

    @Expose
    @SerializedName("amount_paid")
    private String amountPaid;

    @Expose
    @SerializedName("payment_status")
    private String paymentStatus;
}
