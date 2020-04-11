package com.example.spaceship.classes;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.spaceship.R;

import java.util.List;
import java.util.Locale;

/**
 * A custom adapter extending the {@link android.widget.ArrayAdapter<Weapon> ArrayAdapter<Weapon>}
 * class.
 * Used by {@link com.example.spaceship.activities.StoreActivity StoreActivity} to show the list of
 * all the weapons.
 *
 * @see android.widget.ArrayAdapter
 * @see android.widget.ArrayAdapter<com.example.spaceship.classes.Weapon>
 * @see android.widget.ListView
 * @see com.example.spaceship.activities.StoreActivity
 * @see com.example.spaceship.classes.Weapon
 * @see com.example.spaceship.classes.Weapon#weapons
 */

public class StoreWeaponAdapter extends ArrayAdapter<Weapon> {
	
	/**
	 * The {@link android.app.Activity activity} of the
	 * {@link android.widget.ArrayAdapter<com.example.spaceship.classes.Weapon>
	 * adapter}.
	 *
	 * @see android.app.Activity
	 * @see android.content.Context
	 */
	
	private final Activity context;
	
	/**
	 * A {@link java.util.List<com.example.spaceship.classes.Weapon> list} of all the weapons.
	 *
	 * @see com.example.spaceship.classes.Weapon
	 * @see java.util.List
	 * @see java.util.List<com.example.spaceship.classes.Weapon>
	 */
	
	private final List<Weapon> weapons;
	
	/**
	 * The constructor of the Adapter.
	 *
	 * @param context the {@link android.app.Activity activity} of the adapter.
	 * @param weapons the {@link java.util.List<com.example.spaceship.classes.Weapon> list of
	 *                weapons} of the adapter.
	 *
	 * @see android.app.Activity
	 * @see java.util.List<com.example.spaceship.classes.Weapon>
	 * @see android.app.Activity#onCreate(android.os.Bundle, android.os.PersistableBundle)
	 */
	
	public StoreWeaponAdapter (@NonNull Activity context, List<Weapon> weapons) {
		super(context, R.layout.weapons, weapons);
		this.context = context;
		this.weapons = weapons;
	}
	
	/**
	 * Sets the buttons in their proper form in accordance to their {@link Weapon#isOwned()} and
	 * {@link Weapon#isEquipped()} fields
	 *
	 * @param resources the {@link android.content.res.Resources resources}
	 * @param weapon    the {@link com.example.spaceship.classes.Weapon weapon}
	 * @param button    the {@link android.widget.Button button}
	 *
	 * @see android.content.res.Resources
	 * @see android.widget.Button
	 * @see com.example.spaceship.classes.Weapon
	 * @see Weapon#isEquipped()
	 * @see Weapon#isOwned()
	 */
	
	public static void setButtonFormat (Resources resources, Weapon weapon, Button button) {
		if (weapon.isOwned())
			if (weapon.isEquipped()) {
				button.setText(resources.getString(R.string.equipped));
				button.setBackgroundColor(Color.CYAN);
			} else {
				button.setText(resources.getString(R.string.equip));
				button.setBackgroundColor(Color.YELLOW);
			}
		else {
			String buy = String.format(Locale.ENGLISH,
			                           "%s%d",
			                           resources.getString(R.string.buy),
			                           weapon.getPrice());
			button.setText(buy);
			button.setBackgroundColor(Color.GREEN);
		}
	}
	
	@NonNull
	@Override
	public View getView (int position, @Nullable View convertView, @NonNull ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			convertView = inflater.inflate(R.layout.weapons, parent, false);
		}
		
		ImageView image = convertView.findViewById(R.id.image);
		TextView nameText = convertView.findViewById(R.id.name);
		TextView speedText = convertView.findViewById(R.id.speed);
		TextView damageText = convertView.findViewById(R.id.damage);
		Button buyBtn = convertView.findViewById(R.id.buy);
		final Resources resources = convertView.getResources();
		
		final Weapon weapon = weapons.get(position);
		
		image.setImageDrawable(weapon.getImage());
		nameText.setText(weapon.getName());
		speedText.setText(String.format(Locale.ENGLISH,
		                                "%s%s",
		                                resources.getString(R.string.speed),
		                                weapon.getSpeedString()));
		damageText.setText(String.format(Locale.ENGLISH,
		                                 "%s%s",
		                                 resources.getString(R.string.damage),
		                                 weapon.getDamageString()));
		
		setButtonFormat(convertView.getResources(), weapon, buyBtn);
		
		return convertView;
	}
}
