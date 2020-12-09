package local.impactlife.cubetalk.activities;

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
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import local.impactlife.cubetalk.R;
import local.impactlife.cubetalk.databinding.ActivityExpertViewUploadDocumentBinding;
import local.impactlife.cubetalk.databinding.ContentExpertViewUploadDocumentBinding;
import local.impactlife.cubetalk.models.User;
import local.impactlife.cubetalk.models.common.ExpertKycDocOne;
import local.impactlife.cubetalk.models.common.ExpertKycDocTwo;
import local.impactlife.cubetalk.models.expert_document_upload.ExpertDocumentUploadResponseBody;
import local.impactlife.cubetalk.models.get_expert_documents.ExpertDocumentsFetchResponseBody;
import local.impactlife.cubetalk.services.api.ExpertService;
import local.impactlife.cubetalk.services.api.ServiceBuilder;
import local.impactlife.cubetalk.utils.FileUtils;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExpertViewUploadDocumentActivity extends AppCompatActivity {

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

    private SharedPreferences mSharedPreferences;
    private String mUserId;

    private MaterialButton mUploadCvButton;
    private TextView mUploadedCvNameText;
    private MaterialButton mUploadKycOneButton;
    private EditText mUploadedKycOneTitleEdit;
    private TextView mUploadedKycOneNameText;
    private MaterialButton mUploadKycTwoButton;
    private EditText mUploadedKycTwoTitleEdit;
    private TextView mUploadedKycTwoNameText;
    private MaterialButton mUpdateExpertProfileButton;
    private Uri mPhotoURI;
    private File mExpertCvFile;
    private File mExpertKycOneImageFile;
    private File mExpertKycTwoImageFile;
    private ActivityExpertViewUploadDocumentBinding mActivityBinding;
    private ContentExpertViewUploadDocumentBinding mContentBinding;
    private MaterialButton mViewSelectedCvDocumentButton;
    private MaterialButton mViewSelectedKycOneDocumentButton;
    private MaterialButton mViewSelectedKycTwoDocumentButton;
    //    private View mLoadingLayout;

    private View mLoadingLayout;
    private Uri mExpertCvDocumentUri;
    private Uri mExpertKycOneImageUri;
    private Uri mExpertKycTwoImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //        setContentView(R.layout.activity_expert_view_upload_document);
        mActivityBinding = ActivityExpertViewUploadDocumentBinding.inflate(LayoutInflater.from(this));
        setContentView(mActivityBinding.getRoot());

        mLoadingLayout = mActivityBinding.loading.getRoot();

        mContentBinding = mActivityBinding.contentExpertEditProfile;

        mSharedPreferences = getSharedPreferences(User.PREFERENCE_NAME, MODE_PRIVATE);

        mUserId = mSharedPreferences.getString(User.ID, "");

        Toolbar toolbar = mActivityBinding.toolbar;
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mUploadCvButton = mContentBinding.btnUploadCv;
        mUploadedCvNameText = mContentBinding.tvUploadedCvName;
        mViewSelectedCvDocumentButton = mContentBinding.btnViewCvDocument;

        mUploadKycOneButton = mContentBinding.btnUploadKycOne;
        mUploadedKycOneTitleEdit = mContentBinding.etUploadedKycOneTitle;
        mUploadedKycOneNameText = mContentBinding.tvUploadedKycOneName;
        mViewSelectedKycOneDocumentButton = mContentBinding.btnViewKycOneDocument;

        mUploadKycTwoButton = mContentBinding.btnUploadKycTwo;
        mUploadedKycTwoTitleEdit = mContentBinding.etUploadedKycTwoTitle;
        mUploadedKycTwoNameText = mContentBinding.tvUploadedKycTwoName;
        mViewSelectedKycTwoDocumentButton = mContentBinding.btnViewKycTwoDocument;

        mUpdateExpertProfileButton = mContentBinding.btnUpdateExpertProfile;

        mUploadCvButton.setOnClickListener(view -> openCvPicker());
        mUploadKycOneButton.setOnClickListener(view -> showBrowseDialog(RC_CAPTURE_KYC_ONE_IMAGE, RC_SELECT_KYC_ONE_DOCUMENT));
        mUploadKycTwoButton.setOnClickListener(view -> showBrowseDialog(RC_CAPTURE_KYC_TWO_IMAGE, RC_SELECT_KYC_TWO_DOCUMENT));

        mUpdateExpertProfileButton.setOnClickListener(v -> {
            hideSoftKeyboard(v);
            updateExpertProfile();
        });

        showUploadedDocuments();
    }

    private void showUploadedDocuments() {
        showProgressLayout();
        getUploadedDocuments();
    }

    private void getUploadedDocuments() {
        ExpertService expertService = ServiceBuilder.buildService(ExpertService.class);
        Call<ExpertDocumentsFetchResponseBody> createRequest = expertService.getExpertDocuments(mSharedPreferences.getString(User.TOKEN, ""),mUserId);

        createRequest.enqueue(new Callback<ExpertDocumentsFetchResponseBody>() {
            @Override
            public void onResponse(@NotNull Call<ExpertDocumentsFetchResponseBody> call, @NotNull Response<ExpertDocumentsFetchResponseBody> response) {
                Log.d(TAG, "onResponse: response.body(): " + response.body());

                ExpertDocumentsFetchResponseBody responseBody = response.body();

                String cvFullPath = responseBody.getData().getExpertDocuments().getExpertCv().getImage();
                String cvFileName = cvFullPath.substring(cvFullPath.lastIndexOf("/") + 1);
                mUploadedCvNameText.setText(cvFileName);
                mViewSelectedCvDocumentButton.setVisibility(View.VISIBLE);
                mViewSelectedCvDocumentButton.setOnClickListener(v -> {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(cvFullPath));
                    startActivity(intent);
                });

                ExpertKycDocOne expertKycDocOne = responseBody.getData().getExpertDocuments().getExpertKycDocOne();
                String kycOneDocumentFullPath = expertKycDocOne.getImage();
                if (kycOneDocumentFullPath != null) {
                    String kycOneDocumentFileName = kycOneDocumentFullPath.substring(kycOneDocumentFullPath.lastIndexOf("/") + 1);
                    mUploadKycOneButton.setEnabled(false);
                    mUploadedKycOneTitleEdit.setEnabled(false);
                    mUploadedKycOneTitleEdit.setText(expertKycDocOne.getTitle());
                    mUploadedKycOneNameText.setText(kycOneDocumentFileName);
                    mViewSelectedKycOneDocumentButton.setVisibility(View.VISIBLE);
                    mViewSelectedKycOneDocumentButton.setOnClickListener(v -> {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(kycOneDocumentFullPath));
                        startActivity(intent);
                    });
                }

                ExpertKycDocTwo expertKycDocTwo = responseBody.getData().getExpertDocuments().getExpertKycDocTwo();
                String kycTwoDocumentFullPath = expertKycDocTwo.getImage();
                if (kycTwoDocumentFullPath != null) {
                    String kycTwoDocumentFileName = kycTwoDocumentFullPath.substring(kycTwoDocumentFullPath.lastIndexOf("/") + 1);
                    mUploadKycTwoButton.setEnabled(false);
                    mUploadedKycTwoTitleEdit.setEnabled(false);
                    mUploadedKycTwoTitleEdit.setText(expertKycDocTwo.getTitle());
                    mUploadedKycTwoNameText.setText(kycTwoDocumentFileName);
                    mViewSelectedKycTwoDocumentButton.setVisibility(View.VISIBLE);
                    mViewSelectedKycTwoDocumentButton.setOnClickListener(v -> {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(kycTwoDocumentFullPath));
                        startActivity(intent);
                    });
                }

                if (kycOneDocumentFullPath != null && kycTwoDocumentFullPath != null) {
                    mUpdateExpertProfileButton.setEnabled(false);
                }

                hideProgressLayout();
            }

            @Override
            public void onFailure(@NotNull Call<ExpertDocumentsFetchResponseBody> call, @NotNull Throwable throwable) {
                Log.d(TAG, "onResponse: throwable.getMessage() : " + throwable.getMessage());
                hideProgressLayout();
            }
        });
    }

    private void updateExpertProfile() {

        if (!areFieldsValidated()) {
            return;
        }

        showProgressLayout();

        mExpertCvDocumentUri = Uri.parse("content://com.android.providers.downloads.documents/document/msf%3A27");
        File mExpertCvFile = FileUtils.getFile(this, getContentResolver(), mExpertCvDocumentUri);

        RequestBody expertCvTitleRequest = RequestBody.create("Resume", MediaType.parse("plain/text"));
        RequestBody expertCvDocumentRequest = RequestBody.create(mExpertCvFile, MediaType.parse("application/*"));
        MultipartBody.Part expertCvImagePart = MultipartBody.Part.createFormData("expert_cv_image", mExpertCvFile.getName(), expertCvDocumentRequest);

//        RequestBody expertCvTitleRequest = null;
//        MultipartBody.Part expertCvImagePart = null;

        File expertKycOneImageFile = FileUtils.getFile(this, getContentResolver(), mExpertKycOneImageUri);
        RequestBody expertKycOneTitleRequest = null;
        MultipartBody.Part expertKycOneImagePart = null;
        if (expertKycOneImageFile != null) {
            expertKycOneTitleRequest = RequestBody.create(mUploadedKycOneTitleEdit.getText().toString(), MediaType.parse("plain/text"));
            RequestBody expertKycOneImageRequest = RequestBody.create(expertKycOneImageFile, MediaType.parse("*/*"));
            expertKycOneImagePart = MultipartBody.Part.createFormData("expert_kyc_doc_1_image", expertKycOneImageFile.getName(), expertKycOneImageRequest);
        }

        File expertKycTwoImageFile = FileUtils.getFile(this, getContentResolver(), mExpertKycTwoImageUri);
        RequestBody expertKycTwoTitleRequest = null;
        MultipartBody.Part expertKycTwoImagePart = null;
        if (expertKycTwoImageFile != null) {
            expertKycTwoTitleRequest = RequestBody.create(mUploadedKycTwoTitleEdit.getText().toString(), MediaType.parse("plain/text"));
            RequestBody expertKycTwoImageRequest = RequestBody.create(expertKycTwoImageFile, MediaType.parse("*/*"));
            expertKycTwoImagePart = MultipartBody.Part.createFormData("expert_kyc_doc_2_image", expertKycTwoImageFile.getName(), expertKycTwoImageRequest);
        }

        ExpertService expertService = ServiceBuilder.buildService(ExpertService.class);
        Call<ExpertDocumentUploadResponseBody> createRequest = expertService.uploadExpertDocument(
                mSharedPreferences.getString(User.TOKEN, ""),
                mUserId,
                expertCvTitleRequest,
                expertCvImagePart,
                expertKycOneTitleRequest,
                expertKycOneImagePart,
                expertKycTwoTitleRequest,
                expertKycTwoImagePart
        );

        createRequest.enqueue(new Callback<ExpertDocumentUploadResponseBody>() {
            @Override
            public void onResponse(@NotNull Call<ExpertDocumentUploadResponseBody> call, @NotNull Response<ExpertDocumentUploadResponseBody> response) {
                ExpertDocumentUploadResponseBody responseBody = response.body();
                Log.d(TAG, "onResponse: responseBody.getMessage(): " + responseBody.getMessage());
                Toast.makeText(
                        ExpertViewUploadDocumentActivity.this,
                        responseBody.getMessage(),
                        Toast.LENGTH_SHORT
                ).show();
                hideProgressLayout();
                finish();
            }

            @Override
            public void onFailure(@NotNull Call<ExpertDocumentUploadResponseBody> call, @NotNull Throwable throwable) {
                Log.d(TAG, "onFailure: throwable.getMessage(): " + throwable.getMessage());
            }
        });
    }

    private void showProgressLayout() {
        mLoadingLayout.setVisibility(View.VISIBLE);
    }

    private void hideProgressLayout() {
        mLoadingLayout.setVisibility(View.GONE);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }

    private void openCvPicker() {
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

    private void openCamera(int requestCode) {
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
                    mPhotoURI = FileProvider.getUriForFile(this,
                            "local.impactlife.cubetalk.fileprovider",
                            photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mPhotoURI);
                    startActivityForResult(takePictureIntent, requestCode);
                }
            }
        } else {
            EasyPermissions.requestPermissions(
                    this,
                    "Need Camera and Storage Permissions",
                    RC_PERMISSION_CAMERA_AND_WRITE_EXTERNAL_STORAGE,
                    permissions
            );
        }
    }

    private void openFileChooser(int requestCode) {
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        Uri uri = data != null ? data.getData() : null;
//        if (uri != null) {
//            Log.d(TAG, "onActivityResult: " + uri);
//        }
//
//        if (requestCode == RC_SELECT_CV_DOCUMENT && resultCode == RESULT_OK) {
//            Log.d(TAG, "onActivityResult: RC_SELECT_CV_DOCUMENT");
//            if (!isSelectedDocumentUnderOneMegabyte(uri)) {
//                Log.d(TAG, "onActivityResult: File selected is more than 1 MB");
//                showSnackbar("File selected is more than 1 MB");
//                return;
//            }
//
//            mUploadedCvNameText.setText(getSelectedDocumentName(uri));
//            mExpertCvFile = FileUtils.getFile(this, uri);
//        } else if (requestCode == RC_CAPTURE_KYC_ONE_IMAGE && resultCode == RESULT_OK) {
//            Log.d(TAG, "onActivityResult: RC_CAPTURE_KYC_ONE_IMAGE");
//            Uri contentUri = Uri.fromFile(new File(currentPhotoPath));
//            if (!isSelectedDocumentUnderOneMegabyte(contentUri)) {
//                Log.d(TAG, "onActivityResult: File selected is more than 1 MB");
//                showSnackbar("Image captured is more than 1 MB");
//                return;
//            }
//            mUploadedKycTwoNameText.setText(getSelectedDocumentName(contentUri));
//            mExpertKycOneImageFile = new File(currentPhotoPath);
//        } else if (requestCode == RC_SELECT_KYC_ONE_DOCUMENT && resultCode == RESULT_OK) {
//            Log.d(TAG, "onActivityResult: RC_SELECT_KYC_ONE_DOCUMENT");
//            if (!isSelectedDocumentUnderOneMegabyte(uri)) {
//                Log.d(TAG, "onActivityResult: File selected is more than 1 MB");
//                showSnackbar("File selected is more than 1 MB");
//                return;
//            }
//            mUploadedKycOneNameText.setText(getSelectedDocumentName(uri));
//            mExpertKycOneImageFile = FileUtils.getFile(this, uri);
//
//        } else if (requestCode == RC_CAPTURE_KYC_TWO_IMAGE && resultCode == RESULT_OK) {
//            Log.d(TAG, "onActivityResult: RC_CAPTURE_KYC_TWO_IMAGE");
//            Uri contentUri = Uri.fromFile(new File(currentPhotoPath));
//            if (!isSelectedDocumentUnderOneMegabyte(contentUri)) {
//                Log.d(TAG, "onActivityResult: File selected is more than 1 MB");
//                showSnackbar("Image captured is more than 1 MB");
//                return;
//            }
//            mUploadedKycTwoNameText.setText(getSelectedDocumentName(contentUri));
//            mExpertKycTwoImageFile = new File(currentPhotoPath);
//        } else if (requestCode == RC_SELECT_KYC_TWO_DOCUMENT && resultCode == RESULT_OK) {
//            Log.d(TAG, "onActivityResult: RC_SELECT_KYC_TWO_DOCUMENT");
//            if (!isSelectedDocumentUnderOneMegabyte(uri)) {
//                Log.d(TAG, "onActivityResult: File selected is more than 1 MB");
//                showSnackbar("File selected is more than 1 MB");
//                return;
//            }
//
//            mUploadedKycTwoNameText.setText(getSelectedDocumentName(uri));
//            mExpertKycTwoImageFile = FileUtils.getFile(this, uri);
//        }

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
            mUpdateExpertProfileButton.setEnabled(true);
        } else if (requestCode == RC_CAPTURE_KYC_ONE_IMAGE && resultCode == RESULT_OK) {
            Log.d(TAG, "onActivityResult: RC_CAPTURE_KYC_ONE_IMAGE");
//            Uri contentUri = Uri.fromFile(new File(currentPhotoPath));
//            if (!isSelectedDocumentUnderOneMegabyte(contentUri)) {
//                Log.d(TAG, "onActivityResult: File selected is more than 1 MB");
//                showSnackbar("Image captured is more than 1 MB");
//                return;
//            }
            mUploadedKycOneNameText.setText(currentPhotoPath.substring(currentPhotoPath.lastIndexOf("/") + 1));
            mExpertKycOneImageUri = mPhotoURI;
            mUpdateExpertProfileButton.setEnabled(true);
        } else if (requestCode == RC_SELECT_KYC_ONE_DOCUMENT && resultCode == RESULT_OK) {
            Log.d(TAG, "onActivityResult: RC_SELECT_KYC_ONE_DOCUMENT");
            if (!isSelectedDocumentUnderOneMegabyte(uri)) {
                Log.d(TAG, "onActivityResult: File selected is more than 1 MB");
                showSnackbar("File selected is more than 1 MB");
                return;
            }
            mUploadedKycOneNameText.setText(getSelectedDocumentName(uri));
            mExpertKycOneImageUri = uri;
            mUpdateExpertProfileButton.setEnabled(true);
        } else if (requestCode == RC_CAPTURE_KYC_TWO_IMAGE && resultCode == RESULT_OK) {
            Log.d(TAG, "onActivityResult: RC_CAPTURE_KYC_TWO_IMAGE");
//            Uri contentUri = Uri.fromFile(new File(currentPhotoPath));
//            if (!isSelectedDocumentUnderOneMegabyte(contentUri)) {
//                Log.d(TAG, "onActivityResult: File selected is more than 1 MB");
//                showSnackbar("Image captured is more than 1 MB");
//                return;
//            }
            mUploadedKycTwoNameText.setText(currentPhotoPath.substring(currentPhotoPath.lastIndexOf("/") + 1));
            mExpertKycTwoImageUri = mPhotoURI;
            mUpdateExpertProfileButton.setEnabled(true);
        } else if (requestCode == RC_SELECT_KYC_TWO_DOCUMENT && resultCode == RESULT_OK) {
            Log.d(TAG, "onActivityResult: RC_SELECT_KYC_TWO_DOCUMENT");
            if (!isSelectedDocumentUnderOneMegabyte(uri)) {
                Log.d(TAG, "onActivityResult: File selected is more than 1 MB");
                showSnackbar("File selected is more than 1 MB");
                return;
            }

            mUploadedKycTwoNameText.setText(getSelectedDocumentName(uri));
            mExpertKycTwoImageUri = uri;
            mUpdateExpertProfileButton.setEnabled(true);
        }
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    private boolean areFieldsValidated() {
        if (mExpertKycOneImageUri != null && TextUtils.isEmpty(mUploadedKycOneTitleEdit.getText().toString())) {
            showSnackbar("Please enter type of KYC document 1");
            return false;
        }

        if (mExpertKycTwoImageUri != null && TextUtils.isEmpty(mUploadedKycTwoTitleEdit.getText().toString())) {
            showSnackbar("Please enter type of KYC document 2");
            return false;
        }

        return true;
    }

    public void hideSoftKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
