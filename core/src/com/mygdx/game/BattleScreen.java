package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.particles.values.RectangleSpawnShapeValue;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.ShortArray;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.lang.Math;

import static java.lang.Math.max;

// Currently after clicking the "Play :D" button we get this BattleScreen file open
// Make this working and the static GUI part would be completed !!
public class BattleScreen extends ScreenAdapter  {

    private TankStarsGame game;
    private Tank[] tanks;
    private OrthographicCamera camera;
    private Stage stage;
    private Box2DDebugRenderer box2DDebugRenderer;
    private World world;
    private Texture groundTexture;
    private PolygonSprite groundPolygonSprite;
    private PolygonSpriteBatch groundPolygonSpriteBatch;

    private ShapeRenderer[] healths = new ShapeRenderer[2];
    private ShapeRenderer[] fuels = new ShapeRenderer[2];
    private Texture[] tankTextures = new Texture[2];
    private Fixture[] tankFixtures = new Fixture[2];
    private Body tankBodies[] = new Body[2];
    private Fixture cannonFix; private Body cannonBody;

    Skin pauseSkin;
    boolean destroyCannon = false, changeFuel = false;


    public BattleScreen(TankStarsGame game) {
        this.game = game;
        GameData gameData = game.getGameData();
        this.tanks = gameData.getTanks();

        //setting the camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        stage = new Stage(new ScreenViewport());
        pauseSkin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));

        tankTextures[0] = new Texture(Gdx.files.internal(tanks[0].getTankImage()));
        tankTextures[1] = new Texture(Gdx.files.internal(tanks[1].getTankImage()));

    }

    private float[] generateGroundVertices(int X){
        //int A = 100, B = 80, C = 110;
        float[] vertices = new float[14];
        vertices[0] = -350;
        vertices[1] = 0;
        vertices[2] = 1150;
        vertices[3] = 0;
        int i=2;
        for (int x=1000; x>=-200; x-=300){
            //y = A*(int)Math.pow(1-x,2) + B*2*x*(1-x) + C*(int)Math.pow(x,2);
            vertices[2*i] = x;
            vertices[2*i+1] = 100+50*(float)Math.random();
            i++;
        }
        vertices[6] = 600;
        vertices[7] = 100;

        vertices[10] = 200;
        vertices[11] = 100;

        return vertices;
    }

    @Override
    public void show() {
        world = new World(new Vector2(0, -9.81f), true);
        //box2DDebugRenderer = new Box2DDebugRenderer();

        Gdx.input.setInputProcessor(stage);

        createGround();

        healths[0] = new ShapeRenderer();
        healths[1] = new ShapeRenderer();

        fuels[0] = new ShapeRenderer();
        fuels[1] = new ShapeRenderer();


        final Button pauseButton = new TextButton("Pause", pauseSkin, "small");
        pauseButton.setSize(100, 45);
        pauseButton.setPosition(350, 435);

        stage.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (cannonBody != null){
                    return;
                }
                int turn = game.getGameData().getTurn();
                float tankX = tankFixtures[turn].getBody().getPosition().x;
                float tankY = tankFixtures[turn].getBody().getPosition().y;
                float rel_x = x - tankX;
                float rel_y = y - tankY;

                float impulse_coeff = 1;
                CircleShape circleShape = new CircleShape();
                circleShape.setRadius(5f);
                BodyDef cannon = new BodyDef();
                cannon.type = BodyDef.BodyType.DynamicBody;
                cannon.position.set(tankX,tankY+20);
                FixtureDef cannonFixDef = new FixtureDef();
                cannonFixDef.shape = circleShape;
                cannonFixDef.friction = 100f;
                cannonFixDef.restitution = 0;
                cannonBody = world.createBody(cannon);
                cannonFix = cannonBody.createFixture(cannonFixDef);
                changeFuel = false;
                cannonBody.applyLinearImpulse(rel_x*impulse_coeff,rel_y*impulse_coeff, tankX, tankY+20, false);
                circleShape.dispose();

                game.getGameData().switchTurn();
                tanks[game.getGameData().getTurn()].boostFuel();
            }
        });

        stage.addListener(new InputListener()
        {
            @Override
            public boolean keyDown(InputEvent event, int keycode)
            {
                int turn = game.getGameData().getTurn();
                if (cannonBody != null){
                    return true;
                }
                float tankX = tankFixtures[turn].getBody().getPosition().x;
                if (tanks[turn].getFuel() < 0){
                    return true;
                }

                float tankY = tankFixtures[turn].getBody().getPosition().y;
                if (keycode == 21){
                    changeFuel = true;
                    if ((turn == 0 && tankX < 50) || (turn == 1 && tankX < 400)){
                        return true;
                    }
                    tankBodies[turn].applyForce(-6000,-1000, tankX, tankY, true);
                } else if (keycode == 22){
                    changeFuel = true;
                    if ((turn == 0 && tankX > 400) || (turn == 1 && tankX > 750)){
                        return true;
                    }
                    tankBodies[turn].applyForce(6000,-1000, tankX, tankY, true);
                }
                return true;
            }
        });

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                if (contact.getFixtureA() == cannonFix || contact.getFixtureB() == cannonFix){
                    for (int i=0; i<2; i++){
                        float tankX = tankFixtures[i].getBody().getPosition().x;
                        float tankY = tankFixtures[i].getBody().getPosition().y;
                        float rel_x = cannonFix.getBody().getPosition().x - tankX;
                        float rel_y = cannonFix.getBody().getPosition().y - tankY;
                        if (tanks[game.getGameData().getTurn()].getDestructionPower() != 0){
                            tanks[i].reduceHealth(rel_x*rel_x + rel_y*rel_y, tanks[game.getGameData().getTurn()].getDestructionPower());
                        }
                        else{
                            tanks[i].reduceHealth(rel_x*rel_x + rel_y*rel_y);
                        }
                        int direction = rel_x > 0? 1:-1;
                        if (-100 < rel_x && rel_x < 100){
                            tankBodies[i].applyForce(-6000*direction,-1000, tankX, tankY, true);
                            tankBodies[i].applyForce(-6000*direction,-1000, tankX, tankY, true);
                        }

                        if (tanks[i].getHealth() < 0){
                            //System.out.println(i + " Lost");
                            game.setScreen(new GameOver(game));
                        }
                    }
                    destroyCannon = true;
                }
            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });


        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new PauseScreen(game));
            }
        });
        stage.addActor(pauseButton);

        addTanks();
    }

    private void addTanks() {
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(1f);
        // Add tank
        BodyDef leftTankDef = new BodyDef();
        leftTankDef.type = BodyDef.BodyType.DynamicBody;
        int[] pos = tanks[0].getPosition();
        if (pos[0] == pos[1] && pos[0] == 0){
            leftTankDef.position.set(200,110);
            tanks[0].setPosition(200, 110);
        }
        else{
            leftTankDef.position.set(pos[0],pos[1]+10);
        }

        FixtureDef tankFixDef = new FixtureDef();
        tankFixDef.shape = circleShape;
        tankFixDef.friction = 0.6f;
        tankFixDef.restitution = 0;
        tankBodies[0] = world.createBody(leftTankDef);
        tankFixtures[0] = tankBodies[0].createFixture(tankFixDef);
        tankBodies[0].setGravityScale(10);

        // Add tank
        BodyDef rightTankDef = new BodyDef();
        rightTankDef.type = BodyDef.BodyType.DynamicBody;
        pos = tanks[1].getPosition();
        if (pos[0] == pos[1] && pos[0] == 0){
            rightTankDef.position.set(600,110);
            tanks[1].setPosition(600, 110);
        }
        else{
            rightTankDef.position.set(pos[0],pos[1]+10);
        }
        FixtureDef rightTankFixDef = new FixtureDef();
        rightTankFixDef.shape = circleShape;
        rightTankFixDef.friction = 0.6f;
        rightTankFixDef.restitution = 0;
        tankBodies[1] = world.createBody(rightTankDef);
        tankFixtures[1] = tankBodies[1].createFixture(rightTankFixDef);
        tankBodies[1].setGravityScale(10);
        circleShape.dispose();
    }

    private void createGround() {
        float[] arr;
        if (game.getGameData().getTerrainInfo() == null){
            arr = generateGroundVertices(0);//{0,0,100,0,100,100,90,100,80,100,70,100,0,100};
            this.game.getGameData().setTerrainInfo(arr);
        }
        else{
            arr = game.getGameData().getTerrainInfo();
        }

        PolygonShape groundShape = new PolygonShape();
        groundPolygonSpriteBatch = new PolygonSpriteBatch();
        Pixmap pix = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pix.setColor(77 / 255f, 130 / 255f, 4 / 255f,1);
        pix.fill();
        groundTexture = new Texture(pix);
        TextureRegion textureRegion = new TextureRegion(groundTexture);
        EarClippingTriangulator triangulator = new EarClippingTriangulator();
        ShortArray triangleIndices = triangulator.computeTriangles(arr);
        PolygonRegion polygonRegion = new PolygonRegion(textureRegion, arr, triangleIndices.toArray());
        groundPolygonSprite = new PolygonSprite(polygonRegion);

        groundShape.set(arr);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(0, 0);

        ChainShape ground = new ChainShape();
        ground.createChain(arr);
        FixtureDef groundFixtureDef = new FixtureDef();
        groundFixtureDef.shape = ground;
        groundFixtureDef.friction = .6f;
        groundFixtureDef.restitution = 0;

        Fixture groundFixture = world.createBody(bodyDef).createFixture(groundFixtureDef);
        groundShape.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ScreenUtils.clear(135 / 255f, 206 / 255f, 235 / 255f, 1);
        //box2DDebugRenderer.render(world, camera.combined);
        groundPolygonSpriteBatch.begin();
        groundPolygonSprite.draw(groundPolygonSpriteBatch);
        groundPolygonSpriteBatch.end();

        game.getBatch().begin();

        game.getBatch().draw(tankTextures[0], tankFixtures[0].getBody().getPosition().x, tankFixtures[0].getBody().getPosition().y);
        game.getBatch().draw(tankTextures[1], tankFixtures[1].getBody().getPosition().x, tankFixtures[1].getBody().getPosition().y);
        game.getBatch().end();
        stage.act(delta);
        stage.draw();

        //Draw cannon
        if (cannonBody != null){
            Vector2 pos = cannonBody.getWorldCenter();
            float angle = cannonBody.getAngle(); //if you need rotation
            ShapeRenderer shapeRenderer = new ShapeRenderer();
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(220 / 255f, 20 / 255f, 60 / 255f, 1);
            shapeRenderer.circle(pos.x, pos.y, 5f);
            shapeRenderer.end();
            cannonBody.applyForce(0, -10, pos.x, pos.y, false);
        }
        int turn = game.getGameData().getTurn();
        int tankX = (int)tankFixtures[turn].getBody().getPosition().x;
        int tankY = (int)tankFixtures[turn].getBody().getPosition().y;
        if (cannonBody == null && changeFuel==true){ // cannon is sky
            tanks[turn].changePosition(tankX, tankY);
        }
        tanks[0].setPosition((int)tankFixtures[0].getBody().getPosition().x, (int)tankFixtures[0].getBody().getPosition().y);
        tanks[1].setPosition((int)tankFixtures[1].getBody().getPosition().x, (int)tankFixtures[1].getBody().getPosition().y);

        healths[0].begin(ShapeRenderer.ShapeType.Filled);
        healths[0].setColor(Color.FIREBRICK);
        healths[0].rect(0, 434, max(200*tanks[0].getHealth()/tanks[0].getMaxHealth(),0), 60);
        healths[0].end();

        healths[1].begin(ShapeRenderer.ShapeType.Filled);
        healths[1].setColor(Color.FIREBRICK);
        healths[1].rect(800, 434, -max(200*tanks[1].getHealth()/tanks[1].getMaxHealth(),0), 60);
        healths[1].end();

        fuels[0].begin(ShapeRenderer.ShapeType.Filled);
        fuels[0].setColor(Color.ORANGE);
        fuels[0].rect(0, 374, max(200*tanks[0].getFuel()/tanks[0].getMaxFuel(),0), 60);
        fuels[0].end();

        fuels[1].begin(ShapeRenderer.ShapeType.Filled);
        fuels[1].setColor(Color.ORANGE);
        fuels[1].rect(800, 374, -max(200*tanks[1].getFuel()/tanks[1].getMaxFuel(), 0), 60);
        fuels[1].end();

        world.step(1/60f, 100, 100);
        if (destroyCannon){
            cannonBody.destroyFixture(cannonFix);
            cannonBody = null;
            destroyCannon = false;
        }
    }
}
