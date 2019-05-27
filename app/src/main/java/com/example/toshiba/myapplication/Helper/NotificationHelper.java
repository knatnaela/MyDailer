package com.example.toshiba.myapplication.Helper;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.net.Uri;
import android.os.Build;

import com.example.toshiba.myapplication.R;

public class NotificationHelper extends ContextWrapper{

    private static final String GEBETA_CHANNEL_ID = "com.example.toshiba.myapplication.MyDialer";
    private static final String GEBETA_CHANNEL_NAME = "MyDialer";

    private NotificationManager manager;


    public NotificationHelper(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O) //work for api 26 or higher
            createChannel();
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {

        NotificationChannel gebetaChannel = new NotificationChannel(GEBETA_CHANNEL_ID,
                GEBETA_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT);

        gebetaChannel.enableLights(false);
        gebetaChannel.enableVibration(true);
        gebetaChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getManager().createNotificationChannel(gebetaChannel);
    }

    public NotificationManager getManager() {
        if (manager == null)
            manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        return manager;
    }

    @TargetApi(Build.VERSION_CODES.O)
    public Notification.Builder getGebetaChannelNotification(String title, String body, PendingIntent contentIntent,
                                                             Uri soundUri)
    {
        return new Notification.Builder(getApplicationContext(), GEBETA_CHANNEL_ID)
                .setContentIntent(contentIntent)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setSound(soundUri)
                .setAutoCancel(false);
    }
    @TargetApi(Build.VERSION_CODES.O)
    public Notification.Builder getGebetaChannelNotification(String title, String body,
                                                             Uri soundUri)
    {
        return new Notification.Builder(getApplicationContext(), GEBETA_CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setSound(soundUri)
                .setAutoCancel(false);
    }
}
