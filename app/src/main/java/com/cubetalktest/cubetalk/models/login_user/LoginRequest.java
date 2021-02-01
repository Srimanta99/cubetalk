package com.cubetalktest.cubetalk.models.login_user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginRequest {

    @Expose
    @SerializedName("password")
    private String mPassword;

    @Expose
    @SerializedName("email")
    private String mEmail;

    @Expose
    @SerializedName("device_token")
    private String mDeviceToken;

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        this.mPassword = password;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        this.mEmail = email;
    }

    public String getDeviceToken() {
        return mDeviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.mDeviceToken = deviceToken;
    }
}
