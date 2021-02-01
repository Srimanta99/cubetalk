package com.cubetalktest.cubetalk.services.api;

import com.cubetalktest.cubetalk.models.update_user.UpdateUserRequest;
import com.cubetalktest.cubetalk.models.update_user.UpdateUserResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UpdateUserService {



    @PUT("api/users/update_user/{user_id}")
    Call<UpdateUserResponse> updateUser(
            @Header("token") String token,
            @Path("user_id") String id,
            @Body UpdateUserRequest updateUserRequest
    );
}
