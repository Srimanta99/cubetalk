package local.impactlife.cubetalk.models.get_user_past_bookings;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PastBookingRequest {

    @Expose
    @SerializedName("user_id")
    private String mUserId;

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }
}
