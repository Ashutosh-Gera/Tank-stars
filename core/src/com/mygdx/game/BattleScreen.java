package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
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

    private Texture[] healths = new Texture[2];
    private Texture[] tankTextures = new Texture[2];
    private Fixture[] tankFixtures = new Fixture[2];
    private Body tankBodies[] = new Body[2];
    private Fixture cannonFix; private Body cannonBody;

    int turn = 0; // turn of tank, 0 for left, 1 for right

    Skin pauseSkin;


    public BattleScreen(TankStarsGame game) {
        this.game = game;
        GameData gameData = game.getGameData();
        this.tanks = gameData.getTanks();

        //setting the camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        stage = new Stage(new ScreenViewport());
        pauseSkin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));

        healths[0] = new Texture(Gdx.files.internal("leftHealth.png"));
        healths[1] = new Texture(Gdx.files.internal("rightHealth.png"));

        tankTextures[0] = new Texture(Gdx.files.internal(tanks[0].getTankImage()));
        tankTextures[1] = new Texture(Gdx.files.internal(tanks[1].getTankImage()));

    }

    private float[] generateGroundVertices(int X){
        //int A = 100, B = 80, C = 110;
        float[] vertices = new float[14];
        int y;
        vertices[0] = X;
        vertices[1] = 0;
        vertices[2] = X+800;
        vertices[3] = 0;
        int i=2;
        for (int x=X+800; x>=X; x-=200){
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
        box2DDebugRenderer = new Box2DDebugRenderer();

        Gdx.input.setInputProcessor(stage);

        createGround();

        final Button pauseButton = new TextButton("Pause", pauseSkin, "small");
        pauseButton.setSize(100, 45);
        pauseButton.setPosition(350, 435);

        stage.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                float tankX = tankFixtures[turn].getBody().getPosition().x;
                float tankY = tankFixtures[turn].getBody().getPosition().y;
                float rel_x = x - tankX;
                float rel_y = y - tankY;
                float impulse_coeff = 2;
                CircleShape circleShape = new CircleShape();
                circleShape.setRadius(5f);
                BodyDef cannon = new BodyDef();
                cannon.type = BodyDef.BodyType.DynamicBody;
                cannon.position.set(tankX+10,tankY+10);
                FixtureDef cannonFixDef = new FixtureDef();
                cannonFixDef.shape = circleShape;
                cannonFixDef.friction = .6f;
                cannonFixDef.restitution = 0;
                cannonBody = world.createBody(cannon);
                cannonFix = cannonBody.createFixture(cannonFixDef);

                cannonBody.applyLinearImpulse(rel_x/impulse_coeff,rel_y/impulse_coeff, tankX, tankY, false);
                circleShape.dispose();
            }
        });

        stage.addListener(new InputListener()
        {
            @Override
            public boolean keyDown(InputEvent event, int keycode)
            {
                float tankX = tankFixtures[turn].getBody().getPosition().x;
                float tankY = tankFixtures[turn].getBody().getPosition().y;
                if (keycode == 21){
                    tankBodies[turn].applyForce(-800,0, tankX, tankY, false);
                } else if (keycode == 22){
                    tankBodies[turn].applyForce(800,0, tankX, tankY, false);
                }
                return true;
            }
        });

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                if (contact.getFixtureA() == cannonFix || contact.getFixtureB() == cannonFix){
                    cannonBody = null;
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
        circleShape.setRadius(0.1f);
        // Add tank
        BodyDef leftTankDef = new BodyDef();
        leftTankDef.type = BodyDef.BodyType.DynamicBody;
        leftTankDef.position.set(200,110);
        FixtureDef tankFixDef = new FixtureDef();
        tankFixDef.shape = circleShape;
        tankFixDef.friction = 0f;
        tankFixDef.restitution = 0;
        tankBodies[0] = world.createBody(leftTankDef);
        tankFixtures[0] = tankBodies[0].createFixture(tankFixDef);

        // Add tank
        BodyDef rightTankDef = new BodyDef();
        rightTankDef.type = BodyDef.BodyType.DynamicBody;
        rightTankDef.position.set(600,110);
        FixtureDef rightTankFixDef = new FixtureDef();
        rightTankFixDef.shape = circleShape;
        rightTankFixDef.friction = 0.6f;
        rightTankFixDef.restitution = 0;
        tankBodies[1] = world.createBody(rightTankDef);
        tankFixtures[1] = tankBodies[1].createFixture(rightTankFixDef);
        circleShape.dispose();
    }

    private void createGround() {
        float[] arr = generateGroundVertices(0);//{0,0,100,0,100,100,90,100,80,100,70,100,0,100};

        PolygonShape groundShape = new PolygonShape();
        ShapeRenderer groundFill = new ShapeRenderer();
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
        box2DDebugRenderer.render(world, camera.combined);
        groundPolygonSpriteBatch.begin();
        groundPolygonSprite.draw(groundPolygonSpriteBatch);
        groundPolygonSpriteBatch.end();

        game.batch.begin();
        game.batch.draw(healths[0], 0, 434);
        game.batch.draw(healths[1], 600, 434);

        game.batch.draw(tankTextures[0], tankFixtures[0].getBody().getPosition().x, tankFixtures[0].getBody().getPosition().y);
        game.batch.draw(tankTextures[1], tankFixtures[1].getBody().getPosition().x, tankFixtures[1].getBody().getPosition().y);
        game.batch.end();
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

        int tankX = (int)tankFixtures[turn].getBody().getPosition().x;
        int tankY = (int)tankFixtures[turn].getBody().getPosition().y;
        tanks[turn].setPosition(tankX, tankY);
        world.step(1/60f, 100, 100);
    }
}
