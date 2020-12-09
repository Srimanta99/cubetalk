package local.impactlife.cubetalk.models.expert_review_refund;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExpertRefundRequest {
    public String getExpertId() {
        return expertId;
    }

    public void setExpertId(String expertId) {
        this.expertId = expertId;
    }

    @Expose
    @SerializedName("expert_id")
    private String expertId;
}
