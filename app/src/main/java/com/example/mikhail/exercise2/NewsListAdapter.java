package com.example.mikhail.exercise2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public class NewsListAdapter extends BaseAdapter {
    private final List<NewsItem> news;
    private final Context context;
    private final LayoutInflater inflater;

    public NewsListAdapter(List<NewsItem> news, Context context, LayoutInflater inflater) {
        this.news = news;
        this.context = context;
        this.inflater = inflater;
    }


    @Override
    public int getCount() {
        return news.size();
    }

    @Override
    public NewsItem getItem(int position) {
        return news.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowItem = inflater.inflate(R.layout.activity_news_list, parent, false);

        return rowItem;
    }
}
