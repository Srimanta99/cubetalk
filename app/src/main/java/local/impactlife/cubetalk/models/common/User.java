package local.impactlife.cubetalk.models.common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {
    @Expose
    @SerializedName("_id")
    private String mId;

    @Expose
    @SerializedName("name")
    private String mName;

    @Expose
    @SerializedName("profileimage")
    private String mProfileImage;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        this.mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getProfileImage() {
        return mProfileImage;
    }

    public void setProfileImage(String profileImage) {
        this.mProfileImage = profileImage;
    }
}
