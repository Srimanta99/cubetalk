package com.cubetalktest.cubetalk.mesibo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.mesibo.api.Mesibo;


import com.cubetalktest.cubetalk.mesibos.AppConfig;
import com.cubetalktest.cubetalk.mesibos.NotifyUser;

/**
 * Web API to communicate with your own backend server(s).
 * Note - in this example, we are not authenticating. In real app, you should authenticate user first
 * using email or phone OTP.
 *
 * When user is successfully authenticated, your server should create a mesibo auth token using
 * mesibo server side api and send it back here.
 *
 * Refer to PHP server api for code sample.
 */

public class SampleAppWebApi {
    public static final String TAG="SampleAppWebApi";
    private static SharedPreferences mSharedPref = null;
    private static boolean mSyncPending = true;
    public static final String mSharedPrefKey = "com.mesibo.samplecodeapp";
    private static Gson mGson = new Gson();
    private static String mToken = null;
    private static String mGCMToken = null;
    private static boolean mGCMTokenSent = false;
    public final static String KEY_GCMTOKEN = "gcmtoken";
    private static String FCM_SENDER_ID="330678286265";
    private static Context mContext = null;
    private static String mApiUrl = "https://app.mesibo.com/api.php";
    private static NotifyUser mNotifyUser = null;
    public final static String KEY_SYNCEDCONTACTS = "syncedContacts";
    public final static String KEY_SYNCEDDEVICECONTACTSTIME = "syncedPhoneContactTs";
    public final static String KEY_SYNCEDCONTACTSTIME = "syncedTs";
    public final static String KEY_AUTODOWNLOAD = "autodownload";
  //  public final static String KEY_GCMTOKEN = "gcmtoken";

    public static final int VISIBILITY_HIDE = 0;
    public static final int VISIBILITY_VISIBLE = 1;
    public static final int VISIBILITY_UNCHANGED = 2;
    public static abstract class ResponseHandler implements Mesibo.HttpListener {
        private Mesibo.Http http = null;
        private Bundle mRequest = null;
        private boolean mBlocking = false;
        private boolean mOnUiThread = false;
        public static boolean result = true;
        public Context mContext = null;

        @Override
        public boolean Mesibo_onHttpProgress(Mesibo.Http http, int state, int percent) {
            if(percent < 0) {
                HandleAPIResponse(null);
                return true;
            }

            if(100 == percent && Mesibo.Http.STATE_DOWNLOAD == state) {
                String strResponse = http.getDataString();
                Response response = null;

                if (null != strResponse) {
                    try {
                        response = mGson.fromJson(strResponse, Response.class);
                    } catch (Exception e) {
                        result = false;
                    }
                }

                if(null == response)
                    result = false;

                final Context context = (null == this.mContext)?SampleAppWebApi.mContext:this.mContext;

                if(!mOnUiThread) {
                    parseResponse(response, mRequest, context, false);
                    HandleAPIResponse(response);
                }
                else {
                    final Response r = response;

                    if(null == context)
                        return true;

                    Handler uiHandler = new Handler(context.getMainLooper());

                    Runnable myRunnable = new Runnable() {
                        @Override
                        public void run() {
                            parseResponse(r, mRequest, context, true);
                            HandleAPIResponse(r);
                        }
                    };
                    uiHandler.post(myRunnable);
                }
            }
            return true;
        }

        public void setBlocking(boolean blocking) {
            mBlocking = blocking;
        }

        public void setOnUiThread(boolean onUiThread) {
            mOnUiThread = onUiThread;
        }

        public boolean sendRequest(Bundle postBunlde, String filePath, String formFieldName) {

            postBunlde.putString("dt", String.valueOf(Mesibo.getDeviceType()));
            int nwtype = Mesibo.getNetworkConnectivity();
            if(nwtype == 0xFF) {

            }

            mRequest = postBunlde;
            http = new Mesibo.Http();
            http.url = mApiUrl;
            http.postBundle = postBunlde;
            http.uploadFile = filePath;
            http.uploadFileField = formFieldName;
            http.notifyOnCompleteOnly = true;
            http.concatData = true;
            http.listener = this;
            if(mBlocking)
                return http.executeAndWait();
            return http.execute();
        }

        public void setContext(Context context) {
            this.mContext = context;
        }

        public Context getContext() {
            return this.mContext;
        }

        public abstract void HandleAPIResponse(Response response);
    }

    public static void setGCMToken(String token) {
        mGCMToken = token;
        sendGCMToken();
    }

