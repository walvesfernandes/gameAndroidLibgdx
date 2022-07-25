package com.mygdx.game;

import static com.mygdx.game.Constants.H_PANTALLA;
import static com.mygdx.game.Constants.W_PANTALLA;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.BaseScreen;
import com.mygdx.game.MainGame;
import java.util.ArrayList;
import java.util.List;

public class TutorialScreen extends BaseScreen {

    private Stage stage;
    private static int indice=0;


    //Btn derecha
    private ImageButton imgBtnDerecha;
    private Texture imgDerecha;
    private TextureRegion txtDerecha;
    private TextureRegionDrawable tdDerecha;

    //Btn izquierda
    private ImageButton imgBtnIzd;
    private Texture imgIzd;
    private TextureRegion txtIzd;
    private TextureRegionDrawable tdIzd;

    private List<Texture> imgTutorial = new ArrayList<>(); //lista con varios enemigos

    private SpriteBatch batch;
    private Texture texture;


    /**
     * Method para coger una determinada textura de un List
     * @param list
     * @param indice
     * @return
     */
    public Texture cogerImg(List<Texture> list,int indice){

            texture = list.get(indice);


       return texture;
    }

    public TutorialScreen(final MainGame game) {
        super(game);

        stage = new Stage(new FitViewport(W_PANTALLA, H_PANTALLA));
        batch = new SpriteBatch();


        /**
         * ADD img del tutorial
         */
        imgTutorial.add(new Texture(Gdx.files.internal("tuto1.png")));
        imgTutorial.add(new Texture(Gdx.files.internal("tuto2.png")));
        imgTutorial.add(new Texture(Gdx.files.internal("tuto3.png")));
      //  imgTutorial.add(new Texture(Gdx.files.internal("tuto4.png")));

        /**
         * ImageButton derecha
         */
        imgDerecha = new Texture(Gdx.files.internal("pataderecha.png"));
        txtDerecha = new TextureRegion(imgDerecha);
        tdDerecha = new TextureRegionDrawable(txtDerecha);
        imgBtnDerecha = new ImageButton(tdDerecha);
        imgBtnDerecha.setPosition(W_PANTALLA-100,0);
        imgBtnDerecha.setSize(100,50);
        imgBtnDerecha.addListener(new ChangeListener() {

            public void changed(ChangeEvent event, Actor actor) {

                   if(indice > -1 && indice!=imgTutorial.size()-1){ //para cambiar el valor del indice
                       indice++;
                   }

            }
        });

        stage.addActor(imgBtnDerecha);


        /**
         * ImageButton izquierda
         */
        imgIzd = new Texture(Gdx.files.internal("pataizquierda.png"));
        txtIzd = new TextureRegion(imgIzd);
        tdIzd = new TextureRegionDrawable(txtIzd);
        imgBtnIzd = new ImageButton(tdIzd);
        imgBtnIzd.setPosition(0,0);
        imgBtnIzd.setSize(100,50);
        imgBtnIzd.addListener(new ChangeListener() {

            public void changed(ChangeEvent event, Actor actor) {

                if(indice > -1 && indice!=0){
                    indice--;
                }

            }
        });
        stage.addActor(imgBtnIzd);

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
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //volver a la pantalla anterior
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            game.setScreen(game.menuScreen);
        }

        batch.begin();
        batch.draw(cogerImg(imgTutorial,indice),W_PANTALLA/2,H_PANTALLA/2);
        batch.end();

        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        batch.dispose();

    }

}
