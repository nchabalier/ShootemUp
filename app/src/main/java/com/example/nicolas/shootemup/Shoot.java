package com.example.nicolas.shootemup;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

/**
 * Created by Nicolas on 05/11/2016.
 */

public class Shoot extends GameEntity{
    private int dirX; //between -1 and 1
    private int dirY; //between -1 and 1
    private int speed;
    private int power;
    private static int RADIUS = 5;

    public Shoot(Collider collider, Bitmap bitmap, Point position, int dirX, int dirY, int speed, int power) {
        super(position,bitmap,collider);
        this.dirX = dirX;
        this.dirY = dirY;
        this.speed = speed;
        this.power = power;
    }

    public void move() {
        position.x += (int)(speed * dirX);
        position.y += (int)(speed * dirY);
    }

    public void draw(Canvas can) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        can.drawCircle(position.x, position.y, RADIUS, paint);
    }

    public int getDirY() {
        return dirY;
    }


    //FIXME: can be optimized
    public boolean isInside(int height, int width){
        if(position.x>0 && position.x<width) {
            if(position.y>0 && position.y<height){
                return true;
            }
        }
        return false;
    }

    public int getX() {
        return position.x;
    }

    public int getY() {
        return position.y;
    }
}
