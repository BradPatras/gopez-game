package com.mygdx.gopez.android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.mygdx.gopez.MainGame;

/**
 * Created by Calm on 3/7/2016.
 */
public class MainMenuActivity extends AppCompatActivity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private TextView resumeTextView;
    private TextView newTextView;
    private View mContentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_menu);



        mContentView = findViewById(R.id.content_view);
        newTextView = (TextView) findViewById(R.id.main_menu_new);
        resumeTextView = (TextView) findViewById(R.id.main_menu_resume);

        newTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });
        resumeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resumeGame();
            }
        });
        SharedPreferences sharedPreferences  = getSharedPreferences(getString(R.string.prefs), 0);
        if (sharedPreferences.getInt(getString(R.string.prefs_current_level), -1) < 0){
            resumeTextView.setClickable(false);
            resumeTextView.setTextColor(Color.GRAY);
        }

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        hide();
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

    }



    private void resumeGame(){
        //start game activity
        Intent i = new Intent(getApplicationContext(), AndroidLauncher.class);
        startActivity(i);
    }

    private void startGame(){
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.prefs), 0);
        sharedPreferences.edit().putInt(getString(R.string.prefs_current_level), -1).commit();
        resumeGame();
    }

}

