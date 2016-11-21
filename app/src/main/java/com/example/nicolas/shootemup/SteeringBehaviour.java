package com.example.nicolas.shootemup;

/**
 * Created by jojoX on 10/11/2016.
 */

public class SteeringBehaviour {
    private Ship ship;
    private String type;


    public SteeringBehaviour(Ship ship, String type) {
        this.ship = ship;
        this.type = type;
    }

    void goStraight(){
        int speed=10;

        ship.setY(speed+ship.getY());
    }

    void update(){
        if (type.equals("Straight"))
            goStraight();
    }
}
