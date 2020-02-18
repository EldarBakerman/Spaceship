package com.example.spaceship.classes;

import android.graphics.RectF;
import android.graphics.drawable.Drawable;

public class PlayerSpaceship extends Spaceship {
	
	static final Weapon DEFAULT_WEAPON = new Weapon("Default", 5, 2, 1);
	private static final int DEFAULT_HP = 15;
	
	
	private Weapon weapon;
	
	public PlayerSpaceship () {
		super();
		resetWeapon();
		this.hp      = DEFAULT_HP;
		this.hpTotal = DEFAULT_HP;
	}
	
	public void resetWeapon () {
		this.weapon = new Weapon(DEFAULT_WEAPON.getName(),
		                         DEFAULT_WEAPON.getDamage(),
		                         DEFAULT_WEAPON.getFirerate(),
		                         DEFAULT_WEAPON.getSpeed());
	}
	
	public PlayerSpaceship (Drawable image) {
		super(image, DEFAULT_HP);
		resetWeapon();
	}
	
	public PlayerSpaceship (Drawable image, int hp) {
		super(image, hp);
		resetWeapon();
	}
	
	public PlayerSpaceship (Drawable image, int hp, int hpTotal, Weapon weapon) {
		super(image, hp, hpTotal);
		this.weapon = weapon;
	}
	
	public PlayerSpaceship (Drawable image, int hp, int hpTotal, RectF healthbar, Weapon weapon) {
		super(image, hp, hpTotal, healthbar);
		this.weapon = weapon;
	}
	
	public Weapon getWeapon () {
		return weapon;
	}
	
	public void setWeapon (Weapon weapon) {
		this.weapon = weapon;
	}
}
