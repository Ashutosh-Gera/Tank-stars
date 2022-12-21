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


public class TitleScreen extends ScreenAdapter implements ScreenInterface{

    private TankStarsGame game;

    private OrthographicCamera camera;
    private Texture titleImg;
    private Music titleBgm;
    private Stage stage;
    private Skin playSkin;
    //Label outputLabel;


    public TitleScreen(TankStarsGame game) {
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

        final Button playButton = new TextButton("New Game (1 v 1)", playSkin,"small");
        playButton.setSize(200,75);
        playButton.setPosition(550, 250);

        final Button resumeButton = new TextButton("Resume Saved\nGame", playSkin, "small");
        resumeButton.setSize(200, 75);
        resumeButton.setPosition(550, 150);

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
        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LoadGameScreen(game));
            }
        });

        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new SelectScreen(game));
            }

        });
        stage.addActor(playButton);
        stage.addActor(resumeButton);
    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(237/255f, 174/255f, 73/255f, 1);

        //tell the camera to update its matrices
        camera.update();

        game.getBatch().begin();
        game.getBatch().draw(titleImg, 0, 0);
        game.getBatch().end();

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
