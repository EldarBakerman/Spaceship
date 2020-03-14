package com.example.spaceship.views;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.spaceship.R;
import com.example.spaceship.activities.GameActivity;
import com.example.spaceship.activities.SplashActivity;
import com.example.spaceship.classes.EnemySpaceship;
import com.example.spaceship.classes.PlayerSpaceship;
import com.example.spaceship.classes.Spaceship;
import com.example.spaceship.classes.User;

import java.lang.reflect.Field;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.spaceship.classes.EnemySpaceship.EnemyType;

/**
 * Game's Custom View to be shown at {@link com.example.spaceship.activities.GameActivity
 * GameActivity}
 */

public class GameView extends View {
	
	//TODO: Reorganize everything
	
	TextView pointsText;
	// TODO: Points
	private int points = 0;
	private PlayerSpaceship player;
	private Drawable laser;
	private TextView timerText;
	private Drawable dPlayer;
	// Boolean Checkers
	private boolean start = true;
	private boolean timerTick = false;
	private boolean tapped = false;
	private boolean laserAnimating = false;
	private boolean miss = false;
	private boolean lost = false;
	private boolean win = false;
	private EnemySpaceship[] enemies;
	private EnemySpaceship hit = null;
	private LinearLayout infoLayout;
	private Paint hbStroke;
	private Paint hbFill;
	//TODO: User & Database
	private User user;
	
	
	/**
	 * Constructor for {@link com.example.spaceship.views.GameView GameView}
	 * Initializes {@link com.example.spaceship.classes.Spaceship spaceships} and the entire game
	 * mechanics.
	 *
	 * @param context {@link Activity activity} of the game
	 */
	
	public GameView (Context context) {
		super(context);
		
		Activity activity = (Activity) context;
		
		// User
		
		user = SplashActivity.database.getUser(activity.getIntent().getLongExtra("userId", -1));
		
		// Player
		player   = new PlayerSpaceship(getResources().getDrawable(R.drawable.spaceship1, null));
		hbFill   = new Paint();
		hbStroke = new Paint();
		
		// Points, Timer & Level
		
		// Layout
		
		infoLayout = new LinearLayout(context);
		infoLayout.setOrientation(LinearLayout.HORIZONTAL);
		infoLayout.setHorizontalGravity(Gravity.CENTER_HORIZONTAL);
		infoLayout.setVisibility(VISIBLE);
		
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
		                                       LayoutParams.WRAP_CONTENT);
		params.setMargins(0, 100, 0, 0);
		
		// Timer TextView
		
		timerText = new TextView(context);
		timerText.setGravity(Gravity.CENTER);
		timerText.setTextAlignment(TEXT_ALIGNMENT_CENTER);
		timerText.setPadding(30, 0, 0, 0);
		timerText.setVisibility(VISIBLE);
		timerText.setTextSize(22);
		timerText.setText(String.format(Locale.ENGLISH, "%d", 30));
		timerText.setTextColor(Color.WHITE);
		timerText.setTypeface(null, Typeface.BOLD);
		timerText.setLayoutParams(params);
		
		// Points TextView
		
		pointsText = new TextView(context);
		pointsText.setGravity(Gravity.CENTER);
		setTextAlignment(TEXT_ALIGNMENT_CENTER);
		setPadding(0, 0, 30, 0);
		setVisibility(VISIBLE);
		pointsText.setTextSize(22);
		pointsText.setText(String.format(Locale.ENGLISH, "%d", 0));
		pointsText.setTextColor(Color.WHITE);
		pointsText.setTypeface(null, Typeface.BOLD);
		pointsText.setLayoutParams(params);
		
		infoLayout.addView(timerText);
		infoLayout.addView(pointsText);
		
		// Player & Player Healthbar
		
		dPlayer = player.getImage();
		player.getWeapon().setImage(getResources().getDrawable(R.drawable.red_laser, null));
		
		// Laser
		laser = player.getWeapon().getImage();
		
