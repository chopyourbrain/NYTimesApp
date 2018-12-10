package com.example.mikhail.exercise2.Network;

import com.example.mikhail.exercise2.DTO.MyResponse;

import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Endpoint {

    @GET("{category}.json")
    Single<MyResponse> search(@Path("category") String search);

}
