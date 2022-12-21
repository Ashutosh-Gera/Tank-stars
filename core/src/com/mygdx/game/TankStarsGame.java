package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;


public class TankStarsGame extends Game {
	SpriteBatch batch;
	ShapeRenderer shapeRenderer;
	BitmapFont font;
	private GameData gameData = new GameData();

	//Tank[] tanks = new Tank(2);
	//float[] terrainInfp;
	//Stage stage;

	@Override
	public void create () {
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		font = new BitmapFont();
		setScreen(new TitleScreen(this));
	}

	public void render() {
		super.render();
	}

	@Override
	public void dispose () {
		batch.dispose();
		shapeRenderer.dispose();
		font.dispose();
	}

	public GameData getGameData() {
		return gameData;
	}

	public void setGameData(GameData gameData) {
		this.gameData = gameData;
	}

	public void setTankOne(Tank tank){
		this.gameData.setTankOne(tank);
	}

	public void setTankTwo(Tank tank) {
		this.gameData.setTankTwo(tank);
	}

	public void setTerrain(float[] terrainInfo){
		this.gameData.setTerrainInfo(terrainInfo);
	}

	public boolean isTankOneInitialised() {
		return this.gameData.isTankOneInitialised();
	}
	public boolean isTankTwoInitialised() {
		return this.gameData.isTankTwoInitialised();
	}
}
