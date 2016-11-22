package com.example.nicolas.shootemup;

/**
 * Created by Vincent on 19/11/2016.
 */

public class Weapon {

    private TypeOfShoot typeOfShoot;
    private double rateOfFire;



    public Weapon(TypeOfShoot typeOfShoot, double rateOfFire) {
        this.typeOfShoot = typeOfShoot;
         this.rateOfFire = rateOfFire;
    }

    public TypeOfShoot getTypeOfShoot() {
        return typeOfShoot;
    }

    public void setTypeOfShoot(TypeOfShoot typeOfShoot) {
        this.typeOfShoot = typeOfShoot;
    }

    public double getRateOfFire() {
        return rateOfFire;
    }

    public void setRateOfFire(double rateOfFire) {
        this.rateOfFire = rateOfFire;
    }
}
