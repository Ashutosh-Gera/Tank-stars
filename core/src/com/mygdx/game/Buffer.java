package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Buffer extends ApplicationAdapter {

    private Texture homePage;
    private Music homeBgm;
    private OrthographicCamera camera;
    private SpriteBatch batch;

    @Override
    public void create () {

        homePage = new Texture(Gdx.files.internal("HomePage.png"));
        batch = new SpriteBatch();
        homeBgm = Gdx.audio.newMusic(Gdx.files.internal("homeBgm.mp3"));
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        homeBgm.setLooping(true);
        homeBgm.play();
    }

    @Override
    public void render () {
        batch.begin();
        batch.draw(homePage, 0, 0);
        batch.end();
    }

    @Override
    public void dispose () {

    }
}
