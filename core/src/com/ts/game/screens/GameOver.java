package com.ts.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.ts.game.ts;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class GameOver extends ScreenAdapter {
    private Stage stage;
    private ts game;

    public GameOver(ts game) {
        this.game = game;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        stage.act(delta);
        stage.draw();
    }
    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }
    @Override
    public void show() {
        String winner;
        if(game.controls.getWinner()==0) winner = "Player 1";
        else winner = "Player 2";
        final Dialog menu = new Dialog("", game.resource.getSkin());
        VerticalGroup buttons = new VerticalGroup();
        Label over = new Label("Game Over", game.resource.getSkin());
        Label win = new Label(String.format("Winner: %s", winner), game.resource.getSkin());
        TextButton restartBtn = new TextButton("Restart", game.resource.getTextButtonStyle1());
        TextButton exitBtn = new TextButton("Exit", game.resource.getTextButtonStyle1());
        over.setFontScale(2);
        over.setColor(0,0,0,1);
        win.setFontScale(2);
        win.setColor(0,0,0,1);
        buttons.addActor(over);
        buttons.space(10);
        buttons.addActor(win);
        buttons.space(10);
        buttons.addActor(restartBtn);
        buttons.space(10);
        buttons.addActor(exitBtn);
        buttons.pad(15);
        buttons.padTop(10);
        buttons.padBottom(10);
        menu.add(buttons);
        menu.setBackground(game.resource.getMenuPageSide());
        menu.show(stage);
        menu.setSize(buttons.getWidth(), buttons.getHeight());
        menu.setPosition((float)Gdx.graphics.getWidth()/ 2 - menu.getWidth() / 2, (float)Gdx.graphics.getHeight() / 2 - menu.getHeight() / 2);
        restartBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.resource.getBtnClickAudio().play();
                game.gameMode = 0;
                String player1TankChoice = game.metadata.get(0);
                String player2TankChoice = game.metadata.get(1);
                game.metadata = new ArrayList<String>();
                game.initializeMetadata();
                game.metadata.set(0, player1TankChoice);
                game.metadata.set(1, player2TankChoice);
                game.setScreen(new GameScreen(game));
            }
        });
        exitBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.resource.getBtnClickAudio().play();
                game.metadata = new ArrayList<String>();
                game.initializeMetadata();
                game.setScreen(new MainMenu(game));
            }
        });
    }
    @Override
    public void dispose() {
        super.dispose();
    }
}
