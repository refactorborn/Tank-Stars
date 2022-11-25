package com.ts.game;

import com.badlogic.gdx.InputProcessor;
import com.ts.game.components.tank;
import com.ts.game.components.weapon;

public class GameControls implements InputProcessor {
    private tank player1;
    private tank player2;
    private weapon player1WeaponChoice;
    private weapon player2WeaponChoice;
    private boolean bgMusicOn = false;

    public weapon getPlayer1WeaponChoice() {
        return player1WeaponChoice;
    }

    public void setPlayer1WeaponChoice(weapon player1WeaponChoice) {
        this.player1WeaponChoice = player1WeaponChoice;
    }

    public weapon getPlayer2WeaponChoice() {
        return player2WeaponChoice;
    }

    public void setPlayer2WeaponChoice(weapon player2WeaponChoice) {
        this.player2WeaponChoice = player2WeaponChoice;
    }

    public boolean isBgMusicOn() {
        return bgMusicOn;
    }
    public void setBgMusicOn(boolean bgMusicOn) {
        this.bgMusicOn = bgMusicOn;
    }
    private int player1Fuel = 100;
    private int player2Fuel = 100;
    private boolean bgAudio = true;
    public boolean isBgAudio() {
        return bgAudio;
    }
    public void setBgAudio(boolean bgAudio) {
        this.bgAudio = bgAudio;
    }
    public int getPlayer1Fuel() {
        return player1Fuel;
    }
    public void setPlayer1Fuel(int player1Fuel) {
        this.player1Fuel = player1Fuel;
    }
    public int getPlayer2Fuel() {
        return player2Fuel;
    }
    public void setPlayer2Fuel(int player2Fuel) {
        this.player2Fuel = player2Fuel;
    }
    private int currentPlayer = 0;
    private boolean weaponWindowEnabled = false;
    public boolean isWeaponWindowEnabled() {
        return weaponWindowEnabled;
    }
    public void setWeaponWindowEnabled(boolean weaponWindowEnabled) {
        this.weaponWindowEnabled = weaponWindowEnabled;
    }
    public int getCurrentPlayer() {
        return currentPlayer;
    }
    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
    public void initiatePlayers(tank player1, tank player2){
        this.player1 = player1;
        this.player2 = player2;
    }
    public tank getCurrentTank(){
        if(currentPlayer == 0)return player1;
        else return player2;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
