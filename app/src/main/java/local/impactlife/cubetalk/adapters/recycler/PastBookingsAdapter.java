package local.impactlife.cubetalk.adapters.recycler;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import local.impactlife.cubetalk.databinding.ItemCardPastBookingBinding;
import local.impactlife.cubetalk.models.common.Booking;
import local.impactlife.cubetalk.models.common.Expert;
import local.impactlife.cubetalk.models.common.Topic;
import local.impactlife.cubetalk.utils.Utils;

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

        Glide.with(holder.itemView.getContext())
                .load(expert.getProfileImage())
                .into(holder.mExpertImage);

        holder.mExpertNameText.setText(expert.getName());

        holder.mConsultationTopicText.setText(topic.getName());

        holder.mConsultationSlotTypeAndPrice.setText(Utils.convertSlotType(pastBooking.getSlotType()) + " - 500 INR");

        holder.mBookedDateAndTimeText.setText(Utils.convertSlotDate(pastBooking.getCreatedAt()) + " " + Utils.convertSlotTime(pastBooking.getCreatedAt()));

        holder.mCallDateAndTimeText.setText(Utils.convertSlotDate(pastBooking.getSlotDate()) + " " + Utils.convertSlotTime(pastBooking.getSlotTime()));
    }

    @Override
    public int getItemCount() {
        return mPastBookings.size();
    }
}
