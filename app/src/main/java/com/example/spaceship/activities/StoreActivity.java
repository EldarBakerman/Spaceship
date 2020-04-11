package com.example.spaceship.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.spaceship.R;
import com.example.spaceship.classes.PlayerSpaceship;
import com.example.spaceship.classes.StoreWeaponAdapter;
import com.example.spaceship.classes.User;
import com.example.spaceship.classes.Weapon;

import java.util.Locale;

/**
 * The {@link android.app.Activity activity} of the store where you can purchase and equip various
 * {@link com.example.spaceship.classes.Weapon weapons}.
 *
 * @see com.example.spaceship.classes.Weapon
 * @see com.example.spaceship.classes.StoreWeaponAdapter
 */

public class StoreActivity extends AppCompatActivity {
	
	/**
	 * The {@link android.widget.ListView list} of all the
	 * {@link com.example.spaceship.classes.Weapon
	 * weapon} the store features.
	 *
	 * @see com.example.spaceship.classes.Weapon
	 * @see com.example.spaceship.classes.Weapon#weapons
	 * @see android.widget.ListView
	 * @see com.example.spaceship.classes.StoreWeaponAdapter
	 */
	
	ListView weapons;
	
	/**
	 * A {@link android.widget.TextView textView} of current points of the {@link
	 * com.example.spaceship.classes.User user}.
	 *
	 * @see com.example.spaceship.classes.User
	 * @see android.widget.TextView
	 */
	
	TextView pointsText;
	
	/**
	 * The initial method that loads the list and is responsible for the entire logic of the store.
	 *
	 * @param savedInstanceState a {@link android.os.Bundle bundle} that the system uses.
	 *
	 * @see androidx.appcompat.app.AppCompatActivity#onCreate(android.os.Bundle)
	 */
	
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_store);
		
		StoreWeaponAdapter adapter = new StoreWeaponAdapter(this, Weapon.weapons);
		weapons = findViewById(R.id.weapons_list);
		weapons.setAdapter(adapter);
		
		pointsText = findViewById(R.id.points);
		
		User user = SplashActivity.database.getUser(getSharedPreferences("Splash",
		                                                                 MODE_PRIVATE).getLong(
				"userId",
				-1));
		
		pointsText.setText(String.format(Locale.ENGLISH,
		                                 "%s%s",
		                                 getResources().getString(R.string.points),
		                                 user.getPoints() + ""));
		
		weapons.setOnItemClickListener((parent, view, position, id) -> {
			final Weapon weapon = Weapon.weapons.get(position);
			if (weapon.isOwned() && weapon.isEquipped())
				return;
			
			Button buyBtn = view.findViewById(R.id.buy);
			if (!weapon.isOwned())
				if (user.getPoints() >= weapon.getPrice()) {
					weapon.setOwned(true);
					user.setPoints(user.getPoints() - weapon.getPrice());
					pointsText.setText(String.format(Locale.ENGLISH,
					                                 "%s%d",
					                                 getResources().getString(R.string.points),
					                                 user.getPoints()));
					SplashActivity.database.update(user);
					SplashActivity.database.update(weapon);
				} else
					Toast.makeText(this,
					               "You do not have enough points to purchase this weapon",
					               Toast.LENGTH_LONG).show();
			else if (!weapon.isEquipped()) {
				final Weapon equippedWeapon = PlayerSpaceship.getEquippedWeapon();
				int pos = getPos();
				equippedWeapon.setEquipped(false);
				Weapon.weapons.get(pos).setEquipped(false);
				
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
	
	/**
	 * Returns the position of the currently equipped {@link com.example.spaceship.classes.Weapon
	 * weapon} in the {@link com.example.spaceship.classes.Weapon#weapons} list.
	 *
	 * @return the position of the currently equipped {@link com.example.spaceship.classes.Weapon
	 * 		weapon} in the {@link com.example.spaceship.classes.Weapon#weapons} list.
	 *
	 * @see com.example.spaceship.classes.PlayerSpaceship#getEquippedWeapon()
	 * @see com.example.spaceship.classes.Weapon
	 * @see com.example.spaceship.classes.Weapon#weapons
	 */
	
	private int getPos () {
		Weapon weapon = PlayerSpaceship.getEquippedWeapon();
		
		if (weapon == null)
			for (Weapon w : Weapon.weapons)
				if (w.isEquipped())
					PlayerSpaceship.setEquippedWeapon(w);
		
		for (int i = 0; i < Weapon.weapons.size(); i++)
			if (weapon != null && weapon.getName().equals(Weapon.weapons.get(i).getName()))
				return i;
		return -1;
	}
	
	/**
	 * Returns the {@link android.view.View view} in the
	 * {@link com.example.spaceship.activities.StoreActivity#weapons}
	 * list by its position.
	 *
	 * @param pos      the position of the view.
	 * @param listView the list from which the view is retrieved.
	 *
	 * @return the {@link android.view.View view} in the
	 * {@link com.example.spaceship.activities.StoreActivity#weapons}
	 * 		list by its position.
	 *
	 * @see com.example.spaceship.activities.StoreActivity#weapons
	 * @see com.example.spaceship.classes.StoreWeaponAdapter
	 * @see android.view.View
	 * @see android.widget.ListView
	 */
	
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
