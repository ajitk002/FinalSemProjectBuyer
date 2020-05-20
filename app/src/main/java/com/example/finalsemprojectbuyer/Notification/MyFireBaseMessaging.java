package com.example.finalsemprojectbuyer.Notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.finalsemprojectbuyer.Chat.MessageActivity;
import com.example.finalsemprojectbuyer.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFireBaseMessaging extends FirebaseMessagingService
{
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage)
    {
        super.onMessageReceived(remoteMessage);
        String title = remoteMessage.getNotification().getTitle();
        String body = remoteMessage.getNotification().getBody();
        Map<String, String> PACAKET = remoteMessage.getData();

        String mType = PACAKET.get("mType");
        if(mType.equals("M"))
        {
            String user_id = PACAKET.get("user_id");

            NotificationCompat.Builder notificationBuilder = new
                    NotificationCompat
                            .Builder(this, "TAC")
                    .setContentTitle(title)
                    .setContentText(body)
                    .setSmallIcon(R.drawable.ic_launcher_background);
            Intent intent;

            intent = new Intent(this, MessageActivity.class);
            intent.putExtra("user_id", user_id);

            PendingIntent pendingIntent = PendingIntent.getActivity(this, 10, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            notificationBuilder.setContentIntent(pendingIntent);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            int id = (int) System.currentTimeMillis();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            {
                NotificationChannel channel = new NotificationChannel("TAC", "demo", NotificationManager.IMPORTANCE_HIGH);
                assert notificationManager != null;
                notificationManager.createNotificationChannel(channel);
            }
            assert notificationManager != null;
            notificationManager.notify(id, notificationBuilder.build());
        }
        if(mType.equals("S"))
        {
            String product_id = PACAKET.get("product_id");

            NotificationCompat.Builder notificationBuilder = new
                    NotificationCompat
                            .Builder(this, "TAC")
                    .setContentTitle(title)
                    .setContentText(body)
                    .setSmallIcon(R.drawable.ic_launcher_background);
            Intent intent;

            intent = new Intent(this, MessageActivity.class);
            intent.putExtra("product_id", product_id);

            PendingIntent pendingIntent = PendingIntent.getActivity(this, 10, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            notificationBuilder.setContentIntent(pendingIntent);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            int id = (int) System.currentTimeMillis();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            {
                NotificationChannel channel = new NotificationChannel("TAC", "demo", NotificationManager.IMPORTANCE_HIGH);
                assert notificationManager != null;
                notificationManager.createNotificationChannel(channel);
            }
            assert notificationManager != null;
            notificationManager.notify(id, notificationBuilder.build());
        }
    }
}