package com.cubetalktest.cubetalk.models.user_profile_image;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserImageUploadResponseBody {

    @Expose
    @SerializedName("success")
    private boolean mSuccess;

    @Expose
    @SerializedName("message")
    private String mMessage;

    @Expose
    @SerializedName("data")
    private String mData;

    public boolean getSuccess() {
        return mSuccess;
    }

    public void setSuccess(boolean mSuccess) {
        this.mSuccess = mSuccess;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String mMessage) {
        this.mMessage = mMessage;
    }

    public String getData() {
        return mData;
    }

    public void setData(String mData) {
        this.mData = mData;
    }
}
