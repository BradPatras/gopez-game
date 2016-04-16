package com.mygdx.gopez.android;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.mygdx.gopez.MainGame;

public class AndroidLauncher extends AndroidApplication implements MainGame.Callback {
	View mContentView;
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContentView = this.getWindow().getDecorView();
		mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
				| View.SYSTEM_UI_FLAG_FULLSCREEN
				| View.SYSTEM_UI_FLAG_LAYOUT_STABLE
				| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
				| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
				| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		MainGame game = new MainGame();
		game.setCallback(this);
		initialize(game, config);

	}

	@Override
	public void onGameOver(int score) {
		Log.i("AndroidLauncher", score +"");
		Intent i = new Intent(getApplicationContext(), GameOverDialog.class);
		i.putExtra("score", score);
		startActivity(i);
	}
}
