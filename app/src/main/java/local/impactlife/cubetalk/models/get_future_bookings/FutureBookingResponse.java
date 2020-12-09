package local.impactlife.cubetalk.models.get_future_bookings;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import local.impactlife.cubetalk.models.common.Booking;

public class FutureBookingResponse {

    @Expose
    @SerializedName("success")
    private boolean mSuccess;

    @Expose
    @SerializedName("data")
    private List<Booking> mFutureBookings;

    public boolean isSuccess() {
        return mSuccess;
    }

    public void setSuccess(boolean success) {
        mSuccess = success;
    }

    public List<Booking> getFutureBookings() {
        return mFutureBookings;
    }

    public void setFutureBookings(List<Booking> futureBookings) {
        mFutureBookings = futureBookings;
    }
}
