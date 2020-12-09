package local.impactlife.cubetalk.models.post_booking_slot;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookSlotResponse {

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
    private boolean mSuccess;

    public Data getData() {
        return mData;
    }

    public void setData(Data data) {
        mData = data;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public int getStatusCode() {
        return mStatusCode;
    }

    public void setStatusCode(int statusCode) {
        mStatusCode = statusCode;
    }

    public boolean isSuccess() {
        return mSuccess;
    }

    public void setSuccess(boolean success) {
        mSuccess = success;
    }

    public static class Data {

        @Expose
        @SerializedName("user_email")
        private String mUserEmail;

        @Expose
        @SerializedName("user_phone")
        private String mUserPhone;

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
        @SerializedName("payment_status")
        private String mPaymentStatus;

        @Expose
        @SerializedName("payment_mode")
        private String mPaymentMode;

        @Expose
        @SerializedName("amount_paid")
        private int mAmountPaid;

        @Expose
        @SerializedName("status")
        private int mStatus;

        @Expose
        @SerializedName("_id")
        private String mId;

        @Expose
        @SerializedName("booked_expert_id")
        private String mBookedExpertId;

        @Expose
        @SerializedName("user_name")
        private String mUserName;

        @Expose
        @SerializedName("booking_user_id")
        private String mBookingUserId;

        @Expose
        @SerializedName("booked_id")
        private String mBookedId;

        @Expose
        @SerializedName("createdAt")
        private String mCreatedAt;

        @Expose
        @SerializedName("updatedAt")
        private String mUpdatedAt;

        @Expose
        @SerializedName("__v")
        private int mV;

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

        public String getPaymentStatus() {
            return mPaymentStatus;
        }

        public void setPaymentStatus(String paymentStatus) {
            mPaymentStatus = paymentStatus;
        }

        public String getPaymentMode() {
            return mPaymentMode;
        }

        public void setPaymentMode(String paymentMode) {
            mPaymentMode = paymentMode;
        }

        public int getAmountPaid() {
            return mAmountPaid;
        }

        public void setAmountPaid(int amountPaid) {
            mAmountPaid = amountPaid;
        }

        public int getStatus() {
            return mStatus;
        }

        public void setStatus(int status) {
            mStatus = status;
        }

        public String getId() {
            return mId;
        }

        public void setId(String id) {
            mId = id;
        }

        public String getBookedExpertId() {
            return mBookedExpertId;
        }

        public void setBookedExpertId(String bookedExpertId) {
            mBookedExpertId = bookedExpertId;
        }

        public String getUserName() {
            return mUserName;
        }

        public void setUserName(String userName) {
            mUserName = userName;
        }

        public String getBookingUserId() {
            return mBookingUserId;
        }

        public void setBookingUserId(String bookingUserId) {
            mBookingUserId = bookingUserId;
        }

        public String getBookedId() {
            return mBookedId;
        }

        public void setBookedId(String bookedId) {
            mBookedId = bookedId;
        }

        public String getCreatedAt() {
            return mCreatedAt;
        }

        public void setCreatedAt(String createdAt) {
            mCreatedAt = createdAt;
        }

        public String getUpdatedAt() {
            return mUpdatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            mUpdatedAt = updatedAt;
        }

        public int getV() {
            return mV;
        }

        public void setV(int v) {
            mV = v;
        }
    }
}
