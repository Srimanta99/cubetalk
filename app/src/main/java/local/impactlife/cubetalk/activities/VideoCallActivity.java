package local.impactlife.cubetalk.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.mesibo.api.Mesibo;
//import com.mesibo.calls.MesiboAudioCallFragment;
import com.mesibo.calls.MesiboCall;
//import com.mesibo.calls.MesiboVideoCallFragment;

import local.impactlife.cubetalk.R;


public class VideoCallActivity extends AppCompatActivity implements
        Mesibo.ConnectionListener   {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_view_profile);
       // Mesibo.setAccessToken("c224ebddcac22200c4592d43db270a7d0abe66c8775ee6615405b");
        Mesibo.setAccessToken("2e7aeb174f174bfe04344323f31ecb18707a1132f281bfebb15436d");
        Mesibo.start();
        MesiboCall masibocall = MesiboCall.getInstance();
        masibocall.init(this);
       // masibocall.setListener(this);
        Mesibo.UserProfile userProfile = new Mesibo.UserProfile();
      //  userProfile.name = "232284";
        userProfile.address = "u4";
        MesiboCall.getInstance().call(VideoCallActivity.this, Mesibo.random(), userProfile, true);

        // Mesibo.addListener(this);
        // set user authentication token obtained by creating user

    }

    @Override
    public void Mesibo_onConnectionStatus(int i) {

    }

/*
    @Override
    public boolean MesiboCall_onNotify(int i, Mesibo.UserProfile userProfile, boolean b) {
        return false;
    }

    @Override
    public MesiboVideoCallFragment MesiboCall_getVideoCallFragment(Mesibo.UserProfile userProfile) {
        MesiboVideoCallFragment videoCallFragment = new MesiboVideoCallFragment() ;

        return null;
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
