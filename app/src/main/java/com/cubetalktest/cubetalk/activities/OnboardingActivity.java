package com.cubetalktest.cubetalk.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;

import com.cubetalktest.cubetalk.databinding.ActivityOnboardingBinding;

public class OnboardingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityOnboardingBinding mBinding = ActivityOnboardingBinding.inflate(LayoutInflater.from(this));
        setContentView(mBinding.getRoot());


    }

}
