package com.cubetalktest.cubetalk.services.api;

import com.cubetalktest.cubetalk.models.register_user.UserRegistrationRequest;
import com.cubetalktest.cubetalk.models.register_user.UserRegistrationResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserRegistrationService {
    @POST("api/users/user_signup")
    Call<UserRegistrationResponse> register(@Body UserRegistrationRequest userRegistrationRequest);
}
