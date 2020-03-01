package com.example.spaceship.classes;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.spaceship.R;

import java.util.List;


public class StoreWeaponAdapter extends ArrayAdapter<Weapon> {
	
	private final Activity context;
	private final List<Weapon> weapons;
	
	public StoreWeaponAdapter (@NonNull Activity context, List<Weapon> weapons) {
		super(context, R.layout.weapons, weapons);
		this.context = context;
		this.weapons = weapons;
	}
	
	@NonNull
	@Override
	public View getView (int position, @Nullable View convertView, @NonNull ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			convertView = inflater.inflate(R.layout.weapons, parent, true);
		}
		
		ImageView image = convertView.findViewById(R.id.image);
		TextView nameText = convertView.findViewById(R.id.name);
		TextView speedText = convertView.findViewById(R.id.speed);
		TextView damageText = convertView.findViewById(R.id.damage);
		
		final Weapon weapon = weapons.get(position);
		
		image.setImageDrawable(weapon.getImage());
		nameText.setText(weapon.getName());
		speedText.setText(weapon.getSpeedString());
		damageText.setText(weapon.getDamageString());
		
		return convertView;
	}
}
