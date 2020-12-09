package local.impactlife.cubetalk;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mesibo.api.Mesibo;
import com.mesibo.calls.MesiboCall;
import com.mesibo.calls.MesiboVideoCallFragment;

import org.json.JSONException;
import org.json.JSONObject;

import static org.webrtc.ContextUtils.getApplicationContext;

public class VideoCallFragment extends MesiboVideoCallFragment implements Mesibo.CallListener, View.OnTouchListener,
        Mesibo.ConnectionListener {
    Mesibo.UserProfile userProfile;
    OnCallEvents callEvents;
    Activity mainActivity;
    TextView call_Tatus,call_Time,call_name;
    LinearLayout llcalview,llcalv;
    RelativeLayout rl_disconnect;
    ImageButton mretect,mAccept;
    boolean iscall=false;
    ImageButton call_disconnect_dial;
    boolean isTimer=false;

    public VideoCallFragment(Activity mainActivity, int i) {
        this.mainActivity=mainActivity;
        Mesibo.addListener(this);
        Mesibo.setSecureConnection(true);
        // Mesibo.setAccessToken("a16c6806b24340f1c4afdab2f66a2b96ea38e2201fd2e99154134"
       // MesiboCall.getInstance().init(getApplicationContext());
        //MesiboCall.getInstance().setListener((MesiboCall.MesiboCallListener) mainActivity);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View controlView = inflater.inflate(R.layout.fragment_videocall_custom, container, false);


        ImageButton toggleSpeakerButton=controlView.findViewById(R.id.toggleSpeakerButton);
        ImageButton button_call_toggle_mic=controlView.findViewById(R.id.button_call_toggle_mic);
        ImageButton button_call_toggle_camera=controlView.findViewById(R.id.button_call_toggle_camera);

         call_disconnect_dial=controlView.findViewById(R.id.call_disconnect_dial);
      //  Button mDeclineViewButton=controlView.findViewById(R.id.mDeclineViewButton);
      //  Button mAcceptViewButton=controlView.findViewById(R.id.mAcceptViewButton);
        call_Tatus=controlView.findViewById(R.id.call_status);
        call_Time=controlView.findViewById(R.id.call_Time);
        llcalview=controlView.findViewById(R.id.llcalview);
        mAccept=controlView.findViewById(R.id.mAccept);
        mretect=controlView.findViewById(R.id.mretect);
        call_name=controlView.findViewById(R.id.call_name);
        llcalv=controlView.findViewById(R.id.llcalv);
        rl_disconnect=controlView.findViewById(R.id.rl_disconnect);
        call_name.setText(userProfile.name);
        //Toast.makeText(getActivity(),"video call started"+ userProfile.address , Toast.LENGTH_LONG).show();
        toggleSpeakerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleSpeaker();
                boolean enabled = callEvents.onToggleSpeaker();
                toggleSpeakerButton.setAlpha(enabled ? 1.0f : 0.3f);
                callEvents.onToggleSpeaker();
            }
        });
        button_call_toggle_mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleMic();
                boolean enabled = callEvents.onToggleMic();
                button_call_toggle_mic.setAlpha(enabled ? 1.0f : 0.3f);
                callEvents.onToggleMic();
            }
        });
        button_call_toggle_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchCamera();
            }
        });
        call_disconnect_dial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hangup();
            }
        });
        /*disconnectButton.setOnClickListener(new View.OnClickListener() {
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
        })*/;

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

      /*
        toggleCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleCamera();
                boolean enabled = callEvents.onToggleCamera();
                //setButton(toggleCameraButton, enabled);
                toggleCameraButton.setAlpha(enabled ? 1.0f : 0.3f);
                callEvents.onToggleCamera();
            }
        });*/

       /* mDeclineViewButton.setOnClickListener(new View.OnClickListener() {
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
        });*/

        mretect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hangup();
            }
        });

        mAccept.setOnClickListener(new View.OnClickListener() {
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
        Toast.makeText(getApplicationContext(),"video call started",Toast.LENGTH_LONG).show();
        Log.d("call","mesibocall1"+userProfile.address);
        call_name.setText(userProfile.name);
        /*if (userProfile.name.equals("")){
            call_disconnect_dial.setVisibility(View.VISIBLE);
            llcalview.setVisibility(View.INVISIBLE);
          }else {
            llcalview.setVisibility(View.VISIBLE);
            call_disconnect_dial.setVisibility(View.INVISIBLE);
        }*/
        return true;
    }

    @Override
    public boolean Mesibo_onCallStatus(long peerid, long callid, int status, int flags, String s) {
        Log.d("call","mesibocall2"+s);
       // Toast.makeText(mainActivity,"video call connected"+s,Toast.LENGTH_LONG).show();
        /*if (status==0 || status==2 || status==68) {
            call_disconnect_dial.setVisibility(View.VISIBLE);
            llcalview.setVisibility(View.INVISIBLE);
        }
        else {
            llcalview.setVisibility(View.VISIBLE);
            call_disconnect_dial.setVisibility(View.INVISIBLE);
        }*/
        if (s!=null) {
            rl_disconnect.setVisibility(View.VISIBLE);
            llcalview.setVisibility(View.GONE);
            llcalv.setVisibility(View.VISIBLE);
            call_Time.setVisibility(View.VISIBLE);
            try {
                final JSONObject obj = new JSONObject(s);
                 //call_Tatus.setText(obj.getString("type"));
                if (!isTimer) {
                    new CountDownTimer(30000, 1000) {

                        public void onTick(long millisUntilFinished) {
                            isTimer = true;
                            call_Time.setText("seconds remaining: " + millisUntilFinished / 1000);
                        }

                        public void onFinish() {
                            call_Time.setText("Call ended");
                            call_Tatus.setText("Call ended");
                            isTimer = true;
                            hangup();
                            Toast.makeText(mainActivity, "Video call Ended", Toast.LENGTH_LONG).show();
                        }
                    }.start();
                }

            } catch (JSONException e) {
                e.printStackTrace();
               // call_Tatus.setText("calling");
            }

           // Toast.makeText(mainActivity,"video call connected sucess"+s,Toast.LENGTH_LONG).show();
         /*   LayoutInflater inflater = getLayoutInflater();
            View dialoglayout = inflater.inflate(R.layout.activity_call_logs, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
            builder.setView(dialoglayout);
            builder.show();*/
        }else{
           // llcalview.setVisibility(View.VISIBLE);
          //  llcalv.setVisibility(View.INVISIBLE);
        }
        return true;
    }

    @Override
    public void Mesibo_onCallServer(int i, String s, String s1, String s2) {
        Log.d("call","mesibocall3"+s);
    }

    public void setUser(Mesibo.UserProfile userProfiles) {
        this.userProfile=userProfiles;
        iscall=true;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void Mesibo_onConnectionStatus(int i) {

    }
}
