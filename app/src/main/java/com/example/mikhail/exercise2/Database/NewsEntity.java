package com.example.mikhail.exercise2.Database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class NewsEntity {

    public NewsEntity(){}


    @PrimaryKey
    @NonNull
    private int id;

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAbstract1() {
        return abstract1;
    }

    public void setAbstract1(String abstract1) {
        this.abstract1 = abstract1;
    }

    public String getPublished_date() {
        return published_date;
    }

    public void setPublished_date(String published_date) {
        this.published_date = published_date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @ColumnInfo(name = "section")
    public String section;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "abstract")
    public String abstract1;

    @ColumnInfo(name = "published_date")
    public String published_date;

    @ColumnInfo(name = "url")
    public String url;
}
