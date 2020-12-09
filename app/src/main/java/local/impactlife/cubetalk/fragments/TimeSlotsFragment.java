package local.impactlife.cubetalk.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.gridlayout.widget.GridLayout;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.radiobutton.MaterialRadioButton;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import local.impactlife.cubetalk.R;
import local.impactlife.cubetalk.RadioGridGroup;
import local.impactlife.cubetalk.activities.SlotSelectionActivity;
import local.impactlife.cubetalk.databinding.FragmentTimeSlotsBinding;
import local.impactlife.cubetalk.databinding.ItemTimeSlotBinding;
import local.impactlife.cubetalk.models.get_booking_time_slots.TimeSlotsResponse;
import local.impactlife.cubetalk.viewmodels.SlotSelectionViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TimeSlotsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TimeSlotsFragment extends Fragment {

    private static final String TAG = TimeSlotsFragment.class.getSimpleName();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FragmentTimeSlotsBinding mFragmentBinding;

    public TimeSlotsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TimeSlotsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TimeSlotsFragment newInstance(String param1, String param2) {
        TimeSlotsFragment fragment = new TimeSlotsFragment();
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
//        return inflater.inflate(R.layout.fragment_time_slots, container, false);
        mFragmentBinding = FragmentTimeSlotsBinding.inflate(inflater, container, false);
        return mFragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SlotSelectionViewModel mViewModel = new ViewModelProvider(getActivity()).get(SlotSelectionViewModel.class);

        LayoutInflater layoutInflater = LayoutInflater.from(view.getContext());

        RadioGridGroup mTimeSlotsRadioGridGroup = mFragmentBinding.rggTimeSlots;

        mViewModel.getTimeSlots().observe(getViewLifecycleOwner(), timeSlot -> {

            mTimeSlotsRadioGridGroup.removeAllViews();

            ArrayList<String> timeSlots = new ArrayList<>();

            TimeSlotsResponse.SlotWeekdays slotWeekdays = timeSlot.getSlotWeekdays();

            timeSlots.addAll(slotWeekdays.getFirstPhase());
            timeSlots.addAll(slotWeekdays.getSecondPhase());

            for (String timeSlotString : timeSlots) {

                MaterialRadioButton materialRadioButton = ItemTimeSlotBinding.inflate(layoutInflater).getRoot();

                materialRadioButton.setText(timeSlotString);
                GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(
                        GridLayout.spec(GridLayout.UNDEFINED, 1f),
                        GridLayout.spec(GridLayout.UNDEFINED, 1f)
                );

                layoutParams.width = 0;
                materialRadioButton.setLayoutParams(layoutParams);

                materialRadioButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MaterialRadioButton radioButton = (MaterialRadioButton) view;
                        mViewModel.setSelectedTimeSlot(radioButton.getText().toString());
                    }
                });
                mTimeSlotsRadioGridGroup.addView(materialRadioButton);
            }



        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}