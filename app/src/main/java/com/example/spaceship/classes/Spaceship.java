package com.example.spaceship.classes;

import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static android.view.View.INVISIBLE;
import static com.example.spaceship.classes.PlayerSpaceship.DEFAULT_HP;

public class Spaceship {
	
	protected Drawable image, explosion;
	protected int hp, hpTotal;
	protected RectF healthbar;
	protected TextView hpText;
	protected RelativeLayout hpLayout;
	private long id;
	private int stage;
	
	public Spaceship () {
		this.id        = -1;
		this.image     = null;
		this.hp        = -1;
		this.hpTotal   = -1;
		this.healthbar = new RectF();
	}
	
	public Spaceship (Drawable image) {
		this.id        = -1;
		this.image     = image;
		this.hp        = -1;
		this.hpTotal   = -1;
		this.healthbar = new RectF();
		initHealthbar();
	}
	
	public void initHealthbar () {
		Rect bounds = this.image.copyBounds();
		int width = bounds.right - bounds.left;
		this.healthbar.set((bounds.left + ((float) width / 2)) - 75,
		                   bounds.bottom + 25,
		                   (bounds.right - ((float) width / 2)) + 75,
		                   bounds.bottom + 50);
	}
	
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
	
	public Spaceship (Drawable image, int hp) {
		this.id        = -1;
		this.image     = image;
		this.hp        = hp;
		this.hpTotal   = hp;
		this.healthbar = new RectF();
		initHealthbar();
	}
	
	public Spaceship (Drawable image, int hp, int hpTotal) {
		this.id        = -1;
		this.image     = image;
		this.hp        = hp;
		this.hpTotal   = hpTotal;
		this.healthbar = new RectF();
		initHealthbar();
	}
	
	public Spaceship (Drawable image, int hp, int hpTotal, RectF healthbar) {
		this.id        = -1;
		this.image     = image;
		this.hp        = hp;
		this.hpTotal   = hpTotal;
		this.healthbar = healthbar;
	}
	
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
	
	public Drawable getImage () {
		return image;
	}
	
	public void setImage (Drawable image) {
		this.image = image;
	}
	
	public int getHp () {
		return hp;
	}
	
	public void setHp (int hp) {
		this.hp = hp;
	}
	
	public void updateHealthbar () {
		this.healthbar.right = this.healthbar.left +
		                       (((float) this.hp / (float) this.hpTotal) * this.healthbar.width());
	}
	
	public int getHpTotal () {
		return hpTotal;
	}
	
	public void setHpTotal (int hpTotal) {
		this.hpTotal = hpTotal;
	}
	
	public RectF getHealthbar () {
		return healthbar;
	}
	
	public void setHealthbar (RectF healthbar) {
		this.healthbar = healthbar;
	}
	
	public int reduceHP (int damage) {
		if (hp <= damage) {
			this.hp = 0;
		} else
			this.hp -= damage;
		return this.hp;
	}
	
	public String getHPString () {
		return this.hp + "/" + this.hpTotal + " HP";
	}
	
	public TextView getHpText () {
		return hpText;
	}
	
	public void setHpText (TextView hpText) {
		this.hpText = hpText;
	}
	
	public RelativeLayout getHpLayout () {
		return hpLayout;
	}
	
	public void setHpLayout (RelativeLayout hpLayout) {
		this.hpLayout = hpLayout;
	}
	
	public Drawable getExplosion () {
		return explosion;
	}
	
	public void setExplosion (Drawable explosion) {
		this.explosion = explosion;
	}
	
	public void setExplosion (int stage) {
		if (stage > 3 || this.explosion == null) {
			this.stage = -1;
			return;
		}
		
		this.stage = stage;
		
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
	
	public void updateExplosion () {
		setExplosion(stage);
	}
	
	public void setExplosion (Drawable explosion, int stage) {
		setExplosion(explosion);
		setExplosion(stage);
	}
	
	public long getId () {
		return id;
	}
	
	public void setId (long id) {
		this.id = id;
	}
}
