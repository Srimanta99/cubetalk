package com.cubetalktest.cubetalk.mesibicall;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.cubetalktest.cubetalk.models.User;
import com.cubetalktest.cubetalk.models.login_user.LoginRequest;
import com.cubetalktest.cubetalk.models.login_user.LoginResponse;
import com.cubetalktest.cubetalk.models.updateremaining_time.CallRemainingTimeResponse;
import com.cubetalktest.cubetalk.models.updateremaining_time.CallTimeReaminingRequest;
import com.cubetalktest.cubetalk.services.api.LoginService;
import com.cubetalktest.cubetalk.services.api.ReamningTimeService;
import com.cubetalktest.cubetalk.services.api.ServiceBuilder;
import com.cubetalktest.cubetalk.utils.Utils;
import com.mesibo.api.Mesibo;
import com.mesibo.calls.api.MesiboCall;
import com.mesibo.calls.api.MesiboCallActivity;
import com.mesibo.calls.api.MesiboVideoView;

import com.cubetalktest.cubetalk.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mesibo.calls.api.MesiboCall.MESIBOCALL_SOUND_RINGING;
import static com.mesibo.calls.api.MesiboCall.MESIBOCALL_UI_STATE_SHOWCONTROLS;
import static com.mesibo.calls.api.MesiboCall.MESIBOCALL_UI_STATE_SHOWINCOMING;


public class CallFragment extends Fragment implements MesiboCall.InProgressListener,Mesibo.ConnectionListener, View.OnClickListener {

