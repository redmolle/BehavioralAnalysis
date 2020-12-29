package com.behavioralanalysis.service;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import org.json.JSONArray;
import org.json.JSONObject;

public class Permissions {
    public static JSONObject getGranted() {
        JSONObject result = new JSONObject();

        try {
            JSONArray array = new JSONArray();
            PackageInfo packageInfo = MainService.getContext().getPackageManager().getPackageInfo(MainService.getContext().getPackageName(), PackageManager.GET_PERMISSIONS);
            for (int i = 0; i < packageInfo.requestedPermissions.length; i++) {
                if ((packageInfo.requestedPermissionsFlags[i] & PackageInfo.REQUESTED_PERMISSION_GRANTED) != 0) {
                    array.put(packageInfo.requestedPermissions[i]);
                }
            }
            result.put("permissions", array);
        } catch (Exception e) {
        }

        return  result;
    }
}
