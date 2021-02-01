package com.cubetalktest.cubetalk.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.mesibo.api.Mesibo;

import com.mesibo.calls.api.MesiboCall;
import com.mesibo.messaging.MesiboUI;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import com.cubetalktest.cubetalk.adapters.recycler.FutureBookingsAdapter;
import com.cubetalktest.cubetalk.databinding.ActivityUserUpcomingBookingsBinding;
import com.cubetalktest.cubetalk.databinding.ContentUserUpcomingBookingsBinding;
import com.cubetalktest.cubetalk.models.User;
import com.cubetalktest.cubetalk.models.common.Booking;
import com.cubetalktest.cubetalk.models.get_future_bookings.FutureBookingRequest;
import com.cubetalktest.cubetalk.models.get_future_bookings.FutureBookingResponse;
import com.cubetalktest.cubetalk.services.api.BookingService;
import com.cubetalktest.cubetalk.services.api.ServiceBuilder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserUpcomingBookingsActivity extends AppCompatActivity    {
    //MesiboCall.MesiboCallListener,
    //Mesibo.CallListener
    private static final String TAG = UserUpcomingBookingsActivity.class.getSimpleName();
    private String mUserId,token;
    private ActivityUserUpcomingBookingsBinding mActivityBinding;
    private ContentUserUpcomingBookingsBinding mContentBinding;
    private RecyclerView mFutureBookingsRecycler;
    private List<Booking> mFutureBookings;
    private FutureBookingsAdapter mFutureBookingsAdapter;
    Mesibo.ReadDbSession mReadSession;
    SharedPreferences mSharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_user_upcoming_bookings);

        mActivityBinding = ActivityUserUpcomingBookingsBinding.inflate(LayoutInflater.from(this));
        setContentView(mActivityBinding.getRoot());

        Toolbar toolbar = mActivityBinding.toolbar;
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Upcoming Bookings");
        }

        mSharedPreferences = getSharedPreferences(User.PREFERENCE_NAME, MODE_PRIVATE);
        mUserId = mSharedPreferences.getString(User.ID, "");
        token=mSharedPreferences.getString(User.TOKEN, "");
        mContentBinding = mActivityBinding.contentUserUpcomingBookings;

        mFutureBookings = new ArrayList<>();

        mFutureBookingsAdapter = new FutureBookingsAdapter(mFutureBookings,this);

        mFutureBookingsRecycler = mContentBinding.rvFutureBookings;

        mFutureBookingsRecycler.setAdapter(mFutureBookingsAdapter);
          callApiforUserUpcomingBooking();
       /* Mesibo api = Mesibo.getInstance();
        api.init(getApplicationContext());

        Mesibo.addListener(this);
        Mesibo.setSecureConnection(true);
       // Mesibo.setAccessToken("a16c6806b24340f1c4afdab2f66a2b96ea38e2201fd2e99154134");
        Mesibo.setAccessToken("2e7aeb174f174bfe04344323f31ecb18707a1132f281bfebb15436d");
        Mesibo.setDatabase("mydb", 0);
        Mesibo.start();*/

        MesiboCall.getInstance().init(getApplicationContext());
       // MesiboCall.getInstance().setListener(this);
        Mesibo api = Mesibo.getInstance();
        api.init(getApplicationContext());
        Mesibo.start();

        /* initialize call */

        Mesibo.addListener(this);
    }

    private void callApiforUserUpcomingBooking() {
        FutureBookingRequest futureBookingRequest = new FutureBookingRequest();
        futureBookingRequest.setUserId(mUserId);

        BookingService bookingService = ServiceBuilder.buildService(BookingService.class);
        Call<FutureBookingResponse> request = bookingService.getUserFutureBookings(token,futureBookingRequest);

        request.enqueue(new Callback<FutureBookingResponse>() {
            @Override
            public void onResponse(@NotNull Call<FutureBookingResponse> call, @NotNull Response<FutureBookingResponse> response) {
                Log.d(TAG, "onResponse: "+response.body().toString());

                if (response.isSuccessful() && response.body() != null) {
                    FutureBookingResponse futureBookingResponse = response.body();
                    if (futureBookingResponse.getFutureBookings().size() == 0) {
                        mFutureBookings.clear();
                        mFutureBookingsAdapter.notifyDataSetChanged();
                        Toast.makeText(UserUpcomingBookingsActivity.this, "No bookings found", Toast.LENGTH_SHORT).show();
                    } else {
                        mFutureBookings.clear();
                        mFutureBookings.addAll(futureBookingResponse.getFutureBookings());
                        mFutureBookingsAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<FutureBookingResponse> call, @NotNull Throwable throwable) {
                Log.d(TAG, "onFailure: throwable.getMessage(): " + throwable.getMessage());
                //callChatApi();
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

    @Override
    protected void onResume() {
        super.onResume();
      //  callApiforUserUpcomingBooking();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==201){
            callApiforUserUpcomingBooking();
        }
    }

    public void callChatApi(Booking futureBooking){
        Mesibo api = Mesibo.getInstance();
        api.init(this);
        Mesibo.addListener(this);
        Mesibo.setSecureConnection(true);
        Mesibo.setAccessToken(mSharedPreferences.getString(User.MESIBO_TOKEN,""));
        //Mesibo.setAccessToken("2e7aeb174f174bfe04344323f31ecb18707a1132f281bfebb15436d");
      //  Mesibo.setAccessToken("a16c6806b24340f1c4afdab2f66a2b96ea38e2201fd2e99154134");
        Mesibo.setDatabase("mydb", 0);
        Mesibo.start();
        // Mesibo.start();
        // set path for storing DB and messaging files
        //Mesibo.setPath(Environment.getExternalStorageDirectory().getAbsolutePath());
        // add listener
        //  Mesibo.addListener(this);
         Mesibo.UserProfile userProfile = new Mesibo.UserProfile();
         userProfile.name = futureBooking.getExpert().getName();
         userProfile.address = futureBooking.getExpert().getId();
        // userProfile.address = "u3";
        // userProfile.name = "u3";
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

    /*@Override
    public boolean Mesibo_onCall(long l, long l1, Mesibo.UserProfile userProfile, int i) {
        return false;
    }

    @Override
    public boolean Mesibo_onCallStatus(long l, long l1, int i, int i1, String s) {
        return false;
    }

    @Override
    public void Mesibo_onCallServer(int i, String s, String s1, String s2) {

    }
*/
   /* @Override
    public boolean MesiboCall_onNotify(int i, Mesibo.UserProfile userProfile, boolean b) {
        return false;
    }

    @Override
    public MesiboVideoCallFragment MesiboCall_getVideoCallFragment(Mesibo.UserProfile userProfile) {
        MesiboCall masibocall = MesiboCall.getInstance();
        masibocall.init(this);
        MesiboCallConfig config = masibocall.getConfig();
        config.videoCallTitle = "CubeTalk";
        // userProfile.address = "u3";
        //MesiboCall.getInstance().call(this, Mesibo.random(), userProfile, true);
        VideoCallFragment videoCallFragment = new VideoCallFragment(this,30);
        videoCallFragment.setUser(userProfile);
        return videoCallFragment;
    }

    @Override
    public MesiboAudioCallFragment MesiboCall_getAudioCallFragment(Mesibo.UserProfile userProfile) {
        return null;
    }

    @Override
    public Fragment MesiboCall_getIncomingAudioCallFragment(Mesibo.UserProfile userProfile) {
        return null;
    }*/

    /*@Override
    public boolean MesiboCall_onNotify(int i, Mesibo.UserProfile userProfile, boolean b) {
    //    Toast.makeText(this,"video call notify"+b,Toast.LENGTH_LONG).show();
        return false;
    }

    @Override
    public MesiboVideoCallFragment MesiboCall_getVideoCallFragment(Mesibo.UserProfile userProfile) {
        Toast.makeText(this,"video call started",Toast.LENGTH_LONG).show();
        Log.d("call","on mesibocall");
         Mesibo.setAccessToken("a16c6806b24340f1c4afdab2f66a2b96ea38e2201fd2e99154134");
       // Mesibo.setAccessToken("2e7aeb174f174bfe04344323f31ecb18707a1132f281bfebb15436d");
        Mesibo.start();
        MesiboCall masibocall = MesiboCall.getInstance();
        masibocall.init(this);
        // masibocall.setListener(this);
        //  userProfile.name = "232284";
        //   userProfile.picturePath
         // userProfile.address = "u4";
        userProfile.address = "u3";
        VideoCallFragment videoCallFragment = new VideoCallFragment(this,30);
        videoCallFragment.setUser(userProfile);
        return videoCallFragment;
    }

    @Override
    public MesiboAudioCallFragment MesiboCall_getAudioCallFragment(Mesibo.UserProfile userProfile) {
        return null;
    }

    @Override
    public Fragment MesiboCall_getIncomingAudioCallFragment(Mesibo.UserProfile userProfile) {
        return null;
    }*/

   /* @Override
    public boolean Mesibo_onCall(long l, long l1, Mesibo.UserProfile userProfile, int i) {
        Toast.makeText(this,"video call started user",Toast.LENGTH_LONG).show();
        Log.d("call","on Video call calling");
        return false;
    }

    @Override
    public boolean Mesibo_onCallStatus(long l, long l1, int i, int i1, String s) {
        Toast.makeText(this,"video call connected"+s,Toast.LENGTH_LONG).show();
        Log.d("call","on Video call connected"+s);
        if (s!=null) {

            try {
                final JSONObject obj = new JSONObject(s);
               // call_Tatus.setText(obj.getString("type"));
                new CountDownTimer(10000, 1000) {

                    public void onTick(long millisUntilFinished) {

                      //  call_Time.setText("seconds remaining: " + millisUntilFinished / 1000);
                        Toast.makeText(UserUpcomingBookingsActivity.this,"seconds remaining: " + (millisUntilFinished / 1000),Toast.LENGTH_LONG).show();

                    }

                    public void onFinish() {
                      //  call_Time.setText("Call ended");
                      //  hangup();
                        Toast.makeText(UserUpcomingBookingsActivity.this,"video call END",Toast.LENGTH_LONG).show();
                    }
                }.start();

            } catch (JSONException e) {
                e.printStackTrace();
               // call_Tatus.setText("calling");
            }

            //Toast.makeText(mainActivity,"video call connected sucess"+s,Toast.LENGTH_LONG).show();
         *//*   LayoutInflater inflater = getLayoutInflater();
            View dialoglayout = inflater.inflate(R.layout.activity_call_logs, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
            builder.setView(dialoglayout);
            builder.show();*//*
        }
        //else{
          //  llcalview.setVisibility(View.VISIBLE);
            //llcalv.setVisibility(View.INVISIBLE);
        //}
        return false;
    }

    @Override
    public void Mesibo_onCallServer(int i, String s, String s1, String s2) {

    }*/
}