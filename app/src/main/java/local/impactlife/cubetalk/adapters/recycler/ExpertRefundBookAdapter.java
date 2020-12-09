package local.impactlife.cubetalk.adapters.recycler;

import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import local.impactlife.cubetalk.activities.ExpertRefundRequestActivity;
import local.impactlife.cubetalk.databinding.ItemCardReviewRefundBookingBinding;
import local.impactlife.cubetalk.models.User;
import local.impactlife.cubetalk.models.expert_cancel_status_update.ExperCancelStatusUpdateResponse;
import local.impactlife.cubetalk.models.expert_cancel_status_update.ExpertCancelStatusUpdateRequest;
import local.impactlife.cubetalk.models.expert_review_refund.ExpertRefundResponse;
import local.impactlife.cubetalk.services.api.ExpertService;
import local.impactlife.cubetalk.services.api.ServiceBuilder;
import local.impactlife.cubetalk.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class ExpertRefundBookAdapter  extends  RecyclerView.Adapter<ExpertRefundBookAdapter.ViewHolder>{

    ExpertRefundRequestActivity expertRefundRequestActivity;
    List<ExpertRefundResponse.Data> list;
    public ExpertRefundBookAdapter(ExpertRefundRequestActivity expertRefundRequestActivity, List<ExpertRefundResponse.Data> list) {
        this.expertRefundRequestActivity=expertRefundRequestActivity;
        this.list=list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ExpertRefundBookAdapter.ViewHolder(ItemCardReviewRefundBookingBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent,
                false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mExpertNameText.setText(list.get(position).getBookingUserId().getName());
        holder.mConsultationTopicText.setText(list.get(position).getTopicId().getName());
        holder.mBookedDateAndTimeText.setText(Utils.convertSlotDate(list.get(position).getCreatedAt()) + " " + Utils.convertSlotTime(list.get(position).getCreatedAt()));
        holder.mCallDateAndTimeText.setText(Utils.convertSlotDate(list.get(position).getSlotDate()) + " " + Utils.convertSlotTime(list.get(position).getSlotTime()));
        holder.mConsultationSlotTypeAndPrice.setText(Utils.convertSlotType(list.get(position).getSlotType()) + " -INR"+list.get(position).getAmountPaid());

        Glide.with(holder.itemView.getContext())
                .load("kgh")
                .into(holder.mExpertImage);

        holder.mAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callApiforcancelStatusUpdate("2",list.get(position));

            }
        });

        holder.mDisagree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callApiforcancelStatusUpdate("3",list.get(position));
            }
        });
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ShapeableImageView mExpertImage;
        MaterialTextView mExpertNameText;
        MaterialTextView mConsultationTopicText;
        MaterialTextView mConsultationSlotTypeAndPrice;
        MaterialTextView mBookedDateAndTimeText;
        MaterialTextView mCallDateAndTimeText;
        MaterialButton mAgree;
        MaterialButton mDisagree;
        MaterialTextView mtvReasonforrefund;

        public ViewHolder(@NonNull @NotNull ItemCardReviewRefundBookingBinding itemBinding) {
            super(itemBinding.getRoot());
            mExpertImage = itemBinding.sivExpertProfileImage;
            mExpertNameText = itemBinding.mtvExpertName;
            mConsultationTopicText = itemBinding.mtvConsultationTopic;
            mConsultationSlotTypeAndPrice = itemBinding.mtvConsultationSlotTypeAndPrice;
            mBookedDateAndTimeText = itemBinding.mtvBookedDateAndTime;
            mCallDateAndTimeText = itemBinding.mtvCallDateAndTime;
            mtvReasonforrefund=itemBinding.mtvReasonforrefund;
            mAgree = itemBinding.mbtnAgree;
            mDisagree = itemBinding.mbtnDiagree;
        }
    }
    private void callApiforcancelStatusUpdate(String s, ExpertRefundResponse.Data data) {
        SharedPreferences mSharedPreferences = expertRefundRequestActivity.getSharedPreferences(User.PREFERENCE_NAME, MODE_PRIVATE);
        String mUserId = mSharedPreferences.getString(User.ID, "");
        ExpertCancelStatusUpdateRequest expertRefundRequest = new ExpertCancelStatusUpdateRequest();
        expertRefundRequest.setBooking_cancel_status(s);
        expertRefundRequest.setBooked_expert_id(mUserId);
        expertRefundRequest.setBooked_id(data.getBookedId());

        ExpertService expertService = ServiceBuilder.buildService(ExpertService.class);
        Call<ExperCancelStatusUpdateResponse> request = expertService.getExpertRefundrequestUpdate(mSharedPreferences.getString(User.TOKEN, ""),expertRefundRequest);
        request.enqueue(new Callback<ExperCancelStatusUpdateResponse>() {
            @Override
            public void onResponse(Call<ExperCancelStatusUpdateResponse> call, Response<ExperCancelStatusUpdateResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if ((response.body().isSuccess())) {
                        Toast.makeText(expertRefundRequestActivity, response.body().getMessage(), Toast.LENGTH_LONG).show();
                        expertRefundRequestActivity.callApiforRefundRequest();
                    }else
                        Toast.makeText(expertRefundRequestActivity, response.body().getMessage(), Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<ExperCancelStatusUpdateResponse> call, Throwable t) {

            }
        });
    }
}
