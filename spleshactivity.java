package com.example.user;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class spleshactivity extends AppCompatActivity {
    Animation middleAnimation;
    ImageView idIVLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_spleshactivity);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_spleshactivity);

        idIVLogo = findViewById(R.id.idIVLogo);
        middleAnimation = AnimationUtils.loadAnimation(this, R.anim.middle);
        idIVLogo.setAnimation(middleAnimation);

        SharedPreferences sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE);

        new Handler().postDelayed(() -> {

            if (sharedPreferences.contains("Bool")) {
                Intent intent = new Intent(spleshactivity.this, viewdata.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(spleshactivity.this, register.class);
                startActivity(intent);
            }

            finish();

        }, 1000);

    }
}
 //if (sharedPreferences.getBoolean("Bool", true)) {
//                Intent intent = new Intent(spleshactivity.this, viewdata.class);
//                Log.e("Splash", "gotoNextActivity: "+ "inside if" );
//                startActivity(intent);
//                finish();
//            }
//            else {
//                Intent intent = new Intent(spleshactivity.this, register.class);
//                Log.e("Splash", "gotoNextActivity: "+ "inside else" );
//                startActivity(intent);
//                finish();
//            }