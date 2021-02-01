package com.cubetalktest.cubetalk.services.api;

import com.cubetalktest.cubetalk.models.dashboarddetails.DashBoardDetails;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ExpertDashBoardService {
    @GET("api/bookings/expert-dashboard-statistics/?")
    Call<DashBoardDetails> getdashboarddetails(@Header("token") String token, @Query("user_id") String userid);
}
