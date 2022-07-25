package com.mygdx.game;

import static com.mygdx.game.Constants.H_PANTALLA;
import static com.mygdx.game.Constants.W_PANTALLA;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Pantalla que se muestra cuando el player pierde
 */
public class GameOverScreen extends BaseScreen{

    private Stage stage;
    private Skin skin; //atributo estilo de fuente
    private SpriteBatch batch; //atributo usado para dibujar sprites(texture etc..)
    private Image gameover; //Image gameover que muestra en la pantalla
    private Texture fondoGameOver;//atributo fondo de pantalla del game over
    private TextButton retry, menu; //botones
    public BitmapFont bmfHightScore; //para dibujar los puntos mas altos
    public BitmapFont bmfLastScore; // para dibujar los ultimos puntos
    private Music gameoverSon; //sonido para cuando esté en la pantalla de game over
    private Preferencias preferencias; //gestion de la preferencias del usuario


    /**
     * Constructor
     * @param game
     */

    public GameOverScreen(final MainGame game) {
        super(game);

        this.preferencias= game.preferencias;
        batch = new SpriteBatch();
        stage = new Stage(new FitViewport(W_PANTALLA,H_PANTALLA)); //se pasa el ancho y la altura que debe tener la pantalla


        //RECURSOS
        gameover = new Image(game.getManager().get("gameover.png", Texture.class));
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        gameoverSon = game.getManager().get("gameover.mp3");
        gameoverSon.setLooping(true);


       //PUNTUACION
        bmfHightScore = new BitmapFont(Gdx.files.internal("skin/default.fnt"));
        bmfHightScore.getData().setScale(4, 4);//escalar la fuente
        bmfLastScore = new BitmapFont(Gdx.files.internal("skin/default.fnt"));
        bmfLastScore.getData().setScale(4, 4);//escalar la fuente





        //BOTONES

        retry = new TextButton("Reintentar",skin);
        menu = new TextButton("Menu",skin);

        //comportamiento del boton retry
        retry.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                    gameoverSon.stop();
                    game.setScreen(new GameScreen(game));//llama a la pantalla del juego
            }
        });

        //comportamiento del boton menu
        menu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameoverSon.stop();
                game.setScreen(game.menuScreen);//llama a la pantalla del juego
            }
        });

        gameover.setPosition(320 - gameover.getWidth() / 2, 320 -gameover.getHeight());
        retry.setSize(200,80);
        retry.setPosition(60,50);
        menu.setSize(200,80);
        menu.setPosition(380,50);


        //Agregar al stage
        stage.addActor(menu);
        stage.addActor(retry);
        stage.addActor(gameover);


    }

    /**
     * Method el show solo se ejecuta cuando se visualiza la pantalla y donde se puede cargar recursos de forma segura
     */
    @Override
    public void show() {

        if(game.preferencias.getMusica()){
        gameoverSon.play();
        }
        Gdx.input.setInputProcessor(stage);
        fondoGameOver = new Texture("fondoGameOver.png");
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null); // no olvidar de poner el inputProcessor a null en hide para que no siga tratando la entrada
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(fondoGameOver,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight()); //fondo
        /**
         * Pintar los puntos mas altos
         */
        GlyphLayout scoLayout = new GlyphLayout(bmfHightScore, "High Score:" +preferencias.getHighScore(), Color.WHITE, 0, Align.topLeft, false);
        bmfHightScore.draw(batch, scoLayout, (W_PANTALLA/2+scoLayout.width), H_PANTALLA+(gameover.getHeight()+ scoLayout.height));

        /**
         * Pintar los ultimos puntos
         */
        GlyphLayout scoLastLayout = new GlyphLayout(bmfLastScore, "Last Score:" + preferencias.getLastScore(), Color.WHITE, 0, Align.bottomRight, false);
        bmfLastScore.draw(batch, scoLastLayout, (W_PANTALLA/2+(scoLayout.width+scoLastLayout.width)), H_PANTALLA+(gameover.getHeight()+ (scoLastLayout.height+scoLayout.height)));

        batch.end();
        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        batch.dispose();
        bmfHightScore.dispose();
        bmfLastScore.dispose();
        fondoGameOver.dispose();
        gameoverSon.dispose();
    }
}
