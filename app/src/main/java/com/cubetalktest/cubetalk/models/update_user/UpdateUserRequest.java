package com.cubetalktest.cubetalk.models.update_user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateUserRequest {

    @Expose
    @SerializedName("name")
    private String mName;

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

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
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
