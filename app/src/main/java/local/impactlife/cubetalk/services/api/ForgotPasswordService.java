package local.impactlife.cubetalk.services.api;

import local.impactlife.cubetalk.models.forgot_password.ForgotPasswordRequest;
import local.impactlife.cubetalk.models.forgot_password.ForgotPasswordResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ForgotPasswordService {
    @POST("api/users/forgot_pass")
    Call<ForgotPasswordResponse> sendForgotPasswordRequest(@Header ("token") String token,@Body ForgotPasswordRequest forgotPasswordRequest);
}
