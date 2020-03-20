package com.example.spaceship.classes;

/**
 * The User class that holds the Player's information such as name, profile picture, current amount
 * of points and highscore.
 *
 * @see com.example.spaceship.classes.DatabaseOpenHelper
 */

public class User {
	
	// TODO: User Registration Activity/Dialog
	
	/**
	 * The user's Database ID.
	 */
	
	private long id;
	
	/**
	 * The user's name.
	 */
	
	private String name;
	
	/**
	 * The user's amount of points.
	 */
	
	private int points;
	
	/**
	 * The user's highscore.
	 */
	
	private int highscore;
	
	/**
	 * The default constructor setting the user's fields to default values.
	 */
	
	public User () {
		this.id        = -1;
		this.name      = "";
		this.points    = 0;
		this.highscore = 0;
	}
	
	/**
	 * A constructor that covers all the necessary fields that the app can provide.
	 *
	 * @param name      the user's name.
	 * @param points    the user's amount of points.
	 * @param highscore the user's highscore.
	 *
	 * @see com.example.spaceship.classes.User#name
	 * @see com.example.spaceship.classes.User#points
	 * @see com.example.spaceship.classes.User#highscore
	 */
	
	public User (String name, int points, int highscore) {
		this.id        = -1;
		this.name      = name;
		this.points    = points;
		this.highscore = highscore;
	}
	
	/**
	 * A constructor that covers all the fields, usually called by the Database.
	 *
	 * @param id        the user's Database ID.
	 * @param name      the user's name.
	 * @param points    the user's amount of points.
	 * @param highscore the user's highscore.
	 *
	 * @see com.example.spaceship.classes.User#id
	 * @see com.example.spaceship.classes.User#name
	 * @see com.example.spaceship.classes.User#points
	 * @see com.example.spaceship.classes.User#highscore
	 * @see com.example.spaceship.classes.DatabaseOpenHelper
	 */
	
	public User (long id, String name, int points, int highscore) {
		this.id        = id;
		this.name      = name;
		this.points    = points;
		this.highscore = highscore;
	}
	
	/**
	 * Returns the user's amount of points.
	 *
	 * @return the user's amount of points.
	 *
	 * @see com.example.spaceship.classes.User#points
	 */
	
	public int getPoints () {
		return points;
	}
	
	/**
	 * Sets the user's amount of points to the new amount of points.
	 *
	 * @param points the new amount of points.
	 *
	 * @see com.example.spaceship.classes.User#points
	 */
	
	public void setPoints (int points) {
		this.points = points;
	}
	
	/**
	 * Returns the user's highscore.
	 *
	 * @return the user's highscore.
	 *
	 * @see com.example.spaceship.classes.User#highscore
	 */
	
	public int getHighscore () {
		return highscore;
	}
	
	/**
	 * Sets the user's highscore to the new highscore.
	 *
	 * @param highscore the new highscore.
	 *
	 * @see com.example.spaceship.classes.User#highscore
	 */
	
	public void setHighscore (int highscore) {
		this.highscore = highscore;
	}
	
	/**
	 * Returns the user's Database ID.
	 *
	 * @return the user's Database ID.
	 *
	 * @see com.example.spaceship.classes.User#id
	 */
	
	public long getId () {
		return id;
	}
	
	/**
	 * Sets the user's Database ID to the new Database ID.
	 *
	 * @param id the new Database ID.
	 *
	 * @see com.example.spaceship.classes.User#id
	 */
	
	public void setId (long id) {
		this.id = id;
	}
	
	/**
	 * Returns the user's name.
	 *
	 * @return the user's name.
	 *
	 * @see com.example.spaceship.classes.User#name
	 */
	
	public String getName () {
		return name;
	}
	
	/**
	 * Sets the user's name to the new name.
	 *
	 * @param name the new name.
	 *
	 * @see com.example.spaceship.classes.User#name
	 */
	
	public void setName (String name) {
		this.name = name;
	}
	
}