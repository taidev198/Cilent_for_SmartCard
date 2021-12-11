package com.example.client.ui.splash;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;


import com.example.client.R;
import com.example.client.data.model.Staff;
import com.example.client.ui.login.LoginActivity;
import com.example.client.ui.main.MainActivity;
import com.google.gson.Gson;

public class SplashActivity extends AppCompatActivity {

    private Staff mStaff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SharedPreferences preferences = getSharedPreferences("SHARE", MODE_PRIVATE);
        mStaff = new Gson().fromJson(preferences.getString("user", ""), Staff.class);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (null == mStaff) {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                } else {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    //To pass:
                    intent.putExtra("user", mStaff);
                    startActivity(intent);
                    finish();
                }

            }
        }, 2000);
    }
}