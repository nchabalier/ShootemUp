package com.example.nicolas.shootemup;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
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

        //Initialize entities list
        gameEntities = new ArrayList<GameEntity>();


        // Initialize ourHolder and paint objects
        ourHolder = getHolder();
        paint = new Paint();

        // Load Bob from his .png file
        bitmapBob = BitmapFactory.decodeResource(this.getResources(), R.drawable.ship);
        //-----------------------------

        backGround = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.space3);
        movingBackground = new MovingBackground2(backGround);


//        shoots = new ArrayList<Shoot>();
//        enemiesShips = new ArrayList<Ship>();

        Bitmap playerShipBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ship);
        Ship playerShip = new Ship(playerShipBitmap, 0, 775);

        gameEntities.add(playerShip);

        // Initialize player and it's Attributes
        player = new Player(playerShip,0);

        //Add enemies for test
//        enemiesShips.add(new Ship(playerShipBitmap, 300, 0, "Straight"));

        // Set our boolean to true - game on!
        playing = true;

    }

    @Override
    public void run() {
        ShipPNJ testNpc;


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        player.getPlayerShip().setY(getBottom()-player.getPlayerShip().getBitmap().getHeight());
        player.getPlayerShip().setxBound(getRight());
        player.getPlayerShip().setyBound(getBottom());

        testNpc = new ShipPNJ(new Point(20,20),player.getPlayerShip().getBitmap(),10);
        testNpc.setxBound(getRight());
        testNpc.setyBound(getBottom());

        gameEntities.add(testNpc);

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
        List<GameEntity> toDelete = new ArrayList<GameEntity>();
        List<GameEntity> toAdd = new ArrayList<GameEntity>();

        for(GameEntity entity : gameEntities) {
            entity.update(gameEntities,toDelete,toAdd );
        }

        for(GameEntity entity : toAdd){
            gameEntities.add(entity);
        }

        for(GameEntity entity : toDelete){
            entity.onDestroy();
            gameEntities.remove(entity);
        }

    }

    // Draw the newly updated scene
    public void draw() {

        // Make sure our drawing surface is valid or we crash
        if (ourHolder.getSurface().isValid()) {

            // Lock the canvas ready to draw
            canvas = ourHolder.lockCanvas();

            //canvas.drawColor(Color.BLACK);
            movingBackground.draw(canvas);

            for(GameEntity entity : gameEntities){
                entity.draw(canvas);
            }

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
        player.getPlayerShip().setX(x-25);
        invalidate();
        return true;

    }

}
