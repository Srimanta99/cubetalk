package com.cubetalktest.cubetalk.activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;
import com.mesibo.api.Mesibo;

import com.mesibo.calls.api.MesiboCall;
import com.mesibo.messaging.MesiboUI;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.cubetalktest.cubetalk.adapters.recycler.ExpertBookingsAdapter;
import com.cubetalktest.cubetalk.databinding.ActivityExpertBookingManagementBinding;
import com.cubetalktest.cubetalk.databinding.ContentExpertBookingManagementBinding;
import com.cubetalktest.cubetalk.models.User;
import com.cubetalktest.cubetalk.models.common.Booking;
import com.cubetalktest.cubetalk.models.get_expert_bookings.ExpertBookingRequest;
import com.cubetalktest.cubetalk.models.get_user_past_bookings.PastBookingResponse;
import com.cubetalktest.cubetalk.services.api.BookingService;
import com.cubetalktest.cubetalk.services.api.ServiceBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExpertBookingManagementActivity extends AppCompatActivity {

    private static final String TAG = ExpertBookingManagementActivity.class.getSimpleName();

    private ActivityExpertBookingManagementBinding mActivityBinding;
    private String mUserId;
    private ContentExpertBookingManagementBinding mContentBinding;
    private List<Booking> mBookings;
    private ExpertBookingsAdapter mExpertBookingsAdapter;
    private RecyclerView mBookingsRecycler;
    private Spinner mBookingStatusSpinner;
    private Spinner mBookingDatesSpinner;
    private TextView mSelectdateforBook;
    Button mbtn_bookslot_search;
    int selectposition=-1;
    public int status;
    String selecteddate="";
    public String Searchpurpose="";
    private SharedPreferences mSharedPreferences;
    MaterialTextView mTvexpertnobooking;
    MaterialTextView mTvbookingdetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_expert_booking_management);

        mActivityBinding = ActivityExpertBookingManagementBinding.inflate(LayoutInflater.from(this));
        setContentView(mActivityBinding.getRoot());
        mSharedPreferences = getSharedPreferences(User.PREFERENCE_NAME, Context.MODE_PRIVATE);
        Toolbar toolbar = mActivityBinding.toolbar;
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Booking Management");
        }

        SharedPreferences mSharedPreferences = getSharedPreferences(User.PREFERENCE_NAME, MODE_PRIVATE);
        mUserId = mSharedPreferences.getString(User.ID, "");
        mContentBinding = mActivityBinding.contentExpertBookingManagement;
      //  mBookingStatusSpinner = mContentBinding.spBookingStatus;
        mSelectdateforBook=mContentBinding.tvSelectdateForExpert;
        mbtn_bookslot_search=mContentBinding.btnBookslotSearch;
        mTvexpertnobooking=mContentBinding.mTvexpertnobooking;
        mTvbookingdetails=mContentBinding.mTvbookingdetails;
       // mBookingDatesSpinner = mContentBinding.spBookingDates;

        mBookings = new ArrayList<>();

        mExpertBookingsAdapter = new ExpertBookingsAdapter(mBookings,this);

        mBookingsRecycler = mContentBinding.rvBookings;

        mBookingsRecycler.setAdapter(mExpertBookingsAdapter);

      /*  mBookingStatusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectposition=position-1;
                Searchpurpose=mBookingStatusSpinner.getSelectedItem().toString();
               *//* if (position>0) {
                    Log.d(TAG, "onItemSelected: position: " + position);
                    fetchBookings(position);
                }*//*
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d(TAG, "onNothingSelected: ");
            }
        });


        mBookingStatusSpinner.setSelection(0);*/
        mSelectdateforBook.setOnClickListener( view -> {
         opendatepicker();
        });

        mbtn_bookslot_search.setOnClickListener( view -> {
           // if (selectposition>=0) {
                if (!selecteddate.equals("")) {
                    fetchBookings(2);
                } else
                    Toast.makeText(this,"Select Date",Toast.LENGTH_LONG).show();
           // }else
                 //   Toast.makeText(this,"Select Booking Status",Toast.LENGTH_LONG).show();
        });

        //get booking on current date
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        selecteddate = df.format(c.getTime());
        mSelectdateforBook.setText(selecteddate);
       // mTvbookingdetails.setText("Bookings details for "+selecteddate+" are listed for review and action. Change to any other date from selector to view corresponding bookings.");
        fetchBookings(2);

    }

    private void opendatepicker() {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        mSelectdateforBook.setText(year + "-" + String.format("%02d",monthOfYear+1) + "-" + String.format("%02d", dayOfMonth));
                        selecteddate=mSelectdateforBook.getText().toString();
                      //  mTvbookingdetails.setText("Bookings details for "+selecteddate+" are listed for review and action. Change to any other date from selector to view corresponding bookings.");

                    }
                }, mYear, mMonth, mDay);

       // c.add(Calendar.MONTH,-2);
        c.set(mYear, mMonth-2, mDay);
        datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
        c.set(mYear, mMonth, mDay+10);
        datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
        datePickerDialog.show();
    }

    public void fetchBookings(int Selected_status) {
        SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd");
        Date newDate = null;
        try {
            newDate = spf.parse(selecteddate);
            spf = new SimpleDateFormat("dd/MM/yyyy");
            String newDateString = spf.format(newDate);
            mTvbookingdetails.setText("Bookings details for "+newDateString+" are listed for review and action. Change to any other date from selector to view corresponding bookings.");

            //  System.out.println(newDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        status=Selected_status;
        ExpertBookingRequest expertBookingRequest = new ExpertBookingRequest();
        expertBookingRequest.setExpertId(mUserId);
        expertBookingRequest.setSlotDate(selecteddate);
        expertBookingRequest.setStatus("");

        BookingService bookingService = ServiceBuilder.buildService(BookingService.class);
        Call<PastBookingResponse> request = bookingService.getExpertBookings(mSharedPreferences.getString(User.TOKEN, ""),expertBookingRequest);

        request.enqueue(new Callback<PastBookingResponse>() {
            @Override
            public void onResponse(@NotNull Call<PastBookingResponse> call, @NotNull Response<PastBookingResponse> response) {
                Log.d(TAG, "onResponse: "+response.body().toString());
                if (response.isSuccessful() && response.body() != null) {
                    PastBookingResponse pastBookingResponse = response.body();
                    if (pastBookingResponse.getPastBookings().size() == 0) {
                        Toast.makeText(ExpertBookingManagementActivity.this, "No bookings found", Toast.LENGTH_SHORT).show();
                        mTvexpertnobooking.setVisibility(View.VISIBLE);
                         mBookings.clear();
                         mExpertBookingsAdapter.notifyDataSetChanged();


                    } else {
                        mBookings.clear();
                        mTvexpertnobooking.setVisibility(View.GONE);
                        mBookings.addAll(pastBookingResponse.getPastBookings());
                        mExpertBookingsAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<PastBookingResponse> call, @NotNull Throwable throwable) {
                Log.d(TAG, "onFailure: throwable.getMessage(): " + throwable.getMessage());
            }
        });
    }

    public void callChatApi(Booking pastBooking){
        Mesibo api = Mesibo.getInstance();
        api.init(this);
        Mesibo.addListener(this);
        Mesibo.setSecureConnection(true);
        Mesibo.setAccessToken(mSharedPreferences.getString(com.cubetalktest.cubetalk.models.User.MESIBO_TOKEN,""));
        //Mesibo.setAccessToken("2e7aeb174f174bfe04344323f31ecb18707a1132f281bfebb15436d");
        // Mesibo.setAccessToken("a16c6806b24340f1c4afdab2f66a2b96ea38e2201fd2e99154134");
        Mesibo.setDatabase("mydb", 0);
        Mesibo.start();

        // Mesibo.start();
        // set path for storing DB and messaging files
        //Mesibo.setPath(Environment.getExternalStorageDirectory().getAbsolutePath());
        // add listener
        //  Mesibo.addListener(this);
        Mesibo.UserProfile userProfile = new Mesibo.UserProfile();
      //  userProfile.name = "u4";
       // userProfile.address = "u4";
         userProfile.address = pastBooking.getUser().getId();
        userProfile.name = pastBooking.getUser().getName();
        Mesibo.setUserProfile(userProfile, false);
        MesiboCall.getInstance().init(this);

        // MesiboCall.getInstance().setListener(this);
        // Read receipts are enabled only when App is set to be in foreground
      //  Mesibo.setAppInForeground(this, 0, true);
              /*  mReadSession = new Mesibo.ReadDbSession(userProfile.address,this);
                mReadSession.enableReadReceipt(true);
                mReadSession.read(100);*/
        MesiboUI.launchMessageView(this, userProfile.address, 0);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }



}