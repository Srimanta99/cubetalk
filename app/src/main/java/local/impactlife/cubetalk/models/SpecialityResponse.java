
package local.impactlife.cubetalk.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class SpecialityResponse {

    @Expose
    @SerializedName("data")
    private ArrayList<Data> mData;

    @Expose
    @SerializedName("success")
    private Boolean mSuccess;

    public ArrayList<Data> getData() {
        return mData;
    }

    public void setData(ArrayList<Data> data) {
        mData = data;
    }

    public Boolean getSuccess() {
        return mSuccess;
    }

    public void setSuccess(Boolean success) {
        mSuccess = success;
    }

    public static class Data implements Parcelable {

        @Expose
        @SerializedName("_id")
        private String mId;

        @Expose
        @SerializedName("name")
        private String mName;

        @Expose
        @SerializedName("updatedAt")
        private String mUpdatedAt;

        @Expose
        @SerializedName("createdAt")
        private String mCreatedAt;

        @Expose
        @SerializedName("is_topic_exists")
        private int mIsTopicExists;

        @Expose
        @SerializedName("is_active")
        private Long mIsActive;

        @Expose
        @SerializedName("icon")
        private String mIcon;

        @Expose
        @SerializedName("is_category")
        private Object mIsCategory;

        @Expose
        @SerializedName("is_speciality")
        private Long mIsSpeciality;

        @Expose
        @SerializedName("parent_id")
        private String mParentId;

        @Expose
        @SerializedName("parent_name")
        private Object mParentName;

        @Expose
        @SerializedName("__v")
        private Long mV;

        public Data() {
        }

        protected Data(Parcel in) {
            mCreatedAt = in.readString();
            mIcon = in.readString();
            if (in.readByte() == 0) {
                mIsActive = null;
            } else {
                mIsActive = in.readLong();
            }
            if (in.readByte() == 0) {
                mIsSpeciality = null;
            } else {
                mIsSpeciality = in.readLong();
            }
            mName = in.readString();
            mParentId = in.readString();
            mUpdatedAt = in.readString();
            if (in.readByte() == 0) {
                mV = null;
            } else {
                mV = in.readLong();
            }
            mId = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(mCreatedAt);
            dest.writeString(mIcon);
            if (mIsActive == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeLong(mIsActive);
            }
            if (mIsSpeciality == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeLong(mIsSpeciality);
            }
            dest.writeString(mName);
            dest.writeString(mParentId);
            dest.writeString(mUpdatedAt);
            if (mV == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeLong(mV);
            }
            dest.writeString(mId);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<Data> CREATOR = new Creator<Data>() {
            @Override
            public Data createFromParcel(Parcel in) {
                return new Data(in);
            }

            @Override
            public Data[] newArray(int size) {
                return new Data[size];
            }
        };

        public String getCreatedAt() {
            return mCreatedAt;
        }

        public void setCreatedAt(String createdAt) {
            mCreatedAt = createdAt;
        }

        public String getIcon() {
            return mIcon;
        }

        public void setIcon(String icon) {
            mIcon = icon;
        }

        public Long getIsActive() {
            return mIsActive;
        }

        public void setIsActive(Long isActive) {
            mIsActive = isActive;
        }

        public Object getIsCategory() {
            return mIsCategory;
        }

        public void setIsCategory(Object isCategory) {
            mIsCategory = isCategory;
        }

        public Long getIsSpeciality() {
            return mIsSpeciality;
        }

        public void setIsSpeciality(Long isSpeciality) {
            mIsSpeciality = isSpeciality;
        }

        public String getName() {
            return mName;
        }

        public void setName(String name) {
            mName = name;
        }

        public String getParentId() {
            return mParentId;
        }

        public void setParentId(String parentId) {
            mParentId = parentId;
        }

        public Object getParentName() {
            return mParentName;
        }

        public void setParentName(Object parentName) {
            mParentName = parentName;
        }

        public String getUpdatedAt() {
            return mUpdatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            mUpdatedAt = updatedAt;
        }

        public Long getV() {
            return mV;
        }

        public void setV(Long v) {
            mV = v;
        }

        public String getId() {
            return mId;
        }

        public void setId(String id) {
            mId = id;
        }

        public Integer getIsTopicExists() {
            return mIsTopicExists;
        }

        public void setIsTopicExists(Integer mIsTopicExists) {
            this.mIsTopicExists = mIsTopicExists;
        }
    }
}

