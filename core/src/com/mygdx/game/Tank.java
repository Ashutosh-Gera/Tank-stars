package com.mygdx.game;

import java.io.Serializable;

public abstract class Tank implements Serializable {
    private int[] position = {0, 0};
    protected int health;
    protected int fuel;
    protected int maxFuel;
    protected int maxHealth;
    protected int destructionPower;
    protected String tankImage;

    public int getHealth() {
        return this.health;
    }

    public int getFuel() {
        return this.fuel;
    }

    public void reduceFuel(int used_fuel){
        this.fuel -= used_fuel;
    }

    public void boostFuel(){
        this.fuel = 100;
    }

    public String getTankImage() {
        return tankImage;
    }

    public void changePosition(int x, int y){
        this.reduceFuel(this.position[0] - x>0?this.position[0] - x: x-this.position[0]);
        this.setPosition(x, y);
    }

    public void setPosition(int x, int y) {
        this.position = new int[]{x, y};
    }

    public void reduceHealth(float distance, int destructionPower) {
        if (destructionPower / distance > 1){
            this.health -= destructionPower / distance;
        }
    }

    public void reduceHealth(float distance) {
        if (1400 / distance > 1){
            this.health -= 1400 / distance;
        }
    }

    public int getDestructionPower() {
        return this.destructionPower;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getMaxFuel() {
        return maxFuel;
    }

    public int[] getPosition() {
        return this.position;
    }
}
