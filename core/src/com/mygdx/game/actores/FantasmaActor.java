package com.mygdx.game.actores;

import static com.mygdx.game.Constants.PIXELS_POR_METRO;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class FantasmaActor extends Actor {


    private Texture texture;
    private World mundo;
    private Body body;
    private Fixture fixture;


    public FantasmaActor(World world, Texture texture, float x, float y ){

        this.mundo=world;
        this.texture= texture;
        BodyDef def = new BodyDef();
        def.position.set(x,y + 0.5f);
        body = world.createBody(def);


        //crea la forma de un triangulo con los vertices
        PolygonShape paragShape = new PolygonShape();
        Vector2[] vertices =new Vector2[3];
        vertices[0] = new Vector2(-0.5f,-0.5f);
        vertices[1] = new Vector2(0.5f,-0.5f);
        vertices[2] = new Vector2(0,0.5f);
        paragShape.set(vertices);
        fixture = body.createFixture(paragShape,1);
        fixture.setUserData("fantasma");
        paragShape.dispose();


        setSize(PIXELS_POR_METRO, PIXELS_POR_METRO);
        setPosition((x-0.5f)* PIXELS_POR_METRO, y * PIXELS_POR_METRO);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition((body.getPosition().x-0.5f) * PIXELS_POR_METRO,
                (body.getPosition().y-0.5f) * PIXELS_POR_METRO);
        batch.draw(texture,getX(),getY(), getWidth(), getHeight());
    }

    //para quitar el objeto del mundo
    public void dest(){
        body.destroyFixture(fixture);
        mundo.destroyBody(body);


    }


}
