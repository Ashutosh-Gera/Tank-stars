package com.mygdx.game;

public abstract class Tank {
    private int[] position;
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

    public void reduceFuel(){
        this.fuel--;
    }

    public void boostFuel(){
        this.fuel = this.maxFuel;
    }

    public String getTankImage() {
        return tankImage;
    }
}
