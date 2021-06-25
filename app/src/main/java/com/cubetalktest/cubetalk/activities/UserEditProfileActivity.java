package com.cubetalktest.cubetalk.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.cubetalktest.cubetalk.R;
import com.cubetalktest.cubetalk.databinding.ActivityUserEditProfileBinding;
import com.cubetalktest.cubetalk.databinding.ContentUserEditProfileBinding;
import com.cubetalktest.cubetalk.models.Country;
import com.cubetalktest.cubetalk.models.User;
import com.cubetalktest.cubetalk.models.update_user.UpdateUserRequest;
import com.cubetalktest.cubetalk.models.update_user.UpdateUserResponse;
import com.cubetalktest.cubetalk.models.user_info.UserInfoFetchResponse;
import com.cubetalktest.cubetalk.models.user_profile_image.UserImageUploadResponseBody;
import com.cubetalktest.cubetalk.services.api.ServiceBuilder;
import com.cubetalktest.cubetalk.services.api.UpdateUserService;
import com.cubetalktest.cubetalk.services.api.UserInfoService;
import com.cubetalktest.cubetalk.services.api.UserService;
import com.cubetalktest.cubetalk.utils.FileUtils;
import com.cubetalktest.cubetalk.utils.Utils;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cubetalktest.cubetalk.utils.Utils.getCountryNameById;

public class UserEditProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = UserEditProfileActivity.class.getSimpleName();
    private static final int RC_CHOOSE_IMAGE = 1;
    private static final int RC_CAPTURE_IMAGE = 2;
    private static final int RC_READ_EXTERNAL_STORAGE = 10;
    private static final int RC_CAMERA_AND_WRITE_EXTERNAL_STORAGE = 11;

    private SharedPreferences mSharedPreferences;

    private List<String> mGenders;
    private List<Country> mCountries;

    private ArrayAdapter<String> mGenderAdapter;
    private ArrayAdapter<Country> mCountriesAdapter;

    private ImageView mUserPhotoImage;
    private FloatingActionButton mGalleryButton;
    private FloatingActionButton mCameraButton;

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

    private MaterialButton mUpdateButton;

    private Country mCountry;
    private String mUserId;
    private View mLoadingLayout;
    private ActivityUserEditProfileBinding mActivityBinding;
    private ContentUserEditProfileBinding mContentBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_user_edit_profile);

        mActivityBinding = ActivityUserEditProfileBinding.inflate(LayoutInflater.from(this));
        setContentView(mActivityBinding.getRoot());

        mContentBinding = mActivityBinding.contentUserEditProfile;

        mLoadingLayout = mActivityBinding.loading.getRoot();

        mSharedPreferences = getSharedPreferences(User.PREFERENCE_NAME, MODE_PRIVATE);

        mUserId = mSharedPreferences.getString(User.ID, "");

        mGenders = Utils.getGenders();
        mCountries = Utils.getCountries();

        mGenderAdapter = new ArrayAdapter<>(this, R.layout.dropdown_menu_popup_item, mGenders);
        mCountriesAdapter = new ArrayAdapter<>(this, R.layout.dropdown_menu_popup_item, mCountries);

        mUserPhotoImage = mContentBinding.ivUserPhoto;
        mGalleryButton = mContentBinding.fabOpenGallery;
        mCameraButton = mContentBinding.fabOpenCamera;

        mNameLayout = mContentBinding.tilName;
        mNameEdit = mContentBinding.tietName;

        mEmailLayout = mContentBinding.tilEmail;
        mEmailEdit = mEmailLayout.getEditText();
        mEmailEdit = mContentBinding.tietEmail;

        mCountryLayout = mContentBinding.tilCountry;
//        mCountryAutocomplete = mCountryLayout.findViewById(R.id.mactv_countries);
        mCountryAutocomplete = mContentBinding.mactvCountries;

        mCityLayout = mContentBinding.tilCity;
//        mCityEdit = mCityLayout.getEditText();
        mCityEdit = mContentBinding.tietCity;

        mPhoneNumberLayout = mContentBinding.tilPhoneNumber;
