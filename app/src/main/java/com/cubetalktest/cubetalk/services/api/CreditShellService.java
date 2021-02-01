package com.cubetalktest.cubetalk.services.api;

import com.cubetalktest.cubetalk.models.creditshell.CreditShellResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface CreditShellService {
    @GET("api/bookings/user-credit-shell/")
    Call<CreditShellResponse> getcreditShell(@Header ("token") String token, @Query("user_id") String  userid);
}
