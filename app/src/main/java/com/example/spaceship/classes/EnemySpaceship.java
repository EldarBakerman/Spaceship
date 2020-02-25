package com.example.spaceship.classes;

import androidx.annotation.DrawableRes;

import com.example.spaceship.R;

public class EnemySpaceship extends Spaceship {
	
	private int resource;
	private int rarity;
	
	public EnemySpaceship () {
		super();
	}
	
	public EnemySpaceship (EnemyType type) {
		this.hp       = type.hp;
		this.hpTotal  = type.hp;
		this.resource = type.resource;
		this.rarity   = type.rarity;
	}
	
	public @DrawableRes
	int getResource () {
		return resource;
	}
	
	public void setResource (@DrawableRes int resource) {
		this.resource = resource;
	}
	
	public int getRarity () {
		return rarity;
	}
	
	public void setRarity (int rarity) {
		this.rarity = rarity;
	}
	
	public enum EnemyType {
		// TODO: Flip vertically
		ENEMY_1(R.drawable.spaceship2, 5, 1),
		ENEMY_2(R.drawable.spaceship3, 10, 2),
		ENEMY_3(R.drawable.spaceship4, 20, 3),
		ENEMY_4(R.drawable.spaceship5, 30, 4);
		
		private @DrawableRes int resource;
		private int hp;
		private int rarity;
		
		EnemyType (@DrawableRes int resource, int hp, int rarity) {
			this.resource = resource;
			this.hp       = hp;
			this.rarity   = rarity;
		}
		
		public int getResource () {
			return resource;
		}
		
		public int getHp () {
			return hp;
		}
		
		public int getRarity () {
			return rarity;
		}
	}
}
