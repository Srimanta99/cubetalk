package local.impactlife.cubetalk.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mesibo.api.Mesibo;
import com.mesibo.calls.MesiboVideoCallFragment;

import local.impactlife.cubetalk.R;

public class VideoCallFragment extends MesiboVideoCallFragment implements Mesibo.CallListener, View.OnTouchListener {
    Mesibo.UserProfile userProfile;
    OnCallEvents callEvents;
    Activity mainActivity;

    public VideoCallFragment(Activity mainActivity, int i) {
        this.mainActivity=mainActivity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View controlView = inflater.inflate(R.layout.fragment_videocall_customwindow, container, false);
        Button disconnectButton=controlView.findViewById(R.id.disconnectButton);
        Button cameraSwitchButton=controlView.findViewById(R.id.cameraSwitchButton);
        Button toggleSpeakerButton=controlView.findViewById(R.id.toggleSpeakerButton);
        Button toggleMuteButton=controlView.findViewById(R.id.toggleMuteButton);
        Button toggleCameraButton=controlView.findViewById(R.id.toggleCameraButton);
        Button mDeclineViewButton=controlView.findViewById(R.id.mDeclineViewButton);
        Button mAcceptViewButton=controlView.findViewById(R.id.mAcceptViewButton);
       // userProfile.picturePath
        Toast.makeText(getActivity(),"video call started"+ userProfile.address , Toast.LENGTH_LONG).show();
        disconnectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hangup();

            }
        });
        cameraSwitchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchCamera();
            }
        });

       /*  videoScalingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (scalingType == RendererCommon.ScalingType.SCALE_ASPECT_FILL) {
                    videoScalingButton.setBackgroundResource(R.drawable.ic_fullscreen_white_48dp);
                    scalingType = ScalingType.SCALE_ASPECT_FIT;
                } else {
                    videoScalingButton.setBackgroundResource(R.drawable.ic_fullscreen_exit_white_48dp);
                    scalingType = ScalingType.SCALE_ASPECT_FILL;
                }
                ///callEvents.onVideoScalingSwitch(scalingType);
                scaleVideo(true);
            }
        });*/

       toggleSpeakerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleSpeaker();
                boolean enabled = callEvents.onToggleSpeaker();
                toggleSpeakerButton.setAlpha(enabled ? 1.0f : 0.3f);
                callEvents.onToggleSpeaker();

            }
        });

        toggleMuteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleMic();
                boolean enabled = callEvents.onToggleMic();
                toggleMuteButton.setAlpha(enabled ? 1.0f : 0.3f);
                callEvents.onToggleMic();

            }
        });

        toggleCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleCamera();
                boolean enabled = callEvents.onToggleCamera();
                //setButton(toggleCameraButton, enabled);
                toggleCameraButton.setAlpha(enabled ? 1.0f : 0.3f);
                callEvents.onToggleCamera();
            }
        });

        mDeclineViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hangup();
            }
        });

        mAcceptViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer(true);
            }
        });


        return controlView;
    }
    @Override
    public void onResume() {
        super.onResume();
        Mesibo.addListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        Mesibo.removeListener(this);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        callEvents = (OnCallEvents) activity;
    }

    @Override
    public boolean Mesibo_onCall(long l, long l1, Mesibo.UserProfile userProfile, int i) {
        //Toast.makeText(this,"video call started",Toast.LENGTH_LONG).show();
        Log.d("call","mesibocall"+userProfile.name);
        return false;
    }

    @Override
    public boolean Mesibo_onCallStatus(long l, long l1, int i, int i1, String s) {
        return false;
    }

    @Override
    public void Mesibo_onCallServer(int i, String s, String s1, String s2) {

    }

    public void setUser(Mesibo.UserProfile userProfiles) {
        this.userProfile=userProfiles;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }
}
