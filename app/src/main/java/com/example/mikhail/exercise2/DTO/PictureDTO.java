package com.example.mikhail.exercise2.DTO;

import com.google.gson.annotations.SerializedName;

public class PictureDTO {

    @SerializedName("url")
    private String url;

    @SerializedName("format")
    private String format;


    public String getUrl() {
        return url;
    }

    public String getFormat() {
        return format;
    }
}
