package com.cubetalktest.cubetalk.adapters.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import com.cubetalktest.cubetalk.R;
import com.cubetalktest.cubetalk.models.PromotionBannerResponse;

public class PromotionBannersAdapter
        extends RecyclerView.Adapter<PromotionBannersAdapter.ViewHolder> {

    public static class ViewHolder
            extends RecyclerView.ViewHolder {

        public ImageView mPromotionBannerImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mPromotionBannerImage = itemView.findViewById(R.id.iv_promotion_banner);
        }
    }

    private final ArrayList<PromotionBannerResponse.Data> mPromotionBannerResponseDataList;

    public PromotionBannersAdapter(ArrayList<PromotionBannerResponse.Data> promotionBannerResponseDataList) {
        mPromotionBannerResponseDataList = promotionBannerResponseDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View promotionBannerView = inflater.inflate(R.layout.item_promotion_banner, parent, false);
        return new ViewHolder(promotionBannerView);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PromotionBannerResponse.Data data = mPromotionBannerResponseDataList.get(position);

        ImageView imageView = holder.mPromotionBannerImage;
        Glide.with(holder.itemView.getContext()).load(data.getImage()).into(imageView);
    }

    @Override
    public int getItemCount() {
        return mPromotionBannerResponseDataList.size();
    }
}
