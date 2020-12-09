package local.impactlife.cubetalk.services.api;

import local.impactlife.cubetalk.models.get_expert_sign_up_condition.ExpertSignUpConditionResponse;
import local.impactlife.cubetalk.models.login_user.LoginRequest;
import local.impactlife.cubetalk.models.login_user.LoginResponse;
import local.impactlife.cubetalk.models.register_user.UserRegistrationRequest;
import local.impactlife.cubetalk.models.register_user.UserRegistrationResponse;
import local.impactlife.cubetalk.models.get_experts_by_topics.ExpertsByTopicResponse;
import local.impactlife.cubetalk.models.user_privacy_policy_and_terms.UserPrivacyPolicyAndTermsResponse;
import local.impactlife.cubetalk.models.user_profile_image.UserImageUploadResponseBody;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface UserService {
    @POST("api/users/user_signup")
    Call<UserRegistrationResponse> register(@Body UserRegistrationRequest userRegistrationRequest);

    @POST("api/users/user_login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @Multipart
    @POST("api/users/user-profile-image/{user_id}")
    Call<UserImageUploadResponseBody> uploadUserImage(
            @Header("token") String token,
            @Path("user_id") String id,
            @Part MultipartBody.Part image
    );

    @GET("api/users/expert-list-by-topic/{topic_id}")
    Call<ExpertsByTopicResponse> getExpertsByTopics(@Header("token") String token,@Path("topic_id") String id);

    @GET("api/cms/{privacy_policy_id}")
    Call<UserPrivacyPolicyAndTermsResponse> getUserPrivacyPolicy(
            @Header("token") String token,@Path("privacy_policy_id") String id);

    @GET("api/cms/{terms_id}")
    Call<UserPrivacyPolicyAndTermsResponse> getUserTerms(@Path("terms_id") String id);

    @GET("api/users/expert-signup-condition")
    Call<ExpertSignUpConditionResponse> getExpertSignUpCondition(@Header("token") String token);

}
