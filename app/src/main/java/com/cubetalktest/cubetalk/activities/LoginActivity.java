package com.cubetalktest.cubetalk.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.iid.FirebaseInstanceId;

import org.jetbrains.annotations.NotNull;

import com.cubetalktest.cubetalk.databinding.ActivityLoginBinding;
import com.cubetalktest.cubetalk.databinding.ContentLoginBinding;
import com.cubetalktest.cubetalk.mesibo.MainApplication;
import com.cubetalktest.cubetalk.models.User;
import com.cubetalktest.cubetalk.models.login_user.LoginRequest;
import com.cubetalktest.cubetalk.models.login_user.LoginResponse;
import com.cubetalktest.cubetalk.services.api.LoginService;
import com.cubetalktest.cubetalk.services.api.ServiceBuilder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = LoginActivity.class.getSimpleName();


    private TextInputLayout mEmailLayout;
    private EditText mEmailEdit;

    private TextInputLayout mPasswordLayout;
    private EditText mPasswordEdit;

    private MaterialButton mForgetPasswordButton;
    private MaterialButton mLoginButton;
    private MaterialButton mRegisterNowButton;
    private SharedPreferences mSharedPreferences;
    private ActivityLoginBinding mActivityBinding;
    private ContentLoginBinding mContentBinding;
    private String mDeviceFirebaseToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);

        mActivityBinding = ActivityLoginBinding.inflate(LayoutInflater.from(this));
        setContentView(mActivityBinding.getRoot());

        mSharedPreferences = getSharedPreferences(User.PREFERENCE_NAME, MODE_PRIVATE);

        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.w(TAG, "onComplete: getInstanceId failed", task.getException());
                return;
            }

            // Get new Instance ID token
            mDeviceFirebaseToken = task.getResult().getToken();
            Log.d(TAG, "onComplete: mDeviceFirebaseToken: " + mDeviceFirebaseToken);
        });
        
        mContentBinding = mActivityBinding.contentLogin;

        mEmailLayout = mContentBinding.tilEmail;
        mEmailEdit = mContentBinding.tietEmail;

        mPasswordLayout = mContentBinding.tilPassword;
        mPasswordEdit = mContentBinding.tietPassword;

        mForgetPasswordButton = mContentBinding.btnForgotPassword;

        mLoginButton = mContentBinding.btnLogin;

        mRegisterNowButton = mContentBinding.btnRegisterNow;

        mForgetPasswordButton.setOnClickListener(v -> {
            hideSoftKeyboard(v);
            openForgotPassword();
        });

        mLoginButton.setOnClickListener(v -> {
            hideSoftKeyboard(v);
            login();
        });

        mRegisterNowButton.setOnClickListener(v -> {
            hideSoftKeyboard(v);
            openRegisterNow();
        });

        mEmailEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mEmailLayout.setError("");
            }
        });
        mPasswordEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mPasswordLayout.setError("");
            }
        });

        //fillWithDummyData();
    }

    public void hideSoftKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void openForgotPassword() {
        Intent intent = new Intent();
        intent.putExtra("OpenActivity", ForgotPasswordActivity.TAG);
        setResult(RESULT_CANCELED, intent);
        finish();
    }

    private void login() {
        if (!areFieldsValidated()) {
            return;
        }

        mLoginButton.setEnabled(false);

        String email = mEmailEdit.getText().toString();
        String password = mPasswordEdit.getText().toString();

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(email);
        loginRequest.setPassword(password);
        loginRequest.setDeviceToken(mDeviceFirebaseToken);

        LoginService loginService = ServiceBuilder.buildService(LoginService.class);
        Call<LoginResponse> createRequest = loginService.login(loginRequest);

        createRequest.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NotNull Call<LoginResponse> call, @NotNull Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    LoginResponse loginResponse = response.body();
                    if (loginResponse != null) {
                        if (loginResponse.getSuccess()) {
                            saveUserDetails(loginResponse);
                            Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            setResult(RESULT_OK);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            mLoginButton.setEnabled(true);
                        }
                    }
                } else {
                    mLoginButton.setEnabled(true);
                    Toast.makeText(
                            LoginActivity.this,
                            "Server Error",
                            Toast.LENGTH_SHORT
                    ).show();
                }

            }

            @Override
            public void onFailure(@NotNull Call<LoginResponse> call, @NotNull Throwable throwable) {
                Log.d(TAG, "onFailure: throwable.getMessage(): " + throwable.getMessage());
                Toast.makeText(LoginActivity.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                mLoginButton.setEnabled(true);
            }
        });
    }

    private void saveUserDetails(LoginResponse loginResponse) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(User.TOKEN, loginResponse.getToken());
        editor.putString(User.ID, loginResponse.getId());
        editor.putString(User.NAME, loginResponse.getName());
//        editor.putBoolean(User.IS_EXPERT, loginResponse.isExpert());
        editor.putString(User.EMAIL, loginResponse.getEmail());
        editor.putString(User.PROFILE_IMAGE, loginResponse.getProfileImage());
//        editor.putBoolean(User.IS_EXPERT_APPLIED, loginResponse.isExpertApplied());
        editor.putInt(User.EXPERT_APPLICATION_STATUS, loginResponse.getExpertApplicationStatus());
        editor.putBoolean(User.IS_EXPERT_ACTIVE, loginResponse.isExpertActive());
        editor.putString(User.PHONE, loginResponse.getPhoneNumber());
        editor.putInt(User.COUNTRYID,loginResponse.getmCountryId());
        editor.putInt(User.RADIUS, loginResponse.getRadius());
        editor.putString(User.MESIBO_ID, loginResponse.getMesibo_id());
        editor.putString(User.MESIBO_TOKEN, loginResponse.getMesibo_token());
       // editor.putString(User.MESIBO_TOKEN, "2e7aeb174f174bfe04344323f31ecb18707a1132f281bfebb15436d");

        // MainApplication.startMesibo(loginResponse.getMesibo_token());
        editor.apply();
        MainApplication.startMesibo(mSharedPreferences.getString(User.MESIBO_TOKEN,""));


    }

    private boolean areFieldsValidated() {
        mEmailLayout.setError("");
        mPasswordLayout.setError("");

        String email = mEmailEdit.getText().toString();
        String password = mPasswordEdit.getText().toString();

        if (TextUtils.isEmpty(email)) {
            showError(mEmailLayout, "Email cannot be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showError(mEmailLayout, "Email format is incorrect");
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            showError(mPasswordLayout, "Password cannot be empty");
            return false;
        }

        return true;
    }

    private void openRegisterNow() {
        Intent intent = new Intent();
        intent.putExtra("OpenActivity", UserRegistrationActivity.TAG);
        setResult(RESULT_CANCELED, intent);
        finish();
    }

    private void showError(TextInputLayout textInputLayout, String errorString) {
        showErrorOnTextInputLayout(textInputLayout, errorString);
        showSnackbar(errorString);
    }

    private void showErrorOnTextInputLayout(TextInputLayout textInputLayout, String errorString) {
        textInputLayout.setError(errorString);
    }

    private void showSnackbar(String errorString) {
        Snackbar.make(mContentBinding.getRoot(), errorString, Snackbar.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();



    }
}