//        mPhoneNumberEdit = mPhoneNumberLayout.getEditText();
        mPhoneNumberEdit = mContentBinding.tietPhoneNumber;

        mGenderLayout = mContentBinding.tilGender;
//        mGenderAutocomplete = mGenderLayout.findViewById(R.id.mactv_gender);
        mGenderAutocomplete = mContentBinding.mactvGender;

        mAgeLayout = mContentBinding.tilAge;
//        mAgeEdit = mAgeLayout.getEditText();
        mAgeEdit = mContentBinding.tietAge;

        mUpdateButton = mContentBinding.btnUpdateProfile;

        mGalleryButton.setOnClickListener(v -> {
            chooseImage();
        });

        mCameraButton.setOnClickListener(v -> {
            captureImage();
        });

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

        mGenderAutocomplete.setAdapter(mGenderAdapter);
        mCountryAutocomplete.setAdapter(mCountriesAdapter);

        mUpdateButton.setOnClickListener(view -> {
            if (view.getId() == R.id.btn_update_profile) {
//            setResult(RESULT_OK);
//            finish();
                hideSoftKeyboard(view);
                updateUserProfile();
            }

        });

        showUserInformation();
//        fillWithDummyData();
    }

    @AfterPermissionGranted(RC_READ_EXTERNAL_STORAGE)
    private void chooseImage() {

        String permission = Manifest.permission.READ_EXTERNAL_STORAGE;

        if (EasyPermissions.hasPermissions(this, permission)) {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            startActivityForResult(intent, RC_CHOOSE_IMAGE);
        } else {
            EasyPermissions.requestPermissions(
                    this,
                    "Need Storage Permissions",
                    RC_READ_EXTERNAL_STORAGE,
                    permission
            );
        }
    }

    @AfterPermissionGranted(RC_CAMERA_AND_WRITE_EXTERNAL_STORAGE)
    private void captureImage() {

        String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

        if (EasyPermissions.hasPermissions(this, permissions)) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            startActivityForResult(takePictureIntent, RC_CAPTURE_IMAGE);
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                    Log.e(TAG, "captureImage: " + ex.getMessage());
                }
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(this,
                            "com.cubetalktest.cubetalk.fileprovider",
                            photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePictureIntent, RC_CAPTURE_IMAGE);
                }
            }
        } else {
            EasyPermissions.requestPermissions(
                    this,
                    "Need Camera and Storage Permissions",
                    RC_CAMERA_AND_WRITE_EXTERNAL_STORAGE,
                    permissions
            );
        }
    }

    String currentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void showUserInformation() {
        showProgressLayout();
        fetchUserInformation();
    }

    private void showUserImageFromSharedPreferences() {
        String imageUrl = mSharedPreferences.getString(User.PROFILE_IMAGE, "");

        if (!TextUtils.isEmpty(imageUrl)) {
            Glide.with(this)
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .load(imageUrl)
                    .placeholder(R.drawable.ic_account_circle_white_141dp)
                    .listener(new RequestListener<Bitmap>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                            Log.d(TAG, "onLoadFailed: e.getMessage(): " + e.getMessage());
                            showSnackbar("Error fetching user image. Please check your internet connection.");
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                            Log.d(TAG, "onResourceReady: ");
                            return false;
                        }
                    })
                    .circleCrop()
                    .into(mUserPhotoImage);
        }

    }

    private void fetchUserInformation() {
        UserInfoService userInfoService = ServiceBuilder.buildService(UserInfoService.class);
        Call<UserInfoFetchResponse> createRequest = userInfoService.getUserInfo(mUserId,mSharedPreferences.getString(User.TOKEN, ""));

        createRequest.enqueue(new Callback<UserInfoFetchResponse>() {
            @Override
            public void onResponse(@NotNull Call<UserInfoFetchResponse> call, @NotNull Response<UserInfoFetchResponse> response) {
                Log.d(TAG, "onResponse: response: " + response.toString());
                if (response.isSuccessful()) {
                    UserInfoFetchResponse userInfoFetchResponse = response.body();
                    if (userInfoFetchResponse.getSuccess()) {
                        showUserImageFromSharedPreferences();
                        populateFieldsWithData(userInfoFetchResponse);
                        hideProgressLayout();
                    } else {
                        Toast.makeText(UserEditProfileActivity.this, userInfoFetchResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        hideProgressLayout();
                    }
                } else {
                    hideProgressLayout();
                    Toast.makeText(
                            UserEditProfileActivity.this,
                            "Server Error",
                            Toast.LENGTH_SHORT
                    ).show();
                }


            }

            @Override
            public void onFailure(@NotNull Call<UserInfoFetchResponse> call, @NotNull Throwable throwable) {
                mUpdateButton.setEnabled(true);
                Log.d(TAG, "onFailure: throwable.getMessage(): " + throwable.getMessage());
                Toast.makeText(UserEditProfileActivity.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void populateFieldsWithData(UserInfoFetchResponse userInfoFetchResponse) {
        UserInfoFetchResponse.Data data = userInfoFetchResponse.getData();

        if (data.getCountry() == 1) {
            data.setCountry(99);
        }

        String countryName = getCountryNameById(data.getCountry());

        for (Country country : mCountries) {
            if (country.getId() == data.getCountry()) {
                mCountry = country;
            }
        }

        mNameEdit.setText(data.getName());
        mEmailEdit.setText(data.getEmail());
        mCountryAutocomplete.setText(countryName, false);
        mCityEdit.setText(data.getCity());
        mPhoneNumberLayout.setPrefixText(mCountry.getCallingCode() + " ");
        mPhoneNumberEdit.setText(data.getPhoneNumber());
        mGenderAutocomplete.setText(data.getGender(), false);
        mAgeEdit.setText(data.getAge());
    }


    private void showProgressLayout() {
        mLoadingLayout.setVisibility(View.VISIBLE);
    }

    private void hideProgressLayout() {
        mLoadingLayout.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
    }

    private boolean areFieldsValidated() {

        clearErrors();

        String username = mNameEdit.getText().toString();
        String email = mEmailEdit.getText().toString();
        String phoneNumber = mPhoneNumberEdit.getText().toString();
        String gender = mGenderAutocomplete.getText().toString();
        String age = mAgeEdit.getText().toString();
        String country = mCountryAutocomplete.getText().toString();
        String city = mCityEdit.getText().toString();

        if (TextUtils.isEmpty(username)) {
            showError(mNameLayout, "Username cannot be left empty");
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
        } /*else if (!Pattern.compile("^[6-9]\\d{9}$").matcher(phoneNumber).matches()) {
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
        return true;
    }

    private void updateUserProfile() {
        if (!areFieldsValidated()) {
            return;
        }

        mUpdateButton.setEnabled(false);

        String name = mNameEdit.getText().toString();
        String phone = mPhoneNumberEdit.getText().toString();
        String gender = mGenderAutocomplete.getText().toString();
        String age = mAgeEdit.getText().toString();
        int country = mCountry.getId();
        String city = mCityEdit.getText().toString();


        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setName(name);
        updateUserRequest.setPhoneNumber(phone);
        updateUserRequest.setGender(gender);
        updateUserRequest.setAge(age);
        updateUserRequest.setCountry(country);
        updateUserRequest.setCity(city);

        UpdateUserService updateUserService = ServiceBuilder.buildService(UpdateUserService.class);
        Call<UpdateUserResponse> createRequest = updateUserService.updateUser( mSharedPreferences.getString(User.TOKEN, ""),mUserId, updateUserRequest);

        createRequest.enqueue(new Callback<UpdateUserResponse>() {
            @Override
            public void onResponse(@NotNull Call<UpdateUserResponse> call, @NotNull Response<UpdateUserResponse> response) {
                if (response.isSuccessful()) {
                    UpdateUserResponse updateUserResponse = response.body();
                    if (updateUserResponse.getSuccess()) {
                        updateUserDetails(updateUserRequest);
                        Toast.makeText(
                                UserEditProfileActivity.this,
                                updateUserResponse.getMessage(),
                                Toast.LENGTH_SHORT
                        ).show();

                        finish();
                    }
                } else {
                    mUpdateButton.setEnabled(true);
                    Toast.makeText(
                            UserEditProfileActivity.this,
                            "Server Error",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<UpdateUserResponse> call, @NotNull Throwable throwable) {

            }
        });
    }

    private void updateUserDetails(UpdateUserRequest updateUserRequest) {
        if (mSharedPreferences == null) {
            mSharedPreferences = getSharedPreferences(User.PREFERENCE_NAME, MODE_PRIVATE);
        }
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(User.NAME, updateUserRequest.getName());
        editor.putString(User.PHONE, updateUserRequest.getPhoneNumber());
        editor.putString(User.GENDER, updateUserRequest.getGender());
        editor.putString(User.AGE, updateUserRequest.getAge());
        editor.putInt(User.COUNTRY, updateUserRequest.getCountry());
        editor.putString(User.CITY, updateUserRequest.getCity());
        editor.apply();
    }

    public void hideSoftKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void fillWithDummyData() {
        mCountry = new Country(99, "India", "+91", "");

        mNameEdit.setText("Sumit Mandal");
        mEmailEdit.setText("sumitmandal@allindia.com");
        mCountryAutocomplete.setText(mCountry.getName(), false);
        mCityEdit.setText("Kolkata");
        mPhoneNumberLayout.setPrefixText(mCountry.getCallingCode() + " ");
        mPhoneNumberEdit.setText("9876543210");
        mGenderAutocomplete.setText("Male", false);
        mAgeEdit.setText("27");
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Uri uri = data != null ? data.getData() : null;
        if (uri != null) {
            Log.d(TAG, "onActivityResult: " + uri);
        } /*else {
            Log.d(TAG, "onActivityResult: URI missing");
            return;
        }*/

        if (requestCode == RC_CHOOSE_IMAGE && resultCode == RESULT_OK) {
//            SharedPreferences.Editor editor = mSharedPreferences.edit();
//            editor.putString(User.PROFILE_IMAGE, mUri.toString());
//            editor.apply();
//            Glide.with(this)
//                    .asBitmap()
//                    .diskCacheStrategy(DiskCacheStrategy.NONE)
//                    .skipMemoryCache(true)
//                    .load(mUri)
//                    .into(mUserPhotoImage);

            uploadImageToServer(FileUtils.getFile(this, getContentResolver(), uri));
        } else if (requestCode == RC_CAPTURE_IMAGE && resultCode == RESULT_OK) {
            uploadImageToServer(new File(currentPhotoPath));
        }
    }

    private void uploadImageToServer(File originalFile) {
        showProgressLayout();

        RequestBody imagePart = RequestBody.create(
                originalFile,
                MediaType.parse("image/*")
        );

        MultipartBody.Part file = MultipartBody.Part.createFormData("image", originalFile.getName(), imagePart);

        UserService userService = ServiceBuilder.buildService(UserService.class);
        Call<UserImageUploadResponseBody> createRequest = userService.uploadUserImage(mSharedPreferences.getString(User.TOKEN, ""),mUserId, file);

        createRequest.enqueue(new Callback<UserImageUploadResponseBody>() {
            @Override
            public void onResponse(@NotNull Call<UserImageUploadResponseBody> call, @NotNull Response<UserImageUploadResponseBody> response) {
                UserImageUploadResponseBody responseBody = response.body();
                Log.d(TAG, "onResponse: responseBody.getMessage(): " + responseBody.getMessage());
                String imageUrl = responseBody.getData();

                updateImageUrlToSharedPreferences(imageUrl);

                Glide.with(UserEditProfileActivity.this)
                        .asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .load(imageUrl)
                        .placeholder(R.drawable.ic_account_circle_white_141dp)
                        .listener(new RequestListener<Bitmap>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                hideProgressLayout();
                                return false;
                            }
                        })
                        .circleCrop()
                        .into(mUserPhotoImage);
//                showUserImageFromSharedPreferences();
//                hideProgressLayout();
            }

            @Override
            public void onFailure(@NotNull Call<UserImageUploadResponseBody> call, @NotNull Throwable throwable) {
                Log.d(TAG, "onFailure: throwable.getMessage(): " + throwable.getMessage());
            }
        });
    }

    private void updateImageUrlToSharedPreferences(String imageUrl) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(User.PROFILE_IMAGE, imageUrl);
        editor.apply();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
