package com.example.spaceship.classes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * SQLite Database class that manages the Database of the app
 */

public class Database extends SQLiteOpenHelper {
	
	// General Database Values
	private static final String TABLE_SPACESHIPS = "spaceships_table";
	private static final String DATABASE_NAME = "users.db";
	private static final int DATABASE_VERSION = 1;
	private static final String TABLE_USERS = "users_table";
	private static final String TABLE_WEAPONS = "weapons_table";
	
	// Users
	private static final String USERS_COLUMN_ID = "id";
	private static final String USERS_COLUMN_NAME = "name";
	private static final String USERS_COLUMN_POINTS = "points";
	private static final String USERS_COLUMN_HIGHSCORE = "highscore";
	private static final String[] USERS_COLUMNS = {
			USERS_COLUMN_ID, USERS_COLUMN_NAME, USERS_COLUMN_POINTS, USERS_COLUMN_HIGHSCORE
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
	
	private SQLiteDatabase database;
	
	/**
	 * Database constructor
	 *
	 * @param context activity from which the Database is initialized
	 */
	
	public Database (@Nullable Context context) {
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
		
		long id = database.insert(Database.TABLE_USERS, null, values);
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
		
		long id = database.insert(Database.TABLE_WEAPONS, null, values);
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
		
		long id = database.insert(Database.TABLE_SPACESHIPS, null, values);
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
		
		long id = database.insert(Database.TABLE_SPACESHIPS, null, values);
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
	 * @see Database#getUser(User)
	 */
	
	public User deleteUser (User user) {
		User res = getUser(user);
		database.delete(Database.TABLE_USERS, Database.USERS_COLUMN_ID + " = " + user.getId(),
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
	 * @see Database#getUser(long)
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
		Cursor cursor = database.query(Database.TABLE_USERS,
		                               USERS_COLUMNS,
		                               Database.USERS_COLUMN_ID + " = " + id,
		                               null,
		                               null,
		                               null,
		                               null);
		cursor.moveToFirst();
		
		if (cursor.getCount() <= 0)
			return null;
		
		long userId = cursor.getLong(cursor.getColumnIndex(Database.USERS_COLUMN_ID));
		String userName = cursor.getString(cursor.getColumnIndex(Database.USERS_COLUMN_NAME));
		int userPoints = cursor.getInt(cursor.getColumnIndex(Database.USERS_COLUMN_POINTS));
		int userHighscore = cursor.getInt(cursor.getColumnIndex(Database.USERS_COLUMN_HIGHSCORE));
		
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
	 * @see Database#getUser(long)
	 */
	
	public User deleteUser (long id) {
		User res = getUser(id);
		database.delete(Database.TABLE_USERS, Database.USERS_COLUMN_ID + " = " + id, null);
		return res;
	}
	
	/**
	 * Removes a weapon from the Database
	 *
	 * @param weapon the weapon to be deleted
	 *
	 * @return the weapon that was deleted
	 *
	 * @see Database#getWeapon(com.example.spaceship.classes.Weapon)
	 */
	
	public Weapon deleteWeapon (Weapon weapon) {
		Weapon res = getWeapon(weapon);
		database.delete(Database.TABLE_WEAPONS,
		                Database.WEAPONS_COLUMN_ID + " = " + weapon.getId(),
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
	 * @see Database#getWeapon(long)
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
		Cursor cursor = database.query(Database.TABLE_WEAPONS,
		                               WEAPONS_COLUMNS,
		                               Database.WEAPONS_COLUMN_ID + " = " + id,
		                               null,
		                               null,
		                               null,
		                               null);
		cursor.moveToFirst();
		
		if (cursor.getCount() <= 0)
			return null;
		
		long weaponId = cursor.getLong(cursor.getColumnIndex(Database.WEAPONS_COLUMN_ID));
		String weaponName = cursor.getString(cursor.getColumnIndex(Database.WEAPONS_COLUMN_NAME));
		int weaponPrice = cursor.getInt(cursor.getColumnIndex(Database.WEAPONS_COLUMN_PRICE));
		int weaponDamage = cursor.getInt(cursor.getColumnIndex(Database.WEAPONS_COLUMN_DAMAGE));
		int weaponSpeed = cursor.getInt(cursor.getColumnIndex(Database.WEAPONS_COLUMN_SPEED));
		boolean weaponOwned =
				Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(Database.WEAPONS_COLUMN_OWNED)));
		boolean weaponEquipped = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(
				Database.WEAPONS_COLUMN_EQUIPPED)));
		
		cursor.close();
		
		if (Weapon.weapons.size() > 0) {
			for (Weapon weapon : Weapon.weapons)
				if (weapon.getId() == weaponId)
					return weapon;
			return null;
		} else
			return new Weapon(weaponId,
			                  weaponName,
			                  weaponDamage,
			                  weaponSpeed,
			                  weaponPrice, weaponOwned, weaponEquipped, true);
	}
	
	public Weapon getWeapon (String name) {
		Cursor cursor = database.query(Database.TABLE_WEAPONS,
		                               WEAPONS_COLUMNS,
		                               Database.WEAPONS_COLUMN_NAME + " = " + name,
		                               null,
		                               null,
		                               null,
		                               null);
		cursor.moveToFirst();
		
		if (cursor.getCount() <= 0)
			return null;
		
		long weaponId = cursor.getLong(cursor.getColumnIndex(Database.WEAPONS_COLUMN_ID));
		String weaponName = cursor.getString(cursor.getColumnIndex(Database.WEAPONS_COLUMN_NAME));
		int weaponPrice = cursor.getInt(cursor.getColumnIndex(Database.WEAPONS_COLUMN_PRICE));
		int weaponDamage = cursor.getInt(cursor.getColumnIndex(Database.WEAPONS_COLUMN_DAMAGE));
		int weaponSpeed = cursor.getInt(cursor.getColumnIndex(Database.WEAPONS_COLUMN_SPEED));
		boolean weaponOwned =
				Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(Database.WEAPONS_COLUMN_OWNED)));
		boolean weaponEquipped = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(
				Database.WEAPONS_COLUMN_EQUIPPED)));
		
		cursor.close();
		
		if (Weapon.weapons.size() > 0) {
			for (Weapon weapon : Weapon.weapons)
				if (weapon.getName().equals(weaponName))
					return weapon;
			return null;
		} else
			return new Weapon(weaponId,
			                  weaponName,
			                  weaponDamage,
			                  weaponSpeed,
			                  weaponPrice, weaponOwned, weaponEquipped, true);
	}
	
	/**
	 * Removes a weapon from the Database
	 *
	 * @param id the id of the weapon
	 *
	 * @return the weapon that was deleted
	 *
	 * @see Database#getWeapon(long)
	 */
	
	public Weapon deleteWeapon (long id) {
		Weapon res = getWeapon(id);
		
		if (id <= 0 || res == null) {
			Log.d("deleteWeapon", "Invalid ID");
			return res;
		}
		
		int ret = database.delete(Database.TABLE_WEAPONS,
		                          Database.WEAPONS_COLUMN_ID + " = " + id,
		                          null);
		
		if (ret <= 0)
			Log.d("deleteWeapon", "Failed to delete Weapon " + res.getName() + " with ID" + id);
		else
			Log.d("deleteWeapon",
			      "Successfully deleted Weapon " + res.getName() + " with ID " + id);
		
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
		Cursor cursor = database.query(Database.TABLE_USERS, Database.USERS_COLUMNS,
		                               null,
		                               null,
		                               null,
		                               null,
		                               null);
		
		if (cursor.getCount() <= 0)
			return null;
		
		while (cursor.moveToNext()) {
			long userId = cursor.getLong(cursor.getColumnIndex(Database.USERS_COLUMN_ID));
			String userName = cursor.getString(cursor.getColumnIndex(Database.USERS_COLUMN_NAME));
			int userPoints = cursor.getInt(cursor.getColumnIndex(Database.USERS_COLUMN_POINTS));
			int userHighscore =
					cursor.getInt(cursor.getColumnIndex(Database.USERS_COLUMN_HIGHSCORE));
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
		Cursor cursor = database.query(Database.TABLE_WEAPONS,
		                               WEAPONS_COLUMNS,
		                               null,
		                               null,
		                               null,
		                               null,
		                               null);
		if (cursor.getCount() <= 0)
			return null;
		
		while (cursor.moveToNext()) {
			long weaponId = cursor.getLong(cursor.getColumnIndex(Database.WEAPONS_COLUMN_ID));
			String weaponName =
					cursor.getString(cursor.getColumnIndex(Database.WEAPONS_COLUMN_NAME));
			int weaponPrice = cursor.getInt(cursor.getColumnIndex(Database.WEAPONS_COLUMN_PRICE));
			int weaponDamage = cursor.getInt(cursor.getColumnIndex(Database.WEAPONS_COLUMN_DAMAGE));
			int weaponSpeed = cursor.getInt(cursor.getColumnIndex(Database.WEAPONS_COLUMN_SPEED));
			boolean weaponOwned = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(
					Database.WEAPONS_COLUMN_OWNED)));
			boolean weaponEquipped = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(
					Database.WEAPONS_COLUMN_EQUIPPED)));
			
			if (Weapon.weapons != null &&
			    Weapon.weapons.size() > 0 &&
			    Weapon.weapons.size() >= weaponId) {
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
				weapons.add(new Weapon(weaponId, weaponName, weaponDamage, weaponSpeed,
				                       weaponPrice, weaponOwned, weaponEquipped, true));
		}
		
		cursor.close();
		return weapons;
	}
	
	/**
	 * Returns the desired {@link com.example.spaceship.classes.Spaceship spaceship} from the
	 * database.
	 *
	 * @param spaceship the instance of the desired {@link com.example.spaceship.classes.Spaceship
	 *                  spaceship}.
	 *
	 * @return the desired {@link com.example.spaceship.classes.Spaceship spaceship} from the
	 * 		database.
	 */
	
	public Spaceship getSpaceship (Spaceship spaceship) {
		return getSpaceship(spaceship.getId());
	}
	
	/**
	 * Returns the desired {@link com.example.spaceship.classes.Spaceship spaceship} from the
	 * database.
	 *
	 * @param id the id of the desired {@link com.example.spaceship.classes.Spaceship spaceship}.
	 *
	 * @return the desired {@link com.example.spaceship.classes.Spaceship spaceship} from the
	 * 		database.
	 */
	
	public Spaceship getSpaceship (long id) {
		Cursor cursor = database.query(Database.TABLE_SPACESHIPS,
		                               SPACESHIPS_COLUMNS,
		                               Database.SPACESHIPS_COLUMN_ID + " = " + id,
		                               null,
		                               null,
		                               null,
		                               null);
		cursor.moveToFirst();
		
		if (cursor.getCount() <= 0)
			return null;
		
		long spaceshipId = cursor.getLong(cursor.getColumnIndex(Database.SPACESHIPS_COLUMN_ID));
		int spaceshipType = cursor.getInt(cursor.getColumnIndex(Database.SPACESHIPS_COLUMN_TYPE));
		
		cursor.close();
		return spaceshipType > 0
		       ? new EnemySpaceship(spaceshipId, spaceshipType)
		       : new PlayerSpaceship(spaceshipId);
	}
	
	/**
	 * Removes a spaceship from the database.
	 *
	 * @param spaceship the spaceship instance to remove from the database.
	 *
	 * @return the spaceship that was removed.
	 */
	
	public Spaceship deleteSpaceship (Spaceship spaceship) {
		Spaceship res = getSpaceship(spaceship.getId());
		database.delete(Database.TABLE_SPACESHIPS,
		                Database.SPACESHIPS_COLUMN_ID + " = " + spaceship.getId(),
		                null);
		return res;
	}
	
	/**
	 * Removes a {@link  com.example.spaceship.classes.Spaceship spaceship} from the database.
	 *
	 * @param id the id of the {@link  com.example.spaceship.classes.Spaceship spaceship} to remove
	 *           from the database.
	 *
	 * @return the {@link  com.example.spaceship.classes.Spaceship spaceship} that was removed.
	 */
	
	public Spaceship deleteSpaceship (long id) {
		Spaceship res = getSpaceship(id);
		database.delete(Database.TABLE_SPACESHIPS, Database.SPACESHIPS_COLUMN_ID + " = " + id,
		                null);
		return res;
	}
	
	/**
	 * Returns a {@link java.util.List<com.example.spaceship.classes.Spaceship> list} of all the
	 * spaceships from the database.
	 *
	 * @return a {@link java.util.List<com.example.spaceship.classes.Spaceship> list} of all the
	 * 		spaceships from the database.
	 */
	
	public List<Spaceship> getAllSpaceships () {
		List<Spaceship> spaceships = new ArrayList<>();
		Cursor cursor = database.query(Database.TABLE_SPACESHIPS, Database.SPACESHIPS_COLUMNS,
		                               null,
		                               null,
		                               null,
		                               null,
		                               null);
		
		if (cursor.getCount() <= 0)
			return null;
		
		while (cursor.moveToNext()) {
			long spaceshipId =
					cursor.getLong(cursor.getColumnIndex(Database.SPACESHIPS_COLUMN_ID));
			int spaceshipType =
					cursor.getInt(cursor.getColumnIndex(Database.SPACESHIPS_COLUMN_TYPE));
			
			spaceships.add(spaceshipType > 0
			               ? new EnemySpaceship(spaceshipId, spaceshipType)
			               : new PlayerSpaceship(spaceshipId));
		}
		
		cursor.close();
		return spaceships;
	}
	
	/**
	 * Update the corresponding {@link com.example.spaceship.classes.User user} in the database.
	 *
	 * @param user the new instance of {@link com.example.spaceship.classes.User user} to put into
	 *             the database.
	 *
	 * @return the long value of the {@link android.database.sqlite.SQLiteDatabase#update(String,
	 *        android.content.ContentValues, String, String[])} method.
	 */
	
	public long update (User user) {
		ContentValues values = new ContentValues();
		values.put(Database.USERS_COLUMN_ID, user.getId());
		values.put(Database.USERS_COLUMN_NAME, user.getName());
		values.put(Database.USERS_COLUMN_POINTS, user.getPoints());
		values.put(Database.USERS_COLUMN_HIGHSCORE, user.getHighscore());
		
		return database.update(Database.TABLE_USERS,
		                       values,
		                       Database.USERS_COLUMN_ID + " = " + user.getId(),
		                       null);
	}
	
	/**
	 * Update the corresponding {@link com.example.spaceship.classes.Weapon weapon} in the
	 * database.
	 *
	 * @param weapon the new instance of {@link com.example.spaceship.classes.Weapon weapon}
	 *               to put into
	 *               the database.
	 *
	 * @return the long value of the {@link android.database.sqlite.SQLiteDatabase#update(String,
	 *        android.content.ContentValues, String, String[])} method.
	 */
	
	public long update (Weapon weapon) {
		ContentValues values = new ContentValues();
		values.put(Database.WEAPONS_COLUMN_ID, weapon.getId());
		values.put(Database.WEAPONS_COLUMN_NAME, weapon.getName());
		values.put(Database.WEAPONS_COLUMN_PRICE, weapon.getPrice());
		values.put(Database.WEAPONS_COLUMN_DAMAGE, weapon.getDamage());
		values.put(Database.WEAPONS_COLUMN_SPEED, weapon.getSpeed());
		values.put(Database.WEAPONS_COLUMN_OWNED, weapon.isOwned());
		values.put(Database.WEAPONS_COLUMN_EQUIPPED, weapon.isEquipped());
		
		return database.update(Database.TABLE_WEAPONS,
		                       values,
		                       Database.WEAPONS_COLUMN_ID + " = " + weapon.getId() + "",
		                       null);
	}
	
	/**
	 * Update the corresponding {@link com.example.spaceship.classes.Spaceship spaceship} in the
	 * database.
	 *
	 * @param spaceship the new instance of {@link com.example.spaceship.classes.Spaceship
	 *                  spaceship}
	 *                  to put into
	 *                  the database.
	 *
	 * @return the long value of the {@link android.database.sqlite.SQLiteDatabase#update(String,
	 *        android.content.ContentValues, String, String[])} method.
	 */
	
	public long update (Spaceship spaceship) {
		ContentValues values = new ContentValues();
		values.put(Database.SPACESHIPS_COLUMN_ID, spaceship.getId());
		values.put(Database.SPACESHIPS_COLUMN_TYPE,
		           spaceship instanceof EnemySpaceship
		           ? ((EnemySpaceship) spaceship).getType()
		           : 0);
		
		return database.update(Database.TABLE_SPACESHIPS,
		                       values,
		                       Database.SPACESHIPS_COLUMN_ID + " = " + spaceship.getId(),
		                       null);
	}
	
	/**
	 * Actions to perform when the {@link android.database.sqlite.SQLiteDatabase database} is
	 * created. Creates and initializes all tables.
	 *
	 * @param db the {@link android.database.sqlite.SQLiteDatabase database} that was created.
	 */
	
	@Override
	public void onCreate (SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_USERS);
		db.execSQL(CREATE_TABLE_WEAPONS);
		db.execSQL(CREATE_TABLE_SPACESHIPS);
	}
	
	/**
	 * Actions to perform when the {@link android.database.sqlite.SQLiteDatabase database} is
	 * updated. Removes the tables and creates new tables with the new information.
	 *
	 * @param db         the {@link android.database.sqlite.SQLiteDatabase database} that was
	 *                   updated.
	 * @param oldVersion the old version's number.
	 * @param newVersion the new version's number.
	 *
	 * @see Database#onCreate(android.database.sqlite.SQLiteDatabase)
	 */
	
	@Override
	public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_WEAPONS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SPACESHIPS);
		onCreate(db);
	}
	
	public Weapon deleteWeapon (String name) {
		Weapon res = getWeapon(name);
		database.delete(Database.TABLE_WEAPONS, Database.WEAPONS_COLUMN_NAME + " = " + name, null);
		return res;
	}
}
