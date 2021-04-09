package com.cubetalktest.cubetalk.services.api;

import com.cubetalktest.cubetalk.models.login_user.LoginRequest;
import com.cubetalktest.cubetalk.models.login_user.LoginResponse;
import com.cubetalktest.cubetalk.models.updateremaining_time.CallRemainingTimeResponse;
import com.cubetalktest.cubetalk.models.updateremaining_time.CallTimeReaminingRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ReamningTimeService {
    @POST("/api/bookings/update-call-end-time")
    Call<CallRemainingTimeResponse> login(@Body CallTimeReaminingRequest callTimeReaminingRequest);
}
