package com.mygdx.game;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


public class TitleScreen extends ScreenAdapter{

    final tankStarsGame game;

    OrthographicCamera camera;
    Texture titleImg;
    Music titleBgm;
    Stage stage;
    Skin playSkin;
    //Label outputLabel;


    public TitleScreen(tankStarsGame game) {
        this.game = game;
        //setting the camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        //textures
        titleImg = new Texture(Gdx.files.internal("TankStars.png"));
        titleBgm = Gdx.audio.newMusic(Gdx.files.internal("homeBgm.mp3"));

        //loop the music
        titleBgm.setLooping(true);
        titleBgm.play();

        //stage for buttons
        stage = new Stage(new ScreenViewport());
        playSkin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
    }


    @Override
    public void show() {
        /*Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keyCode) {
                if (keyCode == Input.Keys.SPACE) {
                    game.setScreen(new BattleScreen(game));
                }
                return true;
            }
        });*/
        //int row_height = Gdx.graphics.getHeight() / 12 ;
        //int col_width = Gdx.graphics.getWidth() / 12 ;

        Gdx.input.setInputProcessor(stage);

        final Button playButton = new TextButton("Play 1 v 1", playSkin,"small");
        playButton.setSize(200,75);
        playButton.setPosition(550, 200);

        /*playButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                outputLabel.setText("Press a Button");
                game.setScreen(new BattleScreen(game));
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                outputLabel.setText("Pressed Text Button");
                //game.setScreen(new BattleScreen(game));
                return true;
            }
        });*/

        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new SelectScreen(game));
            }

        });
        stage.addActor(playButton);
    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(237/255f, 174/255f, 73/255f, 1);

        //tell the camera to update its matrices
        camera.update();

        game.batch.begin();
        game.batch.draw(titleImg, 0, 0);
        game.batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
        dispose();
        titleBgm.stop();
    }

    /*@Override
    public void resume(){

    }*/

    @Override
    public void dispose() {
        titleImg.dispose();
        stage.dispose();
        playSkin.dispose();
    }

}
