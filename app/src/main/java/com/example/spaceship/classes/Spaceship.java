package com.example.spaceship.classes;

import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static android.view.View.INVISIBLE;
import static com.example.spaceship.classes.PlayerSpaceship.DEFAULT_HP;

/**
 * The class that represents all the spaceships of the game.
 * This class is a general representation and is being inherited by {@link
 * com.example.spaceship.classes.PlayerSpaceship PlayerSpaceship} for the player's spaceship and
 * {@link com.example.spaceship.classes.EnemySpaceship EnemySpaceship} for the enemy spaceships.
 */

public class Spaceship {
	
	/**
	 * The spaceship's {@link android.graphics.drawable.Drawable image}.
	 */
	
	protected Drawable image;
	
	/**
	 * The spaceship's current explosion {@link android.graphics.drawable.Drawable image}.
	 */
	
	protected Drawable explosion;
	
	/**
	 * The spaceship's current HP.
	 */
	
	protected int hp;
	
	/**
	 * The spaceship's total amount of HP.
	 */
	
	protected int hpTotal;
	
	/**
	 * The spaceship's {@link android.graphics.RectF healthbar}.
	 */
	
	protected RectF healthbar;
	
	/**
	 * The spaceship's {@link android.widget.TextView HP text}.
	 */
	
	protected TextView hpText;
	
	/**
	 * The spaceship's {@link android.widget.TextView HP text}'s {@link
	 * android.widget.RelativeLayout layout}.
	 */
	
	protected RelativeLayout hpLayout;
	
	/**
	 * The spaceship's Database ID.
	 */
	
	protected long id;
	
	/**
	 * The spaceship's explosion stage.
	 */
	
	private int stage;
	
	/**
	 * Indicates whether a spaceship is currently exploding or not.
	 * Updates with the calls for any method that updates the
	 * {@link com.example.spaceship.classes.Spaceship#explosion}
	 * field.
	 *
	 * @see com.example.spaceship.classes.Spaceship#explosion
	 * @see com.example.spaceship.classes.Spaceship#setExplosion(int)
	 * @see com.example.spaceship.classes.Spaceship#setExplosion(android.graphics.drawable.Drawable)
	 * @see com.example.spaceship.classes.Spaceship#setExplosion(android.graphics.drawable.Drawable,
	 *        int)
	 * @see Spaceship#updateExplosion()
	 */
	
	private boolean exploding;
	
	/**
	 * The default constructor that sets the spaceship's fields to their default values.
	 */
	
	public Spaceship () {
		this.id        = -1;
		this.image     = null;
		this.hp        = -1;
		this.hpTotal   = -1;
		this.healthbar = new RectF();
	}
	
	/**
	 * A minimal constructor that sets all the fields to their default values and the image to the
	 * provided image.
	 *
	 * @param image the provided image.
	 */
	
	public Spaceship (Drawable image) {
		this.id        = -1;
		this.image     = image;
		this.hp        = -1;
		this.hpTotal   = -1;
		this.healthbar = new RectF();
		initHealthbar();
	}
	
	/**
	 * A method that initializes the Spaceship's healthbar with relative coordinates and size to
	 * the current spaceship's position and size.
	 */
	
	public void initHealthbar () {
		Rect bounds = this.image.copyBounds();
		int width = bounds.right - bounds.left;
		this.healthbar.set((bounds.left + ((float) width / 2)) - 75,
		                   bounds.bottom + 25,
		                   (bounds.right - ((float) width / 2)) + 75,
		                   bounds.bottom + 50);
	}
	
	/**
	 * A constructor used by the Database that is based on the default fields of the spaceship
	 * based
	 * on the type.
	 * If {@code type <= 0}, the constructor uses the
	 * {@link com.example.spaceship.classes.PlayerSpaceship's}
	 * default values, else, the constructor uses the
	 * {@link com.example.spaceship.classes.EnemySpaceship.EnemyType
	 * enemyType} coherently to the type parameter.
	 *
	 * @param id   the spaceship's Database ID.
	 * @param type the type parameter.
	 */
	
	public Spaceship (long id, int type) {
		this.id = id;
		if (this instanceof EnemySpaceship) {
			EnemySpaceship.EnemyType enemyType = EnemySpaceship.EnemyType.valueOf("ENEMY_" + type);
			this.hp      = enemyType.getHp();
			this.hpTotal = enemyType.getHp();
			((EnemySpaceship) this).setResource(enemyType.getResource());
			((EnemySpaceship) this).setRarity(enemyType.getRarity());
		} else if (this instanceof PlayerSpaceship) {
			this.hp      = DEFAULT_HP;
			this.hpTotal = DEFAULT_HP;
			((PlayerSpaceship) this).setWeapon(Weapon.WEAPON_0);
			PlayerSpaceship.setEquippedWeapon(Weapon.WEAPON_0);
			((PlayerSpaceship) this).getWeapon().setEquipped(true);
		}
	}
	
