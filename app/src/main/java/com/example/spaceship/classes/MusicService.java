package com.example.spaceship.classes;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.spaceship.R;

/**
 * A {@link android.app.Service service} for the background music in the game.
 *
 * @see android.app.Service
 */

public class MusicService extends Service {
	
	/**
	 * The {@link android.media.MediaPlayer MediaPlayer} to play to music via.
	 *
	 * @see android.media.MediaPlayer
	 */
	
	MediaPlayer player;
	
	/**
	 *
	 */
	
	@Override
	public void onCreate () {
		super.onCreate();
		player = MediaPlayer.create(this, R.raw.music);
		player.setLooping(true); // Set looping
		player.setVolume(100, 100);
		
	}
	
	public int onStartCommand (Intent intent, int flags, int startId) {
		player.start();
		return Service.START_STICKY;
	}
	
	@Override
	public void onDestroy () {
		player.stop();
		player.release();
	}
	
	@Nullable
	@Override
	public IBinder onBind (Intent intent) {
		return null;
	}
}
