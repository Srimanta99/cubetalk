package com.cubetalktest.cubetalk.services.api;

import com.cubetalktest.cubetalk.models.expert_registration.ExpertRegistrationRequest;
import com.cubetalktest.cubetalk.models.expert_registration.ExpertRegistrationResponse;
import com.cubetalktest.cubetalk.models.expert_registration.SpecialityResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ExpertRegistrationService {

    @GET("/api/categories/topics-by-speciality")
    Call<SpecialityResponse> getSpecialitiesAndTopics(@Header("token") String token);

    @POST("api/users/expert_signup/{user_id}")
    Call<ExpertRegistrationResponse> submitExpertRegistrationRequest(
            @Header("token") String token,
            @Path("user_id") String id,
            @Body ExpertRegistrationRequest expertRegistrationRequest
    );
}
