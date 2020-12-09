package local.impactlife.cubetalk.models.register_user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import local.impactlife.cubetalk.models.common.BankInfo;
import local.impactlife.cubetalk.models.common.ConsultingSlot;
import local.impactlife.cubetalk.models.common.ConsultingSlotDuration;

public class UserRegistrationResponse {

    @Expose
    @SerializedName("data")
    private Data mData;

    @Expose
    @SerializedName("message")
    private String mMessage;

    @Expose
    @SerializedName("statusCode")
    private int mStatusCode;

    @Expose
    @SerializedName("success")
    private boolean mSuccess = false;

    public Data getData() {
        return mData;
    }

    public void setData(Data data) {
        this.mData = data;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        this.mMessage = message;
    }

    public int getStatusCode() {
        return mStatusCode;
    }

    public void setStatuscode(int statusCode) {
        this.mStatusCode = statusCode;
    }

    public boolean isSuccess() {
        return mSuccess;
    }

    public void setSuccess(boolean success) {
        this.mSuccess = success;
    }

    static class Data {

        @Expose
        @SerializedName("name")
        private String mName;

        @Expose
        @SerializedName("password")
        private String mPassword;

        @Expose
        @SerializedName("email_verified")
        private boolean mEmailVerified;

        @Expose
        @SerializedName("email_verification_code")
        private String mEmailVerificationCode;

        @Expose
        @SerializedName("phone_number")
        private String mPhoneNumber;

        @Expose
        @SerializedName("profileimage")
        private String mProfileImage;

        @Expose
        @SerializedName("gender")
        private String mGender;

        @Expose
        @SerializedName("age")
        private String mAge;

        @Expose
        @SerializedName("country")
        private int mCountry;

        @Expose
        @SerializedName("city")
        private String mCity;

        @Expose
        @SerializedName("address")
        private String mAddress;

        @Expose
        @SerializedName("professional_summery")
        private String mProfessionalSummary;

        @Expose
        @SerializedName("key_accomplishment")
        private String mKeyAccomplishment;

        @Expose
        @SerializedName("languages")
        private String mLanguages;

        @Expose
        @SerializedName("years_of_experience")
        private String mYearsOfExperience;

        @Expose
        @SerializedName("speciality_topic")
        private List<String> mSpecialityTopics;

        @Expose
        @SerializedName("consulting_slot")
        private ConsultingSlot mConsultingSlot;

        @Expose
        @SerializedName("consulting_slot_duration")
        private ConsultingSlotDuration mConsultingSlotDuration;

        @Expose
        @SerializedName("bank_info")
        private BankInfo mBankInfo;

        @Expose
        @SerializedName("is_logged_in")
        private boolean mIsLoggedIn;

        @Expose
        @SerializedName("last_login")
        private String mLastLogin;

        @Expose
        @SerializedName("is_blocked")
        private boolean mIsBlocked;

        @Expose
        @SerializedName("is_active")
        private boolean mIsActive;

        @Expose
        @SerializedName("is_admin")
        private boolean mIsAdmin;

        @Expose
        @SerializedName("user_agent")
        private String mUserAgent;

        @Expose
        @SerializedName("device_token")
        private String mDeviceToken;

        @Expose
        @SerializedName("is_expert")
        private boolean mIsExpert;

        @Expose
        @SerializedName("_id")
        private String mId;

        @Expose
        @SerializedName("email")
        private String mEmail;

        @Expose
        @SerializedName("createdAt")
        private String mCreatedAt;

        @Expose
        @SerializedName("updatedAt")
        private String mUpdatedAt;

        @Expose
        @SerializedName("__v")
        private int mV;

        public String getName() {
            return mName;
        }

        public void setName(String name) {
            this.mName = name;
        }

        public String getPassword() {
            return mPassword;
        }

        public void setPassword(String password) {
            this.mPassword = password;
        }

        public boolean getEmailVerified() {
            return mEmailVerified;
        }

