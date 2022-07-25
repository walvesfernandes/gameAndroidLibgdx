package com.mygdx.game;

import static com.mygdx.game.Constants.H_PANTALLA;
import static com.mygdx.game.Constants.W_PANTALLA;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class OpcionesScreen extends BaseScreen {

    private Stage stage;
    private Skin skin;
    private CheckBox cbMusic;
    private CheckBox cbVibrar;
    private Preferencias preferencias;
    private SpriteBatch batch;
    private Texture background;


    public OpcionesScreen(final MainGame game) {
        super(game);
        this.preferencias = game.preferencias;


        batch = new SpriteBatch();
        stage = new Stage(new FitViewport(W_PANTALLA, H_PANTALLA));


        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        background = game.getManager().get("fondoopciones.png");
        //checkbox Musica
        cbMusic = new CheckBox("Musica", skin);
        cbMusic.setPosition(20, 250);
        cbMusic.setChecked(preferencias.getMusica());

        cbMusic.addListener(new ChangeListener() {

            public void changed(ChangeEvent event, Actor actor) {

                if (cbMusic.isChecked()) {
                    preferencias.setMusica(true);
                    cbMusic.setChecked(true);

                } else {
                    preferencias.setMusica(false);
                    cbMusic.setChecked(false);
                }
            }
        });
        stage.addActor(cbMusic);


        //vibrador
        cbVibrar = new CheckBox("Vibrar", skin);
        cbVibrar.setPosition(20, 250 - cbMusic.getHeight());
        cbVibrar.setChecked(preferencias.getVibrador());
        cbVibrar.addListener(new ChangeListener() {

            public void changed(ChangeEvent event, Actor actor) {

//                preferencias.setVibrador(cbVibrar.isChecked());
//                cbVibrar.setChecked(cbVibrar.isChecked());

                if (cbVibrar.isChecked()) {
                    preferencias.setVibrador(true);
                    cbVibrar.setChecked(true);

                } else {
                    preferencias.setVibrador(false);
                    cbVibrar.setChecked(false);

                }
            }
        });


        stage.addActor(cbVibrar);

        //para pode usar el boton de volver del proprio movil
        Gdx.input.setCatchKey(Input.Keys.BACK, true);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null); // no olvidar de poner el inputProcessor a null en hide para que no siga tratando la entrada
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0); //limpiar la pantalla con un color
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);




        //volver a la pantalla anterior
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            game.setScreen(game.menuScreen);
        }

        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); //fondo
        batch.end();


        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
