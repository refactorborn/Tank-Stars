package com.ts.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.ts.game.ts;

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
        options.setColor(0,0,0,1);
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
                game.setScreen(new GameScreen(game));
            }
        });
        exitBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.resource.getBtnClickAudio().play();
                game.metadata = new ArrayList<String>();
                game.setScreen(new MainMenu(game));
            }
        });
    }
    @Override
    public void dispose() {
        super.dispose();
    }
}
