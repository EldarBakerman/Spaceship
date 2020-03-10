package com.example.spaceship.classes;

import android.graphics.RectF;
import android.graphics.drawable.Drawable;

/**
 * This class represents the player spaceship and inherits from the {@link
 * com.example.spaceship.classes.Spaceship Spaceship} class.
 *
 * <p>
 * This class is also used globally in collaboration with the {@link
 * com.example.spaceship.classes.User User} class to manage global values such as the equipped
 * weapon.
 * </p>
 *
 * <p>
 * This class also uses the {@link com.example.spaceship.classes.Weapon Weapon} class since the
 * player has a weapon to kill its enemies.
 * </p>
 *
 * @see com.example.spaceship.classes.Spaceship
 * @see com.example.spaceship.classes.User
 * @see com.example.spaceship.classes.Weapon
 */

public class PlayerSpaceship extends Spaceship {
	
	/**
	 * The default value for the spaceship's total and current HP.
	 */
	
	static final int DEFAULT_HP = 15;
	
	/**
	 * A global static field that holds the currently equipped
	 * {@link com.example.spaceship.classes.Weapon
	 * weapon} of the player.
	 *
	 * @see com.example.spaceship.classes.Weapon
	 * @see com.example.spaceship.classes.PlayerSpaceship#weapon
	 */
	
	private static Weapon equippedWeapon;
	
	/**
	 * A private field that holds the {@link com.example.spaceship.classes.Weapon weapon} that the
	 * player currently has equipped.
	 *
	 * @see com.example.spaceship.classes.Weapon weapon
	 */
	
	private Weapon weapon;
	
	/**
	 * A default constructor that calls the {@link com.example.spaceship.classes.Spaceship
	 * Spaceship}'s default constructor and sets all the fields to their default values.
	 */
	
	public PlayerSpaceship () {
		super();
		this.hp      = DEFAULT_HP;
		this.hpTotal = DEFAULT_HP;
		this.weapon  = Weapon.WEAPON_0;
		this.weapon.setEquipped(true);
		PlayerSpaceship.equippedWeapon = this.weapon;
	}
	
	/**
	 * A constructor used by the Database that provides a Database ID and calls the {@link
	 * com.example.spaceship.classes.Spaceship Spaceship}'s class constructor.
	 *
	 * @param id the spaceship's Database ID.
	 */
	
	public PlayerSpaceship (long id) {
		super(id, 0);
	}
	
	/**
	 * A minimal constructor that provides only an image. All the other fields are set to their
	 * default values.
	 *
	 * @param image the spaceship's image.
	 *
	 * @see com.example.spaceship.classes.PlayerSpaceship#image
	 */
	
	public PlayerSpaceship (Drawable image) {
		super(image, DEFAULT_HP);
		this.weapon = Weapon.WEAPON_0;
		this.weapon.setEquipped(true);
		PlayerSpaceship.equippedWeapon = this.weapon;
	}
	
	/**
	 * A constructor that provides an image and the HP field which is set to both the total and the
	 * current HP fields.
	 *
	 * @param image the spaceship's image.
	 * @param hp    the spaceship's total and current HP.
	 *
	 * @see com.example.spaceship.classes.PlayerSpaceship#image
	 * @see com.example.spaceship.classes.PlayerSpaceship#hp
	 * @see com.example.spaceship.classes.PlayerSpaceship#hpTotal
	 */
	
	public PlayerSpaceship (Drawable image, int hp) {
		super(image, hp);
		this.weapon = Weapon.WEAPON_0;
		this.weapon.setEquipped(true);
		PlayerSpaceship.equippedWeapon = this.weapon;
	}
	
	/**
	 * A constructor that provides all the fields except for the {@link android.graphics.RectF
	 * healthbar} field.
	 *
	 * @param image   the spaceship's image.
	 * @param hp      the spaceship's current HP.
	 * @param hpTotal the spaceship's total HP.
	 * @param weapon  the spaceship's {@link com.example.spaceship.classes.Weapon weapon}.
	 *
	 * @see com.example.spaceship.classes.PlayerSpaceship#image
	 * @see com.example.spaceship.classes.PlayerSpaceship#hp
	 * @see com.example.spaceship.classes.PlayerSpaceship#hpTotal
	 * @see com.example.spaceship.classes.PlayerSpaceship#weapon
	 * @see com.example.spaceship.classes.Weapon
	 */
	
