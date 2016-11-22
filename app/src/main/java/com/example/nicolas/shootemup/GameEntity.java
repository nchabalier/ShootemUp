package com.example.nicolas.shootemup;

import android.graphics.Bitmap;
import android.graphics.Point;

/**
 * Created by Vincent on 19/11/2016.
 */

public class GameEntity {

    protected Point position;

    protected Bitmap bitmap;
    protected Collider collider;

    public GameEntity(Point position, Bitmap bitmap, Collider collider) {
        this.position = position;
        this.bitmap = bitmap;
        this.collider = collider;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Collider getCollider() {
        return collider;
    }

    public void setCollider(Collider collider) {
        this.collider = collider;
    }
}
