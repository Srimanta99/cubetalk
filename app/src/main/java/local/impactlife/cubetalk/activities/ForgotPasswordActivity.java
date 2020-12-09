package local.impactlife.cubetalk.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

import local.impactlife.cubetalk.R;
import local.impactlife.cubetalk.databinding.ActivityForgotPasswordBinding;
import local.impactlife.cubetalk.databinding.ContentForgotPasswordBinding;
import local.impactlife.cubetalk.models.User;
import local.impactlife.cubetalk.models.forgot_password.ForgotPasswordRequest;
import local.impactlife.cubetalk.models.forgot_password.ForgotPasswordResponse;
import local.impactlife.cubetalk.services.api.ForgotPasswordService;
import local.impactlife.cubetalk.services.api.ServiceBuilder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity
        extends AppCompatActivity {

    public static final String TAG = ForgotPasswordActivity.class.getSimpleName();

    private TextInputLayout mEmailLayout;
    private EditText mEmailText;
    private MaterialButton mSendButton;
    private MaterialButton mLoginButton;
    private ActivityForgotPasswordBinding mActivityBinding;
    private ContentForgotPasswordBinding mContentBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_forgot_password);

        mActivityBinding = ActivityForgotPasswordBinding.inflate(LayoutInflater.from(this));
        setContentView(mActivityBinding.getRoot());

        Toolbar toolbar = mActivityBinding.toolbar;
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mContentBinding = mActivityBinding.contentForgotPassword;

        mEmailLayout = mContentBinding.tilEmail;
        mEmailText = mContentBinding.tietEmail;
        mSendButton = mContentBinding.btnSend;
        mLoginButton = mContentBinding.btnLogin;

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendForgotPasswordRequest();
            }
        });
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("OpenActivity", LoginActivity.TAG);
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_forgot_password, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }

    private void sendForgotPasswordRequest() {
        if (!areFieldsValidated()) {
            return;
        }

        mSendButton.setEnabled(false);
        mLoginButton.setEnabled(false);

        String email = mEmailText.getText().toString();

        ForgotPasswordRequest forgotPasswordRequest = new ForgotPasswordRequest();
        forgotPasswordRequest.setEmail(email);
        SharedPreferences mSharedPreferences = getSharedPreferences(User.PREFERENCE_NAME, MODE_PRIVATE);
        ForgotPasswordService forgotPasswordService = ServiceBuilder.buildService(ForgotPasswordService.class);
        Call<ForgotPasswordResponse> createRequest = forgotPasswordService.sendForgotPasswordRequest(mSharedPreferences.getString(User.TOKEN, ""),forgotPasswordRequest);

        createRequest.enqueue(new Callback<ForgotPasswordResponse>() {
            @Override
            public void onResponse(@NotNull Call<ForgotPasswordResponse> call, @NotNull Response<ForgotPasswordResponse> response) {
                if (response.isSuccessful()) {
                    ForgotPasswordResponse forgotPasswordResponse = response.body();
                    if (forgotPasswordResponse.getSuccess()) {

                        //Toast.makeText(ForgotPasswordActivity.this, forgotPasswordResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        Snackbar snackbar = Snackbar.make(mContentBinding.getRoot(), forgotPasswordResponse.getMessage(), Snackbar.LENGTH_LONG);
                        snackbar.addCallback(new Snackbar.Callback() {
                            @Override
                            public void onDismissed(Snackbar transientBottomBar, int event) {
                                Intent intent = new Intent();
                                intent.putExtra("OpenActivity", LoginActivity.TAG);
                                setResult(RESULT_OK, intent);
                                finish();
                            }
                        });
                        snackbar.show();

                    } else {
                        Toast.makeText(ForgotPasswordActivity.this, forgotPasswordResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        mLoginButton.setEnabled(true);
                    }
                } else {
                    mLoginButton.setEnabled(true);
                    Toast.makeText(
                            ForgotPasswordActivity.this,
                            "Server Error",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<ForgotPasswordResponse> call, @NotNull Throwable throwable) {
                Log.d(TAG, "onFailure: throwable.getMessage(): " + throwable.getMessage());
                Toast.makeText(ForgotPasswordActivity.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                mLoginButton.setEnabled(true);
            }
        });
    }

    private boolean areFieldsValidated() {
        mEmailLayout.setError("");

        String email = mEmailText.getText().toString();

        if (TextUtils.isEmpty(email)) {
            showError(mEmailLayout, "Email cannot be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showError(mEmailLayout, "Email format is incorrect");
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
        Snackbar.make(mContentBinding.getRoot(), errorString, Snackbar.LENGTH_LONG).show();
    }
}
