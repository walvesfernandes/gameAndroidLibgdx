package com.mygdx.game;

import static com.mygdx.game.Constants.H_PANTALLA;
import static com.mygdx.game.Constants.W_PANTALLA;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;


/**
 * Pantalla que muestra mientras libgdx cargar los recursos con el AssetManager
 */
public class LoadingScreen extends BaseScreen {


    private Stage stage;

    private Skin skin;
    private Label loading; //para crear la label cuando esta cargando los recursos

    public LoadingScreen(MainGame game) {
        super(game);

        stage = new Stage(new FitViewport(W_PANTALLA, H_PANTALLA));
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));


        loading = new Label("Cargando...", skin);
        loading.setPosition(320 - loading.getWidth() / 2, 180 - loading.getHeight() / 2);
        stage.addActor(loading);
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(Gdx.gl20.GL_COLOR_BUFFER_BIT);


        /**
         * Update carga recurso y devuelve un true o false cuando termina de cargar los recursos
         */
        if (game.getManager().update()) {

            game.finishLoading(); // este metodo hace la segunda carga de recursos

        } else {

            int progress = (int) (game.getManager().getProgress() * 100);
            loading.setText("Cargando... " + progress + "%"); //devuelve el progreso de la carga de recursos
        }

        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
