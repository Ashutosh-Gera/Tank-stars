package com.mygdx.game;

import com.badlogic.gdx.Game;

import java.io.Serializable;

public class GameData implements Serializable {
    static private final long serialVersionUID=42L;
    private Tank[] tanks = new Tank[2];
    private float[] terrainInfo;
    private int turn = 0;

    private static GameData gameData;

    private GameData(){

    }
    public static void createGameData() {
        if (gameData == null){
            gameData = new GameData();
        }
    }

    public static GameData getGameData(){
        return gameData;
    }

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
