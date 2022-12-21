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

    private TankStarsGame game;

    private OrthographicCamera camera;
    private Texture screenImg;

    private Texture[] tankImgs = new Texture[3];
    private Music screenBgm;
    private Stage stage;
    private Skin selectSkin;
    private GameData gameData;
    //Label outputLabel;


    public SelectScreen(TankStarsGame game) {
        this.game = game;
        this.gameData = game.getGameData();
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
                //game.setScreen(new BattleScreen(game));
                System.out.println("1");
                if (!game.isTankOneInitialised()){
                    game.setTankOne(new HeliosTank());
                    game.setScreen(new SelectScreen(game));
                } else if (!game.isTankTwoInitialised()) {
                    //System.out.println(game.getGameData());
                    gameData.setTankTwo(new HeliosTank());
                    game.setScreen(new BattleScreen(game));
                } else{
                    game.setScreen(new BattleScreen(game));
                }
            }

        });
        stage.addActor(playHelios);

        Button playSpectre = new TextButton("PLAY\nSPECTRE", selectSkin, "small");
        playSpectre.setSize(125, 50);
        playSpectre.setPosition(665, 180);
        playSpectre.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x, float y) {
                //game.setScreen(new BattleScreen(game));
                System.out.println("12");
                if (!game.isTankOneInitialised()){
                    game.setTankOne(new SpectreTank());
                    game.setScreen(new SelectScreen(game));
                } else if (!game.isTankTwoInitialised()) {
                    gameData.setTankTwo(new SpectreTank());
                    game.setScreen(new BattleScreen(game));
                }
            }

        });
        stage.addActor(playSpectre);

        Button playBlazer = new TextButton("PLAY\nBLAZER", selectSkin, "small");
        playBlazer.setSize(125, 50);
        playBlazer.setPosition(665, 90);
        playBlazer.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x, float y) {
                //game.setScreen(new BattleScreen(game));
                System.out.println("123");
                if (!game.isTankOneInitialised()){
                    game.setTankOne(new BlazerTank());
                    game.setScreen(new SelectScreen(game));
                } else if (!game.isTankTwoInitialised()) {
                    game.setTankTwo(new BlazerTank());
                    game.setScreen(new BattleScreen(game));
                }
            }

        });
        stage.addActor(playBlazer);

        Button battleStart = new TextButton("START GAME", selectSkin, "small");
        battleStart.setSize(200, 50);
        battleStart.setPosition(550, 20);
        battleStart.setColor(Color.BLACK);
        battleStart.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (game.isTankOneInitialised() && game.isTankTwoInitialised()){
                    game.setScreen(new BattleScreen(game));
                }
            }

        });
        stage.addActor(battleStart);


    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(237/255f, 174/255f, 73/255f, 1);

        camera.update();

        game.getBatch().begin();
        game.getBatch().draw(screenImg, 0, 0);
        game.getBatch().draw(tankImgs[0], 565, 270);
        game.getBatch().draw(tankImgs[1], 565, 180);
        game.getBatch().draw(tankImgs[2], 565, 90);
        game.getBatch().end();

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
