package com.example.spaceship.classes;

import java.util.ArrayList;
import java.util.List;

public class User {
	
	// TODO: Profile pic of camera
	private int points;
	private List<Weapon> weapons;
	private int killCount;
	private int highscore;
	
	public User () {
		this.points  = 0;
		this.weapons = new ArrayList<>();
		this.weapons.add(PlayerSpaceship.DEFAULT_WEAPON);
		this.killCount = 0;
		this.highscore = 0;
	}
	
	public User (int points, List<Weapon> weapons, int killCount, int highscore) {
		this.points    = points;
		this.weapons   = weapons;
		this.killCount = killCount;
		this.highscore = highscore;
	}
	
	public int getPoints () {
		return points;
	}
	
	public void setPoints (int points) {
		this.points = points;
	}
	
	public List<Weapon> getWeapons () {
		return weapons;
	}
	
	public void setWeapons (List<Weapon> weapons) {
		this.weapons = weapons;
	}
	
	public int getKillCount () {
		return killCount;
	}
	
	public void setKillCount (int killCount) {
		this.killCount = killCount;
	}
	
	public int getHighscore () {
		return highscore;
	}
	
	public void setHighscore (int highscore) {
		this.highscore = highscore;
	}
	
	public void increaseKillCount () {
		this.killCount++;
	}
	
	public void increaseKillCount (int kills) {
		this.killCount += kills;
	}
	
	public List<Weapon> addWeapon (Weapon weapon) {
		this.weapons.add(weapon);
		return this.weapons;
	}
	
	public void increasePoints (int points) {
		this.points += points;
	}
}
