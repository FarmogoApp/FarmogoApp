package com.example.farmogoapp.ui.main;


import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.farmogoapp.io.SessionData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

public class AppGlobal extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseMessaging.getInstance().subscribeToTopic("all")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Subscribed to topic";
                        if (!task.isSuccessful()) {
                            msg = "Subscribe failed";
                        }
                        Log.d(TAG, msg);
                    }
                });
        CacheManager.init(this);
        SessionData.init(this);
    }



    @Override
    public void onTerminate() {
        CacheManager.getInstance().shutdown();
        super.onTerminate();
    }
}
