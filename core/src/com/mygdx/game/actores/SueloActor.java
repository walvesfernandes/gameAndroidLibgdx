package com.mygdx.game.actores;

import static com.mygdx.game.Constants.PIXELS_POR_METRO;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class SueloActor extends Actor {

    private Texture suelo, overfloor;
    private World mundo;
    private Body body, leftBody;
    private Fixture fixture, leftFixture;

    /**
     *
     * @param world
     * @param floor
     * @param oFloor
     * @param x borda izquierda del suelo
     * @param width el suelo en metros
     * @param y parte superior del suelo
     */
    //x es borde izquierdo del suelo width el ancho que quiero que tenga, y es el borde superior(donde descansa los objetos)
    public SueloActor(World world, Texture floor,Texture oFloor, float x, float width, float y){
        this.mundo=world;
        this.suelo= floor;
        this.overfloor= oFloor;

        //body principal
        BodyDef def = new BodyDef();
        def.position.set(x+width/2,y-0.5f);
        body = world.createBody(def); //crear el body en el mundo
        //fixture principal
        PolygonShape sueloShape = new PolygonShape();
        sueloShape.setAsBox(width/2,0.5f); //1 metro por 1 metro
        fixture = body.createFixture(sueloShape,1);
        fixture.setUserData("floor");
        sueloShape.dispose();


        //borda izquierda del suelo por si choca con el suelo tambien muera
        BodyDef leftDef = new BodyDef();
        leftDef.position.set(x,y-0.55f);
        leftBody = world.createBody(leftDef);

        PolygonShape leftBox = new PolygonShape();
        leftBox.setAsBox(0.02f,0.45f); //1 metro por 1 metro
        leftFixture = leftBody.createFixture(leftBox,1);
        leftFixture.setUserData("fantasma"); //utilizo la fixture del paraguas para que pueda cochar en el lado izquierdo en los suelos elevados
        leftBox.dispose();

        setSize(width * PIXELS_POR_METRO, PIXELS_POR_METRO);
        setPosition(x * PIXELS_POR_METRO,(y-1)* PIXELS_POR_METRO);
    }

    /**
     * Dibujar las texturas
     * @param batch
     * @param parentAlpha
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {

        batch.draw(suelo,getX(),getY(), getWidth(), getHeight());
        batch.draw(overfloor,getX(),getY() +0.9f * getHeight(), getWidth(),0.1f*getHeight());
    }

    /**
     * Para quitar el objeto del mundo
     */
    public void dest(){
        body.destroyFixture(fixture);
        mundo.destroyBody(body);


    }
}
