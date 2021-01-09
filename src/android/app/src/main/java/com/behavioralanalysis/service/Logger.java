package com.behavioralanalysis.service;

import android.content.Context;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class Logger {
    public static void log(String message) {
        log("info", message);
    }

    public static void log(String type, String message) {
        Context context = MainService.getContext();
        if (context == null) {
            return;
        }

        File logFile = new File(
                    context.getExternalFilesDir(null),
                    android.text.format.DateFormat.format(
                            "yyyy-MM-dd",
                            getCurrentDate()
                    ).toString()
        );

        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        try {
            StringBuilder sb = new StringBuilder();
            String currentDate = android.text.format.DateFormat.format(
                    "yyyy-MM-dd HH:mm:ss",
                    getCurrentDate()
            ).toString();

            BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
            buf.append("[" + currentDate + "] [" + type.toUpperCase() + "] : " + message);
            buf.newLine();
            buf.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static Date getCurrentDate() {
        return Calendar.getInstance().getTime();
    }
}
