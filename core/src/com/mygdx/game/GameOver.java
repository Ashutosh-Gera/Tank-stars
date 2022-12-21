package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GameOver extends ScreenAdapter implements ScreenInterface{

    TankStarsGame game;

    OrthographicCamera camera;
    Texture gameOver;
    Music gameOverBgm;
    Stage stage;
    Skin gameOverSkin;

    public GameOver(TankStarsGame game) {
        this.game = game;
        //setting the camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        //textures
        gameOver = new Texture(Gdx.files.internal("gameOver.png"));
        gameOverBgm = Gdx.audio.newMusic(Gdx.files.internal("gameOver.mp3"));

        //loop the music
        gameOverBgm.setLooping(true);
        gameOverBgm.play();

        //stage for buttons
        stage = new Stage(new ScreenViewport());
        gameOverSkin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        //not making buttons clickable at this point
        int i;
        if (game.getGameData().getTanks()[0].getHealth() < 0){
            i = 1;
        } else {
            i = 2;
        }
        final Button gameOverButton = new TextButton("Player " + i + "won the game.\nClick to exit!", gameOverSkin,"small");
        gameOverButton.setSize(200,75);
        gameOverButton.setPosition(550, 350);

        gameOverButton.addListener(new ClickListener() {
           @Override
           public void clicked(InputEvent event, float x, float y) {
               System.exit(0);
           }
        });
        stage.addActor(gameOverButton);
    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(237/255f, 174/255f, 73/255f, 1);

        //tell the camera to update its matrices
        camera.update();

        game.getBatch().begin();
        game.getBatch().draw(gameOver, 0, 0);
        game.getBatch().end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
        dispose();
        gameOverBgm.stop();
    }

    @Override
    public void dispose() {
        gameOver.dispose();
        stage.dispose();
        gameOverSkin.dispose();
    }





}
