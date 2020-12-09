package local.impactlife.cubetalk.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import local.impactlife.cubetalk.R;
import local.impactlife.cubetalk.databinding.ActivityBookExpertBinding;
import local.impactlife.cubetalk.databinding.ContentBookExpertBinding;
import local.impactlife.cubetalk.models.User;
import local.impactlife.cubetalk.models.common.ConsultingSlotDuration;
import local.impactlife.cubetalk.models.common.SpecialityTopic;
import local.impactlife.cubetalk.models.post_booking_slot.BookSlotRequest;
import local.impactlife.cubetalk.models.user_info.UserInfoFetchResponse;
import local.impactlife.cubetalk.services.api.ServiceBuilder;
import local.impactlife.cubetalk.services.api.UserInfoService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookExpertActivity extends AppCompatActivity {

    private static final String TAG = BookExpertActivity.class.getSimpleName();

    private ActivityBookExpertBinding mActivityBinding;
    private ContentBookExpertBinding mContentBinding;


    private String mExpertId;
    private ShapeableImageView mExpertProfileImage;
    private MaterialTextView mExpertSpecialitiesText;
    private MaterialCardView mConsultingSlotTime12MinutesCard;
    private MaterialTextView mConsultingSlot12MinutesPriceText;
    private MaterialCardView mConsultingSlotTime25MinutesCard;
    private MaterialTextView mConsultingSlot25MinutesPriceText;
    private MaterialCardView mConsultingSlotTime50MinutesCard;
    private MaterialTextView mConsultingSlot50MinutesPriceText;
    private MaterialTextView mExpertNameText;
    private MaterialTextView mSelectedTopicText;
    private String mTopicName;
    private RadioGroup mAvailableCouponRadioGroup;
    private MaterialButton mProceedToDateAndTimeSelectionButton;
    private BookSlotRequest mBookSlotRequest;
    private String mSpecialityId;
    private String mTopicId;
    private SharedPreferences mSharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_book_expert);


        mActivityBinding = ActivityBookExpertBinding.inflate(LayoutInflater.from(this));
        setContentView(mActivityBinding.getRoot());
        mSharedPreferences = getSharedPreferences(User.PREFERENCE_NAME, Context.MODE_PRIVATE);

        Toolbar toolbar = mActivityBinding.toolbar;
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Book Expert");
        }

        Intent mIntent = getIntent();
        mExpertId = mIntent.getStringExtra("ExpertId");
        mSpecialityId = mIntent.getStringExtra("SpecialityId");
        mTopicId = mIntent.getStringExtra("TopicId");
        mTopicName = mIntent.getStringExtra("TopicName");

        mBookSlotRequest = new BookSlotRequest();

        mContentBinding = mActivityBinding.contentBookExpert;

        mExpertProfileImage = mContentBinding.sivExpertProfileImage;

        mExpertNameText = mContentBinding.mtvExpertName;

        mExpertSpecialitiesText = mContentBinding.mtvExpertSpecialities;

        mSelectedTopicText = mContentBinding.mtvSelectedTopic;

        mConsultingSlotTime12MinutesCard = mContentBinding.mcvConsultingSlotTime12Minutes;
        mConsultingSlot12MinutesPriceText = mContentBinding.mtvConsultingSlot12MinutesPrice;

        mConsultingSlotTime25MinutesCard = mContentBinding.mcvConsultingSlotTime25Minutes;
        mConsultingSlot25MinutesPriceText = mContentBinding.mtvConsultingSlot25MinutesPrice;

        mConsultingSlotTime50MinutesCard = mContentBinding.mcvConsultingSlotTime50Minutes;
        mConsultingSlot50MinutesPriceText = mContentBinding.mtvConsultingSlot50MinutesPrice;

        mAvailableCouponRadioGroup = mContentBinding.rgAvailableCoupons;

        mProceedToDateAndTimeSelectionButton = mContentBinding.mbtnProceedToDateAndTimeSelection;

        mSelectedTopicText.setText(mTopicName);

        UserInfoService userInfoService = ServiceBuilder.buildService(UserInfoService.class);
        Call<UserInfoFetchResponse> createRequest = userInfoService.getUserInfo(mExpertId,mSharedPreferences.getString(User.TOKEN, ""));

        createRequest.enqueue(new Callback<UserInfoFetchResponse>() {
            @Override
            public void onResponse(@NotNull Call<UserInfoFetchResponse> call, @NotNull Response<UserInfoFetchResponse> response) {
                Log.d(TAG, "onResponse: response: " + response.body());
                if (response.isSuccessful()) {
                    UserInfoFetchResponse userInfoFetchResponse = response.body();

                    UserInfoFetchResponse.Data expert = userInfoFetchResponse.getData();

                    Glide.with(BookExpertActivity.this)
                            .asBitmap()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .circleCrop()
                            .placeholder(R.drawable.ic_account_circle_white_128dp)
                            .load(expert.getProfileImage())
                            .into(mExpertProfileImage);


                    mExpertNameText.setText(expert.getName());

                    ArrayList<String> specialityNames = new ArrayList<>();
//                    ArrayList<String> topicNames = new ArrayList<>();
                    for (SpecialityTopic specialityTopic : expert.getSpecialityTopics()) {
                        specialityNames.add(specialityTopic.getSpeciality().getName());
//                        for (Topic topic : specialityTopic.getTopics()) {
//                            topicNames.add(topic.getName());
//                        }
                    }

                    mExpertSpecialitiesText.setText(TextUtils.join(", ", specialityNames));

                    ConsultingSlotDuration consultingSlotDuration = expert.getConsultingSlotDuration();

                    UserInfoFetchResponse.ConsultingSlotPrice consultingSlotPrice = expert.getConsultingSlotPrice();

                    if (consultingSlotDuration.getDuration12()) {
                        mConsultingSlotTime12MinutesCard.setVisibility(View.VISIBLE);
                        UserInfoFetchResponse.DurationPrice durationPrice = consultingSlotPrice.getDuration12();
                        String currency=(mSharedPreferences.getInt(User.COUNTRYID, 0)==User.INDIACOUNTRYID? "INR "+durationPrice.getInr():"INR "+durationPrice.getNri());
                        mConsultingSlot12MinutesPriceText.setText(durationPrice.getStatus() ? currency  : "NA");
                        mConsultingSlot12MinutesPriceText.setTag(mSharedPreferences.getInt(User.COUNTRYID, 0)==User.INDIACOUNTRYID?durationPrice.getInr():durationPrice.getNri());
                    } else {
                        mConsultingSlotTime12MinutesCard.setVisibility(View.GONE);
                    }

                    if (consultingSlotDuration.getDuration25()) {
                        mConsultingSlotTime25MinutesCard.setVisibility(View.VISIBLE);
                        UserInfoFetchResponse.DurationPrice durationPrice = consultingSlotPrice.getDuration25();
                        String currency=(mSharedPreferences.getInt(User.COUNTRYID, 0)==User.INDIACOUNTRYID? "INR "+durationPrice.getInr():"INR "+durationPrice.getNri());

                        mConsultingSlot25MinutesPriceText.setText(durationPrice.getStatus() ? currency  : "NA");
                        mConsultingSlot25MinutesPriceText.setTag(mSharedPreferences.getInt(User.COUNTRYID, 0)==User.INDIACOUNTRYID?durationPrice.getInr():durationPrice.getNri());
                    } else {
                        mConsultingSlotTime25MinutesCard.setVisibility(View.GONE);
                    }

                    if (consultingSlotDuration.getDuration50()) {
                        mConsultingSlotTime50MinutesCard.setVisibility(View.VISIBLE);
                        UserInfoFetchResponse.DurationPrice durationPrice = consultingSlotPrice.getDuration50();
                        String currency=(mSharedPreferences.getInt(User.COUNTRYID, 0)==User.INDIACOUNTRYID? "INR "+durationPrice.getInr():"INR "+durationPrice.getNri());

                        mConsultingSlot50MinutesPriceText.setText(durationPrice.getStatus() ? currency   : "NA");
                        mConsultingSlot50MinutesPriceText.setTag(mSharedPreferences.getInt(User.COUNTRYID, 0)==User.INDIACOUNTRYID?durationPrice.getInr():durationPrice.getNri());
                    } else {
                        mConsultingSlotTime50MinutesCard.setVisibility(View.GONE);
                    }

                    mBookSlotRequest.setExpertImage(expert.getProfileImage());
                    mBookSlotRequest.setExpertName(expert.getName());
                    mBookSlotRequest.setExpertSpecialities(String.valueOf(mExpertSpecialitiesText.getText()));
                }
            }

            @Override
            public void onFailure(@NotNull Call<UserInfoFetchResponse> call, @NotNull Throwable throwable) {
                Log.d(TAG, "onFailure: throwable.getMessage(): " + throwable.getMessage());
            }
        });

        mConsultingSlotTime12MinutesCard.setOnClickListener(v -> {
            mConsultingSlotTime12MinutesCard.setChecked(true);
            mConsultingSlotTime25MinutesCard.setChecked(false);
            mConsultingSlotTime50MinutesCard.setChecked(false);
            mBookSlotRequest.setSlotType("duration_12");
            mBookSlotRequest.setAmountPaid(String.valueOf(mConsultingSlot12MinutesPriceText.getTag()));
        });
        mConsultingSlotTime25MinutesCard.setOnClickListener(v -> {
            mConsultingSlotTime12MinutesCard.setChecked(false);
            mConsultingSlotTime25MinutesCard.setChecked(true);
            mConsultingSlotTime50MinutesCard.setChecked(false);
            mBookSlotRequest.setSlotType("duration_25");
            mBookSlotRequest.setAmountPaid(String.valueOf(mConsultingSlot25MinutesPriceText.getTag()));
        });
        mConsultingSlotTime50MinutesCard.setOnClickListener(v -> {
            mConsultingSlotTime12MinutesCard.setChecked(false);
            mConsultingSlotTime25MinutesCard.setChecked(false);
            mConsultingSlotTime50MinutesCard.setChecked(true);
            mBookSlotRequest.setSlotType("duration_50");
            mBookSlotRequest.setAmountPaid(String.valueOf(mConsultingSlot50MinutesPriceText.getTag()));
        });

        mProceedToDateAndTimeSelectionButton.setOnClickListener(v ->
        {
            if (!mConsultingSlotTime12MinutesCard.isChecked() &&
                    !mConsultingSlotTime25MinutesCard.isChecked() &&
                    !mConsultingSlotTime50MinutesCard.isChecked()
            ) {
                Toast.makeText(this, "Please select Session Duration", Toast.LENGTH_SHORT).show();
                return;
            }

            SharedPreferences sharedPreferences = getSharedPreferences(User.PREFERENCE_NAME, MODE_PRIVATE);
            mBookSlotRequest.setBookingUserId(sharedPreferences.getString(User.ID, ""));
            mBookSlotRequest.setBookedExpertId(mExpertId);
            mBookSlotRequest.setCouponCode("");
            mBookSlotRequest.setCouponDiscount("");
            mBookSlotRequest.setUserName(sharedPreferences.getString(User.NAME, ""));
            mBookSlotRequest.setUserEmail(sharedPreferences.getString(User.EMAIL, ""));
            mBookSlotRequest.setUserPhone(sharedPreferences.getString(User.PHONE, ""));
            mBookSlotRequest.setSpecialityId(mSpecialityId);
            mBookSlotRequest.setTopicId(mTopicId);

            Intent intent = new Intent(this, SlotSelectionActivity.class);
            intent.putExtra(BookSlotRequest.TAG, mBookSlotRequest);

            startActivity(intent);
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
