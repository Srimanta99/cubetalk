package local.impactlife.cubetalk.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import local.impactlife.cubetalk.R;
import local.impactlife.cubetalk.adapters.viewpager2.EarningStatisticsAdapter;
import local.impactlife.cubetalk.databinding.ActivityEarningStatisticsBinding;
import local.impactlife.cubetalk.databinding.ContentEarningStatisticsBinding;
import local.impactlife.cubetalk.fragments.AprilFragment;
import local.impactlife.cubetalk.fragments.AugestFragment;
import local.impactlife.cubetalk.fragments.DecemberFragment;
import local.impactlife.cubetalk.fragments.JanuaryFragment;
import local.impactlife.cubetalk.fragments.JulyFragment;
import local.impactlife.cubetalk.fragments.JuneFragment;
import local.impactlife.cubetalk.fragments.MarchFragment;
import local.impactlife.cubetalk.fragments.FebruaryFragment;
import local.impactlife.cubetalk.fragments.MayFragment;
import local.impactlife.cubetalk.fragments.NovombarFragment;
import local.impactlife.cubetalk.fragments.OctobarFragment;
import local.impactlife.cubetalk.fragments.SeptemberFragment;
import local.impactlife.cubetalk.models.User;
import local.impactlife.cubetalk.models.earning_statics.EarningStaticesResponse;
import local.impactlife.cubetalk.models.earning_statics.GetMonthResponse;
import local.impactlife.cubetalk.services.api.EarningStaticService;
import local.impactlife.cubetalk.services.api.ServiceBuilder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EarningStatisticsActivity extends AppCompatActivity {
    private ActivityEarningStatisticsBinding activityEarningStatisticsBinding;
    private ContentEarningStatisticsBinding contentEarningStatisticsBinding;
    private ViewPager viewPagerEarningStatistics;
    private EarningStatisticsAdapter earningStatisticsAdapter;
    private  TabLayout tab_earningbar;
    private SharedPreferences mSharedPreferences;
    private static final String TAG = EarningStatisticsActivity.class.getSimpleName();
    String userId="";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityEarningStatisticsBinding=ActivityEarningStatisticsBinding.inflate(LayoutInflater.from(this));
        setContentView(activityEarningStatisticsBinding.getRoot());
        Toolbar toolbar=activityEarningStatisticsBinding.toolbar;
        mSharedPreferences = getSharedPreferences(User.PREFERENCE_NAME, MODE_PRIVATE);
        userId = mSharedPreferences.getString(User.ID, "");
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(getResources().getString(R.string.action_earning_statices));
        }
        contentEarningStatisticsBinding=activityEarningStatisticsBinding.contentEarningStatisics;
        tab_earningbar=contentEarningStatisticsBinding.tabEarningbar;
        viewPagerEarningStatistics=contentEarningStatisticsBinding.viewPagerEarningStatistics;
        earningStatisticsAdapter= new EarningStatisticsAdapter(this,getSupportFragmentManager());
        earningStatisticsAdapter.addFragment(new JanuaryFragment(), getResources().getString(R.string.jan));
        earningStatisticsAdapter.addFragment(new FebruaryFragment(), getResources().getString(R.string.feb));
        earningStatisticsAdapter.addFragment(new MarchFragment(), getResources().getString(R.string.mar));
        earningStatisticsAdapter.addFragment(new AprilFragment(), getResources().getString(R.string.apr));
        earningStatisticsAdapter.addFragment(new MayFragment(), getResources().getString(R.string.may));
        earningStatisticsAdapter.addFragment(new JuneFragment(),  getResources().getString(R.string.june));
        earningStatisticsAdapter.addFragment(new JulyFragment(), getResources().getString(R.string.july));
        earningStatisticsAdapter.addFragment(new AugestFragment(), getResources().getString(R.string.aug));
        earningStatisticsAdapter.addFragment(new SeptemberFragment(), getResources().getString(R.string.sep));
        earningStatisticsAdapter.addFragment(new OctobarFragment(), getResources().getString(R.string.oct));
        earningStatisticsAdapter.addFragment(new NovombarFragment(), getResources().getString(R.string.nov));
        earningStatisticsAdapter.addFragment(new DecemberFragment(), getResources().getString(R.string.dec));

        viewPagerEarningStatistics.setAdapter(earningStatisticsAdapter);
        tab_earningbar.setupWithViewPager(viewPagerEarningStatistics);
        viewPagerEarningStatistics.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
       // callApiFoEarningStatic();
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void callApiFoEarningStatic() {
      //  Date c = Calendar.getInstance().getTime();
        //SimpleDateFormat df = new SimpleDateFormat("yyyy", Locale.getDefault());
       // String formattedDate = df.format(c);
       // formattedDate=formattedDate+"-09-01";
        EarningStaticService earningStaticService = ServiceBuilder.buildService(EarningStaticService.class);
        Call<GetMonthResponse> createRequest = earningStaticService.getearningmonth(mSharedPreferences.getString(User.TOKEN, ""));
        createRequest.enqueue(new Callback<GetMonthResponse>() {
            @Override
            public void onResponse(Call<GetMonthResponse> call, Response<GetMonthResponse> response) {
                Log.d(TAG, "onResponse: responseshell: " + response.body().toString());
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
