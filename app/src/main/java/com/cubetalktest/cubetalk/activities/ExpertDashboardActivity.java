package com.cubetalktest.cubetalk.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.button.MaterialButton;

import com.cubetalktest.cubetalk.databinding.ActivityExpertDashboardBinding;
import com.cubetalktest.cubetalk.databinding.ContentExpertDashboardBinding;
import com.cubetalktest.cubetalk.models.User;
import com.cubetalktest.cubetalk.models.dashboarddetails.DashBoardDetails;
import com.cubetalktest.cubetalk.services.api.ExpertDashBoardService;
import com.cubetalktest.cubetalk.services.api.ServiceBuilder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExpertDashboardActivity
        extends AppCompatActivity {

    private static final String TAG = ExpertDashboardActivity.class.getSimpleName();

    private ActivityExpertDashboardBinding mActivityBinding;
    private ContentExpertDashboardBinding mContentBinding;
    private MaterialButton mViewProfileButton;
    private MaterialButton mViewUploadDocumentButton;
    private MaterialButton mBookingManagementButton;
    private  MaterialButton btn_view_expert_refund;
    private  MaterialButton btn_earning_statics;
    private SharedPreferences mSharedPreferences;
    TextView tv_countbyweekval;
    TextView tv_bookingcompleted_this_month;
    TextView tv_weekEarningVal;
    TextView tv_monthEarningVal;
    TextView tv_welcome;
    TextView tv_upcomingbookingCount;
    String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_expert_dashboard);

        mActivityBinding = ActivityExpertDashboardBinding.inflate(LayoutInflater.from(this));
        setContentView(mActivityBinding.getRoot());
        mSharedPreferences = getSharedPreferences(User.PREFERENCE_NAME, MODE_PRIVATE);
        userId = mSharedPreferences.getString(User.ID, "");
        Toolbar toolbar = mActivityBinding.toolbar;
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mContentBinding = mActivityBinding.contentExpertDashboard;

        mBookingManagementButton = mContentBinding.mbtnBookingManagement;
        btn_earning_statics=mContentBinding.btnEarningStatics;
        mViewProfileButton = mContentBinding.btnViewProfile;
        tv_countbyweekval=mContentBinding.tvCountbyweekval;
        tv_bookingcompleted_this_month=mContentBinding.tvBookingcompletedThisMonth;
        tv_weekEarningVal=mContentBinding.tvWeekEarningVal;
        tv_monthEarningVal=mContentBinding.tvMonthEarningVal;
        tv_welcome=mContentBinding.tvWelCome;
        tv_upcomingbookingCount=mContentBinding.tvUpcomingbookingCount;
        mViewUploadDocumentButton = mContentBinding.btnViewUploadDocument;
        btn_view_expert_refund=mContentBinding.btnViewExpertRefund;
        //region Listeners
        String name =mSharedPreferences.getString(User.NAME, "");
        String[] nameSplitStr = name.split("\\s+");
        Spannable word = new SpannableString("Welcome ");
        word.setSpan(new ForegroundColorSpan(Color.BLACK), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_welcome.append(word);
        Spannable wordTwo = new SpannableString(nameSplitStr[0]);
        wordTwo.setSpan(new ForegroundColorSpan(Color.BLUE), 0, wordTwo.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_welcome.append(wordTwo);
        Spannable wordThree = new SpannableString(" to Expert Dashboard");
        wordThree.setSpan(new ForegroundColorSpan(Color.BLACK), 0, wordThree.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_welcome.append(wordThree);
       // tv_welcome.setText("Welcome "+mSharedPreferences.getString(User.NAME, "")+ " to Expert Dashboard ");
       callApifordashboarddetails();
        mBookingManagementButton.setOnClickListener(v -> {
            Intent intent = new Intent(
                    this,
                    ExpertBookingManagementActivity.class
            );
            startActivity(intent);
        });

        mViewProfileButton.setOnClickListener(v -> {
            Intent intent = new Intent(
                    this,
                    ExpertViewProfileActivity.class
            );
            startActivity(intent);
        });

        mViewUploadDocumentButton.setOnClickListener(v -> {
            Intent intent = new Intent(
                    this,
                    ExpertViewUploadDocumentActivity.class
            );
            startActivity(intent);
        });

        btn_view_expert_refund.setOnClickListener(v-> {

                Intent intent = new Intent(this, ExpertRefundRequestActivity.class);
                startActivity(intent);
        });
        btn_earning_statics.setOnClickListener( view -> {
            Intent intent = new Intent(this, ExpertEaningStatisticsActivity.class);
            startActivity(intent);
        });
        //endregion

    }

    private void callApifordashboarddetails() {
        ExpertDashBoardService expertDashBoardService = ServiceBuilder.buildService(ExpertDashBoardService.class);
        Call<DashBoardDetails> createRequest = expertDashBoardService.getdashboarddetails(mSharedPreferences.getString(User.TOKEN, ""),userId);
        createRequest.enqueue(new Callback<DashBoardDetails>() {
            @Override
            public void onResponse(Call<DashBoardDetails> call, Response<DashBoardDetails> response) {
                Log.d(TAG, "onResponse: responseshell: " + response.body().toString());
                if (response.body().getSuccess()){
                    try {
                        tv_countbyweekval.setText(response.body().getData().get(0).getCountByWeek().get(0).getTotalBoookings().toString());
                        tv_bookingcompleted_this_month.setText(response.body().getData().get(0).getCountByMonth().get(0).getTotalBoookings().toString());
                        tv_monthEarningVal.setText("INR " + Math.round(response.body().getData().get(0).getCountByMonth().get(0).getTotalExpertShareAmount()));
                        tv_weekEarningVal.setText("INR " + Math.round(response.body().getData().get(0).getCountByWeek().get(0).getTotalExpertShareAmount()));
                        if (response.body().getData().get(0).getUpComingBookings().get(0).getCount()>0)
                          tv_upcomingbookingCount.setText(response.body().getData().get(0).getUpComingBookings().get(0).getCount().toString());
                        else
                            tv_upcomingbookingCount.setText("0");
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<DashBoardDetails> call, Throwable t) {
                //Log.d(TAG, "onResponse: response: " + response.toString());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }
}
