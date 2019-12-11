package com.example.farmogoapp.io;

import android.content.Context;

import com.example.farmogoapp.ui.main.CacheManager;
import com.example.farmogoapp.ui.main.Session;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class FarmogoApiJacksonAdapter {

    private static FarmogoApiService API_SERVICE;

    public static FarmogoApiService getApiService(Context ctx) {

        // Creamos un interceptor y le indicamos el log level a usar
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        // Asociamos el interceptor a las peticiones
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        final Session s = new Session(ctx);
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Request newRequest = request.newBuilder()
                        .addHeader("Authorization", "Bearer " + s.getApiAuth())
                        .build();
                return chain.proceed(newRequest);
            }
        });

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                return CacheManager.getInstance().manageRequest(chain);
            }
        });

        httpClient.addInterceptor(logging);

        //String baseUrl = "http://10.0.2.2:8080/api/";
        String baseUrl = "http://farmogo.quierovinos.com:8080/api/";


        if (API_SERVICE == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(JacksonConverterFactory.create())
                    .client(httpClient.build()) // <-- usamos el log level
                    .build();
            API_SERVICE = retrofit.create(FarmogoApiService.class);
        }

        return API_SERVICE;
    }

}