package com.example.nicolas.shootemup;

import android.graphics.Bitmap;
import android.graphics.Point;

import java.util.List;

/**
 * Created by Nicolas on 11/12/2016.
 */

public class Missile extends Shoot {


    public Missile(Collider collider, Bitmap bitmap, Point position, int dirX, int dirY, int speed, int power, int xBound, int yBound, Boolean firedByNpc) {
        super(collider, bitmap, position, dirX, dirY, speed, power, xBound, yBound, firedByNpc);
    }



    public GameEntity findNearestTarget(List<GameEntity> listEntities) {

        double minDist = Double.MAX_VALUE;
        GameEntity nearestTarget = null;

        for(GameEntity entity : listEntities){
            if(entity instanceof ShipPNJ) {
                //Distance square to avoid slow sqrt computing
                double newDist = Util.dist2(this.position, entity.position);
                if(newDist<minDist) {
                    minDist = newDist;
                    nearestTarget = entity;
                }
            }
        }
        return nearestTarget;
    }

    @Override
    public void updateDirection(List<GameEntity> listEntities) {

        GameEntity target = findNearestTarget(listEntities);

        if(target!=null) {
            double newDirX = target.position.x - this.position.x;
            double newDirY = target.position.y - this.position.y;
            double dist = Util.dist(target.position, this.position);
            newDirX /= dist;
            newDirY /= dist;
            dirX += newDirX;
            dirX += newDirY;
        }
    }

}
