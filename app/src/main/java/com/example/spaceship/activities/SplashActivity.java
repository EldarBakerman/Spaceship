package com.example.spaceship.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.spaceship.R;
import com.example.spaceship.classes.DatabaseOpenHelper;
import com.example.spaceship.classes.Weapon;

public class SplashActivity extends AppCompatActivity {
	
	public static DatabaseOpenHelper database;
	
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		database = new DatabaseOpenHelper(this);
		
		//TODO: if (isLoggedIn) -> Main Activity; Else -> Dialog
		
		for (Weapon weapon : Weapon.weapons) {
			database.insert(weapon);
			Log.d("SplashActivity#onCreate", weapon.toString());
		}
		
		new Handler().postDelayed(() -> {
			final Intent intent = new Intent(SplashActivity.this, MenuActivity.class);
			startActivity(intent);
			finish();
		}, 3000);
	}
}
