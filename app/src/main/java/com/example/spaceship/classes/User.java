package com.example.spaceship.classes;

public class User {
	
	// TODO: Profile pic of camera
	// TODO: Name
	private int points;
	private int highscore;
	
	public User () {
		this.points  = 0;
		this.highscore = 0;
	}
	
	public User (int points, int highscore) {
		this.points    = points;
		this.highscore = highscore;
	}
	
	public int getPoints () {
		return points;
	}
	
	public void setPoints (int points) {
		this.points = points;
	}
	
	public int getHighscore () {
		return highscore;
	}
	
	public void setHighscore (int highscore) {
		this.highscore = highscore;
	}
	
}
