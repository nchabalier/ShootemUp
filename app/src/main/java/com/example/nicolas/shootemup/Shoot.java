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
    protected int dirX;                   //between -1 and 1
    protected int dirY;                   //between -1 and 1
    protected int speed;
    protected int power;
    protected static int RADIUS = 5;
    protected boolean firedByNPC;
    public Shoot(Collider collider, Bitmap bitmap, Point position, int dirX, int dirY, int speed, int power, int xBound, int yBound, Boolean firedByNpc) {
        super(position,bitmap,collider);
        Collider colider = new Collider(position.x-RADIUS,position.y-RADIUS,2*RADIUS,2*RADIUS,this);
        this.collider=colider;

        this.dirX = dirX;
        this.dirY = dirY;
        this.speed = speed;
        this.power = power;
        collider.setOwner(this);
        this.isNpc = firedByNpc;
    }

    public void move() {
        position.x += (speed * dirX);
        collider.setX(position.x-RADIUS);
        position.y += (speed * dirY);
        collider.setY(position.y-RADIUS);
    }

    public void updateDirection(List<GameEntity> listEntities) {
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


    @Override
    public void update(List<GameEntity> listEntities, List<GameEntity> toDelete, List<GameEntity> toAdd) {

        for(GameEntity entity : listEntities){
            if(entity.collider != null) {
                collider.isColliding(entity.collider, toDelete);
            }
        }
        if(!toDelete.contains(this)){
            //Update direction is used for missile
            updateDirection(listEntities);
            move();
        }
        if(isOutOfBounds())
            toDelete.add(this);
    }
}
