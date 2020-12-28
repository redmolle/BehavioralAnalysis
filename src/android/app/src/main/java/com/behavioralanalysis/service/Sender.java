package com.behavioralanalysis.service;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

public class Sender {

    public static String baseUrl = "https://localhost:44395/api/Log/";
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

    public static void showToast(String msg) {
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);

        TextView view = (TextView) toast.getView().findViewById(android.R.id.message);
        view.setTextColor(Color.RED);
        view.setTypeface(Typeface.DEFAULT_BOLD);
        view.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        toast.show();
    }

    public static void send(JSONObject toSend) {
        String url = baseUrl + "raw";

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
        }
    }
}
