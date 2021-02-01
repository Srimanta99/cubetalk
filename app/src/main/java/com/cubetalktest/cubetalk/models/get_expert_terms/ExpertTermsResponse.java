package com.cubetalktest.cubetalk.models.get_expert_terms;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExpertTermsResponse {

    @Expose
    @SerializedName("cms")
    private Cms mCms;

    @Expose
    @SerializedName("success")
    private boolean mSuccess;

    public Cms getCms() {
        return mCms;
    }

    public void setCms(Cms cms) {
        this.mCms = cms;
    }

    public boolean getSuccess() {
        return mSuccess;
    }

    public void setSuccess(boolean success) {
        this.mSuccess = success;
    }

    public static class Cms {

        @Expose
        @SerializedName("is_active")
        private boolean mIsActive;

        @Expose
        @SerializedName("__v")
        private int mV;

        @Expose
        @SerializedName("content")
        private String mContent;

        @Expose
        @SerializedName("title")
        private String mTitle;

        @Expose
        @SerializedName("createdAt")
        private String mCreatedAt;

        @Expose
        @SerializedName("updatedAt")
        private String mUpdatedAt;

        @Expose
        @SerializedName("_id")
        private String mId;

        public boolean getIsActive() {
            return mIsActive;
        }

        public void setIsActive(boolean isActive) {
            this.mIsActive = isActive;
        }

        public int getV() {
            return mV;
        }

        public void setV(int v) {
            this.mV = v;
        }

        public String getContent() {
            return mContent;
        }

        public void setContent(String content) {
            this.mContent = content;
        }

        public String getTitle() {
            return mTitle;
        }

        public void setTitle(String title) {
            this.mTitle = title;
        }

        public String getCreatedAt() {
            return mCreatedAt;
        }

        public void setCreatedAt(String createdAt) {
            this.mCreatedAt = createdAt;
        }

        public String getUpdatedAt() {
            return mUpdatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.mUpdatedAt = updatedAt;
        }

        public String getId() {
            return mId;
        }

        public void setId(String id) {
            this.mId = id;
        }
    }
}
