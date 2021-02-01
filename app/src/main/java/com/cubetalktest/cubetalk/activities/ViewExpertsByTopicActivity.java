package com.cubetalktest.cubetalk.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import com.cubetalktest.cubetalk.adapters.recycler.ExpertsByTopicAdapter;
import com.cubetalktest.cubetalk.databinding.ActivityExpertsByTopicBinding;
import com.cubetalktest.cubetalk.databinding.ContentExpertsByTopicBinding;
import com.cubetalktest.cubetalk.models.User;
import com.cubetalktest.cubetalk.models.common.Expert;
import com.cubetalktest.cubetalk.models.get_experts_by_topics.ExpertsByTopicResponse;
import com.cubetalktest.cubetalk.services.api.ServiceBuilder;
import com.cubetalktest.cubetalk.services.api.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewExpertsByTopicActivity
        extends AppCompatActivity
        implements ExpertsByTopicAdapter.OnExpertByTopicsListener {


    private static final String TAG = ViewExpertsByTopicActivity.class.getSimpleName();

    private ActivityExpertsByTopicBinding mActivityBinding;
    private String mTopicId;
    private String mTopicName;
    private ArrayList<Expert> mExpertsByTopic;
    private ExpertsByTopicAdapter mExpertsByTopicAdapter;
    private RecyclerView mExpertsByTopicRecycler;
    private ContentExpertsByTopicBinding mContentBinding;
    private String mUserId;
    private String mSpecialityId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_experts_by_topic);

        SharedPreferences mSharedPreferences = getSharedPreferences(User.PREFERENCE_NAME, MODE_PRIVATE);
        mUserId = mSharedPreferences.getString(User.ID, "");

        mActivityBinding = ActivityExpertsByTopicBinding.inflate(LayoutInflater.from(this));
        setContentView(mActivityBinding.getRoot());

        Intent intent = getIntent();
        if (intent.hasExtra(SpecialityDetailActivity.SELECTED_TOPIC_ID)) {
            mSpecialityId = intent.getStringExtra("SpecialityId");
            mTopicId = intent.getStringExtra(SpecialityDetailActivity.SELECTED_TOPIC_ID);
            mTopicName = intent.getStringExtra(SpecialityDetailActivity.SELECTED_TOPIC_NAME);

            Toolbar toolbar = mActivityBinding.toolbar;
            setSupportActionBar(toolbar);

            ActionBar actionBar = getSupportActionBar();

            if (actionBar != null) {
                actionBar.setTitle(mTopicName);
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
        } else {
            finish();
        }

        mContentBinding = mActivityBinding.contentExpertsByTopic;

        mExpertsByTopic = new ArrayList<>();
        mExpertsByTopicAdapter = new ExpertsByTopicAdapter(this, mExpertsByTopic);
        mExpertsByTopicRecycler = mContentBinding.rvExpertsByTopic;
        mExpertsByTopicRecycler.setAdapter(mExpertsByTopicAdapter);

        UserService userService = ServiceBuilder.buildService(UserService.class);
        Call<ExpertsByTopicResponse> expertsByTopicResponse = userService.getExpertsByTopics(mSharedPreferences.getString(User.TOKEN, ""),mTopicId);

        expertsByTopicResponse.enqueue(new Callback<ExpertsByTopicResponse>() {
            @Override
            public void onResponse(@NotNull Call<ExpertsByTopicResponse> call, @NotNull Response<ExpertsByTopicResponse> response) {
                Log.d(TAG, "onResponse: ");

                if (response.body() != null) {
                    ExpertsByTopicResponse responseBody = response.body();
                    if (responseBody.getSuccess()) {
                        if (responseBody.getExperts().size() == 0) {
                            Toast.makeText(ViewExpertsByTopicActivity.this, "No Experts found", Toast.LENGTH_SHORT).show();
                        } else {
                            mExpertsByTopic.clear();
                            mExpertsByTopic.addAll(responseBody.getExperts());
                            mExpertsByTopicAdapter.notifyDataSetChanged();
                        }
                    } else {
                        //Toast.makeText(ExpertsByTopicActivity.this, responseBody.getError(), Toast.LENGTH_SHORT).show();
                        new MaterialAlertDialogBuilder(ViewExpertsByTopicActivity.this)
                                .setTitle("Cube Talk")
                                .setMessage(responseBody.getError())
                                .setCancelable(false)
                                .setPositiveButton("Dismiss", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                })
                                .show();

                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<ExpertsByTopicResponse> call, @NotNull Throwable throwable) {
                Log.d(TAG, "onFailure: ");
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

    @Override
    public void onExpertViewProfileClick(int position) {
        Expert expert = mExpertsByTopic.get(position);

        Intent intent = new Intent(this, ViewExpertDetailsActivity.class);
        intent.putExtra("ExpertId", expert.getId());
        intent.putExtra("SpecialityId", mSpecialityId);
        intent.putExtra("TopicId", mTopicId);
        intent.putExtra("TopicName", mTopicName);
        startActivity(intent);
    }

    @Override
    public void onExpertBookNowClick(int position) {
        Expert expert = mExpertsByTopic.get(position);

        String expertId = expert.getId();

        if (TextUtils.equals(mUserId, expertId)) {
            new MaterialAlertDialogBuilder(this)
                    .setTitle("Cube Talk")
                    .setMessage("Booking not possible. User is selected Expert.")
                    .setPositiveButton("Dismiss", null)
                    .show();
        } else{
            Intent intent = new Intent(this, BookExpertActivity.class);
            intent.putExtra("ExpertId", expertId);
            intent.putExtra("SpecialityId", mSpecialityId);
            intent.putExtra("TopicId", mTopicId);
            intent.putExtra("TopicName", mTopicName);
            startActivity(intent);
        }
    }


}
