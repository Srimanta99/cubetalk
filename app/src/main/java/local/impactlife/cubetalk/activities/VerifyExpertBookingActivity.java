package local.impactlife.cubetalk.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import local.impactlife.cubetalk.R;
import local.impactlife.cubetalk.databinding.ActivityVerifyExpertBookingBinding;
import local.impactlife.cubetalk.databinding.ContentVerifyExpertBookingBinding;
import local.impactlife.cubetalk.models.User;
import local.impactlife.cubetalk.models.post_booking_slot.BookSlotRequest;
import local.impactlife.cubetalk.models.user_privacy_policy_and_terms.UserPrivacyPolicyAndTermsResponse;
import local.impactlife.cubetalk.services.api.ServiceBuilder;
import local.impactlife.cubetalk.services.api.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyExpertBookingActivity extends AppCompatActivity {

    private static final String TAG = VerifyExpertBookingActivity.class.getSimpleName();

    private ActivityVerifyExpertBookingBinding mActivityBinding;
    private ContentVerifyExpertBookingBinding mContentBinding;
    private BookSlotRequest mBookSlotRequest;
    private ShapeableImageView mExpertProfileImage;
    private MaterialTextView mExpertNameText;
    private MaterialTextView mExpertSpecialitiesText;
    private MaterialCheckBox mCubeTalkBookingPolicyCheck;
    private MaterialButton mConfirmAndProceedToPayButton;
    private TextInputLayout mUserNameTextInput;
    private TextInputEditText mUserNameTextInputEdit;
    private TextInputLayout mUserEmailTextInput;
    private TextInputEditText mUserEmailTextInputEdit;
    private TextInputLayout mUserPhoneNumberTextInput;
    private TextInputEditText mUserPhoneNumberTextInputEdit;
    private MaterialTextView mBookingDateAndTime;
    private MaterialTextView mBookingSlotTypeAndPrice;
    private SharedPreferences mSharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_verify_expert_booking);

        mActivityBinding = ActivityVerifyExpertBookingBinding.inflate(LayoutInflater.from(this));
        setContentView(mActivityBinding.getRoot());
        mSharedPreferences = getSharedPreferences(User.PREFERENCE_NAME, Context.MODE_PRIVATE);
        Toolbar toolbar = mActivityBinding.toolbar;
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mBookSlotRequest = getIntent().getParcelableExtra(BookSlotRequest.TAG);

        mContentBinding = mActivityBinding.contentVerifyExpertBooking;

        mExpertProfileImage = mContentBinding.sivExpertProfileImage;
        mExpertNameText = mContentBinding.mtvExpertName;
        mExpertSpecialitiesText = mContentBinding.mtvExpertSpecialities;
        mBookingDateAndTime = mContentBinding.mtvBookingDateAndTime;
        mBookingSlotTypeAndPrice = mContentBinding.mtvBookingSlotTypeAndPrice;
        mUserNameTextInput = mContentBinding.tilUserName;
        mUserNameTextInputEdit = mContentBinding.tietUserName;
        mUserEmailTextInput = mContentBinding.tilUserEmail;
        mUserEmailTextInputEdit = mContentBinding.tietUserEmail;
        mUserPhoneNumberTextInput = mContentBinding.tilUserPhoneNumber;
        mUserPhoneNumberTextInputEdit = mContentBinding.tietUserPhoneNumber;
        mCubeTalkBookingPolicyCheck = mContentBinding.mcbCubeTalkBookingPolicy;
        mConfirmAndProceedToPayButton = mContentBinding.mbtnConfirmAndProceedToPay;

        String bookPrivacyPolicyAndTerms = getString(R.string.byconfirming_term);
        SpannableString spannableString = new SpannableString(bookPrivacyPolicyAndTerms);
        ClickableSpan clickableSpan=new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                widget.cancelPendingInputEvents();
               // mConfirmAndProceedToPayButton.setChecked(true);
                UserService userService = ServiceBuilder.buildService(UserService.class);
                Call<UserPrivacyPolicyAndTermsResponse> createRequest = userService.getUserTerms("5f34eaac808063021dffd0fe");
                createRequest.enqueue(new Callback<UserPrivacyPolicyAndTermsResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<UserPrivacyPolicyAndTermsResponse> call, @NotNull Response<UserPrivacyPolicyAndTermsResponse> response) {
                        Log.d(TAG, "onResponse: ");

                        UserPrivacyPolicyAndTermsResponse expertTerms = response.body();
                        if (expertTerms != null && expertTerms.getSuccess()) {
                            UserPrivacyPolicyAndTermsResponse.Cms cms = expertTerms.getCms();
                            new MaterialAlertDialogBuilder(VerifyExpertBookingActivity.this)
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
               /* new MaterialAlertDialogBuilder(VerifyExpertBookingActivity.this)
                        .setTitle("Cube Talk")
                        .setMessage("I agree to CubeTalk Booking Policy")
                        .setPositiveButton("OK", null)
                        .show();*/
            }
        };
        int privacyPolicyStartIndex = bookPrivacyPolicyAndTerms.indexOf("CubeTalk");
        int privacyPolicyEndIndex = bookPrivacyPolicyAndTerms.indexOf(bookPrivacyPolicyAndTerms.length());
        //SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(bookPrivacyPolicyAndTerms);
       // spannableStringBuilder.setSpan(clickableSpan,
              //  privacyPolicyStartIndex, privacyPolicyEndIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(clickableSpan, privacyPolicyStartIndex, bookPrivacyPolicyAndTerms.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mCubeTalkBookingPolicyCheck.setText(spannableString);
        mCubeTalkBookingPolicyCheck.setMovementMethod(LinkMovementMethod.getInstance());

        mConfirmAndProceedToPayButton.setOnClickListener(v -> {
            if (!mCubeTalkBookingPolicyCheck.isChecked()) {
                Toast.makeText(this, "Please accept CubeTalk Booking Policy", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(this, PaymentActivity.class);
            intent.putExtra(BookSlotRequest.TAG, mBookSlotRequest);
            startActivity(intent);
        });

        Glide.with(this)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .circleCrop()
                .placeholder(R.drawable.ic_account_circle_white_128dp)
                .load(mBookSlotRequest.getExpertImage())
                .into(mExpertProfileImage);
        String currency=(mSharedPreferences.getInt(User.COUNTRYID, 0)==User.INDIACOUNTRYID? "INR ":"INR ");

        mExpertNameText.setText(mBookSlotRequest.getExpertName());
        mExpertSpecialitiesText.setText(mBookSlotRequest.getExpertSpecialities());
        mBookingDateAndTime.setText(convertSlotDate(mBookSlotRequest.getSlotDate()) + ", " + convertSlotTime(mBookSlotRequest.getSlotTime()) + " IST");
        mBookingSlotTypeAndPrice.setText(convertSlotType(mBookSlotRequest.getSlotType()) + " Min Call ---- "+currency + mBookSlotRequest.getAmountPaid());
        mUserNameTextInputEdit.setText(mBookSlotRequest.getUserName());
        mUserEmailTextInputEdit.setText(mBookSlotRequest.getUserEmail());
        mUserPhoneNumberTextInputEdit.setText(mBookSlotRequest.getUserPhone());
    }

    private String convertSlotType(String slotType) {
        switch (slotType) {
            case "duration_12":
                return "12";

            case "duration_25":
                return "25";

            case "duration_50":
                return "50";
        }

        return "";
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    String convertSlotDate(String slotDate){
//        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd", Locale.ENGLISH);
//        Date date;
//        try {
//            date = sdf.parse(slotDate);
//            String dateString = date.toString();
//
//            SimpleDateFormat newFormat;
//
//            if(dateString.endsWith("01") && !dateString.endsWith("11")) {
//                newFormat = new SimpleDateFormat("d'st' MMM, YYYY", Locale.ENGLISH);
//            }
//            else if(dateString.endsWith("02") && !dateString.endsWith("12"))
//                newFormat = new SimpleDateFormat("d'nd' MMM, YYYY", Locale.ENGLISH);
//
//            else if(dateString.endsWith("03") && !dateString.endsWith("13"))
//                newFormat = new SimpleDateFormat("d'rd' MMM, YYYY", Locale.ENGLISH);
//
//            else
//                newFormat = new SimpleDateFormat("d'th' MMM, YYYY", Locale.ENGLISH);
//
//            return newFormat.format(date);
//        } catch (ParseException e) {
//            Log.e(TAG, "convertSlotTime: e.getMessage(): ");
//        }
        return slotDate;
    }

    String convertSlotTime(String slotTime){
        SimpleDateFormat sdf = new SimpleDateFormat("H.mm", Locale.ENGLISH);
        Date date;
        try {
            date = sdf.parse(slotTime);
            return new SimpleDateFormat("h:mm a", Locale.ENGLISH).format(date);
        } catch (ParseException e) {
            Log.e(TAG, "convertSlotTime: e.getMessage(): ");
        }
        return "";
    }
}