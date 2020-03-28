package com.example.spaceship.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.spaceship.R;

public class MenuActivity extends AppCompatActivity {
	
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
	}
	
	public void startGame (View view) {
		Intent intent = new Intent(MenuActivity.this, GameActivity.class);
		intent.putExtra("userId", getIntent().getLongExtra("userId", -1));
		startActivity(intent);
	}
	
	public void startStore (View view) {
		Intent intent = new Intent(MenuActivity.this, StoreActivity.class);
		intent.putExtra("userId", getIntent().getLongExtra("userId", -1));
		startActivity(intent);
	}
	
	public void startUser (View view) {
		final Intent intent = new Intent(MenuActivity.this, UserActivity.class);
		intent.putExtra("userId", getIntent().getLongExtra("userId", -1));
		startActivity(intent);
	}
}
