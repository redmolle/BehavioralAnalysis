package com.behavioralanalysis.service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

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

        array.put(walk(dir));

        return array;
    }

    private static JSONObject walk(File sourceFile) {
        JSONObject result = new JSONObject();
        try {
            result.put("name", sourceFile.getName());
            result.put("isDir", sourceFile.isDirectory());
            result.put("path", sourceFile.getAbsoluteFile());
            result.put("size", sourceFile.length() / 1024);
            if (sourceFile.isDirectory()) {
                File[] files = sourceFile.listFiles();
                JSONArray array = new JSONArray();
                for (File file : files) {
                    array.put(walk(file));
                }
                result.put("files", array);
            } else {
                return result;
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        return result;
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
                Sender.downloadFile(object);
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
