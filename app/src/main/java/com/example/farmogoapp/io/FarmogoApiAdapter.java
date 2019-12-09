package com.example.farmogoapp.io;

import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FarmogoApiAdapter {

    private static FarmogoApiService API_SERVICE;
    private static FirebaseAuth mAuth;
    public static FarmogoApiService getApiService() {
        mAuth = FirebaseAuth.getInstance();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request newRequest  = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer " + mAuth.getCurrentUser().getUid())
                        .build();
                return chain.proceed(newRequest);
            }
        });
        httpClient.addInterceptor(logging);
        String baseUrl = "http://10.0.2.2:8080/api/";

        if (API_SERVICE == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
            API_SERVICE = retrofit.create(FarmogoApiService.class);
        }

        return API_SERVICE;
    }
}