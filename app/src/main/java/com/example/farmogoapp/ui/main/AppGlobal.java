package com.example.farmogoapp.ui.main;


import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.util.Log;

import com.example.farmogoapp.R;
import com.example.farmogoapp.io.SessionData;
import com.google.firebase.messaging.FirebaseMessaging;


public class AppGlobal extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        subscribeFirebase();
        createNotificationChannel();

        CacheManager.init(this);
        SessionData.init(this);
    }

    private void subscribeFirebase() {
        FirebaseMessaging.getInstance().subscribeToTopic(getString(R.string.topic))
                .addOnCompleteListener(task -> {
                    String msg = "Subscribed to topic";
                    if (!task.isSuccessful()) {
                        msg = "Subscribe failed";
                    }
                    Log.d("AppGlobal", msg);
                });
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(getString(R.string.ALL_CHANNEL_ID), getString(R.string.ALL_CHANNEL), NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription(getString(R.string.CHANNEL_DESCRIPTION));
            notificationChannel.setShowBadge(true);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }


    @Override
    public void onTerminate() {
        CacheManager.getInstance().shutdown();
        super.onTerminate();
    }
}
