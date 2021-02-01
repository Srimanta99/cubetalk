package com.cubetalktest.cubetalk.models.common;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConsultingSlotDay implements Parcelable {

    @Expose
    @SerializedName("from1")
    private String mFrom1;

    @Expose
    @SerializedName("to1")
    private String mTo1;

    @Expose
    @SerializedName("from2")
    private String mFrom2;

    @Expose
    @SerializedName("to2")
    private String mTo2;

    @Expose
    @SerializedName("is_active")
    private boolean mIsActive;

    public ConsultingSlotDay() {
    }

    protected ConsultingSlotDay(Parcel in) {
        mFrom1 = in.readString();
        mTo1 = in.readString();
        mFrom2 = in.readString();
        mTo2 = in.readString();
        mIsActive = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mFrom1);
        dest.writeString(mTo1);
        dest.writeString(mFrom2);
        dest.writeString(mTo2);
        dest.writeByte((byte) (mIsActive ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ConsultingSlotDay> CREATOR = new Creator<ConsultingSlotDay>() {
        @Override
        public ConsultingSlotDay createFromParcel(Parcel in) {
            return new ConsultingSlotDay(in);
        }

        @Override
        public ConsultingSlotDay[] newArray(int size) {
            return new ConsultingSlotDay[size];
        }
    };

    public String getFrom1() {
        return mFrom1;
    }

    public void setFrom1(String from1) {
        this.mFrom1 = from1;
    }

    public String getTo1() {
        return mTo1;
    }

    public void setTo1(String to1) {
        this.mTo1 = to1;
    }

    public String getFrom2() {
        return mFrom2;
    }

    public void setFrom2(String from2) {
        this.mFrom2 = from2;
    }

    public String getTo2() {
        return mTo2;
    }

    public void setTo2(String to2) {
        this.mTo2 = to2;
    }

    public boolean getIsActive() {
        return mIsActive;
    }

    public void setIsActive(boolean isActive) {
        this.mIsActive = isActive;
    }
}
