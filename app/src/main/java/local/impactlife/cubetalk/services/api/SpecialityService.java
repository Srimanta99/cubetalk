package local.impactlife.cubetalk.services.api;

import local.impactlife.cubetalk.models.SpecialityArticleResponse;
import local.impactlife.cubetalk.models.SpecialityTopicResponse;
import local.impactlife.cubetalk.models.SpecialityVideoResponse;
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

}
