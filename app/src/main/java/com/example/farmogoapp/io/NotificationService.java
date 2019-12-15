package com.example.farmogoapp.io;

import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.farmogoapp.MainActivity;
import com.example.farmogoapp.R;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.JsonObject;

import java.util.Map;

public class NotificationService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d("MESSAGE","NEW COMM");
        Map<String, String> data = remoteMessage.getData();

        String val = data.get("updated");
        Log.d("ACTION", val);

        if (val == null) return;

        DataUpdater dataUpdater = new DataUpdater();
        switch (val){
            case "race":
                dataUpdater.updateRaces();
                pushNotification(getString(R.string.races_updated),"");
                break;
            case "animalType":
                dataUpdater.updateAnimalTypes();
                pushNotification(getString(R.string.animaltypes_updated),"");
                break;
        }

        /*
        if(remoteMessage.getNotification() != null) {
            pushNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
        }

         */
    }

    private void pushNotification(String title, String body){
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, getString(R.string.ALL_CHANNEL_ID))
                .setSmallIcon(R.drawable.cow)
                .setContentTitle(title)
                .setContentText(body)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setChannelId(getString(R.string.ALL_CHANNEL_ID))
                .setAutoCancel(true);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(1, builder.build());
    }
}