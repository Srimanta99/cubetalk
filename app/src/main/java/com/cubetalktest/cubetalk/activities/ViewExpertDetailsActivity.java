package com.cubetalktest.cubetalk.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

import com.cubetalktest.cubetalk.R;
import com.cubetalktest.cubetalk.adapters.viewpager2.ExpertDetailsStateAdapter;
import com.cubetalktest.cubetalk.databinding.ActivityViewExpertDetailsBinding;
import com.cubetalktest.cubetalk.databinding.ContentViewExpertDetailsBinding;
import com.cubetalktest.cubetalk.models.User;
import com.cubetalktest.cubetalk.models.common.SpecialityTopic;
import com.cubetalktest.cubetalk.models.common.Topic;
import com.cubetalktest.cubetalk.viewmodels.ViewExpertDetailsViewModel;

import static com.cubetalktest.cubetalk.adapters.viewpager2.ExpertDetailsStateAdapter.EXPERT_PROFILE;
import static com.cubetalktest.cubetalk.adapters.viewpager2.ExpertDetailsStateAdapter.FEEDBACK;
import static com.cubetalktest.cubetalk.adapters.viewpager2.ExpertDetailsStateAdapter.TIMINGS_AND_FEES;

public class ViewExpertDetailsActivity extends AppCompatActivity {

    private static final String TAG = ViewExpertDetailsActivity.class.getSimpleName();

    private ActivityViewExpertDetailsBinding mActivityBinding;
    private ContentViewExpertDetailsBinding mContentBinding;
    private String mUserId;
    private String mExpertId;
    private ViewExpertDetailsViewModel mViewModel;
    private ImageView mExpertProfileImage;
    private MaterialTextView mExpertNameText;
    private MaterialTextView mExpertSpecialitiesText;
    private MaterialTextView mExpertYearsOfExperienceText;
    private MaterialTextView mExpertRatingText;
    private TabLayout mExpertDetailTabLayout;
    private ExpertDetailsStateAdapter mExpertDetailsStateAdapter;
    private ViewPager2 mExpertDetailsViewPager;
    private MaterialButton mBookNowButton;
    private String mSpecialityId;
    private String mTopicId;
    private String mTopicName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_view_expert_details);

        SharedPreferences mSharedPreferences = getSharedPreferences(User.PREFERENCE_NAME, MODE_PRIVATE);
        mUserId = mSharedPreferences.getString(User.ID, "");

        mActivityBinding = ActivityViewExpertDetailsBinding.inflate(LayoutInflater.from(this));
        setContentView(mActivityBinding.getRoot());

        Toolbar toolbar = mActivityBinding.toolbar;
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Expert Selection");
        }

        Intent mIntent = getIntent();
        mExpertId = mIntent.getStringExtra("ExpertId");
        mSpecialityId = mIntent.getStringExtra("SpecialityId");
        mTopicId = mIntent.getStringExtra("TopicId");
        mTopicName = mIntent.getStringExtra("TopicName");

        mContentBinding = mActivityBinding.contentViewExpertDetails;

        mViewModel = new ViewModelProvider(this).get(ViewExpertDetailsViewModel.class);

        mExpertProfileImage = mContentBinding.ivExpertProfileImage;

        mExpertNameText = mContentBinding.mtvExpertName;

        mExpertSpecialitiesText = mContentBinding.mtvExpertSpecialities;

        mExpertYearsOfExperienceText = mContentBinding.mtvExpertYearsOfExperience;

        mExpertRatingText = mContentBinding.mtvExpertRating;

        mExpertDetailsStateAdapter = new ExpertDetailsStateAdapter(this);

        mExpertDetailTabLayout = mContentBinding.tlExpertDetails;

        mExpertDetailsViewPager = mContentBinding.vp2ExpertDetails;

        mExpertDetailsViewPager.setAdapter(mExpertDetailsStateAdapter);

        new TabLayoutMediator(
                mExpertDetailTabLayout,
                mExpertDetailsViewPager,
                (tab, position) -> {
                    switch (position) {
                        case EXPERT_PROFILE:
                            tab.setText("Expert Profile");
                            break;

                        case TIMINGS_AND_FEES:
                            tab.setText("Timings and Fees");
                            break;

                        case FEEDBACK:
                            tab.setText("Feedback");
                            break;
                    }
                }
        ).attach();

        mBookNowButton = mContentBinding.mbtnBookNow;

        mViewModel.fetchExpertDetails(mExpertId,mSharedPreferences.getString(User.TOKEN, ""));

        mViewModel.getExpert().observe(this, expert -> {
            Glide.with(this)
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .circleCrop()
                    .placeholder(R.drawable.ic_account_circle_white_128dp)
                    .load(expert.getProfileImage())
                    .into(mExpertProfileImage);

            mExpertNameText.setText(expert.getName());

            ArrayList<String> specialityNames = new ArrayList<>();
            ArrayList<String> topicNames = new ArrayList<>();
            for (SpecialityTopic specialityTopic : expert.getSpecialityTopics()) {
                specialityNames.add(specialityTopic.getSpeciality().getName());
                for (Topic topic : specialityTopic.getTopics()) {
                    topicNames.add(topic.getName());
                }
            }

            mExpertSpecialitiesText.setText(TextUtils.join(", ", specialityNames));

            mExpertYearsOfExperienceText.setText(expert.getYearsOfExperience() + " years of Experience");
        });

        mBookNowButton.setOnClickListener(view -> {
            if (TextUtils.equals(mUserId, mExpertId)) {
                new MaterialAlertDialogBuilder(this)
                        .setTitle("Cube Talk")
                        .setMessage("Booking not possible. User is selected Expert.")
                        .setPositiveButton("Dismiss", null)
                        .show();
            } else{
                Intent intent = new Intent(this, BookExpertActivity.class);
                intent.putExtra("ExpertId", mExpertId);
                intent.putExtra("SpecialityId", mSpecialityId);
                intent.putExtra("TopicId", mTopicId);
                intent.putExtra("TopicName", mTopicName);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
