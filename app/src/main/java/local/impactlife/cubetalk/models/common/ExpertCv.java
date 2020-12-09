package local.impactlife.cubetalk.models.common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExpertCv {

    @Expose
    @SerializedName("title")
    private String mTitle;

    @Expose
    @SerializedName("image")
    private String mImage;

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        this.mImage = image;
    }
}
