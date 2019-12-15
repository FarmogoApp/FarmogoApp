package com.example.farmogoapp.io;

import com.example.farmogoapp.model.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class FarmogoApiJacksonAdapter {

    private static FarmogoApiService API_SERVICE;

    private static ObjectMapper objectMapper = null;

    public static FarmogoApiService getApiService() {

        if (API_SERVICE != null) return API_SERVICE;

        // Creamos un interceptor y le indicamos el log level a usar
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        // Asociamos el interceptor a las peticiones
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addInterceptor(chain -> {

            User user = SessionData.getInstance().getActualUser();
            if (user == null) {
                return chain.proceed(chain.request());
            }
            Request request = chain.request();
            Request newRequest = request.newBuilder()
                    .addHeader("Authorization", "Bearer " + user.getFirebaseUuid())
                    .build();
            return chain.proceed(newRequest);
        });
        httpClient.addInterceptor(logging);

        //String baseUrl = "http://10.0.2.2:8080/api/";
        String baseUrl = "http://farmogo.quierovinos.com:8080/api/";


        ObjectMapper objectMapper = getObjectMapper();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .client(httpClient.build()) // <-- usamos el log level
                .build();
        API_SERVICE = retrofit.create(FarmogoApiService.class);

        return API_SERVICE;
    }

    public static ObjectMapper getObjectMapper() {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
            objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            objectMapper.registerModule(new JavaTimeModule());
        }
        return objectMapper;
    }


}