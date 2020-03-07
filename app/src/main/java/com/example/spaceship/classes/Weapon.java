package com.example.spaceship.classes;

import android.graphics.drawable.Drawable;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Weapon {
	
	static final Weapon WEAPON_0;
	private static final Weapon WEAPON_1;
	private static final Weapon WEAPON_2;
	private static final Weapon WEAPON_3;
	public static List<Weapon> weapons = new ArrayList<>();
	
	static {
		WEAPON_0 = new Weapon("Default", 5, 2, 0);
		WEAPON_0.setOwned(true);
		WEAPON_0.setEquipped(true);
		PlayerSpaceship.setEquippedWeapon(WEAPON_0);
		WEAPON_1 = new Weapon("Death Ray", 10, 3, 25);
		WEAPON_2 = new Weapon("Finisher", 20, 1, 50);
		WEAPON_3 = new Weapon("Purified Light", 30, 2, 100);
	}
	
	private long id;
	private String name;
	private int damage;
	private int speed;
	private Drawable image;
	private boolean equipped;
	private int price;
	private boolean owned;
	
	public Weapon (long id, String name, int damage, int speed, int price, boolean equipped,
	               boolean owned) {
		this.id       = id;
		this.name     = name;
		this.damage   = damage;
		this.speed    = speed;
		this.price    = price;
		this.equipped = equipped;
		this.owned    = owned;
		weapons.add(this);
	}
	
	public Weapon (String name, int damage, int speed, int price) {
		this.id     = -1;
		this.name   = name;
		this.damage = damage;
		this.speed  = speed;
		this.price  = price;
		weapons.add(this);
	}
	
	public Weapon (String name, int damage, int speed, Drawable image, int price) {
		this.id     = -1;
		this.name   = name;
		this.damage = damage;
		this.speed  = speed;
		this.image  = image;
		this.price  = price;
		weapons.add(this);
	}
	
	public Drawable getImage () {
		return image;
	}
	
	public void setImage (Drawable image) {
		this.image = image;
	}
	
	public String getDamageString () {
		return this.damage + " HP";
	}
	
	public String getSpeedString () {
		return this.speed + " au/h";
	}
	
	@Override
	public boolean equals (@Nullable Object obj) {
		return obj instanceof Weapon &&
		       ((Weapon) obj).getId() == this.id &&
		       ((Weapon) obj).getName().equals(this.name) &&
		       ((Weapon) obj).getPrice() == this.price &&
		       ((Weapon) obj).getDamage() == this.damage &&
		       ((Weapon) obj).getSpeed() == this.speed &&
		       ((Weapon) obj).isOwned() == this.owned &&
		       ((Weapon) obj).isEquipped() == this.equipped;
	}
	
	public long getId () {
		return id;
	}
	
	public String getName () {
		return name;
	}
	
	public int getPrice () {
		return price;
	}
	
	public void setPrice (int price) {
		this.price = price;
	}
	
	public int getDamage () {
		return damage;
	}
	
	public void setDamage (int damage) {
		this.damage = damage;
	}
	
	public int getSpeed () {
		return speed;
	}
	
	public void setSpeed (int speed) {
		this.speed = speed;
	}
	
	public boolean isOwned () {
		return owned;
	}
	
	public boolean isEquipped () {
		return equipped;
	}
	
	public void setEquipped (boolean equipped) {
		if (!owned)
			throw new AssertionError("can't equip if not owned");
		this.equipped = equipped;
	}
	
	public void setOwned (boolean owned) {
		this.owned    = owned;
		this.equipped = owned && this.equipped;
	}
	
	public void setId (long id) {
		this.id = id;
	}
	
	public void setName (String name) {
		this.name = name;
	}
}
