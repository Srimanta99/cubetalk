package local.impactlife.cubetalk.services.api;

import local.impactlife.cubetalk.models.creditshell.CreditShellResponse;
import local.impactlife.cubetalk.models.dashboarddetails.DashBoardDetails;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ExpertDashBoardService {
    @GET("api/bookings/expert-dashboard-statistics/?")
    Call<DashBoardDetails> getdashboarddetails(@Header("token") String token, @Query("user_id") String userid);
}
