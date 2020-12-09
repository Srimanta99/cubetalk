package local.impactlife.cubetalk.models.expert_registration;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import local.impactlife.cubetalk.models.common.ConsultingSlot;
import local.impactlife.cubetalk.models.common.ConsultingSlotDuration;
import local.impactlife.cubetalk.models.common.Speciality;
import local.impactlife.cubetalk.models.common.Topic;

public class ExpertRegistrationRequest implements Parcelable {

    public static final String EXPERT = "Expert";

    @Expose
    @SerializedName("name")
    private String mName;

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
    private String mConsultationLanguages;

    @Expose
    @SerializedName("years_of_experience")
    private int mYearsOfExperience;

    private Uri mExpertCvDocumentUri;

    private String mExpertKycOneDocumentTitle;

    private Uri mExpertKycOneDocumentUri;

    private String mExpertKycTwoDocumentTitle;

    private Uri mExpertKycTwoDocumentUri;

    @Expose
    @SerializedName("bank_name")
    private String mBankName;

    @Expose
    @SerializedName("bank_account_number")
    private String mBankAccountNumber;

    @Expose
    @SerializedName("bank_ifsc")
    private String mBankIfsc;

    @Expose
    @SerializedName("specialities")
    private ArrayList<ConsultingTopic> mConsultingTopics;

    @Expose
    @SerializedName("consulting_slot")
    private ConsultingSlot mConsultingSlot;

    @Expose
    @SerializedName("consulting_slot_duration")
    private ConsultingSlotDuration mConsultingSlotDuration;

    public ExpertRegistrationRequest() {

    }

