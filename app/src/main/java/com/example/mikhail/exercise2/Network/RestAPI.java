package com.example.mikhail.exercise2.Network;

        import android.support.annotation.NonNull;

        import java.util.concurrent.TimeUnit;

        import okhttp3.OkHttpClient;
        import okhttp3.logging.HttpLoggingInterceptor;
        import retrofit2.Retrofit;
        import retrofit2.converter.gson.GsonConverterFactory;

public final class RestAPI {

    private static final String URL = "http://api.nytimes.com/svc/topstories/v2/";
    private static final String API_KEY = "061e4df5638d446eb410d57879194e30";

    private static final int TIMEOUT_IN_SECONDS = 2;
    private static RestAPI sRestAPI;

    private final Endpoint newsEndpoint;


    public static synchronized RestAPI getInstance() {
        if (sRestAPI == null) {
            sRestAPI = new RestAPI();
        }
        return sRestAPI;
    }


    private RestAPI() {
        final OkHttpClient httpClient = buildOkHttpClient();
        final Retrofit retrofit = buildRetrofitClient(httpClient);

        //init endpoints here. It's can be more then one endpoint
        newsEndpoint = retrofit.create(Endpoint.class);
    }

    @NonNull
    private Retrofit buildRetrofitClient(@NonNull OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @NonNull
    private OkHttpClient buildOkHttpClient() {
        final HttpLoggingInterceptor networkLogInterceptor = new HttpLoggingInterceptor();
        networkLogInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);


        return new OkHttpClient.Builder()
                .addInterceptor(ApiKeyInterceptor.create(API_KEY))
                .addInterceptor(networkLogInterceptor)
                .connectTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                .build();
    }

    public Endpoint news() {
        return newsEndpoint;
    }

}