package com.cubetalktest.cubetalk.adapters.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.cubetalktest.cubetalk.R;
import com.cubetalktest.cubetalk.models.SpecialityVideoResponse;

public class RelatedVideosAdapter
        extends RecyclerView.Adapter<RelatedVideosAdapter.ViewHolder> {

    private static final String TAG = RelatedVideosAdapter.class.getSimpleName();

    public static class ViewHolder
            extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    private final ArrayList<SpecialityVideoResponse.Video> mRelatedVideos;

    public RelatedVideosAdapter(ArrayList<SpecialityVideoResponse.Video> relatedVideos) {
        mRelatedVideos = relatedVideos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View relatedVideoView = inflater.inflate(R.layout.item_related_video, parent, false);
        return new ViewHolder(relatedVideoView);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mRelatedVideos.size();
    }


}
