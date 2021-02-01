package com.cubetalktest.cubetalk.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

import com.cubetalktest.cubetalk.databinding.ActivityChangePasswordBinding;
import com.cubetalktest.cubetalk.databinding.ContentChangePasswordBinding;
import com.cubetalktest.cubetalk.models.User;
import com.cubetalktest.cubetalk.models.change_password.ChangePasswordRequest;
import com.cubetalktest.cubetalk.models.change_password.ChangePasswordResponse;
import com.cubetalktest.cubetalk.services.api.ChangePasswordService;
import com.cubetalktest.cubetalk.services.api.ServiceBuilder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity
        extends AppCompatActivity {

    private static final String TAG = ChangePasswordActivity.class.getSimpleName();

    private SharedPreferences mSharedPreferences;

    private ContentChangePasswordBinding mContentChangePassword;
    private TextInputLayout mCurrentPasswordLayout;
    private EditText mCurrentPasswordEdit;
    private TextInputLayout mNewPasswordLayout;
    private EditText mNewPasswordEdit;
    private TextInputLayout mConfirmPasswordLayout;
    private EditText mConfirmPasswordEdit;
    private MaterialButton mChangePasswordButton;
    private ActivityChangePasswordBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_change_password);
        mBinding = ActivityChangePasswordBinding.inflate(LayoutInflater.from(this));
        setContentView(mBinding.getRoot());

        mSharedPreferences = getSharedPreferences(User.PREFERENCE_NAME, MODE_PRIVATE);

        Toolbar toolbar = mBinding.toolbar;
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mContentChangePassword = mBinding.contentChangePassword;

        mCurrentPasswordLayout = mContentChangePassword.tilCurrentPassword;
        mCurrentPasswordEdit = mCurrentPasswordLayout.getEditText();

        mNewPasswordLayout = mContentChangePassword.tilNewPassword;
        mNewPasswordEdit = mNewPasswordLayout.getEditText();

        mConfirmPasswordLayout = mContentChangePassword.tilConfirmPassword;
        mConfirmPasswordEdit = mConfirmPasswordLayout.getEditText();

        mChangePasswordButton = mContentChangePassword.btnChangePassword;

        mCurrentPasswordEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                mCurrentPasswordLayout.setError("");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mNewPasswordEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                mNewPasswordLayout.setError("");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mConfirmPasswordEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                mConfirmPasswordLayout.setError("");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mChangePasswordButton.setOnClickListener(v -> changePassword());


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }

    private void changePassword() {
        if (!areFieldsValidated()) {
            return;
        }

        mChangePasswordButton.setEnabled(false);

        String currentPassword = mCurrentPasswordEdit.getText().toString();
        String newPassword = mNewPasswordEdit.getText().toString();
        String confirmPassword = mConfirmPasswordEdit.getText().toString();

        String userId = mSharedPreferences.getString(User.ID, "");

        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
        changePasswordRequest.setCurrentPassword(currentPassword);
        changePasswordRequest.setNewPassword(newPassword);
        changePasswordRequest.setConfirmPassword(confirmPassword);

        Log.d(TAG, "changePassword: gson: " + new Gson().toJson(changePasswordRequest));

        ChangePasswordService changePasswordService = ServiceBuilder.buildService(ChangePasswordService.class);
        Call<ChangePasswordResponse> createRequest = changePasswordService.changePasswordRequest(mSharedPreferences.getString(User.TOKEN, ""),userId, changePasswordRequest);

        createRequest.enqueue(new Callback<ChangePasswordResponse>() {
            @Override
            public void onResponse(@NotNull Call<ChangePasswordResponse> call, @NotNull Response<ChangePasswordResponse> response) {
                if (response.isSuccessful()) {
                    ChangePasswordResponse changePasswordResponse = response.body();
                    Snackbar snackbar = Snackbar.make(mContentChangePassword.getRoot(), changePasswordResponse.getMessage(), Snackbar.LENGTH_LONG);
                    if (changePasswordResponse.isSuccess()) {
                        snackbar.addCallback(new Snackbar.Callback() {
                            @Override
                            public void onDismissed(Snackbar transientBottomBar, int event) {
//                                Intent intent = new Intent();
//                                intent.putExtra("OpenActivity", LoginActivity.TAG);
                                setResult(RESULT_OK/*, intent*/);
                                finish();
                            }
                        });

                    } else {
                        mChangePasswordButton.setEnabled(true);
                    }
                    snackbar.show();
                } else {
                    mChangePasswordButton.setEnabled(true);
                    Toast.makeText(
                            ChangePasswordActivity.this,
                            "Server Error",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<ChangePasswordResponse> call, @NotNull Throwable throwable) {
                Log.d(TAG, "onFailure: throwable.getMessage(): " + throwable.getMessage());
                Toast.makeText(ChangePasswordActivity.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                mChangePasswordButton.setEnabled(true);
            }
        });
    }

    private boolean areFieldsValidated() {
        mCurrentPasswordLayout.setError("");
        mNewPasswordLayout.setError("");
        mConfirmPasswordLayout.setError("");

        String currentPassword = mCurrentPasswordEdit.getText().toString();
        String newPassword = mNewPasswordEdit.getText().toString();
        String confirmPassword = mConfirmPasswordEdit.getText().toString();

        if (TextUtils.isEmpty(currentPassword)) {
            showError(mCurrentPasswordLayout, "Current password cannot be empty");
            return false;
        }

        if (TextUtils.isEmpty(newPassword)) {
            showError(mNewPasswordLayout, "New password cannot be empty");
            return false;
        } else if (!Pattern.compile("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$").matcher(newPassword).matches()) {
            showError(mNewPasswordLayout, "Minimum 8 characters, at least one uppercase, one lowercase, one number and one special character");
            return false;
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            showError(mConfirmPasswordLayout, "Confirm password cannot be empty");
            return false;
        } else if (!TextUtils.equals(newPassword, confirmPassword)) {
            showError(mConfirmPasswordLayout, "New password and Confirm password do not match");
            return false;
        }

        return true;
    }

    private void showError(TextInputLayout textInputLayout, String errorString) {
        showErrorOnTextInputLayout(textInputLayout, errorString);
        showSnackbar(errorString);
    }

    private void showErrorOnTextInputLayout(TextInputLayout textInputLayout, String errorString) {
        textInputLayout.setError(errorString);
    }

    private void showSnackbar(String errorString) {
        Snackbar.make(mContentChangePassword.getRoot(), errorString, Snackbar.LENGTH_LONG).show();
    }
}
