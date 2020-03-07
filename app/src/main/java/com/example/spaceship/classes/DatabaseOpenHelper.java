package com.example.spaceship.classes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * SQLite Database class that manages the Database of the app
 */

abstract class DatabaseOpenHelper extends SQLiteOpenHelper {
	
	// General Database Values
	private static final String TABLE_SPACESHIPS = "spaceships_table";
	private static final String DATABASE_NAME = "users.db";
	private static final int DATABASE_VERSION = 1;
	private static final String TABLE_USERS = "users_table";
	private static final String TABLE_WEAPONS = "weapons_table";
	
	// Users
	private static final String USERS_COLUMN_ID = "id";
	private static final String USERS_COLUMN_NAME = "name";
	private static final String USERS_COLUMN_PICTURE = "picture";
	private static final String USERS_COLUMN_POINTS = "points";
	private static final String USERS_COLUMN_HIGHSCORE = "highscore";
	private static final String[] USERS_COLUMNS = {
			USERS_COLUMN_ID,
			USERS_COLUMN_NAME,
			USERS_COLUMN_PICTURE,
			USERS_COLUMN_POINTS,
			USERS_COLUMN_HIGHSCORE
	};
	
	// Weapons
	private static final String WEAPONS_COLUMN_ID = "id";
	private static final String WEAPONS_COLUMN_NAME = "name";
	private static final String WEAPONS_COLUMN_PRICE = "price";
	private static final String WEAPONS_COLUMN_DAMAGE = "damage";
	private static final String WEAPONS_COLUMN_SPEED = "speed";
	private static final String WEAPONS_COLUMN_OWNED = "owned";
	private static final String WEAPONS_COLUMN_EQUIPPED = "equipped";
	private static final String[] WEAPONS_COLUMNS = {
			WEAPONS_COLUMN_ID,
			WEAPONS_COLUMN_NAME,
			WEAPONS_COLUMN_PRICE,
			WEAPONS_COLUMN_DAMAGE,
			WEAPONS_COLUMN_SPEED,
			WEAPONS_COLUMN_OWNED,
			WEAPONS_COLUMN_EQUIPPED
	};
	
	// Spaceships
	private static final String SPACESHIPS_COLUMN_ID = "id";
	private static final String SPACESHIPS_COLUMN_TYPE = "type";
	private static final String[] SPACESHIPS_COLUMNS = {
			SPACESHIPS_COLUMN_ID, SPACESHIPS_COLUMN_TYPE
	};
	
	private static final String CREATE_TABLE_USERS = "CREATE TABLE IF NOT EXISTS " +
	                                                 TABLE_USERS +
	                                                 " (" +
	                                                 USERS_COLUMN_ID +
	                                                 " INTEGER PRIMARY KEY AUTOINCREMENT, " +
	                                                 USERS_COLUMN_NAME +
	                                                 " TEXT, " +
	                                                 USERS_COLUMN_PICTURE +
	                                                 " BLOB, " +
	                                                 USERS_COLUMN_POINTS +
	                                                 " INTEGER, " +
	                                                 USERS_COLUMN_HIGHSCORE +
	                                                 " INTEGER);";
	private static final String CREATE_TABLE_WEAPONS = "CREATE TABLE IF NOT EXISTS " +
	                                                   TABLE_WEAPONS +
	                                                   " (" +
	                                                   WEAPONS_COLUMN_ID +
	                                                   " INTEGER PRIMARY KEY AUTOINCREMENT, " +
	                                                   WEAPONS_COLUMN_NAME +
	                                                   " TEXT, " +
	                                                   WEAPONS_COLUMN_PRICE +
	                                                   " INTEGER, " +
	                                                   WEAPONS_COLUMN_DAMAGE +
	                                                   " INTEGER, " +
	                                                   WEAPONS_COLUMN_SPEED +
	                                                   " INTEGER, " +
	                                                   WEAPONS_COLUMN_OWNED +
	                                                   " TEXT, " +
	                                                   WEAPONS_COLUMN_EQUIPPED +
	                                                   " TEXT);";
	
	private static final String CREATE_TABLE_SPACESHIPS = "CREATE TABLE IF NOT EXISTS " +
	                                                      TABLE_SPACESHIPS +
	                                                      " (" +
	                                                      SPACESHIPS_COLUMN_ID +
	                                                      " INTEGER PRIMARY KEY AUTOINCREMENT, " +
	                                                      SPACESHIPS_COLUMN_TYPE +
	                                                      " INTEGER);";
	
