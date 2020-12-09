package local.impactlife.cubetalk.models.user_info;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FutureConsultingSlotDuration {
    @SerializedName("duration_50")
    @Expose
    private Boolean duration50;
    @SerializedName("duration_25")
    @Expose
    private Boolean duration25;
    @SerializedName("duration_12")
    @Expose
    private Boolean duration12;

    public Boolean getDuration50() {
        return duration50;
    }

    public void setDuration50(Boolean duration50) {
        this.duration50 = duration50;
    }

    public Boolean getDuration25() {
        return duration25;
    }

    public void setDuration25(Boolean duration25) {
        this.duration25 = duration25;
    }

    public Boolean getDuration12() {
        return duration12;
    }

    public void setDuration12(Boolean duration12) {
        this.duration12 = duration12;
    }
}
