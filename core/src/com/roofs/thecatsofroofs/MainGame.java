package com.roofs.thecatsofroofs;


import com.badlogic.gdx.Game;

public class MainGame extends Game {

	@Override
	public void create() {
		//cargar menu
		Recursos.load();
		setScreen(new MenuPrincipal(this));


	}
}
