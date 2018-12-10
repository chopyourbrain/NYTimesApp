package com.example.mikhail.exercise2.DTO;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DTO {
    private List<PictureDTO> multimedia;

    @SerializedName("section")
    private String section;

    @SerializedName("title")
    private String title;

    @SerializedName("abstract")
    private String abstract1;

    @SerializedName("published_date")
    private String publishedDate;

    @SerializedName("url")
    private String url;

    public String getUrl() {
        return url;
    }

    public String getAbstract1() {
        return abstract1;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public String getSection() {
        return section;
    }

    public String getTitle() {
        return title;
    }

    public List<PictureDTO> getMultimedia() {
        return multimedia;
    }
}
