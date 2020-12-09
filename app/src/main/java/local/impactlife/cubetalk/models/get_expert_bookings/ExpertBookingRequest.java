package local.impactlife.cubetalk.models.get_expert_bookings;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExpertBookingRequest {

    @Expose
    @SerializedName("expert_id")
    private String mExpertId;

    @Expose
    @SerializedName("slot_date")
    private String mSlotDate;

    @Expose
    @SerializedName("status")
    private String mStatus;

    public String getExpertId() {
        return mExpertId;
    }

    public void setExpertId(String expertId) {
        mExpertId = expertId;
    }

    public String getSlotDate() {
        return mSlotDate;
    }

    public void setSlotDate(String slotDate) {
        mSlotDate = slotDate;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }
}
