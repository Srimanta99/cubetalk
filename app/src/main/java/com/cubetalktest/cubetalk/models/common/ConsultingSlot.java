package com.cubetalktest.cubetalk.models.common;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConsultingSlot implements Parcelable {

    @Expose
    @SerializedName("weekdays")
    private ConsultingSlotDay mWeekdays;

    @Expose
    @SerializedName("saturday")
    private ConsultingSlotDay mSaturday;

    @Expose
    @SerializedName("sunday")
    private ConsultingSlotDay mSunday;

    public ConsultingSlot() {

    }

    protected ConsultingSlot(Parcel in) {
        mWeekdays = in.readParcelable(ConsultingSlotDay.class.getClassLoader());
        mSaturday = in.readParcelable(ConsultingSlotDay.class.getClassLoader());
        mSunday = in.readParcelable(ConsultingSlotDay.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(mWeekdays, flags);
        dest.writeParcelable(mSaturday, flags);
        dest.writeParcelable(mSunday, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ConsultingSlot> CREATOR = new Creator<ConsultingSlot>() {
        @Override
        public ConsultingSlot createFromParcel(Parcel in) {
            return new ConsultingSlot(in);
        }

        @Override
        public ConsultingSlot[] newArray(int size) {
            return new ConsultingSlot[size];
        }
    };

    public ConsultingSlotDay getWeekdays() {
        return mWeekdays;
    }

    public void setWeekdays(ConsultingSlotDay weekdays) {
        this.mWeekdays = weekdays;
    }

    public ConsultingSlotDay getSaturday() {
        return mSaturday;
    }

    public void setSaturday(ConsultingSlotDay saturday) {
        this.mSaturday = saturday;
    }

    public ConsultingSlotDay getSunday() {
        return mSunday;
    }

    public void setSunday(ConsultingSlotDay sunday) {
        this.mSunday = sunday;
    }
}
