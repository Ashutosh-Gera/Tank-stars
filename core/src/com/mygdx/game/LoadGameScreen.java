package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
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


public class LoadGameScreen extends ScreenAdapter {

    TankStarsGame game;

    OrthographicCamera camera;
    Texture loadImg;
    Music loadBgm;
    Stage stage;
    Skin loadSkin;
    //Label outputLabel;


    public LoadGameScreen(TankStarsGame game) {
        this.game = game;

        //setting the camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        //textures
        loadImg = new Texture(Gdx.files.internal("selectTank.png"));
        loadBgm = Gdx.audio.newMusic(Gdx.files.internal("selectBgm.mp3"));

        //loop the music
        loadBgm.setLooping(true);
        loadBgm.play();

        //stage for buttons
        stage = new Stage(new ScreenViewport());
        loadSkin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
    }

    @Override
    public void show() {

        Gdx.input.setInputProcessor(stage);

        Button chooseGame = new TextButton("CHOOSE SAVED\nGAME", loadSkin, "small");
        chooseGame.setSize(200, 50);
        chooseGame.setPosition(550, 370);
        chooseGame.setColor(Color.BLACK);
        stage.addActor(chooseGame);

        Button firstButton = new TextButton("Game ONE", loadSkin, "small");
        firstButton.setSize(150, 50);
        firstButton.setPosition(575, 270);
        firstButton.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new BattleScreen(game));
            }

        });
        stage.addActor(firstButton);

        Button secondButton = new TextButton("Game TWO", loadSkin, "small");
        secondButton.setSize(150, 50);
        secondButton.setPosition(575, 200);
        secondButton.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new BattleScreen(game));
            }

        });
        stage.addActor(secondButton);

        Button thirdButton = new TextButton("Game THREE", loadSkin, "small");
        thirdButton.setSize(150, 50);
        thirdButton.setPosition(575, 130);
        thirdButton.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new BattleScreen(game));
            }

        });
        stage.addActor(thirdButton);

        Button fourthButton = new TextButton("Game FOUR", loadSkin, "small");
        fourthButton.setSize(150, 50);
        fourthButton.setPosition(575, 60);
        fourthButton.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new BattleScreen(game));
            }

        });
        stage.addActor(fourthButton);
    }


    @Override
    public void render(float delta) {

        ScreenUtils.clear(237/255f, 174/255f, 73/255f, 1);

        camera.update();

        game.batch.begin();
        game.batch.draw(loadImg, 0, 0);
        game.batch.end();

        stage.act(delta);
        stage.draw();

    }


    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
        dispose();
        loadBgm.stop();
    }

    @Override
    public void dispose() {
        loadImg.dispose();
        stage.dispose();
        loadSkin.dispose();
    }






















}
