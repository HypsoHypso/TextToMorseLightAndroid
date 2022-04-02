package com.hypsofactory.texttomorse;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    // UI
    EditText inputText;

    TextView plainTextTV;
    TextView morseCodeTV;

    Button cancelButton;
    // CAROUSEL

    // FLASH
    boolean hasCameraFlash = false;
    boolean flashOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /////////////////////// UI ///////////////////////
        inputText = (EditText) findViewById(R.id.editPlainText);

        plainTextTV = (TextView)findViewById(R.id.plainTextTextView);
        morseCodeTV = (TextView)findViewById(R.id.morseCodeTextView);

        cancelButton = (Button)findViewById(R.id.cancelButton);
        /////////////////////// CAROUSEL ///////////////////////

        /////////////////////// FLASH CONTROL ///////////////////////

        ToggleButton toggleButton = findViewById(R.id.onOffFlashlight);
        hasCameraFlash = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread stop = Thread.currentThread();
                stop.interrupt();
            }
        });

        toggleButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = 44)
            @Override
            public void onClick(View v) {
                // TRANSLATION
                String textToEncode = inputText.getText().toString();
                String encodeText = TextToMorseAlgorithm.Encode(textToEncode);
                plainTextTV.setText(textToEncode);
                morseCodeTV.setText(encodeText);

                Toast.makeText(MainActivity.this, textToEncode, Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, encodeText, Toast.LENGTH_SHORT).show();

                // FLASH CONTROL
                if (hasCameraFlash) {
                    if (flashOn) {
                        flashOn = false;
                        try {
                            flashLightOff();
                        } catch (CameraAccessException e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        flashOn = true;
                        try {
                            LaunchMessage(encodeText);
                        } catch (CameraAccessException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                else {
                    Toast.makeText(MainActivity.this, "No flash available on your device.", Toast.LENGTH_LONG).show();
                }
            }
        });


        /////////////////////// CAROUSEL CONTROL ///////////////////////


    }

    @RequiresApi(api = 44)
    public void LaunchMessage(String encodedText) throws InterruptedException, CameraAccessException {
        Thread waiter = new Thread();

        String[] words = encodedText.split(" ");
        for (String word : words) {
            // Word = .../---/.../
            String[] letters = word.split("/");
            for (String letter : letters) {
                // Letter = ..-.
                for (char sign : letter.toCharArray())
                {
                    // Sign = '.' or '-'
                    if (sign == '.') {
                        flashLightOn();
                        System.out.println("1s");
                        Thread.sleep(1000);
                    }
                    else if (sign == '-') {
                        flashLightOn();
                        System.out.println("3s");
                        Thread.sleep(3000);
                    }
                    flashLightOff();
                    System.out.println("(1s)");
                    Thread.sleep(1000);
                }
                System.out.println("Letter completed (3 - 1 = 2s)");
                Thread.sleep(2000);
            }
            flashLightOff();
            System.out.println("--------------- (7 - 3 = 4s) ---------------");
            Thread.sleep(4000);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP + Build.VERSION_CODES.M)
    public void flashLightOn() throws CameraAccessException {
        CameraManager cameraManager = (CameraManager)getSystemService(Context.CAMERA_SERVICE);
        assert cameraManager != null;
        String cameraId = cameraManager.getCameraIdList()[0];
        cameraManager.setTorchMode(cameraId, true);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP + Build.VERSION_CODES.M)
    private void flashLightOff() throws CameraAccessException {
        CameraManager cameraManager = (CameraManager)getSystemService(Context.CAMERA_SERVICE);
        assert cameraManager != null;
        String cameraId = cameraManager.getCameraIdList()[0];
        cameraManager.setTorchMode(cameraId, false);
    }
}