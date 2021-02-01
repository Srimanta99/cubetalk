package com.cubetalktest.cubetalk.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.cubetalktest.cubetalk.R;
import com.cubetalktest.cubetalk.adapters.recycler.ExpertRefundBookAdapter;
import com.cubetalktest.cubetalk.databinding.ActivityExpertRefundRequestBinding;
import com.cubetalktest.cubetalk.databinding.ContentExpertReviewRequestBinding;
import com.cubetalktest.cubetalk.models.User;

import com.cubetalktest.cubetalk.models.expert_review_refund.ExpertRefundRequest;
import com.cubetalktest.cubetalk.models.expert_review_refund.ExpertRefundResponse;
import com.cubetalktest.cubetalk.services.api.ExpertService;
import com.cubetalktest.cubetalk.services.api.ServiceBuilder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExpertRefundRequestActivity extends AppCompatActivity {

      ActivityExpertRefundRequestBinding mExpertRefundRequestBinding;
      ContentExpertReviewRequestBinding mcontentexpertReviewRequestBinding;
      RecyclerView rv_refund_bookings;
      ExpertRefundBookAdapter expertRefundBookAdapter;
      List<ExpertRefundResponse.Data>  list=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mExpertRefundRequestBinding=ActivityExpertRefundRequestBinding.inflate(LayoutInflater.from(this));
        setContentView(mExpertRefundRequestBinding.getRoot());
        mcontentexpertReviewRequestBinding=mExpertRefundRequestBinding.contentExpertRefundRequest;
        rv_refund_bookings=mcontentexpertReviewRequestBinding.rvRefundBookings;
        expertRefundBookAdapter=new ExpertRefundBookAdapter(this,list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_refund_bookings.setLayoutManager(linearLayoutManager);
        rv_refund_bookings.setAdapter(expertRefundBookAdapter);

        Toolbar toolbar = mExpertRefundRequestBinding.toolbar;
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(getResources().getString(R.string.expert_refund));
        }
     callApiforRefundRequest();

    }

    public void callApiforRefundRequest() {
        list.clear();
        SharedPreferences mSharedPreferences = getSharedPreferences(User.PREFERENCE_NAME, MODE_PRIVATE);
        String mUserId = mSharedPreferences.getString(User.ID, "");
        ExpertRefundRequest expertRefundRequest = new ExpertRefundRequest();
        expertRefundRequest.setExpertId(mUserId);

        ExpertService expertService = ServiceBuilder.buildService(ExpertService.class);
        Call<ExpertRefundResponse> request = expertService.getExpertRefunds(mSharedPreferences.getString(User.TOKEN, ""),expertRefundRequest);
        request.enqueue(new Callback<ExpertRefundResponse>() {
            @Override
            public void onResponse(Call<ExpertRefundResponse> call, Response<ExpertRefundResponse> response) {
                if (response.isSuccessful() && response.body() != null) {

                    if (response.body().getData().size()>0){
                        for (int i=0;i<response.body().getData().size();i++) {
                            list.add(response.body().getData().get(i));
                        }

                    }else
                        Toast.makeText(getApplicationContext(), "No Refund request", Toast.LENGTH_LONG).show();
                    expertRefundBookAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ExpertRefundResponse> call, Throwable t) {

            }
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
