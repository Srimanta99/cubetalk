package local.impactlife.cubetalk.activities;

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
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import local.impactlife.cubetalk.R;
import local.impactlife.cubetalk.databinding.ActivityCancelBookingBinding;
import local.impactlife.cubetalk.databinding.ContentCencelBookingBinding;
import local.impactlife.cubetalk.models.User;
import local.impactlife.cubetalk.models.common.Booking;
import local.impactlife.cubetalk.models.user_cancel_booking.UserCancelBookingResponse;
import local.impactlife.cubetalk.models.user_cancel_booking.UsercancelBookigRequest;
import local.impactlife.cubetalk.services.api.CancelBookingService;
import local.impactlife.cubetalk.services.api.ServiceBuilder;
import local.impactlife.cubetalk.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class  CancelBookingActivity extends AppCompatActivity {
    private static final String TAG = "CancelBooking";
    private ActivityCancelBookingBinding mCancelbookingbinding;
    private ContentCencelBookingBinding mContentCancelbookbinding;
     MaterialTextView mtv_expert_specialities;
     MaterialTextView mtv_cbooking_date_and_time;
     MaterialTextView mtv_cbooking_slot_type_and_price;
     MaterialTextView mtv_expert_name;
     ShapeableImageView msiv_expert_profile_image;
     MaterialButton mbtn_confirm_cancel;
    private SharedPreferences mSharedPreferences;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCancelbookingbinding=ActivityCancelBookingBinding.inflate(LayoutInflater.from(this));
        setContentView(mCancelbookingbinding.getRoot());
        mContentCancelbookbinding=mCancelbookingbinding.contentCancelBook;
        Toolbar toolbar=mCancelbookingbinding.toolbar;
        mSharedPreferences = getSharedPreferences(User.PREFERENCE_NAME, MODE_PRIVATE);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(getResources().getString(R.string.cancelbookingSummery));
        }
        mtv_expert_specialities=mContentCancelbookbinding.mtvExpertSpecialities;
        mtv_expert_name=mContentCancelbookbinding.mtvExpertName;
        mtv_cbooking_date_and_time=mContentCancelbookbinding.mtvCbookingDateAndTime;
        mtv_cbooking_slot_type_and_price=mContentCancelbookbinding.mtvCbookingSlotTypeAndPrice;
        msiv_expert_profile_image=mContentCancelbookbinding.sivExpertProfileImage;
        mbtn_confirm_cancel=mContentCancelbookbinding.mbtnConfirmCancel;
        Gson gson = new Gson();
        String DataObjectAsAString = getIntent().getStringExtra("bookdata");
        Booking bookingInfo = gson.fromJson(DataObjectAsAString, Booking.class);
        mtv_expert_name.setText(bookingInfo.getExpert().getName());
        mtv_expert_specialities.setText(bookingInfo.getTopic().getName());
        mtv_cbooking_date_and_time.setText(Utils.convertSlotDate(bookingInfo.getSlotDate()) + ", " + convertSlotTime(bookingInfo.getSlotTime()) + " IST");
        mtv_cbooking_slot_type_and_price.setText((bookingInfo.getSlotTime()) + " Min Call ---- INR "+bookingInfo.getAmount_paid());

        Glide.with(this)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .circleCrop()
                .placeholder(R.drawable.ic_account_circle_white_128dp)
                .load(bookingInfo.getExpert().getProfileImage())
                .into(msiv_expert_profile_image);

        mbtn_confirm_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CallApiForCancelBooking(bookingInfo.getExpert().getId(),bookingInfo.getBooked_id());
            }
        });

    }

    private void CallApiForCancelBooking(String expertid, String booked_id) {
         String userId = mSharedPreferences.getString(User.ID, "");
        UsercancelBookigRequest usercancelBookigRequest=new UsercancelBookigRequest();
        usercancelBookigRequest.setBooking_user_id(userId);
        usercancelBookigRequest.setBooked_expert_id(expertid);
        usercancelBookigRequest.setBooked_id(booked_id);

        CancelBookingService cancelBookingService = ServiceBuilder.buildService(CancelBookingService.class);
        Call<UserCancelBookingResponse> createRequest = cancelBookingService.getcancelbookService( mSharedPreferences.getString(User.TOKEN, ""),
                usercancelBookigRequest);
        createRequest.enqueue(new Callback<UserCancelBookingResponse>() {
            @Override
            public void onResponse(Call<UserCancelBookingResponse> call, Response<UserCancelBookingResponse> response) {
                Log.d(TAG, "onResponse: responseCancelbook: " + response.toString());
                if (response.body().isSuccess()){
                    Toast.makeText(getApplicationContext(),response.body().getMessage(),Toast.LENGTH_LONG).show();
                    Intent intent=new Intent();
                    setResult(201,intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<UserCancelBookingResponse> call, Throwable t) {
                //Log.d(TAG, "onResponse: response: " + response.toString());
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
            Log.e(TAG, "convertSlotTime: e.getMessage(): ");
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
