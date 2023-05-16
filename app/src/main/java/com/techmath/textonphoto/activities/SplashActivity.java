package com.techmath.textonphoto.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.techmath.textonphoto.R;


public class SplashActivity extends AppCompatActivity {

    ImageView icon, mBg;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_splash);
        icon = findViewById(R.id.icon);
        Glide.with(this)
                .load(R.drawable.sp_logo_off)
                .into(icon);
        new Handler().postDelayed(() -> {
            goToHome();
        }, 3000);
    }


    public void goToHome() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }
}
