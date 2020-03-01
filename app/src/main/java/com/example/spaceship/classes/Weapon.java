package com.example.spaceship.classes;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;

public class Weapon {
	
	public static final Weapon WEAPON_0;
	public static final Weapon WEAPON_1;
	public static final Weapon WEAPON_2;
	public static final Weapon WEAPON_3;
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
	
	private String name;
	private int damage;
	private int speed;
	private Drawable image;
	private boolean equipped;
	private int price;
	private boolean owned;
	
	public Weapon (String name, int damage, int speed, int price) {
		this.name   = name;
		this.damage = damage;
		this.speed  = speed;
		this.price  = price;
		weapons.add(this);
	}
	
	public Weapon (String name, int damage, int speed, Drawable image, int price) {
		this.name   = name;
		this.damage = damage;
		this.speed  = speed;
		this.image  = image;
		this.price  = price;
		weapons.add(this);
	}
	
	public static Weapon copyWeapon (Weapon weapon) {
		Weapon res = new Weapon(weapon.name,
		                        weapon.damage,
		                        weapon.speed,
		                        weapon.image,
		                        weapon.price);
		weapons.remove(weapons.size() - 1);
		return res;
	}
	
	public String getName () {
		return name;
	}
	
	public void setName (String name) {
		this.name = name;
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
	
	public Drawable getImage () {
		return image;
	}
	
	public void setImage (Drawable image) {
		this.image = image;
	}
	
	public boolean isEquipped () {
		return equipped;
	}
	
	public void setEquipped (boolean equipped) {
		assert owned : "can't equip if not owned";
		this.equipped = equipped;
	}
	
	public String getDamageString () {
		return this.damage + " HP";
	}
	
	public String getSpeedString () {
		return this.speed + " au/h";
	}
	
	public int getPrice () {
		return price;
	}
	
	public void setPrice (int price) {
		this.price = price;
	}
	
	public boolean isOwned () {
		return owned;
	}
	
	public void setOwned (boolean owned) {
		this.owned = owned;
	}
}
