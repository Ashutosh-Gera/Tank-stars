package com.mygdx.game;

public abstract class Tank {
    private int[] position = new int[2];
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
        this.fuel = this.maxFuel;
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

    public void reduceHealth(float distance) {
        this.health -= this.destructionPower / distance;
        System.out.println(distance);
    }
}
