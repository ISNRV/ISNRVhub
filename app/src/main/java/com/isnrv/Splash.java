package com.isnrv;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * This class shows a loading animation at the start of the application
 *
 * @author Yasir
 */
public class Splash extends Activity {
	private static final String TAG = Splash.class.getCanonicalName();
	private static final int SPLASH_TIME = 2000;
	private boolean active = true;
	private boolean isRunning = true;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		isRunning = true;
		startAnimations();
		Thread splashTread = new Thread() {
			@Override
			public void run() {
				try {
					int waited = 0;
					while (active && (waited < SPLASH_TIME)) {
						sleep(100);
						if (active) {
							waited += 100;
						}
					}
				} catch (InterruptedException e) {
					// do nothing
					Thread.currentThread().interrupt();

				} finally {
					finish();
					if (isRunning)
						startActivity(new Intent(getApplicationContext(), Main.class));
				}
			}
		};
		splashTread.start();

		Intent intent = new Intent(getApplicationContext(), ScheduledService.class);
		if (PendingIntent.getService(getApplicationContext(), 0, intent, PendingIntent.FLAG_NO_CREATE) == null) {
			//start ScheduledService for prayers
			Log.d(TAG, "Start ScheduledService");
			startService(intent);
		}


	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			active = false;
		}
		return true;
	}

	private void startAnimations() {
		Animation anim = AnimationUtils.loadAnimation(this, R.anim.slow_fade_in);
		anim.reset();
		ImageView logo = findViewById(R.id.splash_logo);
		logo.clearAnimation();
		logo.startAnimation(anim);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		isRunning = false;
	}

	@Override
	protected void onPause() {
		super.onPause();
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
	}
}