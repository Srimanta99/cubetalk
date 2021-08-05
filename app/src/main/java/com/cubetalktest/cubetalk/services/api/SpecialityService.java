package com.cubetalktest.cubetalk.services.api;

import com.cubetalktest.cubetalk.models.SpecialityArticleResponse;
import com.cubetalktest.cubetalk.models.SpecialityTopicResponse;
import com.cubetalktest.cubetalk.models.SpecialityVideoResponse;
import com.cubetalktest.cubetalk.models.spacility.SpecialityImageReaponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SpecialityService {

    @GET("api/categories/get-all-topics")
    Call<SpecialityTopicResponse> getTopics(@Header ("token") String token,@Query("parent_id") String path);

    @GET("api/banners/documents/ARTICLE/{category_id}")
    Call<SpecialityArticleResponse> getArticles(@Header ("token") String token,@Path("category_id") String categoryId);

    @GET("api/banners/documents/VIDEO/{category_id}")
    Call<SpecialityVideoResponse> getVideos(@Header ("token") String token,@Path("category_id") String category_id);

    @GET("api/banners/get-banner/CATEGORY/{spacility_id}")
    Call<SpecialityImageReaponse> getSpacilityDetails(@Header ("token") String token, @Path("spacility_id") String categoryId);


}
