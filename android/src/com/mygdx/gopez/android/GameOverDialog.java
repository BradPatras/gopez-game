package com.mygdx.gopez.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
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
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_game_over_dialog);
        scoreText = (TextView) findViewById(R.id.score);
        layout = (RelativeLayout) findViewById(R.id.dialog_layout);
        score = getIntent().getExtras().getInt("score");

        if (score != 1)
            scoreText.setText("You scored " + score + " points!");
        else
            scoreText.setText("You scored " + score + " point!");


    }

}
