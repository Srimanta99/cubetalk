package com.cubetalktest.cubetalk.mesibo;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.mesibo.api.Mesibo;
import com.mesibo.calls.api.MesiboCall;


import com.cubetalktest.cubetalk.mesibicall.CallActivity;
import com.cubetalktest.cubetalk.models.User;


/**
 * Here is basic mesibo initialization. Depending on application need, you can do it at other places
 * too. However, Application is an ideal place for one time initialization
 */

public class MainApplication extends Application implements  MesiboCall.IncomingListener,Mesibo.CallListener {
    public static final String TAG = "MesiboDemoApplication";
    private static Context mContext = null;
    private static MesiboCall mCall = null;
    private static Mesibo mesibo = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        /** Mesibo one-time initialization */
        mesiboInit();
        MesiboCall.getInstance().init(mContext);
        /** [OPTIONAL] Customize look and feel of Mesibo UI */
      //  MesiboUI.Config opt = MesiboUI.getConfig();
       // opt.mToolbarColor = 0xff00868b;
       // opt.emptyUserListMessage = "Ask your family and friends to download so that you can try out Mesibo functionalities";
       // MediaPicker.setToolbarColor(opt.mToolbarColor);
    }

    private void mesiboInit() {
        //Mesibo.start();
        mesibo = Mesibo.getInstance();

        mesibo.init(getApplicationContext());
        SharedPreferences mSharedPreferences = getSharedPreferences(User.PREFERENCE_NAME, MODE_PRIVATE);
        if (mSharedPreferences.contains(User.TOKEN)) {
            MainApplication.startMesibo(mSharedPreferences.getString(User.MESIBO_TOKEN, ""));
        }
        /** [OPTIONAL] Initializa calls if used  */
       // mCall = MesiboCall.getInstance();
       // mCall.init(this);


        /** [Optional] add listener for file transfer handler
         * you only need if you plan to send and receive files using mesibo
         * */
        //MesiboFileTransferHelper fileTransferHelper = new MesiboFileTransferHelper();
       // Mesibo.addListener(fileTransferHelper);

        /** add other listener - you can add any number of listeners */
        //Mesibo.addListener(new MesiboListener());

        /** [Optional] enable to disable secure connection */
        Mesibo.setSecureConnection(true);
                //   startMesibo("2e7aeb174f174bfe04344323f31ecb18707a1132f281bfebb15436d");

        /** Initialize web api to communicate with your own backend servers */
                  //   SampleAppWebApi.init();
        MesiboCall.getInstance().init(mContext);
        // MesiboCall.getInstance().setListener(this);
        Mesibo api = Mesibo.getInstance();
        api.init(getApplicationContext());
        Mesibo.start();
       // Mesibo.addListener(this);
    }

    /**
     * Start mesibo only after you have a user access token
     * @param s
     */
    public static void startMesibo(String s) {
        /** set user access token  */

        //SampleAppWebApi.init(s);
        MesiboCall.getInstance().init(mContext);
        // MesiboCall.getInstance().setListener(this);
        Mesibo.setAccessToken(s);
        Mesibo api = Mesibo.getInstance();
        api.init(mContext);
        Mesibo.start();

      //  MesiboCall.getInstance().init(mContext);
      // Mesibo.setAccessToken("a16c6806b24340f1c4afdab2f66a2b96ea38e2201fd2e99154134");

        /** [OPTIONAL] set up database to save and restore messages
         * Note: if you call this API before setting an access token, mesibo will
         * create a database exactly as you named it. However, if you call it
         * after setting Access Token like in this example, it will be uniquely
         * named for each user [Preferred].
         * */
       // Mesibo.setDatabase("myAppDb.db", 0);

        /** start mesibo */
       // Mesibo.start();
      //  MesiboCall masibocall = MesiboCall.getInstance();
      //  masibocall.init(getContext());

    }

    public static Context getContext() {
        return mContext;
    }

    @Override
    public MesiboCall.CallProperties MesiboCall_OnIncoming(Mesibo.UserProfile userProfile, boolean video) {
        MesiboCall.CallProperties cc = MesiboCall.getInstance().createCallProperties(video);
        cc.parent = getApplicationContext();
        cc.user = userProfile;
        cc.className = CallActivity.class;
        return cc;
    }

    @Override
    public boolean MesiboCall_OnShowUserInterface(MesiboCall.Call call, MesiboCall.CallProperties cp) {
        launchCustomCallActivity(cp.user.address, cp.video.enabled, true);
        return true;
    }

    @Override
    public void MesiboCall_OnError(MesiboCall.CallProperties callProperties, int i) {

    }

    @Override
    public boolean MesiboCall_onNotify(int i, Mesibo.UserProfile userProfile, boolean b) {
        return false;
    }

    protected void launchCustomCallActivity(String destination, boolean video, boolean incoming) {
        Intent intent = new Intent(this, CallActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.putExtra("video", video);
        intent.putExtra("address", destination);
        intent.putExtra("incoming", incoming);
        startActivity(intent);
    }

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
}
