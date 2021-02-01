package com.cubetalktest.cubetalk.adapters.viewpager2;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

import com.cubetalktest.cubetalk.fragments.DynamicEarningStatisticsFragment;
import com.cubetalktest.cubetalk.models.earning_statics.GetMonthResponse;

public class ExpertEarningAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;
    List<GetMonthResponse.Datum> data;
    public ExpertEarningAdapter(FragmentManager fm, int NumOfTabs, List<GetMonthResponse.Datum> data) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.data=data;
    }
    @Override
    public Fragment getItem(int position) {
        return DynamicEarningStatisticsFragment.newInstance(data.get(position).getStatisticsDate());
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