	SQLiteDatabase database;
	
	// TODO: Test-Runs & Make sure no USERS_ in Weapon/Spaceship methods
	
	/**
	 * Database constructor
	 *
	 * @param context activity from which the Database is initialized
	 */
	
	public DatabaseOpenHelper (@Nullable Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		database = this.getWritableDatabase();
	}
	
	/**
	 * Insert {@link User user} into the Database
	 *
	 * @param user user to insert
	 *
	 * @return the user that was inserted
	 */
	
	public User insert (User user) {
		ContentValues values = new ContentValues();
		values.put("name", user.getName());
		values.put("points", user.getPoints());
		values.put("highscore", user.getHighscore());
		
		long id = database.insert(DatabaseOpenHelper.TABLE_USERS, null, values);
		user.setId(id);
		return user;
	}
	
	/**
	 * Insert {@link Weapon weapon} into the Database
	 *
	 * @param weapon weapon to insert
	 *
	 * @return the weapon that was inserted
	 */
	
	public Weapon insert (Weapon weapon) {
		ContentValues values = new ContentValues();
		values.put("name", weapon.getName());
		values.put("price", weapon.getPrice());
		values.put("damage", weapon.getDamage());
		values.put("speed", weapon.getSpeed());
		
		long id = database.insert(DatabaseOpenHelper.TABLE_WEAPONS, null, values);
		weapon.setId(id);
		return weapon;
	}
	
	/**
	 * Insert {@link com.example.spaceship.classes.EnemySpaceship enemy} into the Database
	 *
	 * @param spaceship the enemy to insert
	 *
	 * @return the enemy that was inserted
	 */
	
	public EnemySpaceship insert (EnemySpaceship spaceship) {
		ContentValues values = new ContentValues();
		values.put("type", spaceship.getType());
		
		long id = database.insert(DatabaseOpenHelper.TABLE_SPACESHIPS, null, values);
		spaceship.setId(id);
		return spaceship;
	}
	
	/**
	 * Insert {@link com.example.spaceship.classes.PlayerSpaceship player} into the Database
	 *
	 * @param spaceship the player to insert
	 *
	 * @return the player that was inserted
	 */
	
	public PlayerSpaceship insert (PlayerSpaceship spaceship) {
		ContentValues values = new ContentValues();
		values.put("type", 0); // PlayerSpaceship doesn't have a type
		
		long id = database.insert(DatabaseOpenHelper.TABLE_SPACESHIPS, null, values);
		spaceship.setId(id);
		return spaceship;
	}
	
	/**
	 * Deletes {@link com.example.spaceship.classes.User user} from the Database
	 *
	 * @param user the user to be deleted
	 *
	 * @return the user that was deleted
	 *
	 * @see com.example.spaceship.classes.DatabaseOpenHelper#getUser(User)
	 */
	
	public User deleteUser (User user) {
		User res = getUser(user);
		database.delete(DatabaseOpenHelper.TABLE_USERS,
		                DatabaseOpenHelper.USERS_COLUMN_ID + " = " + user.getId(),
		                null);
		return res;
	}
	
	/**
	 * Returns the desired user from the Database
	 *
	 * @param user the desired user to find in the Database
	 *
	 * @return the desired user from the Database
	 *
	 * @see com.example.spaceship.classes.DatabaseOpenHelper#getUser(long)
	 */
	
	public User getUser (User user) {
		return getUser(user.getId());
	}
	
	/**
	 * Returns the desired user from the Database
	 *
	 * @param id the id of the desired user
	 *
	 * @return the desired user from the Database
	 */
	
	public User getUser (long id) {
		Cursor cursor = database.query(DatabaseOpenHelper.TABLE_USERS,
		                               USERS_COLUMNS,
		                               DatabaseOpenHelper.USERS_COLUMN_ID + " = " + id,
		                               null,
		                               null,
		                               null,
		                               null);
		cursor.moveToFirst();
		
		if (cursor.getCount() <= 0)
			return null;
		
		long userId = cursor.getLong(cursor.getColumnIndex(DatabaseOpenHelper.USERS_COLUMN_ID));
		String userName =
				cursor.getString(cursor.getColumnIndex(DatabaseOpenHelper.USERS_COLUMN_NAME));
		// TODO: Blob, Bitmap or String
		int userPoints =
				cursor.getInt(cursor.getColumnIndex(DatabaseOpenHelper.USERS_COLUMN_POINTS));
		int userHighscore =
				cursor.getInt(cursor.getColumnIndex(DatabaseOpenHelper.USERS_COLUMN_HIGHSCORE));
		
		cursor.close();
		return new User(userId, userName, userPoints, userHighscore);
	}
	
