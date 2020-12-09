package local.impactlife.cubetalk.models.common;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConsultingSlotDuration implements Parcelable {

    @Expose
    @SerializedName("duration_12")
    private boolean mDuration12;

    @Expose
    @SerializedName("duration_25")
    private boolean mDuration25;

    @Expose
    @SerializedName("duration_50")
    private boolean mDuration50;

    public ConsultingSlotDuration() {
    }

    protected ConsultingSlotDuration(Parcel in) {
        mDuration12 = in.readByte() != 0;
        mDuration25 = in.readByte() != 0;
        mDuration50 = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (mDuration12 ? 1 : 0));
        dest.writeByte((byte) (mDuration25 ? 1 : 0));
        dest.writeByte((byte) (mDuration50 ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ConsultingSlotDuration> CREATOR = new Creator<ConsultingSlotDuration>() {
        @Override
        public ConsultingSlotDuration createFromParcel(Parcel in) {
            return new ConsultingSlotDuration(in);
        }

        @Override
        public ConsultingSlotDuration[] newArray(int size) {
            return new ConsultingSlotDuration[size];
        }
    };

    public boolean getDuration12() {
        return mDuration12;
    }

    public void setDuration12(boolean mDuration12) {
        this.mDuration12 = mDuration12;
    }

    public boolean getDuration25() {
        return mDuration25;
    }

    public void setDuration25(boolean mDuration25) {
        this.mDuration25 = mDuration25;
    }

    public boolean getDuration50() {
        return mDuration50;
    }

    public void setDuration50(boolean mDuration50) {
        this.mDuration50 = mDuration50;
    }
}
