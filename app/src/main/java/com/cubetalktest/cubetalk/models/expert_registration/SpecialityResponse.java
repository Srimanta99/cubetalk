package com.cubetalktest.cubetalk.models.expert_registration;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import com.cubetalktest.cubetalk.models.common.Speciality;

public class SpecialityResponse {

    public static final String SELECTED_SPECIALITIES = "SelectedSpecialities";

    @Expose
    @SerializedName("data")
    private ArrayList<Speciality> mSpecialities;

    @Expose
    @SerializedName("success")
    private boolean mSuccess;

    public ArrayList<Speciality> getData() {
        return mSpecialities;
    }

    public void setData(ArrayList<Speciality> specialities) {
        this.mSpecialities = specialities;
    }

    public boolean getSuccess() {
        return mSuccess;
    }

    public void setSuccess(boolean success) {
        this.mSuccess = success;
    }
}
