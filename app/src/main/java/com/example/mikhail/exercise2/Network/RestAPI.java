package com.example.mikhail.exercise2.Network;

        import android.support.annotation.NonNull;
        import android.util.Log;

        import java.util.concurrent.TimeUnit;

        import okhttp3.OkHttpClient;
        import okhttp3.logging.HttpLoggingInterceptor;
        import retrofit2.Retrofit;
        import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
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
        Log.d("Log","1");
        newsEndpoint = retrofit.create(Endpoint.class);
        Log.d("Log","2");
    }

    @NonNull
    private Retrofit buildRetrofitClient(@NonNull OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
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