package local.impactlife.cubetalk.mesibo;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import androidx.core.app.NotificationCompat;
import androidx.core.view.MenuItemCompat;

import com.google.gson.Gson;
import com.mesibo.api.Mesibo;
import com.mesibo.calls.MesiboCall;

import java.util.ArrayList;

import local.impactlife.cubetalk.R;
import local.impactlife.cubetalk.activities.SplashActivity;
import mesibos.MesiboRegistrationIntentService;

/**
 * Mesibo listener
 */

public class MesiboListener implements Mesibo.UIHelperListner, Mesibo.MessageFilter,Mesibo.MessageListener , MesiboRegistrationIntentService.GCMListener{

    private static Gson mGson = new Gson();

    @Override
    public boolean Mesibo_onMessage(Mesibo.MessageParams messageParams, byte[] bytes) {
      //  SampleAppWebApi.autoAddContact(messageParams);
        //if(Mesibo.isReading(messageParams))
        //  return true;

        String message = "";
        try {
            message = new String(bytes, "UTF-8");
        } catch (Exception e) {
            return false;
        }
       SampleAppWebApi.notify(messageParams, message);

       /* NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(MainApplication.getContext().getApplicationContext(), "notify_001");
        Intent ii = new Intent(MainApplication.getContext().getApplicationContext(), SplashActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(MainApplication.getContext(), 0, ii, 0);

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText(message);
        bigText.setBigContentTitle("Title");
        bigText.setSummaryText("Text in detail");

        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher_round);
        mBuilder.setContentTitle("Your Title");
        mBuilder.setContentText("Your text");
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setStyle(bigText);

        NotificationManager mNotificationManager =
                (NotificationManager) MainApplication.getContext().getSystemService(Context.NOTIFICATION_SERVICE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("notify_001",
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            mNotificationManager.createNotificationChannel(channel);
        }

        mNotificationManager.notify(0, mBuilder.build());*/
        return true;
    }

    @Override
    public void Mesibo_onMessageStatus(Mesibo.MessageParams messageParams) {

    }

    @Override
    public void Mesibo_onActivity(Mesibo.MessageParams messageParams, int i) {

    }

    @Override
    public void Mesibo_onLocation(Mesibo.MessageParams messageParams, Mesibo.Location location) {

    }

    @Override
    public void Mesibo_onFile(Mesibo.MessageParams messageParams, Mesibo.FileInfo fileInfo) {

    }


    public static class MesiboNotification {
        public String subject;
        public String msg;
        public String type;
        public String action;
        public String name;
        public long gid;
        public String phone;
        public String status;
        public String members;
        public String photo;
        public long ts;
        public String tn;

        MesiboNotification() {
        }
    }

    /** Message filtering
     * This function is called every time a message received. This function should return true if
     * message to be acceped or false to drop it
     *
     * In this example, we are using it to get contact and real-time notifications from the server (refer
     * php example). PHP sanmple code sends a special message with type '1' when new contact signs up
     * or need to send any push notification. All such messages are processed and filtered here.
     */

    @Override
    public boolean Mesibo_onMessageFilter(Mesibo.MessageParams messageParams, int i, byte[] data) {
        // using it for notifications
        if(1 != messageParams.type)
            return true;

        String message = "";
        try {
            message = new String(data, "UTF-8");
        } catch (Exception e) {
            return false;
        }

        if(TextUtils.isEmpty(message))
            return false;

        MesiboNotification n = null;

        try {
            n = mGson.fromJson(message, MesiboNotification.class);
        } catch (Exception e) {
            return false;
        }

        if(null == n)
            return false;

        SampleAppWebApi.addContact(n.name, n.phone);
        return false;
    }

    /* [OPTIONAL] implement these methods if Mesibo UI is used
        * Refer to API documentation for more details
        * */
    @Override
    public void Mesibo_onForeground(Context context, int i, boolean b) {

    }

    @Override
    public void Mesibo_onShowProfile(Context context, Mesibo.UserProfile userProfile) {

    }

    @Override
    public void Mesibo_onDeleteProfile(Context context, Mesibo.UserProfile userProfile, Handler handler) {

    }

    @Override
    public int Mesibo_onGetMenuResourceId(Context context, int type, Mesibo.MessageParams params, Menu menu) {
        int id = 0;
        if (type == 0) // Setting menu in userlist
            id = R.menu.mesiboui_contacts_menu;
        else // from User messages
            id = R.menu.mesiboui_messages_menu;

        ((Activity)context).getMenuInflater().inflate(id, menu);

        /* Hide call icons for groups */
        if(1 == type && null != params && params.groupid > 0) {
            MenuItem menuItem = menu.findItem(R.id.action_call);
            menuItem.setVisible(false);
            MenuItemCompat.setShowAsAction(menuItem, MenuItemCompat.SHOW_AS_ACTION_NEVER);

            menuItem = menu.findItem(R.id.action_videocall);
            menuItem.setVisible(false);
            MenuItemCompat.setShowAsAction(menuItem, MenuItemCompat.SHOW_AS_ACTION_NEVER);
        }

        return 0;
    }

    /** Mesibo UI calls this function to get menu buttons to be shown on corresponding screens
     * */
    @Override
    public boolean Mesibo_onMenuItemSelected(Context context, int type, Mesibo.MessageParams params, int item) {
        if (type == 0) { // from userlist
            if (item == R.id.action_settings) {
                /* Launch settings */
            }
        } else { // from messaging box
            if(R.id.action_call == item && 0 == params.groupid) {
                //UIManager.launchCallActivity(MainApplication.getAppContext(), params.peer, true);
                MesiboCall.getInstance().call(context, Mesibo.random(), params.profile, false);
            }
            else if(R.id.action_videocall == item && 0 == params.groupid) {
                //UIManager.launchCallActivity(MainApplication.getAppContext(), params.peer, true);
                MesiboCall.getInstance().call(context, Mesibo.random(), params.profile, true);
            }
        }
        return false;
    }

    @Override
    public void Mesibo_onSetGroup(Context context, long l, String s, int i, String s1, String s2, String[] strings, Handler handler) {

    }

    @Override
    public void Mesibo_onGetGroup(Context context, long l, Handler handler) {

    }

    @Override
    public ArrayList<Mesibo.UserProfile> Mesibo_onGetGroupMembers(Context context, long l) {
        return null;
    }
    @Override
    public void Mesibo_onGCMToken(String token) {
        SampleAppWebApi.setGCMToken(token);
    }

    @Override
    public void Mesibo_onGCMMessage(/*Bundle data,*/ boolean inService) {
        SampleAppWebApi.onGCMMessage(inService);
    }
}
