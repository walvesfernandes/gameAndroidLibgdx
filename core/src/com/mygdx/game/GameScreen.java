package com.mygdx.game;

import static com.mygdx.game.Constants.BASE_PARAPLAYER;
import static com.mygdx.game.Constants.H_PANTALLA;
import static com.mygdx.game.Constants.PIXELS_POR_METRO;
import static com.mygdx.game.Constants.VELOCIDAD_JUGADOR;
import static com.mygdx.game.Constants.W_PANTALLA;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;


import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.actores.CuervoActor;
import com.mygdx.game.actores.FantasmaActor;
import com.mygdx.game.actores.PlayerActor;
import com.mygdx.game.actores.SueloActor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * GameScreen es la pantalla principal del juego
 */
public class GameScreen extends BaseScreen {

    /**
     * Recursos graficos y sonidos
     */
    private Texture background; //Atributo para dibujar el fondo del juego
    private Texture sueloTejado;
    public BitmapFont bmScore;
    private SpriteBatch batch; //Atributo para dibujar imagenes estaticas en el juego
    public Music musica, sonMuerte; //se cargar en el buffer para que no esté cargando siempre la musica

    /**
     * Recursos logicos
     */
    private Preferencias preferencias; //Atributo donde podemos enviar y recuperar datos
    private Stage stage;
    private World mundo; //Atributo para crear el mundo
    private static int highScore, lastScore;
    private int score; //Puntuacion del juego
    private int sumar = 8; //atributo para controlar donde se dibuja los enemigos

    /**
     * Objetos y colecciones
     */
    private PlayerActor player; //Atributo player que sirve para crear el objeto con su body y su comportamiento, es necesario pasar una textura(imagen)
    private List<FantasmaActor> fantasmaList = new ArrayList<>(); //lista con varios enemigos
    private List<SueloActor> sueloList = new ArrayList<>(); //para crear suelos elevados
    private List<CuervoActor> cuervos = new ArrayList<>();


    /**
     * Method para crear numeros aleatorios para agregar enemigos de manera aleatoria
     *
     * @return float
     */
    public float numAleatorio() {
        Random random = new Random();
        float value = random.nextInt(9 + 2) + 2;

        return value;
    }

    /**
     * Metodo auxiliar para saber si los actores han chocado
     * @param contact
     * @param userA
     * @param userB
     * @return
     */

    private boolean hanChocado(Contact contact, Object userA, Object userB) {
        return (contact.getFixtureA().getUserData().equals(userA) && contact.getFixtureB().getUserData().equals(userB)) ||
                (contact.getFixtureA().getUserData().equals(userB) && contact.getFixtureB().getUserData().equals(userA));
    }