        public void setEmailVerified(boolean emailVerified) {
            this.mEmailVerified = emailVerified;
        }

        public String getEmailVerificationCode() {
            return mEmailVerificationCode;
        }

        public void setEmailVerificationCode(String emailVerificationCode) {
            this.mEmailVerificationCode = emailVerificationCode;
        }

        public String getPhoneNumber() {
            return mPhoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.mPhoneNumber = phoneNumber;
        }

        public String getProfileimage() {
            return mProfileImage;
        }

        public void setProfileimage(String profileImage) {
            this.mProfileImage = profileImage;
        }

        public String getGender() {
            return mGender;
        }

        public void setGender(String gender) {
            this.mGender = gender;
        }

        public String getAge() {
            return mAge;
        }

        public void setAge(String age) {
            this.mAge = age;
        }

        public int getCountry() {
            return mCountry;
        }

        public void setCountry(int country) {
            this.mCountry = country;
        }

        public String getCity() {
            return mCity;
        }

        public void setCity(String city) {
            this.mCity = city;
        }

        public String getAddress() {
            return mAddress;
        }

        public void setAddress(String address) {
            this.mAddress = address;
        }

        public String getProfessionalSummary() {
            return mProfessionalSummary;
        }

        public void setProfessionalSummary(String professionalSummary) {
            this.mProfessionalSummary = professionalSummary;
        }

        public String getKeyAccomplishment() {
            return mKeyAccomplishment;
        }

        public void setKeyAccomplishment(String keyAccomplishment) {
            this.mKeyAccomplishment = keyAccomplishment;
        }

        public String getLanguages() {
            return mLanguages;
        }

        public void setLanguages(String languages) {
            this.mLanguages = languages;
        }

        public String getYearsOfExperience() {
            return mYearsOfExperience;
        }

        public void setYearsOfExperience(String yearsOfExperience) {
            this.mYearsOfExperience = yearsOfExperience;
        }

        public List<String> getSpecialityTopic() {
            return mSpecialityTopics;
        }

        public void setSpecialityTopic(List<String> specialityTopics) {
            this.mSpecialityTopics = specialityTopics;
        }

        public ConsultingSlot getConsultingSlot() {
            return mConsultingSlot;
        }

        public void setConsultingSlot(ConsultingSlot consultingSlot) {
            this.mConsultingSlot = consultingSlot;
        }

        public ConsultingSlotDuration getConsultingSlotDuration() {
            return mConsultingSlotDuration;
        }

        public void setConsultingSlotDuration(ConsultingSlotDuration consultingSlotDuration) {
            this.mConsultingSlotDuration = consultingSlotDuration;
        }

        public BankInfo getBankInfo() {
            return mBankInfo;
        }

        public void setBankInfo(BankInfo bankInfo) {
            this.mBankInfo = bankInfo;
        }

        public boolean getIsLoggedIn() {
            return mIsLoggedIn;
        }

        public void setIsLoggedIn(boolean isLoggedIn) {
            this.mIsLoggedIn = isLoggedIn;
        }

        public String getLastLogin() {
            return mLastLogin;
        }

        public void setLastLogin(String lastLogin) {
            this.mLastLogin = lastLogin;
        }

        public boolean getIsBlocked() {
            return mIsBlocked;
        }

        public void setIsBlocked(boolean isBlocked) {
            this.mIsBlocked = isBlocked;
        }

        public boolean getIsActive() {
            return mIsActive;
        }

        public void setIsActive(boolean isActive) {
            this.mIsActive = isActive;
        }

        public boolean getIsAdmin() {
            return mIsAdmin;
        }

        public void setIsAdmin(boolean isAdmin) {
            this.mIsAdmin = isAdmin;
        }

        public String getUserAgent() {
            return mUserAgent;
        }

        public void setUserAgent(String userAgent) {
            this.mUserAgent = userAgent;
        }

        public String getDeviceToken() {
            return mDeviceToken;
        }

        public void setDeviceToken(String deviceToken) {
            this.mDeviceToken = deviceToken;
        }

