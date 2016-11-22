package com.example.nicolas.shootemup;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Nicolas on 05/11/2016.
 */

public class GameView extends SurfaceView implements Runnable {

    // This is our thread
    Thread gameThread = null;

    // This is new. We need a SurfaceHolder
    // When we use Paint and Canvas in a thread
    // We will see it in action in the draw method soon.
    SurfaceHolder ourHolder;

    // A boolean which we will set and unset
    // when the game is running- or not.
    volatile boolean playing;

    // A Canvas and a Paint object
    Canvas canvas;
    Paint paint;

    // This variable tracks the game frame rate
    long fps;

    // This is used to help calculate the fps
    private long timeThisFrame;

    // Declare an object of type Bitmap
    Bitmap bitmapBob;

    // Bob starts off not moving
    boolean isMoving = false;

    // He can walk at 150 pixels per second
    float walkSpeedPerSecond = 150;

    // He starts 10 pixels from the left
    float bobXPosition = 10;


    //---------------------------------------
    private Bitmap backGround;
    private Player player;
    private double time;
    private List<GameEntity> gameEntities;
    private MovingBackground2 movingBackground;

    // When the we initialize (call new()) on gameView
    // This special constructor method runs
    public GameView(Context context) {
        // The next line of code asks the
        // SurfaceView class to set up our object.
        // How kind.
        super(context);

        // Initialize ourHolder and paint objects
        ourHolder = getHolder();
        paint = new Paint();

        // Load Bob from his .png file
        bitmapBob = BitmapFactory.decodeResource(this.getResources(), R.drawable.ship);
        //-----------------------------

        backGround = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.space3);
        movingBackground = new MovingBackground2(backGround);

        Bitmap playerShipBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ship);

        Point departurePoint = new Point (0,0);
        Ship playerShip = new Ship(departurePoint,playerShipBitmap, new Collider(),1,1,1,1,new Weapon(new TypeOfShoot(),10));
        player.setPlayerShip();

        //Add enemies for test
        enemiesShips.add(new Ship(playerShipBitmap, 300, 0, "Straight"));

        // Set our boolean to true - game on!
        playing = true;

    }

    @Override
    public void run() {
        while (playing) {

            // Capture the current time in milliseconds in startFrameTime
            long startFrameTime = System.currentTimeMillis();

            // Update the frame
            update();

            // Draw the frame
            draw();

            // Calculate the fps this frame
            // We can then use the result to
            // time animations and more.
            timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if (timeThisFrame > 0) {
                fps = 1000 / timeThisFrame;
            }

        }

    }

    // Everything that needs to be updated goes in here
    // In later projects we will have dozens (arrays) of objects.
    // We will also do other things like collision detection.
    public void update() {


        Shoot newShoot = playerShip.shoot();
        // if the ship can shoot
        if(newShoot!=null) {
            shoots.add(newShoot);
        }

        // Update npc ships movement
        for(Ship ship : enemiesShips){
            if(ship.getBehavior() != null) {
                ship.getBehavior().update();
                if(ship.getY()>this.getHeight())
                    ship.setY(0);
            }
        }

        //move and remove if shoot is outside the window
        //Check if a shoot hit a ship
        Iterator<Shoot> iteratorShoot = shoots.iterator();
        while (iteratorShoot.hasNext()) {
            Shoot shoot = iteratorShoot.next();
            shoot.move();
            if(!shoot.isInside(getHeight(), getWidth())) {
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
                            int randomX = (int)(Math.abs(Math.random())*(getWidth()-playerShipBitmap.getWidth()));
                            //int randomX = (int)Math.random();
                            enemiesShips.add(new Ship(playerShipBitmap, randomX, 0,"Straight"));
                        }
                    }
                } else { // If the shoot go DOWN
                    if(playerShip.isInside(shoot)) {
                        //GAME OVER
                        playing = false;
                    }
                }
            }
        }

    }

    // Draw the newly updated scene
    public void draw() {

        // Make sure our drawing surface is valid or we crash
        /*if (ourHolder.getSurface().isValid()) {
            // Lock the canvas ready to draw
            canvas = ourHolder.lockCanvas();

            // Draw the background color
            canvas.drawColor(Color.argb(255,  26, 128, 182));

            // Choose the brush color for drawing
            paint.setColor(Color.argb(255,  249, 129, 0));

            // Make the text a bit bigger
            paint.setTextSize(45);

            // Display the current fps on the screen
            canvas.drawText("FPS:" + fps, 20, 40, paint);

            // Draw bob at bobXPosition, 200 pixels
            canvas.drawBitmap(bitmapBob, bobXPosition, 200, paint);

            // Draw everything to the screen
            ourHolder.unlockCanvasAndPost(canvas);
        }*/

        // Make sure our drawing surface is valid or we crash
        if (ourHolder.getSurface().isValid()) {

            // Lock the canvas ready to draw
            canvas = ourHolder.lockCanvas();

            //canvas.drawColor(Color.BLACK);
            movingBackground.draw(canvas);


            playerShip.draw(canvas);

            for (Shoot shoot : shoots) {
                shoot.draw(canvas);
            }

            for (Ship ships : enemiesShips) {
                ships.draw(canvas);
            }

            // Display the current fps on the screen
            //canvas.drawText("FPS:" + fps, 20, 40, paint);

            // Draw everything to the screen
            ourHolder.unlockCanvasAndPost(canvas);


        }

    }


    // If SimpleGameEngine Activity is paused/stopped
    // shutdown our thread.
    public void pause() {
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            Log.e("Error:", "joining thread");
        }

    }

    // If SimpleGameEngine Activity is started then
    // start our thread.
    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    // The SurfaceView class implements onTouchListener
    // So we can override this method and detect screen touches.
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        int x = (int)motionEvent.getX();
        playerShip.setX(x-25);
        invalidate();
        return true;

    }

}
