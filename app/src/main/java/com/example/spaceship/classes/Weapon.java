package com.example.spaceship.classes;

import android.graphics.drawable.Drawable;
import android.util.Log;

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
		final List<Weapon> allWeapons = SplashActivity.database.getAllWeapons();
		if (allWeapons == null || allWeapons.isEmpty()) {
			WEAPON_0          = new Weapon("Default", 5, 2, 0);
			WEAPON_0.owned    = true;
			WEAPON_0.equipped = true;
			WEAPON_1          = new Weapon("Prism", 8, 3, 25);
			WEAPON_2          = new Weapon("Finisher", 12, 1, 50);
			WEAPON_3          = new Weapon("Purity", 15, 2, 100);
		} else {
			WEAPON_0 = new Weapon(allWeapons.get(0));
			WEAPON_1 = new Weapon(allWeapons.get(1));
			WEAPON_2 = new Weapon(allWeapons.get(2));
			WEAPON_3 = new Weapon(allWeapons.get(3));
		}
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
	 * <p>
	 * Min. Value: 5
	 * Max. Value: 20
	 * Def. Value: 5
	 * </p>
	 */
	
	private int damage;
	
	/**
	 * The velocity multiplier that determines in which speed the laser moves.
	 * <p>
	 * Min. Value: 1
	 * Max. Value: 3
	 * Def. Value: 2
	 * </p>
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
	 * Adds {@link com.example.spaceship.classes.Weapon weapon} to the {@link
	 * com.example.spaceship.classes.Weapon#weapons} list and to the {@link
	 * com.example.spaceship.classes.DatabaseOpenHelper database}.
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
	 * @see com.example.spaceship.classes.DatabaseOpenHelper
	 * @see com.example.spaceship.activities.SplashActivity#database
	 */
	
	public Weapon (long id, String name, int damage, int speed, int price, boolean owned,
	               boolean equipped) {
		log(id, name, damage, speed, price, owned, equipped);
		
		try {
			SplashActivity.database.deleteWeapon(this.id);
		} catch (Exception e) {
			Log.d("Weapon#constructor",
			      String.format("Failed to delete Weapon %s with ID %d", this.name, this.id));
		}
		
		this.id       = id;
		this.name     = name;
		this.damage   = damage;
		this.speed    = speed;
		this.price    = price;
		this.owned    = owned;
		this.equipped = equipped && owned;
		
		int i;
		for (i = 0; i < weapons.size(); i++) {
			if ((this.id >= 1 && this.id == weapons.get(i).id) || (this.id < 1 && this.name.equals(
					weapons.get(i).name))) {
				weapons.remove(weapons.get(i));
				weapons.add(i, this);
			}
		}
		
		if (i == weapons.size())
			weapons.add(this);
		
		try {
			SplashActivity.database.getWeapon(this.name);
			SplashActivity.database.update(this);
		} catch (NullPointerException exception) {
			exception.printStackTrace();
		} catch (Exception exception) {
			exception.printStackTrace();
			SplashActivity.database.insert(this);
		}
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
		log(name, damage, speed, price);
		
		try {
			SplashActivity.database.deleteWeapon(this.id);
		} catch (Exception e) {
			Log.d("Weapon#constructor", "Cannot find weapon with ID " + this.id);
		}
		
		this.id     = -1;
		this.name   = name;
		this.damage = damage;
		this.speed  = speed;
		this.price  = price;
		
		int i;
		for (i = 0; i < weapons.size(); i++) {
			if ((this.id >= 1 && this.id == weapons.get(i).id) || (this.id < 1 && this.name.equals(
					weapons.get(i).name))) {
				weapons.remove(weapons.get(i));
				weapons.add(i, this);
			}
		}
		
		if (i == weapons.size())
			weapons.add(this);
		
		if (SplashActivity.database != null)
			this.id = SplashActivity.database.insert(this).id;
	}
	
	/**
	 * A constructor of the weapon that takes only the most basic arguments and the image.
	 * Adds {@link com.example.spaceship.classes.Weapon weapon} to the {@link
	 * com.example.spaceship.classes.Weapon#weapons} list and to the {@link
	 * com.example.spaceship.classes.DatabaseOpenHelper database}.
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
	 * @see com.example.spaceship.classes.DatabaseOpenHelper
	 * @see com.example.spaceship.activities.SplashActivity#database
	 */
	
	public Weapon (String name, int damage, int speed, Drawable image, int price) {
		log(name, damage, speed, price);
		
		try {
			SplashActivity.database.deleteWeapon(this.id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.id     = -1;
		this.name   = name;
		this.damage = damage;
		this.speed  = speed;
		this.image  = image;
		this.price  = price;
		
		int i;
		for (i = 0; i < weapons.size(); i++) {
			if ((this.id >= 1 && this.id == weapons.get(i).id) || (this.id < 1 && this.name.equals(
					weapons.get(i).name))) {
				weapons.remove(weapons.get(i));
				weapons.add(i, this);
			}
		}
		
		if (i == weapons.size())
			weapons.add(this);
		
		try {
			SplashActivity.database.getWeapon(this.name);
			SplashActivity.database.update(this);
		} catch (NullPointerException exception) {
			exception.printStackTrace();
		} catch (Exception exception) {
			exception.printStackTrace();
			SplashActivity.database.insert(this);
		}
	}
	
	/**
	 * Copy constructor that copies all the fields from the other {@link
	 * com.example.spaceship.classes.Weapon weapon}.
	 * <p>
	 * Removes previous {@link com.example.spaceship.classes.Weapon weapon} from {@link
	 * com.example.spaceship.classes.DatabaseOpenHelper database} and adds the new one (if doesn't
	 * already exist).
	 *
	 * @param weapon the other weapon to copy from.
	 *
	 * @see com.example.spaceship.classes.Weapon
	 * @see com.example.spaceship.classes.Weapon#id
	 * @see com.example.spaceship.classes.Weapon#name
	 * @see com.example.spaceship.classes.Weapon#damage
	 * @see com.example.spaceship.classes.Weapon#speed
	 * @see com.example.spaceship.classes.Weapon#price
	 * @see com.example.spaceship.classes.Weapon#image
	 * @see com.example.spaceship.classes.Weapon#owned
	 * @see com.example.spaceship.classes.Weapon#equipped
	 * @see com.example.spaceship.classes.DatabaseOpenHelper
	 * @see com.example.spaceship.activities.SplashActivity#database
	 */
	
	public Weapon (Weapon weapon) {
		log(weapon);
		
		try {
			SplashActivity.database.deleteWeapon(this.id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.id       = weapon.getId();
		this.name     = weapon.getName();
		this.damage   = weapon.getDamage();
		this.speed    = weapon.getSpeed();
		this.price    = weapon.getPrice();
		this.image    = weapon.getImage();
		this.owned    = weapon.isOwned();
		this.equipped = weapon.isEquipped();
		
		int i;
		for (i = 0; i < weapons.size(); i++) {
			if ((this.id >= 1 && this.id == weapons.get(i).id) || (this.id < 1 && this.name.equals(
					weapons.get(i).name))) {
				weapons.remove(weapons.get(i));
				weapons.add(i, this);
			}
		}
		
		if (i == weapons.size())
			weapons.add(this);
		
		try {
			SplashActivity.database.getWeapon(this.name);
			SplashActivity.database.update(this);
		} catch (NullPointerException exception) {
			exception.printStackTrace();
		} catch (Exception exception) {
			exception.printStackTrace();
			SplashActivity.database.insert(this);
		}
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
	
	/**
	 * Logs all the fields of the Weapon and all the parameters.
	 *
	 * @param id       the id parameter
	 * @param name     the name parameter
	 * @param damage   the damage parameter
	 * @param speed    the speed parameter
	 * @param price    the price parameter
	 * @param owned    the owned parameter
	 * @param equipped the equipped parameter
	 *
	 * @see com.example.spaceship.classes.Weapon#id
	 * @see com.example.spaceship.classes.Weapon#name
	 * @see com.example.spaceship.classes.Weapon#damage
	 * @see com.example.spaceship.classes.Weapon#speed
	 * @see com.example.spaceship.classes.Weapon#price
	 * @see com.example.spaceship.classes.Weapon#owned
	 * @see com.example.spaceship.classes.Weapon#equipped
	 */
	
	private void log (long id, String name, int damage, int speed, int price, boolean owned,
	                  boolean equipped) {
		Log.d("Weapon#constructor",
		      String.format("Constructor: All\n" +
		                    "ID: %d NAME: %s DMG: %d SPD: %d PRC: %d OWN: %s EQP: %s\n" +
		                    "Params: ID: %d NAME: %s DMG: %d SPD: %d PRC: %d OWN: %s EQP: %s",
		                    this.id,
		                    this.name,
		                    this.damage,
		                    this.speed,
		                    this.price,
		                    this.owned,
		                    this.equipped,
		                    id,
		                    name,
		                    damage,
		                    speed,
		                    price,
		                    owned,
		                    equipped));
	}
	
	/**
	 * Logs all the basic fields of the Weapon and all the parameters.
	 *
	 * @param name   the name parameter
	 * @param damage the damage parameter
	 * @param speed  the speed parameter
	 * @param price  the price parameter
	 *
	 * @see com.example.spaceship.classes.Weapon#name
	 * @see com.example.spaceship.classes.Weapon#damage
	 * @see com.example.spaceship.classes.Weapon#speed
	 * @see com.example.spaceship.classes.Weapon#price
	 */
	
	private void log (String name, int damage, int speed, int price) {
		Log.d("Weapon#constructor",
		      String.format("Constructor: Basic\n" +
		                    "ID: %d NAME: %s DMG: %d SPD: %d PRC: %d OWN: %s EQP: %s\n" +
		                    "Params: NAME: %s DMG: %d SPD: %d PRC: %d",
		                    this.id,
		                    this.name,
		                    this.damage,
		                    this.speed,
		                    this.price,
		                    this.owned,
		                    this.equipped,
		                    name,
		                    damage,
		                    speed,
		                    price));
	}
	
	/**
	 * Logs all the basic fields of the Weapon and all the parameters.
	 *
	 * @param weapon the {@link com.example.spaceship.classes.Weapon weapon} parameter.
	 *
	 * @see com.example.spaceship.classes.Weapon
	 * @see com.example.spaceship.classes.Weapon#id
	 * @see com.example.spaceship.classes.Weapon#name
	 * @see com.example.spaceship.classes.Weapon#damage
	 * @see com.example.spaceship.classes.Weapon#speed
	 * @see com.example.spaceship.classes.Weapon#price
	 * @see com.example.spaceship.classes.Weapon#owned
	 * @see com.example.spaceship.classes.Weapon#equipped
	 */
	
	private void log (Weapon weapon) {
		Log.d("Weapon#constructor",
		      String.format("Constructor: Weapon\n" +
		                    "ID: %d NAME: %s DMG: %d SPD: %d PRC: %d OWN: %s EQP: %s\n" +
		                    "Params: ID: %d NAME: %s DMG: %d SPD: %d PRC: %d OWN: %s EQP: %s",
		                    this.id,
		                    this.name,
		                    this.damage,
		                    this.speed,
		                    this.price,
		                    this.owned,
		                    this.equipped,
		                    weapon.id,
		                    weapon.name,
		                    weapon.damage,
		                    weapon.speed,
		                    weapon.price,
		                    weapon.owned,
		                    weapon.equipped));
	}
}
