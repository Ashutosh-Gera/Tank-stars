package com.mygdx.game;

public class HeliosTank extends Tank{//tank with high destruction power

    public HeliosTank() {
        this.maxHealth = 100;
        this.maxFuel = 100;
        this.destructionPower = 15;
        this.health = 100;
        this.fuel = 100;
        this.tankImage = "heliosTank.png";
    }
}
