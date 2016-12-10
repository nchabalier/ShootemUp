package com.example.nicolas.shootemup;

import java.util.List;

/**
 * Created by Vincent on 19/11/2016.
 */

public class Collider {

    //Position of top left point
    private int x;
    private int y;

    private int width;
    private int height;

    //Game entity that owns the collider
    private GameEntity owner;

    public Collider(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void isColliding(Collider other, List<GameEntity> toDelete){

        if(other==this)
            return;

        if(owner instanceof Shoot && other.owner instanceof Shoot){
            if(((Shoot) owner).isFiredByNPC() && ((Shoot) other.owner).isFiredByNPC())
                return;
        }

        int otherX = other.x;
        int otherY = other.y;

        if(!((x+width<otherX)||(x>otherX+other.width) || (y+height<otherY)||(y>otherY+other.height)))
            owner.onCollision(toDelete,other.owner );

    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setOwner(GameEntity owner) {
        this.owner = owner;
    }
}
