package com.rmondev.android_multi_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    private static final long SPLASH_DELAY = 3000; // 3 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(() -> {

            Intent intent = new Intent(MainActivity.this, app_selection_activity.class);
            startActivity(intent);
            finish();
        }, SPLASH_DELAY);
    }
}