package com.example.mikhail.exercise2;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class NewsDetailsActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        ImageView imageView = findViewById(R.id.imageViewDetail);
        RequestOptions imageOption = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .fallback(R.drawable.ic_launcher_background)
                .centerCrop();
        Glide.with(this).applyDefaultRequestOptions(imageOption).load(getIntent().getStringExtra("imgurl")).into(imageView);
        CollapsingToolbarLayout title = findViewById(R.id.main_collapsing);
        title.setTitle(getIntent().getStringExtra("title"));
        TextView text = findViewById(R.id.textDetail);
        text.setText(getIntent().getStringExtra("text"));
        TextView category = findViewById(R.id.categoryDetail);
        category.setText(getIntent().getStringExtra("category"));
        TextView date = findViewById(R.id.dateDetail);
        date.setText(getIntent().getStringExtra("date"));
    }

    public static void start(Activity activity, NewsItem newsItem) {
        Intent intent = new Intent(activity, NewsDetailsActivity.class);
        intent.putExtra("imgurl", newsItem.getImageUrl());
        intent.putExtra("title", newsItem.getTitle());
        intent.putExtra("text", newsItem.getFullText());
        intent.putExtra("category", newsItem.getCategory().getName());
        intent.putExtra("date", newsItem.getPublishDate().toString());
        activity.startActivity(intent);
    }
}
