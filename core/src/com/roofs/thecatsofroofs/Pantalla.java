package com.roofs.thecatsofroofs;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import org.graalvm.compiler.phases.common.NodeCounterPhase;

import java.awt.Menu;

/*
  InputAdapter sirve para capturar los eventos, como por ejemplo el click en la pantalla
  junto al Stage que tambien captura eventos
 */
public abstract class Pantalla extends InputAdapter implements Screen {
    //variable para definir el tamaño de la pantalla
    public static final float PANTALLA_WIDTH = 800;
    public static final float PANTALLA_HEIGHT = 480;

    //BOX2D va por metros
    public static final float MAPA_HEIGHT = 4.8f;
    public static final float MAPA_WIDTH = 8f;
    
    public Game game;

    //Camaras
    public OrthographicCamera camUser;
    public OrthographicCamera camBox2D;

    //Ayuda a dibujar
    public SpriteBatch spriteBatch;

    //Stage sirve para poner todos los elementos en la interface del usuario
    public Stage stage;

    //Constructor que recibe un "juego" MainGame
    public Pantalla(MainGame game){
        this.game=game;
        stage = new Stage(new StretchViewport(PANTALLA_WIDTH,PANTALLA_HEIGHT));
        //Inicializa la camara del usuario
        camUser = new OrthographicCamera(PANTALLA_WIDTH,PANTALLA_HEIGHT);
        //Para centrar la camara en la pantalla
        camUser.position.set(PANTALLA_WIDTH/2f,PANTALLA_HEIGHT/2f,0);
        //Inicializa la camara del box2d
        camBox2D = new OrthographicCamera(PANTALLA_WIDTH,PANTALLA_HEIGHT);
        //Para centrar la camara en la pantalla
        camBox2D.position.set(PANTALLA_WIDTH/2f,PANTALLA_HEIGHT/2f,0);

        //Primero capturar los eventos de esta clase y despues del stage (this despues stage)
        InputMultiplexer input = new InputMultiplexer(this,stage);
        Gdx.input.setInputProcessor(input);

        spriteBatch = new SpriteBatch();
    }

    //Actualiza a cada cierto tiempo
    @Override
    public void render(float delta) {

        actualizarF(delta);
        stage.act(delta);
        //borra todos los elementos en pantalla
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Dibujar el juego
        dibujar(delta);

        //y sobre el juego dibujar los elementos de la iterface de usuarios
        stage.draw();
    }

    //Metodo Draw sirve para dibujar todos los elementos en pantalla
    public abstract void dibujar(float tiempo);

    //Actualiza toda la fisica del juego
    public abstract void actualizarF(float tiempo);

    @Override
    public void resize(int width, int height) {
        //para que se la pantalla cambia de tamaño que vuelva a centrarla
        stage.getViewport().update(width,height,true);
    }

    //Metodo para saber si hizo click
    @Override
    public boolean keyDown(int keycode) {

        if(keycode== Input.Keys.BACK){
            //Menu Opcion: salir
            if(this instanceof MenuPrincipal){
                Gdx.app.exit();
            }else {
                //hacer visible el menu
                game.setScreen(new MenuPrincipal((MainGame) game));
            }

        }

        return super.keyDown(keycode);
    }

    @Override
    public void show() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