    /**
     * Constructor donde se crea el stage el mundo y el metodo que detecta las colisiones
     *
     * @param game En constructor es aconsejable solamente iniciar class
     */
    public GameScreen(final MainGame game) {
        super(game);

        this.preferencias = game.preferencias;


        stage = new Stage(new FitViewport(W_PANTALLA, H_PANTALLA)); //para crear la camara con los valores que establecemos en nuestra clase de atributos/variables constantes

        //Sonidos
        sonMuerte =game.getManager().get("muerto.wav");
        sonMuerte.setVolume(0.45f);
        musica = game.getManager().get("catMenu.wav");//poner musica
        musica.setLooping(true); // poner la cancion en loop
        musica.setVolume(0.75f); //volumen de la musica

        //Puntos
        score = 0;
        highScore = preferencias.getHighScore(); //Coge el valor de los puntos mas altos guardados
        bmScore = new BitmapFont(Gdx.files.internal("skin/default.fnt"));
        bmScore.getData().setScale(4, 4);//escalar la fuente



        mundo = new World(new Vector2(0, -10), true); //crear el mundo, y es la gravedad del mundo en este caso -10
        mundo.setContactListener(new ContactListener() {

            /**
             *Method que indica cuando un elemento se choca con otro
             */
            @Override
            public void beginContact(Contact contact) {


                /**
                 * Si choca con algun de los elementos inicia la pantalla Game over
                 */
                //GAME OVER

                /**
                 * Para controlar cuando el personaje puede volver a saltar
                 */
                if (hanChocado(contact, "player", "floor")) {

                    player.setSaltando(false);

                    if (Gdx.input.isTouched()) {
                        player.setDebeSaltar(true);
                    }
                }

                //si choca con el obstaculo el jugador muere
                if (hanChocado(contact, "player", "fantasma") || hanChocado(contact, "player", "cuervo")) {

                    if (preferencias.getVibrador()) {

                        Gdx.input.vibrate(1000);//vibrar cuando el player muere
                    }

                    if (player.getVivo()) {
                        player.setVivo(false);
                        sonMuerte.play();
                        musica.stop();

                        //stage tiene un metodo que podemos pasar acciones en este caso para crear un delay y luego llamar a la pantalla de game over
                        stage.addAction(
                                Actions.sequence(Actions.delay(0.5f), Actions.run(new Runnable() {
                                    @Override
                                    public void run() {
                                        game.setScreen(new GameOverScreen(game)); //llama a la pantalla game over siempre que choca/pierde
                                    }
                                }))
                        );
                    }
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
    }


    /**
     * Metodo para crear los enemigos, crea 3 tipos de enemigos
     * @param floorTexture
     * @param overFloorTexture
     * @param fantasmaTexture
     */
    public void agregarEnemigos(Texture floorTexture, Texture overFloorTexture,Texture fantasmaTexture){

        while (sumar < 1000) {

            cuervos.add(new CuervoActor(mundo, sumar * numAleatorio(), numAleatorio())); //x e y  es la posicion donde sera dibujado el cuervo
            sueloList.add(new SueloActor(mundo, floorTexture, overFloorTexture, sumar * numAleatorio(), 10, 2));

            if (!(sumar % 2 == 0)) {

                sueloList.add(new SueloActor(mundo, floorTexture, overFloorTexture, sumar * numAleatorio(), 3, 2));

                sumar+=2;
            }

            //Para que sea mas dificil dependiendo de donde esté en el mundo
            if(sumar>600){

                fantasmaList.add(new FantasmaActor(mundo, fantasmaTexture, sumar * numAleatorio(), 2));
                cuervos.add(new CuervoActor(mundo, sumar * numAleatorio(), numAleatorio()));

            }

            fantasmaList.add(new FantasmaActor(mundo, fantasmaTexture, sumar * numAleatorio(), 2));

            sumar += 3;
        }

        //para agregar varios suelos al stage
        for (SueloActor suelo : sueloList) {
            stage.addActor(suelo);
        }

        for (FantasmaActor fantasma : fantasmaList) {
            stage.addActor(fantasma);
        }

        for (CuervoActor cuervo : cuervos) {
            stage.addActor(cuervo);
        }

    }

    /**
     * Method el show solo se ejecuta cuando se visualiza la pantalla
     */
    @Override
    public void show() {

        /**
         * Coge la preferencias poner o no la musica
         */
        if (preferencias.getMusica()) {
            musica.play(); //inicia la cancion
        } else {
            musica.stop();
        }

        batch = new SpriteBatch();
        background = new Texture("background.png");
        sueloTejado = new Texture("suelotejado.png");

        //suelos
        Texture overFloorTexture = game.getManager().get("overfloor.png"); //suelos elevados
        Texture floorTexture = game.getManager().get("floor.png"); //textura del suelo principal

        //enemigos
        Texture fantasmaTexture = game.getManager().get("fantasma.png"); //textura del fantasma


        /**
         * PLAYER
         */
        player = new PlayerActor(mundo, new Vector2(1.5f, BASE_PARAPLAYER)); //posicion inicial del personaje


        /**
         * Agregar Suelos y enemigos
         */
        sueloList.add(new SueloActor(mundo, floorTexture, overFloorTexture, 0, 1000, 1)); // suelo principal

        sueloList.add(new SueloActor(mundo, floorTexture, overFloorTexture, 20, 3, 2));
        sueloList.add(new SueloActor(mundo, floorTexture, overFloorTexture, 45, 3, 2));
        sueloList.add(new SueloActor(mundo, floorTexture, overFloorTexture, 60, 4, 2));
        sueloList.add(new SueloActor(mundo, floorTexture, overFloorTexture, 83, 2, 2));
        sueloList.add(new SueloActor(mundo, floorTexture, overFloorTexture, 87, 3, 2));
        sueloList.add(new SueloActor(mundo, floorTexture, overFloorTexture, 96, 4, 2));


        /**
         * Crear enemigos de manera aleatoria en el stage
         */
        agregarEnemigos(floorTexture,overFloorTexture,fantasmaTexture);

        //agregar el player al juego
        stage.addActor(player);


    }


    /**
     * Hide es como un dispose para actores y algunos otros tipos de recursos
     */
    @Override
    public void hide() {
        stage.clear(); //para limpiar el stage va a remover todos los actores a la vez

        musica.dispose();
        player.dest();
        player.remove();


        for (SueloActor floor : sueloList) {
            floor.dest();
            floor.remove();
        }

        for (CuervoActor cuervo : cuervos) {
            cuervo.dest();
            cuervo.remove();
        }

        for (FantasmaActor fantasma : fantasmaList) {
            fantasma.dest();
            fantasma.remove();
        }

        //limpiar las listas
        cuervos.clear();
        sueloList.clear();
        fantasmaList.clear();
    }

    /**
     * Method donde libgdx actualiza el juego (actualizar imagenes etc...
     *
     * @param delta
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0); //limpiar la pantalla con un color
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(); //actualiza el stage

        //Para mover la camara cuando el jugador ya haya movido un cierto tanto de pixeles y esté vivo
        if (player.getX() > 150 && player.getVivo()) {
            float velo = VELOCIDAD_JUGADOR * delta * PIXELS_POR_METRO;
            stage.getCamera().translate(velo,0,0);
        }
        //aumentar los puntos
        if (player.getX() > 0 && player.getVivo()) {
            score++;
        }



        batch.begin(); // el sprite batch pinta td a la vez y no se puede olvidar de poner el end al final

        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); //fondo
        batch.draw(sueloTejado, 0, 0, 0, 1); //fondo

        //Pintar los puntos por pantalla
        GlyphLayout scoLayout = new GlyphLayout(bmScore, "Puntos:" + score, Color.WHITE, 0, Align.left, false);
        bmScore.draw(batch, scoLayout, (Gdx.graphics.getWidth() - 2) - scoLayout.width, Gdx.graphics.getHeight());

        /**
         * Para saber la puntuacion mas alta
         */
        if (score > highScore) {

            highScore = score;
            preferencias.setHighScore(highScore);

        } else {

            lastScore = score;
            preferencias.setLastScore(lastScore);
        }


        batch.end();
        mundo.step(delta, 6, 2);
        stage.draw(); //siempre hay que dibujar el stage por ultimo para que coincida lo que hacen las imagenes con la logica
    }

    @Override
    public void dispose() {
        super.dispose();
        bmScore.dispose();
        stage.dispose();
        mundo.dispose();
        musica.dispose();
    }
}