    public static final String TAG = "CallFragment";
    protected MesiboCall.Call mCall = null;
    protected MesiboCall.CallProperties mCp = null;
    protected MesiboCallActivity mActivity = null;
    int counter = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_call, container, false);

        TextView serviceName = (TextView)view.findViewById(R.id.title);
        serviceName.setText("CubeTalk");

        // Create UI controls.
        ui.controlLayout = view.findViewById(R.id.control_container);

        ui.pipVideo = view.findViewById(R.id.pip_video_view);
        ui.fullscreenVideo = view.findViewById(R.id.fullscreen_video_view);
        ui.contactView = view.findViewById(R.id.call_name);
        ui.status = view.findViewById(R.id.call_status);
        ui.disconnectButton = view.findViewById(R.id.button_call_disconnect);
        ui.cameraSwitchButton = view.findViewById(R.id.button_call_switch_camera);
        ui.toggleSpeakerButton = view.findViewById(R.id.button_call_toggle_speaker);
        ui.toggleCameraButton = view.findViewById(R.id.button_call_toggle_camera);
        ui.toggleMuteButton = view.findViewById(R.id.button_call_toggle_mic);
        ui.acceptButton = view.findViewById(R.id.incoming_call_connect);
        ui.acceptAudioButton = view.findViewById(R.id.incoming_audio_call_connect);
        ui.declineButton = view.findViewById(R.id.incoming_call_disconnect);

        ui.cameraToggleLayout = view.findViewById(R.id.layout_toggle_camera);
        ui.cameraSwitchLayout = view.findViewById(R.id.layout_switch_camera);

        ui.incomingView = view.findViewById(R.id.incoming_call_container);
        ui.inprogressView = view.findViewById(R.id.outgoing_call_container);
        ui.incomingAudioAcceptLayout = view.findViewById(R.id.incoming_audio_accept_container);
        ui.incomingVideoAcceptLayout = view.findViewById(R.id.incoming_video_accept_container);


        ui.remoteMic = view.findViewById(R.id.remote_mic);
        ui.remoteCamera = view.findViewById(R.id.remote_cam);

        ui.disconnectButton.setOnClickListener(this);
        ui.cameraSwitchButton.setOnClickListener(this);
        ui.toggleSpeakerButton.setOnClickListener(this);
        ui.toggleCameraButton.setOnClickListener(this);
        ui.toggleMuteButton.setOnClickListener(this);
        ui.acceptButton.setOnClickListener(this);
        ui.acceptAudioButton.setOnClickListener(this);
        ui.declineButton.setOnClickListener(this);

        // Swap feeds on pip view click.
        if(null != ui.pipVideo) {
            ui.pipVideo.setOnClickListener(this);
            ui.pipVideo.enablePip(true);
        }

        if(null != ui.fullscreenVideo) {
            ui.fullscreenVideo.setOnClickListener(this);
            ui.fullscreenVideo.scaleToFill(true);
            ui.fullscreenVideo.enableHardwareScaler(false);
        }

        ui.background = view.findViewById(R.id.userImage);


        ui.thumbnailLayout = view.findViewById(R.id.photo_layout);
        TextView nameView = (TextView)view.findViewById(R.id.call_name);
        //TextView addrView = (TextView)view.findViewById(R.id.call_address);
        ImageView imageView = view.findViewById(R.id.photo_image);
        setUserDetails(nameView, imageView);

        setStatusView(Mesibo.CALLSTATUS_NONE);

        //CallManager.getInstance().Mesibo_onCallStatus(0, 0, mCall.status, 0, null);

        //setCallView();
        setSwappedFeeds(true);
        //setCallControlsVisibility(!mCallCtx.answered, true);
        ui.pipVideo.setVisibility(mCall.isAnswered()? View.VISIBLE: View.GONE);

        mCall.start((MesiboCallActivity) getActivity(), this);
        return view;
    }

    void answer(boolean video) {
        if(mCall.isAnswered() || !mCall.isIncoming()) return;

        mCall.answer(video);
        setSwappedFeeds(false);
        //setCallView();
        if(mCall.isVideoCall() && video)
            ui.pipVideo.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        // we can't use switch and hence using if-else
        if(id == R.id.pip_video_view)
            setSwappedFeeds(!mSwappedFeeds);
        else if(id == R.id.fullscreen_video_view)
            toggleCallControlsVisibility();
        else if(id == R.id.incoming_call_disconnect || id == R.id.button_call_disconnect) {
            mCall.hangup();
            setStatusView(Mesibo.CALLSTATUS_COMPLETE);
            //setCallControlsVisibility(true, true);
            mActivity.delayedFinish(500); // if user clicked end, close asap
        }
        else if(id == R.id.incoming_call_connect) {
            answer(true);
        }  else if(id == R.id.incoming_audio_call_connect) {
            answer(false);
        } else if(id == R.id.button_call_toggle_speaker) {
            mCall.toggleAudioDevice(MesiboCall.AudioDevice.SPEAKER);
        } else if(id == R.id.button_call_toggle_mic) {
            boolean enabled = mCall.toggleAudioMute();
            setButtonAlpha(ui.toggleMuteButton, enabled);
        } else if(id == R.id.button_call_switch_camera) {
            mCall.switchCamera();
        } else if(id == R.id.button_call_toggle_camera) {
            boolean enabled = mCall.toggleVideoMute();
            setButtonAlpha(ui.toggleCameraButton, enabled);
        }
    }

    private boolean mSwappedFeeds = false;
    private void setSwappedFeeds(boolean isSwappedFeeds) {
        if(!mCall.isVideoCall()) return;

        Log.d(TAG, "setSwappedFeeds: " + isSwappedFeeds);
        mSwappedFeeds = isSwappedFeeds;
        mCall.setVideoView(ui.fullscreenVideo, !mSwappedFeeds);
        mCall.setVideoView(ui.pipVideo, mSwappedFeeds);

///////////////////
       // ui.fullscreenVideo.enableMirror(mCp.video.mirror);
       // ui.pipVideo.enableMirror(mCp.video.mirror);
  ////////////////////
    }

    public void setUserDetails(TextView nameView, ImageView image) {

        if(!TextUtils.isEmpty(mCp.user.name)) {
            nameView.setText(mCp.user.name);
        } else
            nameView.setText(mCp.user.address);

        if(null != image)
            image.setImageDrawable(getActivity().getDrawable(R.drawable.cubetalk_logo));
    }

    @Override
    public void onResume() {
        super.onResume();
        setButtonAlpha(ui.toggleMuteButton, mCall.getMuteStatus(true, false, false));
        setButtonAlpha(ui.toggleCameraButton, mCall.getMuteStatus(false, true, false));
        updateRemoteMuteButtons();
        if(mCp.autoAnswer) {
            answer(mCall.isVideoCall());
        }
    }


    public void setStatusView(int status) {
        if(mCall.isAnswered() && mCall.isCallInProgress() && mCall.isCallConnected()) {
            ui.status.setFormat(null);
            ui.status.setText("");
            /////
            //ui.status.setBase(mCall.getAnswerTime());
            //ui.status.start();
            ///////
            return;
        }

        ui.status.stop();
        ui.mStatusText = statusToString(status, ui.mStatusText);
        ui.status.setText(ui.mStatusText);
    }

    public void updateRemoteMuteButtons() {
        ui.remoteMic.setVisibility(mCall.getMuteStatus(true, false, true)? View.VISIBLE: View.INVISIBLE);
        ui.remoteCamera.setVisibility(mCall.getMuteStatus(false, true, true)? View.VISIBLE: View.INVISIBLE);
    }


    // Should be called from UI thread
    private boolean mConnected = false;
    private void callConnected(boolean video) {
        if(mConnected) return;
        mConnected = true;
        if(video) {
            ui.pipVideo.setVisibility(View.VISIBLE);
            setSwappedFeeds(false);
        }

        if(!mCall.isIncoming()) {
            Long calltine = Long.valueOf(User.CALLTIME);
            String fullDateSlot=User.FullDateSlot;
           final  long reamning_mills;
            try{
                SimpleDateFormat datetimeforslot=new SimpleDateFormat("dd/MM/yyyy hh:mm a");
               // Date date_slot = datetimeforslot.parse("12/03/2021 12:48 PM");
                Date end_date_slot=datetimeforslot.parse(User.EndDateSlot);
                // Date currentdate = new Date();
                String currentDate_ = new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.getDefault()).format(new Date());
                Date currentdatetime=datetimeforslot.parse(currentDate_);
             //   reamning_mills = ((date_slot.getTime())+(calltine*60000)) - currentdatetime.getTime();

                reamning_mills=( end_date_slot.getTime()-currentdatetime.getTime());
                //long seconds = (reamning_mills / 1000)  ;
               // long remailngcalltimediff=(calltine*60-seconds)*1000;
               // long remailngcalltimediff=60000;

                new CountDownTimer(reamning_mills, 1000) {
                    public void onTick(long millisUntilFinished) {
                        int sec= (int) (reamning_mills-counter);
                        int min=(sec / 1000) / 60;
                        int remainsec=(sec / 1000) % 60;
                        ui.status.setText("Time remaining " + String.valueOf(min)+" Min. "+ String.valueOf(remainsec)+" Sec. ");
                        counter=counter+1000;
                    }

                    public void onFinish() {
                        mCall.hangup();
                        ui.status.setText("FINISH!!");
                        setStatusView(Mesibo.CALLSTATUS_COMPLETE);
                        //setCallControlsVisibility(true, true);
                        mActivity.delayedFinish(500);

                    }
                }.start();

            }catch (Exception e){
                e.printStackTrace();
            }
           // User.TIMESTEMP=Utils.getcurrentTimestemp()-4000;
         //   if (User.TIMESTEMP>Utils.getcurrentTimestemp()) {

           /* }else{
                long timediff=Utils.getcurrentTimestemp()-User.TIMESTEMP;
                int time= (int) (timediff/1000);
                int timeElapsed= (int) (calltine-time);
                new CountDownTimer(timeElapsed * 1000, 1000) {
                    public void onTick(long millisUntilFinished) {
                        ui.status.setText("Seconds remaining " + String.valueOf(timeElapsed - counter));
                        counter++;
                    }

                    public void onFinish() {
                        mCall.hangup();
                        ui.status.setText("FINISH!!");
                        setStatusView(Mesibo.CALLSTATUS_COMPLETE);
                        //setCallControlsVisibility(true, true);
                        mActivity.delayedFinish(500);

                    }
                }.start();
            }*/
        }
    }

    @Override
    public void MesiboCall_OnVideo(MesiboCall.CallProperties p, MesiboCall.VideoProperties video, boolean remote) {
        Log.d("call disconnect reason","adh1");
    }

    /* recommended view settings - you can override it */
    @Override
    public void MesiboCall_OnUpdateUserInterface(MesiboCall.CallProperties p, int state, boolean video, boolean enable) {

        if(state == MESIBOCALL_UI_STATE_SHOWCONTROLS) {
            setCallControlsVisibility(enable, false);
            return;
        }

        boolean showIncoming = (state == MESIBOCALL_UI_STATE_SHOWINCOMING);

        ui.incomingView.setVisibility(showIncoming? View.VISIBLE: View.GONE);
        ui.inprogressView.setVisibility(showIncoming? View.GONE: View.VISIBLE);

        // audio call controls visibility
        int acVisibility = video? View.GONE: View.VISIBLE;
        // video call controls visibility
        int vcVisibility = video? View.VISIBLE: View.GONE;

        //audio controls
        ui.background.setVisibility(acVisibility);

        ////////////////
       // if(!video)
          //  ui.background.setImageBitmap(mCp.userImage);
///////////////
            //video controls
        ui.pipVideo.setVisibility(vcVisibility);
        ui.fullscreenVideo.setVisibility(vcVisibility);
        ui.cameraToggleLayout.setVisibility(vcVisibility);
        ui.cameraSwitchLayout.setVisibility(vcVisibility);
        ui.thumbnailLayout.setVisibility(vcVisibility);
        ui.incomingVideoAcceptLayout.setVisibility(vcVisibility);
    }

    @Override
    public void MesiboCall_OnOrientationChanged(boolean b, boolean b1) {

    }

    @Override
    public void MesiboCall_OnBatteryStatus(boolean b, boolean b1) {

    }

    @Override
    public void MesiboCall_OnStatus(MesiboCall.CallProperties p, int status, boolean video) {
        Log.d("call disconnect reason","adh2");
        setStatusView(status);

        if((status& Mesibo.CALLSTATUS_COMPLETE) > 0) {
            mActivity.delayedFinish(3000);
            return;
        }

        int micStatus = 0;

        switch (status) {

            case Mesibo.CALLSTATUS_CONNECTED:
                callConnected(video);
                break;

            case Mesibo.CALLSTATUS_RECONNECTING:
                setCallControlsVisibility(true, false);
                break;

            case Mesibo.CALLSTATUS_MUTE:
            case Mesibo.CALLSTATUS_UNMUTE:
                updateRemoteMuteButtons();
                break;
        }

        return;
    }

    @Override
    public void Mesibo_onConnectionStatus(int i) {
        Log.d("call disconnect reason","adh3");
    }


    public static class CallUserInterface {
        public Chronometer status = null;

        public MesiboVideoView pipVideo;
        public MesiboVideoView fullscreenVideo;

        public TextView contactView;
        public ImageButton cameraSwitchButton;
        public ImageButton toggleCameraButton;
        public ImageButton toggleMuteButton;
        public ImageButton toggleSpeakerButton;
        public ImageView remoteMic, remoteCamera;
        public ImageButton acceptButton, acceptAudioButton;
        public ImageButton declineButton;
        public ImageButton disconnectButton;
        public ImageView background;

        public View incomingView, inprogressView, controlLayout;
        public View cameraToggleLayout, cameraSwitchLayout, thumbnailLayout;
        public View incomingVideoAcceptLayout, incomingAudioAcceptLayout;

        public String mStatusText = "";

        public int buttonAlphaOff = 127;
        public int buttonAlphaMid = 200;
        public int buttonAlphaOn = 255;

    }

    protected CallUserInterface ui = new CallUserInterface();

    protected void setButtonAlpha(ImageButton v, boolean enable) {
        v.setAlpha((float)(enable?ui.buttonAlphaOn:ui.buttonAlphaOff)/255.0f);
        return;
    }

    @Override
    public void MesiboCall_OnSetCall(MesiboCallActivity activity, MesiboCall.Call call) {
        mActivity = activity;
        mCall = call;
        mCp = mCall.getCallProperties();
        mCp.activity = activity;
    }

    @Override
    public void MesiboCall_OnMute(MesiboCall.CallProperties p, boolean audioMuted, boolean videoMuted, boolean remote) {

    }

    @Override
    public boolean MesiboCall_OnPlayInCallSound(MesiboCall.CallProperties p, int type, boolean play) {
        if(!play) {
            mCall.stopInCallSound();
            return true;
        }

        mCall.playInCallSound(mActivity.getApplicationContext(), (MESIBOCALL_SOUND_RINGING == type)?R.raw.mesibo_ring :R.raw.mesibo_busy, true);
        return true;
    }

    @Override
    public void MesiboCall_OnHangup(MesiboCall.CallProperties p, int reason) {
        Log.d("call disconnect reason","adh");
        if(counter>0)
        callApiforCallDisconnect();

    }

    private void callApiforCallDisconnect() {
     //   int min=(counter / 1000) / 60;
       // int remainsec=(counter / 1000) % 60;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String end_Time_str = sdf.format(new Date());
        CallTimeReaminingRequest callTimeReaminingRequest = new CallTimeReaminingRequest();
        callTimeReaminingRequest.setBookedTime(User.BOOKEDID);
        callTimeReaminingRequest.setConferenceFinishTime(end_Time_str);
        ReamningTimeService reamningTimeService = ServiceBuilder.buildService(ReamningTimeService.class);
        Call<CallRemainingTimeResponse> createRequest = reamningTimeService.login(callTimeReaminingRequest);
        createRequest.enqueue(new Callback<CallRemainingTimeResponse>() {
            @Override
            public void onResponse(Call<CallRemainingTimeResponse> call, Response<CallRemainingTimeResponse> response) {
                Log.d("time","Update");
            }

            @Override
            public void onFailure(Call<CallRemainingTimeResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void MesiboCall_OnAudioDeviceChanged(MesiboCall.CallProperties p, MesiboCall.AudioDevice active, MesiboCall.AudioDevice inactive) {
        setButtonAlpha(ui.toggleSpeakerButton, active == MesiboCall.AudioDevice.SPEAKER);
    }

    @Override
    public void MesiboCall_OnVideoSourceChanged(int i, int i1) {

    }

    @Override
    public void MesiboCall_OnDTMF(MesiboCall.CallProperties p, int digit) {

    }

    public String statusToString(int status, String statusText) {

        switch (status) {
            case Mesibo.CALLSTATUS_NONE:
                statusText = "Initiating Call";
                if(mCall.isIncoming())
                    statusText = "Incoming Call";
                break;

            case Mesibo.CALLSTATUS_INPROGRESS:
                statusText = "Calling";
                break;

            case Mesibo.CALLSTATUS_RINGING:
                statusText = "Ringing";
                break;

            case Mesibo.CALLSTATUS_BUSY:
                statusText = "Busy";
                break;

            case Mesibo.CALLSTATUS_NOANSWER:
                statusText = "No Answer";
                break;

            case Mesibo.CALLSTATUS_NOCALLS:
                statusText = "Calls Not Supported";
                break;

            case Mesibo.CALLSTATUS_NETWORKERROR:
                statusText = "Network Error";
                break;

            case Mesibo.CALLSTATUS_UNREACHABLE:
                statusText = "Not Reachable";
                break;

            case Mesibo.CALLSTATUS_INVALIDDEST:
                statusText = "Invalid Destination";
                break;

            case Mesibo.CALLSTATUS_COMPLETE:
                statusText = "Call Completed";
                break;

            case Mesibo.CALLSTATUS_NOTALLOWED:
                statusText = "Not Allowed";
                break;

            case Mesibo.CALLSTATUS_RECONNECTING:
                statusText = "Reconnecting";
                break;

            case Mesibo.CALLSTATUS_HOLD:
                if(mCall.isAnswered()) {
                    statusText = "On Hold";
                }
                break;


            case Mesibo.CALLSTATUS_ANSWER:
                if(!mCall.isLinkUp()) {
                    statusText = "Connecting";
                }
                break;
        }

        return statusText;
    }

    private boolean callControlFragmentVisible = true;
    protected long autoHideVideoControlsTimeout = 7000;
    // Helper functions.
    protected void toggleCallControlsVisibility() {
        if (!mCall.isAnswered()) {
            return;
        }

        setCallControlsVisibility(!callControlFragmentVisible, true);
    }


    protected void setCallControlsVisibility(boolean visibility, boolean autoHide) {
        if(!mCall.isVideoCall()) return;

        try {
            // Show/hide call control fragment
            callControlFragmentVisible = visibility;
            ui.controlLayout.setVisibility(visibility? View.VISIBLE: View.GONE);

            if (autoHide && visibility && autoHideVideoControlsTimeout > 0)
                triggerDelayedAutoHideControls();
        } catch (Exception e) {

        }
    }

    private Thread mControlHidingThread = null;
    protected void triggerDelayedAutoHideControls() {
        if(null != mControlHidingThread) {
            mControlHidingThread.interrupt();
        }

        mControlHidingThread = new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(autoHideVideoControlsTimeout);

                // we set interrupted status from onDestroy
                if(Thread.currentThread().isInterrupted())
                    return;

                // TBD. crashing here sometime after cellular call received while other call in prrogress
                // and we come back here
                setCallControlsVisibility(false, false);
            }
        });

        mControlHidingThread.start();
    }
}
