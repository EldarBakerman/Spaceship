package com.example.spaceship.classes;

import androidx.annotation.DrawableRes;

import com.example.spaceship.R;

public class EnemySpaceship extends Spaceship {
	
	private int difficulty;
	private int resource;
	
	public EnemySpaceship () {
		super();
		initHealthbar();
		this.difficulty = 1;
	}
	
	public EnemySpaceship (EnemyType type) {
		this.hp         = type.hp;
		this.hpTotal    = type.hp;
		this.difficulty = type.difficulty;
		this.resource   = type.resource;
	}
	
	public int getDifficulty () {
		return difficulty;
	}
	
	public void setDifficulty (int difficulty) {
		this.difficulty = difficulty;
	}
	
	public int getResource () {
		return resource;
	}
	
	public void setResource (int resource) {
		this.resource = resource;
	}
	
	public enum EnemyType {
		// TODO: Flip vertically
		ENEMY_1(R.drawable.spaceship2, 5, 1),
		ENEMY_2(R.drawable.spaceship3, 10, 2),
		ENEMY_3(R.drawable.spaceship4, 20, 3),
		ENEMY_4(R.drawable.spaceship5, 30, 4);
		
		@DrawableRes int resource;
		int hp;
		int difficulty;
		
		EnemyType (@DrawableRes int resource, int hp, int difficulty) {
			this.resource   = resource;
			this.hp         = hp;
			this.difficulty = difficulty;
		}
		
		public @DrawableRes
		int getResoure () {
			return resource;
		}
		
		public int getHp () {
			return hp;
		}
		
		public int getDifficulty () {
			return difficulty;
		}
	}
}
