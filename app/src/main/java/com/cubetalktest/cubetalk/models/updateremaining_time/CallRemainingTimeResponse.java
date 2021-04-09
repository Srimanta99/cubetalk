package com.cubetalktest.cubetalk.models.updateremaining_time;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CallRemainingTimeResponse {
    public boolean ismSuccess() {
        return mSuccess;
    }

    public void setmSuccess(boolean mSuccess) {
        this.mSuccess = mSuccess;
    }

    public String getmMessage() {
        return mMessage;
    }

    public void setmMessage(String mMessage) {
        this.mMessage = mMessage;
    }

    @Expose
    @SerializedName("success")
    private boolean mSuccess;

    @Expose
    @SerializedName("message")
    private String mMessage;
}
