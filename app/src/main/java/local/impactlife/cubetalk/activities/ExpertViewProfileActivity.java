package local.impactlife.cubetalk.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import local.impactlife.cubetalk.R;
import local.impactlife.cubetalk.databinding.ActivityExpertViewProfileBinding;
import local.impactlife.cubetalk.databinding.ContentExpertViewProfileBinding;
import local.impactlife.cubetalk.models.Country;
import local.impactlife.cubetalk.models.User;
import local.impactlife.cubetalk.models.common.BankInfo;
import local.impactlife.cubetalk.models.common.ConsultingSlot;
import local.impactlife.cubetalk.models.common.ConsultingSlotDay;
import local.impactlife.cubetalk.models.common.ConsultingSlotDuration;
import local.impactlife.cubetalk.models.common.ExpertCv;
import local.impactlife.cubetalk.models.common.ExpertDocuments;
import local.impactlife.cubetalk.models.common.ExpertKycDocOne;
import local.impactlife.cubetalk.models.common.ExpertKycDocTwo;
import local.impactlife.cubetalk.models.common.SpecialityTopic;
import local.impactlife.cubetalk.models.common.Topic;
import local.impactlife.cubetalk.models.user_info.UserInfoFetchResponse;
import local.impactlife.cubetalk.services.api.ServiceBuilder;
import local.impactlife.cubetalk.services.api.UserInfoService;
import local.impactlife.cubetalk.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static local.impactlife.cubetalk.utils.Utils.convert24HoursTo12Hours;

public class ExpertViewProfileActivity extends AppCompatActivity {

    private static final String TAG = ExpertViewProfileActivity.class.getSimpleName();

