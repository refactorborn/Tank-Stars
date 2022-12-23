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

public class InGameOptionsScreen extends ScreenAdapter {
    private Stage stage;
    private ts game;

    public InGameOptionsScreen(ts game) {
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
        final Dialog menu = new Dialog("", game.resource.getSkin());
        VerticalGroup buttons = new VerticalGroup();
        Label options = new Label("Options", game.resource.getSkin());
        TextButton resumeBtn = new TextButton("Resume", game.resource.getTextButtonStyle1());
        TextButton saveBtn = new TextButton("Save", game.resource.getTextButtonStyle1());
        TextButton exitBtn = new TextButton("Exit", game.resource.getTextButtonStyle1());
        options.setFontScale(2);
        options.setColor(0.5f, 0.2f, 0.1f, 1);
        buttons.addActor(options);
        buttons.space(10);
        buttons.addActor(resumeBtn);
        buttons.space(10);
        buttons.addActor(saveBtn);
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
        resumeBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.resource.getBtnClickAudio().play();
                game.gameMode = 0;
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
        saveBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                final Dialog saveWindow = new Dialog("", game.resource.getSkin());
                VerticalGroup saveGroup = new VerticalGroup();
                final Label save = new Label("Enter Your Save File: ", game.resource.getSkin());
                final TextField saveField = new TextField("", game.resource.getSkin());
                HorizontalGroup buttons = new HorizontalGroup();
                TextButton confirm = new TextButton("Confirm", game.resource.getTextButtonStyle2());
                TextButton cancel = new TextButton("Cancel", game.resource.getTextButtonStyle2());
                save.setFontScale(2);
                save.setColor(0.5f, 0.2f, 0.1f, 1);
                saveGroup.addActor(save);
                saveGroup.space(10);
                buttons.pad(15);
                saveGroup.addActor(saveField);
                buttons.addActor(confirm);
                buttons.space(10);
                buttons.pad(15);
                buttons.addActor(cancel);
                saveGroup.addActor(buttons);
                saveWindow.setBackground(game.resource.getMenuPageSide());
                saveWindow.add(saveGroup);
                saveWindow.show(stage);
                saveWindow.setSize(buttons.getWidth(), buttons.getHeight()*2.2f);
                saveWindow.setPosition((float)Gdx.graphics.getWidth()/ 2 - saveWindow.getWidth() / 2, (float)Gdx.graphics.getHeight() / 2 - saveWindow.getHeight() / 2);
                confirm.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        game.resource.getBtnClickAudio().play();
                        File saveFile = new File(String.format("saves/%s.txt", saveField.getText()));
                        try {
                            FileWriter writer = new FileWriter(saveFile);
                            for (int i = 0; i < game.metadata.size(); i++) {
                                writer.write(game.metadata.get(i));
                                writer.write("\n");
                            }
                            writer.close();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        saveWindow.remove();
                    }});
                cancel.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        game.resource.getBtnClickAudio().play();
                        saveWindow.remove();
                    }
                });
                game.resource.getBtnClickAudio().play();
            }
        });
    }
    @Override
    public void dispose() {
        super.dispose();
    }
}
