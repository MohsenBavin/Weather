package com.bavin.mohsen.weather;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_splash );
        new Handler(  ).postDelayed( new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent( SplashActivity.this, MainActivity.class );
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity( intent );
                finish();

            }
        } ,2000);
    }
}
