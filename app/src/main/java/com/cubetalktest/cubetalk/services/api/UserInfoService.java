package com.cubetalktest.cubetalk.services.api;

import com.cubetalktest.cubetalk.models.user_info.UserInfoFetchResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface UserInfoService {

    @GET("api/users/user-info/{user_id}")
    Call<UserInfoFetchResponse> getUserInfo(@Path("user_id") String id, @Header("token") String token);
}
