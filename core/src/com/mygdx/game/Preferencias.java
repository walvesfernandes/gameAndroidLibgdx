package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class Preferencias {


    //Solo esta actualizando la clase cuando cambia el valor de la musica
    Preferences preferencias = Gdx.app.getPreferences("Preferencias");

    public void setVibrador(boolean op) {
        preferencias.putBoolean("vibrar", op);
        preferencias.flush();
    }

    public boolean getVibrador() {
        return preferencias.getBoolean("vibrar");
    }

    public void setMusica(boolean op) {
        preferencias.putBoolean("musica", op);
        preferencias.flush();
    }

    public boolean getMusica() {
        return preferencias.getBoolean("musica");
    }

    public void setHighScore(int highScore) {
        preferencias.putInteger("highScore", highScore);
        preferencias.flush();
    }

    public int getHighScore() {
        return preferencias.getInteger("highScore");
    }

    public void setLastScore(int lastScore) {
        preferencias.putInteger("lastScore", lastScore);
        preferencias.flush();

    }

    public int getLastScore() {
        return preferencias.getInteger("lastScore");
    }

}
