package local.impactlife.cubetalk.services.api;

import local.impactlife.cubetalk.models.creditshell.CreditShellResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface CreditShellService {
    @GET("api/bookings/user-credit-shell/")
    Call<CreditShellResponse> getcreditShell(@Header ("token") String token, @Query("user_id") String  userid);
}