    private static void sendGCMToken() {
        if(null == mGCMToken || mGCMTokenSent) {
            return;
        }

        synchronized (SampleAppWebApi.class) {
            if(mGCMTokenSent)
                return;
            mGCMTokenSent = true;
        }

        String gcmtoken = Mesibo.readKey(KEY_GCMTOKEN);
        if(!TextUtils.isEmpty(gcmtoken) && gcmtoken.equalsIgnoreCase(mGCMToken)) {
            mGCMTokenSent = true;
            return;
        }

        Bundle b = createPostBundle("setnotify");
        if(null == b) return;

        b.putString("notifytoken", mGCMToken);
        b.putString("fcmid", FCM_SENDER_ID);

        ResponseHandler http = new ResponseHandler() {
            @Override
            public void HandleAPIResponse(Response response) {
                if(null != response && response.result.equalsIgnoreCase("OK") ) {
                    Mesibo.setKey(KEY_GCMTOKEN, mGCMToken);
                } else
                    mGCMTokenSent = false;
            }
        };

        http.sendRequest(b, null, null);
    }
    private static Bundle createPostBundle(String op) {
        if(TextUtils.isEmpty(AppConfig.getConfig().token))
            return null;

        Bundle b = new Bundle();
        b.putString("op", op);
        b.putString("token", AppConfig.getConfig().token);
        return b;
    }

    public static void onGCMMessage(boolean inService) {
        Mesibo.setAppInForeground(null, -1, true);

        while(inService) {
            if(Mesibo.STATUS_ONLINE == Mesibo.getConnectionStatus())
                break;

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }

        // wait for messages to receive etc
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
    }
    public interface DemoWebApiResponseHandler {
        void onApiResponse(boolean result);
    }

    public static class Contacts {
        public String name = "";
        public String phone = "";
        public long   gid = 0;
    }

    public static class Response {
        public String result;
        public String op;
        public String error;
        public String token;

        public Contacts[] contacts;

        Response() {
            result = null;
            op = null;
            error = null;
            token = null;
            contacts = null;
        }
    }

    public static synchronized  void init(String smesibotoken) {
        if(null != mSharedPref) return;

        mSharedPref = MainApplication.getContext().getSharedPreferences(mSharedPrefKey, Context.MODE_PRIVATE);
        mToken = getStringValue("token", null);

        if(isLoggedin())
            MainApplication.startMesibo(smesibotoken);
    }

    public static boolean isLoggedin() {
        return !TextUtils.isEmpty(mToken);
    }

    public static String getToken() {
        return mToken;
    }

    public static void login(String name, String phone, final DemoWebApiResponseHandler handler) {

        Bundle b = new Bundle();
        b.putString("op", "login");
        b.putString("ns", SampleAppConfiguration.namespace);
        b.putString("aid", MainApplication.getContext().getPackageName());
        b.putString("name", name);
        b.putString("phone", phone);
        /* end of post data */

        Mesibo.Http http = new Mesibo.Http();

        http.url = SampleAppConfiguration.apiUrl;
        http.postBundle = b;
        http.onMainThread = true;

        http.listener = new Mesibo.HttpListener() {
            @Override
            public boolean Mesibo_onHttpProgress(Mesibo.Http config, int state, int percent) {

                if(100 == percent && Mesibo.Http.STATE_DOWNLOAD == state) {

                    //parse response
                    String respString = config.getDataString();
                    Response response = mGson.fromJson(respString, Response.class);
                    if(null == response || !response.result.equalsIgnoreCase("OK")) {
                        handler.onApiResponse(false);
                        return true;
                    }

                    setStringValue("token", response.token);
                    mToken = response.token;
                    Mesibo.reset();

                    /* start mesibo before saving contacts */
                    MainApplication.startMesibo("2e7aeb174f174bfe04344323f31ecb18707a1132f281bfebb15436d");

                    if(null != response.contacts) {
                        for(Contacts c : response.contacts) {
                            addContact(c.name, c.phone);
                        }
                    }

                    handler.onApiResponse(true);
                    return true;
                }

                if(percent < 0)
                    handler.onApiResponse(false);

                return true;
            }
        };

        http.execute();
    }

    public static void logout() {
        Mesibo.stop(false);

        Bundle b = new Bundle();
        b.putString("op", "logout");
        b.putString("token", mToken);
        /* end of post data */

        Mesibo.Http http = new Mesibo.Http();

        http.url = SampleAppConfiguration.apiUrl;
        http.postBundle = b;

        http.listener = new Mesibo.HttpListener() {
            @Override
            public boolean Mesibo_onHttpProgress(Mesibo.Http config, int state, int percent) {
                return true;
            }
        };

        http.execute();

        mToken = null;
        setStringValue("token", "");
    }

    public static void addContact(String name, String phone) {
        if(TextUtils.isEmpty(phone)) return;

        Mesibo.UserProfile profile = new Mesibo.UserProfile();
        profile.name = name;
        profile.address = phone;
        if(TextUtils.isEmpty(name))
            profile.name = phone;

        Mesibo.setUserProfile(profile, false);
    }

