package ca.binder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreenActivity extends Activity {

	private static final int SPLASH_TIMEOUT = 2000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// timer
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				//runs when timer finishes, start ManActivity
				Intent startMain = new Intent(getBaseContext(), MainActivity.class);
				startActivity(startMain);
				finish();
			}
		}, SPLASH_TIMEOUT);


		setContentView(R.layout.activity_splash_screen);


	}

}