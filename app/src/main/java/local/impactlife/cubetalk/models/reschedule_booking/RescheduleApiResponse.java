package local.impactlife.cubetalk.models.reschedule_booking;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RescheduleApiResponse {


    @Expose
    @SerializedName("success")
    private boolean mSuccess;

    public boolean ismSuccess() {
        return mSuccess;
    }

    public void setmSuccess(boolean mSuccess) {
        this.mSuccess = mSuccess;
    }

    public String getMessge() {
        return messge;
    }

    public void setMessge(String messge) {
        this.messge = messge;
    }

    @Expose
    @SerializedName("message")
    private String messge;
}
