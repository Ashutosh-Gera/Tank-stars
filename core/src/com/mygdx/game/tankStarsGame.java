package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;


public class tankStarsGame extends Game {
	SpriteBatch batch;
	ShapeRenderer shapeRenderer;
	BitmapFont font;

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




}
