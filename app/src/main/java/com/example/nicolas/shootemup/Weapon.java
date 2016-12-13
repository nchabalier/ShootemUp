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

    private static int yBound;
    private static int xBound;


    public Weapon(TypeWeapon typeWeapon, Ship owner) {
        this.owner=owner;
        time = new Time();

        switch(typeWeapon) {
            case BASE:
                cooldown = 500;
                shotPower = 1;
                shotSpeed = 15;
                break;
            case BAZOOKA:
                cooldown = 2000;
                shotPower = 2;
                shotSpeed = 15;
                typeOfShoot = TypeOfShoot.SEARCHINGMISSLE;
                break;
            case GATTELING:
                cooldown = 500;
                shotPower = 1;
                shotSpeed = 20;
                typeOfShoot = TypeOfShoot.DOUBLE;
                break;
            case BOSSWEAPON:
                cooldown = 600;
                shotPower = 2;
                shotSpeed = 30;
                typeOfShoot = TypeOfShoot.LIKEABOSS;
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
                yOffset = ((ShipPNJ) owner).bitmap.getHeight()+((ShipPNJ) owner).getBehavior().getSpeed();
                firedByNpc = true;
            }
            else {
                dirY = -1;
                yOffset = -5;
                firedByNpc = false;
            }
            if(typeOfShoot==TypeOfShoot.SEARCHINGMISSLE)
                toAdd.add(new Missile(new Collider(owner.getX(),owner.getY()-owner.getBitmap().getHeight(),0,0)
                        ,null,new Point(owner.getX()+owner.bitmap.getWidth()/2,owner.getY()+yOffset),0,dirY,
                        shotSpeed,shotPower,xBound ,yBound, firedByNpc));
            else if(typeOfShoot==TypeOfShoot.DOUBLE){
                toAdd.add(new Shoot(new Collider(owner.getX(),owner.getY()-owner.getBitmap().getHeight(),0,0)
                        ,null,new Point(owner.getX(),owner.getY()+yOffset),0,dirY,
                        shotSpeed,shotPower,xBound ,yBound, firedByNpc));
                toAdd.add(new Shoot(new Collider(owner.getX(),owner.getY()-owner.getBitmap().getHeight(),0,0)
                        ,null,new Point(owner.getX()+owner.bitmap.getWidth(),owner.getY()+yOffset),0,dirY,
                        shotSpeed,shotPower,xBound ,yBound, firedByNpc));
            }
            else if(typeOfShoot==TypeOfShoot.LIKEABOSS){
                toAdd.add(new Shoot(new Collider(owner.getX(),owner.getY()-owner.getBitmap().getHeight(),0,0)
                        ,null,new Point(owner.getX(),owner.getY()+yOffset),0,dirY,
                        shotSpeed,shotPower,xBound ,yBound, firedByNpc));
                toAdd.add(new Shoot(new Collider(owner.getX(),owner.getY()-owner.getBitmap().getHeight(),0,0)
                        ,null,new Point(owner.getX()+owner.bitmap.getWidth()/2,owner.getY()+yOffset),0,dirY,
                        shotSpeed,shotPower,xBound ,yBound, firedByNpc));
                toAdd.add(new Shoot(new Collider(owner.getX(),owner.getY()-owner.getBitmap().getHeight(),0,0)
                        ,null,new Point(owner.getX()+owner.bitmap.getWidth(),owner.getY()+yOffset),0,dirY,
                        shotSpeed,shotPower,xBound ,yBound, firedByNpc));
            }
            else
                toAdd.add(new Shoot(new Collider(owner.getX(),owner.getY()-owner.getBitmap().getHeight(),0,0)
                        ,null,new Point(owner.getX()+owner.bitmap.getWidth()/2,owner.getY()+yOffset),0,dirY,
                        shotSpeed,shotPower,xBound ,yBound, firedByNpc));
            timeLastShotFired=mili;
        }
    }

    public void setxBound(int xBound) {
        Weapon.xBound = xBound;
    }

    public void setyBound(int yBound) {
        Weapon.yBound = yBound;
    }
}
