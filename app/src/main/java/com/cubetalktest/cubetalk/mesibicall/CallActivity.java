package com.cubetalktest.cubetalk.mesibicall;

import android.content.pm.PackageManager;
import android.os.Bundle;


import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.cubetalktest.cubetalk.R;
import com.mesibo.api.Mesibo;
import com.mesibo.calls.api.MesiboCall;
import com.mesibo.calls.api.MesiboCallActivity;


public class CallActivity extends MesiboCallActivity {
  /* private boolean mInit = false;
    MesiboCall.Call mCall = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        int res = checkPermissions(mVideo);

        if(res < 0) {
            finish();
            return;
        }

        if(0 == res) {
            initCall();
        } else {

            return;
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        // If request is cancelled, the result arrays are empty.
        if (grantResults != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            initCall();
        }
        else
            finish();
    }

    private void initCall() {
        if(mInit) return;
        mInit = true;

        mCall = MesiboCall.getInstance().getActiveCall();

        if(null == mCall) {

            Mesibo.UserProfile profile = Mesibo.getUserProfile(mAddress);
            if(null == profile) {
                profile = new Mesibo.UserProfile();
                profile.address = mAddress;
                profile.name = mAddress;
            }

            MesiboCall.CallProperties cc = MesiboCall.getInstance().new CallProperties(mVideo);
            cc.parent = this;
            cc.activity = this;
            cc.user = profile;

            ///////
           // cc.video.screenCapture = mScreenCapture;
            //////

            mCall = MesiboCall.getInstance().call(cc);

            if(null == mCall || !mCall.isCallInProgress()) {
                finish();
                return;
            }
        }

        super.initCall(mCall);

        CallFragment fragment = null;
        fragment = new CallFragment();


        if(mVideo) {
            // show video fragment
        } else if(mIncoming && mCall.isCallInProgress() && !mCall.isAnswered()) {
            //show incoming audio fragment
        } else {
            //show outgoing audio fragemnt
        }

        fragment.MesiboCall_OnSetCall(this, mCall);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.top_fragment_container, fragment);
        ft.commitAllowingStateLoss();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!mInit)
            return;

        mCall = MesiboCall.getInstance().getActiveCall();
        if(null == mCall) {
            finish();
            return;
        }
    }
*/
  private boolean mInit = false;
    MesiboCall.Call mCall = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        int res = checkPermissions(mCp.video.enabled);

        if(res < 0) {
            finish();
            return;
        }


        if(0 == res) {
            initCall();
        } else {

            return;
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        // If request is cancelled, the result arrays are empty.
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            initCall();
        }
        else
            finish();
    }

    private void initCall() {
        if(mInit) return;
        mInit = true;

        mCall = MesiboCall.getInstance().getActiveCall();

        if(null == mCall) {

            if(null == mCp.parent)
                mCp.parent = this;
            mCp.activity = this;
            mCall = MesiboCall.getInstance().call(mCp);

            if(null == mCall || !mCall.isCallInProgress()) {
                finish();
                return;
            }
        }

        super.initCall(mCall);

        CallFragment fragment = null;
        fragment = new CallFragment();

        fragment.MesiboCall_OnSetCall(this, mCall);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.top_fragment_container, fragment);
        ft.commitAllowingStateLoss();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!mInit)
            return;

        mCall = MesiboCall.getInstance().getActiveCall();
        if(null == mCall) {
            finish();
            return;
        }
    }

}
