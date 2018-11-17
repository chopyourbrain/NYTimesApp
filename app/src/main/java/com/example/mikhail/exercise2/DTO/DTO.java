package com.example.mikhail.exercise2.DTO;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DTO {

    private List<PictureDTO> picture;

    @SerializedName("section")
    private String section;

    @SerializedName("title")
    private String title;

    @SerializedName("abstract")
    private String text;

    @SerializedName("published_date")
    private String publishedDate;

    @SerializedName("url")
    private String url;

    public String getSection() {
        return section;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public String getUrl() {
        return url;
    }

    public List<PictureDTO> getPicture() { return picture; }
}
