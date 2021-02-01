package com.cubetalktest.cubetalk.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.gridlayout.widget.GridLayout;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

import com.cubetalktest.cubetalk.BuildConfig;
import com.cubetalktest.cubetalk.R;
import com.cubetalktest.cubetalk.databinding.ActivityExpertRegistrationStepTwoBinding;
import com.cubetalktest.cubetalk.databinding.ContentExpertRegistrationStepTwoBinding;
import com.cubetalktest.cubetalk.enums.ExpertApplicationStatus;
import com.cubetalktest.cubetalk.models.User;
import com.cubetalktest.cubetalk.models.common.ConsultingSlot;
import com.cubetalktest.cubetalk.models.common.ConsultingSlotDay;
import com.cubetalktest.cubetalk.models.common.ConsultingSlotDuration;
import com.cubetalktest.cubetalk.models.common.Speciality;
import com.cubetalktest.cubetalk.models.common.Topic;
import com.cubetalktest.cubetalk.models.expert_document_upload.ExpertDocumentUploadResponseBody;
import com.cubetalktest.cubetalk.models.expert_registration.ExpertRegistrationRequest;
import com.cubetalktest.cubetalk.models.expert_registration.ExpertRegistrationResponse;
import com.cubetalktest.cubetalk.models.expert_registration.SpecialityResponse;
import com.cubetalktest.cubetalk.models.get_expert_terms.ExpertTermsResponse;
import com.cubetalktest.cubetalk.services.api.ExpertRegistrationService;
import com.cubetalktest.cubetalk.services.api.ExpertService;
import com.cubetalktest.cubetalk.services.api.ServiceBuilder;
import com.cubetalktest.cubetalk.utils.FileUtils;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExpertRegistrationStepTwoActivity
        extends AppCompatActivity
        implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private static final String TAG = ExpertRegistrationStepTwoActivity.class.getSimpleName();

    private static final int RC_PERMISSION_READ_EXTERNAL_STORAGE = 10;

    private Intent mIntent;

    private TextInputLayout mBankAccountNumberLayout;
    private TextInputEditText mBankAccountNumberEdit;
    private TextInputLayout mBankNameLayout;
    private TextInputEditText mBankNameEdit;
    private TextInputLayout mIfscCodeLayout;
    private TextInputEditText mIfscCodeEdit;
    private MaterialCheckBox mWeekdaysCheckBox;
    private ConstraintLayout mWeekdaysSlotsConstraintLayout;
    private Spinner mWeekdaysSlotOneFromSpinner;
    private Spinner mWeekdaysSlotOneToSpinner;
    private ImageButton mWeekdaysSlotToggleImageButton;
    private GridLayout mWeekdaysSlotTwoGridLayout;
    private Spinner mWeekdaysSlotTwoFromSpinner;
    private Spinner mWeekdaysSlotTwoToSpinner;
    private MaterialCheckBox mSaturdayCheckBox;
    private ConstraintLayout mSaturdaySlotsConstraintLayout;
    private Spinner mSaturdaySlotOneFromSpinner;
    private Spinner mSaturdaySlotOneToSpinner;
    private ImageButton mSaturdaySlotToggleImageButton;
    private GridLayout mSaturdaySlotTwoGridLayout;
    private Spinner mSaturdaySlotTwoFromSpinner;
    private Spinner mSaturdaySlotTwoToSpinner;
    private MaterialCheckBox mSundayCheckBox;
    private ConstraintLayout mSundaySlotsConstraintLayout;
    private Spinner mSundaySlotOneFromSpinner;
    private Spinner mSundaySlotOneToSpinner;
    private ImageButton mSundaySlotToggleImageButton;
    private GridLayout mSundaySlotTwoGridLayout;
    private Spinner mSundaySlotTwoFromSpinner;
    private Spinner mSundaySlotTwoToSpinner;
    private MaterialCheckBox m12MinutesCheckBox;
    private MaterialCheckBox m25MinutesCheckBox;
    private MaterialCheckBox m50MinutesCheckBox;
    private MaterialCheckBox mExpertTermsAndConditionCheck;
    private MaterialButton mSubmitRegistrationRequestButton;

    private ExpertRegistrationRequest mExpertRegistrationRequest;
    private ArrayList<ExpertRegistrationRequest.ConsultingTopic> mConsultingTopics;
    private ConsultingSlot mConsultingSlot;
    private ConsultingSlotDay mWeekdays;
    private ConsultingSlotDay mSaturdays;
    private ConsultingSlotDay mSundays;
    private ConsultingSlotDuration mConsultingSlotDuration;

    private String mUserId;
    private SharedPreferences mSharedPreferences;
    private ArrayList<Speciality> mSpecialities;
    private LinearLayout mConsultingTopicsLayout;
    private ActivityExpertRegistrationStepTwoBinding mActivityBinding;
    private View mLoadingLayout;
    private ContentExpertRegistrationStepTwoBinding mContentBinding;
    private ExpertRegistrationRequest.ConsultingTopic mConsultingTopic;

    private ArrayList<CharSequence> mConsultingSlotTimings;

    private ArrayList<CharSequence> mWeekdaysSlotOneFromTimings;
    private ArrayList<CharSequence> mWeekdaysSlotOneToTimings;
    private ArrayList<CharSequence> mWeekdaysSlotTwoFromTimings;
    private ArrayList<CharSequence> mWeekdaysSlotTwoToTimings;
    private ArrayList<CharSequence> mSaturdaySlotOneFromTimings;
    private ArrayList<CharSequence> mSaturdaySlotOneToTimings;
    private ArrayList<CharSequence> mSaturdaySlotTwoFromTimings;
    private ArrayList<CharSequence> mSaturdaySlotTwoToTimings;
    private ArrayList<CharSequence> mSundaySlotOneFromTimings;
    private ArrayList<CharSequence> mSundaySlotOneToTimings;
    private ArrayList<CharSequence> mSundaySlotTwoFromTimings;
    private ArrayList<CharSequence> mSundaySlotTwoToTimings;

    private ArrayAdapter<CharSequence> mWeekdaysSlotOneFromAdapter;
    private ArrayAdapter<CharSequence> mWeekdaysSlotOneToAdapter;
    private ArrayAdapter<CharSequence> mWeekdaysSlotTwoFromAdapter;
    private ArrayAdapter<CharSequence> mWeekdaysSlotTwoToAdapter;
    private ArrayAdapter<CharSequence> mSaturdaySlotOneFromAdapter;
    private ArrayAdapter<CharSequence> mSaturdaySlotOneToAdapter;
    private ArrayAdapter<CharSequence> mSaturdaySlotTwoFromAdapter;
    private ArrayAdapter<CharSequence> mSaturdaySlotTwoToAdapter;
    private ArrayAdapter<CharSequence> mSundaySlotOneFromAdapter;
    private ArrayAdapter<CharSequence> mSundaySlotOneToAdapter;
    private ArrayAdapter<CharSequence> mSundaySlotTwoFromAdapter;
    private ArrayAdapter<CharSequence> mSundaySlotTwoToAdapter;

    private ArrayList<CharSequence> mConsultingSlotToTimings;
    private ArrayAdapter<CharSequence> mConsultingSlotFromAdapter;
    private ArrayAdapter<CharSequence> mConsultingSlotToAdapter;
    private ArrayAdapter<CharSequence> mToAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //        setContentView(R.layout.activity_expert_registration_step_two);


        mActivityBinding = ActivityExpertRegistrationStepTwoBinding.inflate(LayoutInflater.from(this));
        setContentView(mActivityBinding.getRoot());

        mContentBinding = mActivityBinding.contentExpertRegistrationStepTwo;

        mLoadingLayout = mActivityBinding.loading.getRoot();

        mIntent = getIntent();
        if (!mIntent.hasExtra(ExpertRegistrationRequest.EXPERT)) {
            if (BuildConfig.DEBUG) mExpertRegistrationRequest = createFakeExpertRegistration();
        } else
            mExpertRegistrationRequest = mIntent.getParcelableExtra(ExpertRegistrationRequest.EXPERT);

        if (!mIntent.hasExtra(SpecialityResponse.SELECTED_SPECIALITIES)) {
            if (BuildConfig.DEBUG) {
                mSpecialities = createFakeSpecialities();
            } else {
                finish();
            }
        } else {
            mSpecialities = mIntent.getParcelableArrayListExtra(SpecialityResponse.SELECTED_SPECIALITIES);
        }

        mSharedPreferences = getSharedPreferences(User.PREFERENCE_NAME, MODE_PRIVATE);
        mUserId = mSharedPreferences.getString(User.ID, "");

        mConsultingTopics = new ArrayList<>();
        mConsultingSlotDuration = new ConsultingSlotDuration();
        mWeekdays = new ConsultingSlotDay();
        mSaturdays = new ConsultingSlotDay();
        mSundays = new ConsultingSlotDay();
        mConsultingSlot = new ConsultingSlot();

        Toolbar toolbar = mActivityBinding.toolbar;
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mBankAccountNumberLayout = mContentBinding.tilBankAccountNumber;
        mBankAccountNumberEdit = mContentBinding.tietBankAccountNumber;

        mBankNameLayout = mContentBinding.tilBankName;
        mBankNameEdit = mContentBinding.tietBankName;

        mIfscCodeLayout = mContentBinding.tilIfscCode;
        mIfscCodeEdit = mContentBinding.tietIfscCode;

        mWeekdaysCheckBox = mContentBinding.cbWeekdays;
        mWeekdaysSlotsConstraintLayout = mContentBinding.clWeekdaysSlots;
        mWeekdaysSlotOneFromSpinner = mContentBinding.spWeekdaySlotOneFrom;
        mWeekdaysSlotOneToSpinner = mContentBinding.spWeekdaySlotOneTo;
        mWeekdaysSlotToggleImageButton = mContentBinding.ibWeekdaysSlotToggle;
        mWeekdaysSlotTwoGridLayout = mContentBinding.glWeekdaysSlotTwo;
        mWeekdaysSlotTwoFromSpinner = mContentBinding.spWeekdaySlotTwoFrom;
        mWeekdaysSlotTwoToSpinner = mContentBinding.spWeekdaySlotTwoTo;

        mSaturdayCheckBox = mContentBinding.cbSaturday;
        mSaturdaySlotsConstraintLayout = mContentBinding.clSaturdaySlots;
        mSaturdaySlotOneFromSpinner = mContentBinding.spSaturdaySlotOneFrom;
        mSaturdaySlotOneToSpinner = mContentBinding.spSaturdaySlotOneTo;
        mSaturdaySlotToggleImageButton = mContentBinding.ibSaturdaySlotToggle;
        mSaturdaySlotTwoGridLayout = mContentBinding.glSaturdaySlotTwo;
        mSaturdaySlotTwoFromSpinner = mContentBinding.spSaturdaySlotTwoFrom;
        mSaturdaySlotTwoToSpinner = mContentBinding.spSaturdaySlotTwoTo;

        mSundayCheckBox = mContentBinding.cbSunday;
        mSundaySlotsConstraintLayout = mContentBinding.clSundaySlots;
        mSundaySlotOneFromSpinner = mContentBinding.spSundaySlotOneFrom;
        mSundaySlotOneToSpinner = mContentBinding.spSundaySlotOneTo;
        mSundaySlotToggleImageButton = mContentBinding.ibSundaySlotToggle;
        mSundaySlotTwoGridLayout = mContentBinding.glSundaySlotTwo;
        mSundaySlotTwoFromSpinner = mContentBinding.spSundaySlotTwoFrom;
        mSundaySlotTwoToSpinner = mContentBinding.spSundaySlotTwoTo;

        mConsultingTopicsLayout = mContentBinding.llConsultingTopics;

        m12MinutesCheckBox = mContentBinding.mcb12Mins;
        m25MinutesCheckBox = mContentBinding.mcb25Mins;
        m50MinutesCheckBox = mContentBinding.mcb50Mins;

        mExpertTermsAndConditionCheck = mContentBinding.cbExpertTermsAndConditions;

        mSubmitRegistrationRequestButton = mContentBinding.btnSubmitRegistrationRequest;

        populateConsultingTopics();

        mConsultingSlotTimings = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.array_consulting_slot)));

        mWeekdaysCheckBox.setOnCheckedChangeListener(this);

        //region Consulting Slot Weekdays
        mWeekdaysSlotOneFromTimings = new ArrayList<>(mConsultingSlotTimings.subList(0, mConsultingSlotTimings.size() - 1));
        mWeekdaysSlotOneFromAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                mWeekdaysSlotOneFromTimings
        );
        mWeekdaysSlotOneFromAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mWeekdaysSlotOneFromSpinner.setAdapter(mWeekdaysSlotOneFromAdapter);
        mWeekdaysSlotOneFromSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mWeekdaysSlotOneToTimings.clear();
                mWeekdaysSlotOneToTimings.addAll(
                        mConsultingSlotTimings.subList(
                                (position + 1),
                                mConsultingSlotTimings.size()
                        )
                );
                mWeekdaysSlotOneToAdapter.notifyDataSetChanged();
                mWeekdaysSlotOneToSpinner.setSelection(0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d(TAG, "onCreate: parent: " + parent);
            }
        });

        mWeekdaysSlotOneToTimings = new ArrayList<>();
        mWeekdaysSlotOneToAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                mWeekdaysSlotOneToTimings
        );
        mWeekdaysSlotOneToAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mWeekdaysSlotOneToSpinner.setAdapter(mWeekdaysSlotOneToAdapter);
        mWeekdaysSlotOneToSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemSelected: parent: " + parent);
                Log.d(TAG, "onItemSelected: view: " + view);
                Log.d(TAG, "onItemSelected: position: " + position);
                Log.d(TAG, "onItemSelected: id: " + id);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d(TAG, "onNothingSelected: parent: " + parent);

            }
        });

        mWeekdaysSlotTwoFromTimings = new ArrayList<>(mConsultingSlotTimings.subList(0, mConsultingSlotTimings.size() - 1));
        mWeekdaysSlotTwoFromAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                mWeekdaysSlotTwoFromTimings
        );
        mWeekdaysSlotTwoFromAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mWeekdaysSlotTwoFromSpinner.setAdapter(mWeekdaysSlotTwoFromAdapter);
        mWeekdaysSlotTwoFromSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemSelected: parent: " + parent);
                Log.d(TAG, "onItemSelected: view: " + view);
                Log.d(TAG, "onItemSelected: position: " + position);
                Log.d(TAG, "onItemSelected: id: " + id);
                mWeekdaysSlotTwoToTimings.clear();
                mWeekdaysSlotTwoToTimings.addAll(
                        mConsultingSlotTimings.subList(
                                (position + 1),
                                mConsultingSlotTimings.size()
                        )
                );
                mWeekdaysSlotTwoToAdapter.notifyDataSetChanged();
                mWeekdaysSlotTwoToSpinner.setSelection(0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d(TAG, "onCreate: parent: " + parent);
            }
        });

        mWeekdaysSlotTwoToTimings = new ArrayList<>();
        mWeekdaysSlotTwoToAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                mWeekdaysSlotTwoToTimings
        );
        mWeekdaysSlotTwoToAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mWeekdaysSlotTwoToSpinner.setAdapter(mWeekdaysSlotTwoToAdapter);
        mWeekdaysSlotTwoToSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onCreate: parent: " + parent);
                Log.d(TAG, "onCreate: view: " + view);
                Log.d(TAG, "onCreate: position: " + position);
                Log.d(TAG, "onCreate: id: " + id);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d(TAG, "onCreate: parent: " + parent);

            }
        });
        //endregion

        //region Consulting Slot Saturday
        mSaturdaySlotOneFromTimings = new ArrayList<>(mConsultingSlotTimings.subList(0, mConsultingSlotTimings.size() - 1));
        mSaturdaySlotOneFromAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                mSaturdaySlotOneFromTimings
        );
        mSaturdaySlotOneFromAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSaturdaySlotOneFromSpinner.setAdapter(mSaturdaySlotOneFromAdapter);
        mSaturdaySlotOneFromSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onCreate: parent: " + parent);
                Log.d(TAG, "onCreate: view: " + view);
                Log.d(TAG, "onCreate: position: " + position);
                Log.d(TAG, "onCreate: id: " + id);
                mSaturdaySlotOneToTimings.clear();
                mSaturdaySlotOneToTimings.addAll(
                        mConsultingSlotTimings.subList(
                                (position + 1),
                                mConsultingSlotTimings.size()
                        )
                );
                mSaturdaySlotOneToAdapter.notifyDataSetChanged();
                mSaturdaySlotOneToSpinner.setSelection(0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d(TAG, "onCreate: parent: " + parent);
            }
        });

        mSaturdaySlotOneToTimings = new ArrayList<>();
        mSaturdaySlotOneToAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                mSaturdaySlotOneToTimings
        );
        mSaturdaySlotOneToAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSaturdaySlotOneToSpinner.setAdapter(mSaturdaySlotOneToAdapter);
        mSaturdaySlotOneToSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onCreate: parent: " + parent);
                Log.d(TAG, "onCreate: view: " + view);
                Log.d(TAG, "onCreate: position: " + position);
                Log.d(TAG, "onCreate: id: " + id);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d(TAG, "onCreate: parent: " + parent);

            }
        });

        mSaturdaySlotTwoFromTimings = new ArrayList<>(mConsultingSlotTimings.subList(0, mConsultingSlotTimings.size() - 1));
        mSaturdaySlotTwoFromAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                mSaturdaySlotTwoFromTimings
        );
        mSaturdaySlotTwoFromAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSaturdaySlotTwoFromSpinner.setAdapter(mSaturdaySlotTwoFromAdapter);
        mSaturdaySlotTwoFromSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onCreate: parent: " + parent);
                Log.d(TAG, "onCreate: view: " + view);
                Log.d(TAG, "onCreate: position: " + position);
                Log.d(TAG, "onCreate: id: " + id);
                mSaturdaySlotTwoToTimings.clear();
                mSaturdaySlotTwoToTimings.addAll(
                        mConsultingSlotTimings.subList(
                                (position + 1),
                                mConsultingSlotTimings.size()
                        )
                );
                mSaturdaySlotTwoToAdapter.notifyDataSetChanged();
                mSaturdaySlotTwoToSpinner.setSelection(0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d(TAG, "onCreate: parent: " + parent);
            }
        });

        mSaturdaySlotTwoToTimings = new ArrayList<>();
        mSaturdaySlotTwoToAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                mSaturdaySlotTwoToTimings
        );
        mSaturdaySlotTwoToAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSaturdaySlotTwoToSpinner.setAdapter(mSaturdaySlotTwoToAdapter);
        mSaturdaySlotTwoToSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onCreate: parent: " + parent);
                Log.d(TAG, "onCreate: view: " + view);
                Log.d(TAG, "onCreate: position: " + position);
                Log.d(TAG, "onCreate: id: " + id);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d(TAG, "onCreate: parent: " + parent);

            }
        });
        //endregion

        //region Consulting Slot Sunday
        mSundaySlotOneFromTimings = new ArrayList<>(mConsultingSlotTimings.subList(0, mConsultingSlotTimings.size() - 1));
        mSundaySlotOneFromAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                mSundaySlotOneFromTimings
        );
        mSundaySlotOneFromAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSundaySlotOneFromSpinner.setAdapter(mSundaySlotOneFromAdapter);
        mSundaySlotOneFromSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onCreate: parent: " + parent);
                Log.d(TAG, "onCreate: view: " + view);
                Log.d(TAG, "onCreate: position: " + position);
                Log.d(TAG, "onCreate: id: " + id);
                mSundaySlotOneToTimings.clear();
                mSundaySlotOneToTimings.addAll(
                        mConsultingSlotTimings.subList(
                                (position + 1),
                                mConsultingSlotTimings.size()
                        )
                );
                mSundaySlotOneToAdapter.notifyDataSetChanged();
                mSundaySlotOneToSpinner.setSelection(0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d(TAG, "onCreate: parent: " + parent);
            }
        });

        mSundaySlotOneToTimings = new ArrayList<>();
        mSundaySlotOneToAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                mSundaySlotOneToTimings
        );
        mSundaySlotOneToAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSundaySlotOneToSpinner.setAdapter(mSundaySlotOneToAdapter);
        mSundaySlotOneToSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onCreate: parent: " + parent);
                Log.d(TAG, "onCreate: view: " + view);
                Log.d(TAG, "onCreate: position: " + position);
                Log.d(TAG, "onCreate: id: " + id);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d(TAG, "onCreate: parent: " + parent);

            }
        });

        mSundaySlotTwoFromTimings = new ArrayList<>(mConsultingSlotTimings.subList(0, mConsultingSlotTimings.size() - 1));
        mSundaySlotTwoFromAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                mSundaySlotTwoFromTimings
        );
        mSundaySlotTwoFromAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSundaySlotTwoFromSpinner.setAdapter(mSundaySlotTwoFromAdapter);
        mSundaySlotTwoFromSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onCreate: parent: " + parent);
                Log.d(TAG, "onCreate: view: " + view);
                Log.d(TAG, "onCreate: position: " + position);
                Log.d(TAG, "onCreate: id: " + id);
                mSundaySlotTwoToTimings.clear();
                mSundaySlotTwoToTimings.addAll(
                        mConsultingSlotTimings.subList(
                                (position + 1),
                                mConsultingSlotTimings.size()
                        )
                );
                mSundaySlotTwoToAdapter.notifyDataSetChanged();
                mSundaySlotTwoToSpinner.setSelection(0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d(TAG, "onCreate: parent: " + parent);
            }
        });

        mSundaySlotTwoToTimings = new ArrayList<>();
        mSundaySlotTwoToAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                mSundaySlotTwoToTimings
        );
        mSundaySlotTwoToAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSundaySlotTwoToSpinner.setAdapter(mSundaySlotTwoToAdapter);
        mSundaySlotTwoToSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onCreate: parent: " + parent);
                Log.d(TAG, "onCreate: view: " + view);
                Log.d(TAG, "onCreate: position: " + position);
                Log.d(TAG, "onCreate: id: " + id);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d(TAG, "onCreate: parent: " + parent);

            }
        });
        //endregion

        mWeekdaysSlotToggleImageButton.setOnClickListener(
                v -> toggleSlotTwo(mWeekdaysSlotToggleImageButton, mWeekdaysSlotTwoGridLayout));
        mSaturdayCheckBox.setOnCheckedChangeListener(this);
        mSaturdaySlotToggleImageButton.setOnClickListener(
                v -> toggleSlotTwo(mSaturdaySlotToggleImageButton, mSaturdaySlotTwoGridLayout)
        );
        mSundayCheckBox.setOnCheckedChangeListener(this);
        mSundaySlotToggleImageButton.setOnClickListener(
                v -> toggleSlotTwo(mSundaySlotToggleImageButton, mSundaySlotTwoGridLayout)
        );
        m12MinutesCheckBox.setOnCheckedChangeListener(this);
        m25MinutesCheckBox.setOnCheckedChangeListener(this);
        m50MinutesCheckBox.setOnCheckedChangeListener(this);


        String expertTermsAndCondition = getString(R.string.expert_registration_terms_and_conditions);
        SpannableString spannableString = new SpannableString(expertTermsAndCondition);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                widget.cancelPendingInputEvents();
                ExpertService expertService = ServiceBuilder.buildService(ExpertService.class);
                Call<ExpertTermsResponse> createRequest = expertService.getExpertTerms(mSharedPreferences.getString(User.TOKEN, ""),"5e99760c5bcc4763a7e96b81");
                createRequest.enqueue(new Callback<ExpertTermsResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<ExpertTermsResponse> call, @NotNull Response<ExpertTermsResponse> response) {
                        Log.d(TAG, "onResponse: ");

                        ExpertTermsResponse expertTerms = response.body();
                        if (expertTerms != null && expertTerms.getSuccess()) {
                            ExpertTermsResponse.Cms cms = expertTerms.getCms();
                            new MaterialAlertDialogBuilder(ExpertRegistrationStepTwoActivity.this)
                                    .setTitle("Cube Talk")
                                    .setMessage(cms.getContent())
                                    .setPositiveButton("OK", null)
                                    .show();
                        }

                    }

                    @Override
                    public void onFailure(@NotNull Call<ExpertTermsResponse> call, @NotNull Throwable throwable) {
                        Log.d(TAG, "onFailure: ");

                    }
                });


            }
        };
        spannableString.setSpan(clickableSpan, expertTermsAndCondition.indexOf("additional"), expertTermsAndCondition.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        mExpertTermsAndConditionCheck.setText(spannableString);
        mExpertTermsAndConditionCheck.setMovementMethod(LinkMovementMethod.getInstance());

        mSubmitRegistrationRequestButton.setOnClickListener(v -> {
            hideSoftKeyboard(v);
            submitRegistrationRequest();
        });


    }

    private void populateConsultingTopics() {
        for (Speciality speciality : mSpecialities) {

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );

            TextView specialityHeadingText = new TextView(this);
            specialityHeadingText.setTag(speciality.getId());
            specialityHeadingText.setText(speciality.getName());
            specialityHeadingText.setTextSize(16);
            specialityHeadingText.setTypeface(Typeface.DEFAULT_BOLD);
            specialityHeadingText.setLayoutParams(params);
            mConsultingTopicsLayout.addView(specialityHeadingText);

            for (Topic topic : speciality.getTopics()) {
                MaterialCheckBox checkBox = new MaterialCheckBox(this);
                checkBox.setText(topic.getName());
                checkBox.setTag(topic.getId());
                checkBox.setLayoutParams(params);
                checkBox.setOnCheckedChangeListener(this);
                mConsultingTopicsLayout.addView(checkBox);
            }
        }
    }

    private ArrayList<Speciality> createFakeSpecialities() {
        ArrayList<Speciality> specialities = new ArrayList<>();
        ArrayList<Topic> specialityOneTopics = new ArrayList<>();
        ArrayList<Topic> specialityTwoTopics = new ArrayList<>();
        ArrayList<Topic> specialityThreeTopics = new ArrayList<>();

        Topic specialityOneTopicOne = new Topic();
        specialityOneTopicOne.setId("1");
        specialityOneTopicOne.setName("Speciality One Topic One");

        Topic specialityTwoTopicOne = new Topic();
        specialityTwoTopicOne.setId("2");
        specialityTwoTopicOne.setName("Speciality Two Topic One");

        Topic specialityTwoTopicTwo = new Topic();
        specialityTwoTopicTwo.setId("3");
        specialityTwoTopicTwo.setName("Speciality Two Topic Two");

        Topic specialityThreeTopicOne = new Topic();
        specialityThreeTopicOne.setId("4");
        specialityThreeTopicOne.setName("Speciality Three Topic One");

        Topic specialityThreeTopicTwo = new Topic();
        specialityThreeTopicTwo.setId("5");
        specialityThreeTopicTwo.setName("Speciality Three Topic Two");

        Topic specialityThreeTopicThree = new Topic();
        specialityThreeTopicThree.setId("6");
        specialityThreeTopicThree.setName("Speciality Three Topic Three");

        specialityOneTopics.add(specialityOneTopicOne);
        specialityTwoTopics.add(specialityTwoTopicOne);
        specialityTwoTopics.add(specialityTwoTopicTwo);
        specialityThreeTopics.add(specialityThreeTopicOne);
        specialityThreeTopics.add(specialityThreeTopicTwo);
        specialityThreeTopics.add(specialityThreeTopicThree);

        Speciality specialityOne = new Speciality();
        specialityOne.setId("1");
        specialityOne.setName("Speciality One");
        specialityOne.setTopics(specialityOneTopics);

        Speciality specialityTwo = new Speciality();
        specialityTwo.setId("2");
        specialityTwo.setName("Speciality Two");
        specialityTwo.setTopics(specialityTwoTopics);

        Speciality specialityThree = new Speciality();
        specialityThree.setId("3");
        specialityThree.setName("Speciality Three");
        specialityThree.setTopics(specialityThreeTopics);

        specialities.add(specialityOne);
        specialities.add(specialityTwo);
        specialities.add(specialityThree);

        return specialities;
    }

    private ExpertRegistrationRequest createFakeExpertRegistration() {
        ExpertRegistrationRequest expert = new ExpertRegistrationRequest();
        expert.setAddress("PseudoAddress");
        expert.setProfessionalSummary("PseudoProfessionalSummary");
        expert.setKeyAccomplishment("PseudoKeyAccomplishment");
        expert.setConsultationLanguages("PseudoConsultationLanguages");
        expert.setYearsOfExperience(1);
        return expert;
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

    private void toggleSlotTwo(ImageButton imageButton, GridLayout gridLayout) {
        if (gridLayout.getVisibility() == View.VISIBLE) {
            gridLayout.setVisibility(View.GONE);
            imageButton.setImageDrawable(getDrawable(R.drawable.ic_add_black_24dp));
        } else if (gridLayout.getVisibility() == View.GONE) {
            gridLayout.setVisibility(View.VISIBLE);
            imageButton.setImageDrawable(getDrawable(R.drawable.ic_remove_black_24dp));
        }
    }

    @AfterPermissionGranted(RC_PERMISSION_READ_EXTERNAL_STORAGE)
    private void submitRegistrationRequest() {

        if (!areFieldsValidated()) {
            return;
        }

        showProgressLayout();

        mSubmitRegistrationRequestButton.setEnabled(false);

        uploadExpertRegistrationDocuments();

    }

    private void uploadExpertRegistrationDocuments() {
        File expertCvFile = FileUtils.getFile(this, getContentResolver(), mExpertRegistrationRequest.getExpertCvDocumentUri());
        RequestBody expertCvTitleRequest = RequestBody.create("Resume", MediaType.parse("plain/text"));
        RequestBody expertCvDocumentRequest = RequestBody.create(expertCvFile, MediaType.parse("application/*"));
        MultipartBody.Part expertCvImagePart = MultipartBody.Part.createFormData("expert_cv_image", expertCvFile.getName(), expertCvDocumentRequest);

        File expertKycOneImageFile = FileUtils.getFile(this, getContentResolver(), mExpertRegistrationRequest.getExpertKycOneDocumentUri());
        RequestBody expertKycOneTitleRequest = null;
        MultipartBody.Part expertKycOneImagePart = null;
        if (expertKycOneImageFile != null) {
            expertKycOneTitleRequest = RequestBody.create(mExpertRegistrationRequest.getExpertKycOneDocumentTitle(), MediaType.parse("plain/text"));
            RequestBody expertKycOneImageRequest = RequestBody.create(expertKycOneImageFile, MediaType.parse("image/*"));
            expertKycOneImagePart = MultipartBody.Part.createFormData("expert_kyc_doc_1_image", expertKycOneImageFile.getName(), expertKycOneImageRequest);
        }

        File expertKycTwoImageFile = FileUtils.getFile(this, getContentResolver(), mExpertRegistrationRequest.getExpertKycTwoDocumentUri());
        RequestBody expertKycTwoTitleRequest = null;
        MultipartBody.Part expertKycTwoImagePart = null;
        if (expertKycTwoImageFile != null) {
            expertKycTwoTitleRequest = RequestBody.create(mExpertRegistrationRequest.getExpertKycTwoDocumentTitle(), MediaType.parse("plain/text"));
            RequestBody expertKycTwoImageRequest = RequestBody.create(expertKycTwoImageFile, MediaType.parse("image/*"));
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
                Log.d(TAG, "onResponse: response.body(): " + response.body().getMessage());
                ExpertDocumentUploadResponseBody responseBody = response.body();
                if (responseBody.getSuccess()) {
                    uploadExpertRegistrationDetails();
                } else {
                    Toast.makeText(
                            ExpertRegistrationStepTwoActivity.this,
                            "Server Error",
                            Toast.LENGTH_SHORT
                    ).show();
                    mSubmitRegistrationRequestButton.setEnabled(true);
                }
            }

            @Override
            public void onFailure(@NotNull Call<ExpertDocumentUploadResponseBody> call, @NotNull Throwable throwable) {
                Log.d(TAG, "onFailure: throwable.getMessage(): " + throwable.getMessage());
                Toast.makeText(
                        ExpertRegistrationStepTwoActivity.this,
                        "Server Error",
                        Toast.LENGTH_SHORT
                ).show();
                mSubmitRegistrationRequestButton.setEnabled(true);
                hideProgressLayout();
            }
        });
    }

    private void uploadExpertRegistrationDetails() {
        mExpertRegistrationRequest.setBankAccountNumber(mBankAccountNumberEdit.getText().toString());

        mExpertRegistrationRequest.setBankName(mBankNameEdit.getText().toString());

        mExpertRegistrationRequest.setBankIfsc(mIfscCodeEdit.getText().toString());

        int childCount = mConsultingTopicsLayout.getChildCount();
        for (int i = 0; i < childCount; ++i) {
            View view = mConsultingTopicsLayout.getChildAt(i);

            if (view instanceof MaterialCheckBox) {
                MaterialCheckBox checkBox = (MaterialCheckBox) view;
                if (checkBox.isChecked()) {
                    Topic topic = new Topic();
                    topic.setId(String.valueOf(view.getTag()));
                    mConsultingTopic.getTopics().add(topic);
                }
                if (i == (childCount - 1)) {
                    mConsultingTopics.add(mConsultingTopic);
                }
            } else if (view instanceof TextView) {
                if (mConsultingTopic != null) {
                    mConsultingTopics.add(mConsultingTopic);
                }
                mConsultingTopic = new ExpertRegistrationRequest.ConsultingTopic();
                Speciality speciality = new Speciality();
                speciality.setId(String.valueOf(view.getTag()));
                mConsultingTopic.setSpeciality(speciality);
            }
        }
        Log.d(TAG, "onCreate: " + mConsultingTopics.size());
        mExpertRegistrationRequest.setConsultingTopics(mConsultingTopics);

        mWeekdays.setFrom1("");
        mWeekdays.setTo1("");
        mWeekdays.setFrom2("");
        mWeekdays.setTo2("");

        if (mWeekdaysCheckBox.isChecked()) {
            mWeekdays.setIsActive(true);
            mWeekdays.setFrom1(String.valueOf(getHours(mWeekdaysSlotOneFromSpinner.getSelectedItem().toString())));
            mWeekdays.setTo1(String.valueOf(getHours(mWeekdaysSlotOneToSpinner.getSelectedItem().toString())));
            if (mWeekdaysSlotTwoGridLayout.getVisibility() == View.VISIBLE) {
                mWeekdays.setFrom2(String.valueOf(getHours(mWeekdaysSlotTwoFromSpinner.getSelectedItem().toString())));
                mWeekdays.setTo2(String.valueOf(getHours(mWeekdaysSlotTwoToSpinner.getSelectedItem().toString())));
            }
        }

        mSaturdays.setFrom1("");
        mSaturdays.setTo1("");
        mSaturdays.setFrom2("");
        mSaturdays.setTo2("");

        if (mSaturdayCheckBox.isChecked()) {
            mSaturdays.setIsActive(true);
            mSaturdays.setFrom1(String.valueOf(getHours(mSaturdaySlotOneFromSpinner.getSelectedItem().toString())));
            mSaturdays.setTo1(String.valueOf(getHours(mSaturdaySlotOneToSpinner.getSelectedItem().toString())));
            if (mSaturdaySlotTwoGridLayout.getVisibility() == View.VISIBLE) {
                mSaturdays.setFrom2(String.valueOf(getHours(mSaturdaySlotTwoFromSpinner.getSelectedItem().toString())));
                mSaturdays.setTo2(String.valueOf(getHours(mSaturdaySlotTwoToSpinner.getSelectedItem().toString())));
            }
        }

        mSundays.setFrom1("");
        mSundays.setTo1("");
        mSundays.setFrom2("");
        mSundays.setTo2("");

        if (mSundayCheckBox.isChecked()) {
            mSundays.setIsActive(true);
            mSundays.setFrom1(String.valueOf(getHours(mSundaySlotOneFromSpinner.getSelectedItem().toString())));
            mSundays.setTo1(String.valueOf(getHours(mSundaySlotOneToSpinner.getSelectedItem().toString())));
            if (mSundaySlotTwoGridLayout.getVisibility() == View.VISIBLE) {
                mSundays.setFrom2(String.valueOf(getHours(mSundaySlotTwoFromSpinner.getSelectedItem().toString())));
                mSundays.setTo2(String.valueOf(getHours(mSundaySlotTwoToSpinner.getSelectedItem().toString())));
            }
        }

        mConsultingSlot.setWeekdays(mWeekdays);
        mConsultingSlot.setSaturday(mSaturdays);
        mConsultingSlot.setSunday(mSundays);
        mExpertRegistrationRequest.setConsultingSlot(mConsultingSlot);

        mConsultingSlotDuration.setDuration12(m12MinutesCheckBox.isChecked());
        mConsultingSlotDuration.setDuration25(m25MinutesCheckBox.isChecked());
        mConsultingSlotDuration.setDuration50(m50MinutesCheckBox.isChecked());
        mExpertRegistrationRequest.setConsultingSlotDuration(mConsultingSlotDuration);

        Log.d(TAG, "submitRegistrationRequest: " + new Gson().toJson(mExpertRegistrationRequest));

        ExpertRegistrationService service = ServiceBuilder.buildService(ExpertRegistrationService.class);
        Call<ExpertRegistrationResponse> expertRegistrationResponseCall = service.submitExpertRegistrationRequest(mSharedPreferences.getString(User.TOKEN, ""),
                mUserId, mExpertRegistrationRequest);

        expertRegistrationResponseCall.enqueue(new Callback<ExpertRegistrationResponse>() {
            @Override
            public void onResponse(@NotNull Call<ExpertRegistrationResponse> call, @NotNull Response<ExpertRegistrationResponse> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse: response: " + response.body().getMessage());
                    ExpertRegistrationResponse responseBody = response.body();
                    if (responseBody.getSuccess()) {
                        SharedPreferences.Editor editor = mSharedPreferences.edit();
                        editor.putInt(User.EXPERT_APPLICATION_STATUS, ExpertApplicationStatus.PENDING.getValue());
                        editor.apply();

                        Intent intent = new Intent(ExpertRegistrationStepTwoActivity.this, HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                        showSnackbar(responseBody.getMessage());
                        Toast.makeText(
                                ExpertRegistrationStepTwoActivity.this,
                                responseBody.getMessage(),
                                Toast.LENGTH_SHORT
                        ).show();
                        hideProgressLayout();

                        finish();

                    } else {
                        Toast.makeText(
                                ExpertRegistrationStepTwoActivity.this,
                                responseBody.getMessage(),
                                Toast.LENGTH_SHORT
                        ).show();
                        mSubmitRegistrationRequestButton.setEnabled(true);
                        hideProgressLayout();
                    }
                } else {
                    mSubmitRegistrationRequestButton.setEnabled(true);
                    Toast.makeText(
                            ExpertRegistrationStepTwoActivity.this,
                            String.format("%s: %s", response.code(), response.message()),
                            Toast.LENGTH_SHORT
                    ).show();
                    hideProgressLayout();
                }
            }

            @Override
            public void onFailure(@NotNull Call<ExpertRegistrationResponse> call, @NotNull Throwable throwable) {
                Log.d(TAG, "onFailure: t.getMessage(): " + throwable.getMessage());
                Toast.makeText(
                        ExpertRegistrationStepTwoActivity.this,
                        String.format("%s", throwable.getMessage()),
                        Toast.LENGTH_SHORT
                ).show();
                mSubmitRegistrationRequestButton.setEnabled(true);
                hideProgressLayout();
            }
        });
    }

    private int getHours(String slotTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("h a", Locale.ENGLISH);
        try {
            Date date = sdf.parse(slotTime);
            Calendar calendar = Calendar.getInstance();
            if (date != null) {
                calendar.setTime(date);
            }
            return calendar.get(Calendar.HOUR_OF_DAY);
        } catch (ParseException e) {
            Log.e(TAG, "submitRegistrationRequest: e.getMessage(): " + e.getMessage());
        }
        return -1;
    }

    private String getHoursInString(int hours) {
        return String.valueOf(hours);
    }


