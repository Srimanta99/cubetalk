package com.cubetalktest.cubetalk.models.get_future_bookings;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FutureBookingRequest {

    @Expose
    @SerializedName("user_id")
    private String mUserId;

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }
}
