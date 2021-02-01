package com.cubetalktest.cubetalk.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.button.MaterialButton;

import com.cubetalktest.cubetalk.R;
import com.cubetalktest.cubetalk.databinding.ActivityIntroScreenBinding;
import com.cubetalktest.cubetalk.databinding.ContentIntroScreenBinding;

import static com.cubetalktest.cubetalk.utils.Utils.isConnectedToInternet;

public class IntroScreenActivity
        extends AppCompatActivity {

    private static final String TAG = IntroScreenActivity.class.getSimpleName();

    private static final int RC_SIGN_UP = 1001;
    private static final int RC_LOGIN = 1002;
    private static final int RC_FORGOT_PASSWORD = 1003;

    private TextView[] dots;
    private int[] layouts;
    private IntroScreenViewsSliderAdapter mAdapter;
    private ViewPager2 mIntroScreenViewPager;

    ViewPager2.OnPageChangeCallback mPageChangeCallback = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            addBottomDots(position);
            Log.d(TAG, "onPageSelected: ");
        }
    };
    private LinearLayout mViewpagerDotsLayout;
    private MaterialButton mSignUpButton;
    private MaterialButton mLoginButton;
    private ActivityIntroScreenBinding mActivityBinding;
    private ContentIntroScreenBinding mContentBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_intro_screen);

        Intent intent = getIntent();
        if (intent.hasExtra("OpenActivity")) {
            String openActivity = intent.getStringExtra("OpenActivity");

            if (TextUtils.equals(openActivity, LoginActivity.TAG)) {
                openLoginActivity();
            } else if (TextUtils.equals(openActivity, ForgotPasswordActivity.TAG)) {
                openForgotPasswordActivity();
            }
        }

        mActivityBinding = ActivityIntroScreenBinding.inflate(LayoutInflater.from(this));
        setContentView(mActivityBinding.getRoot());

        mContentBinding = mActivityBinding.contentIntroScreen;

        mIntroScreenViewPager = mContentBinding.vp2IntroScreen;
        mViewpagerDotsLayout = mContentBinding.viewpagerDots;
        mSignUpButton = mContentBinding.btnRegister;
        mLoginButton = mContentBinding.btnLogin;

        layouts = new int[]{
                R.layout.layout_slide_one,
                R.layout.layout_slide_one,
                R.layout.layout_slide_one};

        mAdapter = new IntroScreenViewsSliderAdapter();

        mIntroScreenViewPager.setAdapter(mAdapter);
        mIntroScreenViewPager.registerOnPageChangeCallback(mPageChangeCallback);
        mSignUpButton.setOnClickListener(v -> openRegisterActivity());
        mLoginButton.setOnClickListener(v -> openLoginActivity());


//        layouts = new ArrayList<>();
//        layouts.add(R.layout.layout_slide_one);
//        layouts.add(R.layout.layout_slide_one);
//        layouts.add(R.layout.layout_slide_one);

        // adding bottom dots
        addBottomDots(0);
        mIntroScreenViewPager.setCurrentItem(0);
    }

    private void openRegisterActivity() {
        if (isConnectedToInternet(this)) {
            startActivityForResult(new Intent(this, UserRegistrationActivity.class), RC_SIGN_UP);
        } else {
            Toast.makeText(this, "Not connected to internet.", Toast.LENGTH_SHORT).show();
        }
    }

    private void openLoginActivity() {
        if (isConnectedToInternet(this)) {
            startActivityForResult(new Intent(this, LoginActivity.class), RC_LOGIN);
        } else {
            Toast.makeText(this, "Not connected to internet.", Toast.LENGTH_SHORT).show();
        }
    }

    private void openForgotPasswordActivity() {
        if (isConnectedToInternet(this)) {
            startActivityForResult(new Intent(this, ForgotPasswordActivity.class), RC_FORGOT_PASSWORD);
        } else {
            Toast.makeText(this, "Not connected to internet.", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RC_SIGN_UP:
                if (resultCode == RESULT_OK) {
                    openLoginActivity();
                }
                break;

            case RC_LOGIN:
                if (resultCode == RESULT_OK) {
                    startActivity(new Intent(this, HomeActivity.class));
                    finish();
                } else if (resultCode == RESULT_CANCELED) {
                    if (data != null) {
                        String openActivity = data.getStringExtra("OpenActivity");
                        if (TextUtils.equals(openActivity, UserRegistrationActivity.TAG)) {
                            openRegisterActivity();
                        } else if (TextUtils.equals(openActivity, ForgotPasswordActivity.TAG)) {
                            openForgotPasswordActivity();
                        }
                    }
                }
                break;

            case RC_FORGOT_PASSWORD:
                if (resultCode == RESULT_OK || resultCode == RESULT_CANCELED) {
                    if (data != null && data.hasExtra("OpenActivity")) {
                        if (TextUtils.equals(data.getStringExtra("OpenActivity"), LoginActivity.TAG))
                            openLoginActivity();

                    }
                }
        }
    }


    private class IntroScreenViewsSliderAdapter
            extends RecyclerView.Adapter<IntroScreenViewsSliderAdapter.SliderViewHolder> {

        @NonNull
        @Override
        public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
            return new SliderViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {

        }

        @Override
        public int getItemViewType(int position) {
            return layouts[position];
        }

        @Override
        public int getItemCount() {
            return layouts.length;
        }

        class SliderViewHolder extends RecyclerView.ViewHolder {

            SliderViewHolder(@NonNull View itemView) {
                super(itemView);
            }
        }

    }

    /*
     * Adds bottom dots indicator
     * */
    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        mViewpagerDotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; ++i) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            mViewpagerDotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }




}
