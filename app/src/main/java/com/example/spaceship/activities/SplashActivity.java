package com.example.spaceship.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.spaceship.R;

public class SplashActivity extends AppCompatActivity {
	
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		new Handler().postDelayed(() -> {
			startActivity(new Intent(SplashActivity.this, MenuActivity.class));
			finish();
		}, 3000);
	}
}
