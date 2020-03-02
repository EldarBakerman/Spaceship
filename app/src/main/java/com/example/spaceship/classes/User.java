package com.example.spaceship.classes;

public class User {
	
	// TODO: Profile pic of camera
	private int points;
	private int killCount;
	private int highscore;
	
	public User () {
		this.points  = 0;
		this.killCount = 0;
		this.highscore = 0;
	}
	
	public User (int points, int killCount, int highscore) {
		this.points    = points;
		this.killCount = killCount;
		this.highscore = highscore;
	}
	
	public int getPoints () {
		return points;
	}
	
	public void setPoints (int points) {
		this.points = points;
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
	
	public void increasePoints (int points) {
		this.points += points;
	}
}
