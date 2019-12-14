package com.example.farmogoapp.io;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.farmogoapp.ui.main.farms.FarmStatsActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class NotificationService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Map<String, String> data = remoteMessage.getData();

        PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, FarmStatsActivity.class), 0);
        Resources r = getResources();
        Notification notification = new NotificationCompat.Builder(this)
                .setTicker("ticker")
                .setSmallIcon(android.R.drawable.ic_menu_report_image)
                .setContentTitle(data.get("title"))
                .setContentText(data.get("body"))
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);

        Log.d("MSG", "Message Received!");

    }
}