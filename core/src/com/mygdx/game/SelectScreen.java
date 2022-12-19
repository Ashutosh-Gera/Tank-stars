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


public class SelectScreen extends ScreenAdapter {

    TankStarsGame game;

    OrthographicCamera camera;
    Texture screenImg;

    Texture[] tankImgs = new Texture[3];
    Music screenBgm;
    Stage stage;
    Skin selectSkin;
    //Label outputLabel;


    public SelectScreen(TankStarsGame game) {
        this.game = game;

        //setting the camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        //textures
        screenImg = new Texture(Gdx.files.internal("selectTank.png"));
        screenBgm = Gdx.audio.newMusic(Gdx.files.internal("selectBgm.mp3"));
        tankImgs[0] = new Texture(Gdx.files.internal("heliosTank.png"));
        tankImgs[1] = new Texture(Gdx.files.internal("spectreTank.png"));
        tankImgs[2] = new Texture(Gdx.files.internal("blazerTank.png"));


        //loop the music
        screenBgm.setLooping(true);
        screenBgm.play();

        //stage for buttons
        stage = new Stage(new ScreenViewport());
        selectSkin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
    }

    @Override
    public void show() {

        Gdx.input.setInputProcessor(stage);

        Button chooseTank = new TextButton("CHOOSE TANK", selectSkin, "small");
        chooseTank.setSize(200, 50);
        chooseTank.setPosition(550, 370);
        chooseTank.setColor(Color.BLACK);
        stage.addActor(chooseTank);

        /*Button playButton = new TextButton("PLAY :D", selectSkin, "small");
        playButton.setSize(150, 75);
        playButton.setPosition(575, 60);
        playButton.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new BattleScreen(game));
            }

        });
        stage.addActor(playButton); */

        Button playHelios = new TextButton("PLAY\nHELIOS", selectSkin, "small");
        playHelios.setSize(125, 50);
        playHelios.setPosition(665, 270);
        playHelios.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new BattleScreen(game));
            }

        });
        stage.addActor(playHelios);

        Button playSpectre = new TextButton("PLAY\nSPECTRE", selectSkin, "small");
        playSpectre.setSize(125, 50);
        playSpectre.setPosition(665, 180);
        playSpectre.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new BattleScreen(game));
            }

        });
        stage.addActor(playSpectre);

        Button playBlazer = new TextButton("PLAY\nBLAZER", selectSkin, "small");
        playBlazer.setSize(125, 50);
        playBlazer.setPosition(665, 90);
        playBlazer.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new BattleScreen(game));
            }

        });
        stage.addActor(playBlazer);


    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(237/255f, 174/255f, 73/255f, 1);

        camera.update();

        game.batch.begin();
        game.batch.draw(screenImg, 0, 0);
        game.batch.draw(tankImgs[0], 565, 270);
        game.batch.draw(tankImgs[1], 565, 180);
        game.batch.draw(tankImgs[2], 565, 90);
        game.batch.end();

        stage.act(delta);
        stage.draw();

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
        screenBgm.stop();
    }

    @Override
    public void dispose() {
        screenImg.dispose();
        stage.dispose();
        selectSkin.dispose();
    }

}