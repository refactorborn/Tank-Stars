package com.ts.game;

import com.badlogic.gdx.physics.box2d.*;

import java.util.Objects;

public class CollisionDetection implements ContactListener {
    private ts game;
    final String tank1 = "playerTank";
    final String tank2 = "enemyTank";
    final String ground = "ground";
    final String bullet = "bullet";
    final String dropBox = "dropBox";

    public CollisionDetection(ts game){
        this.game = game;
    }
    @Override
    public void beginContact(Contact contact) {
        tank_tank_contact(contact);
        bullet_ground_contact(contact);
        tank_dropbox_contact(contact);
        bullet_tank_contact(contact);
        bullet_dropbox_contact(contact);
    }
    @Override
    public void endContact(Contact contact) {}
    private void tank_dropbox_contact(Contact contact) {
        if (contact.getFixtureA().getBody().getUserData() == tank1 && contact.getFixtureB().getBody().getUserData() == dropBox) {
            game.controls.addBodyToDelete(contact.getFixtureB().getBody());
            game.controls.setDropBoxPresent(false);
            game.metadata.set(13, String.valueOf(1));
        }
        if(contact.getFixtureA().getBody().getUserData() == tank2 && contact.getFixtureB().getBody().getUserData() == dropBox){
            game.controls.addBodyToDelete(contact.getFixtureB().getBody());
            game.controls.setDropBoxPresent(false);
            game.metadata.set(14, String.valueOf(1));
        }
    }
    private void tank_tank_contact(Contact contact) {
        if ((contact.getFixtureA().getBody().getUserData() == tank1 && contact.getFixtureB().getBody().getUserData() == tank2) ||
                (contact.getFixtureA().getBody().getUserData() == tank2 && contact.getFixtureB().getBody().getUserData() == tank1)) {
        }
    }
    private void bullet_ground_contact(Contact contact){
        if (contact.getFixtureA().getBody().getUserData() == bullet && contact.getFixtureB().getBody().getUserData() == ground) {
            game.controls.addBodyToDelete(contact.getFixtureA().getBody());
            game.controls.setShotActive(false);
        }
        if (contact.getFixtureA().getBody().getUserData() == ground && contact.getFixtureB().getBody().getUserData() == bullet) {
            game.controls.addBodyToDelete(contact.getFixtureB().getBody());
            game.controls.setShotActive(false);
        }
    }
    private void bullet_tank_contact(Contact contact){
        if (contact.getFixtureA().getBody().getUserData() == bullet && contact.getFixtureB().getBody().getUserData() == tank1) {
            if(Objects.equals(game.metadata.get(9), "-1") || Objects.equals(game.metadata.get(10), "-1")) {
                game.controls.setExpectedValue(game.controls.getPlayer1Health() - 5);
                game.controls.setPlayer1Health(game.controls.getPlayer1Health() - 5);
            }
            else {
                game.controls.setExpectedValue(game.controls.getPlayer1Health() - 1);
                game.controls.setPlayer1Health(game.controls.getPlayer1Health() - 1);
            }
            game.controls.setValueToCheck(game.controls.getPlayer1Health());
            game.controls.addBodyToDelete(contact.getFixtureA().getBody());
            game.controls.setShotActive(false);
        }
        if (contact.getFixtureA().getBody().getUserData() == tank1 && contact.getFixtureB().getBody().getUserData() == bullet) {
            if(Objects.equals(game.metadata.get(9), "-1") || Objects.equals(game.metadata.get(10), "-1")) {
                game.controls.setExpectedValue(game.controls.getPlayer1Health() - 5);
                game.controls.setPlayer1Health(game.controls.getPlayer1Health() - 5);
            }
            else {
                game.controls.setExpectedValue(game.controls.getPlayer1Health() - 1);
                game.controls.setPlayer1Health(game.controls.getPlayer1Health() - 1);
            }
            game.controls.setValueToCheck(game.controls.getPlayer1Health());
            game.controls.addBodyToDelete(contact.getFixtureB().getBody());
            game.controls.setShotActive(false);
        }
        if (contact.getFixtureA().getBody().getUserData() == bullet && contact.getFixtureB().getBody().getUserData() == tank2) {
            if(Objects.equals(game.metadata.get(9), "-1") || Objects.equals(game.metadata.get(10), "-1")) {
                game.controls.setExpectedValue(game.controls.getPlayer2Health() - 5);
                game.controls.setPlayer2Health(game.controls.getPlayer2Health() - 5);
            }
            else {
                game.controls.setExpectedValue(game.controls.getPlayer2Health() - 1);
                game.controls.setPlayer2Health(game.controls.getPlayer2Health() - 1);
            }
            game.controls.setValueToCheck(game.controls.getPlayer2Health());
            game.controls.addBodyToDelete(contact.getFixtureA().getBody());
            game.controls.setShotActive(false);
        }
        if (contact.getFixtureA().getBody().getUserData() == tank2 && contact.getFixtureB().getBody().getUserData() == bullet) {
            if(Objects.equals(game.metadata.get(9), "-1") || Objects.equals(game.metadata.get(10), "-1")) {
                game.controls.setExpectedValue(game.controls.getPlayer2Health() - 5);
                game.controls.setPlayer2Health(game.controls.getPlayer2Health() - 5);
            }
            else {
                game.controls.setExpectedValue(game.controls.getPlayer2Health() - 1);
                game.controls.setPlayer2Health(game.controls.getPlayer2Health() - 1);
            }
            game.controls.setValueToCheck(game.controls.getPlayer2Health());
            game.controls.addBodyToDelete(contact.getFixtureB().getBody());
            game.controls.setShotActive(false);
        }
    }
    private void bullet_dropbox_contact(Contact contact){
        if ((contact.getFixtureA().getBody().getUserData() == dropBox && contact.getFixtureB().getBody().getUserData() == bullet) ||
                (contact.getFixtureA().getBody().getUserData() == bullet && contact.getFixtureB().getBody().getUserData() == dropBox)) {
            game.controls.addBodyToDelete(contact.getFixtureB().getBody());
            game.controls.addBodyToDelete(contact.getFixtureA().getBody());
            game.controls.setDropBoxPresent(false);
            game.controls.setShotActive(false);
        }
    }
    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        if ((contact.getFixtureA().getBody().getUserData() == tank1 && contact.getFixtureB().getBody().getUserData() == dropBox) ||
                (contact.getFixtureA().getBody().getUserData() == tank2 && contact.getFixtureB().getBody().getUserData() == dropBox)){
            contact.setEnabled(false);
        }
        if ((contact.getFixtureA().getBody().getUserData() == tank1 && contact.getFixtureB().getBody().getUserData() == tank2) ||
                (contact.getFixtureA().getBody().getUserData() == tank2 && contact.getFixtureB().getBody().getUserData() == tank1)){
            contact.setEnabled(false);
        }
        //destroy bullet after collision
        if (contact.getFixtureA().getBody().getUserData() == bullet && contact.getFixtureB().getBody().getUserData() == ground) {
            contact.getFixtureA().getBody().setUserData("destroy");
            game.controls.setCollisionDetection(true);
            game.controls.setXcollisionCoord((int) contact.getFixtureA().getBody().getPosition().x);
            game.controls.setYcollisionCoord((int) contact.getFixtureA().getBody().getPosition().y);
        }
        if (contact.getFixtureA().getBody().getUserData() == ground && contact.getFixtureB().getBody().getUserData() == bullet) {
            contact.getFixtureB().getBody().setUserData("destroy");
            game.controls.setCollisionDetection(true);
            game.controls.setXcollisionCoord((int) contact.getFixtureB().getBody().getPosition().x);
            game.controls.setYcollisionCoord((int) contact.getFixtureB().getBody().getPosition().y);
        }
    }
    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {}
}
