package local.impactlife.cubetalk.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.mesibo.messaging.MesiboUI;

import local.impactlife.cubetalk.R;
import local.impactlife.cubetalk.mesibo.MainApplication;
import local.impactlife.cubetalk.models.User;
import local.impactlife.cubetalk.utils.Utils;

public class SplashActivity extends AppCompatActivity {

    private Handler mHandler;
    private Runnable mRunnable;
    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mSharedPreferences = getSharedPreferences(User.PREFERENCE_NAME, MODE_PRIVATE);
        mHandler = new Handler();

        mRunnable = () -> {
            if(!Utils.IsFromChat) {
                Intent intent = new Intent();
                intent.setClass(SplashActivity.this, mSharedPreferences.contains(User.TOKEN) ? HomeActivity.class :
                        IntroScreenActivity.class);
                startActivity(intent);
                finish();
            }else{
                Utils.IsFromChat=false;
                MesiboUI.launchMessageView(this,Utils.MesiboAddress, 0);
                finish();
            }
        };
        mHandler.postDelayed(mRunnable, 3000);
    }

    @Override
    protected void onDestroy() {
        mHandler.removeCallbacks(mRunnable);
        super.onDestroy();
    }
}
