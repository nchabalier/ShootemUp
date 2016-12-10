package com.example.nicolas.shootemup;

import android.graphics.Point;
import android.text.format.Time;

import java.util.List;

/**
 * Created by Vincent on 19/11/2016.
 */



public class Weapon {

    private TypeOfShoot typeOfShoot;
    private double rateOfFire;
    private Time time;
    private long cooldown;              //amount of time it takes for another shoot
                                        // to be fired in ms
    private long timeLastShotFired;     //Time at which the last shot was fired
    private Ship owner;
    private int shotPower;
    private int shotSpeed;

    private int yBound;
    private int xBound;


    public Weapon(TypeWeapon typeWeapon, Ship owner) {
        this.owner=owner;
        time = new Time();

        switch(typeWeapon) {
            case BASE:
                cooldown = 500;
                shotPower = 1;
                shotSpeed = 15;
                break;
        }
    }

    public void update(List<GameEntity> listEntities, List<GameEntity> toAdd){
        fire(toAdd);
    }

    private void fire(List<GameEntity> toAdd){
        long mili;

        time.setToNow();
        mili = time.toMillis(true);
        if((mili-timeLastShotFired)>cooldown){
            int dirY;
            int yOffset =0;
            Boolean firedByNpc;

            if(owner instanceof ShipPNJ){
                dirY = 1;
                yOffset = ((ShipPNJ) owner).bitmap.getHeight()+5;
                firedByNpc = true;
            }
            else {
                dirY = -1;
                firedByNpc = false;
            }
            toAdd.add(new Shoot(new Collider(owner.getX(),owner.getY()-owner.getBitmap().getHeight(),0,0)
                    ,null,new Point(owner.getX()+owner.bitmap.getWidth()/2,owner.getY()+yOffset),0,dirY,
                    shotSpeed,shotPower,xBound ,yBound, firedByNpc));
            timeLastShotFired=mili;
        }
    }

    public void setxBound(int xBound) {
        this.xBound = xBound;
    }

    public void setyBound(int yBound) {
        this.yBound = yBound;
    }
}
