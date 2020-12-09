package local.impactlife.cubetalk.models.expert_registration;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExpertRegistrationResponse {

    @Expose
    @SerializedName("message")
    private String mMessage;

    @Expose
    @SerializedName("statusCode")
    private int mStatusCode;

    @Expose
    @SerializedName("success")
    private boolean mSuccess;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        this.mMessage = message;
    }

    public int getStatusCode() {
        return mStatusCode;
    }

    public void setStatusCode(int statusCode) {
        this.mStatusCode = statusCode;
    }

    public boolean getSuccess() {
        return mSuccess;
    }

    public void setSuccess(boolean success) {
        this.mSuccess = success;
    }
}
