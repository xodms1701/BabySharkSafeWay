package com.gonigoni.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.gonigoni.babysharksafeway.R;
import com.gonigoni.ui.main.MainActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        moveToBaseActivity();
    }

    private final void moveToBaseActivity() {
        final int splash_time = 2000;
        new Handler().postDelayed(() -> {
            startActivity(new Intent(getBaseContext(), MainActivity.class));
            finish();
        }, splash_time);
    }
}
