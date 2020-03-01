package com.example.spaceship.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.spaceship.R;
import com.example.spaceship.classes.PlayerSpaceship;
import com.example.spaceship.classes.StoreWeaponAdapter;
import com.example.spaceship.classes.Weapon;

public class StoreActivity extends AppCompatActivity {
	
	ListView weapons;
	
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_store);
		
		StoreWeaponAdapter adapter = new StoreWeaponAdapter(this, Weapon.weapons);
		weapons = findViewById(R.id.weapons_list);
		weapons.setAdapter(adapter);
		
		weapons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
				final Weapon weapon = Weapon.weapons.get(position);
				if (weapon.isOwned())
					return;
				// TODO: Make purchase using points
				PlayerSpaceship.setEquippedWeapon(weapon);
				view.findViewById(R.id.buy);
			}
		});
	}
}
