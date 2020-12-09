package local.impactlife.cubetalk.services.api;

import local.impactlife.cubetalk.models.login_user.LoginRequest;
import local.impactlife.cubetalk.models.login_user.LoginResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginService {
    @POST("api/users/user_login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);
}
