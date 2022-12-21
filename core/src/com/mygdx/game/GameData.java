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
}
