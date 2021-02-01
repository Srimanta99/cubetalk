package com.cubetalktest.cubetalk.models.get_user_future_bookings;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import com.cubetalktest.cubetalk.models.common.Booking;

public class FutureBookingResponse {

    @Expose
    @SerializedName("success")
    private boolean mSuccess;

    @Expose
    @SerializedName("data")
    private List<Booking> mFutureBookings;

    public boolean isSuccess() {
        return mSuccess;
    }

    public void setSuccess(boolean success) {
        mSuccess = success;
    }

    public List<Booking> getFutureBookings() {
        return mFutureBookings;
    }

    public void setFutureBookings(List<Booking> futureBookings) {
        mFutureBookings = futureBookings;
    }

//    public static class Expert {
//        @Expose
//        @SerializedName("_id")
//        private String mId;
//        @Expose
//        @SerializedName("profileimage")
//        private String mProfileImage;
//        @Expose
//        @SerializedName("name")
//        private String mName;
//
//        public String getId() {
//            return mId;
//        }
//
//        public void setId(String id) {
//            mId = id;
//        }
//
//        public String getProfileImage() {
//            return mProfileImage;
//        }
//
//        public void setProfileImage(String profileImage) {
//            mProfileImage = profileImage;
//        }
//
//        public String getName() {
//            return mName;
//        }
//
//        public void setName(String name) {
//            mName = name;
//        }
//    }
}
