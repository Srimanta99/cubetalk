package com.cubetalktest.cubetalk.models.update_user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateUserResponse {

    @Expose
    @SerializedName("message")
    private String mMessage;

    @Expose
    @SerializedName("name")
    private String mName;

    @Expose
    @SerializedName("success")
    private boolean mSuccess;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String mMessage) {
        this.mMessage = mMessage;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public boolean getSuccess() {
        return mSuccess;
    }

    public void setSuccess(boolean mSuccess) {
        this.mSuccess = mSuccess;
    }
}
