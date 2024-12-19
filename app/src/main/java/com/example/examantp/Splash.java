package com.example.examantp;

import android.content.Intent;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Splash extends AppCompatActivity {

    private static final int SPLASH_DISPLAY_LENGTH = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Lancer l'animation
        ImageView splashImage = findViewById(R.id.splash_image);
        AnimatedVectorDrawable animatedDrawable = (AnimatedVectorDrawable) splashImage.getDrawable();
        animatedDrawable.start();

        // Attendre un moment et passer à MainActivity
        new Handler().postDelayed(() -> {
            Intent mainIntent = new Intent(Splash.this, MainActivity.class);
            startActivity(mainIntent);
            finish();
        }, SPLASH_DISPLAY_LENGTH);
    }}