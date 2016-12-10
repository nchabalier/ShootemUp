package com.example.nicolas.shootemup;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

import java.util.List;

/**
 * Created by Nicolas on 04/11/2016.
 */

public class Ship extends GameEntity {

    protected int healthPoint;
    protected int currentHealth;
    protected int shield;
    protected List<Weapon> availableWeapon;
    protected Weapon activeWeapon;
    protected boolean invincible;
    protected int protectionStartTime;

    Ship(Bitmap bitmap, int x, int y) {
        super(new Point(x,y),bitmap,new Collider(x,y,bitmap.getWidth(),bitmap.getHeight()),0 ,0);
        activeWeapon = new Weapon(TypeWeapon.BASE,this);
        collider.setOwner(this);
        this.healthPoint =1;
        this.currentHealth = this.healthPoint;
    }

    @Override
    public void draw(Canvas can) {
        draw(can, position.x, position.y);
    }

    void draw(Canvas can, int px, int py) {
        can.drawBitmap(bitmap, px, py, null);
    }

    public void setX(int px) {
        position.x = px;
        collider.setX(px);
    }

    public void setY(int py) {
        position.y = py;
        collider.setY(py);
    }

    public int getX() {
        return position.x;
    }

    public int getY() {
        return position.y;
    }

    @Override
    public void setyBound(int yBound) {
        super.setyBound(yBound);
        activeWeapon.setyBound(yBound);
    }

    @Override
    public void setxBound(int xBound) {
        super.setxBound(xBound);
        activeWeapon.setxBound(xBound);
    }

    @Override
    public void update(List<GameEntity> listEntities, List<GameEntity> toDelete, List<GameEntity> toAdd) {

        activeWeapon.update(listEntities,toAdd );
        for(GameEntity entity : listEntities){
            if(entity.collider != null) {
                collider.isColliding(entity.collider, toDelete);
            }
        }
    }

    @Override
    public void onCollision(List<GameEntity> toDelete, GameEntity hitter) {
        if(hitter instanceof Shoot){
            Shoot shotHitting = (Shoot) hitter;
            currentHealth -= shotHitting.getPower();
        }
        else{
            currentHealth-=1;
        }
        if(currentHealth<=0){
            toDelete.add(this);
        }
    }

    @Override
    public void onDestroy() {

    }
}
