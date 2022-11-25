package com.ts.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.ts.game.ts;

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
        TextButton exitBtn = new TextButton("Exit", game.resource.getTextButtonStyle1());
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
