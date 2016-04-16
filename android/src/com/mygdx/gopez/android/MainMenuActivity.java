package com.mygdx.gopez.android;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.mygdx.gopez.MainGame;
import com.mygdx.gopez.android.fragment.StoryPanel;

import java.util.ArrayList;

/**
 * Created by Calm on 3/7/2016.
 */
public class MainMenuActivity extends AppCompatActivity implements StoryPanel.Listener, TypeWriter.FinishListener {
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
    TextView nextText;
    TextView skipText;
    FrameLayout fragContainer;
    int storyIndex;


    ArrayList<StoryPanel> storyPanels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_menu);



        mContentView = findViewById(R.id.content_view);
        newTextView = (TextView) findViewById(R.id.main_menu_new);
        resumeTextView = (TextView) findViewById(R.id.main_menu_resume);
        resumeTextView.setVisibility(View.INVISIBLE);
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
        skipText = (TextView) findViewById(R.id.skip_text);
        nextText = (TextView) findViewById(R.id.next_text);
        nextText.setVisibility(View.INVISIBLE);
        SharedPreferences sharedPreferences  = getSharedPreferences(getString(R.string.prefs), 0);
        if (sharedPreferences.getInt(getString(R.string.prefs_current_level), -1) < 0){
            resumeTextView.setClickable(false);
            resumeTextView.setTextColor(Color.GRAY);
        }

        StoryPanel story1 = StoryPanel.newInstance("Once upon a time in a province far away...                     \nThere lived a girl.",null, this);
        StoryPanel story2 = StoryPanel.newInstance("This girl was the one and only BROPEZ.", BitmapFactory.decodeResource(getResources(), R.drawable.bropez), this);
        StoryPanel story3 = StoryPanel.newInstance("On the outside she kept her composure, but she faced an inner battle that tested every fiber of her being.",BitmapFactory.decodeResource(getResources(), R.drawable.bropez), this);
        StoryPanel story4 = StoryPanel.newInstance("The demons that haunted her were dangerous and terrible, breaking her down at any chance.     \nWhat she faced would have destroyed an average person.",BitmapFactory.decodeResource(getResources(), R.drawable.demon), this);
        StoryPanel story5 = StoryPanel.newInstance("                       \nBut she was not an average person...                 \nInstead of giving up, she learned to FIGHT BACK",BitmapFactory.decodeResource(getResources(), R.drawable.bropez), this);
        StoryPanel story6 = StoryPanel.newInstance("Tap once to do an impressive leap", BitmapFactory.decodeResource(getResources(), R.drawable.gpztut1), this);
        StoryPanel story7 = StoryPanel.newInstance("Tap twice to unleash a devastating, badass downward flip attack on the baddies", BitmapFactory.decodeResource(getResources(), R.drawable.gpztut2), this);

        storyPanels = new ArrayList<>();
        storyPanels.add(0, story1);
        storyPanels.add(1, story2);
        storyPanels.add(2, story3);
        storyPanels.add(3, story4);
        storyPanels.add(4, story5);
        storyPanels.add(5, story6);
        storyPanels.add(6, story7);
        storyIndex = 0;

        skipText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });
        nextText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storyIndex++;
                if (storyIndex<storyPanels.size()){
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.setCustomAnimations(R.animator.fadein_long, R.animator.fadeout_long, R.animator.fadein_long, R.animator.fadeout_long)
                            .replace(R.id.fragment_container, storyPanels.get(storyIndex))
                            .commit();
                    nextText.setVisibility(View.INVISIBLE);
                } else {
                    nextText.setVisibility(View.INVISIBLE);
                    startGame();

                }
            }
        });

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.animator.fadein_long, android.R.animator.fade_out, R.animator.fadein_long, android.R.animator.fade_out)
                .add(R.id.fragment_container, storyPanels.get(storyIndex))
                .commit();

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

    @Override
    public void finished() {
        nextText.setVisibility(View.VISIBLE);
    }

    @Override
    public void finishedTyping() {

    }
}

