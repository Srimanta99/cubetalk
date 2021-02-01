package com.cubetalktest.cubetalk.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;

import com.cubetalktest.cubetalk.R;
import com.cubetalktest.cubetalk.adapters.viewpager2.EarningStatisticsAdapter;
import com.cubetalktest.cubetalk.adapters.viewpager2.ExpertEarningAdapter;
import com.cubetalktest.cubetalk.databinding.ActivityExpertEaningStatisticsBinding;
import com.cubetalktest.cubetalk.databinding.ContentEarningStatisticsBinding;
import com.cubetalktest.cubetalk.models.User;
import com.cubetalktest.cubetalk.models.earning_statics.GetMonthResponse;
import com.cubetalktest.cubetalk.services.api.EarningStaticService;
import com.cubetalktest.cubetalk.services.api.ServiceBuilder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExpertEaningStatisticsActivity extends AppCompatActivity {

    ActivityExpertEaningStatisticsBinding activityExpertEaningStatisticsBinding;
   ContentEarningStatisticsBinding contentEarningStatisticsBinding;
    private ViewPager viewPagerEarningStatistics;
    private EarningStatisticsAdapter earningStatisticsAdapter;
    private TabLayout tab_earningbar;
    private SharedPreferences mSharedPreferences;
    private static final String TAG = ExpertEaningStatisticsActivity.class.getSimpleName();
    String userId="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityExpertEaningStatisticsBinding= com.cubetalktest.cubetalk.databinding.ActivityExpertEaningStatisticsBinding.inflate(LayoutInflater.from(this));
        setContentView(activityExpertEaningStatisticsBinding.getRoot());
        Toolbar toolbar=activityExpertEaningStatisticsBinding.toolbar;
        mSharedPreferences = getSharedPreferences(User.PREFERENCE_NAME, MODE_PRIVATE);
        userId = mSharedPreferences.getString(User.ID, "");
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(getResources().getString(R.string.action_earning_statices));
        }
        contentEarningStatisticsBinding=activityExpertEaningStatisticsBinding.contentEarningStatisics;
        tab_earningbar=contentEarningStatisticsBinding.tabEarningbar;
        viewPagerEarningStatistics=contentEarningStatisticsBinding.viewPagerEarningStatistics;
        earningStatisticsAdapter= new EarningStatisticsAdapter(this,getSupportFragmentManager());
       /* for (int i = 0; i < 5; i++) {
            tab_earningbar.addTab(tab_earningbar.newTab().setText("TAB Layout" + String.valueOf(i + 1)));
        }
        ExpertEarningAdapter adapter = new ExpertEarningAdapter(getSupportFragmentManager(),tab_earningbar.getTabCount());
        viewPagerEarningStatistics.setAdapter(adapter);*/
        viewPagerEarningStatistics.setOffscreenPageLimit(1);
        viewPagerEarningStatistics.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab_earningbar));
        tab_earningbar.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d(TAG, "onTabSelected: tab.getText(): " + tab.getText());
                viewPagerEarningStatistics.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        callApiFoEarningStatic();
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
    private void callApiFoEarningStatic() {
      /*  Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        formattedDate=formattedDate+"-09-01";*/
        EarningStaticService earningStaticService = ServiceBuilder.buildService(EarningStaticService.class);
        Call<GetMonthResponse> createRequest = earningStaticService.getearningmonth(mSharedPreferences.getString(User.TOKEN, ""));
        createRequest.enqueue(new Callback<GetMonthResponse>() {
            @Override
            public void onResponse(Call<GetMonthResponse> call, Response<GetMonthResponse> response) {
                Log.d(TAG, "onResponse: responseshell: " + response.body().toString());
                if(response.body().getSuccess()){
                    for (int i=0;i<response.body().getData().size();i++){
                        tab_earningbar.addTab(tab_earningbar.newTab().setText(response.body().getData().get(i).getFormat()));
                    }
                    ExpertEarningAdapter adapter = new ExpertEarningAdapter(getSupportFragmentManager(),tab_earningbar.getTabCount(),response.body().getData());
                    viewPagerEarningStatistics.setAdapter(adapter);
                }
                //  if (response.body().){

                // }
            }

            @Override
            public void onFailure(Call<GetMonthResponse> call, Throwable t) {
                Log.d(TAG, "onResponse: response: " + t.toString());
            }
        });

    }
}