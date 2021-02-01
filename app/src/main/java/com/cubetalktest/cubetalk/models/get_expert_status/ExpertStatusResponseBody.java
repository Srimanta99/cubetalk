package com.cubetalktest.cubetalk.models.get_expert_status;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExpertStatusResponseBody {

    @Expose
    @SerializedName("data")
    private Data mData;

    @Expose
    @SerializedName("message")
    private String mMessage;

    @Expose
    @SerializedName("success")
    private boolean mSuccess;

    public Data getData() {
        return mData;
    }

    public void setData(Data mData) {
        this.mData = mData;
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

    public static class Data {

        @Expose
        @SerializedName("name")
        private String mName;

        @Expose
        @SerializedName("expert_application_status")
        private int mExpertApplicationStatus;

        @Expose
        @SerializedName("is_expert_active")
        private boolean mIsExpertActive;

        @Expose
        @SerializedName("is_expert_applied")
        private boolean mIsExpertApplied;

        @Expose
        @SerializedName("_id")
        private String mId;

        public String getName() {
            return mName;
        }

        public void setName(String name) {
            this.mName = name;
        }

        public int getExpertApplicationStatus() {
            return mExpertApplicationStatus;
        }

        public void setExpertApplicationStatus(int expertApplicationStatus) {
            this.mExpertApplicationStatus = expertApplicationStatus;
        }

        public boolean getIsExpertActive() {
            return mIsExpertActive;
        }

        public void setIsExpertActive(boolean isExpertActive) {
            this.mIsExpertActive = isExpertActive;
        }

        public boolean getIsExpertApplied() {
            return mIsExpertApplied;
        }

        public void setIsExpertApplied(boolean isExpertApplied) {
            this.mIsExpertApplied = isExpertApplied;
        }

        public String getId() {
            return mId;
        }

        public void setId(String id) {
            this.mId = id;
        }
    }
}
