package com.mygdx.game;

public class SpectreTank extends Tank{//tank with fuell

    public SpectreTank() {
        this.maxHealth = 100;
        this.maxFuel = 120;
        this.destructionPower = 1000;
        this.health = 100;
        this.fuel = 120;
        this.tankImage = "spectreTank.png";
    }
}
