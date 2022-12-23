package com.ts.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ts.game.ts;

public class MainMenu extends ScreenAdapter {
    private Stage stage;
    private Table table1;
    private Table table2;

    public MainMenu(final ts game) {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        table1 = new Table();
        table1.setSize((float)Gdx.graphics.getWidth() * 2/3, Gdx.graphics.getHeight());
        table1.setBackground(game.resource.getMenuPageBackground());
        table1.add(new Image(new Texture("ResizeLogo.png"))).align(Align.center).padBottom(50).row();
        table1.add(new Image(new Texture("abrams-prev.png"))).align(Align.center).padBottom(155).row();
        stage.addActor(table1);
        table2 = new Table();
        table2.setSize((float)Gdx.graphics.getWidth() * 1/3, Gdx.graphics.getHeight());
        table2.setPosition((float)Gdx.graphics.getWidth() * 2/3, 0);
        table2.setBackground(game.resource.getMenuPageSide());
        TextButton button1 = new TextButton("Player vs Player", game.resource.getTextButtonStyle1());
        button1.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.resource.getBtnClickAudio().play();
                game.setScreen(new TankSelectionScreen(game));
                return true;
            }
        });
        table2.add(button1).pad(12).row();
        TextButton button1a = new TextButton("Player vs Comp", game.resource.getTextButtonStyle1());
        button1a.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.resource.getBtnClickAudio().play();
                game.setScreen(new TankSelectionScreenComputer(game));
                return true;
            }
        });
        table2.add(button1a).pad(12).row();
        TextButton button2 = new TextButton("Load Game", game.resource.getTextButtonStyle1());
        button2.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new LoadGame(game));
                game.resource.getBtnClickAudio().play();
                return true;
            }
        });
        table2.add(button2).pad(12).align(Align.center).row();
        TextButton button3 = new TextButton("Options", game.resource.getTextButtonStyle1());
        button3.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new OptionsScreen(game));
                game.resource.getBtnClickAudio().play();
                return true;
            }
        });
        table2.add(button3).pad(12).align(Align.center).row();
        TextButton button4 = new TextButton("Exit Game", game.resource.getTextButtonStyle1());
        button4.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new ExitPopupScreen(game));
                game.resource.getBtnClickAudio().play();
                return true;
            }
        });
        table2.add(button4).pad(12).align(Align.center).row();
        stage.addActor(table2);
    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }
    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }
    @Override
    public void hide() {
        super.hide();
    }
    @Override
    public void pause() {
        super.pause();
    }
    @Override
    public void resume() {
        super.resume();
    }
    @Override
    public void dispose() {
    }
    @Override
    public void show() {
        super.show();
    }
}

