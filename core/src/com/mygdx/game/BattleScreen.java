package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.ShortArray;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.w3c.dom.Text;
import java.lang.Math;


import java.util.ArrayList;

// Currently after clicking the "Play :D" button we get this BattleScreen file open
// Make this working and the static GUI part would be completed !!
public class BattleScreen extends ScreenAdapter {

    final tankStarsGame game;
    OrthographicCamera camera;
    Stage stage;
    Box2DDebugRenderer box2DDebugRenderer;
    World world;
    Texture groundTexture;
    PolygonSprite groundPolygonSprite;
    PolygonSpriteBatch groundPolygonSpriteBatch;
    public BattleScreen(tankStarsGame game) {
        this.game = game;

        //setting the camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        stage = new Stage(new ScreenViewport());
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

        return vertices;
    }

    @Override
    public void show() {
        world = new World(new Vector2(0, -9.81f), true);
        box2DDebugRenderer = new Box2DDebugRenderer();

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(0, 0);
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

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = groundShape;
        fixtureDef.friction = .6f;
        fixtureDef.restitution = 0;

        world.createBody(bodyDef).createFixture(fixtureDef);
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
    }
}
