package local.impactlife.cubetalk.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.gridlayout.widget.GridLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import local.impactlife.cubetalk.RadioGridGroup;
import local.impactlife.cubetalk.databinding.ActivityRescheduleSlotSelectionBinding;
import local.impactlife.cubetalk.databinding.ActivitySlotSelectionBinding;
import local.impactlife.cubetalk.databinding.ContentRescheduleSlotSelectionBinding;
import local.impactlife.cubetalk.databinding.ContentSlotSelectionBinding;
import local.impactlife.cubetalk.databinding.ItemTimeSlotBinding;
import local.impactlife.cubetalk.models.User;
import local.impactlife.cubetalk.models.common.Booking;
import local.impactlife.cubetalk.models.get_booking_time_slots.TimeSlotsResponse;
import local.impactlife.cubetalk.models.post_booking_slot.BookSlotRequest;
import local.impactlife.cubetalk.services.api.BookingService;
import local.impactlife.cubetalk.services.api.ServiceBuilder;
import local.impactlife.cubetalk.utils.Utils;
import local.impactlife.cubetalk.viewmodels.SlotSelectionViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RescheduleSlotSelcetionActivity extends AppCompatActivity {
    private static final String TAG = SlotSelectionActivity.class.getSimpleName();

    ActivityRescheduleSlotSelectionBinding activityRescheduleSlotSelectionBinding;
    ContentRescheduleSlotSelectionBinding contentRescheduleSlotSelectionBinding;
    private TabLayout mTimeSlotsTabLayout;
    private SlotSelectionViewModel mViewModel;
    private MaterialButton mContinueButton;
    private List<TimeSlotsResponse.AvailableDate> mAvailableDates;
    private RadioGridGroup mTimeSlotsRadioGridGroup;
    private SwipeRefreshLayout mTimeSlotsSwipeRefresh;
    private BookSlotRequest mBookSlotRequest;
    Booking bookingInfo=new Booking();
    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityRescheduleSlotSelectionBinding= ActivityRescheduleSlotSelectionBinding.inflate(LayoutInflater.from(this));
        setContentView(activityRescheduleSlotSelectionBinding.getRoot());
        Gson gson = new Gson();
        String DataObjectAsAString = getIntent().getStringExtra("bookdata");
         bookingInfo = gson.fromJson(DataObjectAsAString, Booking.class);
        mSharedPreferences = getSharedPreferences(User.PREFERENCE_NAME, Context.MODE_PRIVATE);
        contentRescheduleSlotSelectionBinding=   activityRescheduleSlotSelectionBinding.contentRescheduleSlotSelection;
        mAvailableDates = new ArrayList<>();

        mTimeSlotsTabLayout = contentRescheduleSlotSelectionBinding.tlTimeSlots;
        mTimeSlotsSwipeRefresh = contentRescheduleSlotSelectionBinding.srlTimeSlots;
        mTimeSlotsRadioGridGroup = contentRescheduleSlotSelectionBinding.rggTimeSlots;
        mContinueButton = contentRescheduleSlotSelectionBinding.mbtnContinue;

        Toolbar toolbar = activityRescheduleSlotSelectionBinding.toolbar;
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mTimeSlotsTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d(TAG, "onTabSelected: tab.getText(): " + tab.getText());
                bookingInfo.setSlotDate(String.valueOf(tab.getTag()));
                mTimeSlotsRadioGridGroup.removeAllViews();
                enableContinueButton(false);
                mTimeSlotsSwipeRefresh.setRefreshing(true);
                fetchTimeSlots(tab.getTag().toString());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mTimeSlotsSwipeRefresh.setOnRefreshListener(() -> mTimeSlotsSwipeRefresh.setRefreshing(false));

        mTimeSlotsRadioGridGroup.setOnCheckedChangeListener((group, checkedId) -> {
            MaterialRadioButton radioButton = group.findViewById(checkedId);
            bookingInfo.setSlotTime(String.valueOf(radioButton.getTag()));
            enableContinueButton(true);
        });

        mContinueButton.setOnClickListener(v -> {
          //  Gson gson1 = new Gson();
            String bookingInfodata = gson.toJson(bookingInfo);
            Intent intentReshdule= new Intent(this, RescheduleBookingActivity.class);
            intentReshdule.putExtra("bookdata", bookingInfodata);
            startActivity(intentReshdule);

            //Intent intent = new Intent(this, RescheduleBookingActivity.class);
           // intent.putExtra(BookSlotRequest.TAG, mBookSlotRequest);
           // startActivity(intent);
        });
        fetchTimeSlots(Utils.convertSlotDate(Utils.convertSlotDate(bookingInfo.getExpert().getExpert_service_start_date())));

    }
    private void enableContinueButton(boolean isEnabled) {
        mContinueButton.setEnabled(isEnabled);
    }
    private void fetchTimeSlots(String date) {

        Date todayDate = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String todayString = formatter.format(todayDate);
        BookingService bookingService = ServiceBuilder.buildService(BookingService.class);
        Call<TimeSlotsResponse> createRequest = bookingService.getBookingTimeSlotsReshedule(
                mSharedPreferences.getString(User.TOKEN, ""),
                "2020-06-04",
                bookingInfo.getExpert().getId(),
                bookingInfo.getSlotType(),
                todayString
        );

        createRequest.enqueue(new Callback<TimeSlotsResponse>() {
            @Override
            public void onResponse(@NotNull Call<TimeSlotsResponse> call, @NotNull Response<TimeSlotsResponse> response) {
                Log.d(TAG, "onResponse: response: " + response.toString());
                if (response.isSuccessful()) {
                    TimeSlotsResponse timeSlotsResponse = response.body();
                    TimeSlotsResponse.Data data = timeSlotsResponse.getData();
                    if (mTimeSlotsTabLayout.getTabCount() == 0) {
                        mAvailableDates.addAll(data.getAvailableDate());
                        for (TimeSlotsResponse.AvailableDate availableDate : mAvailableDates) {
                            TabLayout.Tab tab = mTimeSlotsTabLayout.newTab();
                            tab.setText(availableDate.getDate());
                            tab.setTag(availableDate.getDate());
                            mTimeSlotsTabLayout.addTab(tab);
                        }
                    } else {
                        TimeSlotsResponse.SlotWeekdays slotWeekdays = data.getSlotWeekdays();
                        TimeSlotsResponse.SlotSaturday slotSaturday = data.getSlotSaturday();
                        TimeSlotsResponse.SlotSunday slotSunday = data.getSlotSunday();

                        if (slotWeekdays.getFirstPhase() != null) {
                            addTimeSlotsToRadioGridGroup(slotWeekdays.getFirstPhase());
                            if (slotWeekdays.getSecondPhase() != null) {
                                addTimeSlotsToRadioGridGroup(slotWeekdays.getSecondPhase());
                            }
                        } else if (slotSaturday.getFirstPhase() != null) {
                            addTimeSlotsToRadioGridGroup(slotSaturday.getFirstPhase());
                            if (slotSaturday.getSecondPhase() != null) {
                                addTimeSlotsToRadioGridGroup(slotSaturday.getSecondPhase());
                            }
                        } else if (slotSunday.getFirstPhase() != null) {
                            addTimeSlotsToRadioGridGroup(slotSunday.getFirstPhase());
                            if (slotSunday.getSecondPhase() != null) {
                                addTimeSlotsToRadioGridGroup(slotSunday.getSecondPhase());
                            }
                        }
                        mTimeSlotsSwipeRefresh.setRefreshing(false);
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<TimeSlotsResponse> call, @NotNull Throwable throwable) {
                Log.d(TAG, "onFailure: throwable.getMessage(): " + throwable.getMessage());
            }
        });
    }

    private void addTimeSlotsToRadioGridGroup(List<String> phase) {
        for (String timeSlot : phase) {

            SimpleDateFormat sdf = new SimpleDateFormat("H.mm", Locale.ENGLISH);
            try {
                Date date = sdf.parse(timeSlot);
                String timeSlotString = new SimpleDateFormat("h:mm\na", Locale.ENGLISH).format(date);

                MaterialRadioButton materialRadioButton = ItemTimeSlotBinding.inflate(LayoutInflater.from(RescheduleSlotSelcetionActivity.this)).getRoot();

                materialRadioButton.setText(timeSlotString);
                materialRadioButton.setTag(timeSlot);

                GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(
                        GridLayout.spec(GridLayout.UNDEFINED, 0f),
                        GridLayout.spec(GridLayout.UNDEFINED, 1f)
                );

                layoutParams.width = 0;
                materialRadioButton.setLayoutParams(layoutParams);
                mTimeSlotsRadioGridGroup.addView(materialRadioButton);
            } catch (ParseException e) {
                Log.d(TAG, "onResponse: e.getMessage(): " + e.getMessage());
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
