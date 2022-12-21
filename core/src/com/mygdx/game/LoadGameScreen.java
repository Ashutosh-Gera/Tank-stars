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

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;


public class LoadGameScreen extends ScreenAdapter implements ScreenInterface{

    private TankStarsGame game;
    private OrthographicCamera camera;
    private Texture loadImg;
    private Music loadBgm;
    private Stage stage;
    private Skin loadSkin;
    private ArrayList<String> savedGameNames = new ArrayList<>();
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

        File folder = new File("savedGames");
        savedGameNames.clear();
        File[] files = folder.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                savedGameNames.add("savedGames/" + files[i].getName());
            }
        }
    }

    public void loadGame(String path) {
        try{
            FileInputStream file = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(file);

            // Method for deserialization of object
            this.game.setGameData((GameData)in.readObject());
            in.close();
            file.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        game.setScreen(new BattleScreen(game));
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
                if (savedGameNames.size() >= 1) loadGame(savedGameNames.get(0));
            }

        });
        stage.addActor(firstButton);

        Button secondButton = new TextButton("Game TWO", loadSkin, "small");
        secondButton.setSize(150, 50);
        secondButton.setPosition(575, 200);
        secondButton.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (savedGameNames.size() >= 2) loadGame(savedGameNames.get(1));
            }

        });
        stage.addActor(secondButton);

        Button thirdButton = new TextButton("Game THREE", loadSkin, "small");
        thirdButton.setSize(150, 50);
        thirdButton.setPosition(575, 130);
        thirdButton.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (savedGameNames.size() >= 3) loadGame(savedGameNames.get(2));
            }

        });
        stage.addActor(thirdButton);

        Button fourthButton = new TextButton("Game FOUR", loadSkin, "small");
        fourthButton.setSize(150, 50);
        fourthButton.setPosition(575, 60);
        fourthButton.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (savedGameNames.size() >= 4) loadGame(savedGameNames.get(3));
            }

        });
        stage.addActor(fourthButton);
    }


    @Override
    public void render(float delta) {

        ScreenUtils.clear(237/255f, 174/255f, 73/255f, 1);

        camera.update();

        game.getBatch().begin();
        game.getBatch().draw(loadImg, 0, 0);
        game.getBatch().end();

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
