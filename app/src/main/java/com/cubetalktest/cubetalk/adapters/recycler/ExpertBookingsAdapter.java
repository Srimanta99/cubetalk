package com.cubetalktest.cubetalk.adapters.recycler;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import com.mesibo.api.Mesibo;

import com.mesibo.calls.api.MesiboCall;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import com.cubetalktest.cubetalk.activities.ExpertBookingManagementActivity;
import com.cubetalktest.cubetalk.databinding.ItemCardExpertBookingBinding;
import com.cubetalktest.cubetalk.mesibicall.CallActivity;
import com.cubetalktest.cubetalk.models.common.Booking;
import com.cubetalktest.cubetalk.models.common.Topic;
import com.cubetalktest.cubetalk.models.common.User;
import com.cubetalktest.cubetalk.models.expert_cancel_booking.ExpertCancelBookingResponse;
import com.cubetalktest.cubetalk.models.expert_cancel_booking.ExpertcancelBookigRequest;
import com.cubetalktest.cubetalk.models.expert_confirm_booking.ExpertConfirmBookingResponse;
import com.cubetalktest.cubetalk.models.expert_confirm_booking.ExpertConrirmBookigRequest;
import com.cubetalktest.cubetalk.services.api.ConfirmBookingService;
import com.cubetalktest.cubetalk.services.api.ExpertCancelBookingService;
import com.cubetalktest.cubetalk.services.api.ServiceBuilder;
import com.cubetalktest.cubetalk.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class ExpertBookingsAdapter extends RecyclerView.Adapter<ExpertBookingsAdapter.ViewHolder> implements Mesibo.CallListener
{

    private static final String TAG = FutureBookingsAdapter.class.getSimpleName();

    Mesibo.ReadDbSession mReadSession;

    /*@Override
    public boolean MesiboCall_onNotify(int i, Mesibo.UserProfile userProfile, boolean b) {
        return false;
    }

    @Override
    public MesiboVideoCallFragment MesiboCall_getVideoCallFragment(Mesibo.UserProfile userProfile) {
        // Mesibo.setAccessToken(mSharedPreferences.getString(User.MESIBO_TOKEN,""));
        // Mesibo.start();

        // masibocall.init(userUpcomingBookingsActivity);
        // masibocall.setListener(this);
        Mesibo.UserProfile userProfile1 = new Mesibo.UserProfile();
        userProfile1.name = userProfile.name;
        userProfile1.address = userProfile.address;
        MesiboCall masibocall = MesiboCall.getInstance();
      //  MesiboCallConfig config = masibocall.getConfig();
       // config.videoCallTitle = "CubeTalk";
        // userProfile.address = "u3";
        // MesiboCall.getInstance().call(userUpcomingBookingsActivity, Mesibo.random(), userProfile, true);
        VideoCallFragment videoCallFragment = new VideoCallFragment(expertBookingManagementActivity,30);
        videoCallFragment.setUser(userProfile);
        return videoCallFragment;

    }*/

   /* @Override
    public MesiboAudioCallFragment MesiboCall_getAudioCallFragment(Mesibo.UserProfile userProfile) {
        return null;
    }

    @Override
    public Fragment MesiboCall_getIncomingAudioCallFragment(Mesibo.UserProfile userProfile) {
        return null;
    }*/

    @Override
    public boolean Mesibo_onCall(long l, long l1, Mesibo.UserProfile userProfile, int i) {
        return false;
    }

    @Override
    public boolean Mesibo_onCallStatus(long l, long l1, int i, long l2, long l3, long l4, String s) {
        return false;
    }



    @Override
    public void Mesibo_onCallServer(int i, String s, String s1, String s2) {

    }


   // private static final String TAG = ExpertBookingsAdapter.class.getSimpleName();

    class ViewHolder extends RecyclerView.ViewHolder {

        ShapeableImageView mExpertImage;
        MaterialTextView mExpertNameText;
        MaterialTextView mConsultationTopicText;
        MaterialTextView mConsultationSlotTypeAndPrice;
        MaterialTextView mBookedDateAndTimeText;
        MaterialTextView mCallDateAndTimeText;
        MaterialButton mCallButton;
        MaterialButton mCancelButton;
        MaterialButton mChat;

        public ViewHolder(@NonNull @NotNull ItemCardExpertBookingBinding itemBinding) {
            super(itemBinding.getRoot());

            mExpertImage = itemBinding.sivExpertProfileImage;
            mExpertNameText = itemBinding.mtvExpertName;
            mConsultationTopicText = itemBinding.mtvConsultationTopic;
            mConsultationSlotTypeAndPrice = itemBinding.mtvConsultationSlotTypeAndPrice;
            mBookedDateAndTimeText = itemBinding.mtvBookedDateAndTime;
            mCallDateAndTimeText = itemBinding.mtvCallDateAndTime;
            mCallButton = itemBinding.mbtnCall;
            mCancelButton = itemBinding.mbtnCancel;
            mChat=itemBinding.mbtnChat;
        }
    }

    private List<Booking> mBookings;
    private  ExpertBookingManagementActivity expertBookingManagementActivity ;
   // private String searchPurpose;
   SharedPreferences mSharedPreferences;

    public ExpertBookingsAdapter(List<Booking> bookings, ExpertBookingManagementActivity expertBookingManagementActivity) {
        mBookings = bookings;
        this.expertBookingManagementActivity=expertBookingManagementActivity;
        mSharedPreferences = expertBookingManagementActivity.getSharedPreferences(com.cubetalktest.cubetalk.models.User.PREFERENCE_NAME, MODE_PRIVATE);
        //searchPurpose=searchpurpose;
        MesiboCall.getInstance().init(expertBookingManagementActivity);
       // MesiboCall.getInstance().setListener(this);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolder(
                ItemCardExpertBookingBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Booking pastBooking = mBookings.get(position);

        User user = pastBooking.getUser();
        Topic topic = pastBooking.getTopic();

        Glide.with(holder.itemView.getContext())
                .load(user.getProfileImage())
                .into(holder.mExpertImage);

        holder.mExpertNameText.setText(user.getName());

        holder.mConsultationTopicText.setText(topic.getName());
        String currency=(mSharedPreferences.getInt(com.cubetalktest.cubetalk.models.User.COUNTRYID, 0)== com.cubetalktest.cubetalk.models.User.INDIACOUNTRYID? "INR ":"INR ");

        holder.mConsultationSlotTypeAndPrice.setText(Utils.convertSlotType(pastBooking.getSlotType()) + " - 500 "+currency);

        holder.mBookedDateAndTimeText.setText(Utils.convertSlotDate(pastBooking.getCreatedAt()) + " " + Utils.convertSlotTime(pastBooking.getCreatedAt()));

        holder.mCallDateAndTimeText.setText(Utils.convertSlotDate(pastBooking.getSlotDate()) + " " + Utils.convertSlotTime(pastBooking.getSlotTime()));
        /*if (expertBookingManagementActivity.Searchpurpose.equalsIgnoreCase("Rejected Bookings")){
            holder.mConformButton.setVisibility(View.INVISIBLE);
            holder.mCancelButton.setVisibility(View.INVISIBLE);
        }else  if (expertBookingManagementActivity.Searchpurpose.equalsIgnoreCase("Approved Bookings")){
            holder.mConformButton.setVisibility(View.INVISIBLE);
            holder.mCancelButton.setVisibility(View.VISIBLE);
        }else  if (expertBookingManagementActivity.Searchpurpose.equalsIgnoreCase("Pending Bookings")){
            holder.mConformButton.setVisibility(View.VISIBLE);
            holder.mCancelButton.setVisibility(View.VISIBLE);
        }*/

        if (pastBooking.getStatus()==2){
            holder.mCallButton.setVisibility(View.VISIBLE);
            holder.mCancelButton.setVisibility(View.VISIBLE);
            holder.mChat.setVisibility(View.VISIBLE);
           // holder.mCallButton.setText("Approved");
        }else{
            holder.mCallButton.setVisibility(View.INVISIBLE);
            //holder.mCallButton.setText("Cancelled");
            holder.mChat.setVisibility(View.INVISIBLE);
            holder.mCancelButton.setVisibility(View.INVISIBLE);
        }
        holder.mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new MaterialAlertDialogBuilder(expertBookingManagementActivity)
                        .setTitle("Cube Talk")
                        .setMessage("Are you sure to cancel?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            callApiforCancelBooking(holder.itemView.getContext(),user.getId(),pastBooking.getBooked_id(),position);

                        })
                        .setNegativeButton("No", null)
                        .show();

            }
        });
      holder.mCallButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            //  Mesibo.setAccessToken(mSharedPreferences.getString(local.impactlife.cubetalk.models.User.MESIBO_TOKEN,""));
             // Mesibo.setAccessToken("a16c6806b24340f1c4afdab2f66a2b96ea38e2201fd2e99154134");
            //  Mesibo.setAccessToken("2e7aeb174f174bfe04344323f31ecb18707a1132f281bfebb15436d");
              Mesibo.start();
              //MesiboCall masibocall = MesiboCall.getInstance();
              //masibocall.init(expertBookingManagementActivity);
              // masibocall.setListener(this);
            //  Mesibo.UserProfile userProfile = new Mesibo.UserProfile();
              //  userProfile.name = "232284";
             // userProfile.address = pastBooking.getUser().getId();
             // userProfile.name = pastBooking.getUser().getName();
              Mesibo.UserProfile u = new Mesibo.UserProfile();
              u.name = pastBooking.getUser().getName();
              u.address = pastBooking.getUser().getId();
              Mesibo.setUserProfile(u, false);
              Intent intent = new Intent(expertBookingManagementActivity, CallActivity.class);
              int time=Utils.convertSlotTiming(pastBooking.getSlotType());
              com.cubetalktest.cubetalk.models.User.CALLTIME=time;
              //  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
              intent.putExtra("video", true);
              intent.putExtra("address", pastBooking.getUser().getId());
           //   intent.putExtra("name",pastBooking.getUser().getName());
              intent.putExtra("incoming", false);
              expertBookingManagementActivity.startActivity(intent);
            //  MesiboCall.getInstance().launchCallActivity(expertBookingManagementActivity, CallActivity.class,
                     // pastBooking.getUser().getId(), false);
             // MesiboCallConfig config = masibocall.getConfig();
             // config.videoCallTitle = "CubeTalk";
            //  userProfile.address = "u4";
            //  MesiboCall.getInstance().call(expertBookingManagementActivity, Mesibo.random(), userProfile, true);
          }
      });
        holder.mChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               expertBookingManagementActivity.callChatApi(pastBooking);
            }
        });


    }

    private void callApiforConfirmBook(Context context, String expert_id, String booked_id,int position) {
        SharedPreferences mSharedPreferences;

        mSharedPreferences = context.getSharedPreferences(com.cubetalktest.cubetalk.models.User.PREFERENCE_NAME, MODE_PRIVATE);
        String userId = mSharedPreferences.getString(com.cubetalktest.cubetalk.models.User.ID, "");
        ExpertConrirmBookigRequest expertConrirmBookigRequest=new ExpertConrirmBookigRequest();
        expertConrirmBookigRequest.setBooked_expert_id(userId);
        expertConrirmBookigRequest.setBooked_id(booked_id);


        ConfirmBookingService confirmBookingService = ServiceBuilder.buildService(ConfirmBookingService.class);
        Call<ExpertConfirmBookingResponse> createRequest = confirmBookingService.getConfirmbookService(mSharedPreferences.getString(com.cubetalktest.cubetalk.models.User.TOKEN, ""),
                expertConrirmBookigRequest);
        createRequest.enqueue(new Callback<ExpertConfirmBookingResponse>() {
            @Override
            public void onResponse(Call<ExpertConfirmBookingResponse> call, Response<ExpertConfirmBookingResponse> response) {
                Log.d(TAG, "onResponse: responseConfirmbookExpert: " + response.toString());
                if (response.body().isSuccess()){
                    Toast.makeText(expertBookingManagementActivity, response.body().getMessage(), Toast.LENGTH_LONG).show();
                   // mBookings.remove(position);
                    //notifyItemRemoved(position);
                    expertBookingManagementActivity.fetchBookings(2);
                   // notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ExpertConfirmBookingResponse> call, Throwable t) {
                Log.d(TAG, "onResponse: response: " + t.toString());
            }
        });



       /* ConfirmBookingService confirmBookingService=ServiceBuilder.buildService(ConfirmBookingService.class);
        Call<ExpertConfirmBookingResponse> requestCall=confirmBookingService.getConfirmbookService(mSharedPreferences.getString(local.impactlife.cubetalk.models.User.TOKEN, ""),
                expertConrirmBookigRequest);
          requestCall.enqueue(new Callback<ExpertConfirmBookingResponse>() {
              @Override
              public void onResponse(Call<ExpertConfirmBookingResponse> call, Response<ExpertConfirmBookingResponse> response) {
                  Log.d(TAG, "onResponse: responseConfirmbookExpert: " + response.toString());
                  if (response.body().isSuccess()){
                      mBookings.remove(position);
                      //notifyItemRemoved(position);
                      notifyDataSetChanged();
                  }
              }

              @Override
              public void onFailure(Call<ExpertConfirmBookingResponse> call, Throwable t) {
                  Log.d(TAG, "onResponse: response: " + t.toString());
              }
          });*/
    }


    private void callApiforCancelBooking(Context context, String bookeduserid_id,String bookedid, int position) {
         SharedPreferences mSharedPreferences;
        mSharedPreferences = context.getSharedPreferences(com.cubetalktest.cubetalk.models.User.PREFERENCE_NAME, MODE_PRIVATE);
        String userId = mSharedPreferences.getString(com.cubetalktest.cubetalk.models.User.ID, "");
        ExpertcancelBookigRequest expertcancelBookigRequest=new ExpertcancelBookigRequest();
        expertcancelBookigRequest.setBooking_user_id(bookeduserid_id);
        expertcancelBookigRequest.setBooked_expert_id(userId);
        expertcancelBookigRequest.setBooked_id(bookedid);

        ExpertCancelBookingService cancelBookingService = ServiceBuilder.buildService(ExpertCancelBookingService.class);
        Call<ExpertCancelBookingResponse> createRequest = cancelBookingService.getcancelbookService(mSharedPreferences.getString(com.cubetalktest.cubetalk.models.User.TOKEN, ""),
                expertcancelBookigRequest);
        createRequest.enqueue(new Callback<ExpertCancelBookingResponse>() {
            @Override
            public void onResponse(Call<ExpertCancelBookingResponse> call, Response<ExpertCancelBookingResponse> response) {
                Log.d(TAG, "onResponse: responseCancelbookExpert: " + response.toString());
                if (response.body().isSuccess()){
                    Toast.makeText(expertBookingManagementActivity, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    expertBookingManagementActivity.fetchBookings(2);
                    //mBookings.remove(position);
                  //notifyItemRemoved(position);
                  //  notifyDataSetChanged();
                }
                else
                    Toast.makeText(expertBookingManagementActivity, response.body().getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ExpertCancelBookingResponse> call, Throwable t) {
                //Log.d(TAG, "onResponse: response: " + response.toString());
            }
        });

    }

    @Override
    public int getItemCount() {
        return mBookings.size();
    }
}
