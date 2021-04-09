package com.cubetalktest.cubetalk.models.updateremaining_time;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CallTimeReaminingRequest {

    @Expose
    @SerializedName("booked_id")
    private String bookedTime;

    @Expose
    @SerializedName("conference_finish_time")
    private String conferenceFinishTime;

    public String getBookedTime() {
        return bookedTime;
    }

    public void setBookedTime(String bookedTime) {
        this.bookedTime = bookedTime;
    }

    public String getConferenceFinishTime() {
        return conferenceFinishTime;
    }

    public void setConferenceFinishTime(String conferenceFinishTime) {
        this.conferenceFinishTime = conferenceFinishTime;
    }








}
