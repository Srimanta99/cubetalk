package local.impactlife.cubetalk.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textview.MaterialTextView;

import org.jetbrains.annotations.NotNull;

import local.impactlife.cubetalk.databinding.FragmentViewExpertDetailsExpertProfileBinding;
import local.impactlife.cubetalk.viewmodels.ViewExpertDetailsViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewExpertDetailsExpertProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewExpertDetailsExpertProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ViewExpertDetailsViewModel mViewModel;
    private FragmentViewExpertDetailsExpertProfileBinding mFragmentBinding;
    private MaterialTextView mExpertProfessionalSummaryText;
    private MaterialTextView mExpertKeyAccomplishmentsText;

    public ViewExpertDetailsExpertProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExpertProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewExpertDetailsExpertProfileFragment newInstance(String param1, String param2) {
        ViewExpertDetailsExpertProfileFragment fragment = new ViewExpertDetailsExpertProfileFragment();
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
//        return inflater.inflate(R.layout.fragment_view_expert_details_expert_profile, container, false);
        mFragmentBinding = FragmentViewExpertDetailsExpertProfileBinding.inflate(inflater, container, false);
        return mFragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel = new ViewModelProvider(getActivity()).get(ViewExpertDetailsViewModel.class);

        mExpertProfessionalSummaryText = mFragmentBinding.mtvExpertProfessionalSummary;

        mExpertKeyAccomplishmentsText = mFragmentBinding.mtvExpertKeyAccomplishments;

        mViewModel.getExpert().observe(getViewLifecycleOwner(), expert -> {
            mExpertProfessionalSummaryText.setText(expert.getProfessionalSummary());
            mExpertKeyAccomplishmentsText.setText(expert.getKeyAccomplishment());
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
