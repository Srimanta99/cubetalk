package local.impactlife.cubetalk.adapters.recycler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;
import com.mesibo.api.Mesibo;
import com.mesibo.calls.MesiboAudioCallFragment;
import com.mesibo.calls.MesiboCall;
import com.mesibo.calls.MesiboCallConfig;
import com.mesibo.calls.MesiboVideoCallFragment;
import com.mesibo.messaging.MesiboUI;


import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import local.impactlife.cubetalk.R;
import local.impactlife.cubetalk.VideoCallFragment;
import local.impactlife.cubetalk.activities.CancelBookingActivity;
import local.impactlife.cubetalk.activities.RescheduleBookingActivity;
import local.impactlife.cubetalk.activities.RescheduleSlotSelcetionActivity;
import local.impactlife.cubetalk.activities.UserUpcomingBookingsActivity;
import local.impactlife.cubetalk.activities.VideoCallActivity;
import local.impactlife.cubetalk.databinding.ItemCardFutureBookingBinding;
import local.impactlife.cubetalk.models.User;
import local.impactlife.cubetalk.models.common.Booking;
import local.impactlife.cubetalk.models.common.Expert;
import local.impactlife.cubetalk.models.common.Topic;
import local.impactlife.cubetalk.utils.Utils;

import static android.content.Context.MODE_PRIVATE;
import static org.webrtc.ContextUtils.getApplicationContext;

public class FutureBookingsAdapter extends RecyclerView.Adapter<FutureBookingsAdapter.ViewHolder> implements
        MesiboCall.MesiboCallListener,Mesibo.CallListener
{

    private static final String TAG = FutureBookingsAdapter.class.getSimpleName();

    Mesibo.ReadDbSession mReadSession;

    @Override
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
    }

    @Override
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
        MesiboCall.getInstance().setListener(this);


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
             //   Mesibo.setAccessToken("2e7aeb174f174bfe04344323f31ecb18707a1132f281bfebb15436d");
               // Mesibo.setAccessToken(mSharedPreferences.getString(User.MESIBO_TOKEN,""));
                Mesibo.start();
               // MesiboCall masibocall = MesiboCall.getInstance();
               // masibocall.init(context);
                // masibocall.setListener(this);
                Mesibo.UserProfile userProfile = new Mesibo.UserProfile();
                userProfile.name = futureBooking.getExpert().getName();
                userProfile.address = futureBooking.getExpert().getId();
               // MesiboCallConfig config = masibocall.getConfig();
              //  config.videoCallTitle = "CubeTalk";
               // config.layout_video=R.layout.fragment_videocall_custom;
              //  userProfile.address = "u4";
                MesiboCall.getInstance().call(context, Mesibo.random(), userProfile, true);

            }
        });


        holder.mChatWithExpertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        userUpcomingBookingsActivity.callChatApi(futureBooking);
                        /*Mesibo api = Mesibo.getInstance();
                        api.init(context);
                        Mesibo.addListener(this);
                        Mesibo.setSecureConnection(true);
                       // Mesibo.setAccessToken("2e7aeb174f174bfe04344323f31ecb18707a1132f281bfebb15436d");
                        // Mesibo.setAccessToken("a16c6806b24340f1c4afdab2f66a2b96ea38e2201fd2e99154134");
                      //  Mesibo.setDatabase("mydb", 0);
                        //Mesibo.start();

                        // Mesibo.start();
                        // set path for storing DB and messaging files
                            Mesibo.setPath(Environment.getExternalStorageDirectory().getAbsolutePath());
                        // add listener
                        //  Mesibo.addListener(this);
                        Mesibo.UserProfile userProfile = new Mesibo.UserProfile();
                        //  userProfile.name = "232284";
                        userProfile.address = "u4";
                        //userProfile.address = "u3";

                        // MesiboCall.getInstance().init(context);

                        // MesiboCall.getInstance().setListener(this);
                        // Read receipts are enabled only when App is set to be in foreground
                        // Mesibo.setAppInForeground(this, 0, true);
              *//*  mReadSession = new Mesibo.ReadDbSession(userProfile.address, this);
                mReadSession.enableReadReceipt(true);
                mReadSession.read(100);*//*
                        MesiboUI.launchMessageView(context, userProfile.address, 0);*/
                    }
                });

            }
        });
        //holder.mBookingSlotTypeText.setText(Utils.convertSlotType(futureBooking.getSlotType()));

        try {
            String slotDate=Utils.convertSlotDate(futureBooking.getSlotDate());
            String slotTime=Utils.convertSlotTime(futureBooking.getSlotTime());
            String slotfulldate=slotDate+" "+slotTime;
            SimpleDateFormat datetimeforslot=new SimpleDateFormat("dd/MM/yyyy HH:mm a");
            Date date_slot = datetimeforslot.parse(slotfulldate);
           // Date currentdate = new Date();
            String currentDate_ = new SimpleDateFormat("dd/MM/yyyy HH:mm a", Locale.getDefault()).format(new Date());
            Date currentdatetime=datetimeforslot.parse(currentDate_);

            long mills = date_slot.getTime() - currentdatetime.getTime();
            int hours = (int) (mills/(1000 * 60 * 60));

            SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
            Date date = inputDateFormat.parse(futureBooking.getSlotDate());
            Date currentDate = Calendar.getInstance().getTime();
            if (currentDate.before(date) && hours>=6) {
                holder.mRescheduleBookingButton.setEnabled(true);
                holder.mCancelBookingButton.setEnabled(true);
            }
          //  if (currentDate.before(date) && hours<=1) {
                holder.mCallExpertBookingButton.setEnabled(true);
             //   holder.mChatWithExpertButton.setEnabled(true);
           // }
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
