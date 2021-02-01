package com.cubetalktest.cubetalk.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.common.collect.Collections2;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import com.cubetalktest.cubetalk.R;
import com.cubetalktest.cubetalk.activities.SpecialityDetailActivity;
import com.cubetalktest.cubetalk.adapters.recycler.PromotionBannersAdapter;
import com.cubetalktest.cubetalk.adapters.recycler.SpecialitiesAdapter;
import com.cubetalktest.cubetalk.models.PromotionBannerResponse;
import com.cubetalktest.cubetalk.models.SpecialityResponse;
import com.cubetalktest.cubetalk.models.User;
import com.cubetalktest.cubetalk.services.api.HomeService;
import com.cubetalktest.cubetalk.services.api.ServiceBuilder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment
        extends Fragment
        implements SpecialitiesAdapter.OnSpecialityListener {

    public static final String SPECIALITY = "Speciality";

    private static final String TAG = HomeFragment.class.getSimpleName();
    private HomeViewModel homeViewModel;

    private ArrayList<PromotionBannerResponse.Data> mPromotionBanners;
    private ArrayList<SpecialityResponse.Data> mSpecialities;

    private PromotionBannersAdapter mPromotionBannersAdapter;
    private SpecialitiesAdapter mSpecialitiesAdapter;
    private TextView mUserNameText;
    private String mUserName;
    private SharedPreferences mSharedPreferences;
    private String mUserProfileImageUrl;
    private ImageView mUserProfileImageView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
//        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        mSharedPreferences = getActivity().getSharedPreferences(User.PREFERENCE_NAME, Context.MODE_PRIVATE);

        mUserProfileImageUrl = mSharedPreferences.getString(User.PROFILE_IMAGE, "");
        mUserName = mSharedPreferences.getString(User.NAME, "");

        mUserProfileImageView = root.findViewById(R.id.civ_user_image);
        mUserNameText = root.findViewById(R.id.tv_user_name);

        if (!TextUtils.isEmpty(mUserProfileImageUrl)) {
            Glide.with(this)
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .load(mUserProfileImageUrl)
                    .placeholder(R.drawable.ic_account_circle_white_24dp)
                    .circleCrop()
                    .into(mUserProfileImageView);
        }
        mUserNameText.setText("Welcome " + mUserName.split(" ")[0]);

        mPromotionBanners = new ArrayList<>();
        mSpecialities = new ArrayList<>();

        mPromotionBannersAdapter = new PromotionBannersAdapter(mPromotionBanners);

        RecyclerView mPromotionBannersRecycler = root.findViewById(R.id.rv_promotion_banners);
        mPromotionBannersRecycler.setAdapter(mPromotionBannersAdapter);
        mPromotionBannersRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));

        RecyclerView mSpecialitiesRecyclerView = root.findViewById(R.id.rv_specialities);
        mSpecialitiesRecyclerView.setHasFixedSize(true);
        mSpecialitiesRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        mSpecialitiesAdapter = new SpecialitiesAdapter(this, mSpecialities);
        mSpecialitiesRecyclerView.setAdapter(mSpecialitiesAdapter);
//        SectionedGridRecyclerViewAdapter mSectionedAdapter = new
//                SectionedGridRecyclerViewAdapter(getActivity(), R.layout.section, R.id.section_text, mSpecialitiesRecyclerView, mSpecialitiesAdapter);
//        mSpecialitiesRecyclerView.setAdapter(mSectionedAdapter);


        HomeService homeService = ServiceBuilder.buildService(HomeService.class);

        Call<PromotionBannerResponse> createPromotionBanner = homeService.getPromotionBanner( mSharedPreferences.getString(User.TOKEN, ""));
        Call<SpecialityResponse> createSpecialityRequest = homeService.getAllSpecialities( mSharedPreferences.getString(User.TOKEN, ""));

        createPromotionBanner.enqueue(new Callback<PromotionBannerResponse>() {
            @Override
            public void onResponse(@NotNull Call<PromotionBannerResponse> call, @NotNull Response<PromotionBannerResponse> response) {
                Log.d(TAG, "onResponse: response.message(): " + response.message());

                if (response.body() != null) {
                    mPromotionBanners.clear();
                    if (response.body().getData()!=null) {
                        mPromotionBanners.addAll(response.body().getData());
                        mPromotionBannersAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<PromotionBannerResponse> call, @NotNull Throwable throwable) {
                Log.d(TAG, "onFailure: t.getMessage(): " + throwable.getMessage());
            }
        });

        createSpecialityRequest.enqueue(new Callback<SpecialityResponse>() {
            @Override
            public void onResponse(@NotNull Call<SpecialityResponse> call, @NotNull Response<SpecialityResponse> response) {
                Log.d(TAG, "onResponse: response.message(): " + response.message());
                if (response.isSuccessful()) {
                    SpecialityResponse specialityResponse = response.body();
                    ArrayList<SpecialityResponse.Data> specialities = specialityResponse.getData();

                    mSpecialities.clear();
                    if(specialities!=null)
                    mSpecialities.addAll(Collections2.filter(specialities, speciality -> speciality.getIsTopicExists() == 1));
                    mSpecialitiesAdapter.notifyDataSetChanged();

//                    List<SectionedGridRecyclerViewAdapter.Section> sections = new ArrayList<>();
//                    for (int i = 0; i <= mSpecialities.size(); ++i) {
//                        if (i % 2 == 0)
//                            sections.add(new SectionedGridRecyclerViewAdapter.Section(i, ""));
//                    }
//                    SectionedGridRecyclerViewAdapter.Section[] dummy = new SectionedGridRecyclerViewAdapter.Section[sections.size()];
//                    mSectionedAdapter.setSections(sections.toArray(dummy));

                    root.findViewById(R.id.ll_specialities).setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(@NotNull Call<SpecialityResponse> call, @NotNull Throwable throwable) {
                Log.d(TAG, "onFailure: ");
                Toast.makeText(getActivity(), "Check Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });


        return root;
    }

    @Override
    public void onSpecialityClick(int position) {
        SpecialityResponse.Data speciality = mSpecialities.get(position);
        Intent intent = new Intent(getContext(), SpecialityDetailActivity.class);
        intent.putExtra(SPECIALITY, speciality);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();

        mUserProfileImageUrl = mSharedPreferences.getString(User.PROFILE_IMAGE, "");
        mUserName = mSharedPreferences.getString(User.NAME, "");

        if (!TextUtils.isEmpty(mUserProfileImageUrl)) {
            Glide.with(this)
                    .asBitmap()
                    .load(mUserProfileImageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .circleCrop()
                    .placeholder(R.drawable.ic_account_circle_white_24dp)
                    .into(mUserProfileImageView);
        }
        mUserNameText.setText("Welcome " + mUserName.split(" ")[0]);
    }
}