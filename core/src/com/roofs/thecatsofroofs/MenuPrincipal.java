package com.roofs.thecatsofroofs;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.roofs.thecatsofroofs.utilitarios.Opciones;

import Pantallas.Mundo;

public class MenuPrincipal extends Pantalla {

    ScrollPane scroll;

    public MenuPrincipal(MainGame game) {
        super(game);
        Table menu = new Table();
        menu.setFillParent(true);
        menu.defaults().uniform().fillY();

    //agregar botones

        for(final Opciones op: Opciones.values()){

            TextButton btn = new TextButton(op.name,Recursos.txtBtnStyle);
            btn.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    MenuPrincipal.this.game.setScreen(getScreen(op));
                }
            });

            menu.row().padTop(20).height(50);
            //pasamos al boton que creamos con el for al menu
            menu.add(btn);
        }

        scroll = new ScrollPane(menu, Recursos.scrollPaneStyle);
        scroll.setSize(500, PANTALLA_HEIGHT);
        scroll.setPosition(150,0);
        stage.addActor(scroll); // el stage viene del padre
    }

    private Pantalla getScreen(Opciones op) {
        switch (op) {
            default:
                return new Mundo((MainGame) game);
        }
    }

    @Override
    public void dibujar(float tiempo) {

    }

    @Override
    public void actualizarF(float tiempo) {

    }
}
