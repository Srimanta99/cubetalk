package com.cubetalktest.cubetalk.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.cubetalktest.cubetalk.databinding.ActivityPaymentBinding;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textview.MaterialTextView;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import com.cubetalktest.cubetalk.R;

import com.cubetalktest.cubetalk.models.User;
import com.cubetalktest.cubetalk.models.creditshell.CreditShellResponse;
import com.cubetalktest.cubetalk.models.post_booking_slot.BookSlotRequest;
import com.cubetalktest.cubetalk.models.post_booking_slot.BookSlotResponse;
import com.cubetalktest.cubetalk.services.api.BookingService;
import com.cubetalktest.cubetalk.services.api.CreditShellService;
import com.cubetalktest.cubetalk.services.api.ServiceBuilder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity implements PaymentResultListener {

    private static final String TAG = PaymentActivity.class.getSimpleName();

    private ActivityPaymentBinding mActivityBinding;
    private MaterialButton mPayAndBookSlotButton;
    private MaterialCheckBox materialCheckBox;
    private BookSlotRequest mBookSlotRequest;
    private SharedPreferences mSharedPreferences;
    MaterialTextView materialTvAmountPaid;
    MaterialTextView materialTvCreditshellamount;
    String creditCellAmount,amounttobrPaid;
    boolean payusingCreditShell=false;
    int amountAfterCreditAmount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_payment);

        mActivityBinding = ActivityPaymentBinding.inflate(LayoutInflater.from(this));
        setContentView(mActivityBinding.getRoot());
        mSharedPreferences = getSharedPreferences(User.PREFERENCE_NAME, Context.MODE_PRIVATE);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mBookSlotRequest = getIntent().getParcelableExtra(BookSlotRequest.TAG);
        String currency=(mSharedPreferences.getInt(User.COUNTRYID, 0)==User.INDIACOUNTRYID? "INR ":"INR ");
        amounttobrPaid=mBookSlotRequest.getAmountPaid();
        mPayAndBookSlotButton = mActivityBinding.mbtnPayAndBookSlot;
        materialCheckBox=mActivityBinding.materialCheckBox;
        materialTvAmountPaid=mActivityBinding.materialTvAmountPaid;
        materialTvCreditshellamount=mActivityBinding.materialTextViewCreditamount;
        //materialTvCreditshellamount.setText(materialTvCreditshellamount.getText()+" "+ currency);
        materialTvAmountPaid.setText(materialTvAmountPaid.getText()+currency + mBookSlotRequest.getAmountPaid());
      // mPayAndBookSlotButton.setText(mPayAndBookSlotButton.getText() + mBookSlotRequest.getAmountPaid());
        mPayAndBookSlotButton.setText("CLICK HERE FOR ONLINE PAYMENT \n(debit card/credit card/net banking/wallet)");
        materialCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    payusingCreditShell=true;
                    mPayAndBookSlotButton.setText("CLICK HERE FOR BOOKING EXPERT \n USING CREDIT SHELL");
                }else {
                    payusingCreditShell=false;
                    mPayAndBookSlotButton.setText("CLICK HERE FOR ONLINE PAYMENT \n(debit card/credit card/net banking/wallet)");

                }
            }
        });
        mPayAndBookSlotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  mPayAndBookSlotButton.setEnabled(false);
                if (payusingCreditShell){
                    if (Integer.parseInt(amounttobrPaid)<=Integer.parseInt(creditCellAmount)) {
                        int aa=   amountAfterCreditAmount=Integer.parseInt(creditCellAmount)-Integer.parseInt(amounttobrPaid);
                        materialTvCreditshellamount.setText("INR "+String.valueOf(aa));
                        callApiforpaymentCreditShell(0);
                    } else{
                         amountAfterCreditAmount=Integer.parseInt(amounttobrPaid)-Integer.parseInt(creditCellAmount);
                      //  callApiforpaymentCreditShell(0);
                        materialTvCreditshellamount.setText("INR 0.0");
                        startpayment(String.valueOf(amountAfterCreditAmount));
                    }
                }else
                 startpayment(amounttobrPaid);
             //  callApiforpaymentStatusUpdate();
            }
        });
        callApiForCreditshell();
    }
    private void callApiForCreditshell() {

        CreditShellService creditShellService = ServiceBuilder.buildService(CreditShellService.class);
        Call<CreditShellResponse> createRequest = creditShellService.getcreditShell(mSharedPreferences.getString(User.TOKEN, ""),mSharedPreferences.getString(User.ID, ""));
        createRequest.enqueue(new Callback<CreditShellResponse>() {
            @Override
            public void onResponse(Call<CreditShellResponse> call, Response<CreditShellResponse> response) {
                Log.d(TAG, "onResponse: responseshell: " + response.toString());
                if (response.body().getSuccess()){
                    creditCellAmount=response.body().getTotalShellBalance().toString();
                    materialTvCreditshellamount.setText("INR "+creditCellAmount);
                   // materialTvCreditshellamount.setText(materialTvCreditshellamount.getText()+" "+ currency);
                }else
                    creditCellAmount="0";

            }

            @Override
            public void onFailure(Call<CreditShellResponse> call, Throwable t) {
                //Log.d(TAG, "onResponse: response: " + response.toString());
            }
        });

    }
    private void callApiforpaymentStatusUpdate() {
        String currency=(mSharedPreferences.getInt(User.COUNTRYID, 0)==User.INDIACOUNTRYID? "INR":"NRI");
        mBookSlotRequest.setPaymentMode("DEBIT_CARD");
        mBookSlotRequest.setAmountPaid(mBookSlotRequest.getAmountPaid());
        mBookSlotRequest.setStatus(1);
        mBookSlotRequest.setPaymentStatus("COMPLETE");
        mBookSlotRequest.setAmount_currency_type(currency);
        mBookSlotRequest.setCreditShellApplied(payusingCreditShell);
        mBookSlotRequest.setTotalAmountIncludeCreditShell(Integer.valueOf(amounttobrPaid));
        mBookSlotRequest.setCreditShellamount(Integer.valueOf(creditCellAmount));

        BookingService bookingService = ServiceBuilder.buildService(BookingService.class);
        Call<BookSlotResponse> createRequest = bookingService.postBookingDetails(mSharedPreferences.getString(User.TOKEN, ""),mBookSlotRequest);

        createRequest.enqueue(new Callback<BookSlotResponse>() {
            @Override
            public void onResponse(@NotNull Call<BookSlotResponse> call, @NotNull Response<BookSlotResponse> response) {
                Log.d(TAG, "onResponse: ");
                if (response.isSuccessful()) {
                    BookSlotResponse bookSlotResponse = response.body();
                    Log.d(TAG, "onResponse: inside");
                    new MaterialAlertDialogBuilder(PaymentActivity.this)
                            .setTitle("Cube Talk")
                            .setMessage(bookSlotResponse.getMessage())
                            .setPositiveButton("Ok", (dialog, which) -> {
                                //startpayment();
                                        Intent intent = new Intent(PaymentActivity.this, HomeActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                                Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                            })
                            .show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<BookSlotResponse> call, @NotNull Throwable throwable) {
                Log.d(TAG, "onFailure: throwable.getMessage(): " + throwable.getMessage());
            }
        });
    }

    private void callApiforpaymentCreditShell(int i) {
        String currency=(mSharedPreferences.getInt(User.COUNTRYID, 0)==User.INDIACOUNTRYID? "INR":"NRI");
        mBookSlotRequest.setPaymentMode("DEBIT_CARD");
        mBookSlotRequest.setAmountPaid(mBookSlotRequest.getAmountPaid());
        mBookSlotRequest.setStatus(1);
        mBookSlotRequest.setPaymentStatus("COMPLETE");
        mBookSlotRequest.setAmount_currency_type(currency);
        mBookSlotRequest.setCreditShellApplied(true);
        mBookSlotRequest.setTotalAmountIncludeCreditShell(Integer.valueOf(amounttobrPaid));
        mBookSlotRequest.setCreditShellamount(Integer.valueOf(creditCellAmount));


        BookingService bookingService = ServiceBuilder.buildService(BookingService.class);
        Call<BookSlotResponse> createRequest = bookingService.postBookingDetails(mSharedPreferences.getString(User.TOKEN, ""),mBookSlotRequest);

        createRequest.enqueue(new Callback<BookSlotResponse>() {
            @Override
            public void onResponse(@NotNull Call<BookSlotResponse> call, @NotNull Response<BookSlotResponse> response) {
                Log.d(TAG, "onResponse: ");
                if (response.isSuccessful()) {
                    BookSlotResponse bookSlotResponse = response.body();
                    Log.d(TAG, "onResponse: inside");

                    new MaterialAlertDialogBuilder(PaymentActivity.this)
                            .setTitle("Cube Talk")
                            .setMessage(bookSlotResponse.getMessage())
                             .setPositiveButton("Ok", (dialog, which) -> {
                                //startpayment();
                                Intent intent = new Intent(PaymentActivity.this, HomeActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            })
                            .show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<BookSlotResponse> call, @NotNull Throwable throwable) {
                Log.d(TAG, "onFailure: throwable.getMessage(): " + throwable.getMessage());
            }
        });
    }

    private void startpayment(String amounttobrPaid) {
            /**
             * You need to pass current activity in order to let Razorpay create CheckoutActivity
             */
            final Activity activity = this;

            final Checkout co = new Checkout();

            try {
                //int image = R.mipmap.ic_launcher;
               /*
                Bitmap bitmapOrg = BitmapFactory.decodeResource(getResources(),  R.drawable.cubetalk_logo);
                ByteArrayOutputStream bao = new ByteArrayOutputStream();
                bitmapOrg.compress(Bitmap.CompressFormat.JPEG, 100, bao);
                byte [] ba = bao.toByteArray();
                String ba1=Base64.encodeToString(ba, Base64.DEFAULT);*/

                JSONObject options = new JSONObject();
                options.put("name", "CubeTalk");
                options.put("description", "Video Consulting Charges");
                //You can omit the image option to fetch the image from dashboard
             //   options.put("image", "https://rzp-mobile.s3.amazonaws.com/images/rzp.png");
               // options.put("image", "http://45.79.24.49:7200/public/logo.png");
                //options.put("image", ba1);
                options.put("currency", "INR");
               // String payment = mBookSlotRequest.getAmountPaid();

                double total = Double.parseDouble(amounttobrPaid);
                total = total * 100;
                options.put("amount", total);

                JSONObject preFill = new JSONObject();
                preFill.put("email", mSharedPreferences.getString(User.EMAIL, ""));
                preFill.put("contact", mSharedPreferences.getString(User.PHONE, ""));
                int image = R.drawable.cubetalk_logo;
                options.put("prefill", preFill);
                co.setImage(image);

                co.open(activity, options);
            } catch (Exception e) {
                Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        Checkout.clearUserData(this);
       // if(payusingCreditShell){
           // materialTvCreditshellamount.setText("INR "+creditCellAmount);
      //  }
        callApiforpaymentStatusUpdate();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this,s,Toast.LENGTH_LONG).show();

    }
}