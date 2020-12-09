package local.impactlife.cubetalk.models.common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SpecialityTopic {

    @Expose
    @SerializedName("_id")
    private String mId;

    @Expose
    @SerializedName("topic")
    private List<Topic> mTopics;

    @Expose
    @SerializedName("speciality")
    private Speciality mSpeciality;

    public Speciality getSpeciality() {
        return mSpeciality;
    }

    public void setSpeciality(Speciality speciality) {
        this.mSpeciality = speciality;
    }

    public List<Topic> getTopics() {
        return mTopics;
    }

    public void setTopics(List<Topic> topics) {
        this.mTopics = topics;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        this.mId = id;
    }
}
