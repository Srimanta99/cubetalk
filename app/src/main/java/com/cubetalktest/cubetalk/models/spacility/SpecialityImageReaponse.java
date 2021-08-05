package com.cubetalktest.cubetalk.models.spacility;

import com.cubetalktest.cubetalk.models.SpecialityArticleResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SpecialityImageReaponse {
    public boolean ismSuccess() {
        return mSuccess;
    }

    public void setmSuccess(boolean mSuccess) {
        this.mSuccess = mSuccess;
    }

    public List<ImgDetails> getmArticles() {
        return mArticles;
    }

    public void setmArticles(List<ImgDetails> mArticles) {
        this.mArticles = mArticles;
    }

    @Expose
    @SerializedName("success")
    private boolean mSuccess;


    @Expose
    @SerializedName("data")
    private List<SpecialityImageReaponse.ImgDetails> mArticles;

    public static class ImgDetails{
        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getIs_active() {
            return is_active;
        }

        public void setIs_active(Integer is_active) {
            this.is_active = is_active;
        }

        public String getCategory_id() {
            return category_id;
        }

        public void setCategory_id(String category_id) {
            this.category_id = category_id;
        }

        public String getContent_type() {
            return content_type;
        }

        public void setContent_type(String content_type) {
            this.content_type = content_type;
        }

        public String getImage_location() {
            return image_location;
        }

        public void setImage_location(String image_location) {
            this.image_location = image_location;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        @Expose
        @SerializedName("_id")
        private String _id;

        @Expose
        @SerializedName("updatedAt")
        private String updatedAt;

        @Expose
        @SerializedName("createdAt")
        private String createdAt;

        @Expose
        @SerializedName("name")
        private String name;

        @Expose
        @SerializedName("is_active")
        private Integer is_active;

        @Expose
        @SerializedName("category_id")
        private String category_id;

        @Expose
        @SerializedName("content_type")
        private String content_type;

        @Expose
        @SerializedName("image_location")
        private String image_location;

        @Expose
        @SerializedName("image")
        private String image;

        @Expose
        @SerializedName("description")
        private String description;

    }
}
