  package com.cubetalktest.cubetalk.adapters.recycler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cubetalktest.cubetalk.R;
import com.cubetalktest.cubetalk.databinding.ItemCardFutureBookingBinding;
import com.cubetalktest.cubetalk.mesibicall.CallActivity1;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;
import com.mesibo.api.Mesibo;


import com.mesibo.calls.api.MesiboCall;


import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.cubetalktest.cubetalk.activities.CancelBookingActivity;
import com.cubetalktest.cubetalk.activities.RescheduleSlotSelcetionActivity;
import com.cubetalktest.cubetalk.activities.UserUpcomingBookingsActivity;
import com.cubetalktest.cubetalk.mesibicall.CallActivity;
import com.cubetalktest.cubetalk.models.User;
import com.cubetalktest.cubetalk.models.common.Booking;
import com.cubetalktest.cubetalk.models.common.Expert;
import com.cubetalktest.cubetalk.models.common.Topic;
import com.cubetalktest.cubetalk.utils.Utils;

import static android.content.Context.MODE_PRIVATE;

  public class FutureBookingsAdapter extends RecyclerView.Adapter<FutureBookingsAdapter.ViewHolder> implements
        Mesibo.CallListener
{

    private static final String TAG = FutureBookingsAdapter.class.getSimpleName();

    Mesibo.ReadDbSession mReadSession;

   /* @Override
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
        MesiboCallConfig config = masibocall.getConfig();
     //   config.videoCallTitle = "CubeTalk";
        // userProfile.address = "u3";
       // MesiboCall.getInstance().call(userUpcomingBookingsActivity, Mesibo.random(), userProfile, true);
        VideoCallFragment videoCallFragment = new VideoCallFragment(userUpcomingBookingsActivity,30);
        videoCallFragment.setUser(userProfile1);
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

   /* @Override
    public MesiboCall.CallContext MesiboCall_OnIncoming(MesiboCall.Call call, Mesibo.UserProfile userProfile, boolean b) {
        MesiboCall.CallContext cc;
        if(call != null) {
            *//* launch activity - will be called only if activity was passed*//*
            return null;
        }

        *//* set up call infomation and class name of activity to be launched. You can also pass
           an existing activity. In that case, MesiboCall_OnIncoming will be called again ith the call
           object which you can use in your activity.
         *//*
        cc = new MesiboCall.CallContext(b);
        cc.parent = getApplicationContext();
        cc.className = CallActivity.class;
        *//* setup other parameters as required *//*
        return cc;
    }

    @Override
    public boolean MesiboCall_OnError(MesiboCall.CallContext callContext, int i) {
        return false;
    }*/

   /* @Override
    public MesiboCall.CallProperties MesiboCall_OnIncoming(Mesibo.UserProfile userProfile, boolean video) {
        MesiboCall.CallProperties cc = MesiboCall.getInstance().createCallProperties(video);
        cc.parent = getApplicationContext();
        cc.user = userProfile;
        cc.className = CallActivity.class;
        return cc;
    }

    @Override
    public boolean MesiboCall_OnShowUserInterface(MesiboCall.Call call, MesiboCall.CallProperties callProperties) {
        return false;
    }

    @Override
    public void MesiboCall_OnError(MesiboCall.CallProperties callProperties, int i) {

    }

    @Override
    public boolean MesiboCall_onNotify(int i, Mesibo.UserProfile userProfile, boolean b) {
        return false;
    }*/


    class ViewHolder extends RecyclerView.ViewHolder {

        ShapeableImageView mExpertImage;
        MaterialTextView mExpertNameText;
        MaterialTextView mConsultationTopicText;
        MaterialTextView mBookingDateAndTimeText;
        MaterialTextView mBookingSlotTypeText;
        MaterialButton mRescheduleBookingButton;
        MaterialButton mCancelBookingButton;
        MaterialButton mCallExpertBookingButton;
        MaterialButton mChatWithExpertButton;
        MaterialTextView mCallSlot;
        MaterialTextView mConsultingSlotTypePrice;
        Context context;
        public ViewHolder(@NonNull @NotNull ItemCardFutureBookingBinding itemBinding) {
            super(itemBinding.getRoot());
            mExpertImage = itemBinding.sivExpertProfileImage;
            mExpertNameText = itemBinding.mtvExpertName;
            mConsultationTopicText = itemBinding.mtvConsultationTopic;
            mBookingDateAndTimeText = itemBinding.mtvBookingDateAndTime;
           // mBookingSlotTypeText = itemBinding.mtv_consultation_slot_type_and_price;
            mConsultingSlotTypePrice=itemBinding.mtvConsultationSlotTypeAndPrice;
          //  mBookingSlotTypeText = itemBinding.mtvBookingSlotType;
            mCallSlot=itemBinding.mtvCallSlotType;
            mRescheduleBookingButton = itemBinding.mbtnReschedule;

            mCancelBookingButton = itemBinding.mbtnCancelBooking;
            mCallExpertBookingButton = itemBinding.mbtnCallExpert;
            mChatWithExpertButton = itemBinding.mbtnChatWithExpert;
        }
    }

    private List<Booking> mFutureBookings;
    UserUpcomingBookingsActivity userUpcomingBookingsActivity;
    private SharedPreferences mSharedPreferences;
    public FutureBookingsAdapter(List<Booking> futureBookings, UserUpcomingBookingsActivity userUpcomingBookingsActivity) {
        mFutureBookings = futureBookings;
        this.userUpcomingBookingsActivity=userUpcomingBookingsActivity;
        mSharedPreferences = userUpcomingBookingsActivity.getSharedPreferences(User.PREFERENCE_NAME, MODE_PRIVATE);
        MesiboCall.getInstance().init(userUpcomingBookingsActivity);
        //MesiboCall.getInstance().setListener(this);

        Mesibo.addListener(this);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemCardFutureBookingBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Booking futureBooking = mFutureBookings.get(position);
        View itemView = holder.itemView;
        Context context = itemView.getContext();
        Expert expert = futureBooking.getExpert();
        Topic topic = futureBooking.getTopic();

        if (expert.getProfileImage()!=null)
        Glide.with(holder.itemView.getContext())
                .load(expert.getProfileImage())
                .into(holder.mExpertImage);

        holder.mExpertNameText.setText(expert.getName());
        //"2020-07-16T00:00:00.000Z"  //14.30
        holder.mConsultationTopicText.setText(topic != null ? topic.getName() : "");

        holder.mBookingDateAndTimeText.setText(Utils.convertSlotDate(futureBooking.getCreatedAt()) + " " + Utils.convertSlotTime(futureBooking.getCreatedAt()));
        holder.mCallSlot.setText(Utils.convertSlotDate(futureBooking.getSlotDate()) + " " + Utils.convertSlotTime(futureBooking.getSlotTime()));
        holder.mConsultingSlotTypePrice.setText(Utils.convertSlotType(futureBooking.getSlotType()) + " -INR"+futureBooking.getAmount_paid());

        holder.mCancelBookingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Gson gson = new Gson();
                String bookingInfodata = gson.toJson(futureBooking);
                Intent intentCancelBook= new Intent(context, CancelBookingActivity.class);
                intentCancelBook.putExtra("bookdata", bookingInfodata);
                ((Activity) context).startActivityForResult(intentCancelBook,201);

            }
        });

        holder.mRescheduleBookingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gson gson = new Gson();
                String bookingInfodata = gson.toJson(futureBooking);
                Intent intentReshdule= new Intent(context, RescheduleSlotSelcetionActivity.class);
                intentReshdule.putExtra("bookdata", bookingInfodata);
                context.startActivity(intentReshdule);
            }
        });
        holder.mCallExpertBookingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              try {
                  String slotDate=Utils.convertSlotDate(futureBooking.getSlotDate());
                  String slotTime=Utils.convertSlotTime(futureBooking.getSlotTime());
                  String slotfulldate=slotDate+" "+slotTime;
                  SimpleDateFormat datetimeforslot = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
                  String currentDate_ = new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.getDefault()).format(new Date());
                  Date currentdatetime = datetimeforslot.parse(currentDate_);
                  Date date_slot = datetimeforslot.parse(slotfulldate);
                //  Date date_slot = datetimeforslot.parse("25/03/2021 11:15 AM");
                   String slot_end_datetime=Utils.convertSlotDate(futureBooking.getSlotEndDate())+" "+Utils.convertSlotTime(futureBooking.getSlotEndTime());
                  Date slotEndDateTime=datetimeforslot.parse(slot_end_datetime);

                  if (currentdatetime.getTime()+1000<date_slot.getTime()){
                      new MaterialAlertDialogBuilder(userUpcomingBookingsActivity)
                              .setTitle("Cube Talk")
                              .setMessage("Please wait for your slot time")
                              .setPositiveButton("Ok", (dialog, which) -> {
                                  //startpayment();
                                  dialog.dismiss();
                              })
                              .show();
                  }
                  else if (currentdatetime.getTime()+1000>slotEndDateTime.getTime()){
                      new MaterialAlertDialogBuilder(userUpcomingBookingsActivity)
                              .setTitle("Cube Talk")
                              .setMessage("You missed the meeting slot")
                              .setPositiveButton("Ok", (dialog, which) -> {
                                  //startpayment();
                                  dialog.dismiss();
                              })
                              .show();
                  }
                  else if(currentdatetime.getTime()+1000>date_slot.getTime()){
                      Mesibo.start();
                      MesiboCall.CallProperties cp = MesiboCall.getInstance().createCallProperties(true);
                      Mesibo.UserProfile profile = Mesibo.getUserProfile(futureBooking.getExpert().getId());
                      if(null == profile) {
                          profile = new Mesibo.UserProfile();
                          profile.address = futureBooking.getExpert().getId();
                          profile.name = futureBooking.getExpert().getName();
                      }
                      cp.user = profile;
                      cp.parent = userUpcomingBookingsActivity;
                      cp.className = CallActivity.class; // or whatever is your classname
                      MesiboCall.getInstance().callUi(cp);
                      // MesiboCall masibocall = MesiboCall.getInstance();
                      // masibocall.init(context);
                      // masibocall.setListener(this);
             /*   Mesibo.UserProfile userProfile = new Mesibo.UserProfile();
                userProfile.name = futureBooking.getExpert().getName();
                userProfile.address = futureBooking.getExpert().getId();*/
                      //MesiboCallConfig config = masibocall.getConfig();
                      //config.videoCallTitle = "CubeTalk";
                      // config.layout_video=R.layout.fragment_videocall_custom;
                      //  userProfile.address = "u4";
                      //  MesiboCall.getInstance().call(context, Mesibo.random(), userProfile, true);
                      Mesibo.UserProfile u = new Mesibo.UserProfile();
                      u.name = futureBooking.getExpert().getName();
                      u.address = futureBooking.getExpert().getId();
                      Mesibo.setUserProfile(u, false);

                      int time=Utils.convertSlotTiming(futureBooking.getSlotType());
                      User.CALLTIME=time;
                      long timestamp=Utils.getMilliFromDate(Utils.convertSlotDate(futureBooking.getSlotTime()) + " " + Utils.convertSlotTime(futureBooking.getSlotTime()));
                      //   intent.putExtra("name",pastBooking.getUser().getName());
                      com.cubetalktest.cubetalk.models.User.TIMESTEMP=timestamp;
                      User.BOOKEDID=futureBooking.getBooked_id();

                   //   String slotEndDateTime=Utils.convertSlotDate(futureBooking.getSlotEndDate())+" "+Utils.convertSlotTime(futureBooking.getSlotEndTime());
                      //   com.cubetalktest.cubetalk.models.User.EndDateSlot=slotEndDateTime;
                      com.cubetalktest.cubetalk.models.User.EndDateSlot=slot_end_datetime;
                      //    Toast.makeText(userUpcomingBookingsActivity, String.valueOf(User.CALLTIME),Toast.LENGTH_LONG).show();
                     // Intent intent = new Intent(userUpcomingBookingsActivity, CallActivity.class);
                      //  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                     // intent.putExtra("video", true);
                     // intent.putExtra("address", futureBooking.getExpert().getId());
                     // intent.putExtra("incoming", false);
                  }

             }catch (Exception e){
                  e.printStackTrace();
              }

                   /* String slotDate=Utils.convertSlotDate(futureBooking.getSlotDate());
                    String slotTime=Utils.convertSlotTime(futureBooking.getSlotTime());
                    String slotfulldate=slotDate+" "+slotTime;
                    User.FullDateSlot=slotfulldate;*/
             //   Mesibo.setAccessToken("2e7aeb174f174bfe04344323f31ecb18707a1132f281bfebb15436d");
               // Mesibo.setAccessToken(mSharedPreferences.getString(User.MESIBO_TOKEN,""));

                //userUpcomingBookingsActivity.startActivity(intent);


               /* MesiboCall.getInstance().launchCallActivity(userUpcomingBookingsActivity, CallActivity.class,
                        futureBooking.getExpert().getId(), false);*/
            }
        });


        holder.mChatWithExpertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{
                    SimpleDateFormat datetimeforslot = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
                    String currentDate_ = new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.getDefault()).format(new Date());
                    Date currentdatetime = datetimeforslot.parse(currentDate_);
                  //  Date currentDate = Calendar.getInstance().getTime();
                    String slot_end_datetime=Utils.convertSlotDate(futureBooking.getSlotEndDate())+" "+Utils.convertSlotTime(futureBooking.getSlotEndTime());
                    Date slotEndDateTime=datetimeforslot.parse(slot_end_datetime);
                    //Date slotEndDateTime=datetimeforslot.parse("25/03/2021 10:38 AM");
                    if (currentdatetime.after(slotEndDateTime)){
                        new MaterialAlertDialogBuilder(userUpcomingBookingsActivity)
                                .setTitle("Cube Talk")
                                .setMessage("You missed the chat slot")
                                .setPositiveButton("Ok", (dialog, which) -> {
                                    //startpayment();
                                    dialog.dismiss();
                                })
                                .show();
                    }else{
                        ((Activity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                userUpcomingBookingsActivity.callChatApi(futureBooking);

                            }
                        });
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }


            }
        });
        //holder.mBookingSlotTypeText.setText(Utils.convertSlotType(futureBooking.getSlotType()));

        try {
            String slotDate=Utils.convertSlotDate(futureBooking.getSlotDate());
            String slotTime=Utils.convertSlotTime(futureBooking.getSlotTime());
            String slotfulldate=slotDate+" "+slotTime;
           SimpleDateFormat datetimeforslot=new SimpleDateFormat("dd/MM/yyyy hh:mm a");
         //  Date date_slot = datetimeforslot.parse("25/03/2021 11:15 AM");
            Date end_date_slot=datetimeforslot.parse("14/03/2021 10:55 PM");
            Date date_slot = datetimeforslot.parse(slotfulldate);
           // Date currentdate = new Date();
            String currentDate_ = new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.getDefault()).format(new Date());
            Date currentdatetime=datetimeforslot.parse(currentDate_);

            //long remaining_milliseconds=end_date_slot.getTime()-currentdatetime.getTime();


            long mills = ((date_slot.getTime())+(Utils.convertSlotTiming(futureBooking.getSlotType())*60000)) - currentdatetime.getTime();
            int hours = (int) (mills/(1000 * 60 * 60));
            SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
            Date date = inputDateFormat.parse(futureBooking.getSlotDate());
            Date currentDate = Calendar.getInstance().getTime();

            if (currentDate.before(date) && hours>=6) {
                holder.mRescheduleBookingButton.setEnabled(true);
                holder.mCancelBookingButton.setEnabled(true);
            }
            if ( mills>0 && mills<300000+(Utils.convertSlotTiming(futureBooking.getSlotType())*60000))   {
            //if (currentDate.before(date) && hours<=1) {
                //holder.mCallExpertBookingButton.setEnabled(true);
               // holder.mChatWithExpertButton.setEnabled(true);
                holder.mCallExpertBookingButton.setTextColor(userUpcomingBookingsActivity.getResources().getColor(R.color.colorPrimary));

            }else{
                holder.mCallExpertBookingButton.setTextColor(userUpcomingBookingsActivity.getResources().getColor(R.color.black_overlay));

            }
            String slot_end_datetime=Utils.convertSlotDate(futureBooking.getSlotEndDate())+" "+Utils.convertSlotTime(futureBooking.getSlotEndTime());
            Date slotEndDateTime=datetimeforslot.parse(slot_end_datetime);
            //Date slotEndDateTime=datetimeforslot.parse("25/03/2021 10:38 AM");
            if (currentdatetime.after(slotEndDateTime)){
                holder.mChatWithExpertButton.setTextColor(userUpcomingBookingsActivity.getResources().getColor(R.color.black_overlay));

            }else
                holder.mChatWithExpertButton.setTextColor(userUpcomingBookingsActivity.getResources().getColor(R.color.colorPrimary));


           /* if (mills+(Utils.convertSlotTiming(futureBooking.getSlotType())*1000)<0){
                holder.mCallExpertBookingButton.setEnabled(false);
                holder.mChatWithExpertButton.setEnabled(false);
                holder.mCancelBookingButton.setEnabled(false);
                holder.mRescheduleBookingButton.setEnabled(false);
            }*/
            //holder.mCancelBookingButton.setEnabled(true);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private boolean enableButton(String slotDate, String slotTime) {

        return false;
    }


    @Override
    public int getItemCount() {
        return mFutureBookings.size();
    }

   /* @Override
    public boolean MesiboCall_onNotify(int i, Mesibo.UserProfile userProfile, boolean b) {
        return false;
    }

    @Override
    public MesiboVideoCallFragment MesiboCall_getVideoCallFragment(Mesibo.UserProfile userProfile) {
        //Toast.makeText(this,"video call started",Toast.LENGTH_LONG).show();
        Log.d("call","on mesibocall");
        // Mesibo.setAccessToken("a16c6806b24340f1c4afdab2f66a2b96ea38e2201fd2e99154134");
        Mesibo.setAccessToken("2e7aeb174f174bfe04344323f31ecb18707a1132f281bfebb15436d");
        Mesibo.start();
        MesiboCall masibocall = MesiboCall.getInstance();
        masibocall.init(userUpcomingBookingsActivity);
        // masibocall.setListener(this);
        //  userProfile.name = "232284";
        //   userProfile.picturePath
        // userProfile.address = "u4";
        userProfile.address = "u3";
        VideoCallFragment videoCallFragment = new VideoCallFragment(userUpcomingBookingsActivity,30);
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
}
