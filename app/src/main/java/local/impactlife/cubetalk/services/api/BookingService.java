package local.impactlife.cubetalk.services.api;

import local.impactlife.cubetalk.models.get_booking_time_slots.TimeSlotsResponse;
import local.impactlife.cubetalk.models.get_expert_bookings.ExpertBookingRequest;
import local.impactlife.cubetalk.models.get_future_bookings.FutureBookingRequest;
import local.impactlife.cubetalk.models.get_future_bookings.FutureBookingResponse;
import local.impactlife.cubetalk.models.get_user_past_bookings.PastBookingRequest;
import local.impactlife.cubetalk.models.get_user_past_bookings.PastBookingResponse;
import local.impactlife.cubetalk.models.post_booking_slot.BookSlotRequest;
import local.impactlife.cubetalk.models.post_booking_slot.BookSlotResponse;
import local.impactlife.cubetalk.models.reschedule_booking.RescheduleApiResponse;
import local.impactlife.cubetalk.models.reschedule_booking.RescheduleBooking;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BookingService {

    @GET("/api/bookings/get-slots/?expert_service_start_date=2020-06-04")
    Call<TimeSlotsResponse> getBookingTimeSlots(
            @Header("token") String token,
            @Query("user_id") String expertId,
            @Query("slot_type") String slotType,
            @Query("slot_date") String slotDate);

    @POST("/api/bookings/book-slot/")
    Call<BookSlotResponse> postBookingDetails(@Header("token") String token,@Body BookSlotRequest bookSlotRequest);

    @POST("/api/bookings/future-bookings/")
    Call<FutureBookingResponse> getFutureBookings(@Body FutureBookingRequest futureBookingRequest);

    @POST("/api/bookings/user-future-bookings/")
    Call<FutureBookingResponse> getUserFutureBookings(@Header("token") String token,@Body FutureBookingRequest futureBookingRequest);

    @POST("/api/bookings/user-past-bookings/")
    Call<PastBookingResponse> getUserPastBookings(@Header("token") String token,@Body PastBookingRequest pastBookingRequest);

    @POST("/api/bookings/expert-bookings/")
    Call<PastBookingResponse> getExpertBookings(@Header("token") String token,@Body ExpertBookingRequest expertBookingRequest);

    @GET("/api/bookings/get-slots/?")
    Call<TimeSlotsResponse> getBookingTimeSlotsReshedule(
            @Header("token") String token,
            @Query("expert_service_start_date") String expert_service_start_date,
            @Query("user_id") String expertId,
            @Query("slot_type") String slotType,
            @Query("slot_date") String slotDate);

    @POST("/api/bookings/reschedule-booking/")
    Call<RescheduleApiResponse> reschdule(@Header("token") String token,@Body RescheduleBooking rescheduleBooking);


}
