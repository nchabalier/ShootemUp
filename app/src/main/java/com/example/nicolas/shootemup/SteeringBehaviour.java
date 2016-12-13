package com.example.nicolas.shootemup;

/**
 * Created by jojoX on 10/11/2016.
 */

public class SteeringBehaviour {
    private Ship ship;
    private String type;
    private int speed;
    private boolean goLeft;
    private int offset;

    public SteeringBehaviour(Ship ship, String type) {
        this.ship = ship;
        this.type = type;
        offset = -1;
        if(type.equals("Straight"))
            speed=10;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    void goStraight(){
        ship.setY(speed+ship.getY());
    }

    void zigzag(){
        if(speed+ship.getX()>ship.getxBound()-ship.getBitmap().getWidth())
            offset=-1;
        else if(ship.getX()-speed<0)
            offset=1;

        ship.setX(ship.getX()+offset*speed);
    }


    void update(){
        if (type.equals("Straight"))
            goStraight();
        else if(type.equals("ZigZag"))
            zigzag();
    }
}
