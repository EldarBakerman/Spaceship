package com.example.spaceship.views;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.BatteryManager;
import android.text.TextPaint;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

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
import java.util.concurrent.atomic.AtomicBoolean;

import static com.example.spaceship.classes.EnemySpaceship.EnemyType;

/**
 * {@link com.example.spaceship.activities.GameActivity GameActivity}'s custom {@link
 * android.view.View view}.
 * Contains all the logic and graphics of the game.
 *
 * @see com.example.spaceship.activities.GameActivity
 * @see android.view.View
 */

public class GameView extends View {
	
	/**
	 * The amount of points a spaceship awards.
	 *
	 * @see com.example.spaceship.views.GameView#userPoints
	 */
	
	private static final int SPACESHIP_POINTS = 1;
	
	/**
	 * The damage penalty for a miss.
	 */
	
	private static final int PLAYER_MISS_DAMAGE = 5;
	
	/**
	 * The points' {@link android.widget.TextView TextView}.
	 *
	 * @see com.example.spaceship.views.GameView#userPoints
	 */
	
	TextView pointsText;
	
	/**
	 * A field storing the current in-game amount of points.
	 */
	
	private int points;
	
	/**
	 * A field storing the user's amount of points.
	 */
	
	private int userPoints;
	
	/**
	 * The {@link com.example.spaceship.classes.PlayerSpaceship player}'s spaceship.
	 *
	 * @see com.example.spaceship.classes.PlayerSpaceship
	 */
	
	private PlayerSpaceship player;
	
	/**
	 * The {@link android.graphics.drawable.Drawable drawable} representing the {@link
	 * com.example.spaceship.views.GameView#player}'s weapon visually.
	 *
	 * @see com.example.spaceship.views.GameView#player
	 * @see com.example.spaceship.classes.Weapon
	 */
	
	private Drawable laser;
	
	/**
	 * The timer's {@link android.widget.TextView TextView}.
	 *
	 * @see java.util.Timer
	 */
	
	private TextView timerText;
	
	/**
	 * The {@link android.graphics.drawable.Drawable drawable} representing the {@link
	 * com.example.spaceship.views.GameView#player}'s
	 * {@link com.example.spaceship.classes.Spaceship
	 * spaceship} visually.
	 *
	 * @see com.example.spaceship.classes.PlayerSpaceship
	 * @see com.example.spaceship.classes.Spaceship
	 */
	
	private Drawable dPlayer;
	
	/**
	 * An array of {@link com.example.spaceship.classes.EnemySpaceship enemies} that holds all the
	 * enemies in the current level.
	 *
	 * @see com.example.spaceship.classes.EnemySpaceship
	 */
	
	private EnemySpaceship[] enemies;
	
	
	/**
	 * An {@link com.example.spaceship.classes.EnemySpaceship EnemySpaceship} reference to the
	 * current target of the laser. Null if no target.
	 *
	 * @see GameView#laserTarget()
	 * @see com.example.spaceship.views.GameView#miss
	 */
	
	private EnemySpaceship hit = null;
	
	/**
	 * The {@link android.widget.LinearLayout layout} for the
	 * {@link com.example.spaceship.views.GameView#timerText}
	 * and {@link com.example.spaceship.views.GameView#pointsText}.
	 *
	 * @see com.example.spaceship.views.GameView#timerText
	 * @see com.example.spaceship.views.GameView#pointsText
	 * @see android.widget.LinearLayout
	 */
	
	private LinearLayout infoLayout;
	
	/**
	 * An {@link android.graphics.drawable.Drawable indicator} that shows the current status of the
	 * battery.
	 *
	 * @see android.content.BroadcastReceiver
	 */
	
	private Drawable battery;
	
	/**
	 * The {@link android.graphics.Paint paint} for the stroke of the {@link android.graphics.RectF
	 * healthbar} of
	 * the {@link com.example.spaceship.classes.Spaceship spaceships}.
	 *
	 * @see com.example.spaceship.views.GameView#player
	 * @see com.example.spaceship.views.GameView#enemies
	 * @see com.example.spaceship.classes.Spaceship
	 * @see com.example.spaceship.classes.PlayerSpaceship
	 * @see com.example.spaceship.classes.EnemySpaceship
	 * @see com.example.spaceship.views.GameView#initHealthbar(android.graphics.Canvas,
	 *        com.example.spaceship.classes.Spaceship)
	 */
	
	private Paint hbStroke;
	
