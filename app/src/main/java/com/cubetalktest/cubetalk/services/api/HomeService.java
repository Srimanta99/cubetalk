package com.cubetalktest.cubetalk.services.api;

import com.cubetalktest.cubetalk.models.PromotionBannerResponse;
import com.cubetalktest.cubetalk.models.SpecialityResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface HomeService {

    @GET("api/banners/get-all-home-banners")
    Call<PromotionBannerResponse> getPromotionBanner(@Header("token") String token);

    @GET("api/categories/get-all-specialities")
    Call<SpecialityResponse> getAllSpecialities(@Header("token") String token);
}
