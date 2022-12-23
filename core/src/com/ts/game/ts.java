package com.ts.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.ts.game.screens.LoadingScreen;

import java.io.Serializable;
import java.util.ArrayList;
class logicThread implements Runnable{
    private static ts game;
    public logicThread(ts game){
        this.game = game;
        createGame();
    }
    private static void createGame(){
        game.batch = new SpriteBatch();
        game.metadata = new ArrayList<String>();
        game.shapeRenderer = new ShapeRenderer();
        game.font = new BitmapFont();
        game.controls = new GameControls();
        game.resource = Resources.getGameResources();
        game.initializeMetadata();
    }
    @Override
    public void run() {
//        game.setScreen(new LoadingScreen(game));
    }
}
class animationThread implements Runnable{
    @Override
    public void run() {
    }
}
public class ts extends Game implements Serializable {
    public ArrayList<String> metadata;
    public GameControls controls;
    public SpriteBatch batch;
    public ShapeRenderer shapeRenderer;
    public BitmapFont font;
    public Resources resource;
    public int gameMode;

    @Override
    public void create () {
        logicThread logic = new logicThread(this);
        Thread logicThread = new Thread(logic);
        logicThread.start();
        setScreen(new LoadingScreen(this));
    }
    public void initializeMetadata(){
        //MetaData Structure for Load/Save Game
        metadata.add("0"); // 0 - Player 1 Choice of Tank
        metadata.add("0"); // 1 - Player 2 Choice of Tank
        metadata.add("10"); // 2 - Player 1 HealthBar
        metadata.add("10"); // 3 - Player 2 HealthBar
        metadata.add("-450,50"); // 4 - Player 1 Position
        metadata.add("450,-80"); // 5 - Player 2 Position
        metadata.add("0"); // 6 - Current Turn
        metadata.add("10"); // 7 - Player 1 Fuel
        metadata.add("10"); // 8 - Player 2 Fuel
        metadata.add("0"); // 9 - Player 1 Selected Weapon
        metadata.add("0"); // 10 - Player 2 Selected Weapon
        metadata.add("0"); // 11 - Player 1 Weapon Weel Information
        metadata.add("0"); // 12 - Player 2 Weapon Weel Information
        metadata.add("0"); // 13 - Player 1 Special Weapon
        metadata.add("0"); // 14 - Player 2 Special Weapon
        metadata.add("0"); // 15 - DropBox Location
        if(gameMode==0)metadata.add("0"); // 16 - GameMode
        else metadata.add("1");
        metadata.add("-650,1000;-650,-70;-500,27;-353,27;-178,-90;-40,-90;38,-145;227,-145;275,-112;508,-112;650,-13;650,1000"); // 17 - Ground Orientation
    }
    public void parseMetaData(ArrayList<String> newData){
        metadata = newData;
    }

    @Override
    public void dispose () {
        batch.dispose();
        shapeRenderer.dispose();
        font.dispose();
    }

    @Override
    public String toString(){
        String data = "";
        for(String s : metadata){
            data += s + ";";
        }
        return data;
    }
}

