package com.cubetalktest.cubetalk.models.register_user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserRegistrationRequest {

    @Expose
    @SerializedName("name")
    private String mName;

    @Expose
    @SerializedName("email")
    private String mEmail;

    @Expose
    @SerializedName("password")
    private String mPassword;

    @Expose
    @SerializedName("phone_number")
    private String mPhoneNumber;

    @Expose
    @SerializedName("gender")
    private String mGender;

    @Expose
    @SerializedName("age")
    private String mAge;

    @Expose
    @SerializedName("country")
    private int mCountry;

    @Expose
    @SerializedName("city")
    private String mCity;

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    @Expose
    @SerializedName("app_id")
    private String app_id;

    @Expose
    @SerializedName("confirm_password")
    private String confirm_password;

    public String getConfirm_password() {
        return confirm_password;
    }

    public void setConfirm_password(String confirm_password) {
        this.confirm_password = confirm_password;
    }



    public void setName(String name) {
        this.mName = name;
    }

    public String getName() {
        return mName;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        this.mEmail = email;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        this.mPassword = password;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.mPhoneNumber = phoneNumber;
    }

    public String getGender() {
        return mGender;
    }

    public void setGender(String gender) {
        this.mGender = gender;
    }

    public String getAge() {
        return mAge;
    }

    public void setAge(String age) {
        this.mAge = age;
    }

    public int getCountry() {
        return mCountry;
    }

    public void setCountry(int country) {
        this.mCountry = country;
    }

    public String getCity() {
        return mCity;
    }

    public void setCity(String city) {
        this.mCity = city;
    }
}
