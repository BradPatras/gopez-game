package com.mygdx.gopez.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class GameOverDialog extends AppCompatActivity {

    int score;
    RelativeLayout layout;
    TextView scoreText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);

        // ...but notify us that it happened.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        View mContentView = this.getWindow().getDecorView();
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        setContentView(R.layout.activity_game_over_dialog);
        scoreText = (TextView) findViewById(R.id.score);
        layout = (RelativeLayout) findViewById(R.id.dialog_layout);
        score = getIntent().getExtras().getInt("score");

        if (score != 1)
            scoreText.setText("You scored " + score + " points!");
        else
            scoreText.setText("You scored " + score + " point!");


        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AndroidLauncher.class);
                startActivity(i);
            }
        });

    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // If we've received a touch notification that the user has touched
        // outside the app, finish the activity.
        Intent i = new Intent(getApplicationContext(), AndroidLauncher.class);
        startActivity(i);

        // Delegate everything else to Activity.
        return super.onTouchEvent(event);
    }

}
