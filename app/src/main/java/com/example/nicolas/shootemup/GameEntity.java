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

    protected int xBound;
    protected int yBound;

    public GameEntity(Point position, Bitmap bitmap, Collider collider, int xBound, int yBound) {
        this.position = position;
        this.bitmap = bitmap;
        this.collider = collider;
        this.xBound = xBound;
        this.yBound = yBound;
    }

    public Bitmap getBitmap() {
        return bitmap;
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

    public void setxBound(int xBound) {
        this.xBound = xBound;
    }

    public void setyBound(int yBound) {
        this.yBound = yBound;
    }
}
