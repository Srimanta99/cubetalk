package com.cubetalktest.cubetalk.models.common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Expert {

    @Expose
    @SerializedName("_id")
    private String mId;

    @Expose
    @SerializedName("name")
    private String mName;

    @Expose
    @SerializedName("profileimage")
    private String mProfileImage;

    @Expose
    @SerializedName("languages")
    private String mLanguages;

    @Expose
    @SerializedName("years_of_experience")
    private int mYearsOfExperience;

    @Expose
    @SerializedName("speciality_topic")
    private List<SpecialityTopic> mSpecialityTopics;

    @Expose
    @SerializedName("email")
    private String mEmail;

    @Expose
    @SerializedName("professional_summery")
    private String mProfessionalSummary;

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
    @SerializedName("mesibo_id")
    private String mesibo_id;

    @Expose
    @SerializedName("mesibo_token")
    private String mesibo_token;

    public String getExpert_service_start_date() {
        return expert_service_start_date;
    }

    public void setExpert_service_start_date(String expert_service_start_date) {
        this.expert_service_start_date = expert_service_start_date;
    }

    @Expose
    @SerializedName("expert_service_start_date")
    private String expert_service_start_date;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getProfileImage() {
        return mProfileImage;
    }

    public void setProfileImage(String profileImage) {
        this.mProfileImage = profileImage;
    }

    public String getLanguages() {
        return mLanguages;
    }

    public void setLanguages(String languages) {
        this.mLanguages = languages;
    }

    public int getYearsOfExperience() {
        return mYearsOfExperience;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        this.mYearsOfExperience = yearsOfExperience;
    }

    public List<SpecialityTopic> getSpecialityTopics() {
        return mSpecialityTopics;
    }

    public void setSpecialityTopic(List<SpecialityTopic> specialityTopics) {
        this.mSpecialityTopics = specialityTopics;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        this.mEmail = mEmail;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        this.mId = id;
    }

    public String getProfessionalSummary() {
        return mProfessionalSummary;
    }

    public void setProfessionalSummary(String professionalSummary) {
        this.mProfessionalSummary = professionalSummary;
    }
}

