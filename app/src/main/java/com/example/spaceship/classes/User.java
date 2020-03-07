package com.example.spaceship.classes;

public class User {
	
	// TODO: Profile pic of camera
	// TODO: User Registration Activity/Dialog
	private long id;
	private String name;
	private int points;
	private int highscore;
	
	public User () {
		this.id        = -1;
		this.name      = "";
		this.points    = 0;
		this.highscore = 0;
	}
	
	public User (String name, int points, int highscore) {
		this.id        = -1;
		this.name      = name;
		this.points    = points;
		this.highscore = highscore;
	}
	
	// TODO: String/Blob/Bitmap parameter
	public User (long id, String name, int points, int highscore) {
		this.id        = id;
		this.name      = name;
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
	
	public long getId () {
		return id;
	}
	
	public void setId (long id) {
		this.id = id;
	}
	
	public String getName () {
		return name;
	}
	
	public void setName (String name) {
		this.name = name;
	}
}
