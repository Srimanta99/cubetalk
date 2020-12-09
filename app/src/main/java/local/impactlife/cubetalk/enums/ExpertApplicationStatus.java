package local.impactlife.cubetalk.enums;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

public enum ExpertApplicationStatus {
    @SerializedName("0")
    NOT_APPLIED(0),
    @SerializedName("1")
    PENDING(1),
    @SerializedName("2")
    APPROVED(2),
    @SerializedName("3")
    REJECTED(3);

    private int value;
    private static Map<Integer, ExpertApplicationStatus> map = new HashMap<>();

    ExpertApplicationStatus(int value) {
        this.value = value;
    }

    static {
        for (ExpertApplicationStatus expertApplicationStatus : ExpertApplicationStatus.values()) {
            map.put(expertApplicationStatus.value, expertApplicationStatus);
        }
    }

    public static ExpertApplicationStatus valueOf(int expertApplicationStatus) {
        return map.get(expertApplicationStatus);
    }

    public int getValue() {
        return value;
    }
}
