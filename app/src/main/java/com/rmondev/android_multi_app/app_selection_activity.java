package com.rmondev.android_multi_app;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class app_selection_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_selection);

        Button btnCameraApp = findViewById(R.id.btnCameraApp);
        Button btnMessagingApp = findViewById(R.id.btnMessagingApp);
        Button btnCallingApp = findViewById(R.id.btnCallingApp);
        Button btnExit = findViewById(R.id.btnExit);

        btnCameraApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(app_selection_activity.this, camera_activity.class);
                startActivity(intent);


            }
        });

        btnCallingApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(app_selection_activity.this, calling_activity.class);
                startActivity(intent);


            }
        });

        btnMessagingApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(app_selection_activity.this, messaging_activity.class);
                startActivity(intent);


            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




    }
}