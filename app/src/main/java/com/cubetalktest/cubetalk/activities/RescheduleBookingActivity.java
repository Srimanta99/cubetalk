package com.cubetalktest.cubetalk.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.cubetalktest.cubetalk.R;
import com.cubetalktest.cubetalk.databinding.ActivityRescheduleBookingBinding;
import com.cubetalktest.cubetalk.databinding.ContentRescheduleBookingBinding;
import com.cubetalktest.cubetalk.models.User;
import com.cubetalktest.cubetalk.models.common.Booking;
import com.cubetalktest.cubetalk.models.reschedule_booking.RescheduleApiResponse;
import com.cubetalktest.cubetalk.models.reschedule_booking.RescheduleBooking;
import com.cubetalktest.cubetalk.services.api.BookingService;
import com.cubetalktest.cubetalk.services.api.ServiceBuilder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RescheduleBookingActivity extends AppCompatActivity {
    ActivityRescheduleBookingBinding mRescheduleBookingBinding;
    ContentRescheduleBookingBinding mContentRescheduleBookingBinding;
    private SharedPreferences mSharedPreferences;
    ShapeableImageView siv_expert_profile_image;
    MaterialTextView mtv_expert_name;
    MaterialTextView mtv_expert_specialities;
    MaterialTextView mtv_booking_date_and_time;
    MaterialTextView mtv_booking_slot_type_and_price;
    TextInputEditText tiet_user_name;
    TextInputEditText tiet_user_email;
    TextInputEditText tiet_usePhoneNumber;
    MaterialButton mbtn_confirm_reschedule;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRescheduleBookingBinding=ActivityRescheduleBookingBinding.inflate(LayoutInflater.from(this));
        mContentRescheduleBookingBinding=mRescheduleBookingBinding.contentCancelBook;
        setContentView(mRescheduleBookingBinding.getRoot());
        Toolbar toolbar=mRescheduleBookingBinding.toolbar;
        mSharedPreferences = getSharedPreferences(User.PREFERENCE_NAME, MODE_PRIVATE);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(getResources().getString(R.string.reschedule));
        }

        Gson gson = new Gson();
        String DataObjectAsAString = getIntent().getStringExtra("bookdata");
        Booking bookingInfo = gson.fromJson(DataObjectAsAString, Booking.class);

        siv_expert_profile_image=mContentRescheduleBookingBinding.sivExpertProfileImage;
        mtv_expert_name=mContentRescheduleBookingBinding.mtvExpertName;
        mtv_expert_specialities=mContentRescheduleBookingBinding.mtvExpertSpecialities;
        mtv_booking_date_and_time=mContentRescheduleBookingBinding.mtvBookingDateAndTime;
        mtv_booking_slot_type_and_price=mContentRescheduleBookingBinding.mtvBookingSlotTypeAndPrice;
        tiet_user_name=mContentRescheduleBookingBinding.tietUserName;
        tiet_user_email=mContentRescheduleBookingBinding.tietUserEmail;
        tiet_usePhoneNumber=mContentRescheduleBookingBinding.tietUsePhoneNumber;
        mbtn_confirm_reschedule=mContentRescheduleBookingBinding.mbtnConfirmReschedule;

        mtv_expert_name.setText(bookingInfo.getExpert().getName());
        mtv_expert_specialities.setText(bookingInfo.getTopic().getName());
        String currency=(mSharedPreferences.getInt(User.COUNTRYID, 0)==User.INDIACOUNTRYID? "INR ":"INR ");

        mtv_booking_date_and_time.setText(bookingInfo.getSlotDate() + ", " + convertSlotTime(bookingInfo.getSlotTime()) + " IST");
        mtv_booking_slot_type_and_price.setText((bookingInfo.getSlotTime()) + " Min Call ---- "+currency+bookingInfo.getAmount_paid());
        tiet_user_name.setText(mSharedPreferences.getString(User.NAME,""));
        tiet_user_email.setText(mSharedPreferences.getString(User.EMAIL,""));
        tiet_usePhoneNumber.setText(mSharedPreferences.getString(User.PHONE,""));

        Glide.with(this)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .circleCrop()
                .placeholder(R.drawable.ic_account_circle_white_128dp)
                .load(bookingInfo.getExpert().getProfileImage())
                .into(siv_expert_profile_image);

        mbtn_confirm_reschedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callAppiforReshedulebook(bookingInfo);
            }
        });


    }

    private void callAppiforReshedulebook(Booking bookingInfo) {
        RescheduleBooking rescheduleBooking = new RescheduleBooking();
        rescheduleBooking.setBookedid(bookingInfo.getBooked_id());
        rescheduleBooking.setBookinguserId(mSharedPreferences.getString(User.ID,""));
        rescheduleBooking.setBookedexpertid(bookingInfo.getExpert().getId());
        rescheduleBooking.setSlotTime(bookingInfo.getSlotTime());
        rescheduleBooking.setSlotDate(bookingInfo.getSlotDate());
        rescheduleBooking.setSlotType(bookingInfo.getSlotType());
        rescheduleBooking.setUserName(mSharedPreferences.getString(User.NAME,""));
        rescheduleBooking.setUseremail(mSharedPreferences.getString(User.EMAIL,""));
        rescheduleBooking.setUserPhone(mSharedPreferences.getString(User.PHONE,""));
        rescheduleBooking.setPaymentMode(bookingInfo.getPaymentMode());
        rescheduleBooking.setAmountPaid(bookingInfo.getAmount_paid());
        rescheduleBooking.setPaymentStatus("COMPLETED");


        BookingService bookingService = ServiceBuilder.buildService(BookingService.class);
       Call<RescheduleApiResponse> request = bookingService.reschdule(mSharedPreferences.getString(User.TOKEN,""),
               rescheduleBooking);
       request.enqueue(new Callback<RescheduleApiResponse>() {
           @Override
           public void onResponse(Call<RescheduleApiResponse> call, Response<RescheduleApiResponse> response) {
               if (response.body().ismSuccess()){
                   new MaterialAlertDialogBuilder(getApplicationContext())
                           .setTitle("Cube Talk")
                           .setMessage("Booking rescheduled successfully")
                           .setPositiveButton("Ok", (dialog, which) -> {
                               //startpayment();
                               dialog.dismiss();
                           })
                           .show();
                   //Toast.makeText(getApplicationContext(),response.body().getMessge().toString(),Toast.LENGTH_LONG).show();
                   Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
                   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                   startActivity(intent);
                   finish();
               }
           }

           @Override
           public void onFailure(Call<RescheduleApiResponse> call, Throwable t) {

           }
       });

    }

    String convertSlotTime(String slotTime){
        SimpleDateFormat sdf = new SimpleDateFormat("H.mm", Locale.ENGLISH);
        Date date;
        try {
            date = sdf.parse(slotTime);
            return new SimpleDateFormat("h:mm a", Locale.ENGLISH).format(date);
        } catch (ParseException e) {
            Log.e("TAG", "convertSlotTime: e.getMessage(): ");
        }
        return "";
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
