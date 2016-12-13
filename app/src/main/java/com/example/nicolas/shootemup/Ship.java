package com.example.nicolas.shootemup;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Vibrator;

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

    private int speed;
    private int point;

    Ship(Bitmap bitmap, int x, int y) {
        super(new Point(x,y),bitmap,new Collider(x,y,bitmap.getWidth(),bitmap.getHeight()));
        activeWeapon = new Weapon(TypeWeapon.BASE,this);
        collider.setOwner(this);
        this.healthPoint =1;
        this.currentHealth = this.healthPoint;
        speed = 10;
    }

    Ship(Bitmap bitmap,Point position,TypeWeapon typeWeapon, int hp, int speed, int shootSpeed){
        super(position,bitmap,new Collider(position.x,position.y,bitmap.getWidth(),bitmap.getHeight()));
        collider.setOwner(this);
        activeWeapon=new Weapon(typeWeapon,this);
        activeWeapon.cooldown-=shootSpeed;
        this.speed=speed;
        this.healthPoint=hp;
        this.currentHealth = this.healthPoint;

    }

    @Override
    public void draw(Canvas can) {
        draw(can, position.x, position.y);
    }

    void draw(Canvas can, int px, int py) {
        can.drawBitmap(bitmap, px, py, null);
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public void setX(int px) {
        position.x = px;
        collider.setX(px);
    }

    public void setY(int py) {
        position.y = py;
        collider.setY(py);
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getX() {
        return position.x;
    }

    public int getY() {
        return position.y;
    }

    public void setyBound(int yBound) {
        GameEntity.yBound = yBound;
        activeWeapon.setyBound(yBound);
    }

    public void setxBound(int xBound) {
        GameEntity.xBound = xBound;
        activeWeapon.setxBound(xBound);
    }

    public void setActiveWeapon(Weapon activeWeapon) {
        this.activeWeapon = activeWeapon;
    }

    public void move(){
        int direction;
        int distanceToPoint;

        if(position.x > point){
            direction=-1;
            distanceToPoint = position.x-point;
        } else {
            direction=1;
            distanceToPoint = point - position.x;
        }

        if(distanceToPoint > speed){
            setX(position.x+direction*speed);
        }
        else
            setX(point);
    }
    @Override
    public void update(List<GameEntity> listEntities, List<GameEntity> toDelete, List<GameEntity> toAdd) {

        activeWeapon.update(listEntities,toAdd );
        for(GameEntity entity : listEntities){
            if(entity.collider != null) {
                collider.isColliding(entity.collider, toDelete);
            }
        }

        if(!toDelete.contains(this))
            move();
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
