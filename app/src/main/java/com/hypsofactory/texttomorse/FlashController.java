package com.hypsofactory.texttomorse;

import android.accessibilityservice.AccessibilityService;
import android.app.Activity;
import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.view.View;

import androidx.annotation.RequiresApi;

public class FlashController extends Activity {

    private static AccessibilityService context;
    public FlashController(Context context) {
        FlashController.context = (AccessibilityService) context;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP + Build.VERSION_CODES.M)
    public static void flashLightOn() throws CameraAccessException {
        CameraManager cameraManager = (CameraManager)context.getSystemService(Context.CAMERA_SERVICE);
        assert cameraManager != null;
        String cameraId = cameraManager.getCameraIdList()[0];
        cameraManager.setTorchMode(cameraId, true);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP + Build.VERSION_CODES.M)
    public static void flashLightOff() throws CameraAccessException {
        CameraManager cameraManager = (CameraManager)context.getSystemService(Context.CAMERA_SERVICE);
        assert cameraManager != null;
        String cameraId = cameraManager.getCameraIdList()[0];
        cameraManager.setTorchMode(cameraId, false);
    }
}
