package com.ts.game;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.ts.game.components.Tank;
import com.ts.game.components.Weapon;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class GameControls implements InputProcessor {
    //Variables
    private Resources res = Resources.getGameResources();
    private Tank player1;
    private Tank player2;
    private Weapon player1WeaponChoice;
    private Weapon player2WeaponChoice;
    private Weapon specialWpn;
    private int player1Fuel = 10;
    private int player2Fuel = 10;
    private int player1Health = 10;
    private int player2Health = 10;
    private int playerTankDirection = 0;
    private int player2TankDirection = 1;
    private int XcollisionCoord;
    private int YcollisionCoord;
    private int winner;
    private int currentPlayer = 0;
    private int gameTurns = 0;
    private double valueToCheck = -1;
    private double expectedValue = -1;
    private boolean shotActive = false;
    private boolean dropBoxPresent;
    private boolean developmentMode = false;
    private boolean wpnWindowActive = false;
    private boolean collisionDetection = false;
    private boolean mutableGround = false;
    private boolean testing = false;
    private ArrayList<Body> bodyToDelete = new ArrayList<Body>();
    private ArrayList<Image> fuelImages = new ArrayList<Image>();
    private ArrayList<Image> healthImages = new ArrayList<Image>();
    private ArrayList<String> scriptData = new ArrayList<String>();

    //Getters and Setters

    //1. Bullet Controls - Controls bullet movement and collision detection
    public int getXcollisionCoord() {
        return XcollisionCoord;
    }
    public void setXcollisionCoord(int xcollisionCoord) {
        XcollisionCoord = xcollisionCoord;
    }
    public int getYcollisionCoord() {
        return YcollisionCoord;
    }
    public void setYcollisionCoord(int ycollisionCoord) {
        YcollisionCoord = ycollisionCoord;
    }
    public boolean isCollisionDetection() {
        return collisionDetection;
    }
    public void setCollisionDetection(boolean collisionDetection) {
        this.collisionDetection = collisionDetection;
    }
    public boolean isShotActive() {
        return shotActive;
    }
    public void setShotActive(boolean shotActive) {
        this.shotActive = shotActive;
    }

    //2. HUD Controls - Controls all HUD elements and game information
    public int getPlayer1Health() {
        return player1Health;
    }
    public void setPlayer1Health(int player1Health) {
        this.player1Health = player1Health;
    }
    public int getPlayer2Health() {
        return player2Health;
    }
    public void setPlayer2Health(int player2Health) {
        this.player2Health = player2Health;
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
    public int getPlayerTankDirection() {
        return playerTankDirection;
    }
    public void setPlayerTankDirection(int playerTankDirection) {
        this.playerTankDirection = playerTankDirection;
    }
    public int getPlayer2TankDirection() {
        return player2TankDirection;
    }
    public void setPlayer2TankDirection(int player2TankDirection) {
        this.player2TankDirection = player2TankDirection;
    }

    //3. Weapon Controls - Controls all weapon related functions
    public boolean isWpnWindowActive() {
        return wpnWindowActive;
    }
    public void setWpnWindowActive(boolean wpnWindowActive) {
        this.wpnWindowActive = wpnWindowActive;
    }
    public Weapon getPlayer1WeaponChoice() {
        return player1WeaponChoice;
    }
    public void setPlayer1WeaponChoice(Weapon player1WeaponChoice) {
        this.player1WeaponChoice = player1WeaponChoice;
    }
    public Weapon getPlayer2WeaponChoice() {
        return player2WeaponChoice;
    }
    public void setPlayer2WeaponChoice(Weapon player2WeaponChoice) {
        this.player2WeaponChoice = player2WeaponChoice;
    }
    public Weapon getSpecialWpn() {
        return specialWpn;
    }
    public void setSpecialWpn(Weapon specialWpn) {
        this.specialWpn = specialWpn;
    }
    public boolean isDropBoxPresent() {
        return dropBoxPresent;
    }
    public void setDropBoxPresent(boolean dropBoxPresent) {
        this.dropBoxPresent = dropBoxPresent;
    }

    //4. Gameplay Controls - Controls the game flow
    public boolean isDevelopmentMode() {
        return developmentMode;
    }
    public void setDevelopmentMode(boolean developmentMode) {
        this.developmentMode = developmentMode;
    }
    public int getGameTurns() {
        return gameTurns;
    }
    public void setGameTurns(int gameTurns) {
        this.gameTurns = gameTurns;
    }
    public int getWinner() {
        return winner;
    }
    public void setWinner(int winner) {
        this.winner = winner;
    }
    public int getCurrentPlayer() {
        return currentPlayer;
    }
    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
    public Tank getCurrentTank(){
        if(currentPlayer == 0)return player1;
        else return player2;
    }
    public boolean isMutableGround() {
        return mutableGround;
    }
    public void setMutableGround(boolean mutableGround) {
        this.mutableGround = mutableGround;
    }
    public boolean isTesting() {
        return testing;
    }
    public void setTesting(boolean testing) {
        this.testing = testing;
    }
    public double getValueToCheck() {
        return valueToCheck;
    }
    public void setValueToCheck(double valueToCheck) {
        this.valueToCheck = valueToCheck;
    }
    public double getExpectedValue() {
        return expectedValue;
    }
    public void setExpectedValue(double expectedValue) {
        this.expectedValue = expectedValue;
    }

    // 5. Render Controls - Used only for Box2D and Scene2D rendering
    public ArrayList<Body> getBodyToDelete() {
        return bodyToDelete;
    }
    public void addBodyToDelete(Body body) {
        bodyToDelete.add(body);
    }
    public void setBodyToDelete(ArrayList<Body> bodyToDelete) {
        this.bodyToDelete = bodyToDelete;
    }
    public Image getFuelImageAtIndex(int index){
        return fuelImages.get(index);
    }
    public Image getHealthImageAtIndex(int index){
        return healthImages.get(index);
    }
    public String getScriptDataAtIndex(int index){
        return scriptData.get(index);
    }
    public int getScriptDataSize(){
        return scriptData.size();
    }

    //Methods
    public void resetGameControl(){
        player1 = null;
        player2 = null;
        player1WeaponChoice = null;
        player2WeaponChoice = null;
        player1Fuel = 10;
        player2Fuel = 10;
        player1Health = 10;
        player2Health = 10;
        winner = 0;
        bodyToDelete = new ArrayList<Body>();
        currentPlayer = 0;
        shotActive = false;
        res = Resources.getGameResources();
        fuelImages = new ArrayList<Image>();
        healthImages = new ArrayList<Image>();
        gameTurns = 0;
        dropBoxPresent = false;
        specialWpn = null;
        wpnWindowActive = false;
        playerTankDirection = 0;
        player2TankDirection = 1;
    } // Needs to be reset at start of every new game
    public void initiateFuelImages(){
        fuelImages.add(new Image(res.getFuel_100())); // P1 100
        fuelImages.add(new Image(res.getFuel_100())); // P1 100
        fuelImages.add(new Image(res.getFuel_90())); // P1 90
        fuelImages.add(new Image(res.getFuel_90())); // P1 90
        fuelImages.add(new Image(res.getFuel_80())); // P1 80
        fuelImages.add(new Image(res.getFuel_80())); // P1 80
        fuelImages.add(new Image(res.getFuel_70())); // P1 70
        fuelImages.add(new Image(res.getFuel_70())); // P1 70
        fuelImages.add(new Image(res.getFuel_60())); // P1 60
        fuelImages.add(new Image(res.getFuel_60())); // P1 60
        fuelImages.add(new Image(res.getFuel_50())); // P1 50
        fuelImages.add(new Image(res.getFuel_50())); // P1 50
        fuelImages.add(new Image(res.getFuel_40())); // P1 40
        fuelImages.add(new Image(res.getFuel_40())); // P1 40
        fuelImages.add(new Image(res.getFuel_30())); // P1 30
        fuelImages.add(new Image(res.getFuel_30())); // P1 30
        fuelImages.add(new Image(res.getFuel_20())); // P1 20
        fuelImages.add(new Image(res.getFuel_20())); // P1 20
        fuelImages.add(new Image(res.getFuel_10())); // P1 10
        fuelImages.add(new Image(res.getFuel_10())); // P1 10
        fuelImages.add(new Image(res.getFuel_0())); // P1 0
        fuelImages.add(new Image(res.getFuel_0())); // P1 0
    } // Create Fuel Images for HUD
    public void initiateHealthImages(){
        healthImages.add(new Image(res.getHealth0()));
        healthImages.add(new Image(res.getHealth1()));
        healthImages.add(new Image(res.getHealth2()));
        healthImages.add(new Image(res.getHealth3()));
        healthImages.add(new Image(res.getHealth4()));
        healthImages.add(new Image(res.getHealth5()));
        healthImages.add(new Image(res.getHealth6()));
        healthImages.add(new Image(res.getHealth7()));
        healthImages.add(new Image(res.getHealth8()));
        healthImages.add(new Image(res.getHealth9()));
        healthImages.add(new Image(res.getHealth10()));
        healthImages.add(new Image(res.getHealth0()));
        healthImages.add(new Image(res.getHealth1()));
        healthImages.add(new Image(res.getHealth2()));
        healthImages.add(new Image(res.getHealth3()));
        healthImages.add(new Image(res.getHealth4()));
        healthImages.add(new Image(res.getHealth5()));
        healthImages.add(new Image(res.getHealth6()));
        healthImages.add(new Image(res.getHealth7()));
        healthImages.add(new Image(res.getHealth8()));
        healthImages.add(new Image(res.getHealth9()));
        healthImages.add(new Image(res.getHealth10()));

    } // Create Health Images for HUD
    public void initiatePlayers(Tank player1, Tank player2){
        this.player1 = player1;
        this.player2 = player2;
    } // Store tank choices
    public void parseScriptData() throws FileNotFoundException {
        try {
            FileInputStream script = new FileInputStream(String.format("Scripts/script%d.txt", ((int) (Math.random() * 2) + 1)));
            Scanner sc = new Scanner(script);
            while (sc.hasNextLine()) {
                scriptData.add(sc.nextLine());
            }
            sc.close();
            System.out.println(scriptData);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //Extend Methods
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