		// Timer Method
		
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run () {
				if (lost || win) {
					timer.cancel();
					postInvalidate();
					return;
				}
				
				int timeLeft = Integer.parseInt(timerText.getText().toString());
				if (timeLeft <= 0) {
					explode(player);
					invalidate();
					timer.cancel();
				} else {
					activity.runOnUiThread(() -> {
						timerText.setText(String.format(Locale.ENGLISH, "%d", timeLeft - 1));
						timerTick = true;
						postInvalidate();
					});
				}
			}
		}, 1000, 1000);
	}
	
	/**
	 * Explodes a spaceship.
	 * If the Spaceship is an enemy, increases the user's points.
	 *
	 * @param spaceship the spaceship to explode.
	 */
	
	private void explode (Spaceship spaceship) {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			int timerCount = 0;
			
			@Override
			public void run () {
				if (timerCount < 3)
					spaceship.setExplosion(getRes("explosion" + timerCount), timerCount);
				else {
					spaceship.setExplosion(timerCount);
					timer.cancel();
					if (spaceship instanceof PlayerSpaceship) {
						lost = true;
						if (user.getHighscore() < points)
							user.setHighscore(points);
					}
					hit = null;
					invalidate();
				}
				timerCount++;
				invalidate();
			}
		}, 0, 300);
		
		if (spaceship instanceof EnemySpaceship) {
			user.setPoints(user.getPoints() + 5);
			points += 5;
			pointsText.setText(String.format(Locale.ENGLISH,
			                                 "%s%d",
			                                 getContext().getString(R.string.Points),
			                                 points));
		}
	}
	
	/**
	 * Custom method that shortcuts the usage of the {@link android.view.View#getResources()}
	 * method.
	 * Gets a drawable value using a String.
	 *
	 * @param id the name of the drawable
	 *
	 * @return the drawable with the corresponding name
	 */
	
	private Drawable getRes (String id) {
		try {
			Field idField = R.drawable.class.getDeclaredField(id);
			return getResources().getDrawable(idField.getInt(idField), null);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			return null;
		}
	}
	
	/**
	 * Restarts if the game is over. Else, shoots a laser from the {@link
	 * com.example.spaceship.classes.PlayerSpaceship spaceship's} gun.
	 *
	 * @return a boolean that is generated by the system.
	 */
	
	@Override
	public boolean performClick () {
		tapped = true;
		if (win || lost)
			launchGame();
		postInvalidate();
		return super.performClick();
	}
	
	/**
	 * {@link View#onDraw(Canvas) onDraw} method implemented from {@link View view} that draws the
	 * entire game.
	 *
	 * @param canvas canvas to draw on
	 */
	
	@Override
	protected void onDraw (Canvas canvas) {
		super.onDraw(canvas);
		
		if (lost) {
			start = timerTick = tapped = laserAnimating = miss = false;
			loseText(canvas);
			return;
		}
		
		if (win)
			launchGame();
		
		// Animation
		if (start) {
			setBackgroundColor(Color.BLACK);
			dPlayer.setBounds(getWidth() / 2 - 150,
			                  getHeight() - 300,
			                  getWidth() / 2 + 150,
			                  getHeight() - 100);
			start = false;
			generateEnemies(new Random().nextInt(6) + 1);
		}
		
		for (EnemySpaceship enemy : enemies) {
			enemy.getImage().draw(canvas);
			initHealthbar(canvas, enemy);
		}
		
		dPlayer.draw(canvas);
		initHealthbar(canvas, player);
		
		infoLayout.measure(getWidth(), getHeight());
		infoLayout.layout(0, 0, getWidth(), getHeight());
		infoLayout.draw(canvas);
		
		
		if (timerTick) {
			animateSpaceship(Integer.parseInt(timerText.getText().toString()) % 2 == 0);
			timerTick = false;
		}
		
		if (tapped) {
			initLaser();
			animateLaser();
			tapped = false;
		}
		
		if (laserAnimating) {
			laser.draw(canvas);
			laserAnimating = false;
		} else if (miss) {
			miss = false;
			if (player.reduceHP(5) <= 0)
				explode(player);
		} else if (hit != null) {
			if (hit.reduceHP(player.getWeapon().getDamage()) <= 0)
				explode(hit);
			hit = null;
		}
		
		for (Spaceship enemy : enemies)
			if (enemy.isExploding() && !enemy.getExplosion().getBounds().isEmpty()) {
				enemy.updateExplosion();
				enemy.getExplosion().draw(canvas);
			}
		
		if (player.getExplosion() != null && !player.getExplosion().getBounds().isEmpty()) {
			player.updateExplosion();
			player.getExplosion().draw(canvas);
		}
		
		boolean noEnemies = false;
		for (EnemySpaceship enemy : enemies) {
			if (enemy.getImage().getBounds().isEmpty())
				noEnemies = true;
			else {
				noEnemies = false;
				break;
			}
		}
		
		if (noEnemies)
			win = true;
		
	}
	
	/**
	 * Launches a new game, if won, passes the points value to the new game and updates the
	 * highscore of the {@link com.example.spaceship.classes.User user}.
	 */
	
	private void launchGame () {
		Activity activity = ((Activity) getContext());
		Intent intent = new Intent(activity, GameActivity.class);
		
		if (win)
			intent.putExtra("points", user.getPoints());
		
		activity.startActivity(intent);
		activity.finish();
	}
	
	/**
	 * The text that announces that the game is over and that the user has lost.
	 *
	 * @param canvas the canvas on which the text is drawn.
	 */
	
	private void loseText (Canvas canvas) {
		TextPaint losePaint = new TextPaint();
		losePaint.setColor(Color.RED);
		losePaint.setTextSize(36);
		losePaint.setTextAlign(Paint.Align.CENTER);
		losePaint.setTypeface(Typeface.DEFAULT_BOLD);
		
		canvas.drawText("Game Over!", (float) getWidth() / 2, (float) getHeight() / 2, losePaint);
		
		restartText(canvas);
	}
	
	/**
	 * Creates {@link com.example.spaceship.classes.EnemySpaceship enemy spaceships} by randomizing
	 * the {@link EnemyType} of each spaceship in
	 * accordance to the chances pre-set by the rarity of the
	 * {@link EnemyType
	 * enemy types}.
	 * Generates automatic coordinates for each spaceship in accordance to one of two pre-set
	 * patterns chosen randomly.
	 *
	 * @param amount the amount of spaceships to generate
	 *
	 * @throws AssertionError an error thrown if the amount is smaller than 1 or bigger than 6.
	 */
	
	private void generateEnemies (int amount) throws AssertionError {
		if (amount > 6 || amount < 1)
			throw new AssertionError("amount is invalid");
		
		enemies = new EnemySpaceship[amount];
		
		/* Chances array in order to generate chances in accordance to the rarity of the EnemyType
		 * by creating an array of numbers representing the number of EnemyType.
		 * Each number is appearing (5 - rarity) times in the array, then a number is chosen
		 * randomly using the Random class. Since the appearance of some types in the array is
		 * more frequent than others, it creates a chance in accordance to the rarity of the type.
		 */
		
		int[] chances = new int[0];
		
		
		for (int i = 1; i < 4; i++) {
			
			// Get EnemyType by String using the i of the for loop
			final EnemyType enemyType = EnemyType.valueOf(String.format(Locale.ENGLISH,
			                                                            "ENEMY_%d",
			                                                            i));
			int oldLength = chances.length;
			
			// Create a temporary array to hold the previous values (to save from being nullified)
			int[] oldChances = new int[oldLength];
			System.arraycopy(chances, 0, oldChances, 0, oldLength);
			
			// Increase the length of the array by (5 - rarity)
			chances = new int[oldLength + (5 - enemyType.getRarity())];
			
			// Retrieve the previous values to the new array
			System.arraycopy(oldChances, 0, chances, 0, oldLength);
			
			// Add the new values to the new array (the EnemyType number)
			for (int j = oldLength; j < chances.length; j++) {
				chances[j] = i;
			}
		}
		
		// Use Random to set a pattern of coordinates for the ships (the amount of patterns is 2)
		Random rPattern = new Random();
		int pattern = rPattern.nextInt(2);
		
		for (int i = 0; i < amount; i++) {
			
			// Use Random to generate the wanted amount of enemies randomly, in accordance to
			// rarity
			Random random = new Random();
			enemies[i] = new EnemySpaceship(EnemyType.valueOf(String.format(Locale.ENGLISH,
			                                                                "ENEMY_%d",
			                                                                chances[random.nextInt(
					                                                                chances.length)])));
			enemies[i].setImage(getResources().getDrawable(enemies[i].getResource(), null));
			
			// If method was called from constructor - exit (can't set coordinates if canvas
			// hasn't loaded yet)
			if (start)
				return;
			
			final int LEFT_EDGE = 0;
			final int LEFT_MARGIN_EDGE = LEFT_EDGE + 100;
			final int RIGHT_EDGE = getWidth();
			final int RIGHT_MARGIN_EDGE = RIGHT_EDGE - 100;
			final int TOP_EDGE = 0;
			final int TOP_MARGIN_EDGE = TOP_EDGE + 200;
			final int WIDTH = 200;
			final int HEIGHT = 200;
			final int CENTER_LEFT = getWidth() / 2 - WIDTH / 2;
			final int CENTER_RIGHT = getWidth() / 2 + WIDTH / 2;
			final int BOTTOM_MARGIN = TOP_MARGIN_EDGE + HEIGHT;
			final int RIGHT_MARGIN = LEFT_MARGIN_EDGE + WIDTH;
			final Drawable image = enemies[i].getImage();
			final Rect enemyBounds = i >= 2 ? enemies[i - 2].getImage().getBounds() : new Rect();
			final int PREV_BOTTOM_MARGIN = enemyBounds.bottom + 100;
			
			Log.d("GameView#generate", String.format("Pattern: %d ; Amount: %d", pattern, amount));
			
			/* Pattern 0 (enemy spaceships represented by 0):
			 * If amount is odd
			 * 0       0
			 * 0       0
			 *     0
			 *
			 * If amount is even
			 * 0       0
			 * 0       0
			 */
			
			if (pattern == 0) {
				if (amount % 2 == 0 || i < amount - 1) {
					
					
					if (i % 2 == 0)
						image.setBounds(LEFT_MARGIN_EDGE,
						                i == 0 ? TOP_MARGIN_EDGE : PREV_BOTTOM_MARGIN,
						                RIGHT_MARGIN,
						                i == 0 ? BOTTOM_MARGIN : PREV_BOTTOM_MARGIN + HEIGHT);
					else
						image.setBounds(RIGHT_MARGIN_EDGE - WIDTH,
						                i == 1 ? TOP_MARGIN_EDGE : PREV_BOTTOM_MARGIN,
						                RIGHT_MARGIN_EDGE,
						                i == 1 ? BOTTOM_MARGIN : PREV_BOTTOM_MARGIN + HEIGHT);
				} else
					image.setBounds(CENTER_LEFT,
					                i >= 2 ? PREV_BOTTOM_MARGIN : TOP_MARGIN_EDGE,
					                CENTER_RIGHT,
					                i >= 2 ? PREV_BOTTOM_MARGIN + HEIGHT : BOTTOM_MARGIN);
			} else if (pattern == 1) {
				
				/*
				 * Pattern 1 (enemy spaceships represented by 0):
				 * If amount is odd (above 3 - below 3 is one line of enemies)
				 * 0 0 0
				 * 0   0
				 *
				 * If amount is even (above 3 - below 3 is one line of enemies)
				 * 0 0 0
				 *   0
				 */
				
				switch (i) {
					case 0:
						image.setBounds(LEFT_MARGIN_EDGE,
						                TOP_MARGIN_EDGE,
						                RIGHT_MARGIN,
						                BOTTOM_MARGIN);
						break;
					case 1:
						if (amount == 2)
							image.setBounds(RIGHT_MARGIN_EDGE - WIDTH,
							                TOP_MARGIN_EDGE,
							                RIGHT_MARGIN_EDGE,
							                BOTTOM_MARGIN);
						else
							image.setBounds(CENTER_LEFT,
							                TOP_MARGIN_EDGE,
							                CENTER_RIGHT,
							                BOTTOM_MARGIN);
						break;
					case 2:
						image.setBounds(RIGHT_MARGIN_EDGE - WIDTH,
						                TOP_MARGIN_EDGE,
						                RIGHT_MARGIN_EDGE,
						                BOTTOM_MARGIN);
						break;
					case 3:
						image.setBounds(LEFT_MARGIN_EDGE,
						                PREV_BOTTOM_MARGIN,
						                RIGHT_MARGIN,
						                PREV_BOTTOM_MARGIN + HEIGHT);
					case 4:
						if (amount == 5)
							image.setBounds(RIGHT_MARGIN_EDGE - WIDTH,
							                PREV_BOTTOM_MARGIN,
							                RIGHT_MARGIN_EDGE,
							                PREV_BOTTOM_MARGIN + HEIGHT);
						else
							image.setBounds(CENTER_LEFT,
							                PREV_BOTTOM_MARGIN,
							                CENTER_RIGHT,
							                PREV_BOTTOM_MARGIN + HEIGHT);
						break;
					case 5:
						image.setBounds(RIGHT_MARGIN_EDGE - WIDTH,
						                PREV_BOTTOM_MARGIN,
						                RIGHT_MARGIN_EDGE,
						                PREV_BOTTOM_MARGIN + HEIGHT);
						break;
				}
			}
		}
	}
	
	/**
	 * Animates the {@link com.example.spaceship.classes.PlayerSpaceship player's spaceship}
	 * movement from one edge of the screen to the other
	 *
	 * @param left a parameter that indicates whether the
	 *             {@link com.example.spaceship.classes.PlayerSpaceship
	 *             spaceship} should move left or right.
	 */
	
	private void animateSpaceship (boolean left) {
		if (lost || player.getExplosion() != null) {
			return;
		}
		
		final Rect bounds = dPlayer.getBounds();
		int width = Math.abs(bounds.width());
		ValueAnimator animator = ValueAnimator.ofInt(bounds.left, left ? 0 : getWidth() - width);
		
		animator.setDuration(1000);
		
		animator.addUpdateListener(animation -> {
			final Object animatedValue = animation.getAnimatedValue();
			assert dPlayer != null : "dPlayer mustn't be null";
			dPlayer.setBounds((int) animatedValue,
			                  bounds.top,
			                  (int) animatedValue + width,
			                  bounds.bottom);
			invalidate();
		});
		animator.start();
	}
	
	/**
	 * Animates the laser's movement from the {@link com.example.spaceship.classes.PlayerSpaceship
	 * player spaceship's} gun to the {@link com.example.spaceship.classes.EnemySpaceship enemy
	 * spaceship's} bottom coordinate or, if missed, the end of the screen.
	 */
	
	private void animateLaser () {
		initLaser();
		
		final Rect bounds = laser.getBounds();
		final int height = Math.abs(bounds.height());
		ValueAnimator animator;
		
		if (laserTarget() == null) {
			animator = ValueAnimator.ofInt(bounds.top, -1 * height);
			animator.setDuration(200);
			miss = true;
		} else {
			final Rect targetBounds = Objects.requireNonNull(laserTarget()).getImage().getBounds();
			animator = ValueAnimator.ofInt(bounds.top, targetBounds.bottom);
			animator.setDuration(200 * (int) ((float) targetBounds.bottom / (float) getHeight()));
			hit = laserTarget();
		}
		
		animator.addUpdateListener(animation -> {
			final Object animatedValue = animation.getAnimatedValue();
			laser.setBounds(bounds.left,
			                (int) animatedValue,
			                bounds.right,
			                (int) animatedValue + height);
			laserAnimating = true;
			invalidate();
		});
		animator.start();
	}
	
	/**
	 * Initializes the laser by setting its coordinates to the gun of the spaceship.
	 */
	
	private void initLaser () {
		final Rect bounds = dPlayer.getBounds();
		laser.setBounds(bounds.centerX() - 30,
		                bounds.top - 110,
		                bounds.centerX() + 30,
		                bounds.top - 10);
	}
	
	/**
	 * Calculates the {@link com.example.spaceship.classes.EnemySpaceship spaceship} that the laser
	 * hits and returns that {@link com.example.spaceship.classes.EnemySpaceship spaceship}.
	 *
	 * @return the {@link com.example.spaceship.classes.EnemySpaceship spaceship} that the laser
	 * 		will hit. Null if laser's X coordinate is not in range of any {@link
	 *        com.example.spaceship.classes.EnemySpaceship spaceship}
	 */
	
	private EnemySpaceship laserTarget () {
		if (enemies == null || enemies.length <= 0 || laser == null)
			return null;
		
		EnemySpaceship result = null;
		
		for (EnemySpaceship enemy : enemies) {
			final Rect enemyBounds = enemy.getImage().getBounds();
			final Rect laserBounds = laser.getBounds();
			
			if (enemyBounds.left <= laserBounds.right &&
			    enemyBounds.right >= laserBounds.left &&
			    (result == null || result.getImage().getBounds().bottom < enemyBounds.bottom) &&
			    !enemy.isExploding())
				result = enemy;
		}
		return result;
	}
	
	/**
	 * Initializes the healthbar coordinates, size and general appearance of the given spaceship.
	 *
	 * @param canvas the canvas on which the healthbar is drawn
	 * @param sp     the spaceships to initialize the healthbar of
	 */
	
	private void initHealthbar (Canvas canvas, Spaceship sp) {
		if (sp.getHp() <= 0) {
			sp.getHealthbar().setEmpty();
			return;
		}
		
		hbFill.setStyle(Paint.Style.FILL);
		hbFill.setColor(Color.GREEN);
		
		hbStroke.setStyle(Paint.Style.STROKE);
		hbStroke.setColor(Color.WHITE);
		hbStroke.setStrokeWidth(3);
		
		sp.initHealthbar();
		initHp(canvas, sp);
		
		RectF hb = sp.getHealthbar();
		
		Paint black = new Paint();
		black.setStyle(Paint.Style.FILL);
		black.setColor(Color.BLACK);
		canvas.drawRect(hb, black);
		canvas.drawRect(hb, hbStroke);
		sp.updateHealthbar();
		canvas.drawRect(hb, hbFill);
		
	}
	
	/**
	 * Executes {@link com.example.spaceship.views.GameView#performClick()} whenever the screen is
	 * tapped.
	 *
	 * @param event the {@link android.view.MotionEvent motion event} data
	 *
	 * @return boolean used by the system
	 */
	
	@Override
	public boolean onTouchEvent (MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			performClick();
			return true;
		}
		return false;
	}
	
	/**
	 * Initializes the Health Points (HP) and the Health Points Text of the healthbar.
	 *
	 * @param canvas the canvas on which the healthbar and the text is drawn
	 * @param sp     the spaceship to initialize the healthbar of
	 */
	
	private void initHp (Canvas canvas, Spaceship sp) {
		RectF hb = sp.getHealthbar();
		
		sp.setHpLayout(new RelativeLayout(getContext()));
		RelativeLayout hpLayout = sp.getHpLayout();
		hpLayout.setTop((int) (hb.top + 25));
		hpLayout.setBottom((int) (hb.top + 5));
		hpLayout.setLeft((int) ((hb.left + (hb.width() / 2)) - 25));
		hpLayout.setRight((int) ((hb.right - (hb.width() / 2)) + 25));
		LayoutParams hpParams = new LayoutParams(LayoutParams.MATCH_PARENT,
		                                         LayoutParams.MATCH_PARENT);
		
		sp.setHpText(new TextView(getContext()));
		TextView hpText = sp.getHpText();
		hpText.setLayoutParams(hpParams);
		hpText.setText(sp.getHPString());
		hpText.setTextColor(Color.WHITE);
		hpText.setTextSize(12);
		hpText.setVisibility(VISIBLE);
		hpText.setGravity(Gravity.CENTER);
		hpText.setTextAlignment(TEXT_ALIGNMENT_CENTER);
		hpText.setTypeface(null, Typeface.BOLD);
		
		canvas.save();
		hpLayout.addView(sp.getHpText());
		canvas.translate(hb.centerX() - (hb.left / 6), (int) (hb.top - 60));
		hpLayout.measure(getWidth(), getHeight());
		hpLayout.layout(0, 0, 0, 0);
		hpLayout.draw(canvas);
		canvas.restore();
	}
	
	/**
	 * Draws the text that instructs the user to tap anywhere on the screen to restart the
	 * game.
	 *
	 * @param canvas the canvas on which the text is drawn.
	 */
	
	private void restartText (Canvas canvas) {
		TextPaint restartPaint = new TextPaint();
		restartPaint.setColor(Color.WHITE);
		restartPaint.setTextSize(36);
		restartPaint.setTextAlign(Paint.Align.CENTER);
		restartPaint.setTypeface(Typeface.DEFAULT_BOLD);
		
		canvas.drawText("Tap anywhere to restart",
		                (float) getWidth() / 2,
		                (float) getHeight() / 2 + 100, restartPaint);
	}
}
	
	
