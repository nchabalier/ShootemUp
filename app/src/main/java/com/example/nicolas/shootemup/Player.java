package com.example.nicolas.shootemup;

/**
 * Created by Vincent on 19/11/2016.
 */

public class Player {

    private Ship playerShip;
    private int coin;
    public int score;

    public Player(Ship playerShip, int coin) {
        this.playerShip = playerShip;
        this.coin = coin;
    }

    public void setPlayerShip(Ship playerShip) {
        this.playerShip = playerShip;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public Ship getPlayerShip() {

        return playerShip;
    }

    public int getCoin() {
        return coin;
    }
}
