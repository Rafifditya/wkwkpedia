package com.rijal.wkwkpedia.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.rijal.wkwkpedia.R;
import com.rijal.wkwkpedia.SessionManager;

public class SplashScreen extends AppCompatActivity {

    SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        session = new SessionManager(getApplicationContext());

        Thread myThread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(1500);

                    if (session.checkLogin()){
                        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                        finish();
                    }


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
        myThread.start();
    }
}
