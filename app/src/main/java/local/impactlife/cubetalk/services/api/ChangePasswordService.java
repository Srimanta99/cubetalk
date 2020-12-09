package local.impactlife.cubetalk.services.api;

import local.impactlife.cubetalk.models.change_password.ChangePasswordResponse;
import local.impactlife.cubetalk.models.change_password.ChangePasswordRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ChangePasswordService {

    @POST("api/users/changepassword/{user_id}")
    Call<ChangePasswordResponse> changePasswordRequest(
            @Header("token") String token,
            @Path("user_id") String userId,
            @Body ChangePasswordRequest changePasswordRequest
    );
}
