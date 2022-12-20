package com.mygdx.game;

public class Tank {
    private int[] position;
    private int health;
    private int fuel;
    protected int maxFuel;
    protected int maxHealth;
    protected int destructionPower;
    protected int tankImage;

    public int getHealth() {
        return this.health;
    }

    public int getFuel() {
        return this.fuel;
    }

    public void reduceFuel(){
        this.fuel--;
    }

    public void boostFuel(){
        this.fuel = this.maxFuel;
    }

}