        public boolean getIsExpert() {
            return mIsExpert;
        }

        public void setIsExpert(boolean isExpert) {
            this.mIsExpert = isExpert;
        }

        public String getId() {
            return mId;
        }

        public void setId(String id) {
            this.mId = id;
        }

        public String getEmail() {
            return mEmail;
        }

        public void setEmail(String email) {
            this.mEmail = email;
        }

        public String getCreatedat() {
            return mCreatedAt;
        }

        public void setCreatedat(String createdAt) {
            this.mCreatedAt = createdAt;
        }

        public String getUpdatedat() {
            return mUpdatedAt;
        }

        public void setUpdatedat(String updatedAt) {
            this.mUpdatedAt = updatedAt;
        }

        public int getV() {
            return mV;
        }

        public void setV(int v) {
            this.mV = v;
        }
    }

//    static class Weekdays {
//        @Expose
//        @SerializedName("from1")
//        private String mFrom1;
//        @Expose
//        @SerializedName("to1")
//        private String mTo1;
//        @Expose
//        @SerializedName("from2")
//        private String mFrom2;
//        @Expose
//        @SerializedName("to2")
//        private String mTo2;
//
//        public String getFrom1() {
//            return mFrom1;
//        }
//
//        public void setFrom1(String mFrom1) {
//            this.mFrom1 = mFrom1;
//        }
//
//        public String getTo1() {
//            return mTo1;
//        }
//
//        public void setTo1(String mTo1) {
//            this.mTo1 = mTo1;
//        }
//
//        public String getFrom2() {
//            return mFrom2;
//        }
//
//        public void setFrom2(String mFrom2) {
//            this.mFrom2 = mFrom2;
//        }
//
//        public String getTo2() {
//            return mTo2;
//        }
//
//        public void setTo2(String mTo2) {
//            this.mTo2 = mTo2;
//        }
//    }

//    class Saturday {
//        @Expose
//        @SerializedName("from1")
//        private String mFrom1;
//        @Expose
//        @SerializedName("to1")
//        private String mTo1;
//        @Expose
//        @SerializedName("from2")
//        private String mFrom2;
//        @Expose
//        @SerializedName("to2")
//        private String mTo2;
//
//        public String getFrom1() {
//            return mFrom1;
//        }
//
//        public void setFrom1(String mFrom1) {
//            this.mFrom1 = mFrom1;
//        }
//
//        public String getTo1() {
//            return mTo1;
//        }
//
//        public void setTo1(String mTo1) {
//            this.mTo1 = mTo1;
//        }
//
//        public String getFrom2() {
//            return mFrom2;
//        }
//
//        public void setFrom2(String mFrom2) {
//            this.mFrom2 = mFrom2;
//        }
//
//        public String getTo2() {
//            return mTo2;
//        }
//
//        public void setTo2(String mTo2) {
//            this.mTo2 = mTo2;
//        }
//    }

//    static class Sunday {
//        @Expose
//        @SerializedName("from1")
//        private String mFrom1;
//        @Expose
//        @SerializedName("to1")
//        private String mTo1;
//        @Expose
//        @SerializedName("from2")
//        private String mFrom2;
//        @Expose
//        @SerializedName("to2")
//        private String mTo2;
//
//        public String getFrom1() {
//            return mFrom1;
//        }
//
//        public void setFrom1(String mFrom1) {
//            this.mFrom1 = mFrom1;
//        }
//
//        public String getTo1() {
//            return mTo1;
//        }
//
//        public void setTo1(String mTo1) {
//            this.mTo1 = mTo1;
//        }
//
//        public String getFrom2() {
//            return mFrom2;
//        }
//
//        public void setFrom2(String mFrom2) {
//            this.mFrom2 = mFrom2;
//        }
//
//        public String getTo2() {
//            return mTo2;
//        }
//
//        public void setTo2(String mTo2) {
//            this.mTo2 = mTo2;
//        }
//    }
}
