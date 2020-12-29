package com.behavioralanalysis.service;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Sender extends AsyncTask<String, Void, String> {

    public static String baseUrl = "http://192.168.0.111:5000/api/Log/";

    @Override
    protected String doInBackground(String... strings) {
        String data = "";
        URL url;
        try {
            url = new URL(baseUrl + strings[0]);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type","application/json");
            httpURLConnection.setRequestProperty("Accept", "application/json");
            httpURLConnection.connect();

            OutputStream outputStream = httpURLConnection.getOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
            outputStreamWriter.write(strings[1]);
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
            ex.printStackTrace();
        }

        return data;
    }

    public static void send(JSONObject toSend) {
        new Sender().execute("raw", toSend.toString());
    }
}
