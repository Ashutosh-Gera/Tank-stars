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

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.Date;

public class PauseScreen extends ScreenAdapter  implements ScreenInterface{

    TankStarsGame game;

    OrthographicCamera camera;
    Texture pauseImg;
    Music pauseBgm;
    Stage stage;
    Skin pauseSkin;
    //Label outputLabel;

    public PauseScreen(TankStarsGame game) {
        this.game = game;
        //setting the camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        //textures
        pauseImg = new Texture(Gdx.files.internal("TankStars.png"));
        pauseBgm = Gdx.audio.newMusic(Gdx.files.internal("homeBgm.mp3"));

        //loop the music
        pauseBgm.setLooping(true);
        pauseBgm.play();

        //stage for buttons
        stage = new Stage(new ScreenViewport());
        pauseSkin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
    }

    public void saveGame(){
        try{
            SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss z");
            Date date = new Date(System.currentTimeMillis());
            FileOutputStream file = new FileOutputStream("assets/savedGames/" + formatter.format(date));
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(this.game.getGameData());
            out.close();
            file.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void show() {

        Gdx.input.setInputProcessor(stage);

        final Button resumeButton = new TextButton("Resume current\ngame", pauseSkin,"small");
        resumeButton.setSize(200,75);
        resumeButton.setPosition(550, 350);

        final Button exitButton = new TextButton("Exit Game", pauseSkin, "small");
        exitButton.setSize(200, 75);
        exitButton.setPosition(550, 250);

        final Button saveExitButton = new TextButton("Save and\nexit game", pauseSkin, "small");
        saveExitButton.setSize(200, 75);
        saveExitButton.setPosition(550, 150);


        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new BattleScreen(game));
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.exit(0);
            }
        });

        saveExitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                saveGame();
                System.exit(0);
            }
        });
        stage.addActor(resumeButton);
        stage.addActor(exitButton);
        stage.addActor(saveExitButton);

    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(237/255f, 174/255f, 73/255f, 1);

        //tell the camera to update its matrices
        camera.update();

        game.getBatch().begin();
        game.getBatch().draw(pauseImg, 0, 0);
        game.getBatch().end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
        dispose();
        pauseBgm.stop();
    }

    @Override
    public void dispose() {
        pauseImg.dispose();
        stage.dispose();
        pauseSkin.dispose();
    }

}

