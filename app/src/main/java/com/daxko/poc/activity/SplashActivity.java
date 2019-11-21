package com.daxko.poc.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.daxko.poc.R;

public class SplashActivity extends AppCompatActivity {
    ImageView splashImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        splashImg = findViewById(R.id.splash_img);

        Glide.with(SplashActivity.this)
                .load(R.raw.tenor)
                .into(splashImg);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this,ChallengeListActivity.class));
                finish();

            }
        }, 3000);
    }
}
