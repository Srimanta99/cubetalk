package com.cubetalktest.cubetalk.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.regex.Pattern;

import com.cubetalktest.cubetalk.R;
import com.cubetalktest.cubetalk.databinding.ActivityUserRegistrationBinding;
import com.cubetalktest.cubetalk.databinding.ContentUserRegistrationBinding;
import com.cubetalktest.cubetalk.models.Country;
import com.cubetalktest.cubetalk.models.User;
import com.cubetalktest.cubetalk.models.register_user.UserRegistrationRequest;
import com.cubetalktest.cubetalk.models.register_user.UserRegistrationResponse;
import com.cubetalktest.cubetalk.models.user_privacy_policy_and_terms.UserPrivacyPolicyAndTermsResponse;
import com.cubetalktest.cubetalk.services.api.ServiceBuilder;
import com.cubetalktest.cubetalk.services.api.UserRegistrationService;
import com.cubetalktest.cubetalk.services.api.UserService;
import com.cubetalktest.cubetalk.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRegistrationActivity
        extends AppCompatActivity {

    public static final String TAG = UserRegistrationActivity.class.getSimpleName();

    private TextInputLayout mNameLayout;
    private EditText mNameEdit;

    private TextInputLayout mEmailLayout;
    private EditText mEmailEdit;

    private TextInputLayout mCountryLayout;
    private MaterialAutoCompleteTextView mCountryAutocomplete;

    private TextInputLayout mCityLayout;
    private EditText mCityEdit;

    private TextInputLayout mPhoneNumberLayout;
    private EditText mPhoneNumberEdit;

    private TextInputLayout mGenderLayout;
    private MaterialAutoCompleteTextView mGenderAutocomplete;

    private TextInputLayout mAgeLayout;
    private EditText mAgeEdit;

    private TextInputLayout mPasswordLayout;
    private EditText mPasswordEdit;
    private SharedPreferences mSharedPreferences;
   private TextInputLayout mConfirmPasswordLayout;
    private EditText mConfirmPasswordEdit;

    private MaterialCheckBox mPrivacyPolicyAndTermsCheck;

    private MaterialButton mRegisterButton;

    private Country mCountry;
    private List<String> mGenders;
    private List<Country> mCountries;
    private ArrayAdapter<String> mGenderAdapter;
    private ArrayAdapter<Country> mCountriesAdapter;
    private ActivityUserRegistrationBinding mActivityBinding;
    private ContentUserRegistrationBinding mContentBinding;
    private MaterialButton mSignInButton;
    private MaterialButton mForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_user_registration);

        mActivityBinding = ActivityUserRegistrationBinding.inflate(LayoutInflater.from(this));
        setContentView(mActivityBinding.getRoot());
        mSharedPreferences = getSharedPreferences(User.PREFERENCE_NAME, Context.MODE_PRIVATE);
        mContentBinding = mActivityBinding.contentUserRegistration;

        mGenders = Utils.getGenders();

        mCountries = Utils.getCountries();

        mGenderAdapter = new ArrayAdapter<>(this, R.layout.dropdown_menu_popup_item, mGenders);
        mCountriesAdapter = new ArrayAdapter<>(this, R.layout.dropdown_menu_popup_item, mCountries);

        mNameLayout = mContentBinding.tilName;
        mNameEdit = mNameLayout.getEditText();

        mEmailLayout = mContentBinding.tilEmail;
        mEmailEdit = mEmailLayout.getEditText();

        mCountryLayout = mContentBinding.tilCountry;
        mCountryAutocomplete = mContentBinding.mactvCountries;

        mCityLayout = mContentBinding.tilCity;
        mCityEdit = mCityLayout.getEditText();

        mPhoneNumberLayout = mContentBinding.tilPhoneNumber;
        mPhoneNumberEdit = mPhoneNumberLayout.getEditText();

        mGenderLayout = mContentBinding.tilGender;
        mGenderAutocomplete = mContentBinding.mactvGender;

        mAgeLayout = mContentBinding.tilAge;
        mAgeEdit = mAgeLayout.getEditText();

        mPasswordLayout = mContentBinding.tilPassword;
        mPasswordEdit = mPasswordLayout.getEditText();

       mConfirmPasswordLayout = mContentBinding.tilConfirmPassword;
        mConfirmPasswordEdit = mConfirmPasswordLayout.getEditText();

        mPrivacyPolicyAndTermsCheck = mContentBinding.cbPrivacyPolicyAndTerms;

        mRegisterButton = mContentBinding.btnRegister;

        mSignInButton = mContentBinding.btnSignIn;

        mForgotPassword = mContentBinding.btnForgotPassword;


        mNameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mNameLayout.setError("");
            }
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

        mCountryAutocomplete.setAdapter(mCountriesAdapter);
        mCountryAutocomplete.setOnClickListener(v -> {
            hideSoftKeyboard(v);
            mCountryLayout.setError("");
        });
        mCountryAutocomplete.setOnItemClickListener((parent, view, position, id) -> {
            mCountry = mCountries.get(position);
            mCityEdit.setText("");
            mPhoneNumberLayout.setPrefixText(mCountry.getCallingCode() + " ");
            mPhoneNumberEdit.setText("");

        });

        mCityEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mCityLayout.setError("");
            }
        });

        mPhoneNumberEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mPhoneNumberLayout.setError("");
            }
        });

        mGenderAutocomplete.setAdapter(mGenderAdapter);
        mGenderAutocomplete.setOnClickListener(v -> {
            hideSoftKeyboard(v);
            mGenderLayout.setError("");
        });

        mAgeEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mAgeLayout.setError("");
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

       mConfirmPasswordEdit.addTextChangedListener(new TextWatcher() {
            @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {
           }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
           }

            @Override
            public void afterTextChanged(Editable s) {
               mConfirmPasswordLayout.setError("");
            }
        });

        String userPrivacyPolicyAndTerms = getString(R.string.user_registration_privacy_policy_and_terms);
        SpannableString spannableString = new SpannableString(userPrivacyPolicyAndTerms);
        ClickableSpan privacyPolicyClickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                widget.cancelPendingInputEvents();

                UserService userService = ServiceBuilder.buildService(UserService.class);
                Call<UserPrivacyPolicyAndTermsResponse> createRequest = userService.getUserPrivacyPolicy(mSharedPreferences.getString(User.ID, ""),"5de5730bce44af2df76a1465");
                createRequest.enqueue(new Callback<UserPrivacyPolicyAndTermsResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<UserPrivacyPolicyAndTermsResponse> call, @NotNull Response<UserPrivacyPolicyAndTermsResponse> response) {
                        Log.d(TAG, "onResponse: ");

                        UserPrivacyPolicyAndTermsResponse expertTerms = response.body();
                        if (expertTerms != null && expertTerms.getSuccess()) {
                            UserPrivacyPolicyAndTermsResponse.Cms cms = expertTerms.getCms();
                            new MaterialAlertDialogBuilder(UserRegistrationActivity.this)
                                    .setTitle("Cube Talk")
                                    .setMessage(cms.getContent())
                                    .setPositiveButton("OK", null)
                                    .show();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<UserPrivacyPolicyAndTermsResponse> call, @NotNull Throwable throwable) {
                        Log.d(TAG, "onFailure: ");
                    }
                });
            }
        };

        ClickableSpan termsClickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                widget.cancelPendingInputEvents();

                UserService userService = ServiceBuilder.buildService(UserService.class);
                Call<UserPrivacyPolicyAndTermsResponse> createRequest = userService.getUserTerms("5e206ab02a7fda18a539714d");
                createRequest.enqueue(new Callback<UserPrivacyPolicyAndTermsResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<UserPrivacyPolicyAndTermsResponse> call, @NotNull Response<UserPrivacyPolicyAndTermsResponse> response) {
                        Log.d(TAG, "onResponse: ");

                        UserPrivacyPolicyAndTermsResponse expertTerms = response.body();
                        if (expertTerms != null && expertTerms.getSuccess()) {
                            UserPrivacyPolicyAndTermsResponse.Cms cms = expertTerms.getCms();
                            new MaterialAlertDialogBuilder(UserRegistrationActivity.this)
                                    .setTitle("Cube Talk")
                                    .setMessage(cms.getContent())
                                    .setPositiveButton("OK", null)
                                    .show();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<UserPrivacyPolicyAndTermsResponse> call, @NotNull Throwable throwable) {
                        Log.d(TAG, "onFailure: ");
                    }
                });
            }
        };

        int privacyPolicyStartIndex = userPrivacyPolicyAndTerms.indexOf("Privacy");
        int privacyPolicyEndIndex = userPrivacyPolicyAndTerms.indexOf("&");
        spannableString.setSpan(privacyPolicyClickableSpan, privacyPolicyStartIndex, privacyPolicyEndIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        int termsStartIndex = userPrivacyPolicyAndTerms.indexOf("Terms");
        int termsEndIndex = termsStartIndex + "Terms".length();
        spannableString.setSpan(termsClickableSpan, termsStartIndex, termsEndIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        mPrivacyPolicyAndTermsCheck.setText(spannableString);
        mPrivacyPolicyAndTermsCheck.setMovementMethod(LinkMovementMethod.getInstance());

        mRegisterButton.setOnClickListener(view -> {
            hideSoftKeyboard(view);
            registerUser();
        });

        mSignInButton.setOnClickListener(v -> {
            Intent intent = new Intent(UserRegistrationActivity.this, IntroScreenActivity.class);
            intent.putExtra("OpenActivity", LoginActivity.TAG);
            startActivity(intent);
            finish();
        });

        mForgotPassword.setOnClickListener(v -> {
            Intent intent = new Intent(UserRegistrationActivity.this, IntroScreenActivity.class);
            intent.putExtra("OpenActivity", ForgotPasswordActivity.TAG);
            startActivity(intent);
            finish();
        });
    }

    public void hideSoftKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void registerUser() {
        if (!areFieldsValidated()) {
            return;
        }

        mRegisterButton.setEnabled(false);

        String name = mNameEdit.getText().toString();
        String email = mEmailEdit.getText().toString();
        String phoneNumber = mPhoneNumberEdit.getText().toString();
        String gender = mGenderAutocomplete.getText().toString();
        String age = mAgeEdit.getText().toString();
        int country = mCountry.getId();
        String city = mCityEdit.getText().toString();
        String password = mPasswordEdit.getText().toString();
        String conpassword=mConfirmPasswordEdit.getText().toString();

        UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest();
        userRegistrationRequest.setName(name);
        userRegistrationRequest.setEmail(email);
        userRegistrationRequest.setPhoneNumber(phoneNumber);
        userRegistrationRequest.setGender(gender);
        userRegistrationRequest.setAge(age);
        userRegistrationRequest.setCountry(country);
        userRegistrationRequest.setCity(city);
        userRegistrationRequest.setPassword(password);
        userRegistrationRequest.setConfirm_password(conpassword);
      //  userRegistrationRequest.setApp_id("local.impactlife.cubetalk");
        userRegistrationRequest.setApp_id("com.cubetalktest.cubetalk");

        UserRegistrationService userRegistrationService = ServiceBuilder.buildService(UserRegistrationService.class);
        Call<UserRegistrationResponse> createRequest = userRegistrationService.register(userRegistrationRequest);

        createRequest.enqueue(new Callback<UserRegistrationResponse>() {
            @Override
            public void onResponse(@NotNull Call<UserRegistrationResponse> call, @NotNull Response<UserRegistrationResponse> response) {
                Log.d(TAG, "onResponse: response: " + response.toString());
                if (response.isSuccessful()) {
                    UserRegistrationResponse userRegistrationResponse = response.body();
                    if (userRegistrationResponse.isSuccess() && userRegistrationResponse.getStatusCode() == 200) {
                        setResult(RESULT_OK);
                        finish();
                    } else {
                        mRegisterButton.setEnabled(true);
                    }
                    Toast.makeText(
                            UserRegistrationActivity.this,
                            userRegistrationResponse.getMessage(),
                            Toast.LENGTH_SHORT
                    ).show();
                } else {
                    mRegisterButton.setEnabled(true);

                    Toast.makeText(
                            UserRegistrationActivity.this,
                            "Server Error",
                            Toast.LENGTH_SHORT
                    ).show();
                }


            }

            @Override
            public void onFailure(Call<UserRegistrationResponse> call, Throwable throwable) {
                mRegisterButton.setEnabled(true);
                Log.d(TAG, "onFailure: t.getMessage(): " + throwable.getMessage());
                Toast.makeText(UserRegistrationActivity.this, "onFailure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean areFieldsValidated() {

        clearErrors();

        String name = mNameEdit.getText().toString();
        String email = mEmailEdit.getText().toString();
        String phoneNumber = mPhoneNumberEdit.getText().toString();
        String gender = mGenderAutocomplete.getText().toString();
        String age = mAgeEdit.getText().toString();
        String country = mCountryAutocomplete.getText().toString();
        String city = mCityEdit.getText().toString();
        String password = mPasswordEdit.getText().toString();
       String confirmPassword = mConfirmPasswordEdit.getText().toString();

        if (TextUtils.isEmpty(name)) {
            showError(mNameLayout, "Name cannot be left empty");
            return false;
        }

        if (TextUtils.isEmpty(email)) {
            showError(mEmailLayout, "Email cannot be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showError(mEmailLayout, "Email format is incorrect");
            return false;
        }

        if (TextUtils.isEmpty(country)) {
            showError(mCountryLayout, "Country not selected");
            return false;
        }

        if (TextUtils.isEmpty(city)) {
            showError(mCityLayout, "City cannot be left empty");
            return false;
        }

        if (TextUtils.isEmpty(phoneNumber)) {
            showError(mPhoneNumberLayout, "Phone number cannot be left empty");
            return false;
        } /*else if (!Pattern.compile("^[0-9]\\d{9}$").matcher(phoneNumber).matches()) {
            showError(mPhoneNumberLayout, "Phone number format is incorrect");
            return false;
        }*/

        if (TextUtils.isEmpty(gender)) {
            showError(mGenderLayout, "Gender not selected");
            return false;
        }

        if (TextUtils.isEmpty(age)) {
            showError(mAgeLayout, "Age cannot be left empty");
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            showError(mPasswordLayout, "Password cannot be left empty");
            return false;
        } else if (!Pattern.compile("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$").matcher(password).matches()) {
            showError(mPasswordLayout, "Minimum 8 characters, at least one uppercase, one lowercase, one number and one special character");
            return false;
        }

        if (TextUtils.isEmpty(confirmPassword)) {
           showError(mConfirmPasswordLayout, "Confirm Password cannot be left empty");
            return false;
        }
//        /*else if (!Pattern.compile("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$").matcher(confirmPassword).matches()) {
//            showError(mConfirmPasswordLayout, "Minimum 8 characters, at least one uppercase, one lowercase, and one special character");
//            return false;
     //   }
           else if (!TextUtils.equals(password, confirmPassword)) {
           showError(mConfirmPasswordLayout, "Password and Confirm password do not match");
            return false;
       }

        if (!mPrivacyPolicyAndTermsCheck.isChecked()) {
            showSnackbar("Privacy policy and Terms not checked");
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

    private void clearErrors() {
        mNameLayout.setError("");
        mEmailLayout.setError("");
        mPhoneNumberLayout.setError("");
        mGenderLayout.setError("");
        mAgeLayout.setError("");
        mCountryLayout.setError("");
        mCityLayout.setError("");
        mPasswordLayout.setError("");
//        mConfirmPasswordLayout.setError("");
    }


}
