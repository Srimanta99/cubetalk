package com.cubetalktest.cubetalk.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;
import com.mesibo.api.Mesibo;
import com.mesibo.calls.api.MesiboCall;


import org.jetbrains.annotations.NotNull;

import com.cubetalktest.cubetalk.R;
import com.cubetalktest.cubetalk.enums.ExpertApplicationStatus;
import com.cubetalktest.cubetalk.mesibo.MainApplication;
import com.cubetalktest.cubetalk.models.User;
import com.cubetalktest.cubetalk.models.get_expert_sign_up_condition.ExpertSignUpConditionResponse;
import com.cubetalktest.cubetalk.models.get_expert_status.ExpertStatusResponseBody;
import com.cubetalktest.cubetalk.services.api.ExpertService;
import com.cubetalktest.cubetalk.services.api.ServiceBuilder;
import com.cubetalktest.cubetalk.services.api.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,Mesibo.CallListener{

    private static final String TAG = HomeActivity.class.getSimpleName();

    private static final int RC_SIGN_UP_AS_EXPERT = 1000;
    private static final int RC_USER_EDIT_PROFILE = 2000;
    private static final int RC_CHANGE_PASSWORD = 3000;

    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout mDrawer;
    private SharedPreferences mSharedPreferences;
    private String mUserName;
    private TextView mUserNameText;
    private ImageButton mEditProfileButton;
    private String mUserProfileImageUrl;
    private ImageView mUserProfileImageView;
    private String mUserId;
    private MenuItem mSignUpAsExpertMenuItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSharedPreferences = getSharedPreferences(User.PREFERENCE_NAME, MODE_PRIVATE);
        if (!mSharedPreferences.contains(User.TOKEN)) {
            Intent intent = new Intent(HomeActivity.this, IntroScreenActivity.class);
            startActivity(intent);
            finish();
        }

      // Mesibo.setAccessToken("a16c6806b24340f1c4afdab2f66a2b96ea38e2201fd2e99154134");
        MesiboCall masibocall = MesiboCall.getInstance();
        MesiboCall.getInstance().init(getApplicationContext());
        MainApplication.startMesibo(mSharedPreferences.getString(User.MESIBO_TOKEN,""));
        // MesiboCall.getInstance().setListener(this);
        Mesibo api = Mesibo.getInstance();
        api.init(getApplicationContext());
        Mesibo.start();
        //masibocall.setListener(this);
        Mesibo.addListener(this);
        MesiboCall.getInstance().init(this);

        // set user authentication token obtained by creating user


        // MesiboCall mesiboCall = MesiboCall.getInstance();
       // mesiboCall.init(this);

        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
      //get fcm token
       /* FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }
                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        System.out.println("token "+token);
                        // Log and toast
                       *//* String msg = getString(R.string.msg_token_fmt, token);
                        Log.d(TAG, msg);
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();*//*
                    }
                });*/
        //get fcm token
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayUseLogoEnabled(true);
        }

        mDrawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        Menu navMenu = navigationView.getMenu();
        String currency=(mSharedPreferences.getInt(User.COUNTRYID, 0)==User.INDIACOUNTRYID? "INR ":"NRI ");
        Log.d("currency",currency);

        mSignUpAsExpertMenuItem = navMenu.findItem(R.id.nav_sign_up_as_expert);
