package com.cubetalktest.cubetalk.services.api;

import com.cubetalktest.cubetalk.models.expert_confirm_booking.ExpertConfirmBookingResponse;
import com.cubetalktest.cubetalk.models.expert_confirm_booking.ExpertConrirmBookigRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ConfirmBookingService {
    @POST("api/bookings/booking-confirmation/")
    Call<ExpertConfirmBookingResponse> getConfirmbookService(@Header("token") String token,
                                                             @Body ExpertConrirmBookigRequest expertConrirmBookigRequest);
}
