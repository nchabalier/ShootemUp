package com.example.nicolas.shootemup;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Point;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Vincent on 19/11/2016.
 */

public class ShipPNJ extends Ship {

    private SteeringBehaviour behavior;
    private int coin;
    private boolean isBoss;

    public ShipPNJ(Point position, Bitmap bitmap, int coin) {
        super(bitmap,position.x,position.y);

        this.behavior = new SteeringBehaviour(this,"Straight");
        this.coin = coin;
        isNpc = true;
    }

    public void makeBoss(){
        activeWeapon = new Weapon(TypeWeapon.BOSSWEAPON,this);
        this.behavior = new SteeringBehaviour(this,"ZigZag");
        this.healthPoint =20;
        this.currentHealth = this.healthPoint;
        coin = 1000;
        isBoss=true;
        behavior.setSpeed(5);

    }

    public void makeSuperMob(){
        activeWeapon = new Weapon(TypeWeapon.GATTELING,this);
        this.healthPoint =2;
        this.currentHealth = this.healthPoint;

    }

    public boolean isBoss() {
        return isBoss;
    }

    public SteeringBehaviour getBehavior() {
        return behavior;
    }

    @Override
    public void update(List<GameEntity> listEntities, List<GameEntity> toDelete, List<GameEntity> toAdd) {
        if(isOutOfBounds()) {
            Random rand = new Random();
            int superiorBound = (xBound-bitmap.getWidth()) + 1;

            if(superiorBound!=0) {
                int randomX = rand.nextInt(superiorBound);
                setY(0);
                setX(randomX);
            }
        }

        for(GameEntity entity : listEntities){
            if(entity.collider != null) {
                collider.isColliding(entity.collider, toDelete);
            }
        }
        if(!toDelete.contains(this)){
            behavior.update();
        }
        activeWeapon.update(listEntities,toAdd);
    }

    public void onDestroy(Player player){
        player.setCoin(player.getCoin()+coin);
        player.score+=coin*10;
    }
}
