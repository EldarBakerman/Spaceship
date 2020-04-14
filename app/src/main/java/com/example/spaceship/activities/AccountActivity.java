package com.example.spaceship.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.spaceship.R;
import com.example.spaceship.classes.User;

import java.util.Locale;

public class AccountActivity extends AppCompatActivity {
	
	EditText username;
	TextView points;
	TextView highscore;
	Button update;
	Button delete;
	
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user);
		
		long userId = getIntent().getLongExtra("userId", -1);
		User user = SplashActivity.database.getUser(userId);
		
		username  = findViewById(R.id.user_edit_text);
		points    = findViewById(R.id.points_text_view);
		highscore = findViewById(R.id.highscore_text_view);
		update    = findViewById(R.id.update_btn);
		delete    = findViewById(R.id.delete_btn);
		
		username.setText(user.getName());
		points.setText(String.format(Locale.ENGLISH,
		                             "%s%d",
		                             getResources().getString(R.string.points),
		                             user.getPoints()));
		highscore.setText(String.format(Locale.ENGLISH,
		                                "%s%d",
		                                getResources().getString(R.string.highscore),
		                                user.getHighscore()));
		
		update.setOnClickListener(v -> {
			final String name = username.getText().toString();
			if (name.matches("^[a-zA-Z]+$") && !name.equals(user.getName())) {
				user.setName(name);
				try {
					SplashActivity.database.update(user);
					Toast.makeText(this, "Account updated successfully!", Toast.LENGTH_SHORT)
					     .show();
				} catch (Exception e) {
					e.printStackTrace();
					Toast.makeText(this,
					               "An error has occurred while updating the account",
					               Toast.LENGTH_SHORT).show();
				}
			} else
				Toast.makeText(this, "Invalid name", Toast.LENGTH_SHORT).show();
		});
		
		delete.setOnClickListener(v -> {
			SplashActivity.database.deleteUser(user);
			user.setId(-1);
			user.setName("");
			user.setHighscore(0);
			user.setPoints(0);
			AccountActivity.this.getIntent().removeExtra("userId");
			AccountActivity.this.finish();
			System.exit(2); // 2 - User Invalid
		});
	}
}
