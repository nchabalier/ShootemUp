package com.example.nicolas.shootemup;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

import java.util.List;

/**
 * Created by Nicolas on 04/11/2016.
 */

public class Ship extends GameEntity{


    protected int cooldown = 5;
    protected int cmp;
    protected int healthPoint;
    protected int currentHealth;
    protected int shield;
    protected List<Weapon> availableWeapon;
    protected Weapon activeWeapon;

    public Ship(Point position, Bitmap bitmap, Collider collider, int cmp, int health, int currentHealth, int shield, List<Weapon> availableWeapon, Weapon activeWeapon) {
        super(position, bitmap, collider);
        this.cmp = cmp;
        this.healthPoint = health;
        this.currentHealth = currentHealth;
        this.shield = shield;
        this.availableWeapon = availableWeapon;
        this.activeWeapon = activeWeapon;
    }

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    public int getCmp() {
        return cmp;
    }

    public void setCmp(int cmp) {
        this.cmp = cmp;
    }

    public int getHealthPoint() {
        return healthPoint;
    }

    public void setHealthPoint(int healthPoint) {
        this.healthPoint = healthPoint;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }

    public int getShield() {
        return shield;
    }

    public void setShield(int shield) {
        this.shield = shield;
    }

    public List<Weapon> getAvailableWeapon() {
        return availableWeapon;
    }


    public void setAvailableWeapon(List<Weapon> availableWeapon) {
        this.availableWeapon = availableWeapon;
    }



    public Weapon getActiveWeapon() {
        return activeWeapon;
    }


    public void setActiveWeapon(Weapon activeWeapon) {
        this.activeWeapon = activeWeapon;
    }

    void draw(Canvas can) {
        draw(can, position.x, position.y);
    }

    void draw(Canvas can, int px, int py) {
        can.drawBitmap(bitmap, px, py, null);
    }



    public void setX(int px) {
        position.x = px;
    }

    public void setY(int py) {
        position.y = py;
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
        int posX = position.x+bitmap.getWidth()/2;
        Point pos = new Point(posX,position.y);
        return new Shoot(collider,bitmap,pos, 0, -1, 10, 1);
    }



    //Check if the shoot hit the ship
    public boolean isInside(Shoot shoot) {
        int shootX = shoot.getX();
        if(shootX > position.x && shootX< position.x + bitmap.getWidth()) {
            int shootY = shoot.getY();
            if(shootY > position.y && shootY< position.y + bitmap.getHeight()) {
                return true;
            }
        }
        return false;
    }
}
