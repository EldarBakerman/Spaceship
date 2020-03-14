package com.example.spaceship.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.spaceship.R;
import com.example.spaceship.classes.DatabaseOpenHelper;
import com.example.spaceship.classes.User;
import com.example.spaceship.classes.Weapon;

import java.util.Objects;

public class SplashActivity extends AppCompatActivity {
	
	private static final int CAMERA_REQUEST = 1888;
	private static final int CAMERA_PERMISSION_CODE = 100;
	public static DatabaseOpenHelper database;
	Bitmap photo = null;
	
	String name = "";
	boolean checked = false;
	
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		database = new DatabaseOpenHelper(this);
		SharedPreferences sharedPreferences = getSharedPreferences("Splash", MODE_PRIVATE);
		
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
		builder.setMessage("You are not logged in, please register by typing your name below" +
		                   ".\n" +
		                   "You can also take a photo and set it as your profile picture, " +
		                   "if you'd like.");
		builder.setCancelable(false);
		
		final EditText nameInput = new EditText(this);
		CheckBox photoInput = new CheckBox(this);
		LinearLayout layout = new LinearLayout(this);
		
		nameInput.setInputType(InputType.TYPE_CLASS_TEXT |
		                       InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
		photoInput.setInputType(InputType.TYPE_CLASS_TEXT |
		                        InputType.TYPE_TEXT_VARIATION_SHORT_MESSAGE);
		photoInput.setText(R.string.take_photo);
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
		                                                    ViewGroup.LayoutParams.WRAP_CONTENT));
		layout.addView(nameInput);
		layout.addView(photoInput);
		
		
		builder.setView(layout);
		builder.setPositiveButton("OK", (dialog, which) -> {
			final String inputString = nameInput.getText().toString();
			
			if (!name.equals(""))
				nameInput.setText(name);
			
			if (checked)
				photoInput.setChecked(true);
			
			if (inputString.isEmpty() || !inputString.matches("^[a-zA-Z]+$")) {
				Toast.makeText(SplashActivity.this,
				               "Please type a name that consists only of letters.",
				               Toast.LENGTH_SHORT).show();
				builder.setView(null);
				dialog.cancel();
				showDialog();
				return;
			} else {
				user.setName(inputString);
				name = inputString;
			}
			
			if (photoInput.isChecked()) {
				checked = true;
				if (takePhoto())
					while (true)
						if (photo != null) {
							user.setImage(photo);
							break;
						}
			} else
				checked = false;
			
			long id = database.insert(user).getId();
			getSharedPreferences("Splash", MODE_PRIVATE).edit().putLong("userId", id).apply();
			Intent intent = new Intent(SplashActivity.this, MenuActivity.class);
			intent.putExtra("userId", id);
			startActivity(intent);
			finish();
		});
		
		builder.create().show();
	}
	
	private boolean takePhoto () {
		if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
			requestPermissions(new String[] {Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
		if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
			startActivityForResult(new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE),
			                       CAMERA_REQUEST);
			showDialog();
			return true;
		}
		return false;
	}
	
	@Override
	protected void onActivityResult (int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Log.d("OnActivityResult", "Result");
		for (Fragment fragment : getSupportFragmentManager().getFragments()) {
			fragment.onActivityResult(requestCode, resultCode, data);
		}
		if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK)
			photo = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
	}
	
	@Override
	public void onRequestPermissionsResult (int requestCode,
	                                        @NonNull String[] permissions,
	                                        @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == CAMERA_PERMISSION_CODE) {
			if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				startActivityForResult(new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE),
				                       CAMERA_REQUEST);
			} else
				Toast.makeText(this, "No permission to use Camera", Toast.LENGTH_LONG).show();
		}
	}
}
