package local.impactlife.cubetalk.models.common;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Speciality implements Parcelable {

    @Expose
    @SerializedName("id")
    private String mId;

    @Expose
    @SerializedName("name")
    private String mName;

    @Expose
    @SerializedName("child")
    private ArrayList<Topic> mTopics;

    public Speciality() {
    }

    protected Speciality(Parcel in) {
        mId = in.readString();
        mName = in.readString();
        mTopics = in.createTypedArrayList(Topic.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mName);
        dest.writeTypedList(mTopics);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Speciality> CREATOR = new Creator<Speciality>() {
        @Override
        public Speciality createFromParcel(Parcel in) {
            return new Speciality(in);
        }

        @Override
        public Speciality[] newArray(int size) {
            return new Speciality[size];
        }
    };

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

    public ArrayList<Topic> getTopics() {
        return mTopics;
    }

    public void setTopics(ArrayList<Topic> topics) {
        mTopics = topics;
    }
}