	/**
	 * A minimal constructor that is provided with an image and the HP of the spaceship. All the
	 * other fields are set to their default values.
	 *
	 * @param image the spaceship's image.
	 * @param hp    the spaceship's HP.
	 */
	
	public Spaceship (Drawable image, int hp) {
		this.id        = -1;
		this.image     = image;
		this.hp        = hp;
		this.hpTotal   = hp;
		this.healthbar = new RectF();
		initHealthbar();
	}
	
	/**
	 * A constructor that is provided with an image, the current HP and the total HP of the
	 * spaceship. All the other fields are set to their default values.
	 *
	 * @param image   the spaceship's image.
	 * @param hp      the spaceship's current HP.
	 * @param hpTotal the spaceship's total HP.
	 */
	
	public Spaceship (Drawable image, int hp, int hpTotal) {
		this.id        = -1;
		this.image     = image;
		this.hp        = hp;
		this.hpTotal   = hpTotal;
		this.healthbar = new RectF();
		initHealthbar();
	}
	
	/**
	 * A constructor that is provided with all the fields except for the {@link
	 * com.example.spaceship.classes.Spaceship#hpText} field, the {@link
	 * com.example.spaceship.classes.Spaceship#hpLayout} field and the {@link
	 * com.example.spaceship.classes.Spaceship#explosion} field.
	 *
	 * @param image     the spaceship's image.
	 * @param hp        the spaceship's current HP.
	 * @param hpTotal   the spaceship's total HP.
	 * @param healthbar the spaceship's {@link android.graphics.RectF healthbar}.
	 */
	
	public Spaceship (Drawable image, int hp, int hpTotal, RectF healthbar) {
		this.id        = -1;
		this.image     = image;
		this.hp        = hp;
		this.hpTotal   = hpTotal;
		this.healthbar = healthbar;
	}
	
	/**
	 * A constructor provided with all the fields except for the {@link
	 * com.example.spaceship.classes.Spaceship#explosion} field.
	 *
	 * @param image     the spaceship's image.
	 * @param hp        the spaceship's current HP.
	 * @param hpTotal   the spaceship's total HP.
	 * @param healthbar the spaceship's {@link android.graphics.RectF healthbar}.
	 * @param hpText    the spaceship's {@link android.widget.TextView hpText}.
	 * @param hpLayout  the spaceship's {@link android.widget.RelativeLayout hpLayout}.
	 */
	
	public Spaceship (Drawable image, int hp, int hpTotal, RectF healthbar, TextView hpText,
	                  RelativeLayout hpLayout) {
		this.id        = -1;
		this.image     = image;
		this.hp        = hp;
		this.hpTotal   = hpTotal;
		this.healthbar = healthbar;
		this.hpText    = hpText;
		this.hpLayout  = hpLayout;
	}
	
	/**
	 * A constructor provided with all the fields of Spaceship except for the {@link
	 * com.example.spaceship.classes.Spaceship#id Database ID} field.
	 *
	 * @param image     the spaceship's image.
	 * @param explosion the spaceship's explosion.
	 * @param hp        the spaceship's current HP.
	 * @param hpTotal   the spaceship's total HP.
	 * @param healthbar the spaceship's {@link android.graphics.RectF healthbar}.
	 * @param hpText    the spaceship's {@link android.widget.TextView hpText}.
	 * @param hpLayout  the spaceship's {@link android.widget.RelativeLayout hpLayout}.
	 */
	
	public Spaceship (Drawable image, Drawable explosion, int hp, int hpTotal, RectF healthbar,
	                  TextView hpText, RelativeLayout hpLayout) {
		this.id        = -1;
		this.image     = image;
		this.explosion = explosion;
		this.hp        = hp;
		this.hpTotal   = hpTotal;
		this.healthbar = healthbar;
		this.hpText    = hpText;
		this.hpLayout  = hpLayout;
	}
	
	/**
	 * Returns the spaceship's image.
	 *
	 * @return the spaceship's image.
	 *
	 * @see com.example.spaceship.classes.Spaceship#image
	 */
	
	public Drawable getImage () {
		return image;
	}
	
	/**
	 * Sets the spaceship's image to the new image.
	 *
	 * @param image the new image.
	 *
	 * @see com.example.spaceship.classes.Spaceship#image
	 */
	
