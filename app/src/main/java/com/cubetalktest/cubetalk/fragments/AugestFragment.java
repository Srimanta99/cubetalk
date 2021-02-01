package com.cubetalktest.cubetalk.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.cubetalktest.cubetalk.activities.EarningStatisticsActivity;
import com.cubetalktest.cubetalk.adapters.recycler.EarningStaticAdapter;
import com.cubetalktest.cubetalk.databinding.FragmentAuguestBinding;
import com.cubetalktest.cubetalk.models.User;
import com.cubetalktest.cubetalk.models.earning_statics.EarningStaticesResponse;
import com.cubetalktest.cubetalk.services.api.EarningStaticService;
import com.cubetalktest.cubetalk.services.api.ServiceBuilder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class AugestFragment extends Fragment {
    private SharedPreferences mSharedPreferences;
    String userId="";
    private static final String TAG = EarningStatisticsActivity.class.getSimpleName();
    FragmentAuguestBinding fragmentAuguestBinding;
    MaterialTextView mtotalEarningValue;
    MaterialTextView mTotalConfirmbook;
    RecyclerView recEarningStatics;
    EarningStaticAdapter earningStatisticsAdapter;
    RelativeLayout rlshell;
    RelativeLayout rlbook;
    TextView nobook;
    MaterialTextView tv_ytd;
    MaterialTextView tv_fyval;
    ImageView lfypdf,ytdpdf;
    List<EarningStaticesResponse.BookingsList> bookingsLists=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceStat) {
        fragmentAuguestBinding= FragmentAuguestBinding.inflate(inflater, container, false);
        return fragmentAuguestBinding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mtotalEarningValue=fragmentAuguestBinding.mtvTotalEarningvalue;
        mTotalConfirmbook=fragmentAuguestBinding.mtvTotalBookingvalue;
        recEarningStatics=fragmentAuguestBinding.recEarningStatics;
        rlshell=fragmentAuguestBinding.rlshell;
        rlbook=fragmentAuguestBinding.rlBook;
        nobook=fragmentAuguestBinding.tvNobook;
        tv_ytd=fragmentAuguestBinding.tvYtdVal;
        tv_fyval=fragmentAuguestBinding.tvFyval;
        lfypdf=fragmentAuguestBinding.lfypdf;
        ytdpdf=fragmentAuguestBinding.ytdpdf;
        mSharedPreferences = getActivity().getSharedPreferences(User.PREFERENCE_NAME, MODE_PRIVATE);
        userId = mSharedPreferences.getString(User.ID, "");
        earningStatisticsAdapter= new EarningStaticAdapter(getActivity(),bookingsLists);
        recEarningStatics.setAdapter(earningStatisticsAdapter);
        callApiFoEarningStatic();
    }
    private void callApiFoEarningStatic() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        formattedDate=formattedDate+"-08-01";
        EarningStaticService earningStaticService = ServiceBuilder.buildService(EarningStaticService.class);
        Call<EarningStaticesResponse> createRequest = earningStaticService.getearningStatics(mSharedPreferences.getString(User.TOKEN, ""),userId,formattedDate);
        createRequest.enqueue(new Callback<EarningStaticesResponse>() {
            @Override
            public void onResponse(Call<EarningStaticesResponse> call, Response<EarningStaticesResponse> response) {
                Log.d(TAG, "onResponse: responseshell: " + response.toString());
                if (response.body().getSuccess()){
                    if (response.body().getData().get(0).getTotalCountInMOnth().size() > 0) {
                        bookingsLists.clear();
                        mtotalEarningValue.setText("INR "+ response.body().getData().get(0).getTotalCountInMOnth().get(0).getTotalAmount().toString());
                        mTotalConfirmbook.setText(response.body().getData().get(0).getTotalCountInMOnth().get(0).getTotalBoookings().toString());
                        bookingsLists.addAll(response.body().getData().get(0).getBookingsList());
                        rlbook.setVisibility(View.VISIBLE);
                        nobook.setVisibility(View.GONE);
                        if (response.body().getData().get(0).getYTDIncome()!=null)
                            tv_ytd.setText("INR "+String.valueOf(response.body().getData().get(0).getYTDIncome().get(0).getTotalYTDIncome()));
                        // if (response.body().getData().get(0).getLastFYIncome()!=null)
                        //  tv_fyval.setText("INR "+ response.body().getData().get(0).getLastFYIncome().get(0));

                        earningStatisticsAdapter.notifyDataSetChanged();
                    }else{
                        rlbook.setVisibility(View.GONE);
                        nobook.setVisibility(View.VISIBLE);
                    }

                }

            }

            @Override
            public void onFailure(Call<EarningStaticesResponse> call, Throwable t) {
                Log.d(TAG, "onResponse: response: " + t.toString());
            }
        });

    }
}
