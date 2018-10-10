package com.example.mikhail.exercise2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder> {
    @NonNull
    private final List<NewsItem> newsItems;
    @NonNull
    private final LayoutInflater inflater;
    @Nullable
    private final OnItemClickListener clickListener;
    @NonNull
    private final RequestManager imageLoader;

    public NewsListAdapter(@NonNull Context context, @NonNull List<NewsItem> newsItems,
                                @Nullable OnItemClickListener clickListener) {
        this.newsItems = newsItems;
        this.inflater = LayoutInflater.from(context);
        this.clickListener = clickListener;

        RequestOptions imageOption = new RequestOptions()
                .placeholder(R.drawable.github)
                .fallback(R.drawable.guitar)
                .centerCrop();
        this.imageLoader = Glide.with(context).applyDefaultRequestOptions(imageOption);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.activity_news_list, parent, false), clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(newsItems.get(position));
    }

    @Override
    public int getItemCount() {
        return newsItems.size();
    }

    public interface OnItemClickListener {
        void onItemClick(NewsItem newsItem);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final TextView labelView;
        private final TextView textView;

        ViewHolder(@NonNull View itemView, @Nullable OnItemClickListener listener) {
            super(itemView);
            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(newsItems.get(position));
                }
            });
            imageView = itemView.findViewById(R.id.textView5);
            labelView = itemView.findViewById(R.id.imageView5);
            textView = itemView.findViewById(R.id.textView6);
        }

        void bind(NewsItem newsItem) {
        //    imageLoader.load(newsItem.getImageUrl()).into(imageView);
            labelView.setText(newsItem.getTitle());
            textView.setText(newsItem.getPreviewText());
        }
    }
}