package com.example.spaceship.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
		
		weapons.setOnItemClickListener((parent, view, position, id) -> {
			final Weapon weapon = Weapon.weapons.get(position);
			if (weapon.isOwned() && weapon.isEquipped()) {
				return;
			}
			
			Button buyBtn = view.findViewById(R.id.buy);
			if (!weapon.isOwned()) {
				// TODO: Make purchase using points
				weapon.setOwned(true);
			} else if (!weapon.isEquipped()) {
				final Weapon equippedWeapon = PlayerSpaceship.getEquippedWeapon();
				equippedWeapon.setEquipped(false);
				
				int pos = Weapon.weapons.indexOf(equippedWeapon);
				weapons.setItemChecked(pos, true);
				View prevWeapon = getViewByPosition(pos, weapons);
				
				StoreWeaponAdapter.setButtonFormat(prevWeapon.getResources(),
				                                   equippedWeapon,
				                                   prevWeapon.findViewById(R.id.buy));
				
				weapon.setEquipped(true);
				PlayerSpaceship.setEquippedWeapon(weapon);
			}
			StoreWeaponAdapter.setButtonFormat(view.getResources(), weapon, buyBtn);
		});
	}
	
	private View getViewByPosition (int pos, ListView listView) {
		final int firstListItemPosition = listView.getFirstVisiblePosition();
		final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;
		
		if (pos < firstListItemPosition || pos > lastListItemPosition) {
			return listView.getAdapter().getView(pos, null, listView);
		} else {
			final int childIndex = pos - firstListItemPosition;
			return listView.getChildAt(childIndex);
		}
	}
}
