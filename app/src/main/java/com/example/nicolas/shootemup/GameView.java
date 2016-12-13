package com.example.nicolas.shootemup;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.text.format.Time;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    Bitmap commonEnnemyBitmap;
    Bitmap bossBitmap;

    Time timeMesuring;
    long gamebegin;

    Context context;


    //---------------------------------------
    private Bitmap backGround;
    private Player player;
    private double time;
    private List<GameEntity> gameEntities;
    private MovingBackground2 movingBackground;
    private int ennemiesKilled;
    private int cycleNumber;
    private long timeLastEnnemyGenerated;
    private Boolean bossPhase;
    private ScoreBoard score;

    // When the we initialize (call new()) on gameView
    // This special constructor method runs
    public GameView(Context context,ScoreBoard score) {
        // The next line of code asks the
        // SurfaceView class to set up our object.
        // How kind.
        super(context);

        this.score = score;
        this.context = context;

        timeMesuring = new Time();
        ennemiesKilled = 0;
        cycleNumber = 0;

        //Initialize entities list
        gameEntities = new ArrayList<GameEntity>();


        // Initialize ourHolder and paint objects
        ourHolder = getHolder();
        paint = new Paint();

        // Load Bob from his .png file
        bitmapBob = BitmapFactory.decodeResource(this.getResources(), R.drawable.ship);
        //-----------------------------

        if((getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK) ==
                Configuration.SCREENLAYOUT_SIZE_LARGE
                || (getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK) ==
                Configuration.SCREENLAYOUT_SIZE_XLARGE)
            bossBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.spacestationlarge);
        else
            bossBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.spacestation);

        backGround = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.space4);
        movingBackground = new MovingBackground2(backGround);


//        shoots = new ArrayList<Shoot>();
//        enemiesShips = new ArrayList<Ship>();



        //configure bitmap for ennemies
        commonEnnemyBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ship);
        Matrix matrixRotation = new Matrix();
        matrixRotation.setRotate(180);
        commonEnnemyBitmap = Bitmap.createBitmap(commonEnnemyBitmap,0, 0,
                commonEnnemyBitmap.getWidth(),commonEnnemyBitmap.getHeight(),
                matrixRotation,false);

        bossPhase = false;

        // Set our boolean to true - game on!
        playing = true;


        setup();

    }

    @Override
    public void run() {


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Bitmap playerShipBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.mship1);
        Ship playerShip = new Ship(playerShipBitmap,new Point(getRight()/2,getBottom()
                - playerShipBitmap.getHeight()),score.getWeaponType(),3,score.getShipSpeed(),score.getShootSpeed());

        gameEntities.add(playerShip);

        // Initialize player and it's Attributes
        player = new Player(playerShip, 0);

        player.getPlayerShip().setxBound(getRight());
        player.getPlayerShip().setyBound(getBottom());


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

    // Method used to setup the game correctly
    private void setup() {
        timeMesuring.setToNow();
        gamebegin = timeMesuring.toMillis(true);

    }

    //Random Generation of ennemies
    private void randomEnnemyGenerator() {
        long currentTime;
        Random rand = new Random();
        int randomX = rand.nextInt((getWidth() - commonEnnemyBitmap.getWidth()) + 1);

        timeMesuring.setToNow();
        currentTime = timeMesuring.toMillis(true);

        if (currentTime - timeLastEnnemyGenerated > 1000-cycleNumber*50) {
            timeLastEnnemyGenerated = currentTime;
            if (ennemiesKilled < 20 * cycleNumber) {
                gameEntities.add(new ShipPNJ(new Point(randomX, 0), commonEnnemyBitmap, 10));
            } else if (!bossPhase && ennemiesKilled >= 20 * cycleNumber) {
                bossPhase = true;
                generateBoss();
            }
        }
    }

    // Method used to generate a Big Bad Boss
    private void generateBoss() {
        ShipPNJ boss = new ShipPNJ(new Point(0, 0), bossBitmap, 1000);
        boss.makeBoss();
        gameEntities.add(boss);
    }

    // Method to update the score depending on the time spent
    // in game
    public void updateScore() {
        timeMesuring.setToNow();
        player.score += (timeMesuring.toMillis(true) - gamebegin) / 6000;
    }

    // Everything that needs to be updated goes in here
    // In later projects we will have dozens (arrays) of objects.
    // We will also do other things like collision detection.
    public void update() {
        List<GameEntity> toDelete = new ArrayList<GameEntity>();
        List<GameEntity> toAdd = new ArrayList<GameEntity>();

        updateScore();
        randomEnnemyGenerator();

        for (GameEntity entity : gameEntities) {
            entity.update(gameEntities, toDelete, toAdd);
        }

        for (GameEntity entity : toAdd) {
            gameEntities.add(entity);
        }


        for (GameEntity entity : toDelete) {
            //if ennemy died
            if (entity instanceof ShipPNJ) {
                ennemiesKilled += 1;
                ((ShipPNJ) entity).onDestroy(player);
                Log.d("Score",Integer.toString(player.score));
                Log.d("Coin",Integer.toString(player.getCoin()));
                if(((ShipPNJ) entity).isBoss()) {
                    bossPhase = false;
                    ennemiesKilled=0;
                    cycleNumber++;
                }
            }
            //if playerShip has been killed
            else if (entity instanceof Ship)
            {
                if(score.getScore()<player.score){
                    score.setScore(player.score);
                }
                score.setCoin(score.getCoin()+player.getCoin());
                ScoreBoardDAO database = new ScoreBoardDAO(context);
                database.open();
                database.update(score);
                System.gc();
                database.close();
                if(context instanceof GameActivity) ((GameActivity) context).end();
            }
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

            for (GameEntity entity : gameEntities) {
                entity.draw(canvas);
            }

            Paint color = new Paint();

            color.setColor(Color.WHITE);
            canvas.drawText(Integer.toString(player.score),getRight()-50,15,color);

            // Draw everything to the screen
            ourHolder.unlockCanvasAndPost(canvas);


        }

    }


    // If SimpleGameEngine Activity is paused/stopped
    // shutdown our thread.
    public void pause() {
        playing = false;
        System.gc();
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
        if (motionEvent.getX() == player.getPlayerShip().getX())
            return true;

        int x = (int) motionEvent.getX();

        player.getPlayerShip().setPoint(x - player.getPlayerShip().bitmap.getWidth());
        invalidate();
        return true;

    }

//    @Override
//    public boolean onDragEvent(DragEvent event) {
//
//        int x = event.getAction();
//        if (x == event.ACTION_DROP) {
//            player.getPlayerShip().setPoint((int) event.getX() - 25);
//            invalidate();
//        }
//        return true;
//    }
}