    public static boolean setStringValue(String key, String value) {
        try {
            synchronized (mSharedPref) {
                SharedPreferences.Editor poEditor = mSharedPref.edit();
                poEditor.putString(key, value);
                poEditor.commit();
                //backup();
                return true;
            }
        } catch (Exception e) {
            Log.d(TAG, "Unable to set long value in RMS:" + e.getMessage());
            return false;
        }
    }

    public static String getStringValue(String key, String defaultVal) {
        try {
            synchronized (mSharedPref) {
                if (mSharedPref.contains(key))
                    return mSharedPref.getString(key, defaultVal);
                return defaultVal;
            }
        } catch (Exception e) {
            Log.d(TAG, "Unable to fet long value in RMS:" + e.getMessage());
            return defaultVal;
        }
    }
    private static boolean parseResponse(Response response, Bundle request, Context context, boolean uiThread) {
        mNotifyUser = new NotifyUser(MainApplication.getContext());
        if(null == response) {
            if(request.getString("op").equalsIgnoreCase("getcontacts")) {
                mSyncPending = true;
            }

            if(uiThread && null != context) {
                showConnectionError(context);
            }
            return false;
        }

        if(!response.result.equalsIgnoreCase("OK") ) {
            if(response.error.equalsIgnoreCase("AUTHFAIL")) {
                forceLogout();
            }
            return false;
        }

        boolean save = false;
       /* if(null != response.urls) {
            AppConfig.getConfig().uploadurl = response.urls.upload;
            AppConfig.getConfig().downloadurl = response.urls.download;
            save = true;
        }*/




        return true;


    }
    public static void showConnectionError(Context context) {
        String title = "No Internet Connection";
        String message = "Your phone is not connected to the internet. Please check your internet connection and try again later.";
       // UIManager.showAlert(context, title, message);
    }
    public static void forceLogout(){
        mGCMTokenSent = false;
        Mesibo.setKey(KEY_GCMTOKEN, "");
        SampleAppWebApi.saveLocalSyncedContacts("", 0);
        SampleAppWebApi.saveSyncedTimestamp(0);
        Mesibo.stop(true);
        AppConfig.getConfig().reset();

        Mesibo.reset();
        //Mesibo.resetDB();
        //ContactUtils.syncReset();

        //UIManager.launchStartupActivity(mContext, true);
    }
    public static void saveLocalSyncedContacts(String contacts, long timestamp) {
        Mesibo.setKey(SampleAppWebApi.KEY_SYNCEDCONTACTS, contacts);
        Mesibo.setKey(SampleAppWebApi.KEY_SYNCEDDEVICECONTACTSTIME, String.valueOf(timestamp));
    }

    public static void saveSyncedTimestamp(long timestamp) {
        Mesibo.setKey(SampleAppWebApi.KEY_SYNCEDCONTACTSTIME, String.valueOf(timestamp));
    }

    public static void notify(Mesibo.MessageParams params, String message) {
        mNotifyUser = new NotifyUser(MainApplication.getContext());
        // if call is in progress, we must give notification even if reading because user is in call
        // screen
       /* if(!MesiboCall.getInstance().isCallInProgress() && Mesibo.isReading(params))
            return;
*/
        if(Mesibo.ORIGIN_REALTIME != params.origin || Mesibo.MSGSTATUS_OUTBOX == params.getStatus())
            return;

        //MUST not happen for realtime message
        if(params.groupid > 0 && null == params.groupProfile)
            return;

        Mesibo.UserProfile profile = Mesibo.getUserProfile(params);
         AppConfig.p_name=profile.name;
        // this will also mute message from user in group
        if(null != profile && profile.isMuted())
            return;

        String name = params.peer;
        if(null != profile) {
            name = profile.name;
        }

        if(params.groupid > 0) {
            Mesibo.UserProfile gp = Mesibo.getUserProfile(params.groupid);
            if(null == gp)
                return; // must not happen

            if(gp.isMuted())
                return;

            name += " @ " + gp.name;
        }

        if(params.isMissedCall()) {
            String subject = "Mesibo Missed Call";
            message = "You missed a mesibo " + (params.isVideoCall()?"video ":"") + "call from " + profile.name;
            SampleAppWebApi.notify(NotifyUser.NOTIFYCALL_CHANNEL_ID, 2, subject, message, profile.name);
            return;
        }

        // outgoing or incoming call
        if(params.isCall()) return;
      //  SampleAppWebApi.notify(NotifyUser.NOTIFYCALL_CHANNEL_ID, 2, " Cubrla", message);
            mNotifyUser.sendNotificationInList(name, message);
      //  Toast.makeText(MainApplication.getContext(),"message recived"+message,Toast.LENGTH_LONG).show();
    }
    public static void notify(String channelid, int id, String title, String message, String pname) {
        mNotifyUser.sendNotification(channelid, id, title, message);
    }
}
