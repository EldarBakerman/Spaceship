package com.example.spaceship.classes;

import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.spaceship.activities.SplashActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@link com.example.spaceship.classes.PlayerSpaceship player}'s weapon.
 */

public class Weapon {
	
	static final Weapon WEAPON_0;
	private static final Weapon WEAPON_1;
	private static final Weapon WEAPON_2;
	private static final Weapon WEAPON_3;
	public static List<Weapon> weapons = new ArrayList<>();
	
	static {
		// TODO: Get equipped weapon from Database
		WEAPON_0 = new Weapon("Default", 5, 2, 0);
		WEAPON_0.setOwned(true);
		WEAPON_0.setEquipped(true);
		PlayerSpaceship.setEquippedWeapon(WEAPON_0);
		WEAPON_1 = new Weapon("Death Ray", 10, 3, 25);
		WEAPON_2 = new Weapon("Finisher", 20, 1, 50);
		WEAPON_3 = new Weapon("Purified Light", 30, 2, 100);
	}
	
	/**
	 * The Database ID of the Weapon
	 */
	
	private long id;
	
	/**
	 * The name of the weapon.
	 */
	
	private String name;
	
	/**
	 * The amount of HP the weapon reduces from the target after it hits.
	 */
	
	private int damage;
	
	/**
	 * The velocity in which the laser is moving.
	 */
	
	private int speed;
	
	/**
	 * The weapon's image (in the form of a laser)
	 */
	
	private Drawable image;
	
	/**
	 * Is the weapon currently equipped.
	 */
	
	private boolean equipped;
	
	/**
	 * The weapon's price in store in points.
	 */
	
	private int price;
	
	/**
	 * Is the weapon currently owned.
	 */
	
	private boolean owned;
	
	/**
	 * A constructor of the weapon that is usually used by the Database.
	 *
	 * @param id       the ID of the weapon in the Database.
	 * @param name     the name of the weapon.
	 * @param damage   the damage of the weapon.
	 * @param speed    the speed of the weapon.
	 * @param price    the price of the weapon.
	 * @param owned    is the weapon currently owned.
	 * @param equipped is the weapon currently equipped.
	 *
	 * @see com.example.spaceship.classes.Weapon#id
	 * @see com.example.spaceship.classes.Weapon#name
	 * @see com.example.spaceship.classes.Weapon#damage
	 * @see com.example.spaceship.classes.Weapon#speed
	 * @see com.example.spaceship.classes.Weapon#price
	 * @see com.example.spaceship.classes.Weapon#owned
	 * @see com.example.spaceship.classes.Weapon#equipped
	 */
	
	public Weapon (long id, String name, int damage, int speed, int price, boolean owned,
	               boolean equipped) {
		this.id       = id;
		this.name     = name;
		this.damage   = damage;
		this.speed    = speed;
		this.price    = price;
		this.owned    = owned;
		this.equipped = equipped && owned;
		weapons.add(this);
		if (SplashActivity.database != null &&
		    SplashActivity.database.getAllWeapons() != null &&
		    SplashActivity.database.getAllWeapons().size() > 0)
			SplashActivity.database.update(this);
	}
	
	/**
	 * A constructor of the weapon that takes only the most basic arguments.
	 *
	 * @param name   the name of the weapon.
	 * @param damage the damage of the weapon.
	 * @param speed  the speed of the weapon.
	 * @param price  the price of the weapon.
	 *
	 * @see com.example.spaceship.classes.Weapon#name
	 * @see com.example.spaceship.classes.Weapon#damage
	 * @see com.example.spaceship.classes.Weapon#speed
	 * @see com.example.spaceship.classes.Weapon#price
	 */
	
	public Weapon (String name, int damage, int speed, int price) {
		this.id     = -1;
		this.name   = name;
		this.damage = damage;
		this.speed  = speed;
		this.price  = price;
		weapons.add(this);
		if (SplashActivity.database != null &&
		    SplashActivity.database.getAllWeapons() != null &&
		    SplashActivity.database.getAllWeapons().size() > 0)
			SplashActivity.database.update(this);
	}
	
	/**
	 * A constructor of the weapon that takes only the most basic arguments and the image.
	 *
	 * @param name   the name of the weapon.
	 * @param damage the damage of the weapon.
	 * @param speed  the speed of the weapon.
	 * @param image  the image of the weapon.
	 * @param price  the price of the weapon.
	 *
	 * @see com.example.spaceship.classes.Weapon#name
	 * @see com.example.spaceship.classes.Weapon#damage
	 * @see com.example.spaceship.classes.Weapon#speed
	 * @see com.example.spaceship.classes.Weapon#price
	 * @see com.example.spaceship.classes.Weapon#image
	 */
	
	public Weapon (String name, int damage, int speed, Drawable image, int price) {
		this.id     = -1;
		this.name   = name;
		this.damage = damage;
		this.speed  = speed;
		this.image  = image;
		this.price  = price;
		weapons.add(this);
		if (SplashActivity.database != null &&
		    SplashActivity.database.getAllWeapons() != null &&
		    SplashActivity.database.getAllWeapons().size() > 0)
			SplashActivity.database.update(this);
	}
	
	/**
	 * Get's the weapon's image.
	 *
	 * @return the weapon's image.
	 *
	 * @see com.example.spaceship.classes.Weapon#image
	 */
	
	public Drawable getImage () {
		return image;
	}
	
	/**
	 * Sets the weapon's image to the new image.
	 *
	 * @param image the new image.
	 *
	 * @see com.example.spaceship.classes.Weapon#image
	 */
	
	public void setImage (Drawable image) {
		this.image = image;
		SplashActivity.database.update(this);
	}
	
