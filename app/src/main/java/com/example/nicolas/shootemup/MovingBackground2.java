package com.example.nicolas.shootemup;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Nicolas on 05/11/2016.
 */

public class MovingBackground2 {

    private Bitmap backGroundBitmap;
    private int mBGFarMoveY = 0;

    MovingBackground2(Bitmap bitmap) {
        this.backGroundBitmap = bitmap;
    }

    public void draw(Canvas canvas) {

        // decrement the far background
        mBGFarMoveY = mBGFarMoveY - 5;

        // decrement the near background
        //mBGNearMoveX = mBGNearMoveX - 20;

        // calculate the wrap factor for matching image draw
        int newFarY = backGroundBitmap.getHeight() + mBGFarMoveY;

        // if we have scrolled all the way, reset to start
        if (newFarY <= 0) {
            mBGFarMoveY = 0;
            // only need one draw
            canvas.drawBitmap(backGroundBitmap, 0, -mBGFarMoveY, null);

        } else {
            // need to draw original and wrap
            canvas.drawBitmap(backGroundBitmap, 0, -mBGFarMoveY, null);
            canvas.drawBitmap(backGroundBitmap, 0, -newFarY, null);
        }

    }
}
