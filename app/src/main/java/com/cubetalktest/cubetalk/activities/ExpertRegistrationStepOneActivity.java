package com.cubetalktest.cubetalk.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import com.cubetalktest.cubetalk.R;
import com.cubetalktest.cubetalk.databinding.ActivityExpertRegistrationStepOneBinding;
import com.cubetalktest.cubetalk.databinding.ContentExpertRegistrationStepOneBinding;
import com.cubetalktest.cubetalk.models.User;
import com.cubetalktest.cubetalk.models.common.Speciality;
import com.cubetalktest.cubetalk.models.expert_registration.ExpertRegistrationRequest;
import com.cubetalktest.cubetalk.models.expert_registration.SpecialityResponse;
import com.cubetalktest.cubetalk.services.api.ExpertRegistrationService;
import com.cubetalktest.cubetalk.services.api.ServiceBuilder;
import com.cubetalktest.cubetalk.utils.Utils;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExpertRegistrationStepOneActivity
        extends AppCompatActivity
        implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private static final String TAG = ExpertRegistrationStepOneActivity.class.getSimpleName();


    private static final int RC_SELECT_CV_DOCUMENT = 1001;

    private static final int RC_CAPTURE_KYC_ONE_IMAGE = 2001;
    private static final int RC_SELECT_KYC_ONE_DOCUMENT = 2002;

    private static final int RC_CAPTURE_KYC_TWO_IMAGE = 3001;
    private static final int RC_SELECT_KYC_TWO_DOCUMENT = 3002;

    private static final int RC_PERMISSION_READ_EXTERNAL_STORAGE = 10;
    private static final int RC_PERMISSION_CAMERA_AND_WRITE_EXTERNAL_STORAGE = 11;

    private static final int ONE_MB = 1048576;

    private static final int CHOICE_INDEX_CAPTURE_PHOTO = 0;
    private static final int CHOICE_INDEX_UPLOAD_DOCUMENT = 1;

    private ArrayList<Speciality> mSpecialities;
    private ArrayList<Speciality> mSelectedSpecialities;

    private TextInputLayout mAddressTextInput;
    private TextInputEditText mAddressEdit;
    private TextInputLayout mProfessionalSummaryTextInput;
    private TextInputEditText mProfessionalSummaryEdit;
    private TextInputLayout mKeyAccomplishmentTextInput;
    private TextInputEditText mKeyAccomplishmentEdit;
    private TextInputLayout mLanguagesForConsultationTextInput;
    private TextInputEditText mLanguagesForConsultationEdit;
    private MaterialTextView mConsultationCategoriesText;
    private LinearLayout mConsultingCategoriesLayout;
    private TextInputLayout mYearsOfExperienceTextInput;
    private TextInputEditText mYearsOfExperienceEdit;

    private MaterialButton mUploadCvButton;
    private MaterialTextView mUploadedCvNameText;
    private MaterialButton mViewSelectedCvDocumentButton;

    private MaterialButton mUploadKycOneButton;
    private EditText mUploadedKycOneTitleEdit;
    private MaterialTextView mUploadedKycOneNameText;
    private MaterialButton mViewSelectedKycOneDocumentButton;

    private MaterialButton mUploadKycTwoButton;
    private EditText mUploadKycTwoTitleEdit;
    private MaterialTextView mUploadedKycTwoNameText;
    private MaterialButton mViewSelectedKycTwoDocumentButton;

    private MaterialButton mProceedToStepTwoButton;

    private ExpertRegistrationRequest mExpertRegistrationRequest;
    private ActivityExpertRegistrationStepOneBinding mActivityBinding;
    private ContentExpertRegistrationStepOneBinding mContentBinding;
    private View mLoadingLayout;
    private Uri mPhotoURI;
    private File mExpertCvFile;
    //    private File mExpertKycOneImageFile;
