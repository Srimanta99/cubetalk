package local.impactlife.cubetalk.models.get_user_past_bookings;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import local.impactlife.cubetalk.models.common.Booking;

public class PastBookingResponse {
    @Expose
    @SerializedName("success")
    private boolean mSuccess;

    @Expose
    @SerializedName("data")
    private List<Booking> mPastBookings;

    public boolean isSuccess() {
        return mSuccess;
    }

    public void setSuccess(boolean success) {
        mSuccess = success;
    }

    public List<Booking> getPastBookings() {
        return mPastBookings;
    }

    public void setPastBookings(List<Booking> pastBookings) {
        mPastBookings = pastBookings;
    }
}
