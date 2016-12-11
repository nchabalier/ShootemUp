package com.example.nicolas.shootemup;

import android.graphics.Bitmap;
import android.graphics.Matrix;
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

        Matrix matrixRotation = new Matrix();
        this.behavior = new SteeringBehaviour(this,"Straight");
        this.coin = coin;

        matrixRotation.setRotate(180);
        this.bitmap = Bitmap.createBitmap(this.bitmap,0, 0,
                this.bitmap.getWidth(),this.bitmap.getHeight(),
                matrixRotation,false);
    }

    public SteeringBehaviour getBehavior() {
        return behavior;
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