	/**
	 * The {@link android.graphics.Paint paint} for the fill of the {@link android.graphics.RectF
	 * healthbar} of
	 * the {@link com.example.spaceship.classes.Spaceship spaceships}.
	 *
	 * @see com.example.spaceship.views.GameView#player
	 * @see com.example.spaceship.views.GameView#enemies
	 * @see com.example.spaceship.classes.Spaceship
	 * @see com.example.spaceship.classes.PlayerSpaceship
	 * @see com.example.spaceship.classes.EnemySpaceship
	 * @see com.example.spaceship.views.GameView#initHealthbar(android.graphics.Canvas,
	 *        com.example.spaceship.classes.Spaceship)
	 */
	
	private Paint hbFill;
	
	/**
	 * The current {@link com.example.spaceship.classes.User user}.
	 * Initialized and updated by the {@link com.example.spaceship.classes.DatabaseOpenHelper
	 * database}.
	 *
	 * @see com.example.spaceship.classes.User
	 * @see com.example.spaceship.classes.DatabaseOpenHelper
	 * @see com.example.spaceship.activities.SplashActivity#database
	 */
	
	private User user;
	
	/**
	 * A {@link java.util.Random random} object that is used in order to generate random values in
	 * methods that require random values.
	 *
	 * @see java.util.Random
	 * @see com.example.spaceship.views.GameView#generateEnemies(int)
	 */
	
	private Random random = new Random();
	
	// Boolean Checkers
	
	/**
	 * A boolean value that indicates whether the canvas has been initialized for the first time or
	 * not.
	 *
	 * @see java.util.concurrent.atomic.AtomicBoolean
	 * @see com.example.spaceship.views.GameView#onDraw(android.graphics.Canvas)
	 * @see android.graphics.Canvas
	 */
	
	private AtomicBoolean start = new AtomicBoolean(true);
	
	/**
	 * A boolean value that indicates whether the current call to {@link
	 * com.example.spaceship.views.GameView#onDraw(android.graphics.Canvas)} is by the {@link
	 * java.util.Timer timer} or not.
	 *
	 * @see com.example.spaceship.views.GameView#onDraw(android.graphics.Canvas)
	 * @see java.util.Timer
	 */
	
	private boolean timerTick = false;
	
	/**
	 * A boolean value that indicates whether the current call to {@link
	 * com.example.spaceship.views.GameView#onDraw(android.graphics.Canvas)} is by the {@link
	 * com.example.spaceship.views.GameView#onTouchEvent(android.view.MotionEvent)} or not.
	 *
	 * @see com.example.spaceship.views.GameView#onDraw(android.graphics.Canvas)
	 * @see com.example.spaceship.views.GameView#onTouchEvent(android.view.MotionEvent)
	 */
	
	private boolean tapped = false;
	
	/**
	 * A boolean value that indicates whether the laser is currently mid-animation or not.
	 *
	 * @see com.example.spaceship.views.GameView#laser
	 * @see GameView#animateLaser()
	 */
	
	private boolean laserAnimating = false;
	
	/**
	 * A boolean value that indicates whether the
	 * {@link com.example.spaceship.views.GameView#laser}
	 * has missed its shot
	 * or not.
	 *
	 * @see com.example.spaceship.views.GameView#laser
	 * @see GameView#laserTarget()
	 */
	
	private boolean miss = false;
	
	/**
	 * A boolean value that indicates whether the game is lost or not.
	 * Performs its check by checking whether the
	 * {@link com.example.spaceship.views.GameView#player}'s
	 * HP is below 0.
	 *
	 * @see com.example.spaceship.views.GameView#player
	 * @see com.example.spaceship.classes.Spaceship#getHp()
	 */
	
	private boolean lost = false;
	
	/**
	 * A boolean value that indicates whether the game is won or not.
	 * Performs its check by checking whether all the
	 * {@link com.example.spaceship.classes.EnemySpaceship
	 * enemies} are exploded.
	 *
	 * @see com.example.spaceship.views.GameView#enemies
	 * @see com.example.spaceship.classes.EnemySpaceship
	 * @see com.example.spaceship.views.GameView#explode(com.example.spaceship.classes.Spaceship)
	 */
	
	private boolean win = false;
	
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
		
		user       = SplashActivity.database.getUser(activity.getIntent()
		                                                     .getLongExtra("userId", -1));
		userPoints = user.getPoints();
		points     = activity.getIntent().getIntExtra("points", 0);
		
		// Player
		
