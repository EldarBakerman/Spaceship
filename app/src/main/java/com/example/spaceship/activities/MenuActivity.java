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
		startActivity(new Intent(MenuActivity.this, GameActivity.class));
	}
	
	public void startStore (View view) {
		startActivity(new Intent(MenuActivity.this, StoreActivity.class));
	}
}
