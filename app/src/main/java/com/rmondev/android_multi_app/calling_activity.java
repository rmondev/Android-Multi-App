package com.rmondev.android_multi_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class calling_activity extends AppCompatActivity {

    private static final int REQUESTCODE = 1002;

    EditText etPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calling);

        Button btnBeginCall = findViewById(R.id.btnBeginCall);
        etPhone = findViewById(R.id.etPhone);

        btnBeginCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phnNumber = etPhone.getText().toString().trim();

                // Validate phone number format here
                // Must be 10digit phone number
                if(isValidPhoneNumber(phnNumber)){
                    if (isPhonePermissionGranted()) {
                        makeCall(phnNumber);
                    } else {
                        requestPhonePermission();
                    }
                } else {
                    Toast.makeText(calling_activity.this, "Invalid Phone Number Format", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private boolean isPhonePermissionGranted() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPhonePermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, REQUESTCODE);
    }

    private void makeCall(String phoneNumber) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(callIntent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUESTCODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, make the call
                String phnNumber = etPhone.getText().toString().trim();
                makeCall(phnNumber);
            } else {
                // Permission denied, inform the user
                Toast.makeText(this, "Phone call permission denied.", Toast.LENGTH_SHORT).show();

                // Create a reference to the activity
                final calling_activity activity = this;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity, "Change Permissions in Android App Settings.", Toast.LENGTH_LONG).show();
                    }
                }, 3000);
            }
        }
    }


    private boolean isValidPhoneNumber(String phoneNumber) {
        // Check if the phone number contains only digits 0-9 and has 10 digits
        return phoneNumber.matches("[0-9]{10}");
    }
}
