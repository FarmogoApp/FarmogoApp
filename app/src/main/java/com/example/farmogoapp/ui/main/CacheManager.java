package com.example.farmogoapp.ui.main;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class CacheManager {

    Context context;
    static CacheManager instance;


    Map<String, String> cache = new HashMap<>();

    private CacheManager(Context context) {
        this.context = context;
    }


    public static void init(Context context) {
        Log.d("CacheManager", "init");
        instance = new CacheManager(context);
    }

    public static CacheManager getInstance() {
        return instance;
    }

    public void shutdown() {
        Log.d("CacheManager", "shutdown");
    }


    public Response manageRequest(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        if (request.method() != "GET") {
            return chain.proceed(request);
        }

        String authorization = request.header("Authorization");
        String url = request.url().toString();
        Log.d("CacheManager", "call intecepted auth:" + authorization + " , url: " + url);


        String keyCache = String.format("%s-%s", authorization, url);
        String body = cache.get(keyCache);
        if (body == null) {
            Log.d("CacheManager", "NO CACHE");
            Response response = chain.proceed(request);
            body = response.body().string();
            Log.d("CacheManager", "CACHING: " + body);
            cache.put(keyCache, body);
        }

        Log.d("CacheManager", "USE CACHE");
        Response.Builder builder = new Response.Builder();
        builder.request(request);
        builder.protocol(Protocol.HTTP_1_1);
        builder.code(200);
        builder.message("OK");
        builder.body(ResponseBody.create(MediaType.get("application/json"), body));
        return builder.build();

    }
}

