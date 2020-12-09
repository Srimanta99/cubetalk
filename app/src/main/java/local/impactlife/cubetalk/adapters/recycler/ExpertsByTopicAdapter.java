package local.impactlife.cubetalk.adapters.recycler;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.button.MaterialButton;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import local.impactlife.cubetalk.R;
import local.impactlife.cubetalk.databinding.ItemCardExpertByTopicBinding;
import local.impactlife.cubetalk.models.common.Expert;
import local.impactlife.cubetalk.models.common.SpecialityTopic;
import local.impactlife.cubetalk.models.common.Topic;

public class ExpertsByTopicAdapter extends RecyclerView.Adapter<ExpertsByTopicAdapter.ViewHolder> {

    private Context mContext;

    public interface OnExpertByTopicsListener {
        void onExpertViewProfileClick(int position);

        void onExpertBookNowClick(int position);
    }

    public static class ViewHolder
            extends RecyclerView.ViewHolder {

        OnExpertByTopicsListener mOnExpertByTopicsListener;

        ImageView mExpertProfileImage;
        TextView mNameText;
        TextView mYearOfExperienceText;
        TextView mProfessionalSummaryText;
        TextView mExpertInTopicsText;
        TextView mLanguagesText;

        MaterialButton mViewProfileButton;
        MaterialButton mBookNowButton;

        public ViewHolder(OnExpertByTopicsListener onExpertByTopicsListener, @NonNull @NotNull ItemCardExpertByTopicBinding itemBinding) {
            super(itemBinding.getRoot());

            mOnExpertByTopicsListener = onExpertByTopicsListener;

            mExpertProfileImage = itemBinding.ivExpertProfileImage;
            mNameText = itemBinding.tvExpertName;
            mYearOfExperienceText = itemBinding.tvYearsOfExperience;
            mProfessionalSummaryText = itemBinding.tvProfessionalSummary;
            mExpertInTopicsText = itemBinding.tvExpertInTopics;
            mLanguagesText = itemBinding.tvExpertConsultationLanguages;
            mViewProfileButton = itemBinding.mbtnViewProfile;
            mBookNowButton = itemBinding.mbtnBookNow;

            mViewProfileButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnExpertByTopicsListener.onExpertViewProfileClick(getAdapterPosition());
                }
            });
            mBookNowButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnExpertByTopicsListener.onExpertBookNowClick(getAdapterPosition());
                }
            });
        }

    }

    ArrayList<Expert> mExpertsByTopic;
    OnExpertByTopicsListener mOnExpertByTopicsListener;

    public ExpertsByTopicAdapter(OnExpertByTopicsListener onExpertByTopicsListener, ArrayList<Expert> expertsByTopic) {
        mExpertsByTopic = expertsByTopic;
        mOnExpertByTopicsListener = onExpertByTopicsListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);

//        View expertByTopicView = inflater.inflate(R.layout.item_card_expert_by_topic, parent, false);

        return new ViewHolder(mOnExpertByTopicsListener, ItemCardExpertByTopicBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Expert expert = mExpertsByTopic.get(position);

        holder.mNameText.setText(expert.getName());

        holder.mYearOfExperienceText.setText(expert.getYearsOfExperience() + " years of experience");

        if (expert.getProfessionalSummary() != null) {
            holder.mProfessionalSummaryText.setText(expert.getProfessionalSummary());
        }

        ArrayList<String> topicNames = new ArrayList<>();

        for (SpecialityTopic specialityTopic : expert.getSpecialityTopics()) {
            for (Topic topic : specialityTopic.getTopics()) {
                topicNames.add(topic.getName());
            }
        }

        holder.mExpertInTopicsText.setText(TextUtils.join(", ", topicNames));

        holder.mLanguagesText.setText(expert.getLanguages());

        Glide
                .with(mContext)
                .asBitmap()
                .circleCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .load(expert.getProfileImage())
                .placeholder(R.drawable.ic_account_circle_black_24dp)
                .into(holder.mExpertProfileImage);
    }

    @Override
    public int getItemCount() {
        return mExpertsByTopic.size();
    }

}
