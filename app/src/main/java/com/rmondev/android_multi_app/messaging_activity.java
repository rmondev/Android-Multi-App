package com.rmondev.android_multi_app;

import static android.content.Intent.ACTION_VIEW;

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

public class messaging_activity extends AppCompatActivity {

    private static int REQUESTCODE = 1003;
    EditText etMessage;
    EditText etMsgPhone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);

        etMsgPhone = findViewById(R.id.etMsgPhone);
        etMessage = findViewById(R.id.etMessage);
        Button btnSendMessage = findViewById(R.id.btnSendMessage);

        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phnNumber = etMsgPhone.getText().toString().trim();
                String smsMsg = etMessage.getText().toString();

                if(isValidPhoneNumber(phnNumber)){
                    if(isMessageEmpty(smsMsg))
                    {
                        if (isSMSPermissionGranted()) {
                            sndSMS(phnNumber);
                        } else {
                            requestSMSPermission();
                        }
                    } else {
                        Toast.makeText(messaging_activity.this, "Message cannot be empty", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(messaging_activity.this, "Invalid Phone Number Format", Toast.LENGTH_LONG).show();
                }


            }
        });
    }

    private boolean isMessageEmpty(String smsMsg) {
        return smsMsg.length() > 0;
    }

    private boolean isSMSPermissionGranted() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                == PackageManager.PERMISSION_GRANTED;
    }

    private void requestSMSPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, REQUESTCODE);
    }

    private void sndSMS(String phoneNumber) {
        Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
        smsIntent.setData(Uri.parse("smsto:" + phoneNumber));
        String msg = etMessage.getText().toString();
        smsIntent.putExtra("sms_body", msg);
        startActivity(smsIntent);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUESTCODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, send the SMS
                String phnNumber = etMsgPhone.getText().toString().trim();
                sndSMS(phnNumber);
            } else {
                // Permission denied, inform the user
                Toast.makeText(this, "Messaging permission denied.", Toast.LENGTH_SHORT).show();

                // Display the second toast message after a delay
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(messaging_activity.this, "Change Permissions in Android App Settings.", Toast.LENGTH_LONG).show();
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