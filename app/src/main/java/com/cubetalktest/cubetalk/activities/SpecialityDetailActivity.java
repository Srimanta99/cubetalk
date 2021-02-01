package com.cubetalktest.cubetalk.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.radiobutton.MaterialRadioButton;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cubetalktest.cubetalk.ItemClickSupport;
import com.cubetalktest.cubetalk.R;
import com.cubetalktest.cubetalk.adapters.recycler.PromotionBannersAdapter;
import com.cubetalktest.cubetalk.adapters.recycler.RelatedArticlesAdapter;
import com.cubetalktest.cubetalk.adapters.recycler.RelatedVideosAdapter;
import com.cubetalktest.cubetalk.databinding.ActivitySpecialityDetailBinding;
import com.cubetalktest.cubetalk.databinding.ContentSpecialityDetailBinding;
import com.cubetalktest.cubetalk.models.PromotionBannerResponse;
import com.cubetalktest.cubetalk.models.SpecialityArticleResponse;
import com.cubetalktest.cubetalk.models.SpecialityResponse;
import com.cubetalktest.cubetalk.models.SpecialityTopicResponse;
import com.cubetalktest.cubetalk.models.SpecialityVideoResponse;
import com.cubetalktest.cubetalk.models.User;
import com.cubetalktest.cubetalk.services.api.ServiceBuilder;
import com.cubetalktest.cubetalk.services.api.SpecialityService;
import com.cubetalktest.cubetalk.ui.home.HomeFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SpecialityDetailActivity
        extends AppCompatActivity
        implements CompoundButton.OnCheckedChangeListener {

    private static final String TAG = SpecialityDetailActivity.class.getSimpleName();
    public static final String SELECTED_TOPIC_ID = "SelectedTopicId";
    public static final String SELECTED_TOPIC_NAME = "SelectedTopicName";

    private SpecialityResponse.Data mSpeciality;
    private ArrayList<PromotionBannerResponse.Data> mPromotionBanners;
    private ArrayList<SpecialityArticleResponse.Article> mRelatedArticles;
    private ArrayList<SpecialityVideoResponse.Video> mRelatedVideos;
    private RecyclerView mPromotionBannersRecycler;
    private RecyclerView mRelatedArticlesRecycler;
    private RecyclerView mRelatedVideosRecycler;
    private PromotionBannersAdapter mPromotionBannersAdapter;
    private RelatedArticlesAdapter mRelatedArticlesAdapter;
    private RelatedVideosAdapter mRelatedVideosAdapter;
    private String mSpecialityId;
    //    private ArrayList<SpecialityTopicResponse.Data> mSpecialityTopics;
//    private SpecialityTopicsAdapter mSpecialityTopicsAdapter;
//    private RecyclerView mSpecialityTopicsRecycler;
    private View mLoadingLayout;
    private ActivitySpecialityDetailBinding mActivityBinding;
    private RadioGroup mSpecialityTopicRadioGroup;
    private String mSelectedTopicId;
    private String mSelectedTopicName;
    private ContentSpecialityDetailBinding mContentBinding;
    private MaterialButton mOpenExpertsByTopic;
    private LinearLayout mRelatedArticlesLinearLayout;
    private LinearLayout mRelatedVideosLinearLayout;
    private Intent mIntent;
    private SpecialityService mSpecialityService;
    private SharedPreferences mSharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_speciality_detail);

        mActivityBinding = ActivitySpecialityDetailBinding.inflate(LayoutInflater.from(this));
        setContentView(mActivityBinding.getRoot());
        mSharedPreferences = getSharedPreferences(User.PREFERENCE_NAME, Context.MODE_PRIVATE);
        Toolbar toolbar = mActivityBinding.toolbar;
        setSupportActionBar(toolbar);

        mIntent = getIntent();
        if (!mIntent.hasExtra(HomeFragment.SPECIALITY)) {
            Toast.makeText(this, "SpecialityId Missing", Toast.LENGTH_LONG).show();
            finish();
        }
        mSpeciality = mIntent.getParcelableExtra(HomeFragment.SPECIALITY);
        mSpecialityId = mSpeciality.getId();
