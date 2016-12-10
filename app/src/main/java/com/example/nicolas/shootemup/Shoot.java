package com.example.nicolas.shootemup;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.List;

/**
 * Created by Nicolas on 05/11/2016.
 */

public class Shoot extends GameEntity {
    private int dirX;                   //between -1 and 1
    private int dirY;                   //between -1 and 1
    private int speed;
    private int power;
    private static int RADIUS = 5;
    private boolean firedByNPC;

    public Shoot(Collider collider, Bitmap bitmap, Point position, int dirX, int dirY, int speed, int power, int xBound, int yBound, Boolean firedByNpc) {
        super(position,bitmap,collider,xBound ,yBound );
        this.dirX = dirX;
        this.dirY = dirY;
        this.speed = speed;
        this.power = power;
        collider.setOwner(this);
        this.firedByNPC = firedByNpc;
    }

    public void move() {
        position.x += (speed * dirX);
        collider.setX(position.x);
        position.y += (speed * dirY);
        collider.setY(position.y);
    }

    @Override
    public void draw(Canvas can) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        can.drawCircle(position.x, position.y, RADIUS, paint);
    }

    public int getPower() {
        return power;
    }

    @Override
    public void onCollision(List<GameEntity> toDelete, GameEntity hitter) {
        toDelete.add(this);
    }

    @Override
    public void onDestroy() {

    }

    private boolean isOutOfBounds(){
        boolean outOfBounds = false;

        if(position.x <0 || position.y <0 || position.x > xBound || position.y > yBound)
            outOfBounds = true;

        return outOfBounds;
    }


    @Override
    public void update(List<GameEntity> listEntities, List<GameEntity> toDelete, List<GameEntity> toAdd) {

        for(GameEntity entity : listEntities){
            if(entity.collider != null) {
                collider.isColliding(entity.collider, toDelete);
            }
        }
        if(!toDelete.contains(this)){
            move();
        }
        if(isOutOfBounds())
            toDelete.add(this);
    }

    public boolean isFiredByNPC() {
        return firedByNPC;
    }
}