	/**
	 * Removes a user from the Database
	 *
	 * @param id the id of the user
	 *
	 * @return the user that was deleted
	 *
	 * @see com.example.spaceship.classes.DatabaseOpenHelper#getUser(long)
	 */
	
	public User deleteUser (long id) {
		User res = getUser(id);
		database.delete(DatabaseOpenHelper.TABLE_USERS,
		                DatabaseOpenHelper.USERS_COLUMN_ID + " = " + id,
		                null);
		return res;
	}
	
	/**
	 * Removes a weapon from the Database
	 *
	 * @param weapon the weapon to be deleted
	 *
	 * @return the weapon that was deleted
	 *
	 * @see com.example.spaceship.classes.DatabaseOpenHelper#getWeapon(com.example.spaceship.classes.Weapon)
	 */
	
	public Weapon deleteWeapon (Weapon weapon) {
		Weapon res = getWeapon(weapon);
		database.delete(DatabaseOpenHelper.TABLE_WEAPONS,
		                DatabaseOpenHelper.WEAPONS_COLUMN_ID + " = " + weapon.getId(),
		                null);
		return res;
	}
	
	/**
	 * Returns the desired {@link com.example.spaceship.classes.Weapon weapon} by the instance.
	 *
	 * @param weapon the instance of {@link com.example.spaceship.classes.Weapon weapon}.
	 *
	 * @return the desired {@link com.example.spaceship.classes.Weapon weapon}.
	 *
	 * @see com.example.spaceship.classes.DatabaseOpenHelper#getWeapon(long)
	 */
	
	public Weapon getWeapon (Weapon weapon) {
		return getWeapon(weapon.getId());
	}
	
	/**
	 * Returns the desired {@link com.example.spaceship.classes.Weapon weapon} by the id.
	 *
	 * @param id the id of the desired weapon.
	 *
	 * @return the desired {@link com.example.spaceship.classes.Weapon weapon} from the {@link
	 *        java.util.List<com.example.spaceship.classes.Weapon> weapons} list located at {@link
	 *        com.example.spaceship.classes.Weapon weapon} class. If the {@link
	 *        java.util.List<com.example.spaceship.classes.Weapon> weapons} list is empty, returns
	 * 		a new
	 *        {@link com.example.spaceship.classes.Weapon weapon} using the Database values.
	 */
	
