package com.example.mikhail.exercise2.Network;

import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Endpoint {
    @NonNull
    @GET("{category}.json")
    Single<RestAPI> search(@Path("category") String category);
}
