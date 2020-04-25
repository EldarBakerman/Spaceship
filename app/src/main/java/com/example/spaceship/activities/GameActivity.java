package com.example.spaceship.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.spaceship.classes.MusicService;
import com.example.spaceship.views.GameView;

public class GameActivity extends AppCompatActivity {
	
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new GameView(this));
	}
	
	@Override
	protected void onPause () {
		super.onPause();
		Intent intent = new Intent(this, MusicService.class);
		stopService(intent);
	}
	
	@Override
	protected void onResume () {
		super.onResume();
		Intent intent = new Intent(this, MusicService.class);
		startService(intent);
	}
}