//        signUpAsExpertMenuItem.setVisible(!mSharedPreferences.getBoolean(User.IS_EXPERT, false));
        ExpertApplicationStatus expertApplicationStatus = ExpertApplicationStatus.valueOf(
                mSharedPreferences.getInt(User.EXPERT_APPLICATION_STATUS, -1));

        switch (expertApplicationStatus) {
            case APPROVED:
                mSignUpAsExpertMenuItem.setVisible(false);
                break;

            case PENDING:
            case NOT_APPLIED:
            case REJECTED:
            default:
                mSignUpAsExpertMenuItem.setVisible(true);
                break;
        }


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home)
                .setDrawerLayout(mDrawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        mUserId = mSharedPreferences.getString(User.ID, "");
        mUserProfileImageUrl = mSharedPreferences.getString(User.PROFILE_IMAGE, "");
        mUserName = mSharedPreferences.getString(User.NAME, "");

        mUserProfileImageView = headerView.findViewById(R.id.iv_user_photo);
        mUserNameText = headerView.findViewById(R.id.tv_user_name);
        mEditProfileButton = headerView.findViewById(R.id.ib_edit_profile);

        if (!TextUtils.isEmpty(mUserProfileImageUrl)) {
            Glide.with(this)
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .load(mUserProfileImageUrl)
                    .placeholder(R.drawable.ic_account_circle_white_24dp)
                    .circleCrop()
                    .into(mUserProfileImageView); }

        mUserNameText.setText(mUserName);
        mEditProfileButton.setOnClickListener(v -> {
            if (mDrawer.isDrawerOpen(GravityCompat.START)) {
                mDrawer.closeDrawer(GravityCompat.START);
            }
            startActivityForResult(new Intent(HomeActivity.this, UserEditProfileActivity.class), RC_USER_EDIT_PROFILE);
        });

        if (actionBar != null) {
            actionBar.setTitle(getString(R.string.app_name));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);

        MenuItem expertDashboardMenuItem = menu.findItem(R.id.action_expert_dashboard);

//        expertDashboardMenuItem.setVisible(mSharedPreferences.getBoolean(User.IS_EXPERT, false));

        ExpertApplicationStatus expertApplicationStatus = ExpertApplicationStatus.valueOf(
                mSharedPreferences.getInt(User.EXPERT_APPLICATION_STATUS, -1)
        );

        switch (expertApplicationStatus) {
            case APPROVED:
                expertDashboardMenuItem.setVisible(true);
                break;

            case PENDING:
            case NOT_APPLIED:
            case REJECTED:
            default:
                expertDashboardMenuItem.setVisible(false);
                break;
        }

        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_expert_dashboard) {
            ExpertService expertService = ServiceBuilder.buildService(ExpertService.class);
            Call<ExpertStatusResponseBody> createRequest = expertService.getExpertStatus(mSharedPreferences.getString(User.TOKEN, ""),mUserId);

            createRequest.enqueue(new Callback<ExpertStatusResponseBody>() {
                @Override
                public void onResponse(@NotNull Call<ExpertStatusResponseBody> call, @NotNull Response<ExpertStatusResponseBody> response) {
                    Log.d(TAG, "onResponse: " + response.body());

                    ExpertStatusResponseBody responseBody = response.body();
                    if (responseBody.getSuccess()) {
                        SharedPreferences.Editor editor = mSharedPreferences.edit();
                        editor.putInt(User.EXPERT_APPLICATION_STATUS, responseBody.getData().getExpertApplicationStatus());
                        editor.putBoolean(User.IS_EXPERT_ACTIVE, responseBody.getData().getIsExpertActive());
                        editor.apply();
                        invalidateOptionsMenu();

                        if (mSharedPreferences.getBoolean(User.IS_EXPERT_ACTIVE, false)) {
                            startActivity(new Intent(HomeActivity.this, ExpertDashboardActivity.class));
                        } else {
                            new MaterialAlertDialogBuilder(HomeActivity.this)
                                    .setTitle("Cube Talk")
                                    .setMessage("You are currently suspended. Please contact admin for any query.")
                                    .setPositiveButton("OK", null)
                                    .show();
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<ExpertStatusResponseBody> call, @NotNull Throwable throwable) {
                    Log.d(TAG, "onResponse: throwable.getMessage(): " + throwable.getMessage());
                }
            });


            return true;
        }else  if (item.getItemId() == R.id.action_settings) {
            new MaterialAlertDialogBuilder(HomeActivity.this)
                    .setTitle("Cube Talk")
                    .setMessage("Coming Soon!")
                    .setPositiveButton("Ok", (dialog, which) -> {
                        //startpayment();
                        dialog.dismiss();
                    })
                    .show();
            return  true;

        }
        else  if (item.getItemId() == R.id.action_search) {
            new MaterialAlertDialogBuilder(HomeActivity.this)
                    .setTitle("Cube Talk")
                    .setMessage("Coming Soon!")
                    .setPositiveButton("Ok", (dialog, which) -> {
                        //startpayment();
                        dialog.dismiss();
                    })
                    .show();
            return  true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        }

        if (item.getItemId() == R.id.nav_sign_up_as_expert) {
            fetchExpertSignUpCondition();
            return true;
        } else if (item.getItemId() == R.id.nav_credit_shell) {
          /*  new MaterialAlertDialogBuilder(HomeActivity.this)
                    .setTitle("Cube Talk")
                    .setMessage("Coming Soon!")
                    .setPositiveButton("Ok", (dialog, which) -> {
                        //startpayment();
                        dialog.dismiss();
                    })
                    .show();*/
           Intent intent = new Intent(this, CreditShellActivity.class);
           startActivity(intent);
           /* if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.RECORD_AUDIO},
                        100);

            }*/
           // startActivity(new Intent(HomeActivity.this, VideoCallActivity.class));


          // Mesibo.UserProfile userProfile = new Mesibo.UserProfile();
           // userProfile.name = "232283";
            //userProfile.address = "u1";
          // Making audio call
           // MesiboCall.getInstance().call(HomeActivity.this,Mesibo.random() , userProfile, true);


           // Mesibo.addListener(this);

            // set user authentication token obtained by creating user


        } else if (item.getItemId() == R.id.nav_upcoming_booking) {
            Intent intent = new Intent(this, UserUpcomingBookingsActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.nav_past_booking) {
            Intent intent = new Intent(this, UserPastBookingsActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id .nav_contact_admin) {
            new MaterialAlertDialogBuilder(HomeActivity.this)
                    .setTitle("Cube Talk")
                    .setMessage("Coming Soon!")
                    .setPositiveButton("Ok", (dialog, which) -> {
                        //startpayment();
                        dialog.dismiss();
                    })
                    .show();
           // Intent intent = new Intent(this, ContactAdminActivity.class);
            //startActivity(intent);
        }else if (item.getItemId() == R.id.nav_change_password) {
            startChangePassword();
        } else if (item.getItemId() == R.id.nav_logout) {
            new MaterialAlertDialogBuilder(this)
                    .setTitle("Cube Talk")
                    .setMessage("Are you sure you want to logout?")
                    .setPositiveButton("Logout", (dialog, which) -> {
                        clearLoginPreferences();
                        startIntroScreen();
                        finish();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
            return true;
        }
        return false;
    }

    private void fetchExpertSignUpCondition() {
        UserService userService = ServiceBuilder.buildService(UserService.class);
        Call<ExpertSignUpConditionResponse> response = userService.getExpertSignUpCondition(mSharedPreferences.getString(User.TOKEN, ""));
        response.enqueue(new Callback<ExpertSignUpConditionResponse>() {
            @Override
            public void onResponse(
                    @NotNull Call<ExpertSignUpConditionResponse> call,
                    @NotNull Response<ExpertSignUpConditionResponse> response
            ) {
                ExpertSignUpConditionResponse responseBody = response.body();
                if (responseBody != null && responseBody.getSuccess()) {
                    ExpertSignUpConditionResponse.Setting setting = responseBody.getSetting();
                    if (setting.getExpertSignUp()) {
                        fetchExpertStatus(true);
                    } else {
                        new MaterialAlertDialogBuilder(HomeActivity.this)
                                .setTitle("Cube Talk")
                                .setMessage("Expert Registration is temporarily disabled.")
                                .setPositiveButton("OK", null)
                                .show();
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<ExpertSignUpConditionResponse> call, @NotNull Throwable throwable) {
                Log.d(TAG, "onFailure: throwable.getMessage(): " + throwable.getMessage());
            }
        });
    }

    private void startChangePassword() {
        Intent intent = new Intent(this, ChangePasswordActivity.class);
        startActivityForResult(intent, RC_CHANGE_PASSWORD);
    }

    private void startIntroScreen() {
        Intent intent = new Intent(this, IntroScreenActivity.class);
        intent.putExtra("OpenActivity", LoginActivity.TAG);
        startActivity(intent);
    }

    private void clearLoginPreferences() {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        Mesibo.start();

        mUserProfileImageUrl = mSharedPreferences.getString(User.PROFILE_IMAGE, "");
        mUserName = mSharedPreferences.getString(User.NAME, "");

        if (!TextUtils.isEmpty(mUserProfileImageUrl)) {
            Glide.with(this)
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .load(mUserProfileImageUrl)
                    .placeholder(R.drawable.ic_account_circle_white_24dp)
                    .circleCrop()
                    .into(mUserProfileImageView);
        }

        mUserNameText.setText(mUserName);

        fetchExpertStatus(false);



    }

    private void fetchExpertStatus(boolean shouldShowDialog) {
        ExpertService expertService = ServiceBuilder.buildService(ExpertService.class);
        Call<ExpertStatusResponseBody> createRequest = expertService.getExpertStatus( mSharedPreferences.getString(User.TOKEN, ""),mUserId );

        createRequest.enqueue(new Callback<ExpertStatusResponseBody>() {
            @Override
            public void onResponse(@NotNull Call<ExpertStatusResponseBody> call, @NotNull Response<ExpertStatusResponseBody> response) {
                Log.d(TAG, "onResponse: " + response.body());
                ExpertStatusResponseBody responseBody = response.body();
                if (responseBody.getSuccess()) {
                    SharedPreferences.Editor editor = mSharedPreferences.edit();
                    editor.putInt(User.EXPERT_APPLICATION_STATUS, responseBody.getData().getExpertApplicationStatus());
                    editor.putBoolean(User.IS_EXPERT_ACTIVE, responseBody.getData().getIsExpertActive());
                    editor.apply();
                    invalidateOptionsMenu();

                    ExpertApplicationStatus expertApplicationStatus = ExpertApplicationStatus.valueOf(
                            mSharedPreferences.getInt(User.EXPERT_APPLICATION_STATUS, -1)
                    );

                    switch (expertApplicationStatus) {
                        case APPROVED:
                            if (shouldShowDialog) {
                                new MaterialAlertDialogBuilder(HomeActivity.this)
                                        .setTitle("Cube Talk")
                                        .setMessage("You application has been accepted.")
                                        .setPositiveButton("OK", null)
                                        .show();
                            }
                            mSignUpAsExpertMenuItem.setVisible(false);
                            break;

                        case PENDING:
                            if (shouldShowDialog) {
                                new MaterialAlertDialogBuilder(HomeActivity.this)
                                        .setTitle("Cube Talk")
                                        .setMessage("You have already applied. Your application verification is pending.")
                                        .setPositiveButton("OK", null)
                                        .show();
                            }

                            mSignUpAsExpertMenuItem.setVisible(true);
                            break;

                        case NOT_APPLIED:
                        case REJECTED:
                        default:
                            if (shouldShowDialog) {
                                startActivity(new Intent(HomeActivity.this, ExpertIntroScreenActivity.class));
                            }
                            mSignUpAsExpertMenuItem.setVisible(true);

                            break;
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<ExpertStatusResponseBody> call, @NotNull Throwable throwable) {
                Log.d(TAG, "onResponse: throwable.getMessage(): " + throwable.getMessage());
            }
        });
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

   /* @Override
    public boolean MesiboCall_onNotify(int i, Mesibo.UserProfile userProfile, boolean b) {
        return false;
    }

    @Override
    public MesiboVideoCallFragment MesiboCall_getVideoCallFragment(Mesibo.UserProfile userProfile) {
        // masibocall.setListener(this);
        Mesibo.UserProfile userProfile1 = new Mesibo.UserProfile();
        userProfile1.name = userProfile.name;
        userProfile1.address = userProfile.address;
        MesiboCall masibocall = MesiboCall.getInstance();
        MesiboCallConfig config = masibocall.getConfig();
        //   config.videoCallTitle = "CubeTalk";
        // userProfile.address = "u3";
        // MesiboCall.getInstance().call(userUpcomingBookingsActivity, Mesibo.random(), userProfile, true);
        VideoCallFragment videoCallFragment = new VideoCallFragment(this,30);
        videoCallFragment.setUser(userProfile1);
        return videoCallFragment;
    }

    @Override
    public MesiboAudioCallFragment MesiboCall_getAudioCallFragment(Mesibo.UserProfile userProfile) {
        return null;
    }

    @Override
    public Fragment MesiboCall_getIncomingAudioCallFragment(Mesibo.UserProfile userProfile) {
        return null;
    }
    */

}