	public void setImage (Drawable image) {
		this.image = image;
	}
	
	/**
	 * Returns the spaceship's current HP.
	 *
	 * @return the spaceship's current HP.
	 *
	 * @see com.example.spaceship.classes.Spaceship#hp
	 */
	
	public int getHp () {
		return hp;
	}
	
	/**
	 * Sets the spaceship's current HP to the new current HP.
	 *
	 * @param hp the new current HP.
	 *
	 * @see com.example.spaceship.classes.Spaceship#hp
	 */
	
	public void setHp (int hp) {
		this.hp = hp;
	}
	
	/**
	 * Updates the healthbar's shape to match the spaceship's current HP.
	 *
	 * @see com.example.spaceship.classes.Spaceship#healthbar
	 * @see com.example.spaceship.classes.Spaceship#hp
	 * @see com.example.spaceship.classes.Spaceship#hpTotal
	 */
	
	public void updateHealthbar () {
		this.healthbar.right = this.healthbar.left +
		                       (((float) this.hp / (float) this.hpTotal) * this.healthbar.width());
	}
	
	/**
	 * Returns the spaceship's total HP.
	 *
	 * @return the spaceship's total HP.
	 *
	 * @see com.example.spaceship.classes.Spaceship#hpTotal
	 */
	
	public int getHpTotal () {
		return hpTotal;
	}
	
	/**
	 * Sets the spaceship's total HP to the new total HP.
	 *
	 * @param hpTotal the new total HP.
	 *
	 * @see com.example.spaceship.classes.Spaceship#hpTotal
	 */
	
	public void setHpTotal (int hpTotal) {
		this.hpTotal = hpTotal;
	}
	
	/**
	 * Returns the spaceship's {@link android.graphics.RectF healthbar}.
	 *
	 * @return the spaceship's {@link android.graphics.RectF healthbar}.
	 */
	
	public RectF getHealthbar () {
		return healthbar;
	}
	
	/**
	 * Sets the spaceship's {@link android.graphics.RectF healthbar} to the new {@link
	 * android.graphics.RectF healthbar}.
	 *
	 * @param healthbar the new {@link android.graphics.RectF healthbar}.
	 *
	 * @see com.example.spaceship.classes.Spaceship#healthbar
	 */
	
	public void setHealthbar (RectF healthbar) {
		this.healthbar = healthbar;
	}
	
	/**
	 * Reduces the spaceship's current HP by a specific amount.
	 *
	 * @param damage the amount of HP to reduce.
	 *
	 * @return returns the updated current HP.
	 *
	 * @see com.example.spaceship.classes.Spaceship#hp
	 */
	
	public int reduceHP (int damage) {
		if (hp <= damage) {
			this.hp = 0;
		} else
			this.hp -= damage;
		return this.hp;
	}
	
	/**
	 * Returns the {@link java.lang.String string} form of the spaceship's current HP.
	 *
	 * @return the {@link java.lang.String string} form of the spaceship's current HP.
	 *
	 * @see com.example.spaceship.classes.Spaceship#hp
	 */
	
	public String getHPString () {
		return this.hp + "/" + this.hpTotal + " HP";
	}
	
	/**
	 * Returns the spaceship's {@link android.widget.TextView hpText}.
	 *
	 * @return the spaceship's {@link android.widget.TextView hpText}.
	 *
	 * @see com.example.spaceship.classes.Spaceship#hpText
	 */
	
	public TextView getHpText () {
		return hpText;
	}
	
	/**
	 * Sets the spaceship's {@link android.widget.TextView hpText} to the new {@link
	 * android.widget.TextView hpText}.
	 *
	 * @param hpText the new {@link android.widget.TextView hpText}.
	 *
	 * @see com.example.spaceship.classes.Spaceship#hpText
	 */
	
	public void setHpText (TextView hpText) {
		this.hpText = hpText;
	}
	
	/**
	 * Returns the spaceship's {@link android.widget.RelativeLayout hpLayout}.
	 *
	 * @return the spaceship's {@link android.widget.RelativeLayout hpLayout}.
	 *
	 * @see com.example.spaceship.classes.Spaceship#hpLayout
	 */
	
	public RelativeLayout getHpLayout () {
		return hpLayout;
	}
	
	/**
	 * Sets the spaceship's {@link android.widget.RelativeLayout hpLayout} to the new {@link
	 * android.widget.RelativeLayout hpLayout}.
	 *
	 * @param hpLayout the new {@link android.widget.RelativeLayout hpLayout}.
	 *
	 * @see com.example.spaceship.classes.Spaceship#hpLayout
	 */
	
