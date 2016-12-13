package com.example.nicolas.shootemup;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;


/**
 * Created by Nicolas on 04/11/2016.
 */

public class GameActivity extends Activity{

    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //setContentView(movingBackground);
        gameView = new GameView(this);
        setContentView(gameView);

        //Get my current information (score, coins, information about ship, ...)
        Intent i = getIntent();
        ScoreBoard myScore = (ScoreBoard) i.getParcelableExtra("my_score");


    }



    // This method executes when the player starts the game
    @Override
    protected void onResume() {
        super.onResume();

        // Tell the gameView resume method to execute
        gameView.resume();
    }

    // This method executes when the player quits the game
    @Override
    protected void onPause() {
        super.onPause();

        // Tell the gameView pause method to execute
        gameView.pause();
    }

}
