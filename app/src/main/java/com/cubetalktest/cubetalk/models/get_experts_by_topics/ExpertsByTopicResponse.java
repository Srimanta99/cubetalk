package com.cubetalktest.cubetalk.models.get_experts_by_topics;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import com.cubetalktest.cubetalk.models.common.Expert;

public class ExpertsByTopicResponse {

    @Expose
    @SerializedName("data")
    private List<Expert> mExperts;

    @Expose
    @SerializedName("success")
    private boolean mSuccess;

    @Expose
    @SerializedName("err")
    private String mError;

    public List<Expert> getExperts() {
        return mExperts;
    }

    public void setExperts(List<Expert> experts) {
        this.mExperts = experts;
    }

    public boolean getSuccess() {
        return mSuccess;
    }

    public void setSuccess(boolean mSuccess) {
        this.mSuccess = mSuccess;
    }

    public String getError() {
        return mError;
    }

    public void setError(String error) {
        this.mError = error;
    }
}
