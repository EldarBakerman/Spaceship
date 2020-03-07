package com.example.spaceship.classes;

import android.graphics.RectF;
import android.graphics.drawable.Drawable;

public class PlayerSpaceship extends Spaceship {
	
	static final int DEFAULT_HP = 15;
	private static Weapon equippedWeapon;
	private Weapon weapon;
	
	public PlayerSpaceship () {
		super();
		this.hp      = DEFAULT_HP;
		this.hpTotal = DEFAULT_HP;
		this.weapon  = Weapon.WEAPON_0;
		this.weapon.setEquipped(true);
		PlayerSpaceship.equippedWeapon = this.weapon;
	}
	
	public PlayerSpaceship (long id, int type) {
		super(id, type);
	}
	
	public PlayerSpaceship (Drawable image) {
		super(image, DEFAULT_HP);
		this.weapon = Weapon.WEAPON_0;
		this.weapon.setEquipped(true);
		PlayerSpaceship.equippedWeapon = this.weapon;
	}
	
	public PlayerSpaceship (Drawable image, int hp) {
		super(image, hp);
		this.weapon = Weapon.WEAPON_0;
		this.weapon.setEquipped(true);
		PlayerSpaceship.equippedWeapon = this.weapon;
	}
	
	public PlayerSpaceship (Drawable image, int hp, int hpTotal, Weapon weapon) {
		super(image, hp, hpTotal);
		this.weapon = weapon;
		this.weapon.setEquipped(true);
		PlayerSpaceship.equippedWeapon = this.weapon;
	}
	
	public PlayerSpaceship (Drawable image, int hp, int hpTotal, RectF healthbar, Weapon weapon) {
		super(image, hp, hpTotal, healthbar);
		this.weapon = weapon;
		this.weapon.setEquipped(true);
		PlayerSpaceship.equippedWeapon = this.weapon;
	}
	
	public static Weapon getEquippedWeapon () {
		return equippedWeapon;
	}
	
	public static void setEquippedWeapon (Weapon equippedWeapon) {
		if (!equippedWeapon.isOwned())
			throw new AssertionError("weapon isn't owned");
		
		PlayerSpaceship.equippedWeapon = equippedWeapon;
	}
	
	public Weapon getWeapon () {
		return weapon;
	}
	
	public void setWeapon (Weapon weapon) {
		if (!weapon.isOwned())
			throw new AssertionError("weapon isn't owned");
		
		this.weapon.setEquipped(false);
		this.weapon = weapon;
		this.weapon.setEquipped(true);
		PlayerSpaceship.equippedWeapon = this.weapon;
	}
	
	public void updateWeapon () {
		this.setWeapon(equippedWeapon);
	}
}
