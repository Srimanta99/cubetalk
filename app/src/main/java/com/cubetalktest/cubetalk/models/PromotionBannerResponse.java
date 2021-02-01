
package com.cubetalktest.cubetalk.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PromotionBannerResponse {

    @Expose
    @SerializedName("data")
    private List<Data> mData;

    @Expose
    @SerializedName("success")
    private Boolean mSuccess;

    public List<Data> getData() {
        return mData;
    }

    public void setData(List<Data> data) {
        mData = data;
    }

    public Boolean getSuccess() {
        return mSuccess;
    }

    public void setSuccess(Boolean success) {
        mSuccess = success;
    }

    public static class Data {

        @Expose
        @SerializedName("content_type")
        private String mContentType;

        @Expose
        @SerializedName("createdAt")
        private String mCreatedAt;

        @Expose
        @SerializedName("description")
        private String mDescription;

        @Expose
        @SerializedName("image")
        private String mImage;

        @Expose
        @SerializedName("image_location")
        private String mImageLocation;

        @Expose
        @SerializedName("is_active")
        private Long mIsActive;

        @Expose
        @SerializedName("name")
        private String mName;

        @Expose
        @SerializedName("updatedAt")
        private String mUpdatedAt;

        @Expose
        @SerializedName("__v")
        private Long mV;

        @Expose
        @SerializedName("_id")
        private String mId;

        public String getContentType() {
            return mContentType;
        }

        public void setContentType(String contentType) {
            mContentType = contentType;
        }

        public String getCreatedAt() {
            return mCreatedAt;
        }

        public void setCreatedAt(String createdAt) {
            mCreatedAt = createdAt;
        }

        public String getDescription() {
            return mDescription;
        }

        public void setDescription(String description) {
            mDescription = description;
        }

        public String getImage() {
            return mImage;
        }

        public void setImage(String image) {
            mImage = image;
        }

        public String getImageLocation() {
            return mImageLocation;
        }

        public void setImageLocation(String imageLocation) {
            mImageLocation = imageLocation;
        }

        public long getIsActive() {
            return mIsActive;
        }

        public void setIsActive(long isActive) {
            mIsActive = isActive;
        }

        public String getName() {
            return mName;
        }

        public void setName(String name) {
            mName = name;
        }

        public String getUpdatedAt() {
            return mUpdatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            mUpdatedAt = updatedAt;
        }

        public long get_V() {
            return mV;
        }

        public void set_V(long _V) {
            mV = _V;
        }

        public String get_id() {
            return mId;
        }

        public void set_id(String _id) {
            mId = _id;
        }

    }
}
