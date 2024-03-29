package com.pappydevelopers.groupsforwhatsapp.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pappydevelopers.groupsforwhatsapp.R;

public class SplashScreen extends AppCompatActivity {

    static int SPLASH_TIME_OUT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent i = new Intent(SplashScreen.this, WhatsappHomeScreen.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
