package com.example.nicolas.shootemup;

/**
 * Created by Nicolas on 12/11/2016.
 */

public class ScoreBoard {

    private long id;
    private String name;
    private long score;
    private int shootSpeed;
    private int shipSpeed;
    private TypeWeapon weaponType;

    public ScoreBoard() {
        super();
    }

    public ScoreBoard(long id, String name, long score) {
        super();
        this.id = id;
        this.name = name;
        this.score = score;
    }

    public ScoreBoard(long id, String name, long score, int shootSpeed, int shipSpeed, TypeWeapon weaponType) {
        this.id = id;
        this.name = name;
        this.score = score;
        this.shootSpeed = shootSpeed;
        this.shipSpeed = shipSpeed;
        this.weaponType = weaponType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public long getScore() {
        return score;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public int getShootSpeed() {
        return shootSpeed;
    }

    public void setShootSpeed(int shootSpeed) {
        this.shootSpeed = shootSpeed;
    }

    public int getShipSpeed() {
        return shipSpeed;
    }

    public void setShipSpeed(int shipSpeed) {
        this.shipSpeed = shipSpeed;
    }

    public TypeWeapon getWeaponType() {
        return weaponType;
    }

    public void setWeaponType(TypeWeapon weaponType) {
        this.weaponType = weaponType;
    }
}
