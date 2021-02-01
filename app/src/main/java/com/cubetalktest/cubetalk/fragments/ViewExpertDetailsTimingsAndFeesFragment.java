package com.cubetalktest.cubetalk.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.cubetalktest.cubetalk.databinding.FragmentViewExpertDetailsTimingsAndFeesBinding;
import com.cubetalktest.cubetalk.models.User;
import com.cubetalktest.cubetalk.models.common.ConsultingSlot;
import com.cubetalktest.cubetalk.models.common.ConsultingSlotDay;
import com.cubetalktest.cubetalk.models.common.ConsultingSlotDuration;
import com.cubetalktest.cubetalk.models.user_info.UserInfoFetchResponse;
import com.cubetalktest.cubetalk.utils.customview.CustomAlertReviewUpcomingChanges;
import com.cubetalktest.cubetalk.viewmodels.ViewExpertDetailsViewModel;

import static com.cubetalktest.cubetalk.utils.Utils.convert24HoursTo12Hours;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewExpertDetailsTimingsAndFeesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewExpertDetailsTimingsAndFeesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ViewExpertDetailsViewModel mViewModel;
    private FragmentViewExpertDetailsTimingsAndFeesBinding mFragmentBinding;

    private LinearLayout mExpertConsultationSlotsLayout;
    private LinearLayout mConsultingSlotWeekdaysLayout;
    private MaterialTextView mConsultingSlotWeekdaysDurationText;
    private LinearLayout mConsultingSlotSaturdayLayout;
    private MaterialTextView mConsultingSlotSaturdayDurationText;
    private LinearLayout mConsultingSlotSundayLayout;
    private MaterialTextView mConsultingSlotSundayDurationText;
    private LinearLayout mExpertConsultationSlotTimingsAndPriceLayout;
    private LinearLayout mServiceStartDateLayout;
    private MaterialTextView mServiceStartDateText;
    private MaterialCardView mConsultingSlotTime12MinutesCard;
    private MaterialTextView mConsultingSlot12MinutesPriceText;
    private MaterialCardView mConsultingSlotTime25MinutesCard;
    private MaterialTextView mConsultingSlot25MinutesPriceText;
    private MaterialCardView mConsultingSlotTime50MinutesCard;
    private MaterialTextView mConsultingSlot50MinutesPriceText;
    private MaterialButton mbtnReviewUpcomingChange;
    private SharedPreferences mSharedPreferences;
    public ViewExpertDetailsTimingsAndFeesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TimingsAndFeesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewExpertDetailsTimingsAndFeesFragment newInstance(String param1, String param2) {
        ViewExpertDetailsTimingsAndFeesFragment fragment = new ViewExpertDetailsTimingsAndFeesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_view_expert_details_timings_and_fees, container, false);

        mFragmentBinding = FragmentViewExpertDetailsTimingsAndFeesBinding.inflate(inflater, container, false);
        return mFragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSharedPreferences = getActivity().getSharedPreferences(User.PREFERENCE_NAME, Context.MODE_PRIVATE);

        mViewModel = new ViewModelProvider(getActivity()).get(ViewExpertDetailsViewModel.class);

//        mExpertConsultationSlotsLayout = mFragmentBinding.llExpertConsultationTimings;

        mConsultingSlotWeekdaysLayout = mFragmentBinding.llConsultingSlotWeekdays;
        mConsultingSlotWeekdaysDurationText = mFragmentBinding.mtvConsultingSlotWeekdaysDuration;

        mConsultingSlotSaturdayLayout = mFragmentBinding.llConsultingSlotSaturday;
        mConsultingSlotSaturdayDurationText = mFragmentBinding.mtvConsultingSlotSaturdayDuration;

        mConsultingSlotSundayLayout = mFragmentBinding.llConsultingSlotSunday;
        mConsultingSlotSundayDurationText = mFragmentBinding.mtvConsultingSlotSundayDuration;

        mServiceStartDateLayout = mFragmentBinding.llServiceStartDate;
        mServiceStartDateText = mFragmentBinding.mtvServiceStartDate;

