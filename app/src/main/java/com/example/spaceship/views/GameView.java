package com.example.spaceship.views;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.spaceship.R;
import com.example.spaceship.classes.EnemySpaceship;
import com.example.spaceship.classes.PlayerSpaceship;
import com.example.spaceship.classes.Spaceship;
import com.example.spaceship.classes.User;

import java.lang.reflect.Field;
import java.util.Locale;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Game's Custom View to be shown at {@link com.example.spaceship.activities.GameActivity
 * GameActivity}
 */

public class GameView extends View {
	
	//TODO: Reorganize everything
	
	private static final int[] TIMER = {10, 20, 30};
	PlayerSpaceship player;
	Drawable laser;
	TextView timerText;
	//TODO: Draw Spaceships Proportionally
	Drawable dPlayer;
	RectF hPlayer;
	// Boolean Checkers
	boolean start = true;
	boolean timerTick = false;
	boolean tapped = false;
	boolean laserAnimating = false;
	boolean miss = false;
	boolean lost = false;
	EnemySpaceship[] enemies;
	// TODO: Level & Points
	TextView pointsText, levelText;
	private EnemySpaceship hit = null;
	private LinearLayout infoLayout;
	// TODO: Proportional size
	private Paint hbStroke;
	private Paint hbFill;
	private int level, points;
	//TODO: User & Database
	private User user;
	
	
	/**
	 * Constructor for {@link View view}
	 * Initializes {@link PlayerSpaceship player}, {@link Paint paints} for the Healthbar and
	 * {@link
	 * EnemySpaceship enemies}
	 * Initializes {@link LinearLayout infoLayout} and several {@link TextView text viewes} for the
	 * level, timer and points.
	 *
	 * @param context {@link Activity activity} of the game
	 */
	
	public GameView (Context context) {
		super(context);
		
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
		timerText.setVisibility(VISIBLE);
		timerText.setTextSize(22);
		timerText.setText(String.format(Locale.ENGLISH, "%d", TIMER[2]));
		timerText.setTextColor(Color.WHITE);
		timerText.setTypeface(null, Typeface.BOLD);
		timerText.setLayoutParams(params);
		
		infoLayout.addView(timerText);
		
		// Player & Player Healthbar
		
		dPlayer = player.getImage();
		hPlayer = player.getHealthbar();
		
		// Laser
		// TODO: Replace direct call to Drawable file with Weapon#getDrawable
		laser = getResources().getDrawable(R.drawable.red_laser, null);
		// TODO: Make generateEnemies completely automatic (not requiring input/having an
		//  automatic input)
		generateEnemies(4);
		
		// Timer Method
		
		Activity activity = (Activity) context;
		
		//TODO: Method to determine TIMER seconds by difficulty
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run () {
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
		
		enemies[0].getImage().setBounds(100, 200, 300, 400);
		enemies[1].getImage().setBounds(500, 200, 700, 400);
		//		enemies[2].getImage().setBounds(900, 200, 900, 400);
		//		enemies[3].getImage().setBounds(400, 500, 600, 700);
	}
	
	// TODO: Generate automatic coordinates
	private void generateEnemies (int amount) {
		enemies = new EnemySpaceship[amount];
		for (int i = 0; i < amount; i++) {
			enemies[i] =
					new EnemySpaceship(EnemySpaceship.EnemyType.valueOf(String.format(Locale.ENGLISH,
					                                                                  "ENEMY_%d",
					                                                                  i + 1)));
			enemies[i].setImage(getResources().getDrawable(enemies[i].getResource(), null));
		}
	}
	
	/**
	 * {@link View#onDraw(Canvas) onDraw} method inherited from {@link View view} for initial run
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
		
		// TODO: Array of enemy spaceships
		
		// TODO: Documentation
		
		// Animation
		if (start) {
			setBackgroundColor(Color.BLACK);
			dPlayer.setBounds(getWidth() / 2 - 150,
			                  getHeight() - 300,
			                  getWidth() / 2 + 150,
			                  getHeight() - 100);
			start = false;
		}
		
		enemies[0].getImage().draw(canvas);
		enemies[1].getImage().draw(canvas);
		initHealthbar(canvas, enemies[0]);
		initHealthbar(canvas, enemies[1]);
		
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
			if (enemy.getExplosion() != null && !enemy.getExplosion().getBounds().isEmpty()) {
				enemy.updateExplosion();
				enemy.getExplosion().draw(canvas);
			}
		
		if (player.getExplosion() != null && !player.getExplosion().getBounds().isEmpty()) {
			player.updateExplosion();
			player.getExplosion().draw(canvas);
		}
	}
	
	private void explode (Spaceship spaceship) {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			int timerCount = 0;
			
			@Override
			public void run () {
				// TODO: Find a way to count timer without public variable or create a local
				//  variable for every instance of timer
				
				if (timerCount < 3)
					spaceship.setExplosion(getRes("explosion" + timerCount), timerCount);
				else {
					spaceship.setExplosion(timerCount);
					timer.cancel();
					if (spaceship instanceof PlayerSpaceship)
						lost = true;
					hit = null;
					invalidate();
				}
				timerCount++;
				invalidate();
			}
		}, 0, 300);
	}
	
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
			animator.setDuration(200 * (1 - targetBounds.bottom / getHeight()));
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
	
	private EnemySpaceship laserTarget () {
		if (enemies == null || enemies.length <= 0 || laser == null)
			return null;
		
		EnemySpaceship result = null;
		
		for (EnemySpaceship enemy : enemies) {
			final Rect enemyBounds = enemy.getImage().getBounds();
			final Rect laserBounds = laser.getBounds();
			
			if (enemyBounds.left <= laserBounds.right && enemyBounds.right >= laserBounds.left &&
			    (result == null || result.getImage().getBounds().bottom < enemyBounds.bottom))
				result = enemy;
		}
		return result;
	}
	
	
	private void animateSpaceship (boolean left) {
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
	
	private void initLaser () {
		final Rect bounds = dPlayer.getBounds();
		laser.setBounds(bounds.centerX() - 30,
		                bounds.top - 110,
		                bounds.centerX() + 30,
		                bounds.top - 10);
	}
	
	private void loseText (Canvas canvas) {
		TextPaint losePaint = new TextPaint();
		losePaint.setColor(Color.RED);
		losePaint.setTextSize(36);
		losePaint.setTextAlign(Paint.Align.CENTER);
		losePaint.setTypeface(Typeface.DEFAULT_BOLD);
		
		canvas.drawText("Game Over!", getWidth() / 2, getHeight() / 2, losePaint);
	}
	
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
	
	@Override
	public boolean onTouchEvent (MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			performClick();
			return true;
		}
		return false;
	}
	
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
	
	
	@Override
	public boolean performClick () {
		tapped = true;
		postInvalidate();
		return super.performClick();
	}
	
	private Drawable getRes (String id) {
		try {
			Field idField = R.drawable.class.getDeclaredField(id);
			return getResources().getDrawable(idField.getInt(idField), null);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			return null;
		}
	}
}
