package local.impactlife.cubetalk.mesibo;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.mesibo.api.Mesibo;
import com.mesibo.calls.MesiboCall;

import local.impactlife.cubetalk.models.User;


/**
 * Here is basic mesibo initialization. Depending on application need, you can do it at other places
 * too. However, Application is an ideal place for one time initialization
 */

public class MainApplication extends Application {
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

        /** [OPTIONAL] Initializa calls if used  */
        mCall = MesiboCall.getInstance();
        mCall.init(this);


        /** [Optional] add listener for file transfer handler
         * you only need if you plan to send and receive files using mesibo
         * */
        //MesiboFileTransferHelper fileTransferHelper = new MesiboFileTransferHelper();
       // Mesibo.addListener(fileTransferHelper);

        /** add other listener - you can add any number of listeners */
        Mesibo.addListener(new MesiboListener());

        /** [Optional] enable to disable secure connection */
        Mesibo.setSecureConnection(true);
                //   startMesibo("2e7aeb174f174bfe04344323f31ecb18707a1132f281bfebb15436d");
              SharedPreferences mSharedPreferences = getSharedPreferences(User.PREFERENCE_NAME, MODE_PRIVATE);
              if (mSharedPreferences.contains(User.TOKEN)) {
                MainApplication.startMesibo(mSharedPreferences.getString(User.MESIBO_TOKEN, ""));
               }
        /** Initialize web api to communicate with your own backend servers */
                  //   SampleAppWebApi.init();

    }

    /**
     * Start mesibo only after you have a user access token
     * @param s
     */
    public static void startMesibo(String s) {
        /** set user access token  */
        Mesibo.setAccessToken(s);
        SampleAppWebApi.init(s);
      // Mesibo.setAccessToken("a16c6806b24340f1c4afdab2f66a2b96ea38e2201fd2e99154134");

        /** [OPTIONAL] set up database to save and restore messages
         * Note: if you call this API before setting an access token, mesibo will
         * create a database exactly as you named it. However, if you call it
         * after setting Access Token like in this example, it will be uniquely
         * named for each user [Preferred].
         * */
       // Mesibo.setDatabase("myAppDb.db", 0);

        /** start mesibo */
        Mesibo.start();
      //  MesiboCall masibocall = MesiboCall.getInstance();
      //  masibocall.init(getContext());

    }

    public static Context getContext() {
        return mContext;
    }
}