//        mExpertConsultationSlotTimingsAndPriceLayout = mFragmentBinding.llConsultingSlotTimingsAndPrice;

        mConsultingSlotTime12MinutesCard = mFragmentBinding.mcvConsultingSlotTime12Minutes;
        mConsultingSlot12MinutesPriceText = mFragmentBinding.mtvConsultingSlot12MinutesPrice;

        mConsultingSlotTime25MinutesCard = mFragmentBinding.mcvConsultingSlotTime25Minutes;
        mConsultingSlot25MinutesPriceText = mFragmentBinding.mtvConsultingSlot25MinutesPrice;

        mConsultingSlotTime50MinutesCard = mFragmentBinding.mcvConsultingSlotTime50Minutes;
        mConsultingSlot50MinutesPriceText = mFragmentBinding.mtvConsultingSlot50MinutesPrice;
        mbtnReviewUpcomingChange=mFragmentBinding.mbtnReviewUpcomingChange;

        mViewModel.getExpert().observe(getViewLifecycleOwner(), expert -> {
            ConsultingSlot consultingSlot = expert.getConsultingSlot();

            ConsultingSlotDay weekdays = consultingSlot.getWeekdays();
            if (expert.ismIsFuturePriceSlotTime()) {
                mbtnReviewUpcomingChange.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                         new CustomAlertReviewUpcomingChanges(getActivity(), expert.getmFuturePriceSlotTimeDate(), expert.getmFutureConsultingSlotPrice(),
                                    expert.getmFutureConsultingSlot()).show();

                    }
                });
            }else
                mbtnReviewUpcomingChange.setVisibility(View.GONE);

            if (!TextUtils.isEmpty(weekdays.getFrom1())) {
                mConsultingSlotWeekdaysLayout.setVisibility(View.VISIBLE);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(convert24HoursTo12Hours(weekdays.getFrom1()));
                stringBuilder.append(" to ");
                stringBuilder.append(convert24HoursTo12Hours(weekdays.getTo1()));
                if (!TextUtils.isEmpty(weekdays.getFrom2())) {
                    stringBuilder.append("\n");
                    stringBuilder.append(convert24HoursTo12Hours(weekdays.getFrom2()));
                    stringBuilder.append(" to ");
                    stringBuilder.append(convert24HoursTo12Hours(weekdays.getTo2()));
            }
                mConsultingSlotWeekdaysDurationText.setText(stringBuilder);
            }

            ConsultingSlotDay saturday = consultingSlot.getSaturday();
            if (!TextUtils.isEmpty(saturday.getFrom1())) {
                mConsultingSlotSaturdayLayout.setVisibility(View.VISIBLE);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(convert24HoursTo12Hours(saturday.getFrom1()));
                stringBuilder.append(" to ");
                stringBuilder.append(convert24HoursTo12Hours(saturday.getTo1()));
                if (!TextUtils.isEmpty(saturday.getFrom2())) {
                    stringBuilder.append("\n");
                    stringBuilder.append(convert24HoursTo12Hours(saturday.getFrom2()));
                    stringBuilder.append(" to ");
                    stringBuilder.append(convert24HoursTo12Hours(saturday.getTo2()));
                }
                mConsultingSlotSaturdayDurationText.setText(stringBuilder);
            }

            ConsultingSlotDay sunday = consultingSlot.getSunday();
            if (!TextUtils.isEmpty(sunday.getFrom1())) {
                mConsultingSlotSundayLayout.setVisibility(View.VISIBLE);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(convert24HoursTo12Hours(sunday.getFrom1()));
                stringBuilder.append(" to ");
                stringBuilder.append(convert24HoursTo12Hours(sunday.getTo1()));
                if (!TextUtils.isEmpty(sunday.getFrom2())) {
                    stringBuilder.append("\n");
                    stringBuilder.append(convert24HoursTo12Hours(sunday.getFrom2()));
                    stringBuilder.append(" to ");
                    stringBuilder.append(convert24HoursTo12Hours(sunday.getTo2()));
                }
                mConsultingSlotSundayDurationText.setText(stringBuilder);
            }

            try {
                String expertServiceStartDateString = expert.getExpertServiceStartDate();
//                expertServiceStartDateString = "2020-06-05T00:00:00.000Z";
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
                SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
                Date date = inputFormat.parse(expertServiceStartDateString);
                Date currentDate = Calendar.getInstance().getTime();
                if (currentDate.before(date)) {
                    mServiceStartDateLayout.setVisibility(View.VISIBLE);
                    String formattedDate = outputFormat.format(date);
                    mServiceStartDateText.setText(formattedDate);
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }


            ConsultingSlotDuration consultingSlotDuration = expert.getConsultingSlotDuration();
//            mConsultingSlotTime12MinutesCard.setVisibility(consultingSlotDuration.getDuration12() ? View.VISIBLE : View.GONE);
//            mConsultingSlotTime25MinutesCard.setVisibility(consultingSlotDuration.getDuration25() ? View.VISIBLE : View.GONE);
//            mConsultingSlotTime50MinutesCard.setVisibility(consultingSlotDuration.getDuration50() ? View.VISIBLE : View.GONE);

            UserInfoFetchResponse.ConsultingSlotPrice consultingSlotPrice = expert.getConsultingSlotPrice();


            if (consultingSlotDuration.getDuration12()) {
                mConsultingSlotTime12MinutesCard.setVisibility(View.VISIBLE);
                UserInfoFetchResponse.DurationPrice durationPrice = consultingSlotPrice.getDuration12();
                String currency=(mSharedPreferences.getInt(User.COUNTRYID, 0)==User.INDIACOUNTRYID? "INR "+ durationPrice.getInr():"INR "+durationPrice.getNri());

                mConsultingSlot12MinutesPriceText.setText(durationPrice.getStatus() ? currency : "NA");
            } else {
                mConsultingSlotTime12MinutesCard.setVisibility(View.GONE);
            }

            if (consultingSlotDuration.getDuration25()) {
                mConsultingSlotTime25MinutesCard.setVisibility(View.VISIBLE);
                UserInfoFetchResponse.DurationPrice durationPrice = consultingSlotPrice.getDuration25();
                String currency=(mSharedPreferences.getInt(User.COUNTRYID, 0)==User.INDIACOUNTRYID? "INR "+ durationPrice.getInr():"INR "+durationPrice.getNri());

                mConsultingSlot25MinutesPriceText.setText(durationPrice.getStatus() ? currency  : "NA");
            } else {
                mConsultingSlotTime25MinutesCard.setVisibility(View.GONE);
            }

            if (consultingSlotDuration.getDuration50()) {
                mConsultingSlotTime50MinutesCard.setVisibility(View.VISIBLE);
                UserInfoFetchResponse.DurationPrice durationPrice = consultingSlotPrice.getDuration50();
                String currency=(mSharedPreferences.getInt(User.COUNTRYID, 0)==User.INDIACOUNTRYID? "INR "+ durationPrice.getInr():"INR "+durationPrice.getNri());
                mConsultingSlot50MinutesPriceText.setText(durationPrice.getStatus() ? currency  : "NA");
            } else {
                mConsultingSlotTime50MinutesCard.setVisibility(View.GONE);
            }
        });
    }
}
