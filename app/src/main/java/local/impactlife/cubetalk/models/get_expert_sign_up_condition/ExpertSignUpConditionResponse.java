package local.impactlife.cubetalk.models.get_expert_sign_up_condition;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExpertSignUpConditionResponse {

    @Expose
    @SerializedName("setting")
    private Setting mSetting;

    @Expose
    @SerializedName("message")
    private String mMessage;

    @Expose
    @SerializedName("success")
    private boolean mSuccess;

    public Setting getSetting() {
        return mSetting;
    }

    public void setSetting(Setting setting) {
        this.mSetting = setting;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        this.mMessage = message;
    }

    public boolean getSuccess() {
        return mSuccess;
    }

    public void setSuccess(boolean success) {
        this.mSuccess = success;
    }

    public static class Setting {
        @Expose
        @SerializedName("expert_signup")
        private boolean mExpertSignUp;
        @Expose
        @SerializedName("_id")
        private String mId;

        public boolean getExpertSignUp() {
            return mExpertSignUp;
        }

        public void setExpertSignUp(boolean expertSignUp) {
            this.mExpertSignUp = expertSignUp;
        }

        public String getId() {
            return mId;
        }

        public void setId(String id) {
            this.mId = id;
        }
    }
}
