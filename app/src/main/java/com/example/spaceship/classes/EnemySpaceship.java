package com.example.spaceship.classes;

import androidx.annotation.DrawableRes;
import androidx.annotation.IntRange;

import com.example.spaceship.R;

/**
 * This class represents the enemy spaceship and inherits from the {@link
 * com.example.spaceship.classes.Spaceship Spaceship} class.
 * <p>
 * It also uses the {@link com.example.spaceship.classes.EnemySpaceship.EnemyType EnemyType} enum in
 * order to store the different types of enemy spaceships.
 * </p>
 *
 * @see com.example.spaceship.classes.Spaceship
 * @see com.example.spaceship.classes.EnemySpaceship.EnemyType
 */

public class EnemySpaceship extends Spaceship {
	
	/**
	 * The resource of the spaceship's image.
	 */
	
	private int resource;
	
	/**
	 * The frequency in which the spaceship may appear. The higher is the rarity, the lower is the
	 * chance for it to appear.
	 */
	
	private int rarity;
	
	/**
	 * The number of the {@link com.example.spaceship.classes.EnemySpaceship.EnemyType EnemyType}
	 * that this enemy
	 * is using. This field is used mainly by the Database.
	 */
	
	private int type;
	
	/**
	 * The default constructor that calls the default constructor of the {@link
	 * com.example.spaceship.classes.Spaceship Spaceship} class.
	 */
	
	public EnemySpaceship () {
		super();
	}
	
	/**
	 * A constructor that uses the {@link com.example.spaceship.classes.EnemySpaceship.EnemyType
	 * EnemyType} class in order to generate a ready-made enemy.
	 *
	 * @param type the {@link com.example.spaceship.classes.EnemySpaceship.EnemyType type}.
	 *
	 * @see com.example.spaceship.classes.EnemySpaceship.EnemyType
	 */
	
	public EnemySpaceship (EnemyType type) {
		super();
		this.hp       = type.hp;
		this.hpTotal  = type.hp;
		this.resource = type.resource;
		this.rarity   = type.rarity;
		this.type     = Integer.parseInt(type.name().substring(type.name().length() - 1));
	}
	
	/**
	 * A constructor that is used by the Database and includes the Database ID and the type.
	 *
	 * @param id   the spaceship's Database ID.
	 * @param type the {@link com.example.spaceship.classes.EnemySpaceship.EnemyType type} number.
	 *
	 * @see com.example.spaceship.classes.Spaceship
	 * @see com.example.spaceship.classes.EnemySpaceship.EnemyType
	 * @see com.example.spaceship.classes.DatabaseOpenHelper
	 */
	
	public EnemySpaceship (long id, int type) {
		super(id, type);
	}
	
	/**
	 * Returns the spaceship's image resource.
	 *
	 * @return the spaceship's image resource.
	 *
	 * @see com.example.spaceship.classes.EnemySpaceship#resource
	 */
	
	public @DrawableRes
	int getResource () {
		return resource;
	}
	
	/**
	 * Sets the spaceship's image resource to the new image resource.
	 *
	 * @param resource the new image resource.
	 *
	 * @see com.example.spaceship.classes.EnemySpaceship#resource
	 */
	
	public void setResource (@DrawableRes int resource) {
		this.resource = resource;
	}
	
	/**
	 * Returns the spaceship's rarity.
	 *
	 * @return the spaceship's rarity.
	 *
	 * @see com.example.spaceship.classes.EnemySpaceship#rarity
	 */
	
	public int getRarity () {
		return rarity;
	}
	
	/**
	 * Sets the spaceship's rarity to the new rarity.
	 *
	 * @param rarity the new rarity.
	 *
	 * @see com.example.spaceship.classes.EnemySpaceship#rarity
	 */
	
	public void setRarity (int rarity) {
		this.rarity = rarity;
	}
	
	/**
	 * Returns spaceship's the {@link com.example.spaceship.classes.EnemySpaceship.EnemyType type}
	 * number.
	 * Must be in the range of 1 to 4.
	 *
	 * @return spaceship's the {@link com.example.spaceship.classes.EnemySpaceship.EnemyType type}
	 * 		number.
	 *
	 * @see com.example.spaceship.classes.EnemySpaceship#type
	 * @see com.example.spaceship.classes.EnemySpaceship.EnemyType
	 */
	
	public int getType () {
		return type;
	}
	
	/**
	 * Sets the spaceship's {@link com.example.spaceship.classes.EnemySpaceship.EnemyType type}
	 * number to the new {@link com.example.spaceship.classes.EnemySpaceship.EnemyType type}
	 * number.
	 *
	 * @param type the new {@link com.example.spaceship.classes.EnemySpaceship.EnemyType type}
	 *             number. Must be in the range of 1 to 4.
	 */
	
	public void setType (@IntRange(from = 1, to = 4) int type) {
		this.type = type;
	}
	
	/**
	 * An enum that holds default values for 4 different types of an enemy.
	 *
	 * @see com.example.spaceship.classes.EnemySpaceship#type
	 */
	
	public enum EnemyType {
		// TODO: Flip vertically
		ENEMY_1(R.drawable.spaceship2, 5, 1),
		ENEMY_2(R.drawable.spaceship3, 10, 2), ENEMY_3(R.drawable.spaceship4, 15, 3);
		
		/**
		 * The spaceship's image resource.
		 *
		 * @see com.example.spaceship.classes.EnemySpaceship#resource
		 */
		
		private @DrawableRes int resource;
		
		/**
		 * The spaceship's total (and current) hp.
		 *
		 * @see com.example.spaceship.classes.EnemySpaceship#hp
		 * @see com.example.spaceship.classes.EnemySpaceship#hpTotal
		 */
		
		private int hp;
		
		/**
		 * The spaceship's rarity.
		 *
		 * @see com.example.spaceship.classes.EnemySpaceship#rarity
		 */
		
		private int rarity;
		
		/**
		 * A constructor that requires all three fields.
		 *
		 * @param resource the type's image resource.
		 * @param hp       the type's total and current HP.
		 * @param rarity   the type's rarity.
		 */
		
		EnemyType (@DrawableRes int resource, int hp, int rarity) {
			this.resource = resource;
			this.hp       = hp;
			this.rarity   = rarity;
		}
		
		/**
		 * Returns the type's image resource.
		 *
		 * @return the type's image resource.
		 *
		 * @see com.example.spaceship.classes.EnemySpaceship.EnemyType#resource
		 * @see com.example.spaceship.classes.EnemySpaceship#resource
		 */
		
		public int getResource () {
			return resource;
		}
		
		/**
		 * Returns the type's total and current HP.
		 *
		 * @return the type's total and current HP.
		 *
		 * @see com.example.spaceship.classes.EnemySpaceship.EnemyType#hp
		 * @see com.example.spaceship.classes.EnemySpaceship#hp
		 * @see com.example.spaceship.classes.EnemySpaceship#hpTotal
		 */
		
		public int getHp () {
			return hp;
		}
		
		/**
		 * Returns the type's rarity.
		 *
		 * @return the type's rarity.
		 *
		 * @see com.example.spaceship.classes.EnemySpaceship.EnemyType#rarity
		 * @see com.example.spaceship.classes.EnemySpaceship#rarity
		 */
		
		public int getRarity () {
			return rarity;
		}
	}
}
