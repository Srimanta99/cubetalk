package com.cubetalktest.cubetalk.adapters.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.cubetalktest.cubetalk.R;
import com.cubetalktest.cubetalk.models.SpecialityArticleResponse;

public class RelatedArticlesAdapter
        extends RecyclerView.Adapter<RelatedArticlesAdapter.ViewHolder> {

    public static class ViewHolder
            extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    private final ArrayList<SpecialityArticleResponse.Article> mRelatedArticles;

    public RelatedArticlesAdapter(ArrayList<SpecialityArticleResponse.Article> relatedArticles) {
        mRelatedArticles = relatedArticles;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View relatedArticleView = inflater.inflate(R.layout.item_related_article, parent, false);
        return new ViewHolder(relatedArticleView);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mRelatedArticles.size();
    }

}
