package com.behavioralanalysis.service;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class Wifi {
    public static JSONObject scan(Context context) {
        JSONObject result = new JSONObject();

        try {
            JSONArray array = new JSONArray();
            WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
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
                    result.put("networks", array);
                }
            }
        } catch (Throwable th) {
            Toast.makeText(context, "Wifi.scan", Toast.LENGTH_SHORT).show();
            Log.e("Mta.SDK", "isWifiNet error", th);
        }

        return result;
    }
}