	public void setHpLayout (RelativeLayout hpLayout) {
		this.hpLayout = hpLayout;
	}
	
	/**
	 * Returns the spaceship's {@link android.graphics.drawable.Drawable explosion}.
	 *
	 * @return the spaceship's {@link android.graphics.drawable.Drawable explosion}.
	 *
	 * @see com.example.spaceship.classes.Spaceship#explosion
	 */
	
	public Drawable getExplosion () {
		return explosion;
	}
	
	/**
	 * Sets the explosion's stage to the new stage and updates the {@link
	 * com.example.spaceship.classes.Spaceship#explosion}'s image and size accordingly.
	 *
	 * @param stage the new stage.
	 *
	 * @see com.example.spaceship.classes.Spaceship#explosion
	 * @see com.example.spaceship.classes.Spaceship#stage
	 */
	
	public void setExplosion (int stage) {
		if (stage > 3 || this.explosion == null) {
			this.stage = -1;
			return;
		}
		
		this.stage     = stage;
		this.exploding = true;
		
		final Rect bounds = this.image.getBounds();
		final int width = bounds.width();
		final int height = bounds.height();
		
		switch (stage) {
			case 0:
				this.explosion.setBounds(bounds.centerX() - (width / 4),
				                         bounds.centerY() - (height / 4),
				                         bounds.centerX() + (width / 4),
				                         bounds.centerY() + height / 4);
				break;
			case 1:
				this.explosion.setBounds(bounds.centerX() - (width / 3),
				                         bounds.centerY() - (height / 3),
				                         bounds.centerX() + (width / 3),
				                         bounds.centerY() + height / 3);
				break;
			case 2:
				this.explosion.setBounds(bounds);
				break;
			case 3:
				this.image.setBounds(0, 0, 0, 0);
				this.explosion.setBounds(0, 0, 0, 0);
				this.healthbar.setEmpty();
				this.hpText.setText("");
				this.hpText.setVisibility(INVISIBLE);
				this.hpLayout.setVisibility(INVISIBLE);
				break;
		}
	}
	
	/**
	 * Sets the spaceship's {@link android.graphics.drawable.Drawable explosion} to the new {@link
	 * android.graphics.drawable.Drawable explosion}.
	 *
	 * @param explosion the new {@link android.graphics.drawable.Drawable explosion}.
	 *
	 * @see com.example.spaceship.classes.Spaceship#explosion
	 */
	
	public void setExplosion (Drawable explosion) {
		this.explosion = explosion;
		this.exploding = true;
	}
	
	/**
	 * Updates the spaceship's explosion to be in accordance to the stage.
	 *
	 * @see com.example.spaceship.classes.Spaceship#explosion
	 * @see com.example.spaceship.classes.Spaceship#stage
	 * @see com.example.spaceship.classes.Spaceship#setExplosion(int)
	 */
	
	public void updateExplosion () {
		setExplosion(stage);
		this.exploding = true;
	}
	
	/**
	 * Sets the spaceship's {@link android.graphics.drawable.Drawable explosion} to the new {@link
	 * android.graphics.drawable.Drawable explosion} and updates its size and image to be in
	 * accordance to the new stage.
	 *
	 * @param explosion the new {@link android.graphics.drawable.Drawable explosion}.
	 * @param stage     the new stage.
	 *
	 * @see com.example.spaceship.classes.Spaceship#explosion
	 * @see com.example.spaceship.classes.Spaceship#stage
	 */
	
	public void setExplosion (Drawable explosion, int stage) {
		setExplosion(explosion);
		setExplosion(stage);
		this.exploding = true;
	}
	
	/**
	 * Returns the spaceship's Database ID.
	 *
	 * @return the spaceship's Database ID.
	 *
	 * @see com.example.spaceship.classes.Spaceship#id
	 */
	
	public long getId () {
		return id;
	}
	
	/**
	 * Sets the spaceship's Database ID to the new Database ID.
	 *
	 * @param id the new Database ID.
	 *
	 * @see com.example.spaceship.classes.Spaceship#id
	 */
	
	public void setId (long id) {
		this.id = id;
	}
	
	/**
	 * Returns the value of {@link com.example.spaceship.classes.Spaceship#exploding}
	 *
	 * @return the value of {@link com.example.spaceship.classes.Spaceship#exploding}
	 *
	 * @see com.example.spaceship.classes.Spaceship#exploding
	 */
	
	public boolean isExploding () {
		return this.exploding;
	}
}

