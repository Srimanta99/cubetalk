package com.cubetalktest.cubetalk.models.get_booking_time_slots;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TimeSlotsResponse {

    @Expose
    @SerializedName("data")
    private Data mData;

    @Expose
    @SerializedName("message")
    private String mMessage;

    @Expose
    @SerializedName("success")
    private boolean mSuccess;

    public Data getData() {
        return mData;
    }

    public void setData(Data mData) {
        this.mData = mData;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String mMessage) {
        this.mMessage = mMessage;
    }

    public boolean getSuccess() {
        return mSuccess;
    }

    public void setSuccess(boolean mSuccess) {
        this.mSuccess = mSuccess;
    }

    public static class Data {

        @Expose
        @SerializedName("date")
        private String mDate;

        @Expose
        @SerializedName("available_dates")
        private List<AvailableDate> mAvailableDate;

        @Expose
        @SerializedName("slot_weekdays")
        private SlotWeekdays mSlotWeekdays;

        @Expose
        @SerializedName("slot_saturday")
        private SlotSaturday mSlotSaturday;

        @Expose
        @SerializedName("slot_sunday")
        private SlotSunday mSlotSunday;

        public String getDate() {
            return mDate;
        }

        public void setDate(String date) {
            this.mDate = date;
        }

        public List<AvailableDate> getAvailableDate() {
            return mAvailableDate;
        }

        public void setAvailableDate(List<AvailableDate> availableDates) {
            this.mAvailableDate = availableDates;
        }

        public SlotWeekdays getSlotWeekdays() {
            return mSlotWeekdays;
        }

        public void setSlotWeekdays(SlotWeekdays slotWeekdays) {
            this.mSlotWeekdays = slotWeekdays;
        }

        public SlotSaturday getSlotSaturday() {
            return mSlotSaturday;
        }

        public void setSlotSaturday(SlotSaturday slotSaturday) {
            this.mSlotSaturday = slotSaturday;
        }

        public SlotSunday getSlotSunday() {
            return mSlotSunday;
        }

        public void setSlotSunday(SlotSunday slotSunday) {
            this.mSlotSunday = slotSunday;
        }
    }

    public static class AvailableDate{

        @Expose
        @SerializedName("date")
        private String mDate;

        @Expose
        @SerializedName("day_string")
        private String mDay;

        public String getDate() {
            return mDate;
        }

        public void setDate(String date) {
            this.mDate = date;
        }

        public String getDay() {
            return mDay;
        }

        public void setDay(String day) {
            this.mDay = day;
        }
    }

    public static class SlotWeekdays {

        @Expose
        @SerializedName("first_phase")
        private List<String> mFirstPhase;

        @Expose
        @SerializedName("second_phase")
        private List<String> mSecondPhase;

        public List<String> getFirstPhase() {
            return mFirstPhase;
        }

        public void setFirstPhase(List<String> firstPhase) {
            this.mFirstPhase = firstPhase;
        }

        public List<String> getSecondPhase() {
            return mSecondPhase;
        }

        public void setSecondPhase(List<String> secondPhase) {
            this.mSecondPhase = secondPhase;
        }
    }

    public static class SlotSaturday {

        @Expose
        @SerializedName("first_phase")
        private List<String> mFirstPhase;

        @Expose
        @SerializedName("second_phase")
        private List<String> mSecondPhase;

        public List<String> getFirstPhase() {
            return mFirstPhase;
        }

        public void setFirstPhase(List<String> firstPhase) {
            this.mFirstPhase = firstPhase;
        }

        public List<String> getSecondPhase() {
            return mSecondPhase;
        }

        public void setSecondPhase(List<String> secondPhase) {
            this.mSecondPhase = secondPhase;
        }
    }

    public static class SlotSunday {

        @Expose
        @SerializedName("first_phase")
        private List<String> mFirstPhase;

        @Expose
        @SerializedName("second_phase")
        private List<String> mSecondPhase;

        public List<String> getFirstPhase() {
            return mFirstPhase;
        }

        public void setFirstPhase(List<String> firstPhase) {
            this.mFirstPhase = firstPhase;
        }

        public List<String> getSecondPhase() {
            return mSecondPhase;
        }

        public void setSecondPhase(List<String> secondPhase) {
            this.mSecondPhase = secondPhase;
        }
    }
}
