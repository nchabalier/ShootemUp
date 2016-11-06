package com.example.nicolas.shootemup;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Nicolas on 04/11/2016.
 */

public class Ship {

    private Bitmap bitmap;
    private int x;
    private int y;
    private int cooldown = 5;
    private int cmp;

    Ship(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    Ship(Bitmap bitmap, int x, int y) {
        this(bitmap);
        this.x = x;
        this.y = y;
    }

    void draw(Canvas can) {
        draw(can, x, y);
    }

    void draw(Canvas can, int px, int py) {
        can.drawBitmap(bitmap, px, py, null);
    }

    public void setPosition(int px, int py)
    {
        this.x=px;
        this.y=py;
    }

    public void setX(int px) {
        this.x = px;
    }

    public void setY(int py) {
        this.y = py;
    }

    /**
     * Shoot if cooldown is finish, if not return null
     * @return
     */
    public Shoot shoot() {
        if(cmp < cooldown) {
            cmp++;
            return null;
        }
        cmp=0;
        return new Shoot(x+bitmap.getWidth()/2, y, 0, -1, 10, 1);
    }

    //Check if the shoot hit the ship
    public boolean isInside(Shoot shoot) {
        int shootX = shoot.getX();
        if(shootX > this.x && shootX< this.x + bitmap.getWidth()) {
            int shootY = shoot.getY();
            if(shootY > this.y && shootY< this.y + bitmap.getHeight()) {
                return true;
            }
        }
        return false;
    }
}