	public Weapon getWeapon (long id) {
		Cursor cursor = database.query(DatabaseOpenHelper.TABLE_WEAPONS,
		                               WEAPONS_COLUMNS,
		                               DatabaseOpenHelper.WEAPONS_COLUMN_ID + " = " + id,
		                               null,
		                               null,
		                               null,
		                               null);
		cursor.moveToFirst();
		
		if (cursor.getCount() <= 0)
			return null;
		
		long weaponId =
				cursor.getLong(cursor.getColumnIndex(DatabaseOpenHelper.WEAPONS_COLUMN_ID));
		String weaponName =
				cursor.getString(cursor.getColumnIndex(DatabaseOpenHelper.WEAPONS_COLUMN_NAME));
		int weaponPrice =
				cursor.getInt(cursor.getColumnIndex(DatabaseOpenHelper.WEAPONS_COLUMN_PRICE));
		int weaponDamage =
				cursor.getInt(cursor.getColumnIndex(DatabaseOpenHelper.WEAPONS_COLUMN_DAMAGE));
		int weaponSpeed =
				cursor.getInt(cursor.getColumnIndex(DatabaseOpenHelper.WEAPONS_COLUMN_SPEED));
		boolean weaponOwned = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(
				DatabaseOpenHelper.WEAPONS_COLUMN_OWNED)));
		boolean weaponEquipped = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(
				DatabaseOpenHelper.WEAPONS_COLUMN_EQUIPPED)));
		
		cursor.close();
		
		if (Weapon.weapons.size() > 0) {
			for (Weapon weapon : Weapon.weapons)
				if (weapon.getId() == weaponId &&
				    weapon.getName().equals(weaponName) &&
				    weapon.getPrice() == weaponPrice &&
				    weapon.getDamage() == weaponDamage &&
				    weapon.getSpeed() == weaponSpeed &&
				    weapon.isOwned() == weaponOwned &&
				    weapon.isEquipped() == weaponEquipped)
					return weapon;
			return null;
		} else
			return new Weapon(weaponId,
			                  weaponName,
			                  weaponDamage,
			                  weaponSpeed,
			                  weaponPrice,
			                  weaponOwned, weaponEquipped);
	}
	
	/**
	 * Removes a weapon from the Database
	 *
	 * @param id the id of the weapon
	 *
	 * @return the weapon that was deleted
	 *
	 * @see com.example.spaceship.classes.DatabaseOpenHelper#getWeapon(long)
	 */
	
	public Weapon deleteWeapon (long id) {
		Weapon res = getWeapon(id);
		database.delete(DatabaseOpenHelper.TABLE_WEAPONS,
		                DatabaseOpenHelper.WEAPONS_COLUMN_ID + " = " + id,
		                null);
		return res;
	}
	
	/**
	 * Returns a {@link java.util.List<com.example.spaceship.classes.User> list} of all the
	 * users in
	 * the Database.
	 *
	 * @return a {@link java.util.List<com.example.spaceship.classes.User> list} of all the
	 * 		users in
	 * 		the Database.
	 */
	
	public List<User> getAllUsers () {
		List<User> users = new ArrayList<>();
		Cursor cursor = database.query(DatabaseOpenHelper.TABLE_USERS,
		                               DatabaseOpenHelper.USERS_COLUMNS,
		                               null,
		                               null,
		                               null,
		                               null,
		                               null);
		
		if (cursor.getCount() <= 0)
			return null;
		
		while (cursor.moveToNext()) {
			long userId = cursor.getLong(cursor.getColumnIndex(DatabaseOpenHelper.USERS_COLUMN_ID));
			String userName =
					cursor.getString(cursor.getColumnIndex(DatabaseOpenHelper.USERS_COLUMN_NAME));
			// TODO: Blob, Bitmap or String
			int userPoints =
					cursor.getInt(cursor.getColumnIndex(DatabaseOpenHelper.USERS_COLUMN_POINTS));
			int userHighscore =
					cursor.getInt(cursor.getColumnIndex(DatabaseOpenHelper.USERS_COLUMN_HIGHSCORE));
			users.add(new User(userId, userName, userPoints, userHighscore));
		}
		
		cursor.close();
		return users;
	}
	
	/**
	 * Returns a {@link java.util.List<com.example.spaceship.classes.Weapon> list} of all weapons.
	 *
	 * @return a {@link java.util.List<com.example.spaceship.classes.Weapon> list} of all weapons.
	 * 		If the {@link java.util.List<com.example.spaceship.classes.Weapon> weapons} list
	 * 		located at {@link com.example.spaceship.classes.Weapon Weapon} class is empty,
	 * 		creates a new list using the Database values.
	 */
	
	public List<Weapon> getAllWeapons () {
		List<Weapon> weapons = new ArrayList<>();
		Cursor cursor = database.query(DatabaseOpenHelper.TABLE_WEAPONS,
		                               WEAPONS_COLUMNS,
		                               null,
		                               null,
		                               null,
		                               null,
		                               null);
		if (cursor.getCount() <= 0)
			return null;
		
		while (cursor.moveToNext()) {
			long weaponId =
					cursor.getLong(cursor.getColumnIndex(DatabaseOpenHelper.WEAPONS_COLUMN_ID));
			String weaponName =
					cursor.getString(cursor.getColumnIndex(DatabaseOpenHelper.WEAPONS_COLUMN_NAME));
			int weaponPrice =
					cursor.getInt(cursor.getColumnIndex(DatabaseOpenHelper.WEAPONS_COLUMN_PRICE));
			int weaponDamage =
					cursor.getInt(cursor.getColumnIndex(DatabaseOpenHelper.WEAPONS_COLUMN_DAMAGE));
			int weaponSpeed =
					cursor.getInt(cursor.getColumnIndex(DatabaseOpenHelper.WEAPONS_COLUMN_SPEED));
			boolean weaponOwned = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(
					DatabaseOpenHelper.WEAPONS_COLUMN_OWNED)));
			boolean weaponEquipped = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(
					DatabaseOpenHelper.WEAPONS_COLUMN_EQUIPPED)));
			
			if (Weapon.weapons.size() > 0) {
				for (Weapon weapon : Weapon.weapons)
					if (weapon.getId() == weaponId &&
					    weapon.getName().equals(weaponName) &&
					    weapon.getPrice() == weaponPrice &&
					    weapon.getDamage() == weaponDamage &&
					    weapon.getSpeed() == weaponSpeed &&
					    weapon.isOwned() == weaponOwned &&
					    weapon.isEquipped() == weaponEquipped) {
						weapons.add(weapon);
						break;
					}
			} else
				weapons.add(new Weapon(weaponId,
				                       weaponName,
				                       weaponPrice,
				                       weaponDamage,
				                       weaponSpeed,
				                       weaponOwned,
				                       weaponEquipped));
		}
		
		cursor.close();
		return weapons;
	}
	
	public Spaceship getSpaceship (Spaceship spaceship) {
		return getSpaceship(spaceship.getId());
	}
	
	public Spaceship getSpaceship (long id) {
		Cursor cursor = database.query(DatabaseOpenHelper.TABLE_SPACESHIPS,
		                               SPACESHIPS_COLUMNS,
		                               DatabaseOpenHelper.SPACESHIPS_COLUMN_ID + " = " + id,
		                               null,
		                               null,
		                               null,
		                               null);
		cursor.moveToFirst();
		
		if (cursor.getCount() <= 0)
			return null;
		
		long spaceshipId =
				cursor.getLong(cursor.getColumnIndex(DatabaseOpenHelper.SPACESHIPS_COLUMN_ID));
		int spaceshipType =
				cursor.getInt(cursor.getColumnIndex(DatabaseOpenHelper.SPACESHIPS_COLUMN_TYPE));
		
		cursor.close();
		return spaceshipType > 0
		       ? new EnemySpaceship(spaceshipId, spaceshipType)
		       : new PlayerSpaceship(spaceshipId, spaceshipType);
	}
	
	public Spaceship deleteSpaceship (Spaceship spaceship) {
		Spaceship res = getSpaceship(spaceship.getId());
		database.delete(DatabaseOpenHelper.TABLE_SPACESHIPS,
		                DatabaseOpenHelper.SPACESHIPS_COLUMN_ID + " = " + spaceship.getId(),
		                null);
		return res;
	}
	
	public Spaceship deleteSpaceship (long id) {
		Spaceship res = getSpaceship(id);
		database.delete(DatabaseOpenHelper.TABLE_SPACESHIPS,
		                DatabaseOpenHelper.SPACESHIPS_COLUMN_ID + " = " + id,
		                null);
		return res;
	}
	
	public List<Spaceship> getAllSpaceships () {
		List<Spaceship> spaceships = new ArrayList<>();
		Cursor cursor = database.query(DatabaseOpenHelper.TABLE_SPACESHIPS,
		                               DatabaseOpenHelper.SPACESHIPS_COLUMNS,
		                               null,
		                               null,
		                               null,
		                               null,
		                               null);
		
		if (cursor.getCount() <= 0)
			return null;
		
		while (cursor.moveToNext()) {
			long spaceshipId =
					cursor.getLong(cursor.getColumnIndex(DatabaseOpenHelper.SPACESHIPS_COLUMN_ID));
			int spaceshipType =
					cursor.getInt(cursor.getColumnIndex(DatabaseOpenHelper.SPACESHIPS_COLUMN_TYPE));
			
			spaceships.add(spaceshipType > 0
			               ? new EnemySpaceship(spaceshipId, spaceshipType)
			               : new PlayerSpaceship(spaceshipId, spaceshipType));
		}
		
		cursor.close();
		return spaceships;
	}
	
	@Override
	public void onCreate (SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_USERS);
		db.execSQL(CREATE_TABLE_WEAPONS);
		db.execSQL(CREATE_TABLE_SPACESHIPS);
	}
	
	@Override
	public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_WEAPONS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SPACESHIPS);
		onCreate(db);
	}
}