		player   = new PlayerSpaceship(getResources().getDrawable(R.drawable.spaceship1, null));
		hbFill   = new Paint();
		hbStroke = new Paint();
		
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
		timerText.setPadding(0, 0, 50, 0);
		timerText.setVisibility(VISIBLE);
		timerText.setTextSize(22);
		timerText.setText(String.format(Locale.ENGLISH, "%d", 30));
		timerText.setTextColor(Color.WHITE);
		timerText.setTypeface(null, Typeface.BOLD);
		timerText.setLayoutParams(params);
		timerText.setText(String.format(Locale.ENGLISH,
		                                "%s%d",
		                                getResources().getString(R.string.time),
		                                30));
		
		// Points TextView
		
		pointsText = new TextView(context);
		pointsText.setGravity(Gravity.CENTER);
		setTextAlignment(TEXT_ALIGNMENT_CENTER);
		setPadding(0, 0, 50, 0);
		setVisibility(VISIBLE);
		pointsText.setTextSize(22);
		pointsText.setTextColor(Color.WHITE);
		pointsText.setTypeface(null, Typeface.BOLD);
		pointsText.setLayoutParams(params);
		pointsText.setText(String.format(Locale.ENGLISH,
		                                 "%s%d",
		                                 getResources().getString(R.string.points),
		                                 points));
		
		infoLayout.addView(timerText);
		infoLayout.addView(pointsText);
		
		// Battery Indicator
		
		setBatteryRes(context);
		
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
				
