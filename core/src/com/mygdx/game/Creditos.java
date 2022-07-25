package com.mygdx.game;

import static com.mygdx.game.Constants.H_PANTALLA;
import static com.mygdx.game.Constants.W_PANTALLA;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;


/**
 * Clase para crear la pantalla de creditos
 */
public class Creditos extends BaseScreen{

    private SpriteBatch batch; //para pintar el texto
    private BitmapFont texto; //atributo para crear el texto


    /**
     * Constructor
     * @param game
     */
    public Creditos(final MainGame game) {
        super(game);

        texto = new BitmapFont(Gdx.files.internal("skin/default.fnt"));
        texto.getData().setScale(3, 3);
        batch= new SpriteBatch();




    }


    /**
     * Method el show solo se ejecuta cuando se visualiza la pantalla
     */
    @Override
    public void show() {

    }

    /**
     * Hide es como un dispose para actores y algunos otros tipos de recursos
     */
    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null); // no olvidar de poner el inputProcessor a null en hide para que no siga tratando la entrada
    }

    /**
     * Method donde libgdx actualiza el juego (actualizar imagenes etc...
     *
     * @param delta
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //volver a la pantalla anterior
        if(Gdx.input.isKeyPressed(Input.Keys.BACK)){
            game.setScreen(game.menuScreen);
        }

        batch.begin();
        texto.draw(batch, "Juego desarrollado con libGDX 2022 framework \ncon el lenguaje de programacíon Java\nDesarrollador: Willian Alves Fernandes\nSonidos:\nMusica: Willian Alves Fernandes\nGato: sonidosmp3gratis\nImagenes:\nPersonaje:gameart2d.com y Willian Alves Fernandes\nFondo game: gameart2d.com\nFuente: libgdx", W_PANTALLA, W_PANTALLA+ texto.getScaleY(), 0, Align.topLeft,true);
        batch.end();


    }

    @Override
    public void dispose() {

    }
}
