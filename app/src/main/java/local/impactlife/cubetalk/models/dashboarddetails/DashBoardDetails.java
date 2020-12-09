package local.impactlife.cubetalk.models.dashboarddetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DashBoardDetails {
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

    public class Data{
        @SerializedName("countByWeek")
        @Expose
        private List<CountByWeek> countByWeek = null;
        @SerializedName("countByMonth")
        @Expose
        private List<CountByMonth> countByMonth = null;
        @SerializedName("upComingBookings")
        @Expose
        private List<UpComingBooking> upComingBookings = null;

        public List<CountByWeek> getCountByWeek() {
            return countByWeek;
        }

        public void setCountByWeek(List<CountByWeek> countByWeek) {
            this.countByWeek = countByWeek;
        }

        public List<CountByMonth> getCountByMonth() {
            return countByMonth;
        }

        public void setCountByMonth(List<CountByMonth> countByMonth) {
            this.countByMonth = countByMonth;
        }

        public List<UpComingBooking> getUpComingBookings() {
            return upComingBookings;
        }

        public void setUpComingBookings(List<UpComingBooking> upComingBookings) {
            this.upComingBookings = upComingBookings;
        }
    }

    public class CountByMonth{
        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("total_boookings")
        @Expose
        private Integer totalBoookings;
        @SerializedName("total_expert_share_amount")
        @Expose
        private Float totalExpertShareAmount;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Integer getTotalBoookings() {
            return totalBoookings;
        }

        public void setTotalBoookings(Integer totalBoookings) {
            this.totalBoookings = totalBoookings;
        }

        public Float getTotalExpertShareAmount() {
            return totalExpertShareAmount;
        }

        public void setTotalExpertShareAmount(Float totalExpertShareAmount) {
            this.totalExpertShareAmount = totalExpertShareAmount;
        }
    }

    public class CountByWeek {
        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("total_boookings")
        @Expose
        private Integer totalBoookings;
        @SerializedName("total_expert_share_amount")
        @Expose
        private Float totalExpertShareAmount;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Integer getTotalBoookings() {
            return totalBoookings;
        }

        public void setTotalBoookings(Integer totalBoookings) {
            this.totalBoookings = totalBoookings;
        }

        public Float getTotalExpertShareAmount() {
            return totalExpertShareAmount;
        }

        public void setTotalExpertShareAmount(Float totalExpertShareAmount) {
            this.totalExpertShareAmount = totalExpertShareAmount;
        }

    }

    public class UpComingBooking {

        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("count")
        @Expose
        private Integer count;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

    }
}
