package com.example.nicolas.shootemup;

import android.graphics.Bitmap;
import android.graphics.Point;

import java.util.List;

/**
 * Created by Vincent on 19/11/2016.
 */

public class ShipPNJ extends Ship {

    private SteeringBehaviour behavior;
    private int coin;

    public ShipPNJ(Point position, Bitmap bitmap, int coin) {
        super(bitmap,position.x,position.y);
        this.behavior = new SteeringBehaviour(this,"Straight");
        this.coin = coin;
    }

    @Override
    public void update(List<GameEntity> listEntities, List<GameEntity> toDelete, List<GameEntity> toAdd) {
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
}
