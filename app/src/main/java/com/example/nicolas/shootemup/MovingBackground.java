package com.example.nicolas.shootemup;

/**
 * Created by Nicolas on 01/11/2016.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class MovingBackground extends SurfaceView implements
        SurfaceHolder.Callback {
    private Bitmap backGround;
    private Bitmap playerShipBitmap;
    private Ship playerShip;
    private List<Shoot> shoots;
    private List<Ship> enemiesShips;

    public MovingBackground(Context context) {
        super(context);
        backGround = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.space3);
        setWillNotDraw(true);

        shoots = new ArrayList<Shoot>();
        enemiesShips = new ArrayList<Ship>();

        playerShipBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ship);
        playerShip = new Ship(playerShipBitmap, 0, 775);


        //Add enemies for test
        enemiesShips.add(new Ship(playerShipBitmap, 300, 0));

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        doDrawRunningVerticalReverse(canvas);

        playerShip.draw(canvas);

        Shoot newShoot = playerShip.shoot();
        shoots.add(newShoot);

        //Draw, move and remove if shoot is outside the window
        //Check if a shoot hit a ship
        Iterator<Shoot> iteratorShoot = shoots.iterator();
        while (iteratorShoot.hasNext()) {
            Shoot shoot = iteratorShoot.next();
            shoot.draw(canvas);
            shoot.move();
            if(!shoot.isInside(canvas.getHeight(), canvas.getWidth())) {
                iteratorShoot.remove();
            } else {
                //If the shoot go UP
                if(shoot.getDirY() < 0) {
                    Iterator<Ship> iteratorShip = enemiesShips.iterator();
                    while (iteratorShip.hasNext()) {
                        Ship ship = iteratorShip.next();
                        if(ship.isInside(shoot)) {
                            iteratorShip.remove();
                            iteratorShoot.remove();
                            //Add a new enemy for test
                            int randomX = (int)(Math.abs(Math.random())*(canvas.getWidth()-playerShipBitmap.getWidth()));
                            //int randomX = (int)Math.random();
                            enemiesShips.add(new Ship(playerShipBitmap, randomX, 0));
                        }
                    }
                } else { // If the shoot go DOWN
                    if(playerShip.isInside(shoot)) {
                        //TODO : GAME OVER
                    }
                }
            }
        }

        for(Ship ship : enemiesShips){
            ship.draw(canvas);
        }

        invalidate();

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    /**
     * Draws current state of the game Canvas.
     */

    private int mBGFarMoveX = 0;
    //private int mBGNearMoveX = 0;
    private int mBGFarMoveY = 0;
    //private int mBGNearMoveY = 0;

    private void doDrawRunningHorinzontal(Canvas canvas) {

        // decrement the far background
        mBGFarMoveX = mBGFarMoveX - 5;

        // decrement the near background
        //mBGNearMoveX = mBGNearMoveX - 20;

        // calculate the wrap factor for matching image draw
        int newFarX = backGround.getWidth() - (-mBGFarMoveX);

        // if we have scrolled all the way, reset to start
        if (newFarX <= 0) {
            mBGFarMoveX = 0;
            // only need one draw
            canvas.drawBitmap(backGround, mBGFarMoveX, 0, null);

        } else {
            // need to draw original and wrap
            canvas.drawBitmap(backGround, mBGFarMoveX, 0, null);
            canvas.drawBitmap(backGround, newFarX, 0, null);
        }

    }

    private void doDrawRunningHorinzontalReverse(Canvas canvas) {

        // decrement the far background
        mBGFarMoveX = mBGFarMoveX - 5;

        // decrement the near background
        //mBGNearMoveX = mBGNearMoveX - 20;

        // calculate the wrap factor for matching image draw
        int newFarX = backGround.getWidth() + mBGFarMoveX;

        // if we have scrolled all the way, reset to start
        if (newFarX <= 0) {
            mBGFarMoveX = 0;
            // only need one draw
            canvas.drawBitmap(backGround, -mBGFarMoveX, 0, null);

        } else {
            // need to draw original and wrap
            canvas.drawBitmap(backGround, -mBGFarMoveX , 0, null);
            canvas.drawBitmap(backGround, -newFarX, 0, null);
        }

    }

    private void doDrawRunningVerticalReverse(Canvas canvas) {

        // decrement the far background
        mBGFarMoveY = mBGFarMoveY - 5;

        // decrement the near background
        //mBGNearMoveX = mBGNearMoveX - 20;

        // calculate the wrap factor for matching image draw
        int newFarY = backGround.getHeight() + mBGFarMoveY;

        // if we have scrolled all the way, reset to start
        if (newFarY <= 0) {
            mBGFarMoveY = 0;
            // only need one draw
            canvas.drawBitmap(backGround, 0, -mBGFarMoveY, null);

        } else {
            // need to draw original and wrap
            canvas.drawBitmap(backGround, 0, -mBGFarMoveY, null);
            canvas.drawBitmap(backGround, 0, -newFarY, null);
        }

    }




    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int)event.getX();
        playerShip.setX(x-25);
        invalidate();
        return true;
    }





}
