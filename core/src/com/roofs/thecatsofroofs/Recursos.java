package com.roofs.thecatsofroofs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;

import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class Recursos {

    public static BitmapFont font;
    public static TextButtonStyle txtBtnStyle;
    public static ScrollPaneStyle scrollPaneStyle;

    public static void load(){

        font = new BitmapFont();

        //donde esta el archivo
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("data/ui.txt"));

        NinePatchDrawable bt = new NinePatchDrawable(atlas.createPatch("bt"));

        NinePatchDrawable btDown = new NinePatchDrawable(atlas.createPatch("btDown"));


        txtBtnStyle = new TextButtonStyle(bt,btDown,null,font);
        NinePatchDrawable knob = new NinePatchDrawable(atlas.createPatch("scroll"));
        scrollPaneStyle = new ScrollPaneStyle(null, knob,knob,knob,knob);

    }

}
