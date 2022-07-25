package com.mygdx.game;

import static com.mygdx.game.Constants.H_PANTALLA;
import static com.mygdx.game.Constants.W_PANTALLA;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Clase que dibuja el Menu
 */
public class MenuScreen extends BaseScreen {

    private Stage stage;
    private Skin skin;

    //Botones
    private TextButton playBtn; //Boton para jugar
    private TextButton opBtn; //Boton para ir a las opciones del juego
    private TextButton crediBtn; //Boton que lleva a los creditos del juego
    private TextButton tutorialBtn; //Boton que lleva a la pantalla del tutorial
    private TextButton salirBtn; //Boton para salir del juego

    //Recursos graficos
    private Image logo;
    private SpriteBatch batch;
    private Texture background;
    private Texture logoGyro;

    //Giroscopio
    boolean gyroscopeAvail = Gdx.input.isPeripheralAvailable(Input.Peripheral.Gyroscope); //para saber si el giroscopio esta activo
    float gyroX = 0;
    float gyroY = 0;
    float gyroZ = 0;


    /**
     * Giroscopio
     * Method que actualiza las coordenadas del gyroscope, y cambia el color de los botones
     * y mueve el titulo del juego en la pantalla del menu(this)
     */
    public void updateGyro() {
        if (gyroscopeAvail) {
            if ((gyroX - Gdx.input.getGyroscopeY()) * 50 > 40) {
                if (logo.getX() > 0) {
                    tutorialBtn.setColor(Color.BLUE);
                    logo.setPosition(logo.getX() - W_PANTALLA / 40, logo.getY());
                }else{
                    tutorialBtn.setColor(Color.DARK_GRAY);
                }
            }
            if ((gyroX - Gdx.input.getGyroscopeY()) * 50 < -40) {
                if (logo.getX() + logo.getWidth() <= W_PANTALLA) {
                    crediBtn.setColor(Color.BLUE);
                    logo.setPosition(logo.getX() + W_PANTALLA / 40, logo.getY());
                }else{
                    crediBtn.setColor(Color.DARK_GRAY);
                }
            }
            if ((gyroX - Gdx.input.getGyroscopeZ()) * 50 > 40) {
                if (logo.getY() > 0) {

                    playBtn.setColor(Color.BLUE);
                    logo.setPosition(logo.getX(), logo.getY() - W_PANTALLA / 40);
                }else{
                    playBtn.setColor(Color.DARK_GRAY);
                }
            }
            if ((gyroX - Gdx.input.getGyroscopeZ()) * 50 < -40) {
                if (logo.getY() + logo.getHeight() <= W_PANTALLA) {
                    opBtn.setColor(Color.BLUE);
                    logo.setPosition(logo.getX(), logo.getY() + W_PANTALLA / 40);
                }else{
                    opBtn.setColor(Color.DARK_GRAY);

                }
            }

            gyroY = Gdx.input.getGyroscopeX();
            gyroX = Gdx.input.getGyroscopeY();
            gyroZ = Gdx.input.getGyroscopeZ();
        }
    }

    /**
     * Constructor
     *
     * @param game donde se instancia los objetos
     */
    public MenuScreen(final MainGame game) {
        super(game);

        stage = new Stage(new FitViewport(W_PANTALLA, H_PANTALLA));

        skin = new Skin(Gdx.files.internal("skin/uiskin.json")); //json con la fuente que coge del png
        logo = new Image(game.getManager().get("logo.png", Texture.class));
        logo.setPosition(W_PANTALLA - 120 - logo.getWidth() / 2, 220 - logo.getHeight());

        //Botones
        playBtn = new TextButton("Jugar", skin); //instanciar el boton con el texto y la fuente que es la skin
        opBtn = new TextButton("Opciones", skin);
        crediBtn = new TextButton("Creditos", skin);
        tutorialBtn = new TextButton("Tutorial",skin);
        salirBtn = new TextButton("X",skin);

        /**
         * MENU
         */
        //PLAYBTN
        playBtn.setSize(100, 50);
        playBtn.setPosition(110, 50);
        playBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new GameScreen(game));//llama a la pantalla del juego
            }
        });
        stage.addActor(playBtn);

        //CREDITOSBTN
        crediBtn.setSize(100, 50);
        crediBtn.setPosition(210, 50);
        crediBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new Creditos(game));//llama a la pantalla del juego
            }
        });

        stage.addActor(crediBtn);

        //TUTORIAL
        tutorialBtn.setSize(100, 50);
        tutorialBtn.setPosition(313, 50);
        tutorialBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new TutorialScreen(game));//llama a la pantalla del juego
            }
        });
        stage.addActor(tutorialBtn);

        //OPCIONESBTN
        opBtn.setSize(100, 50);
        opBtn.setPosition(413, 50);
        opBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.opcionesScreen);//llama a la pantalla del juego
            }
        });
        stage.addActor(opBtn);


        //SALIRBTN
        salirBtn.setColor(Color.RED);
        salirBtn.setSize(40, 40);
        salirBtn.setPosition(W_PANTALLA-salirBtn.getWidth(),H_PANTALLA-salirBtn.getHeight());
        salirBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
        stage.addActor(salirBtn);


        logo.setPosition(320 - logo.getWidth() / 2, 320 - logo.getHeight());
        stage.addActor(logo);


    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        batch = new SpriteBatch();
        background = game.getManager().get("background.png");
        logoGyro = game.getManager().get("catLogo.png");

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null); // no olvidar de poner el inputProcessor a null en hide para que no siga tratando la entrada
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        updateGyro();
        batch.begin(); // el sprite batch pinta td a la vez y no se puede olvidar de poner el end al final
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); //fondo
        batch.draw(logoGyro, W_PANTALLA / 2 + logoGyro.getWidth(), (H_PANTALLA - logo.getHeight()));
        batch.end();
        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        batch.dispose();
        skin.dispose();
    }
}
