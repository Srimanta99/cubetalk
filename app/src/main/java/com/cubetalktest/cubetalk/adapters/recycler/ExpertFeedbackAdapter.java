package com.cubetalktest.cubetalk.adapters.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

import com.cubetalktest.cubetalk.databinding.ItemLinearLayoutExpertFeedbackBinding;

public class ExpertFeedbackAdapter extends RecyclerView.Adapter<ExpertFeedbackAdapter.ViewHolder> {
    
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public RatingBar mExpertFeedbackRating;
        public MaterialTextView mExpertFeedbackText;
        public MaterialTextView mExpertFeedbackByText;

        public ViewHolder(@NonNull ItemLinearLayoutExpertFeedbackBinding itemBinding) {
            super(itemBinding.getRoot());

            mExpertFeedbackRating = itemBinding.rbExpertFeedback;
            mExpertFeedbackText = itemBinding.mtvExpertFeedback;
            mExpertFeedbackByText = itemBinding.mtvExpertFeedbackBy;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

//        View expertFeedbackView = inflater.inflate(R.layout.item_linear_layout_expert_feedback, parent, false);
//        return new ViewHolder(expertFeedbackView);

        return new ViewHolder(ItemLinearLayoutExpertFeedbackBinding.inflate(inflater, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

}
