package com.rmondev.android_multi_app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class camera_activity extends AppCompatActivity {

    private final int REQUESTCODE = 1001;
    ImageView ivSelfie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        Button btnLaunchCamera = findViewById(R.id.btnLaunchCamera);
        ivSelfie = findViewById(R.id.ivSelfie);

        if(isCameraAvailable()){
            getCameraPermission();
        }else {
            Toast.makeText(this, "Camera Not Available", Toast.LENGTH_LONG).show();
        }


        btnLaunchCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, REQUESTCODE);
            }
        });
    }


    private boolean isCameraAvailable(){
        return this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }

    private void getCameraPermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED){

            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, REQUESTCODE);

        } else {

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == REQUESTCODE) {
            Bitmap selfie = (Bitmap) data.getExtras().get("data");
            ivSelfie.setImageBitmap(selfie);

        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}