package com.example.nicolas.shootemup;

/**
 * Created by jojoX on 10/11/2016.
 */

public class SteeringBehaviour {
    private Ship ship;
    private String type;
    private int speed;


    public SteeringBehaviour(Ship ship, String type) {
        this.ship = ship;
        this.type = type;
        if(type.equals("Straight"))
            speed=10;
    }

    public int getSpeed() {
        return speed;
    }

    void goStraight(){
        ship.setY(speed+ship.getY());
    }


    void update(){
        if (type.equals("Straight"))
            goStraight();
    }
}
