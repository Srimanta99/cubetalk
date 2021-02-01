package com.cubetalktest.cubetalk.models.earning_statics;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EarningStaticesResponse {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public class Datum {

        @SerializedName("bookingsList")
        @Expose
        private List<BookingsList> bookingsList = null;
        @SerializedName("TotalCountInMOnth")
        @Expose
        private List<TotalCountInMOnth> totalCountInMOnth = null;
        @SerializedName("LastFYIncome")
        @Expose
        private List<Object> lastFYIncome = null;
        @SerializedName("YTDIncome")
        @Expose
        private List<YTDIncome> yTDIncome = null;

        public List<BookingsList> getBookingsList() {
            return bookingsList;
        }

        public void setBookingsList(List<BookingsList> bookingsList) {
            this.bookingsList = bookingsList;
        }

        public List<TotalCountInMOnth> getTotalCountInMOnth() {
            return totalCountInMOnth;
        }

        public void setTotalCountInMOnth(List<TotalCountInMOnth> totalCountInMOnth) {
            this.totalCountInMOnth = totalCountInMOnth;
        }

        public List<Object> getLastFYIncome() {
            return lastFYIncome;
        }

        public void setLastFYIncome(List<Object> lastFYIncome) {
            this.lastFYIncome = lastFYIncome;
        }

        public List<YTDIncome> getYTDIncome() {
            return yTDIncome;
        }

        public void setYTDIncome(List<YTDIncome> yTDIncome) {
            this.yTDIncome = yTDIncome;
        }

    }

    public class BookingsList {

        @SerializedName("expert_share_amount")
        @Expose
        private Integer expertShareAmount;
        @SerializedName("expert_boookings")
        @Expose
        private Integer expertBoookings;
        @SerializedName("date")
        @Expose
        private String date;

        public Integer getExpertShareAmount() {
            return expertShareAmount;
        }

        public void setExpertShareAmount(Integer expertShareAmount) {
            this.expertShareAmount = expertShareAmount;
        }

        public Integer getExpertBoookings() {
            return expertBoookings;
        }

        public void setExpertBoookings(Integer expertBoookings) {
            this.expertBoookings = expertBoookings;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

    }
    public class TotalCountInMOnth {

        @SerializedName("total_amount")
        @Expose
        private Integer totalAmount;
        @SerializedName("total_boookings")
        @Expose
        private Integer totalBoookings;

        public Integer getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(Integer totalAmount) {
            this.totalAmount = totalAmount;
        }

        public Integer getTotalBoookings() {
            return totalBoookings;
        }

        public void setTotalBoookings(Integer totalBoookings) {
            this.totalBoookings = totalBoookings;
        }

    }
    public class YTDIncome {

        @SerializedName("total_YTD_income")
        @Expose
        private Integer totalYTDIncome;

        public Integer getTotalYTDIncome() {
            return totalYTDIncome;
        }

        public void setTotalYTDIncome(Integer totalYTDIncome) {
            this.totalYTDIncome = totalYTDIncome;
        }

    }
}
