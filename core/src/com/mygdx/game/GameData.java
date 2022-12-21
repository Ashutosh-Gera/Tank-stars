package com.mygdx.game;

public class GameData {
    private Tank[] tanks = new Tank[2];
    private float[] terrainInfo;

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
}
