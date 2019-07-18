package com.simanisandroid.simanis.Notifikasi;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.view.View;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.simanisandroid.simanis.DetailActivity;
import com.simanisandroid.simanis.HomeActivity;
import com.simanisandroid.simanis.R;

public class NotifikasiMessagingService extends FirebaseMessagingService {
    private static final int NOTIFICATION_ID = 0;

    private static final String NOTIFICATION_CHANNEL_1_NAME = "Volume Infus";
    private static final String NOTIFICATION_CHANNEL_1_DESC = "Notifikasi Infus Habis";
    private static final String NOTIFICATION_CHANNEL_1_ID = "CHANNEL_1";


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        createNotificationChannel();
        //check if message contain a data playload
        if (remoteMessage.getData().size() > 0) {
            showNotification(NOTIFICATION_CHANNEL_1_ID,NOTIFICATION_ID, remoteMessage.getData().get("title"),
            remoteMessage.getData().get("body"),remoteMessage.getData().get("ruangan"),
                    remoteMessage.getData().get("bangsal"));
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_1_ID,
                    NOTIFICATION_CHANNEL_1_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription(NOTIFICATION_CHANNEL_1_DESC);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            assert notificationManager != null;

            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    private void showNotification(String channel, int id, String title, String body, String ruangan, String bangsal) {

        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channel)
                .setContentTitle("Infus Pasien di "+ruangan +" "+bangsal)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .setPriority(Notification.PRIORITY_MAX)
                .setDefaults(Notification.DEFAULT_ALL);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            notificationBuilder.setPriority(Notification.PRIORITY_HIGH);
        }

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        assert notificationManager != null;
        notificationManager.notify(id, notificationBuilder.build());
    }
}