	/**
	 * An equals method overriding the {@link Object#equals(Object)} method located at {@link
	 * java.lang.Object}.
	 *
	 * @param obj the {@link java.lang.Object object } to compare to.
	 *
	 * @return true if all the fields are equal.
	 */
	
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
	
	/**
	 * Returns the weapon's id.
	 *
	 * @return the weapon's id.
	 *
	 * @see com.example.spaceship.classes.Weapon#id
	 */
	
	public long getId () {
		return id;
	}
	
	/**
	 * Returns the weapon's name.
	 *
	 * @return the weapon's name.
	 *
	 * @see com.example.spaceship.classes.Weapon#name
	 */
	
	public String getName () {
		return name;
	}
	
	/**
	 * Returns the weapon's price.
	 *
	 * @return the weapon's price.
	 *
	 * @see com.example.spaceship.classes.Weapon#price
	 */
	
	public int getPrice () {
		return price;
	}
	
	/**
	 * Sets the weapon's price to the new price.
	 *
	 * @param price the new price.
	 *
	 * @see com.example.spaceship.classes.Weapon#price
	 */
	
	public void setPrice (int price) {
		this.price = price;
		SplashActivity.database.update(this);
	}
	
	/**
	 * Returns the weapon's damage.
	 *
	 * @return the weapon's damage.
	 *
	 * @see com.example.spaceship.classes.Weapon#damage
	 */
	
	public int getDamage () {
		return damage;
	}
	
	/**
	 * Sets the weapon's damage to the new damage.
	 *
	 * @param damage the new damage.
	 *
	 * @see com.example.spaceship.classes.Weapon#damage
	 */
	
	public void setDamage (int damage) {
		this.damage = damage;
		SplashActivity.database.update(this);
	}
	
	/**
	 * Returns the weapon's speed.
	 *
	 * @return the weapon's speed.
	 *
	 * @see com.example.spaceship.classes.Weapon#speed
	 */
	
	public int getSpeed () {
		return speed;
	}
	
	/**
	 * Sets the weapon's speed to the new speed.
	 *
	 * @param speed the new speed.
	 *
	 * @see com.example.spaceship.classes.Weapon#speed
	 */
	
	public void setSpeed (int speed) {
		this.speed = speed;
		SplashActivity.database.update(this);
	}
	
	/**
	 * Returns true if the weapon is owned.
	 *
	 * @return true if the weapon is owned.
	 *
	 * @see com.example.spaceship.classes.Weapon#owned
	 */
	
	public boolean isOwned () {
		return owned;
	}
	
	/**
	 * Returns true if the weapon is equipped.
	 *
	 * @return true if the weapon is equipped.
	 *
	 * @see com.example.spaceship.classes.Weapon#equipped
	 */
	
	public boolean isEquipped () {
		return equipped;
	}
	
	/**
	 * Sets the equipped field to the new equipped field. If
	 * {@link com.example.spaceship.classes.Weapon#owned}
	 * is false, sets to false.
	 *
	 * @param equipped the new equipped field.
	 *
	 * @see com.example.spaceship.classes.Weapon#equipped
	 * @see com.example.spaceship.classes.Weapon#owned
	 */
	
	public void setEquipped (boolean equipped) {
		if (!owned)
			throw new AssertionError("can't equip if not owned");
		this.equipped = equipped;
		SplashActivity.database.update(this);
	}
	
	/**
	 * Sets the equipped field to the new owned field. If the field is set to false, sets the
	 * {@link
	 * com.example.spaceship.classes.Weapon#equipped} field to false.
	 *
	 * @param owned the new owned field.
	 *
	 * @see com.example.spaceship.classes.Weapon#owned
	 * @see com.example.spaceship.classes.Weapon#equipped
	 */
	
	public void setOwned (boolean owned) {
		this.owned    = owned;
		this.equipped = owned && this.equipped;
		SplashActivity.database.update(this);
	}
	
	/**
	 * Sets the weapon's name to the new name.
	 *
	 * @param name the new name.
	 *
	 * @see com.example.spaceship.classes.Weapon#name
	 */
	
	public void setName (String name) {
		this.name = name;
		SplashActivity.database.update(this);
	}
	
	/**
	 * Sets the weapon's id to the new id.
	 *
	 * @param id the new id.
	 *
	 * @see com.example.spaceship.classes.Weapon#id
	 */
	
	public void setId (long id) {
		this.id = id;
		SplashActivity.database.update(this);
	}
	
	/**
	 * A method overriding the {@link Object#toString()} method located at
	 * {@link java.lang.Object}.
	 *
	 * @return the {@link java.lang.String} form of the weapon.
	 */
	
	@NonNull
	@Override
	public String toString () {
		return "Id: " +
		       this.id +
		       " Name: " +
		       this.name +
		       " Price: " +
		       this.price +
		       " P Damage: " +
		       this.getDamageString() +
		       " Speed: " +
		       this.getSpeedString() +
		       " Owned: " +
		       this.owned +
		       " Equipped: " +
		       this.equipped;
	}
	
	/**
	 * Returns the {@link java.lang.String} form of the weapon's damage field.
	 *
	 * @return the {@link java.lang.String} form of the weapon's damage field.
	 *
	 * @see com.example.spaceship.classes.Weapon#damage
	 */
	
	public String getDamageString () {
		return this.damage + " HP";
	}
	
	/**
	 * Returns the {@link java.lang.String} form of the weapon's speed field.
	 *
	 * @return the {@link java.lang.String} form of the weapon's speed field.
	 *
	 * @see com.example.spaceship.classes.Weapon#speed
	 */
	
	public String getSpeedString () {
		return this.speed + " au/h";
	}
}
