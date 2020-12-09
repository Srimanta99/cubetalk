package local.impactlife.cubetalk.fragments;

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
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import local.impactlife.cubetalk.R;
import local.impactlife.cubetalk.activities.EarningStatisticsActivity;
import local.impactlife.cubetalk.adapters.recycler.EarningStaticAdapter;
import local.impactlife.cubetalk.databinding.FragmentJanuaryBinding;
import local.impactlife.cubetalk.databinding.FragmentTimeSlotsBinding;
import local.impactlife.cubetalk.models.User;
import local.impactlife.cubetalk.models.earning_statics.EarningStaticesResponse;
import local.impactlife.cubetalk.services.api.EarningStaticService;
import local.impactlife.cubetalk.services.api.ServiceBuilder;
import local.impactlife.cubetalk.utils.Utils;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class DynamicEarningStatisticsFragment extends Fragment {
    private static final String TAG = EarningStatisticsActivity.class.getSimpleName();
    View view;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private SharedPreferences mSharedPreferences;
    String userId="";
    MaterialTextView mtotalEarningValue;
    MaterialTextView mTotalConfirmbook;
    MaterialTextView earningMonth;
    MaterialTextView bookingMonth;
    RecyclerView recEarningStatics;
    RelativeLayout rlshell;
    RelativeLayout rl_book;
    TextView nobook;
    MaterialTextView tv_ytd;
    MaterialTextView tv_fyval;
    ImageView lfypdf,ytdpdf;
    EarningStaticAdapter earningStatisticsAdapter;
    List<EarningStaticesResponse.BookingsList> bookingsLists=new ArrayList<>();
    FragmentJanuaryBinding fragmentJanuaryBinding;

    public static DynamicEarningStatisticsFragment newInstance(String param1) {
        DynamicEarningStatisticsFragment fragment = new DynamicEarningStatisticsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    int val;
    TextView c;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        fragmentJanuaryBinding=FragmentJanuaryBinding.inflate(inflater, container, false);
        return fragmentJanuaryBinding.getRoot();
        //view = inflater.inflate(R.layout.fragment_april, container, false);
      //  val = getArguments().getInt("someInt", 0);
      //  c = view.findViewById(R.id.mtv_total_bookingvalue);
      //  c.setText("" + val);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mtotalEarningValue=fragmentJanuaryBinding.mtvTotalEarningvalue;
        mTotalConfirmbook=fragmentJanuaryBinding.mtvTotalBookingvalue;
        recEarningStatics=fragmentJanuaryBinding.recEarningStatics;
        earningMonth=fragmentJanuaryBinding.tvEarningmonth;
        bookingMonth=fragmentJanuaryBinding.tvBookingmonth;
        rlshell=fragmentJanuaryBinding.rlshell;
        rl_book=fragmentJanuaryBinding.rlBook;
        nobook=fragmentJanuaryBinding.tvNobook;
        tv_ytd=fragmentJanuaryBinding.tvYtdVal;
        tv_fyval=fragmentJanuaryBinding.tvFyval;
        lfypdf=fragmentJanuaryBinding.lfypdf;
        ytdpdf=fragmentJanuaryBinding.ytdpdf;

        mSharedPreferences = getActivity().getSharedPreferences(User.PREFERENCE_NAME, MODE_PRIVATE);
        userId = mSharedPreferences.getString(User.ID, "");
        earningStatisticsAdapter= new EarningStaticAdapter(getActivity(),bookingsLists);
        recEarningStatics.setAdapter(earningStatisticsAdapter);
        earningMonth.setText("Total Earning in "+Utils.convertdtatomonthname(mParam1));
        bookingMonth.setText("Total Booking in "+Utils.convertdtatomonthname(mParam1)+"(Confirmed)");
        lfypdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 callApiforlfPdf();
            }
        });

        ytdpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callytdApipdf();

            }
        });
        callApiFoEarningStatic();

    }

    private void callytdApipdf() {
        EarningStaticService earningStaticService = ServiceBuilder.buildService(EarningStaticService.class);
        Call<ResponseBody> createRequest = earningStaticService.getExpertStaticYtdPdf(mSharedPreferences.getString(User.TOKEN, ""),userId);
        createRequest.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(TAG, "onResponse: dynamic: " + response.toString());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    private void callApiforlfPdf() {
        EarningStaticService earningStaticService = ServiceBuilder.buildService(EarningStaticService.class);
        Call<ResponseBody> createRequest = earningStaticService.getExpertStaticPdf(mSharedPreferences.getString(User.TOKEN, ""),userId);
        createRequest.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(TAG, "onResponse: dynamic: " + response.toString());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    private void callApiFoEarningStatic() {
       /* Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        if(mParam1.equals("2"))
          formattedDate=formattedDate+"-09-01";
        else if(mParam1.equals("3"))
            formattedDate=formattedDate+"-10-01";
        else
            formattedDate=formattedDate+"-11-01";
*/
        EarningStaticService earningStaticService = ServiceBuilder.buildService(EarningStaticService.class);
        Call<EarningStaticesResponse> createRequest = earningStaticService.getearningStatics(mSharedPreferences.getString(User.TOKEN, ""),userId,mParam1);
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
                        rl_book.setVisibility(View.VISIBLE);
                        nobook.setVisibility(View.GONE);
                        if (response.body().getData().get(0).getYTDIncome()!=null)
                            tv_ytd.setText("INR "+String.valueOf(response.body().getData().get(0).getYTDIncome().get(0).getTotalYTDIncome()));
                        // if (response.body().getData().get(0).getLastFYIncome()!=null)
                        //  tv_fyval.setText("INR "+ response.body().getData().get(0).getLastFYIncome().get(0));
                        earningStatisticsAdapter.notifyDataSetChanged();
                    }else{
                        rl_book.setVisibility(View.GONE);
                        nobook.setVisibility(View.VISIBLE);
                       // nobook.setText(mParam1.toString());
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
