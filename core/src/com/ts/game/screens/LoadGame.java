package com.ts.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.ts.game.ts;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class LoadGame extends ScreenAdapter {
    private Stage stage;
    private ts game;

    public LoadGame(ts game) {
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
        Dialog menu = new Dialog("", game.resource.getSkin());
        VerticalGroup buttons = new VerticalGroup();
        Label loadGame = new Label("Load Game", game.resource.getSkin());
        TextButton load = new TextButton("Load Game", game.resource.getTextButtonStyle1());
        final TextButton exitBtn = new TextButton("Exit", game.resource.getTextButtonStyle1());
        loadGame.setFontScale(2);
        loadGame.setColor(0,0,0,1);
        buttons.addActor(loadGame);
        buttons.space(10);
        buttons.addActor(load);
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
        load.addListener(new ClickListener() {
            @Override
            public void clicked(final InputEvent event, float x, float y) {
                game.resource.getBtnClickAudio().play();
                final Dialog loadWindow = new Dialog("", game.resource.getSkin());
                VerticalGroup loadInfo = new VerticalGroup();
                Label loadGame = new Label("Enter Save Name to Load: ", game.resource.getSkin());
                final TextField loadName = new TextField("", game.resource.getSkin());
                HorizontalGroup loadButtons = new HorizontalGroup();
                TextButton load = new TextButton("Load", game.resource.getTextButtonStyle2());
                TextButton cancel = new TextButton("Cancel", game.resource.getTextButtonStyle2());
                loadGame.setFontScale(1.5f);
                loadGame.setColor(0,0,0,1);
                loadInfo.addActor(loadGame);
                loadInfo.addActor(loadName);
                loadButtons.addActor(load);
                loadButtons.addActor(cancel);
                loadInfo.addActor(loadButtons);
                loadWindow.add(loadInfo);
                loadWindow.show(stage);
                loadWindow.setBackground(game.resource.getMenuPageSide());
                load.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        try {
                            game.resource.getBtnClickAudio().play();
                            ArrayList<String> metadata = new ArrayList<String>();
                            FileInputStream loadFile = new FileInputStream(String.format("Saves/%s.txt", loadName.getText()));
                            Scanner sc = new Scanner(loadFile);
                            while (sc.hasNextLine()) {
                                metadata.add(sc.nextLine());
                            }
                            sc.close();
                            game.parseMetaData(metadata);
                            System.out.println(game.metadata.get(16));
                            if(Objects.equals(game.metadata.get(16), "0")) {
                                game.gameMode = 0;
                                game.setScreen(new GameScreen(game));
                            }
                            else {
                                game.gameMode = 1;
                                game.setScreen(new GameScreenComputer(game));
                            }
                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
                cancel.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        game.resource.getBtnClickAudio().play();
                        loadWindow.remove();
                    }
                });
            }
        });
        exitBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.resource.getBtnClickAudio().play();
                game.setScreen(new MainMenu(game));
            }
        });
    }
    @Override
    public void dispose() {
        super.dispose();
    }
}
