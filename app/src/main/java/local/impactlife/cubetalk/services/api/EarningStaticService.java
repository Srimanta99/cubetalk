package local.impactlife.cubetalk.services.api;

import local.impactlife.cubetalk.models.creditshell.CreditShellResponse;
import local.impactlife.cubetalk.models.earning_statics.EarningStaticesResponse;
import local.impactlife.cubetalk.models.earning_statics.GetMonthResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface EarningStaticService {
    @GET("api/bookings/expert-booking-earning-statistics/?")
    Call<EarningStaticesResponse> getearningStatics(@Header("token") String token, @Query("user_id") String userid, @Query("statistics_date") String statisticsdate);

    @GET("api/bookings/expert-booking-earning-statistics/?")
    Call<ResponseBody> getearningStaticsresp(@Header("token") String token, @Query("user_id") String userid, @Query("statistics_date") String statisticsdate);

    @GET("api/users/get-earning-months")
    Call<GetMonthResponse> getearningmonth(@Header("token") String token);

    @GET("api/booking/expert-booking-earning-statistics-pdf-last-fy/?")
    Call<ResponseBody> getExpertStaticPdf(@Header("token") String token, @Query("user_id ") String expert_id);

    @GET("api/booking/expert-booking-earning-statistics-pdf-ytd/?")
    Call<ResponseBody> getExpertStaticYtdPdf(@Header("token") String token, @Query("user_id ") String expert_id);

}
