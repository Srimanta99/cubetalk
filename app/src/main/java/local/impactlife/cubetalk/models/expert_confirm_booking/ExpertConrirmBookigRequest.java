package local.impactlife.cubetalk.models.expert_confirm_booking;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExpertConrirmBookigRequest {

    @Expose
    @SerializedName("booked_expert_id")
    private String booked_expert_id;

    @Expose
    @SerializedName("booked_id")
    private String booked_id;


    public String getBooked_expert_id() {
        return booked_expert_id;
    }

    public void setBooked_expert_id(String booked_expert_id) {
        this.booked_expert_id = booked_expert_id;
    }

    public String getBooked_id() {
        return booked_id;
    }

    public void setBooked_id(String booked_id) {
        this.booked_id = booked_id;
    }


}