//    private void updateIsExpertAppliedInUserPreference() {
//        SharedPreferences.Editor editor = mSharedPreferences.edit();
//        editor.putBoolean(User.IS_EXPERT_APPLIED, true);
//        editor.apply();
//    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.cb_weekdays:
                mWeekdaysSlotsConstraintLayout.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                break;

            case R.id.cb_saturday:
                mSaturdaySlotsConstraintLayout.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                break;

            case R.id.cb_sunday:
                mSundaySlotsConstraintLayout.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                break;

            case R.id.mcb_12_mins:
            case R.id.mcb_25_mins:
            case R.id.mcb_50_mins:
                clearConsultingSlotDurationCheckBoxesError();
                break;
        }
    }

    private void clearConsultingSlotDurationCheckBoxesError() {
        Log.d(TAG, "onCheckedChanged: Clear error on Consulting Slot Duration");
//        mWeekdaysCheckBox.setError(null);
//        mSaturdayCheckBox.setError(null);
//        mSundayCheckBox.setError(null);
    }

    private boolean areFieldsValidated() {
        mBankAccountNumberLayout.setError("");
        mBankNameLayout.setError("");
        mIfscCodeLayout.setError("");

        String bankAccountNumber = mBankAccountNumberEdit.getText().toString();
        String bankName = mBankNameEdit.getText().toString();
        String ifscCode = mIfscCodeEdit.getText().toString();

        if (TextUtils.isEmpty(bankAccountNumber)) {
            showError(mBankAccountNumberLayout, "Bank account number is empty");
            return false;
        }

        if (TextUtils.isEmpty(bankName)) {
            showError(mBankNameLayout, "Bank name is empty");
            return false;
        }

        if (TextUtils.isEmpty(ifscCode)) {
            showError(mIfscCodeLayout, "IFSC Code is empty");
            return false;
        } else if (!Pattern.compile("^[A-Za-z]{4}[0-9A-Za-z]{7}$").matcher(ifscCode).matches()) {
            showError(mIfscCodeLayout, "IFSC Code is incorrect");
            return false;
        }

        if (!mWeekdaysCheckBox.isChecked() && !mSaturdayCheckBox.isChecked() && !mSundayCheckBox.isChecked()) {
            showSnackbar("Please select at least one consulting slot ");

            return false;
        }

        if (mWeekdaysCheckBox.isChecked()) {
            int slotOneFrom = getHours(mWeekdaysSlotOneFromSpinner.getSelectedItem().toString());
            int slotOneTo = getHours(mWeekdaysSlotOneToSpinner.getSelectedItem().toString());
//            if (slotOneFrom >= slotOneTo) {
//                showSnackbar("Weekdays slot one \"From\" cannot be greater or equal to \"To\"");
//                return false;
//            }
            if (mWeekdaysSlotTwoGridLayout.getVisibility() == View.VISIBLE) {
                int slotTwoFrom = getHours(mWeekdaysSlotTwoFromSpinner.getSelectedItem().toString());
                int slotTwoTo = getHours(mWeekdaysSlotTwoToSpinner.getSelectedItem().toString());
//                if (slotTwoFrom >= slotTwoTo) {
//                    showSnackbar("Weekdays slot two \"From\" cannot be greater or equal to \"To\"");
//                    return false;
//                }
                if (slotOneFrom == slotTwoFrom) {
                    showSnackbar("Weekdays slot one \"From\" cannot be equal to slot two \"From\"");
                    return false;
                }
            }
        }

        if (mSaturdayCheckBox.isChecked()) {
            int slotOneFrom = getHours(mSaturdaySlotOneFromSpinner.getSelectedItem().toString());
            int slotOneTo = getHours(mSaturdaySlotOneToSpinner.getSelectedItem().toString());
//            if (slotOneFrom >= slotOneTo) {
//                showSnackbar("Saturday slot one \"From\" cannot be greater or equal to \"To\"");
//                return false;
//            }
            if (mSaturdaySlotTwoGridLayout.getVisibility() == View.VISIBLE) {
                int slotTwoFrom = getHours(mSaturdaySlotTwoFromSpinner.getSelectedItem().toString());
                int slotTwoTo = getHours(mSaturdaySlotTwoToSpinner.getSelectedItem().toString());
//                if (slotTwoFrom >= slotTwoTo) {
//                    showSnackbar("Saturday slot two \"From\" cannot be greater or equal to \"To\"");
//                    return false;
//                }
                if (slotOneFrom == slotTwoFrom) {
                    showSnackbar("Saturday slot one \"From\" cannot be equal to slot two \"From\"");
                    return false;
                }
            }
        }

        if (mSundayCheckBox.isChecked()) {
            int slotOneFrom = getHours(mSundaySlotOneFromSpinner.getSelectedItem().toString());
            int slotOneTo = getHours(mSundaySlotOneToSpinner.getSelectedItem().toString());
            if (slotOneFrom >= slotOneTo) {
                showSnackbar("Sunday slot one \"From\" cannot be greater or equal to \"To\"");
                return false;
            }
            if (mSundaySlotTwoGridLayout.getVisibility() == View.VISIBLE) {
                int slotTwoFrom = getHours(mSundaySlotTwoFromSpinner.getSelectedItem().toString());
                int slotTwoTo = getHours(mSundaySlotTwoToSpinner.getSelectedItem().toString());
//                if (slotTwoFrom >= slotTwoTo) {
//                    showSnackbar("Sunday slot two \"From\" cannot be greater or equal to \"To\"");
//                    return false;
//                }
                if (slotOneFrom == slotTwoFrom) {
                    showSnackbar("Sunday slot one \"From\" cannot be equal to slot two \"From\"");
                    return false;
                }
            }
        }


        if (!m12MinutesCheckBox.isChecked() && !m25MinutesCheckBox.isChecked() && !m50MinutesCheckBox.isChecked()) {
//            m12MinutesCheckBox.setButtonTintList(ContextCompat.getColorStateList(this, android.R.color.holo_red_dark));
//            m25MinutesCheckBox.setButtonTintList(ContextCompat.getColorStateList(this, android.R.color.holo_red_dark));
//            m50MinutesCheckBox.setButtonTintList(ContextCompat.getColorStateList(this, android.R.color.holo_red_dark));
            showSnackbar("Please select at least one slot duration");
            return false;
        }

        if (!mExpertTermsAndConditionCheck.isChecked()) {
            showSnackbar("Please check the terms and condition check box");
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
        if (textInputLayout.getEditText() != null)
            textInputLayout.getEditText().requestFocus();
    }

    private void showErrorOnTextInputLayout(TextInputLayout textInputLayout, String errorString) {
        textInputLayout.setError(errorString);
    }

    private void showSnackbar(String errorString) {
        Snackbar.make(mContentBinding.getRoot(), errorString, Snackbar.LENGTH_LONG).show();
    }

    public void hideSoftKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void showProgressLayout() {
        mLoadingLayout.setVisibility(View.VISIBLE);
    }

    private void hideProgressLayout() {
        mLoadingLayout.setVisibility(View.GONE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }
}
