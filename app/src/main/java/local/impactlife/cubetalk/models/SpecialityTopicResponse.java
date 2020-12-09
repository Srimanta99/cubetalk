package local.impactlife.cubetalk.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SpecialityTopicResponse {

    @Expose
    @SerializedName("success")
    private Boolean mSuccess;

    @Expose
    @SerializedName("data")
    private ArrayList<Data> mData;

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
        @SerializedName("updatedAt")
        private String mUpdatedAt;

        @Expose
        @SerializedName("createdAt")
        private String mCreatedAt;

        @Expose
        @SerializedName("name")
        private String mName;

        @Expose
        @SerializedName("__v")
        private Long mV;

        @Expose
        @SerializedName("icon")
        private String mIcon;

        @Expose
        @SerializedName("is_active")
        private Long mIsActive;

        @Expose
        @SerializedName("is_topic_exists")
        private int mIsTopicExists;

        @Expose
        @SerializedName("is_category")
        private Object mIsCategory;

        @Expose
        @SerializedName("is_speciality")
        private Long mIsSpeciality;

        @Expose
        @SerializedName("parent_name")
        private Object mParentName;

        @Expose
        @SerializedName("parent_id")
        private ParentId mParentId;

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
            mParentId = in.readParcelable(ParentId.class.getClassLoader());
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
            dest.writeParcelable(mParentId, flags);
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

        public ParentId getParentId() {
            return mParentId;
        }

        public void setParentId(ParentId parentId) {
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

        public int getIsTopicExists() {
            return mIsTopicExists;
        }

        public void setIsTopicExists(int mIsTopicExists) {
            this.mIsTopicExists = mIsTopicExists;
        }

        public static class ParentId implements Parcelable{

            @Expose
            @SerializedName("_id")
            private String mId;

            @Expose
            @SerializedName("name")
            private String mName;

            @Expose
            @SerializedName("is_document_active")
            private int mIsDocumentActive;

            protected ParentId(Parcel in) {
                mId = in.readString();
                mName = in.readString();
                mIsDocumentActive = in.readInt();
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(mId);
                dest.writeString(mName);
                dest.writeInt(mIsDocumentActive);
            }

            @Override
            public int describeContents() {
                return 0;
            }

            public static final Creator<ParentId> CREATOR = new Creator<ParentId>() {
                @Override
                public ParentId createFromParcel(Parcel in) {
                    return new ParentId(in);
                }

                @Override
                public ParentId[] newArray(int size) {
                    return new ParentId[size];
                }
            };

            public String getId() {
                return mId;
            }

            public void setId(String id) {
                mId = id;
            }

            public String getName() {
                return mName;
            }

            public void setName(String name) {
                mName = name;
            }

            public int getIsDocumentActive() {
                return mIsDocumentActive;
            }

            public void setIsDocumentActive(int isDocumentActive) {
                mIsDocumentActive = isDocumentActive;
            }
        }
    }
}
