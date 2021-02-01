package com.cubetalktest.cubetalk.adapters.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import com.cubetalktest.cubetalk.R;
import com.cubetalktest.cubetalk.models.SpecialityResponse;

public class SpecialitiesAdapter
        extends RecyclerView.Adapter<SpecialitiesAdapter.SpecialityViewHolder> {

    public interface OnSpecialityListener {
        void onSpecialityClick(int position);
    }

    public static class SpecialityViewHolder
            extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        ImageView mSpecialityImage;
        TextView mSpecialityText;

        OnSpecialityListener mOnSpecialityListener;

        SpecialityViewHolder(OnSpecialityListener onSpecialityListener, @NonNull View itemView) {
            super(itemView);

            mOnSpecialityListener = onSpecialityListener;
            mSpecialityImage = itemView.findViewById(R.id.iv_speciality_logo);
            mSpecialityText = itemView.findViewById(R.id.tv_speciality_name);
        }

        @Override
        public void onClick(View v) {

        }
    }

    private OnSpecialityListener mOnSpecialityListener;
    private ArrayList<SpecialityResponse.Data> mSpecialities;

    public SpecialitiesAdapter(OnSpecialityListener onSpecialityListener, ArrayList<SpecialityResponse.Data> specialities) {
        mOnSpecialityListener = onSpecialityListener;
        mSpecialities = specialities;
    }

    @NonNull
    @Override
    public SpecialityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View specialityView = inflater.inflate(R.layout.item_speciality, parent, false);

        return new SpecialityViewHolder(mOnSpecialityListener, specialityView);
    }

    @Override
    public void onBindViewHolder(@NonNull SpecialityViewHolder holder, int position) {
        SpecialityResponse.Data data = mSpecialities.get(position);

        View itemView = holder.itemView;

        Context context = itemView.getContext();

        ImageView imageView = holder.mSpecialityImage;
        Glide.with(context).load(data.getIcon()).into(imageView);

        TextView textView = holder.mSpecialityText;
        textView.setText(data.getName());

        itemView.setOnClickListener(v -> mOnSpecialityListener.onSpecialityClick(position));
    }

    @Override
    public int getItemCount() {
        return mSpecialities.size();
    }


}
