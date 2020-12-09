package local.impactlife.cubetalk.models.expert_review_refund;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExpertRefundResponse {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("data")
    @Expose
    private List<Data> data = null;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

   public class Data {

        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("createdAt")
        @Expose
        private String createdAt;
        @SerializedName("booked_id")
        @Expose
        private String bookedId;
        @SerializedName("booking_user_id")
        @Expose
        private BookingUserId bookingUserId;
        @SerializedName("topic_id")
        @Expose
        private TopicId topicId;
        @SerializedName("booking_cancel_status")
        @Expose
        private Integer bookingCancelStatus;
        @SerializedName("amount_paid")
        @Expose
        private Integer amountPaid;
        @SerializedName("slot_type")
        @Expose
        private String slotType;
        @SerializedName("slot_date")
        @Expose
        private String slotDate;
        @SerializedName("slot_time")
        @Expose
        private String slotTime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getBookedId() {
            return bookedId;
        }

        public void setBookedId(String bookedId) {
            this.bookedId = bookedId;
        }

        public BookingUserId getBookingUserId() {
            return bookingUserId;
        }

        public void setBookingUserId(BookingUserId bookingUserId) {
            this.bookingUserId = bookingUserId;
        }

        public TopicId getTopicId() {
            return topicId;
        }

        public void setTopicId(TopicId topicId) {
            this.topicId = topicId;
        }

        public Integer getBookingCancelStatus() {
            return bookingCancelStatus;
        }

        public void setBookingCancelStatus(Integer bookingCancelStatus) {
            this.bookingCancelStatus = bookingCancelStatus;
        }

        public Integer getAmountPaid() {
            return amountPaid;
        }

        public void setAmountPaid(Integer amountPaid) {
            this.amountPaid = amountPaid;
        }

        public String getSlotType() {
            return slotType;
        }

        public void setSlotType(String slotType) {
            this.slotType = slotType;
        }

        public String getSlotDate() {
            return slotDate;
        }

        public void setSlotDate(String slotDate) {
            this.slotDate = slotDate;
        }

        public String getSlotTime() {
            return slotTime;
        }

        public void setSlotTime(String slotTime) {
            this.slotTime = slotTime;
        }

    }

    public class BookingUserId {

        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("name")
        @Expose
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

    public class TopicId {

        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("name")
        @Expose
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }
}

