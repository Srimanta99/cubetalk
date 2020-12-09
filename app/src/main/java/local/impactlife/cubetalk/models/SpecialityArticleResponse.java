package local.impactlife.cubetalk.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SpecialityArticleResponse {

    @Expose
    @SerializedName("data")
    private List<Article> mArticles;

    @Expose
    @SerializedName("success")
    private boolean mSuccess;

    public List<Article> getArticles() {
        return mArticles;
    }

    public void setData(List<Article> articles) {
        this.mArticles = articles;
    }

    public boolean getSuccess() {
        return mSuccess;
    }

    public void setSuccess(boolean success) {
        this.mSuccess = success;
    }

    public static class Article {

        @Expose
        @SerializedName("description")
        private String mDescription;

        @Expose
        @SerializedName("speciality_id")
        private String mSpecialityId;

        @Expose
        @SerializedName("image")
        private String mImage;

        @Expose
        @SerializedName("url")
        private String mUrl;

        @Expose
        @SerializedName("content_type")
        private String mContentType;

        @Expose
        @SerializedName("is_active")
        private int mIsActive;

        @Expose
        @SerializedName("__v")
        private int mV;

        @Expose
        @SerializedName("name")
        private String mName;

        @Expose
        @SerializedName("createdAt")
        private String mCreatedAt;

        @Expose
        @SerializedName("updatedAt")
        private String mUpdatedAt;

        @Expose
        @SerializedName("_id")
        private String mId;

        public String getDescription() {
            return mDescription;
        }

        public void setDescription(String description) {
            this.mDescription = description;
        }

        public String getSpecialityId() {
            return mSpecialityId;
        }

        public void setSpecialityId(String specialityId) {
            this.mSpecialityId = specialityId;
        }

        public String getImage() {
            return mImage;
        }

        public void setImage(String mImage) {
            this.mImage = mImage;
        }

        public String getUrl() {
            return mUrl;
        }

        public void setUrl(String url) {
            this.mUrl = url;
        }

        public String getContentType() {
            return mContentType;
        }

        public void setContentType(String contentType) {
            this.mContentType = contentType;
        }

        public int getIsActive() {
            return mIsActive;
        }

        public void setIsActive(int isActive) {
            this.mIsActive = isActive;
        }

        public int getV() {
            return mV;
        }

        public void setV(int v) {
            this.mV = v;
        }

        public String getName() {
            return mName;
        }

        public void setName(String name) {
            this.mName = name;
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
