package com.example.nicolas.shootemup;

import android.app.Activity;
import android.os.Bundle;


/**
 * Created by Nicolas on 04/11/2016.
 */

public class GameActivity extends Activity{

    private MovingBackground movingBackground;
    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //movingBackground = new MovingBackground(this);
        //setContentView(movingBackground);
        gameView = new GameView(this);
        setContentView(gameView);

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
