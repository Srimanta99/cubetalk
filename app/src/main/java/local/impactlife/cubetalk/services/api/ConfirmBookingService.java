package local.impactlife.cubetalk.services.api;

import local.impactlife.cubetalk.models.expert_confirm_booking.ExpertConfirmBookingResponse;
import local.impactlife.cubetalk.models.expert_confirm_booking.ExpertConrirmBookigRequest;
import local.impactlife.cubetalk.models.user_cancel_booking.UserCancelBookingResponse;
import local.impactlife.cubetalk.models.user_cancel_booking.UsercancelBookigRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ConfirmBookingService {
    @POST("api/bookings/booking-confirmation/")
    Call<ExpertConfirmBookingResponse> getConfirmbookService(@Header("token") String token,
                                                             @Body ExpertConrirmBookigRequest expertConrirmBookigRequest);
}
