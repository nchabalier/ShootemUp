package com.example.nicolas.shootemup;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

import java.util.List;

/**
 * Created by Vincent on 19/11/2016.
 */

public class GameEntity  {

    protected Point position;

    protected Bitmap bitmap;
    protected Collider collider;
    protected boolean isNpc;

    protected static int xBound;
    protected static int yBound;

    public GameEntity(Point position, Bitmap bitmap, Collider collider) {
        this.position = position;
        this.bitmap = bitmap;
        this.collider = collider;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public boolean isNpc() {
        return isNpc;
    }

    public void update(List<GameEntity> listEntities, List<GameEntity> toDelete,
                       List<GameEntity> toAdd) {
    }

    public void onDestroy() {

    }

    public void draw(Canvas can){

    }

    public void onCollision(List<GameEntity> toDelete, GameEntity hitter){

    }

    protected boolean isOutOfBounds(){
        boolean outOfBounds = false;

        if(position.x <0 || position.y <0 || position.x > xBound || position.y > yBound)
            outOfBounds = true;

        return outOfBounds;
    }

    public static int getxBound() {
        return xBound;
    }
}
