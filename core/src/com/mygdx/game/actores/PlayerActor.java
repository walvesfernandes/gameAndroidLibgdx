package com.mygdx.game.actores;


import static com.mygdx.game.Constants.IMPULSE_SALTO;
import static com.mygdx.game.Constants.PIXELS_POR_METRO;
import static com.mygdx.game.Constants.VELOCIDAD_JUGADOR;

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

public class PlayerActor extends Actor {

    private static final int FRAME_COLS = 3, FRAME_ROWS = 1; // numero de columnas y lineas del sprite
    private Animation<TextureRegion> walkAnimation; //para crear la animacion frame a frame
    private Texture walkSheet; //un frame en concreto
    private TextureRegion txtRegion;
    public float stateTime; //atributo tiempo de actualizacion
    private World mundo; //atributo el juego
    private Body body; //atributo cuerpo del actor(Personaje)
    private Fixture fixture;//atributo para identificar a la hora de crear colisiones

    private boolean vivo = true; //atributo que controla si el player esta vivo o no
    private boolean saltando = false; //atributo para controlar el salto
    private boolean debeSaltar = false;//atributo para controlar cuando toca el suelo

    /**
     * Method para crear la animacion del personaje
     * Mas sobre la clase Animation: permite almacenar un conjunto de frames (texturas) como una animación y permite así añadir movimiento de una forma sencilla a un conjunto de texturas relacionadas.
     */
    private void crearAnimation() {

        walkSheet = new Texture(Gdx.files.internal("run.png"));

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

        walkAnimation = new Animation<TextureRegion>(0.3f, walkFrames);
        stateTime = 0f;
        txtRegion = walkAnimation.getKeyFrame(0); //frame inicial
    }

    public boolean getDebeSaltar() {
        return debeSaltar;
    }

    public void setDebeSaltar(boolean debeSaltar) {
        this.debeSaltar = debeSaltar;
    }

    public boolean getVivo() {
        return vivo;
    }

    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }

    public boolean getSaltando() {
        return saltando;
    }

    public void setSaltando(boolean saltando) {
        this.saltando = saltando;
    }


    /**
     * Constructor
     * @param world
     * @param position
     */
    public PlayerActor(World world, Vector2 position) {

        crearAnimation();
        this.mundo = world;
        BodyDef def = new BodyDef();
        def.position.set(position);
        def.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(def);


        PolygonShape gatoShape = new PolygonShape();
        gatoShape.setAsBox(0.2f, 0.5f); //1 metro por 1 metro
        fixture = body.createFixture(gatoShape, 3);
        fixture.setUserData("player");
        gatoShape.dispose();

        setSize(PIXELS_POR_METRO, PIXELS_POR_METRO);


    }

    /**
     * Method que actualiza el actor
     * @param delta
     */
    @Override
    public void act(float delta) {

        stateTime += delta;
        txtRegion = walkAnimation.getKeyFrame(stateTime, true);

        if(Gdx.input.justTouched()){
            saltar();
        }

        //salto se toca la pantalla
        //mover el player si esta vivo
        //cambiar el sprite cuando salta y camina
        //si mantiene el dedo tocando la pantalla saltará
        if (debeSaltar) {
            debeSaltar = false;
            saltar();
        }

        //avanzar si el jugador esta vivo
        if (vivo) {
            float velocidadY = body.getLinearVelocity().y;
            body.setLinearVelocity(VELOCIDAD_JUGADOR, velocidadY);
            stateTime += delta;
        }

        if (saltando) { // para que al saltar no de saltos tan largos
            body.applyForceToCenter(0, -IMPULSE_SALTO * 1.75f, true);

        }
    }

    /**
     * Method que indica que debe saltar
     */
    public void saltar() {
        if (!saltando && vivo) {

            saltando = true; //paso a true para que no vuelva a saltar
            Vector2 position = body.getPosition();
            body.applyLinearImpulse(0, IMPULSE_SALTO, position.x, position.y, true);

        }
    }

    /**
     * Method que dibuja el actor(personaje)
     * @param batch
     * @param parentAlpha
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition((body.getPosition().x - 0.5f) * PIXELS_POR_METRO,
                (body.getPosition().y - 0.5f) * PIXELS_POR_METRO);
        batch.draw(txtRegion, getX(), getY(), getWidth() / 2, getHeight() / 2, getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
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
