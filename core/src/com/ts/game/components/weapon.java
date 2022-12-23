package com.ts.game.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.*;
import com.ts.game.CollisionDetection;

public class Weapon {
    private String name;
    private double damage;
    private double range;
    private boolean isShooting;
    private Texture bulletTexture;
    public boolean isShooting() {
        return isShooting;
    }

    public Texture getTexture() {
        return bulletTexture;
    }
    public void setShooting(boolean shooting) {
        isShooting = shooting;
    }
    public Weapon(String name, double damage, double range, Texture texture){
        this.name = name;
        this.damage = damage;
        this.range = range;
        this.bulletTexture = texture;
    }
    public String getName (){
        return name;
    }
    public Body shoot(World gameWorld, Body tankBody, int check, int forceX, int forceY){
        int Xoffset = 0;
        int forceXDirectional = 0;
        if(check==0) {
            Xoffset = 60;
            forceXDirectional = 10000*forceX;
        }
        else {
            Xoffset = -60;
            forceXDirectional = -10000*forceX;
        }
        BodyDef bulletBodyDef = new BodyDef();
        bulletBodyDef.type = BodyDef.BodyType.DynamicBody;
        bulletBodyDef.position.set(tankBody.getPosition().x+Xoffset, tankBody.getPosition().y+5);
        CircleShape bulletShape = new CircleShape();
        bulletShape.setRadius(10);
        FixtureDef bulletFixtureDef = new FixtureDef();
        bulletFixtureDef.density = 1.0f;
        bulletFixtureDef.friction = 0.0f;
        bulletFixtureDef.restitution = 0.0f;
        bulletFixtureDef.shape = bulletShape;
        Body bulletBody = gameWorld.createBody(bulletBodyDef);
        bulletBody.createFixture(bulletFixtureDef);
        bulletBody.applyLinearImpulse(forceXDirectional, 7500*forceY, bulletBody.getPosition().x, bulletBody.getPosition().y, true);
        bulletBody.setUserData("bullet");
        return bulletBody;
    }
}
