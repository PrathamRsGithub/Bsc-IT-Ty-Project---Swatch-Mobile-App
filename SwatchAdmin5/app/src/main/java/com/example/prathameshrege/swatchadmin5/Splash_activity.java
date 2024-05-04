package com.example.prathameshrege.swatchadmin5;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Splash_activity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 750;   //Timer Of Splash Screen
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_activity);


        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                if(SavedSharedPrefrences.getUserName(Splash_activity.this).length() == 0)
                {
                Intent i = new Intent(Splash_activity.this, Admin_login.class);
                startActivity(i);

                // close this activity
                finish();
                }
                else
                {
                    Intent i = new Intent(Splash_activity.this, HomePageActivity.class);
                    startActivity(i);

                    // close this activity
                    finish();
                }
            }
        }, SPLASH_TIME_OUT);

    }
}
