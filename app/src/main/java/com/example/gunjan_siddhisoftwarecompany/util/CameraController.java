package com.example.gunjan_siddhisoftwarecompany.util;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;

public class CameraController {

    private static boolean isFlashOn = false;

    public static void toggleFlash(Context context) {
        CameraManager cameraManager =
                (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);

        try {
            String cameraId = cameraManager.getCameraIdList()[0];
            isFlashOn = !isFlashOn;
            cameraManager.setTorchMode(cameraId, isFlashOn);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }
    private static String focusMode = "auto";

    public static void setFocusMode(String mode) {
        focusMode = mode;
    }

    public static String getFocusMode() {
        return focusMode;
    }


    public static boolean isFlashOn() {
        return isFlashOn;
    }
}
