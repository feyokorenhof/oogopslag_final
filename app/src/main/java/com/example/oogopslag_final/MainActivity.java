package com.example.oogopslag_final;

import android.content.Intent;
import android.media.Image;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {


    private static int SPLASH_TIME_OUT = 4000;
    private ProgressBar pgrbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView SPLASH_IMG = (ImageView) findViewById(R.id.SPLASH_IMG);
        pgrbar = findViewById(R.id.load_bar);
        pgrbar.setVisibility(View.VISIBLE);
        //int imageResource = getResources().getIdentifier("@drawable/oogopslag.png", null, this.getPackageName());
        SPLASH_IMG.setImageResource(R.drawable.oogopslag);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(MainActivity.this, Login_SignUp.class);
                startActivity(homeIntent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }


}
