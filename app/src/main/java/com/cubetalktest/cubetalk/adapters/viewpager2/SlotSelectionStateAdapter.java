package com.cubetalktest.cubetalk.adapters.viewpager2;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.cubetalktest.cubetalk.fragments.TimeSlotsFragment;

public class SlotSelectionStateAdapter extends FragmentStateAdapter {

    public SlotSelectionStateAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return TimeSlotsFragment.newInstance("", "");
    }

    @Override
    public int getItemCount() {
        return 1;
    }
}