				int timeLeft = Integer.parseInt(timerText.getText()
				                                         .toString()
				                                         .replace(getResources().getString(R.string.time),
				                                                  ""));
				if (timeLeft <= 0) {
					explode(player);
					invalidate();
					timer.cancel();
				} else {
					activity.runOnUiThread(() -> {
						timerText.setText(String.format(Locale.ENGLISH,
						                                "%s%d",
						                                getResources().getString(R.string.time),
						                                timeLeft - 1));
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
					}
					hit = null;
					invalidate();
				}
				timerCount++;
				invalidate();
			}
		}, 0, 300);
		
		if (spaceship instanceof EnemySpaceship) {
			points += SPACESHIP_POINTS;
			userPoints += SPACESHIP_POINTS;
			pointsText.setText(String.format(Locale.ENGLISH,
			                                 "%s%d",
			                                 getResources().getString(R.string.points),
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
	 * @return the {@link android.graphics.drawable.Drawable drawable} with the corresponding name
	 *
	 * @see android.graphics.drawable.Drawable
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
	 * Exits if the game is over. Else, shoots a laser from the {@link
	 * com.example.spaceship.classes.PlayerSpaceship spaceship's} gun.
	 *
	 * @return a boolean that is generated by the system.
	 *
	 * @see android.view.View#performClick()
	 * @see android.view.View#onTouchEvent(android.view.MotionEvent)
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
	 * {@link View#onDraw(Canvas) onDraw} method implemented from {@link View view} that draws the
	 * entire game.
	 *
	 * @param canvas canvas to draw on
	 */
	
	@Override
	protected void onDraw (Canvas canvas) {
		super.onDraw(canvas);
		
		if (lost) {
			start.getAndSet(false);
			timerTick = tapped = laserAnimating = miss = false;
			loseText(canvas);
			return;
		}
		
		if (win)
			launchGame();
		
		// Animation
		if (start.get()) {
			setBackgroundColor(Color.BLACK);
			dPlayer.setBounds(getWidth() / 2 - 150, getHeight() - 300,
			                  getWidth() / 2 + 150,
			                  getHeight() - 100);
			generateEnemies(random.nextInt(6) + 1);
			start.getAndSet(false);
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
		
		setBatteryRes(getContext());
		if (battery != null) {
			battery.setBounds(getWidth() - 200, 90, getWidth() - 100, 190);
			battery.draw(canvas);
			final Rect bounds = battery.getBounds();
			Log.d("GameView#onDraw",
			      String.format(Locale.ENGLISH,
			                    "left: %d right: %d top: %d bottom: %d",
			                    bounds.left,
			                    bounds.right,
			                    bounds.top,
			                    bounds.bottom));
		} else {
			Log.d("GameView#onDraw", "Battery null");
		}
		
		if (timerTick) {
			animateSpaceship(Integer.parseInt(timerText.getText()
			                                           .toString()
			                                           .replace(getResources().getString(R.string.time),
			                                                    "")) % 2 == 0);
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
			if (player.reduceHP(PLAYER_MISS_DAMAGE) <= 0)
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
		
		endText(canvas);
	}
	
	/**
	 * Launches a new game, if won, passes the points value to the new game and updates the
	 * highscore of the {@link com.example.spaceship.classes.User user}.
	 */
	
	private void launchGame () {
		Activity activity = (Activity) getContext();
		Intent intent = new Intent(activity, GameActivity.class);
		
		user.setPoints(userPoints);
		
		if (user.getHighscore() < points)
			user.setHighscore(points);
		
		SplashActivity.database.update(user);
		intent.putExtra("userId", user.getId());
		intent.putExtra("points", points);
		
		activity.startActivity(intent);
		activity.finish();
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
	 * @throws AssertionError an error thrown if the method is called after the first time the
	 *                        canvas is initialized.
	 */
	
	private synchronized void generateEnemies (int amount) throws AssertionError {
		if (amount > 6 || amount < 1)
			throw new AssertionError("amount is invalid");
		
		// If method was called from constructor - exit (can't set coordinates if canvas
		// hasn't loaded yet)
		if (!start.get())
			throw new AssertionError("must be called only on start");
		
		enemies = new EnemySpaceship[amount];
		
		/* Chances array in order to generate chances in accordance to the rarity of the EnemyType
		 * by creating an array of numbers representing the number of EnemyType.
		 * Each number is appearing (4 - rarity) times in the array, then a number is chosen
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
			
			// Increase the length of the array by (4 - rarity)
			chances = new int[oldLength + (4 - enemyType.getRarity())];
			
			// Retrieve the previous values to the new array
			System.arraycopy(oldChances, 0, chances, 0, oldLength);
			
			// Add the new values to the new array (the EnemyType number)
			for (int j = oldLength; j < chances.length; j++) {
				chances[j] = i;
			}
		}
		
		// Use Random to set a pattern of coordinates for the ships (the amount of patterns is 2)
		int pattern = random.nextInt(2);
		
		for (int i = 0; i < amount; i++) {
			
			// Use Random to generate the wanted amount of enemies randomly, in accordance to
			// rarity
			enemies[i] = new EnemySpaceship(EnemyType.valueOf(String.format(Locale.ENGLISH,
			                                                                "ENEMY_%d",
			                                                                chances[random.nextInt(
					                                                                chances.length)])));
			enemies[i].setImage(getResources().getDrawable(enemies[i].getResource(), null));
			
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
						break;
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
	 * Animates the laser's movement from the {@link com.example.spaceship.classes.PlayerSpaceship
	 * player spaceship's} gun to the {@link com.example.spaceship.classes.EnemySpaceship enemy
	 * spaceship's} bottom coordinate or, if missed, the end of the screen.
	 */
	
	private void animateLaser () {
		initLaser();
		
		final Rect bounds = laser.getBounds();
		final int height = Math.abs(bounds.height());
		ValueAnimator animator;
		
		hit = laserTarget();
		if (hit == null) {
			animator = ValueAnimator.ofInt(bounds.top, -1 * height);
			animator.setDuration(player.getWeapon().getSpeed() * 100);
			miss = true;
		} else {
			final Rect targetBounds = Objects.requireNonNull(laserTarget()).getImage().getBounds();
			animator = ValueAnimator.ofInt(bounds.top, targetBounds.bottom);
			animator.setDuration((int) ((float) (player.getWeapon().getSpeed() * 100) /
			                            ((float) getHeight() / (float) targetBounds.bottom)));
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
	 * Draws the text that instructs the user to tap anywhere on the screen to exit the
	 * game.
	 *
	 * @param canvas the canvas on which the text is drawn.
	 */
	
	private void endText (Canvas canvas) {
		TextPaint endPaint = new TextPaint();
		endPaint.setColor(Color.WHITE);
		endPaint.setTextSize(36);
		endPaint.setTextAlign(Paint.Align.CENTER);
		endPaint.setTypeface(Typeface.DEFAULT_BOLD);
		
		canvas.drawText("Tap anywhere to exit",
		                (float) getWidth() / 2, (float) getHeight() / 2 + 100, endPaint);
	}
	
	private void setBatteryRes (Context context) {
		IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
		@Nullable Intent batteryStatus = context.registerReceiver(null, intentFilter);
		
		assert batteryStatus != null : "batteryStatus is null";
		int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
		int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
		
		float percent = level * 100 / (float) scale;
		
		if (percent > 80)
			battery = getRes("battery_5");
		else if (percent <= 80 && percent > 60)
			battery = getRes("battery_4");
		else if (percent <= 60 && percent > 40)
			battery = getRes("battery_3");
		else if (percent <= 40 && percent > 20)
			battery = getRes("battery_2");
		else
			battery = getRes("battery_1");
	}
}
	
	
