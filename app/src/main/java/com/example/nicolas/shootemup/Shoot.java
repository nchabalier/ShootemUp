package com.example.nicolas.shootemup;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by Nicolas on 05/11/2016.
 */

public class Shoot {
    private int x;
    private int y;
    private int dirX; //between -1 and 1
    private int dirY; //between -1 and 1
    private int speed;
    private int power;
    private static int RADIUS = 5;

    public Shoot(int x, int y, int dirX, int dirY, int speed, int power) {
        this.x = x;
        this.y = y;
        this.dirX = dirX;
        this.dirY = dirY;
        this.speed = speed;
        this.power = power;
    }

    public void move() {
        this.x += (int)(speed * dirX);
        this.y += (int)(speed * dirY);
    }

    public void draw(Canvas can) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        can.drawCircle(x, y, RADIUS, paint);
    }

    public int getDirY() {
        return dirY;
    }


    //FIXME: can be optimized
    public boolean isInside(int height, int width){
        if(x>0 && x<width) {
            if(y>0 && y<height){
                return true;
            }
        }
        return false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
