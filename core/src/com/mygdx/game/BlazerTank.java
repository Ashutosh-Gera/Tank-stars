package com.mygdx.game;

public class BlazerTank extends Tank{

    public BlazerTank() {//tank with high health
        this.maxHealth = 120;
        this.maxFuel = 100;
        this.destructionPower = 1000;
        this.health = 120;
        this.fuel = 100;
        this.tankImage = "blazerTank.png";
    }

}