//    private File mExpertKycTwoImageFile;
    private Uri mExpertCvDocumentUri;
    private Uri mExpertKycOneImageUri;
    private Uri mExpertKycTwoImageUri;
    private ArrayAdapter<Integer> mYearsOfExperienceAdapter;
    private List<Integer> mYearsOfExperiences;
    private SharedPreferences mSharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_expert_registration_step_one);

        mActivityBinding = ActivityExpertRegistrationStepOneBinding.inflate(LayoutInflater.from(this));
        setContentView(mActivityBinding.getRoot());
        mSharedPreferences = getSharedPreferences(User.PREFERENCE_NAME, Context.MODE_PRIVATE);
        Toolbar toolbar = mActivityBinding.toolbar;
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mLoadingLayout = mActivityBinding.loading.getRoot();

        mContentBinding = mActivityBinding.contentExpertRegistrationStepOne;

        mExpertRegistrationRequest = new ExpertRegistrationRequest();

        mSpecialities = new ArrayList<>();
        mSelectedSpecialities = new ArrayList<>();

        mYearsOfExperiences = Utils.getYearsOfExperiences();
        mYearsOfExperienceAdapter = new ArrayAdapter<>(this, R.layout.dropdown_menu_popup_item, mYearsOfExperiences);

        mAddressTextInput = mContentBinding.tilAddress;
        mAddressEdit = mContentBinding.tietAddress;

        mProfessionalSummaryTextInput = mContentBinding.tilProfessionalSummary;
        mProfessionalSummaryEdit = mContentBinding.tietProfessionalSummary;

        mKeyAccomplishmentTextInput = mContentBinding.tilKeyAccomplishment;
        mKeyAccomplishmentEdit = mContentBinding.tietKeyAccomplishment;

        mLanguagesForConsultationTextInput = mContentBinding.tilLanguagesForConsultation;
        mLanguagesForConsultationEdit = mContentBinding.tietLanguagesForConsultation;

        mConsultationCategoriesText = mContentBinding.mtvConsultationCategories;
        mConsultingCategoriesLayout = mContentBinding.llConsultingCategories;

        mYearsOfExperienceTextInput = mContentBinding.tilYearsOfExperience;
        mYearsOfExperienceEdit = mContentBinding.tietYearsOfExperience;

        mUploadCvButton = mContentBinding.btnUploadCv;
        mUploadedCvNameText = mContentBinding.tvUploadedCvName;
        mViewSelectedCvDocumentButton = mContentBinding.btnViewSelectedCvDocument;

        mUploadKycOneButton = mContentBinding.btnUploadKycOne;
        mUploadedKycOneTitleEdit = mContentBinding.etUploadedKycOneTitle;
        mUploadedKycOneNameText = mContentBinding.tvUploadedKycOneName;
        mViewSelectedKycOneDocumentButton = mContentBinding.btnViewSelectedKycOneDocument;

        mUploadKycTwoButton = mContentBinding.btnUploadKycTwo;
        mUploadKycTwoTitleEdit = mContentBinding.etUploadedKycTwoTitle;
        mUploadedKycTwoNameText = mContentBinding.tvUploadedKycTwoName;
        mViewSelectedKycTwoDocumentButton = mContentBinding.btnViewSelectedKycTwoDocument;

        mProceedToStepTwoButton = mContentBinding.btnProceedToStepTwo;

        mAddressEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mAddressTextInput.setError("");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mProfessionalSummaryEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mProfessionalSummaryTextInput.setError("");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mKeyAccomplishmentEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mKeyAccomplishmentTextInput.setError("");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mLanguagesForConsultationEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mLanguagesForConsultationTextInput.setError("");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mYearsOfExperienceEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mYearsOfExperienceTextInput.setError("");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mUploadCvButton.setOnClickListener(view -> openCvPicker());
        mUploadKycOneButton.setOnClickListener(view -> browseKycDocumentOne());
        mUploadKycTwoButton.setOnClickListener(view -> browseKycDocumentTwo());

        mProceedToStepTwoButton.setOnClickListener(v -> {
            hideSoftKeyboard(v);
            proceedToStepTwo();
        });

        getSpecialities();
    }

    //    @AfterPermissionGranted(RC_PERMISSION_READ_EXTERNAL_STORAGE)
    private void openCvPicker() {

        String permission = Manifest.permission.READ_EXTERNAL_STORAGE;

        if (EasyPermissions.hasPermissions(this, permission)) {
            String[] mimeTypes = {
                    "application/msword", // .doc
                    "application/vnd.openxmlformats-officedocument.wordprocessingml.document", // .docx
                    "application/pdf" //.pdf
            };

            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("*/*");
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            startActivityForResult(intent, RC_SELECT_CV_DOCUMENT);
        } else {
            EasyPermissions.requestPermissions(
                    this,
                    "Need Storage Permissions",
                    RC_PERMISSION_READ_EXTERNAL_STORAGE,
                    permission
            );
        }
    }

    private void showBrowseDialog(int cameraCode, int selectCode) {
        new MaterialAlertDialogBuilder(this)
                .setItems(R.array.kyc_file_picker_choices, (dialog, which) -> {
                    switch (which) {
                        case CHOICE_INDEX_CAPTURE_PHOTO:
                            openCamera(cameraCode);
                            break;

                        case CHOICE_INDEX_UPLOAD_DOCUMENT:
                            openFileChooser(selectCode);
                            break;
                    }
                })
                .show();
    }

    //    @AfterPermissionGranted(RC_PERMISSION_CAMERA_AND_WRITE_EXTERNAL_STORAGE)
    private void browseKycDocumentOne() {
        String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

        if (EasyPermissions.hasPermissions(this, permissions)) {
            showBrowseDialog(RC_CAPTURE_KYC_ONE_IMAGE, RC_SELECT_KYC_ONE_DOCUMENT);
        } else {
            EasyPermissions.requestPermissions(
                    this,
                    "Need Camera and Storage Permissions",
                    RC_PERMISSION_CAMERA_AND_WRITE_EXTERNAL_STORAGE,
                    permissions
            );
        }
    }


    //    @AfterPermissionGranted(RC_PERMISSION_CAMERA_AND_WRITE_EXTERNAL_STORAGE)
    private void browseKycDocumentTwo() {

        String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

        if (EasyPermissions.hasPermissions(this, permissions)) {
            showBrowseDialog(RC_CAPTURE_KYC_TWO_IMAGE, RC_SELECT_KYC_TWO_DOCUMENT);
        } else {
            EasyPermissions.requestPermissions(
                    this,
                    "Need Camera and Storage Permissions",
                    RC_PERMISSION_CAMERA_AND_WRITE_EXTERNAL_STORAGE,
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

    private void openCamera(int requestCode) {

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
                mPhotoURI = FileProvider.getUriForFile(this,
                        "local.impactlife.cubetalk.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mPhotoURI);
                takePictureIntent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, ONE_MB);
                startActivityForResult(takePictureIntent, requestCode);
            }
        }
    }

    private void openFileChooser(int requestCode) {

        String permission = Manifest.permission.READ_EXTERNAL_STORAGE;

        if (EasyPermissions.hasPermissions(this, permission)) {
            String[] mimeTypes = {
                    "image/jpeg", // .jpg or jpeg
                    "image/png", // .png
                    "application/pdf" //.pdf
            };

            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("*/*");
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            startActivityForResult(intent, requestCode);
        } else {
            EasyPermissions.requestPermissions(
                    this,
                    "Need Storage Permissions",
                    RC_PERMISSION_READ_EXTERNAL_STORAGE,
                    permission
            );
        }
    }

    private void getSpecialities() {

        ExpertRegistrationService service = ServiceBuilder.buildService(ExpertRegistrationService.class);
        Call<SpecialityResponse> createRequest = service.getSpecialitiesAndTopics(mSharedPreferences.getString(User.TOKEN, ""));

        createRequest.enqueue(new Callback<SpecialityResponse>() {

            @Override
            public void onResponse(@NotNull Call<SpecialityResponse> call, @NotNull Response<SpecialityResponse> response) {

                mSpecialities = response.body().getData();

                for (Speciality speciality : mSpecialities) {

                    if (speciality.getTopics().size() == 0)
                        continue;

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    );

                    MaterialCheckBox checkBox = new MaterialCheckBox(ExpertRegistrationStepOneActivity.this);
                    checkBox.setText(speciality.getName());
                    checkBox.setTag(speciality.getId());
                    checkBox.setLayoutParams(params);
                    checkBox.setOnCheckedChangeListener(ExpertRegistrationStepOneActivity.this);
                    mConsultingCategoriesLayout.addView(checkBox);
                }
                mLoadingLayout.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(@NotNull Call<SpecialityResponse> call, @NotNull Throwable throwable) {
                Log.d(TAG, "onResponse: throwable: " + throwable);
            }
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

    @Override
    public void onClick(View v) {

    }

    private void openKycOnePicker() {
        String[] mimeTypes = {
                "image/jpeg", // .jpg or jpeg
                "image/png", // .png
                "application/pdf" //.pdf
        };


        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        startActivityForResult(intent, RC_SELECT_KYC_ONE_DOCUMENT);
    }

    private void openKycTwoPicker() {
        String[] mimeTypes = {
                "image/jpeg", // .jpg or jpeg
                "image/png", // .png
                "application/pdf" //.pdf
        };

        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        startActivityForResult(intent, RC_SELECT_KYC_TWO_DOCUMENT);
    }

    private void proceedToStepTwo() {

        if (!areFieldsValidated()) {
            return;
        }

        mExpertRegistrationRequest.setAddress(mAddressEdit.getText().toString());
        mExpertRegistrationRequest.setProfessionalSummary(mProfessionalSummaryEdit.getText().toString());
        mExpertRegistrationRequest.setKeyAccomplishment(mKeyAccomplishmentEdit.getText().toString());
        mExpertRegistrationRequest.setConsultationLanguages(mLanguagesForConsultationEdit.getText().toString());
        mExpertRegistrationRequest.setYearsOfExperience(Integer.parseInt(mYearsOfExperienceEdit.getText().toString()));
        mExpertRegistrationRequest.setExpertCvDocumentUri(mExpertCvDocumentUri);
        mExpertRegistrationRequest.setExpertKycOneDocumentTitle(mUploadedKycOneTitleEdit.getText().toString());
        mExpertRegistrationRequest.setExpertKycOneDocumentUri(mExpertKycOneImageUri);
        mExpertRegistrationRequest.setExpertKycTwoDocumentTitle(mUploadKycTwoTitleEdit.getText().toString());
        mExpertRegistrationRequest.setExpertKycTwoDocumentUri(mExpertKycTwoImageUri);

        Intent intent = new Intent(this, ExpertRegistrationStepTwoActivity.class);
        intent.putExtra(ExpertRegistrationRequest.EXPERT, mExpertRegistrationRequest);
        intent.putParcelableArrayListExtra(SpecialityResponse.SELECTED_SPECIALITIES, mSelectedSpecialities);
        startActivity(intent);
    }

    private boolean areFieldsValidated() {
        mAddressTextInput.setError("");
        mProfessionalSummaryTextInput.setError("");
        mKeyAccomplishmentTextInput.setError("");
        mLanguagesForConsultationTextInput.setError("");
        mYearsOfExperienceTextInput.setError("");

        String address = mAddressEdit.getText().toString();
        String professionalSummary = mProfessionalSummaryEdit.getText().toString();
        String keyAccomplishment = mKeyAccomplishmentEdit.getText().toString();
        String languagesForConsultation = mLanguagesForConsultationEdit.getText().toString();
        String yearOfExperience = mYearsOfExperienceEdit.getText().toString();

        if (TextUtils.isEmpty(address)) {
            showError(mAddressTextInput, "Address is empty");
            return false;
        }

        if (TextUtils.isEmpty(professionalSummary)) {
            showError(mProfessionalSummaryTextInput, "Professional Summary is empty");
            return false;
        }

        if (TextUtils.isEmpty(keyAccomplishment)) {
            showError(mKeyAccomplishmentTextInput, "Key Accomplishment is empty");
            return false;
        }

        if (TextUtils.isEmpty(languagesForConsultation)) {
            showError(mLanguagesForConsultationTextInput, "Languages For Consultation is empty");
            return false;
        }

        boolean isAnyConsultingCategorySelected = false;
        int childCount = mConsultingCategoriesLayout.getChildCount();
        for (int i = 0; i < childCount; ++i) {
            View view = mConsultingCategoriesLayout.getChildAt(i);
            if (view instanceof MaterialCheckBox) {
                MaterialCheckBox checkBox = (MaterialCheckBox) view;
                if (checkBox.isChecked()) {
                    isAnyConsultingCategorySelected = true;
                }
            }
        }
        if (!isAnyConsultingCategorySelected) {
            mConsultationCategoriesText.setError("Consultation Category not selected");
            showSnackbar("Consultation Category not selected");
            return false;
        }

        if (TextUtils.isEmpty(yearOfExperience)) {
            showError(mYearsOfExperienceTextInput, "Years of Experience is empty");
            return false;
        } else if (!Pattern.compile("^([5-9]|1[0-9]|2[0-5])$").matcher(yearOfExperience).matches()) {
            showError(mYearsOfExperienceTextInput, "Years of Experience is invalid");
            return false;
        }

        if (mExpertCvDocumentUri == null) {
            showSnackbar("Expert CV not selected");
            return false;
        }

        if (mExpertKycOneImageUri != null && TextUtils.isEmpty(mUploadedKycOneTitleEdit.getText().toString())) {
            showSnackbar("Please enter type of KYC document 1");
            return false;
        }

        if (mExpertKycTwoImageUri != null && TextUtils.isEmpty(mUploadKycTwoTitleEdit.getText().toString())) {
            showSnackbar("Please enter type of KYC document 2");
            return false;
        }

        return true;
    }

    private void showError(TextInputLayout textInputLayout, String errorString) {
        setFocusOnTextInputLayout(textInputLayout);
        showErrorOnTextInputLayout(textInputLayout, errorString);
        showSnackbar(errorString);
    }

    private void setFocusOnTextInputLayout(TextInputLayout textInputLayout) {
        if (textInputLayout.getEditText() != null) {
            textInputLayout.getEditText().requestFocus();
        }
    }

    private void showErrorOnTextInputLayout(TextInputLayout textInputLayout, String errorString) {
        textInputLayout.setError(errorString);
    }

    private void showSnackbar(String errorString) {
        Snackbar.make(mContentBinding.getRoot(), errorString, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        hideSoftKeyboard(buttonView);

        mConsultationCategoriesText.setError(null);
        Log.d(TAG, "onCheckedChanged: Tag: " + buttonView.getTag());
        String specialityId = (String) buttonView.getTag();

        Speciality speciality = findSpeciality(specialityId, mSpecialities);
        if (isChecked) {
            mSelectedSpecialities.add(speciality);
        } else {
            mSelectedSpecialities.remove(speciality);
        }

        Log.d(TAG, "onCheckedChanged: mSelectedSpecialities.size(): " + mSelectedSpecialities.size());
    }

    private Speciality findSpeciality(String specialityId, List<Speciality> specialities) {
        for (Speciality speciality : specialities) {
            if (TextUtils.equals(speciality.getId(), specialityId)) {
                return speciality;
            }
        }
        return null;
    }

    public void hideSoftKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Uri uri = data != null ? data.getData() : null;
        if (uri != null) {
            Log.d(TAG, "onActivityResult: " + uri);
        }

        if (requestCode == RC_SELECT_CV_DOCUMENT && resultCode == RESULT_OK) {
            Log.d(TAG, "onActivityResult: RC_SELECT_CV_DOCUMENT");
            if (!isSelectedDocumentUnderOneMegabyte(uri)) {
                Log.d(TAG, "onActivityResult: File selected is more than 1 MB");
                showSnackbar("File selected is more than 1 MB");
                return;
            }

            mUploadedCvNameText.setText(getSelectedDocumentName(uri));
            mExpertCvDocumentUri = uri;
        } else if (requestCode == RC_CAPTURE_KYC_ONE_IMAGE && resultCode == RESULT_OK) {
            Log.d(TAG, "onActivityResult: RC_CAPTURE_KYC_ONE_IMAGE");
            Uri contentUri = Uri.fromFile(new File(currentPhotoPath));

            mUploadedKycOneNameText.setText(currentPhotoPath.substring(currentPhotoPath.lastIndexOf("/") + 1));
            mExpertKycOneImageUri = contentUri;
        } else if (requestCode == RC_SELECT_KYC_ONE_DOCUMENT && resultCode == RESULT_OK) {
            Log.d(TAG, "onActivityResult: RC_SELECT_KYC_ONE_DOCUMENT");
            if (!isSelectedDocumentUnderOneMegabyte(uri)) {
                Log.d(TAG, "onActivityResult: File selected is more than 1 MB");
                showSnackbar("File selected is more than 1 MB");
                return;
            }
            mUploadedKycOneNameText.setText(getSelectedDocumentName(uri));
            mExpertKycOneImageUri = uri;

        } else if (requestCode == RC_CAPTURE_KYC_TWO_IMAGE && resultCode == RESULT_OK) {
            Log.d(TAG, "onActivityResult: RC_CAPTURE_KYC_TWO_IMAGE");
            Uri contentUri = Uri.fromFile(new File(currentPhotoPath));
            mUploadedKycTwoNameText.setText(currentPhotoPath.substring(currentPhotoPath.lastIndexOf("/") + 1));
            mExpertKycTwoImageUri = contentUri;

        } else if (requestCode == RC_SELECT_KYC_TWO_DOCUMENT && resultCode == RESULT_OK) {
            Log.d(TAG, "onActivityResult: RC_SELECT_KYC_TWO_DOCUMENT");
            if (!isSelectedDocumentUnderOneMegabyte(uri)) {
                Log.d(TAG, "onActivityResult: File selected is more than 1 MB");
                showSnackbar("File selected is more than 1 MB");
                return;
            }

            mUploadedKycTwoNameText.setText(getSelectedDocumentName(uri));
            mExpertKycTwoImageUri = uri;
        }
    }

    private String getSelectedDocumentName(Uri uri) {
        // The query, because it only applies to a single document, returns only
        // one row. There's no need to filter, sort, or select fields,
        // because we want all fields for one document.

        try (Cursor cursor = getContentResolver().query(
                uri,
                null,
                null,
                null,
                null,
                null
        )) {
            // moveToFirst() returns false if the cursor has 0 rows. Very handy for
            // "if there's anything to look at, look at it" conditionals.
            if (cursor != null && cursor.moveToFirst()) {
                // Note it's called "Display Name". This is
                // provider-specific, and might not necessarily be the file name.
                String displayName = cursor.getString(
                        cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                Log.i(TAG, "Display Name: " + displayName);
                return displayName;
            }
        }
        return "";
    }

    private boolean isSelectedDocumentUnderOneMegabyte(Uri uri) {
        // The query, because it only applies to a single document, returns only
        // one row. There's no need to filter, sort, or select fields,
        // because we want all fields for one document.

        try (Cursor cursor = getContentResolver().query(
                uri,
                null,
                null,
                null,
                null,
                null
        )) {
            // moveToFirst() returns false if the cursor has 0 rows. Very handy for
            // "if there's anything to look at, look at it" conditionals.
            if (cursor != null && cursor.moveToFirst()) {
                // Note it's called "Display Name". This is
                // provider-specific, and might not necessarily be the file name.
                String displayName = cursor.getString(
                        cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                Log.i(TAG, "Display Name: " + displayName);

                int sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE);
                // If the size is unknown, the value stored is null. But because an
                // int can't be null, the behavior is implementation-specific,
                // and unpredictable. So as
                // a rule, check if it's null before assigning to an int. This will
                // happen often: The storage API allows for remote files, whose
                // size might not be locally known.
//                String size;
                int size;
                if (!cursor.isNull(sizeIndex)) {
                    // Technically the column stores an int, but cursor.getString()
                    // will do the conversion automatically.
//                    size = cursor.getString(sizeIndex);
                    size = cursor.getInt(sizeIndex);
                    return size <= ONE_MB;
                } /*else {
//                    size = "Unknown";

                }*/
//                Log.i(TAG, "Size: " + size);
            }
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
