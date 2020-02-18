package com.example.spaceship.classes;

import android.graphics.drawable.Drawable;

public class Weapon {
	
	private String name;
	private int damage;
	private int firerate;
	private int speed;
	private Drawable image;
	
	public Weapon (String name, int damage, int firerate, int speed) {
		this.name     = name;
		this.damage   = damage;
		this.firerate = firerate;
		this.speed    = speed;
	}
	
	public Weapon (String name, int damage, int firerate, int speed, Drawable image) {
		this.name     = name;
		this.damage   = damage;
		this.firerate = firerate;
		this.speed    = speed;
		this.image    = image;
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
	
	public int getFirerate () {
		return firerate;
	}
	
	public void setFirerate (int firerate) {
		this.firerate = firerate;
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
	
	public String getDamageString () {
		return this.damage + " HP";
	}
	
	public String getFirerateString () {
		return this.firerate + "s";
	}
	
	public String getSpeedString () {
		return this.speed + " au/h";
	}
}
