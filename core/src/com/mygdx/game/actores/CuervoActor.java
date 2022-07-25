package com.mygdx.game.actores;
import static com.mygdx.game.Constants.PIXELS_POR_METRO;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class CuervoActor extends Actor {

    private static final int FRAME_COLS = 3, FRAME_ROWS = 1; // numero de columnas y lineas del sprite
    private Animation<TextureRegion> walkAnimation; //para crear la animacion frame a frame
    private Texture walkSheet; //un frame en concreto
    private TextureRegion txtRegion;
    public float stateTime; //atributo tiempo de actualizacion
    private World mundo; //atributo el juego
    private Body body; //atributo cuerpo del actor(Personaje)
    private Fixture fixture;//atributo para identificar a la hora de crear colisiones



    /**
     * Method para crear la animacion del personaje
     */
    private void crearAnimation() {

        walkSheet = new Texture(Gdx.files.internal("cuervo.png"));

        //para cortar el sprite en una determinada posicion
        TextureRegion[][] tmp = TextureRegion.split(walkSheet,
                walkSheet.getWidth() / 3,
                walkSheet.getHeight() / FRAME_ROWS);

        TextureRegion[] walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }

        walkAnimation = new Animation<TextureRegion>(0.5f, walkFrames);
        stateTime = 0f;
        txtRegion = walkAnimation.getKeyFrame(0); //frame inicial
    }

    /**
     * Constructor
     * @param world
     * @param x
     * @param y
     */
    public CuervoActor(World world, float x, float y){

        crearAnimation();
        this.mundo = world;

        this.mundo = world;
        BodyDef def = new BodyDef();
        def.position.set(x,y);
        body = world.createBody(def);

        PolygonShape cuervo = new PolygonShape();
        Vector2[] vertices =new Vector2[4];
        vertices[0] = new Vector2(-0.5f,-0.5f);
        vertices[1] = new Vector2(0.5f,-0.5f);
        vertices[2] = new Vector2(0,0.5f);
        vertices[3] = new Vector2(1,-1f);

        cuervo.set(vertices);
        fixture = body.createFixture(cuervo,1);
        fixture.setUserData("cuervo");
        cuervo.dispose();


        setSize(PIXELS_POR_METRO, PIXELS_POR_METRO);
        setPosition((x-0.5f)* PIXELS_POR_METRO, y * PIXELS_POR_METRO);

    }


    /**
     * Method que actualiza el actor
     * @param delta
     */
    @Override
    public void act(float delta) {

        stateTime += delta;
        txtRegion = walkAnimation.getKeyFrame(stateTime, true);
        stateTime += delta;
    }

    /**
     * Method que dibuja el actor(personaje)
     * @param batch
     * @param parentAlpha
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition((body.getPosition().x-0.5f) * PIXELS_POR_METRO,
                (body.getPosition().y-0.5f) * PIXELS_POR_METRO);
        batch.draw(txtRegion,getX(),getY(), getWidth(), getHeight());
    }

    /**
     * Method para quitar los objetos del mundo para que quedé atrapados en la memoria(funciona como el dispose)
     * pero para las clases fixture y body
     */
    public void dest() {
        body.destroyFixture(fixture);
        mundo.destroyBody(body);
    }

}
