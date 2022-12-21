package com.mygdx.game;

import java.io.Serializable;

public class GameData implements Serializable {
    static private final long serialVersionUID=42L;
    private Tank[] tanks = new Tank[2];
    private float[] terrainInfo;
    private int turn = 0;

    public Tank[] getTanks() {
        return tanks;
    }

    public float[] getTerrainInfo() {
        return terrainInfo;
    }

    public void setTankOne(Tank tank){
        this.tanks[0] = tank;
    }

    public void setTankTwo(Tank tank){
        this.tanks[1] = tank;
    }

    public void setTerrainInfo(float[] terrainInfo) {
        this.terrainInfo = terrainInfo;
    }

    public boolean isTankOneInitialised(){
        return !(this.tanks[0] == null);
    }

    public boolean isTankTwoInitialised(){
        return !(this.tanks[1] == null);
    }

    public int getTurn() {
        return turn;
    }

    public void switchTurn(){
        turn = 1 - turn;
    }
}
