package local.impactlife.cubetalk.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import local.impactlife.cubetalk.adapters.recycler.PastBookingsAdapter;
import local.impactlife.cubetalk.databinding.ActivityUserPastBookingsBinding;
import local.impactlife.cubetalk.databinding.ContentUserPastBookingsBinding;
import local.impactlife.cubetalk.models.User;
import local.impactlife.cubetalk.models.common.Booking;
import local.impactlife.cubetalk.models.get_user_past_bookings.PastBookingRequest;
import local.impactlife.cubetalk.models.get_user_past_bookings.PastBookingResponse;
import local.impactlife.cubetalk.services.api.BookingService;
import local.impactlife.cubetalk.services.api.ServiceBuilder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserPastBookingsActivity extends AppCompatActivity {

    private static final String TAG = UserPastBookingsActivity.class.getSimpleName();

    private ActivityUserPastBookingsBinding mActivityBinding;
    private String mUserId;
    private ContentUserPastBookingsBinding mContentBinding;
    private RecyclerView mPastBookingsRecycler;
    private List<Booking> mPastBookings;
    private PastBookingsAdapter mPastBookingsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_user_past_bookings);

        mActivityBinding = ActivityUserPastBookingsBinding.inflate(LayoutInflater.from(this));
        setContentView(mActivityBinding.getRoot());

        Toolbar toolbar = mActivityBinding.toolbar;
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Past Bookings");
        }

        SharedPreferences mSharedPreferences = getSharedPreferences(User.PREFERENCE_NAME, MODE_PRIVATE);
        mUserId = mSharedPreferences.getString(User.ID, "");

        mContentBinding = mActivityBinding.contentUserPastBookings;

        mPastBookings = new ArrayList<>();

        mPastBookingsAdapter = new PastBookingsAdapter(mPastBookings);

        mPastBookingsRecycler = mContentBinding.rvPastBookings;

        mPastBookingsRecycler.setAdapter(mPastBookingsAdapter);

        PastBookingRequest pastBookingRequest = new PastBookingRequest();
        pastBookingRequest.setUserId(mUserId);

        BookingService bookingService = ServiceBuilder.buildService(BookingService.class);
        Call<PastBookingResponse> request = bookingService.getUserPastBookings(mSharedPreferences.getString(User.TOKEN, ""),pastBookingRequest);

        request.enqueue(new Callback<PastBookingResponse>() {
            @Override
            public void onResponse(@NotNull Call<PastBookingResponse> call, @NotNull Response<PastBookingResponse> response) {
                Log.d(TAG, "onResponse: ");

                if (response.isSuccessful() && response.body() != null) {
                    PastBookingResponse pastBookingResponse = response.body();
                    if (pastBookingResponse.getPastBookings().size() == 0) {
                        Toast.makeText(UserPastBookingsActivity.this, "No bookings found", Toast.LENGTH_SHORT).show();
                    } else {
                        mPastBookings.clear();
                        mPastBookings.addAll(pastBookingResponse.getPastBookings());
                        mPastBookingsAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<PastBookingResponse> call, @NotNull Throwable throwable) {
                Log.d(TAG, "onFailure: throwable.getMessage(): " + throwable.getMessage());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

}