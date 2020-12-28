package com.behavioralanalysis.service;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.sql.PreparedStatement;

public class Files {
    public static JSONArray walk(String path) {
        JSONArray result = new JSONArray();
        /*File directory = new File(path);
        if (!directory.canRead()) {
            try {
                JSONObject error = new JSONObject();
                error.put("type", "error");
                error.put("error", "access denied");
                Sender.Send(error);
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }

        File[] files = directory.listFiles();
        if (files != null) {
            try {
                JSONObject root = new
            }
        }*/

        return result;
    }
}
