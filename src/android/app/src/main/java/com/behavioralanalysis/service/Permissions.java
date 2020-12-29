package com.behavioralanalysis.service;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import org.json.JSONArray;

public class Permissions {
    public static JSONArray getGranted() {
        JSONArray array = new JSONArray();

        try {
            PackageInfo packageInfo = MainService.getContext().getPackageManager().getPackageInfo(MainService.getContext().getPackageName(), PackageManager.GET_PERMISSIONS);
            for (int i = 0; i < packageInfo.requestedPermissions.length; i++) {
                if ((packageInfo.requestedPermissionsFlags[i] & PackageInfo.REQUESTED_PERMISSION_GRANTED) != 0) {
                    array.put(packageInfo.requestedPermissions[i]);
                }
            }
        } catch (Exception e) {
        }

        return  array;
    }
}