	public PlayerSpaceship (Drawable image, int hp, int hpTotal, Weapon weapon) {
		super(image, hp, hpTotal);
		this.weapon = weapon;
		this.weapon.setEquipped(true);
		PlayerSpaceship.equippedWeapon = this.weapon;
	}
	
	/**
	 * A constructor that provides all the fields.
	 *
	 * @param image     the spaceship's image.
	 * @param hp        the spaceship's current HP.
	 * @param hpTotal   the spaceship's total HP.
	 * @param healthbar the spaceship's {@link android.graphics.RectF healthbar}.
	 * @param weapon    the spaceship's {@link com.example.spaceship.classes.Weapon weapon}.
	 *
	 * @see com.example.spaceship.classes.PlayerSpaceship#image
	 * @see com.example.spaceship.classes.PlayerSpaceship#hp
	 * @see com.example.spaceship.classes.PlayerSpaceship#hpTotal
	 * @see com.example.spaceship.classes.PlayerSpaceship#healthbar
	 * @see com.example.spaceship.classes.PlayerSpaceship#weapon
	 * @see com.example.spaceship.classes.Weapon
	 */
	
	public PlayerSpaceship (Drawable image, int hp, int hpTotal, RectF healthbar, Weapon weapon) {
		super(image, hp, hpTotal, healthbar);
		this.weapon = weapon;
		this.weapon.setEquipped(true);
		PlayerSpaceship.equippedWeapon = this.weapon;
	}
	
	/**
	 * Returns the currently equipped {@link com.example.spaceship.classes.Weapon weapon}.
	 *
	 * @return the currently equipped {@link com.example.spaceship.classes.Weapon weapon}.
	 *
	 * @see com.example.spaceship.classes.PlayerSpaceship#equippedWeapon
	 * @see com.example.spaceship.classes.Weapon
	 */
	
	public static Weapon getEquippedWeapon () {
		return equippedWeapon;
	}
	
	/**
	 * Sets the currently equipped static {@link com.example.spaceship.classes.Weapon weapon} to
	 * the
	 * new {@link com.example.spaceship.classes.Weapon weapon}.
	 *
	 * @param equippedWeapon the new static {@link com.example.spaceship.classes.Weapon weapon}.
	 *
	 * @see com.example.spaceship.classes.PlayerSpaceship#equippedWeapon
	 * @see com.example.spaceship.classes.Weapon
	 */
	
	public static void setEquippedWeapon (Weapon equippedWeapon) {
		if (!equippedWeapon.isOwned())
			throw new AssertionError("weapon isn't owned");
		
		PlayerSpaceship.equippedWeapon = equippedWeapon;
	}
	
	/**
	 * Returns the currently equipped {@link com.example.spaceship.classes.Weapon weapon}.
	 *
	 * @return the currently equipped {@link com.example.spaceship.classes.Weapon weapon}.
	 *
	 * @see com.example.spaceship.classes.PlayerSpaceship#weapon
	 * @see com.example.spaceship.classes.Weapon
	 */
	
	public Weapon getWeapon () {
		return weapon;
	}
	
	/**
	 * Sets the currently equipped {@link com.example.spaceship.classes.Weapon weapon} to the new
	 * {@link com.example.spaceship.classes.Weapon weapon}.
	 *
	 * @param weapon the new {@link com.example.spaceship.classes.Weapon weapon}.
	 *
	 * @see com.example.spaceship.classes.PlayerSpaceship#weapon
	 * @see com.example.spaceship.classes.Weapon
	 */
	
	public void setWeapon (Weapon weapon) {
		if (!weapon.isOwned())
			throw new AssertionError("weapon isn't owned");
		
		this.weapon.setEquipped(false);
		this.weapon = weapon;
		this.weapon.setEquipped(true);
		PlayerSpaceship.equippedWeapon = this.weapon;
	}
	
	/**
	 * Updates the {@link com.example.spaceship.classes.PlayerSpaceship#weapon} to match the {@link
	 * com.example.spaceship.classes.PlayerSpaceship#equippedWeapon}.
	 *
	 * @see com.example.spaceship.classes.PlayerSpaceship#weapon
	 * @see com.example.spaceship.classes.PlayerSpaceship#equippedWeapon
	 * @see com.example.spaceship.classes.Weapon
	 */
	
	public void updateWeapon () {
		this.setWeapon(equippedWeapon);
	}
}