    private SharedPreferences mSharedPreferences;
    private String mUserId;
    private ActivityExpertViewProfileBinding mActivityBinding;
    private ContentExpertViewProfileBinding mContentBinding;
    private TextInputLayout mNameLayout;
    private TextInputEditText mNameEdit;
    private TextInputLayout mAddressTextInput;
    private TextInputEditText mAddressEdit;
    private TextInputLayout mKeyAccomplishmentTextInput;
    private TextInputEditText mKeyAccomplishmentEdit;
    private TextInputLayout mLanguagesForConsultationTextInput;
    private TextInputEditText mLanguagesForConsultationEdit;
    private TextInputLayout mYearsOfExperienceTextInput;
    private TextInputEditText mYearsOfExperienceEdit;
    private LinearLayout mConsultingCategoriesLayout;
    private MaterialTextView mExpertCvText;
    private MaterialTextView mExpertKycDocumentOneNameText;
    private MaterialTextView mExpertKycDocumentTwoNameText;
    private MaterialTextView mBankNameText;
    private MaterialTextView mBankAccountNumberText;
    private MaterialTextView mBankIfscCodeText;
    private MaterialTextView mConsultingSlotWeekdaysText;
    private MaterialTextView mConsultingSlotSaturdayText;
    private MaterialTextView mConsultingSlotSundayText;
    private LinearLayout mConsultingTopicsLayout;
    private MaterialTextView mServiceStartDateText;
    private MaterialTextView mConsultingSlotDuration12MinutesText;
    private MaterialTextView mConsultingSlotDuration25MinutesText;
    private MaterialTextView mConsultingSlotDuration50MinutesText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_expert_view_profile);

        mActivityBinding = ActivityExpertViewProfileBinding.inflate(LayoutInflater.from(this));
        setContentView(mActivityBinding.getRoot());

        Toolbar toolbar = mActivityBinding.toolbar;
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Profile Info");
        }

        mSharedPreferences = getSharedPreferences(User.PREFERENCE_NAME, MODE_PRIVATE);

        mUserId = mSharedPreferences.getString(User.ID, "");

        List<Country> mCountries = Utils.getCountries();

        mContentBinding = mActivityBinding.contentExpertViewProfile;

        mNameLayout = mContentBinding.tilName;
        mNameEdit = mContentBinding.tietName;

        mAddressTextInput = mContentBinding.tilAddress;
        mAddressEdit = mContentBinding.tietAddress;

        mKeyAccomplishmentTextInput = mContentBinding.tilKeyAccomplishment;
        mKeyAccomplishmentEdit = mContentBinding.tietKeyAccomplishment;

        mLanguagesForConsultationTextInput = mContentBinding.tilLanguagesForConsultation;
        mLanguagesForConsultationEdit = mContentBinding.tietLanguagesForConsultation;

        mYearsOfExperienceTextInput = mContentBinding.tilYearsOfExperience;
        mYearsOfExperienceEdit = mContentBinding.tietYearsOfExperience;

        mExpertCvText = mContentBinding.tvExpertCv;

        mExpertKycDocumentOneNameText = mContentBinding.tvExpertKycDocumentOneName;

        mExpertKycDocumentTwoNameText = mContentBinding.tvExpertKycDocumentTwoName;

        mConsultingCategoriesLayout = mContentBinding.llConsultingCategories;
        mConsultingTopicsLayout = mContentBinding.llConsultingTopics;

        mBankNameText = mContentBinding.tvBankName;
        mBankAccountNumberText = mContentBinding.tvBankAccountNumber;
        mBankIfscCodeText = mContentBinding.tvBankIfscCode;

        mConsultingSlotWeekdaysText = mContentBinding.tvConsultingSlotWeekdays;
        mConsultingSlotSaturdayText = mContentBinding.tvConsultingSlotSaturday;
        mConsultingSlotSundayText = mContentBinding.tvConsultingSlotSunday;

        mServiceStartDateText = mContentBinding.mtvServiceStartDate;

        mConsultingSlotDuration12MinutesText = mContentBinding.tvConsultingSlotDuration12MinutesAndPrice;
        mConsultingSlotDuration25MinutesText = mContentBinding.tvConsultingSlotDuration25MinutesAndPrice;
        mConsultingSlotDuration50MinutesText = mContentBinding.tvConsultingSlotDuration50MinutesAndPrice;

        showProgressLayout();
        fetchUserInformation();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }

    private void showProgressLayout() {
//        mLoadingLayout.setVisibility(View.VISIBLE);
    }

    private void hideProgressLayout() {
//        mLoadingLayout.setVisibility(View.GONE);
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
                        populateFieldsWithData(userInfoFetchResponse);
                        hideProgressLayout();
                    } else {
                        Toast.makeText(
                                ExpertViewProfileActivity.this,
                                userInfoFetchResponse.getMessage(),
                                Toast.LENGTH_SHORT
                        ).show();
                        hideProgressLayout();
                    }
                } else {
                    hideProgressLayout();
                    Toast.makeText(
                            ExpertViewProfileActivity.this,
                            "Server Error",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<UserInfoFetchResponse> call, @NotNull Throwable throwable) {
                Log.d(TAG, "onFailure: throwable.getMessage(): " + throwable.getMessage());
                Toast.makeText(ExpertViewProfileActivity.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void populateFieldsWithData(UserInfoFetchResponse userInfoFetchResponse) {
        UserInfoFetchResponse.Data data = userInfoFetchResponse.getData();

        LayoutInflater inflater = LayoutInflater.from(this);

        List<SpecialityTopic> specialityTopics = data.getSpecialityTopics();
        for (SpecialityTopic specialityTopic : specialityTopics) {
            MaterialTextView specialityText = (MaterialTextView) inflater.inflate(
                    R.layout.item_text,
                    mConsultingCategoriesLayout,
                    false
            );
            specialityText.setText(specialityTopic.getSpeciality().getName());
            mConsultingCategoriesLayout.addView(specialityText);

            for (Topic topic : specialityTopic.getTopics()) {
                MaterialTextView topicText = (MaterialTextView) inflater.inflate(
                        R.layout.item_text,
                        mConsultingTopicsLayout,
                        false
                );
                topicText.setText(topic.getName());
                mConsultingTopicsLayout.addView(topicText);
            }
        }

        mNameEdit.setText(data.getName());
        mAddressEdit.setText(data.getAddress());
        mKeyAccomplishmentEdit.setText(data.getKeyAccomplishment());
        mLanguagesForConsultationEdit.setText(data.getLanguages());
        mYearsOfExperienceEdit.setText(String.valueOf(data.getYearsOfExperience()));

        ExpertDocuments expertDocuments = data.getExpertDocuments();

        ExpertCv expertCv = expertDocuments.getExpertCv();
        mExpertCvText.setText(expertCv.getImage());

        ExpertKycDocOne expertKycDocOne = expertDocuments.getExpertKycDocOne();
        if (!TextUtils.isEmpty(expertKycDocOne.getTitle())) {
            mExpertKycDocumentOneNameText.setText(expertKycDocOne.getTitle() + ": " + expertKycDocOne.getImage());
        }

        ExpertKycDocTwo expertKycDocTwo = expertDocuments.getExpertKycDocTwo();
        if (!TextUtils.isEmpty(expertKycDocTwo.getTitle())) {
            mExpertKycDocumentTwoNameText.setText(expertKycDocTwo.getTitle() + ": " + expertKycDocTwo.getImage());
        }

        BankInfo bankInfo = data.getBankInfo();
        mBankNameText.setText(bankInfo.getBankName());
        mBankAccountNumberText.setText("Account No.: " + bankInfo.getBankAccountNumber());
        mBankIfscCodeText.setText("IFSC: " + bankInfo.getBankIfsc());

        ConsultingSlot consultingSlot = data.getConsultingSlot();

        ConsultingSlotDay weekdays = consultingSlot.getWeekdays();
        if (!TextUtils.isEmpty(weekdays.getFrom1())) {
            mConsultingSlotWeekdaysText.setVisibility(View.VISIBLE);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Weekdays: ");
            stringBuilder.append(convert24HoursTo12Hours(weekdays.getFrom1()));
            stringBuilder.append(" to ");
            stringBuilder.append(convert24HoursTo12Hours(weekdays.getTo1()));
            if (!TextUtils.isEmpty(weekdays.getFrom2())) {
                stringBuilder.append(", ");
                stringBuilder.append(convert24HoursTo12Hours(weekdays.getFrom2()));
                stringBuilder.append(" to ");
                stringBuilder.append(convert24HoursTo12Hours(weekdays.getTo2()));
            }
            mConsultingSlotWeekdaysText.setText(stringBuilder);
        }

        ConsultingSlotDay saturday = consultingSlot.getSaturday();
        if (!TextUtils.isEmpty(saturday.getFrom1())) {
            mConsultingSlotSaturdayText.setVisibility(View.VISIBLE);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Saturday: ");
            stringBuilder.append(convert24HoursTo12Hours(saturday.getFrom1()));
            stringBuilder.append(" to ");
            stringBuilder.append(convert24HoursTo12Hours(saturday.getTo1()));
            if (!TextUtils.isEmpty(saturday.getFrom2())) {
                stringBuilder.append(", ");
                stringBuilder.append(convert24HoursTo12Hours(saturday.getFrom2()));
                stringBuilder.append(" to ");
                stringBuilder.append(convert24HoursTo12Hours(saturday.getTo2()));
            }
            mConsultingSlotSaturdayText.setText(stringBuilder);
        }

        ConsultingSlotDay sunday = consultingSlot.getSunday();
        if (!TextUtils.isEmpty(sunday.getFrom1())) {
            mConsultingSlotSundayText.setVisibility(View.VISIBLE);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Sunday: ");
            stringBuilder.append(convert24HoursTo12Hours(sunday.getFrom1()));
            stringBuilder.append(" to ");
            stringBuilder.append(convert24HoursTo12Hours(sunday.getTo1()));
            if (!TextUtils.isEmpty(sunday.getFrom2())) {
                stringBuilder.append(", ");
                stringBuilder.append(convert24HoursTo12Hours(sunday.getFrom2()));
                stringBuilder.append(" to ");
                stringBuilder.append(convert24HoursTo12Hours(sunday.getTo2()));
            }
            mConsultingSlotSundayText.setText(stringBuilder);
        }

        try {
            String expertServiceStartDate = data.getExpertServiceStartDate();
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
            Date date;
            date = inputFormat.parse(expertServiceStartDate);
            String formattedDate = outputFormat.format(date);
            mServiceStartDateText.setText(formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ConsultingSlotDuration consultingSlotDuration = data.getConsultingSlotDuration();

        UserInfoFetchResponse.ConsultingSlotPrice consultingSlotPrice = data.getConsultingSlotPrice();

        if (consultingSlotDuration.getDuration12()) {
            mConsultingSlotDuration12MinutesText.setVisibility(View.VISIBLE);
            UserInfoFetchResponse.DurationPrice durationPrice = consultingSlotPrice.getDuration12();
            mConsultingSlotDuration12MinutesText.setText(durationPrice.getStatus() ? mConsultingSlotDuration12MinutesText.getText() + " - INR " + durationPrice.getInr() : "NA");
        } else {
            mConsultingSlotDuration12MinutesText.setVisibility(View.GONE);
        }

        if (consultingSlotDuration.getDuration25()) {
            mConsultingSlotDuration25MinutesText.setVisibility(View.VISIBLE);
            UserInfoFetchResponse.DurationPrice durationPrice = consultingSlotPrice.getDuration25();
            mConsultingSlotDuration25MinutesText.setText(durationPrice.getStatus() ? mConsultingSlotDuration25MinutesText.getText() + " - INR " + durationPrice.getInr() : "NA");
        } else {
            mConsultingSlotDuration25MinutesText.setVisibility(View.GONE);
        }

        if (consultingSlotDuration.getDuration50()) {
            mConsultingSlotDuration50MinutesText.setVisibility(View.VISIBLE);
            UserInfoFetchResponse.DurationPrice durationPrice = consultingSlotPrice.getDuration50();
            mConsultingSlotDuration50MinutesText.setText(durationPrice.getStatus() ? mConsultingSlotDuration50MinutesText.getText() + " - INR " + durationPrice.getInr() : "NA");
        } else {
            mConsultingSlotDuration50MinutesText.setVisibility(View.GONE);
        }
    }


}
