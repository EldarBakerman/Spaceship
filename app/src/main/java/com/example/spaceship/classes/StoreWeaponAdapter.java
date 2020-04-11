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


public class StoreWeaponAdapter extends ArrayAdapter<Weapon> {
	
	private final Activity context;
	private final List<Weapon> weapons;
	
	public StoreWeaponAdapter (@NonNull Activity context, List<Weapon> weapons) {
		super(context, R.layout.weapons, weapons);
		this.context = context;
		this.weapons = weapons;
	}
	
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
