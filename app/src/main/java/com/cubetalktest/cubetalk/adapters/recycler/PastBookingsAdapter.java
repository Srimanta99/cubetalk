package com.cubetalktest.cubetalk.adapters.recycler;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import com.cubetalktest.cubetalk.databinding.ItemCardPastBookingBinding;
import com.cubetalktest.cubetalk.models.common.Booking;
import com.cubetalktest.cubetalk.models.common.Expert;
import com.cubetalktest.cubetalk.models.common.Topic;
import com.cubetalktest.cubetalk.utils.Utils;

public class PastBookingsAdapter extends RecyclerView.Adapter<PastBookingsAdapter.ViewHolder> {

    private static final String TAG = PastBookingsAdapter.class.getSimpleName();

    class ViewHolder extends RecyclerView.ViewHolder {

        ShapeableImageView mExpertImage;
        MaterialTextView mExpertNameText;
        MaterialTextView mConsultationTopicText;
        MaterialTextView mConsultationSlotTypeAndPrice;
        MaterialTextView mBookedDateAndTimeText;
        MaterialTextView mCallDateAndTimeText;
        RatingBar mBookingRating;

        public ViewHolder(@NonNull @NotNull ItemCardPastBookingBinding itemBinding) {
            super(itemBinding.getRoot());
            mExpertImage = itemBinding.sivExpertProfileImage;
            mExpertNameText = itemBinding.mtvExpertName;
            mConsultationTopicText = itemBinding.mtvConsultationTopic;
            mConsultationSlotTypeAndPrice = itemBinding.mtvConsultationSlotTypeAndPrice;
            mBookedDateAndTimeText = itemBinding.mtvBookedDateAndTime;
            mCallDateAndTimeText = itemBinding.mtvCallDateAndTime;
            mBookingRating = itemBinding.rbBookingRating;
        }
    }

    private List<Booking> mPastBookings;

    public PastBookingsAdapter(List<Booking> pastBookings) {
        mPastBookings = pastBookings;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolder(ItemCardPastBookingBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Booking pastBooking = mPastBookings.get(position);

        Expert expert = pastBooking.getExpert();
        Topic topic = pastBooking.getTopic();

        if(expert!=null) {
            Glide.with(holder.itemView.getContext())
                    .load(expert.getProfileImage())
                    .into(holder.mExpertImage);
        }

         if ((expert!=null))
            holder.mExpertNameText.setText(expert.getName());
         //else
            // holder.mExpertNameText.setText("NA");

        holder.mConsultationTopicText.setText(topic.getName());

        holder.mConsultationSlotTypeAndPrice.setText(Utils.convertSlotType(pastBooking.getSlotType()) + " - INR "+pastBooking.getAmount_paid());

        holder.mBookedDateAndTimeText.setText(Utils.convertSlotDate(pastBooking.getCreatedAt()) + " " + Utils.convertSlotTime(pastBooking.getCreatedAt()));

        holder.mCallDateAndTimeText.setText(Utils.convertSlotDate(pastBooking.getSlotDate()) + " " + Utils.convertSlotTime(pastBooking.getSlotTime()));
    }

    @Override
    public int getItemCount() {
        return mPastBookings.size();
    }
}
