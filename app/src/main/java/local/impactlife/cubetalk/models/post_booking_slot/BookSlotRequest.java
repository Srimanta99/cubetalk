package local.impactlife.cubetalk.models.post_booking_slot;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookSlotRequest implements Parcelable {

    public static final String TAG = BookSlotRequest.class.getSimpleName();

    @Expose
    @SerializedName("booking_user_id")
    private String mBookingUserId;

    @Expose
    @SerializedName("booked_expert_id")
    private String mBookedExpertId;

    private String mExpertImage;

    private String mExpertName;

    private String mExpertSpecialities;

    @Expose
    @SerializedName("slot_time")
    private String mSlotTime;

    @Expose
    @SerializedName("slot_date")
    private String mSlotDate;

    @Expose
    @SerializedName("slot_type")
    private String mSlotType;

    @Expose
    @SerializedName("payment_mode")
    private String mPaymentMode;

    @Expose
    @SerializedName("coupon_code")
    private String mCouponCode;

    @Expose
    @SerializedName("coupon_discount")
    private String mCouponDiscount;

    @Expose
    @SerializedName("amount_paid")
    private String mAmountPaid;

    @Expose
    @SerializedName("status")
    private int mStatus;

    @Expose
    @SerializedName("user_name")
    private String mUserName;

    @Expose
    @SerializedName("user_email")
    private String mUserEmail;

    @Expose
    @SerializedName("user_phone")
    private String mUserPhone;

    @Expose
    @SerializedName("payment_status")
    private String mPaymentStatus;

    @Expose
    @SerializedName("speciality_id")
    private String mSpecialityId;

    @Expose
    @SerializedName("topic_id")
    private String mTopicId;

    @Expose
    @SerializedName("amount_currency_type")
    private String amount_currency_type;

    public boolean isCreditShellApplied() {
        return isCreditShellApplied;
    }

    public void setCreditShellApplied(boolean creditShellApplied) {
        isCreditShellApplied = creditShellApplied;
    }

    public int getTotalAmountIncludeCreditShell() {
        return totalAmountIncludeCreditShell;
    }

    public void setTotalAmountIncludeCreditShell(int totalAmountIncludeCreditShell) {
        this.totalAmountIncludeCreditShell = totalAmountIncludeCreditShell;
    }

    public int getCreditShellamount() {
        return creditShellamount;
    }

    public void setCreditShellamount(int creditShellamount) {
        this.creditShellamount = creditShellamount;
    }

    @Expose
    @SerializedName("is_credit_shell_applied")
    private boolean isCreditShellApplied;

    @Expose
    @SerializedName("total_amount_incl_creditshell")
    private int totalAmountIncludeCreditShell;

    @Expose
    @SerializedName("credit_shell_amount")
    private int creditShellamount;

    public String getAmount_currency_type() {
        return amount_currency_type;
    }

    public void setAmount_currency_type(String amount_currency_type) {
        this.amount_currency_type = amount_currency_type;
    }



    public BookSlotRequest() {
    }

    protected BookSlotRequest(Parcel in) {
        mBookingUserId = in.readString();
        mBookedExpertId = in.readString();
        mExpertImage = in.readString();
        mExpertName = in.readString();
        mExpertSpecialities = in.readString();
        mSlotTime = in.readString();
        mSlotDate = in.readString();
        mSlotType = in.readString();
        mPaymentMode = in.readString();
        mCouponCode = in.readString();
        mCouponDiscount = in.readString();
        mAmountPaid = in.readString();
        mStatus = in.readInt();
        mUserName = in.readString();
        mUserEmail = in.readString();
        mUserPhone = in.readString();
        mPaymentStatus = in.readString();
        mSpecialityId = in.readString();
        mTopicId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mBookingUserId);
        dest.writeString(mBookedExpertId);
        dest.writeString(mExpertImage);
        dest.writeString(mExpertName);
        dest.writeString(mExpertSpecialities);
        dest.writeString(mSlotTime);
        dest.writeString(mSlotDate);
        dest.writeString(mSlotType);
        dest.writeString(mPaymentMode);
        dest.writeString(mCouponCode);
        dest.writeString(mCouponDiscount);
        dest.writeString(mAmountPaid);
        dest.writeInt(mStatus);
        dest.writeString(mUserName);
        dest.writeString(mUserEmail);
        dest.writeString(mUserPhone);
        dest.writeString(mPaymentStatus);
        dest.writeString(mSpecialityId);
        dest.writeString(mTopicId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BookSlotRequest> CREATOR = new Creator<BookSlotRequest>() {
        @Override
        public BookSlotRequest createFromParcel(Parcel in) {
            return new BookSlotRequest(in);
        }

        @Override
        public BookSlotRequest[] newArray(int size) {
            return new BookSlotRequest[size];
        }
    };

    public String getBookingUserId() {
        return mBookingUserId;
    }

    public void setBookingUserId(String bookingUserId) {
        mBookingUserId = bookingUserId;
    }

    public String getBookedExpertId() {
        return mBookedExpertId;
    }

    public void setBookedExpertId(String bookedExpertId) {
        mBookedExpertId = bookedExpertId;
    }

    public String getExpertImage() {
        return mExpertImage;
    }

    public void setExpertImage(String expertImage) {
        mExpertImage = expertImage;
    }

    public String getExpertName() {
        return mExpertName;
    }

    public void setExpertName(String expertName) {
        mExpertName = expertName;
    }

    public String getExpertSpecialities() {
        return mExpertSpecialities;
    }

    public void setExpertSpecialities(String expertSpecialities) {
        mExpertSpecialities = expertSpecialities;
    }

    public String getSlotTime() {
        return mSlotTime;
    }

    public void setSlotTime(String slotTime) {
        mSlotTime = slotTime;
    }

    public String getSlotDate() {
        return mSlotDate;
    }

    public void setSlotDate(String slotDate) {
        mSlotDate = slotDate;
    }

    public String getSlotType() {
        return mSlotType;
    }

    public void setSlotType(String slotType) {
        mSlotType = slotType;
    }

    public String getPaymentMode() {
        return mPaymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        mPaymentMode = paymentMode;
    }

    public String getCouponCode() {
        return mCouponCode;
    }

    public void setCouponCode(String couponCode) {
        mCouponCode = couponCode;
    }

    public String getCouponDiscount() {
        return mCouponDiscount;
    }

    public void setCouponDiscount(String couponDiscount) {
        mCouponDiscount = couponDiscount;
    }

    public String getAmountPaid() {
        return mAmountPaid;
    }

    public void setAmountPaid(String amountPaid) {
        mAmountPaid = amountPaid;
    }

    public int getStatus() {
        return mStatus;
    }

    public void setStatus(int status) {
        mStatus = status;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public String getUserEmail() {
        return mUserEmail;
    }

    public void setUserEmail(String userEmail) {
        mUserEmail = userEmail;
    }

    public String getUserPhone() {
        return mUserPhone;
    }

    public void setUserPhone(String userPhone) {
        mUserPhone = userPhone;
    }

    public String getPaymentStatus() {
        return mPaymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        mPaymentStatus = paymentStatus;
    }

    public String getSpecialityId() {
        return mSpecialityId;
    }

    public void setSpecialityId(String specialityId) {
        mSpecialityId = specialityId;
    }

    public String getTopicId() {
        return mTopicId;
    }

    public void setTopicId(String topicId) {
        mTopicId = topicId;
    }
}
