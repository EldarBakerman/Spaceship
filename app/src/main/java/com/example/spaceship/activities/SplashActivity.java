package com.example.spaceship.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.spaceship.R;
import com.example.spaceship.classes.DatabaseOpenHelper;
import com.example.spaceship.classes.User;
import com.example.spaceship.classes.Weapon;

public class SplashActivity extends AppCompatActivity {
	
	public static DatabaseOpenHelper database;
	
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		database = new DatabaseOpenHelper(this);
		SharedPreferences sharedPreferences = getSharedPreferences("Splash", MODE_PRIVATE);
		
		if (database.getAllWeapons() == null || database.getAllWeapons().isEmpty())
			for (Weapon weapon : Weapon.weapons)
				database.insert(weapon);
		
		long userId;
		
		if ((userId = sharedPreferences.getLong("userId", -1)) >= 1) {
			Intent intent = new Intent(SplashActivity.this, MenuActivity.class);
			intent.putExtra("userId", userId);
			startActivity(intent);
			finish();
		} else
			showDialog();
	}
	
	private void showDialog () {
		User user = new User();
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Register");
		builder.setMessage("You are not logged in, please register by typing your name below");
		builder.setCancelable(false);
		
		final EditText nameInput = new EditText(this);
		
		nameInput.setInputType(InputType.TYPE_CLASS_TEXT |
		                       InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
		
		
		builder.setView(nameInput);
		builder.setPositiveButton("OK", (dialog, which) -> {
			final String inputString = nameInput.getText().toString();
			
			if (inputString.isEmpty() || !inputString.matches("^[a-zA-Z]+$")) {
				Toast.makeText(SplashActivity.this,
				               "Please type a name that consists only of letters.",
				               Toast.LENGTH_SHORT).show();
				builder.setView(null);
				dialog.cancel();
				showDialog();
				return;
			}
			
			long userId = database.insert(user).getId();
			getSharedPreferences("Splash", MODE_PRIVATE).edit().putLong("userId", userId).apply();
			Intent intent = new Intent(SplashActivity.this, MenuActivity.class);
			intent.putExtra("userId", userId);
			startActivity(intent);
			finish();
		});
		
		builder.create().show();
	}
	
}
