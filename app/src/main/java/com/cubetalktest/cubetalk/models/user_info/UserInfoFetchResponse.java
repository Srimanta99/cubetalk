package com.cubetalktest.cubetalk.models.user_info;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import com.cubetalktest.cubetalk.models.common.BankInfo;
import com.cubetalktest.cubetalk.models.common.ConsultingSlot;
import com.cubetalktest.cubetalk.models.common.ConsultingSlotDuration;
import com.cubetalktest.cubetalk.models.common.ExpertDocuments;
import com.cubetalktest.cubetalk.models.common.SpecialityTopic;

public class UserInfoFetchResponse {

    @Expose
    @SerializedName("success")
    private boolean mSuccess;

    @Expose
    @SerializedName("message")
    private String mMessage;

    @Expose
    @SerializedName("data")
    private Data mData;

    public boolean getSuccess() {
        return mSuccess;
    }

    public void setSuccess(boolean success) {
        this.mSuccess = success;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        this.mMessage = message;
    }

    public Data getData() {
        return mData;
    }

    public void setData(Data data) {
        this.mData = data;
    }

    public static class Data {

        @Expose
        @SerializedName("_id")
        private String mId;

        @Expose
        @SerializedName("updatedAt")
        private String mUpdatedAt;

        @Expose
        @SerializedName("createdAt")
        private String mCreatedAt;

        @Expose
        @SerializedName("email")
        private String mEmail;

        @Expose
        @SerializedName("consulting_slot_price")
        private ConsultingSlotPrice mConsultingSlotPrice;

        @Expose
        @SerializedName("name")
        private String mName;

        @Expose
        @SerializedName("email_verified")
        private boolean mEmailVerified;

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
        @SerializedName("key_accomplishment")
        private String mKeyAccomplishment;

        @Expose
        @SerializedName("professional_summery")
        private String mProfessionalSummary;

        @Expose
        @SerializedName("languages")
        private String mLanguages;

        @Expose
        @SerializedName("years_of_experience")
        private int mYearsOfExperience;

        @Expose
        @SerializedName("speciality_topic")
        private List<SpecialityTopic> mSpecialityTopics;

        @Expose
        @SerializedName("consulting_slot")
        private ConsultingSlot mConsultingSlot;

        @Expose
        @SerializedName("consulting_slot_duration")
        private ConsultingSlotDuration mConsultingSlotDuration;

        @Expose
        @SerializedName("expert_documents")
        private ExpertDocuments mExpertDocuments;

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
        @SerializedName("is_expert")
        private boolean mIsExpert;

        @Expose
        @SerializedName("is_expert_applied")
        private boolean mIsExpertApplied;

        @Expose
        @SerializedName("__v")
        private int mV;



        @Expose
        @SerializedName("is_future_price_slot_time")
        private  boolean mIsFuturePriceSlotTime;

        @Expose
        @SerializedName("expert_service_start_date")
        private String mExpertServiceStartDate;

        @Expose
        @SerializedName("future_price_slot_time_date")
        private  String mFuturePriceSlotTimeDate;

        @Expose
        @SerializedName("future_consulting_slot_price")
        private  FatureConsultingSlotPrice mFutureConsultingSlotPrice;
        @Expose
        @SerializedName("future_consulting_slot")
        private  ConsultingSlot mFutureConsultingSlot;


        public ConsultingSlot getmFutureConsultingSlot() {
            return mFutureConsultingSlot;
        }

        public void setmFutureConsultingSlot(ConsultingSlot mFutureConsultingSlot) {
            this.mFutureConsultingSlot = mFutureConsultingSlot;
        }



        public FatureConsultingSlotPrice getmFutureConsultingSlotPrice() {
            return mFutureConsultingSlotPrice;
        }

        public void setmFutureConsultingSlotPrice(FatureConsultingSlotPrice mFutureConsultingSlotPrice) {
            this.mFutureConsultingSlotPrice = mFutureConsultingSlotPrice;
        }




        public FutureConsultingSlotDuration getmFatureSlotDuration() {
            return mFatureSlotDuration;
        }

        public void setmFatureSlotDuration(FutureConsultingSlotDuration mFatureSlotDuration) {
            this.mFatureSlotDuration = mFatureSlotDuration;
        }

        @Expose
        @SerializedName("future_consulting_slot_duration")
        private  FutureConsultingSlotDuration mFatureSlotDuration;



        public String getmFuturePriceSlotTimeDate() {
            return mFuturePriceSlotTimeDate;
        }

        public void setmFuturePriceSlotTimeDate(String mFuturePriceSlotTimeDate) {
            this.mFuturePriceSlotTimeDate = mFuturePriceSlotTimeDate;
        }


        public boolean ismIsFuturePriceSlotTime() {
            return mIsFuturePriceSlotTime;
        }

        public void setmIsFuturePriceSlotTime(boolean mIsFuturePriceSlotTime) {
            this.mIsFuturePriceSlotTime = mIsFuturePriceSlotTime;
        }
        public String getName() {
            return mName;
        }

