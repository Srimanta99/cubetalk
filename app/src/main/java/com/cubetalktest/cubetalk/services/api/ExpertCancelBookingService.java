package com.cubetalktest.cubetalk.services.api;

import com.cubetalktest.cubetalk.models.expert_cancel_booking.ExpertCancelBookingResponse;
import com.cubetalktest.cubetalk.models.expert_cancel_booking.ExpertcancelBookigRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
;

public interface ExpertCancelBookingService {
    @POST("api/bookings/cancel-booking-expert/")
    Call<ExpertCancelBookingResponse> getcancelbookService(@Header("token") String token,
                                                           @Body ExpertcancelBookigRequest expertcancelBookigRequest);
}
