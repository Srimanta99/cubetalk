package com.cubetalktest.cubetalk.models.change_password;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChangePasswordRequest {

    @Expose
    @SerializedName("current_password")
    private String mCurrentPassword;

    @Expose
    @SerializedName("new_password")
    private String mNewPassword;

    @Expose
    @SerializedName("confirm_password")
    private String mConfirmPassword;

    public String getCurrentPassword() {
        return mCurrentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.mCurrentPassword = currentPassword;
    }

    public String getNewPassword() {
        return mNewPassword;
    }

    public void setNewPassword(String newPassword) {
        this.mNewPassword = newPassword;
    }

    public String getConfirmPassword() {
        return mConfirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.mConfirmPassword = confirmPassword;
    }
}
