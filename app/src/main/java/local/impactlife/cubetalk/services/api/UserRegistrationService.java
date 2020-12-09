package local.impactlife.cubetalk.services.api;

import local.impactlife.cubetalk.models.register_user.UserRegistrationRequest;
import local.impactlife.cubetalk.models.register_user.UserRegistrationResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserRegistrationService {
    @POST("api/users/user_signup")
    Call<UserRegistrationResponse> register(@Body UserRegistrationRequest userRegistrationRequest);
}
