package local.impactlife.cubetalk.models;

import androidx.annotation.NonNull;

public class Country {

    private int mId;

    private String mName;

    private String mCallingCode;

    private String mPhoneNumberRegex;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getCallingCode() {
        return mCallingCode;
    }

    public void setCallingCode(String callingCode) {
        this.mCallingCode = callingCode;
    }

    public String getPhoneNumberRegex() {
        return mPhoneNumberRegex;
    }

    public void setPhoneNumberRegex(String phoneNumberRegex) {
        this.mPhoneNumberRegex = phoneNumberRegex;
    }

    public Country(int id, String name, String callingCode, String phoneNumberRegex) {
        this.mId = id;
        this.mName = name;
        this.mCallingCode = callingCode;
        this.mPhoneNumberRegex = phoneNumberRegex;
    }

    @NonNull
    @Override
    public String toString() {
        return mName;
    }
}
