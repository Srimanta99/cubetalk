package com.cubetalktest.cubetalk.adapters.viewpager2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.cubetalktest.cubetalk.fragments.DemoObjectFragment;
import com.cubetalktest.cubetalk.fragments.ViewExpertDetailsExpertProfileFragment;
import com.cubetalktest.cubetalk.fragments.ViewExpertDetailsFeedbackFragment;
import com.cubetalktest.cubetalk.fragments.ViewExpertDetailsTimingsAndFeesFragment;

public class ExpertDetailsStateAdapter extends FragmentStateAdapter {

    public static final int EXPERT_PROFILE = 0;
    public static final int TIMINGS_AND_FEES = 1;
    public static final int FEEDBACK = 2;

    public ExpertDetailsStateAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case EXPERT_PROFILE:
                return ViewExpertDetailsExpertProfileFragment.newInstance("", "");

            case TIMINGS_AND_FEES:
                return ViewExpertDetailsTimingsAndFeesFragment.newInstance("", "");

            case FEEDBACK:
                return ViewExpertDetailsFeedbackFragment.newInstance("", "");

            default:
                // Return a NEW fragment instance in createFragment(int)
                Fragment fragment = new DemoObjectFragment();
                Bundle args = new Bundle();
                // Our object is just an integer :-P
                args.putInt(DemoObjectFragment.ARG_OBJECT, position + 1);
                fragment.setArguments(args);
                return fragment;
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
