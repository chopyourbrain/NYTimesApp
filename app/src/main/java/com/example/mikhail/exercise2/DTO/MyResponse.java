package com.example.mikhail.exercise2.DTO;

import com.example.mikhail.exercise2.DTO.DTO;

import java.util.List;

import io.reactivex.annotations.Nullable;

public class MyResponse {
    private List<DTO> results;

    @Nullable
    public List<DTO> getData() {
        return results;
    }
}