        public void setName(String name) {
            this.mName = name;
        }

        public boolean getEmailVerified() {
            return mEmailVerified;
        }

        public void setEmailVerified(boolean emailVerified) {
            this.mEmailVerified = emailVerified;
        }

        public String getPhoneNumber() {
            return mPhoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.mPhoneNumber = phoneNumber;
        }

        public String getProfileImage() {
            return mProfileImage;
        }

        public void setProfileImage(String profileImage) {
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

        public int getYearsOfExperience() {
            return mYearsOfExperience;
        }

        public void setYearsOfExperience(int yearsOfExperience) {
            this.mYearsOfExperience = yearsOfExperience;
        }

        public List<SpecialityTopic> getSpecialityTopics() {
            return mSpecialityTopics;
        }

        public void setSpecialityTopics(List<SpecialityTopic> specialityTopics) {
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

        public ExpertDocuments getExpertDocuments() {
            return mExpertDocuments;
        }

        public void setExpertDocuments(ExpertDocuments expertDocuments) {
            this.mExpertDocuments = expertDocuments;
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

        public boolean getIsExpert() {
            return mIsExpert;
        }

        public void setIsExpert(boolean isExpert) {
            this.mIsExpert = isExpert;
        }

        public boolean getIsExpertApplied() {
            return mIsExpertApplied;
        }

        public void setIsExpertApplied(boolean isExpertApplied) {
            this.mIsExpertApplied = isExpertApplied;
        }

        public int getV() {
            return mV;
        }

        public void setV(int v) {
            this.mV = v;
        }

        public String getEmail() {
            return mEmail;
        }

        public void setEmail(String email) {
            this.mEmail = email;
        }

        public String getCreatedAt() {
            return mCreatedAt;
        }

        public void setCreatedAt(String createdAt) {
            this.mCreatedAt = createdAt;
        }

        public String getUpdatedAt() {
            return mUpdatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.mUpdatedAt = updatedAt;
        }

        public String getId() {
            return mId;
        }

        public void setId(String id) {
            this.mId = id;
        }

        public String getProfessionalSummary() {
            return mProfessionalSummary;
        }

        public void setProfessionalSummary(String professionalSummary) {
            this.mProfessionalSummary = professionalSummary;
        }

        public ConsultingSlotPrice getConsultingSlotPrice() {
            return mConsultingSlotPrice;
        }

        public void setConsultingSlotPrice(ConsultingSlotPrice consultingSlotPrice) {
            this.mConsultingSlotPrice = consultingSlotPrice;
        }

        public String getExpertServiceStartDate() {
            return mExpertServiceStartDate;
        }

        public void setExpertServiceStartDate(String expertServiceStartDate) {
            this.mExpertServiceStartDate = expertServiceStartDate;
        }
    }

    public static class ConsultingSlotPrice {
        @Expose
        @SerializedName("duration_50")
        private DurationPrice mDuration50;
        @Expose
        @SerializedName("duration_25")
        private DurationPrice mDuration25;
        @Expose
        @SerializedName("duration_12")
        private DurationPrice mDuration12;

        public DurationPrice getDuration50() {
            return mDuration50;
        }

        public void setDuration50(DurationPrice duration50) {
            this.mDuration50 = duration50;
        }

        public DurationPrice getDuration25() {
            return mDuration25;
        }

        public void setDuration25(DurationPrice duration25) {
            this.mDuration25 = duration25;
        }

        public DurationPrice getDuration12() {
            return mDuration12;
        }

        public void setDuration12(DurationPrice duration12) {
            this.mDuration12 = duration12;
        }
    }

    public static class DurationPrice {
        @Expose
        @SerializedName("NRI")
        private Integer mNri;
        @Expose
        @SerializedName("INR")
        private Integer mInr;
        @Expose
        @SerializedName("status")
        private boolean mStatus;

        public Integer getNri() {
            return mNri;
        }

        public void setNri(Integer nri) {
            this.mNri = nri;
        }

        public Integer getInr() {
            return mInr;
        }

        public void setInr(Integer inr) {
            this.mInr = inr;
        }

        public boolean getStatus() {
            return mStatus;
        }

        public void setStatus(boolean status) {
            this.mStatus = status;
        }
    }

    public static class FatureConsultingSlotPrice {
        @Expose
        @SerializedName("duration_50")
        private DurationPrice mDuration50;
        @Expose
        @SerializedName("duration_25")
        private DurationPrice mDuration25;
        @Expose
        @SerializedName("duration_12")
        private DurationPrice mDuration12;

        public DurationPrice getDuration50() {
            return mDuration50;
        }

        public void setDuration50(DurationPrice duration50) {
            this.mDuration50 = duration50;
        }

        public DurationPrice getDuration25() {
            return mDuration25;
        }

        public void setDuration25(DurationPrice duration25) {
            this.mDuration25 = duration25;
        }

        public DurationPrice getDuration12() {
            return mDuration12;
        }

        public void setDuration12(DurationPrice duration12) {
            this.mDuration12 = duration12;
        }
    }
}
