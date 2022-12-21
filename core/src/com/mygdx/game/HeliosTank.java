package com.mygdx.game;

import java.io.Serializable;

public class HeliosTank extends Tank implements Serializable {//tank with high destruction power

    public HeliosTank() {
        this.maxHealth = 100;
        this.maxFuel = 100;
        this.destructionPower = 1300;
        this.health = 100;
        this.fuel = 100;
        this.tankImage = "heliosTank.png";
    }
}
