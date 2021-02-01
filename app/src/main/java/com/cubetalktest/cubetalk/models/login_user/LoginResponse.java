package com.cubetalktest.cubetalk.models.login_user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import com.cubetalktest.cubetalk.enums.ExpertApplicationStatus;

public class LoginResponse {

    @Expose
    @SerializedName("success")
    private boolean mSuccess;

    @Expose
    @SerializedName("message")
    private String mMessage;

    @Expose
    @SerializedName("token")
    private String mToken;

    @Expose
    @SerializedName("name")
    private String mName;

    @Expose
    @SerializedName("is_expert")
    private boolean mIsExpert;

    @Expose
    @SerializedName("id")
    private String mId;

    @Expose
    @SerializedName("email")
    private String mEmail;

    @Expose
    @SerializedName("profileimage")
    private String mProfileImage;

    @Expose
    @SerializedName("is_expert_applied")
    private boolean mIsExpertApplied;

    @Expose
    @SerializedName("expert_application_status")
    private ExpertApplicationStatus mExpertApplicationStatus;

    @Expose
    @SerializedName("is_expert_active")
    private boolean mIsExpertActive;

    @Expose
    @SerializedName("phone_number")
    private String mPhoneNumber;

    @Expose
    @SerializedName("country_id")
    private int mCountryId;


    @Expose
    @SerializedName("radius")
    private int mRadius;

    @Expose
    @SerializedName("mesibo_id")
    private String mesibo_id;

    public String getMesibo_id() {
        return mesibo_id;
    }

    public void setMesibo_id(String mesibo_id) {
        this.mesibo_id = mesibo_id;
    }

    public String getMesibo_token() {
        return mesibo_token;
    }

    public void setMesibo_token(String mesibo_token) {
        this.mesibo_token = mesibo_token;
    }

    @Expose
    @SerializedName("mesibo_token")
    private String mesibo_token;


    public int getmCountryId() {
        return mCountryId;
    }

    public void setmCountryId(int mCountryId) {
        this.mCountryId = mCountryId;
    }



    public int getRadius() {
        return mRadius;
    }

    public void setRadius(int radius) {
        this.mRadius = radius;
    }

    public boolean isExpertActive() {
        return mIsExpertActive;
    }

    public void setExpertActive(boolean isExpertActive) {
        this.mIsExpertActive = isExpertActive;
    }

    public int getExpertApplicationStatus() {
        return mExpertApplicationStatus.getValue();
    }

    public void setExpertApplicationStatus(ExpertApplicationStatus expertApplicationStatus) {
        this.mExpertApplicationStatus = expertApplicationStatus;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        this.mEmail = email;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        this.mId = id;
    }

//    public boolean isExpert() {
//        return mIsExpert;
//    }
//
//    public void setIsExpert(boolean isExpert) {
//        this.mIsExpert = isExpert;
//    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getToken() {
        return mToken;
    }

    public void setToken(String mToken) {
        this.mToken = mToken;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String mMessage) {
        this.mMessage = mMessage;
    }

    public boolean getSuccess() {
        return mSuccess;
    }

    public void setSuccess(boolean mSuccess) {
        this.mSuccess = mSuccess;
    }

    public String getProfileImage() {
        return mProfileImage;
    }

    public void setProfileImage(String profileImage) {
        this.mProfileImage = profileImage;
    }

    public boolean isExpertApplied() {
        return mIsExpertApplied;
    }

    public void setIsExpertApplied(boolean isExpertApplied) {
        this.mIsExpertApplied = isExpertApplied;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        mPhoneNumber = phoneNumber;
    }
}
