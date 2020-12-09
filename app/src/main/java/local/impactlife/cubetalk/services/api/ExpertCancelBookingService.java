package local.impactlife.cubetalk.services.api;

import local.impactlife.cubetalk.models.expert_cancel_booking.ExpertCancelBookingResponse;
import local.impactlife.cubetalk.models.expert_cancel_booking.ExpertcancelBookigRequest;
import local.impactlife.cubetalk.models.user_cancel_booking.UserCancelBookingResponse;
import local.impactlife.cubetalk.models.user_cancel_booking.UsercancelBookigRequest;
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
