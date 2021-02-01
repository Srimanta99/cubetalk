package com.cubetalktest.cubetalk.adapters.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.radiobutton.MaterialRadioButton;

import java.util.ArrayList;

import com.cubetalktest.cubetalk.R;
import com.cubetalktest.cubetalk.models.SpecialityTopicResponse;

public class SpecialityTopicsAdapter
        extends RecyclerView.Adapter<SpecialityTopicsAdapter.ViewHolder> {

    public interface OnSpecialityTopicListener {
        void onSpecialityTopicClick(int position);
    }

    public static class ViewHolder
            extends RecyclerView.ViewHolder
            implements CompoundButton.OnCheckedChangeListener {

        MaterialRadioButton mTopicRadio;

        OnSpecialityTopicListener mOnSpecialityTopicListener;

        public ViewHolder(OnSpecialityTopicListener onSpecialityTopicListener, @NonNull View itemView) {
            super(itemView);

            mOnSpecialityTopicListener = onSpecialityTopicListener;
            mTopicRadio = itemView.findViewById(R.id.mrb_topic);
            mTopicRadio.setOnCheckedChangeListener(this);
        }


        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                mOnSpecialityTopicListener.onSpecialityTopicClick(getAdapterPosition());
            }
        }
    }

    private OnSpecialityTopicListener mOnSpecialityTopicListener;
    private ArrayList<SpecialityTopicResponse.Data> mSpecialityTopics;

    public SpecialityTopicsAdapter(OnSpecialityTopicListener onSpecialityTopicListener, ArrayList<SpecialityTopicResponse.Data> specialityTopics) {
        mOnSpecialityTopicListener = onSpecialityTopicListener;
        mSpecialityTopics = specialityTopics;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View specialityTopicView = inflater.inflate(R.layout.item_speciality_topic, parent, false);

        return new ViewHolder(mOnSpecialityTopicListener, specialityTopicView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SpecialityTopicResponse.Data data = mSpecialityTopics.get(position);

        MaterialRadioButton materialRadioButton = holder.mTopicRadio;
        materialRadioButton.setText(data.getName());
    }

    @Override
    public int getItemCount() {
        return mSpecialityTopics.size();
    }

}
