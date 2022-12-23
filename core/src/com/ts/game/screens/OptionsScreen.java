package com.ts.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.ts.game.ts;

public class OptionsScreen extends ScreenAdapter {
    private Stage stage;
    private ts game;
    public OptionsScreen(ts game) {
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
        Label optionsLabel = new Label("Options", game.resource.getSkin());
        TextButton develop = new TextButton("Development", game.resource.getTextButtonStyle1());
        TextButton mutable = new TextButton("Ground ++", game.resource.getTextButtonStyle1());
        TextButton testing = new TextButton("Testing", game.resource.getTextButtonStyle1());
        TextButton exitBtn = new TextButton("Exit", game.resource.getTextButtonStyle1());
        optionsLabel.setFontScale(2);
        optionsLabel.setColor(0,0,0,1);
        buttons.addActor(optionsLabel);
        buttons.space(10);
        buttons.addActor(testing);
        buttons.space(10);
        buttons.addActor(develop);
        buttons.space(10);
        buttons.addActor(mutable);
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
        develop.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.resource.getBtnClickAudio().play();
                game.controls.setDevelopmentMode(!game.controls.isDevelopmentMode());
            }
        });
        mutable.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.resource.getBtnClickAudio().play();
                game.controls.setMutableGround(!game.controls.isMutableGround());
            }
        });
        testing.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.resource.getBtnClickAudio().play();
                game.controls.setTesting(!game.controls.isTesting());
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
