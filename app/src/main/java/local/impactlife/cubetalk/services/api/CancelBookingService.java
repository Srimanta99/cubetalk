package local.impactlife.cubetalk.services.api;

import local.impactlife.cubetalk.models.user_cancel_booking.UserCancelBookingResponse;
import local.impactlife.cubetalk.models.user_cancel_booking.UsercancelBookigRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface CancelBookingService {
    @POST("api/bookings/cancel-booking-user/")
    Call<UserCancelBookingResponse> getcancelbookService(@Header ("token") String token,@Body UsercancelBookigRequest usercancelBookigRequest);
}
