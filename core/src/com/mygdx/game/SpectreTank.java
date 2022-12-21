package com.mygdx.game;

import java.io.Serializable;

public class SpectreTank extends Tank implements Serializable {//tank with fuell

    public SpectreTank() {
        this.maxHealth = 100;
        this.maxFuel = 120;
        this.destructionPower = 1000;
        this.health = 100;
        this.fuel = 120;
        this.tankImage = "spectreTank.png";
    }
}
