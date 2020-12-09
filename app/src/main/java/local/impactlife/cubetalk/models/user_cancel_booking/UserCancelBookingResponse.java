package local.impactlife.cubetalk.models.user_cancel_booking;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserCancelBookingResponse {

    @Expose
    @SerializedName("success")
    private boolean mSuccess;

    @Expose
    @SerializedName("message")
    private String mMessage;

    public boolean isSuccess() {
        return mSuccess;
    }

    public void setSuccess(boolean mSuccess) {
        this.mSuccess = mSuccess;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String mMessage) {
        this.mMessage = mMessage;
    }
}