//        Toast.makeText(this, mSpecialityId, Toast.LENGTH_LONG).show();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(mSpeciality.getName());
        }

        mContentBinding = mActivityBinding.contentSpecialityDetail;

        mLoadingLayout = mActivityBinding.loading.getRoot();

        mPromotionBannersRecycler = mContentBinding.rvSpecialityDetailPromotionBanners;
        mRelatedArticlesLinearLayout = mContentBinding.llRelatedArticles;
        mRelatedArticlesRecycler = mContentBinding.rvSpecialityDetailRelatedArticles;
        mRelatedVideosLinearLayout = mContentBinding.llRelatedVideos;
        mRelatedVideosRecycler = mContentBinding.rvSpecialityDetailRelatedVideos;

        mPromotionBanners = new ArrayList<>();
        mPromotionBannersAdapter = new PromotionBannersAdapter(mPromotionBanners);
        mPromotionBannersRecycler.setAdapter(mPromotionBannersAdapter);

        mRelatedArticles = new ArrayList<>();
        mRelatedArticlesAdapter = new RelatedArticlesAdapter(mRelatedArticles);
        mRelatedArticlesRecycler.setAdapter(mRelatedArticlesAdapter);
        ItemClickSupport.addTo(mRelatedArticlesRecycler).setOnItemClickListener((recyclerView, position, v) -> {
            SpecialityArticleResponse.Article article = mRelatedArticles.get(position);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(article.getImage()));
            startActivity(intent);
        });

        mRelatedVideos = new ArrayList<>();
        mRelatedVideosAdapter = new RelatedVideosAdapter(mRelatedVideos);
        mRelatedVideosRecycler.setAdapter(mRelatedVideosAdapter);
        ItemClickSupport.addTo(mRelatedVideosRecycler).setOnItemClickListener((recyclerView, position, v) -> {
            SpecialityVideoResponse.Video video = mRelatedVideos.get(position);
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(video.getUrl()));

//            Intent intent = YouTubeStandalonePlayer.createVideoIntent(
//                    SpecialityDetailActivity.this,
//                    "AIzaSyAhJ7k7tHoXidDPK3Rpb35lLGNhaFB0UpU",
//                    getYoutubeVideoId(video.getUrl())
//            );

            Intent intent = new Intent(SpecialityDetailActivity.this, CustomYouTubeVideoPlayerActivity.class);
            intent.putExtra("YouTubeVideoId", getYoutubeVideoId(video.getUrl()));
            startActivity(intent);


        });

        mSpecialityTopicRadioGroup = mContentBinding.rgSpecialityTopics;

        mSpecialityService = ServiceBuilder.buildService(SpecialityService.class);

        Call<SpecialityTopicResponse> specialityTopicResponse = mSpecialityService.getTopics(mSharedPreferences.getString(User.TOKEN, ""),mSpecialityId);
        specialityTopicResponse.enqueue(new Callback<SpecialityTopicResponse>() {
            @Override
            public void onResponse(@NotNull Call<SpecialityTopicResponse> call, @NotNull Response<SpecialityTopicResponse> response) {
                Log.d(TAG, "onResponse: response.toString(): " + response.toString());

                if (response.body() != null) {
                    SpecialityTopicResponse responseBody = response.body();

                    if (responseBody.getSuccess()) {
                        LayoutInflater layoutInflater = LayoutInflater.from(SpecialityDetailActivity.this);

                        for (SpecialityTopicResponse.Data data : responseBody.getData()) {
                            MaterialRadioButton radioButton = (MaterialRadioButton) layoutInflater.inflate(
                                    R.layout.item_radio_button_topic,
                                    mSpecialityTopicRadioGroup,
                                    false
                            );
                            radioButton.setText(data.getName());
                            radioButton.setTag(data.getId());
                            radioButton.setOnCheckedChangeListener(SpecialityDetailActivity.this);
                            mSpecialityTopicRadioGroup.addView(radioButton);
                        }
                        mLoadingLayout.setVisibility(View.GONE);

                        SpecialityTopicResponse.Data data = responseBody.getData().get(0);
                        if (data.getParentId().getIsDocumentActive() == 1) {
                            fetchSpecialityArticles();
                            fetchSpecialityVideos();
                        }

                    } else {
                        new MaterialAlertDialogBuilder(SpecialityDetailActivity.this)
                                .setTitle("Cube Talk")
                                .setMessage("The current speciality is disabled.")
                                .setPositiveButton("Dismiss", (dialog, which) -> {
                                    finish();
                                })
                                .show();
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<SpecialityTopicResponse> call, @NotNull Throwable throwable) {
                Log.d(TAG, "onFailure: ");
            }
        });


        mOpenExpertsByTopic = mContentBinding.btnOpenExpertsByTopic;
        mOpenExpertsByTopic.setOnClickListener(v -> {
            if (mSpecialityTopicRadioGroup.getCheckedRadioButtonId() == -1) {
                Toast.makeText(SpecialityDetailActivity.this, "Please select a topic", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent i = new Intent(SpecialityDetailActivity.this, ViewExpertsByTopicActivity.class);
            i.putExtra("SpecialityId", mSpecialityId);
            i.putExtra(SELECTED_TOPIC_ID, mSelectedTopicId);
            i.putExtra(SELECTED_TOPIC_NAME, mSelectedTopicName);
            startActivity(i);
        });
    }

    private void fetchSpecialityArticles() {
        Call<SpecialityArticleResponse> specialityArticleResponse = mSpecialityService.getArticles(mSharedPreferences.getString(User.TOKEN, ""),"all");
        specialityArticleResponse.enqueue(new Callback<SpecialityArticleResponse>() {
            @Override
            public void onResponse(@NotNull Call<SpecialityArticleResponse> call, @NotNull Response<SpecialityArticleResponse> response) {
                SpecialityArticleResponse responseBody = response.body();
                if (responseBody.getSuccess()) {
                    mRelatedArticles.clear();
                    mRelatedArticles.addAll(responseBody.getArticles());
                    mRelatedArticlesAdapter.notifyDataSetChanged();

                    mRelatedArticlesLinearLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(@NotNull Call<SpecialityArticleResponse> call, @NotNull Throwable throwable) {

            }
        });
    }

    private void fetchSpecialityVideos() {
        Call<SpecialityVideoResponse> specialityVideoResponse = mSpecialityService.getVideos(mSharedPreferences.getString(User.TOKEN, ""),"all");
        specialityVideoResponse.enqueue(new Callback<SpecialityVideoResponse>() {
            @Override
            public void onResponse(@NotNull Call<SpecialityVideoResponse> call, @NotNull Response<SpecialityVideoResponse> response) {
                SpecialityVideoResponse responseBody = response.body();
                if (responseBody.getSuccess()) {
                    mRelatedVideos.clear();
                    mRelatedVideos.addAll(responseBody.getVideos());
                    mRelatedVideosAdapter.notifyDataSetChanged();

                    mRelatedVideosLinearLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(@NotNull Call<SpecialityVideoResponse> call, @NotNull Throwable throwable) {

            }
        });

    }

    private String getYoutubeVideoId(String ytUrl) {
        String pattern = "(?<=youtu.be/|watch\\?v=|/videos/|embed/)[^#&?]*";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(ytUrl);
        if(matcher.find()){
            return matcher.group();
        }
        return "";
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {

            mSelectedTopicId = String.valueOf(buttonView.getTag());
            mSelectedTopicName = String.valueOf(buttonView.getText());
        }
    }
}
