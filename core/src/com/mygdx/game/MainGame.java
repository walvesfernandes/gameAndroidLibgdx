package com.mygdx.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;

/**
 * Main game es la clase principal que libgdx ejecuta primero
 * para mas informacion sobre libgdx https://libgdx.com/
 */
public class MainGame extends Game {

    public AssetManager getManager() {
        return manager;
    }

    //Screens
    private LoadingScreen loadingScreen;
    public GameScreen gameScreen;
    public TutorialScreen tutorialScreen;
    public GameOverScreen gameOverScreen;
    public MenuScreen menuScreen;
    public OpcionesScreen opcionesScreen;

    //Clases de gestion
    public Preferencias preferencias;
    private AssetManager manager;

    @Override
    public void create() {


        preferencias = new Preferencias();

        //Primero el manager hace la cargar de los archivos y luego de las clases
        manager = new AssetManager();


        manager.load("muerto.wav", Music.class);
        manager.load("gameover.mp3",Music.class);
        manager.load("catMenu.wav",Music.class);
        manager.load("catLogo.png", Texture.class);
        manager.load("floor.png", Texture.class);
        manager.load("overfloor.png", Texture.class);
        manager.load("tejado.png", Texture.class);
        manager.load("gameover.png", Texture.class);
        manager.load("fantasma.png", Texture.class);
        manager.load("logo.png", Texture.class);
        manager.load("background.png", Texture.class);
        manager.load("suelotejado.png", Texture.class);
        manager.load("fondoopciones.png",Texture.class);

        loadingScreen = new LoadingScreen(this);

        setScreen(loadingScreen);

    }

    public void finishLoading() {
        menuScreen = new MenuScreen(this);
        tutorialScreen = new TutorialScreen(this);
        gameScreen = new GameScreen(this);
        gameOverScreen = new GameOverScreen(this);
        opcionesScreen = new OpcionesScreen(this);
        setScreen(new MenuScreen(this));
    }
}
