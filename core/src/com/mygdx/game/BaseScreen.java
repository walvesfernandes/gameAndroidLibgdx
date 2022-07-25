package com.mygdx.game;

import com.badlogic.gdx.Screen;


//class base para crear otras pantallas
public class BaseScreen implements Screen {

    protected MainGame game;

    public BaseScreen(MainGame game){
        this.game=game;
    }


    //Mostrar la pantalla
    @Override
    public void show() {

    }


    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

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
