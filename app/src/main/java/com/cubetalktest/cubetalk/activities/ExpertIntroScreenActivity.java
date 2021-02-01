package com.cubetalktest.cubetalk.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.button.MaterialButton;

import com.cubetalktest.cubetalk.databinding.ActivityExpertIntroScreenBinding;
import com.cubetalktest.cubetalk.databinding.ContentExpertIntroScreenBinding;
import com.cubetalktest.cubetalk.models.User;

public class ExpertIntroScreenActivity
        extends AppCompatActivity {

    private static final String TAG = ExpertIntroScreenActivity.class.getSimpleName();

    private static final int RC_SIGN_UP_AS_EXPERT = 1000;

    private SharedPreferences mSharedPreferences;

    private MaterialButton mContinueButton;
    private ActivityExpertIntroScreenBinding mActivityBinding;
    private ContentExpertIntroScreenBinding mContentBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_expert_intro_screen);

        mActivityBinding = ActivityExpertIntroScreenBinding.inflate(LayoutInflater.from(this));
        setContentView(mActivityBinding.getRoot());

        mSharedPreferences = getSharedPreferences(User.PREFERENCE_NAME, MODE_PRIVATE);

        Toolbar toolbar = mActivityBinding.toolbar;
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("");
        }

        mContentBinding = mActivityBinding.contentExpertIntroScreen;

        mContinueButton = mContentBinding.btnContinue;
        mContinueButton.setOnClickListener(v -> {
            startActivity(new Intent(this, ExpertRegistrationStepOneActivity.class));
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }
}
