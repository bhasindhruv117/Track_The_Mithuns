package com.akshit.sih;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import static java.lang.Thread.sleep;

public class Splash extends AppCompatActivity {

    public static int splash_time_out = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        // Thread mythread=new Thread();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(Splash.this,MainActivity.class);
                startActivity(homeIntent);
                finish();
            }
        }, splash_time_out);

    }
}