    protected ExpertRegistrationRequest(Parcel in) {
        mName = in.readString();
        mAddress = in.readString();
        mProfessionalSummary = in.readString();
        mKeyAccomplishment = in.readString();
        mConsultationLanguages = in.readString();
        mYearsOfExperience = in.readInt();
        mExpertCvDocumentUri = in.readParcelable(Uri.class.getClassLoader());
        mExpertKycOneDocumentTitle = in.readString();
        mExpertKycOneDocumentUri = in.readParcelable(Uri.class.getClassLoader());
        mExpertKycTwoDocumentTitle = in.readString();
        mExpertKycTwoDocumentUri = in.readParcelable(Uri.class.getClassLoader());
        mBankName = in.readString();
        mBankAccountNumber = in.readString();
        mBankIfsc = in.readString();
        mConsultingTopics = in.createTypedArrayList(ConsultingTopic.CREATOR);
        mConsultingSlot = in.readParcelable(ConsultingSlot.class.getClassLoader());
        mConsultingSlotDuration = in.readParcelable(ConsultingSlotDuration.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeString(mAddress);
        dest.writeString(mProfessionalSummary);
        dest.writeString(mKeyAccomplishment);
        dest.writeString(mConsultationLanguages);
        dest.writeInt(mYearsOfExperience);
        dest.writeParcelable(mExpertCvDocumentUri, flags);
        dest.writeString(mExpertKycOneDocumentTitle);
        dest.writeParcelable(mExpertKycOneDocumentUri, flags);
        dest.writeString(mExpertKycTwoDocumentTitle);
        dest.writeParcelable(mExpertKycTwoDocumentUri, flags);
        dest.writeString(mBankName);
        dest.writeString(mBankAccountNumber);
        dest.writeString(mBankIfsc);
        dest.writeTypedList(mConsultingTopics);
        dest.writeParcelable(mConsultingSlot, flags);
        dest.writeParcelable(mConsultingSlotDuration, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ExpertRegistrationRequest> CREATOR = new Creator<ExpertRegistrationRequest>() {
        @Override
        public ExpertRegistrationRequest createFromParcel(Parcel in) {
            return new ExpertRegistrationRequest(in);
        }

        @Override
        public ExpertRegistrationRequest[] newArray(int size) {
            return new ExpertRegistrationRequest[size];
        }
    };

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
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

    public String getConsultationLanguages() {
        return mConsultationLanguages;
    }

    public void setConsultationLanguages(String consultationLanguages) {
        this.mConsultationLanguages = consultationLanguages;
    }

    public int getYearsOfExperience() {
        return mYearsOfExperience;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        this.mYearsOfExperience = yearsOfExperience;
    }

    public Uri getExpertCvDocumentUri() {
        return mExpertCvDocumentUri;
    }

    public void setExpertCvDocumentUri(Uri expertCvDocumentUri) {
        this.mExpertCvDocumentUri = expertCvDocumentUri;
    }

    public String getExpertKycOneDocumentTitle() {
        return mExpertKycOneDocumentTitle;
    }

    public void setExpertKycOneDocumentTitle(String expertKycOneDocumentTitle) {
        this.mExpertKycOneDocumentTitle = expertKycOneDocumentTitle;
    }

    public Uri getExpertKycOneDocumentUri() {
        return mExpertKycOneDocumentUri;
    }

    public void setExpertKycOneDocumentUri(Uri expertKycOneDocumentUri) {
        this.mExpertKycOneDocumentUri = expertKycOneDocumentUri;
    }

    public String getExpertKycTwoDocumentTitle() {
        return mExpertKycTwoDocumentTitle;
    }

    public void setExpertKycTwoDocumentTitle(String expertKycTwoDocumentTitle) {
        this.mExpertKycTwoDocumentTitle = expertKycTwoDocumentTitle;
    }

    public Uri getExpertKycTwoDocumentUri() {
        return mExpertKycTwoDocumentUri;
    }

    public void setExpertKycTwoDocumentUri(Uri expertKycTwoDocumentUri) {
        this.mExpertKycTwoDocumentUri = expertKycTwoDocumentUri;
    }

    public String getBankName() {
        return mBankName;
    }

    public void setBankName(String bankName) {
        this.mBankName = bankName;
    }

    public String getBankAccountNumber() {
        return mBankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.mBankAccountNumber = bankAccountNumber;
    }

    public String getBankIfsc() {
        return mBankIfsc;
    }

    public void setBankIfsc(String bankIfsc) {
        this.mBankIfsc = bankIfsc;
    }

    public ArrayList<ConsultingTopic> getConsultingTopics() {
        return mConsultingTopics;
    }

    public void setConsultingTopics(ArrayList<ConsultingTopic> consultingTopics) {
        this.mConsultingTopics = consultingTopics;
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

//    public static class Speciality implements Parcelable {
//
//        @Expose
//        @SerializedName("id")
//        private String mId;
//
//        public Speciality() {
//        }
//
//        protected Speciality(Parcel in) {
//            mId = in.readString();
//        }
//
//        @Override
//        public void writeToParcel(Parcel dest, int flags) {
//            dest.writeString(mId);
//        }
//
//        @Override
//        public int describeContents() {
//            return 0;
//        }
//
//        public static final Creator<Speciality> CREATOR = new Creator<Speciality>() {
//            @Override
//            public Speciality createFromParcel(Parcel in) {
//                return new Speciality(in);
//            }
//
//            @Override
//            public Speciality[] newArray(int size) {
//                return new Speciality[size];
//            }
//        };
//
//        public String getId() {
//            return mId;
//        }
//
//        public void setId(String id) {
//            this.mId = id;
//        }
//    }

    public static class ConsultingTopic implements Parcelable {
        @Expose
        @SerializedName("speciality")
        private Speciality mSpeciality;
        @Expose
        @SerializedName("topic")
        private ArrayList<Topic> mTopics;

        public ConsultingTopic() {
            mTopics = new ArrayList<>();
        }

        protected ConsultingTopic(Parcel in) {
            mSpeciality = in.readParcelable(Speciality.class.getClassLoader());
            mTopics = in.createTypedArrayList(Topic.CREATOR);
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(mSpeciality, flags);
            dest.writeTypedList(mTopics);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<ConsultingTopic> CREATOR = new Creator<ConsultingTopic>() {
            @Override
            public ConsultingTopic createFromParcel(Parcel in) {
                return new ConsultingTopic(in);
            }

            @Override
            public ConsultingTopic[] newArray(int size) {
                return new ConsultingTopic[size];
            }
        };

        public Speciality getSpeciality() {
            return mSpeciality;
        }

        public void setSpeciality(Speciality speciality) {
            this.mSpeciality = speciality;
        }

        public ArrayList<Topic> getTopics() {
            return mTopics;
        }

        public void setTopics(ArrayList<Topic> topics) {
            this.mTopics = topics;
        }
    }

//    public static class Weekdays implements Parcelable {
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
//        public Weekdays() {
//        }
//
//        protected Weekdays(Parcel in) {
//            mFrom1 = in.readString();
//            mTo1 = in.readString();
//            mFrom2 = in.readString();
//            mTo2 = in.readString();
//        }
//
//        @Override
//        public void writeToParcel(Parcel dest, int flags) {
//            dest.writeString(mFrom1);
//            dest.writeString(mTo1);
//            dest.writeString(mFrom2);
//            dest.writeString(mTo2);
//        }
//
//        @Override
//        public int describeContents() {
//            return 0;
//        }
//
//        public static final Creator<Weekdays> CREATOR = new Creator<Weekdays>() {
//            @Override
//            public Weekdays createFromParcel(Parcel in) {
//                return new Weekdays(in);
//            }
//
//            @Override
//            public Weekdays[] newArray(int size) {
//                return new Weekdays[size];
//            }
//        };
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

//    public static class Saturday implements Parcelable {
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
//        public Saturday() {
//        }
//
//        protected Saturday(Parcel in) {
//            mFrom1 = in.readString();
//            mTo1 = in.readString();
//            mFrom2 = in.readString();
//            mTo2 = in.readString();
//        }
//
//        @Override
//        public void writeToParcel(Parcel dest, int flags) {
//            dest.writeString(mFrom1);
//            dest.writeString(mTo1);
//            dest.writeString(mFrom2);
//            dest.writeString(mTo2);
//        }
//
//        @Override
//        public int describeContents() {
//            return 0;
//        }
//
//        public static final Creator<Saturday> CREATOR = new Creator<Saturday>() {
//            @Override
//            public Saturday createFromParcel(Parcel in) {
//                return new Saturday(in);
//            }
//
//            @Override
//            public Saturday[] newArray(int size) {
//                return new Saturday[size];
//            }
//        };
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

//    public static class Sunday implements Parcelable {
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
//        public Sunday() {
//        }
//
//        protected Sunday(Parcel in) {
//            mFrom1 = in.readString();
//            mTo1 = in.readString();
//            mFrom2 = in.readString();
//            mTo2 = in.readString();
//        }
//
//        @Override
//        public void writeToParcel(Parcel dest, int flags) {
//            dest.writeString(mFrom1);
//            dest.writeString(mTo1);
//            dest.writeString(mFrom2);
//            dest.writeString(mTo2);
//        }
//
//        @Override
//        public int describeContents() {
//            return 0;
//        }
//
//        public static final Creator<Sunday> CREATOR = new Creator<Sunday>() {
//            @Override
//            public Sunday createFromParcel(Parcel in) {
//                return new Sunday(in);
//            }
//
//            @Override
//            public Sunday[] newArray(int size) {
//                return new Sunday[size];
//            }
//        };
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

//    public static class ConsultingSlot implements Parcelable {
//        @Expose
//        @SerializedName("weekdays")
//        private Weekdays mWeekdays;
//        @Expose
//        @SerializedName("saturday")
//        private Saturday mSaturday;
//        @Expose
//        @SerializedName("sunday")
//        private Sunday mSunday;
//
//        public ConsultingSlot() {
//
//        }
//
//        protected ConsultingSlot(Parcel in) {
//            mWeekdays = in.readParcelable(Weekdays.class.getClassLoader());
//            mSaturday = in.readParcelable(Saturday.class.getClassLoader());
//            mSunday = in.readParcelable(Sunday.class.getClassLoader());
//        }
//
//        @Override
//        public void writeToParcel(Parcel dest, int flags) {
//            dest.writeParcelable(mWeekdays, flags);
//            dest.writeParcelable(mSaturday, flags);
//            dest.writeParcelable(mSunday, flags);
//        }
//
//        @Override
//        public int describeContents() {
//            return 0;
//        }
//
//        public static final Creator<ConsultingSlot> CREATOR = new Creator<ConsultingSlot>() {
//            @Override
//            public ConsultingSlot createFromParcel(Parcel in) {
//                return new ConsultingSlot(in);
//            }
//
//            @Override
//            public ConsultingSlot[] newArray(int size) {
//                return new ConsultingSlot[size];
//            }
//        };
//
//        public Weekdays getWeekdays() {
//            return mWeekdays;
//        }
//
//        public void setWeekdays(Weekdays weekdays) {
//            this.mWeekdays = weekdays;
//        }
//
//        public Saturday getSaturday() {
//            return mSaturday;
//        }
//
//        public void setSaturday(Saturday saturday) {
//            this.mSaturday = saturday;
//        }
//
//        public Sunday getSunday() {
//            return mSunday;
//        }
//
//        public void setSunday(Sunday sunday) {
//            this.mSunday = sunday;
//        }
//    }

//    public static class ConsultingSlotDuration implements Parcelable {
//        @Expose
//        @SerializedName("duration_12")
//        private boolean mDuration12;
//        @Expose
//        @SerializedName("duration_25")
//        private boolean mDuration25;
//        @Expose
//        @SerializedName("duration_50")
//        private boolean mDuration50;
//
//        public ConsultingSlotDuration() {
//        }
//
//        protected ConsultingSlotDuration(Parcel in) {
//            mDuration12 = in.readByte() != 0;
//            mDuration25 = in.readByte() != 0;
//            mDuration50 = in.readByte() != 0;
//        }
//
//        @Override
//        public void writeToParcel(Parcel dest, int flags) {
//            dest.writeByte((byte) (mDuration12 ? 1 : 0));
//            dest.writeByte((byte) (mDuration25 ? 1 : 0));
//            dest.writeByte((byte) (mDuration50 ? 1 : 0));
//        }
//
//        @Override
//        public int describeContents() {
//            return 0;
//        }
//
//        public static final Creator<ConsultingSlotDuration> CREATOR = new Creator<ConsultingSlotDuration>() {
//            @Override
//            public ConsultingSlotDuration createFromParcel(Parcel in) {
//                return new ConsultingSlotDuration(in);
//            }
//
//            @Override
//            public ConsultingSlotDuration[] newArray(int size) {
//                return new ConsultingSlotDuration[size];
//            }
//        };
//
//        public boolean getDuration12() {
//            return mDuration12;
//        }
//
//        public void setDuration12(boolean mDuration12) {
//            this.mDuration12 = mDuration12;
//        }
//
//        public boolean getDuration25() {
//            return mDuration25;
//        }
//
//        public void setDuration25(boolean mDuration25) {
//            this.mDuration25 = mDuration25;
//        }
//
//        public boolean getDuration50() {
//            return mDuration50;
//        }
//
//        public void setDuration50(boolean mDuration50) {
//            this.mDuration50 = mDuration50;
//        }
//    }
}
