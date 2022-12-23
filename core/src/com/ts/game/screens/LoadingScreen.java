package com.ts.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.ts.game.Resources;
import com.ts.game.ts;

public class LoadingScreen extends ScreenAdapter {
    private Resources resources;
    private ts game;
    private Texture t1;
    private SpriteBatch batch;
    private Stage stage;
    private float time = 0;

    public LoadingScreen(final ts game) {
        this.resources = Resources.getGameResources();
        this.game = game;
        this.stage = new Stage();
        t1 = resources.getTexture();
        batch = new SpriteBatch();
    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        time += delta;
        batch.begin();
        batch.draw(t1, 0, 0);
        batch.end();
        if(time>1) {
            stage.act(delta);
            stage.draw();
        }
    }
    @Override
    public void dispose() {
        t1.dispose();
        batch.dispose();
    }
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        Dialog audioPrompt = new Dialog("", resources.getSkin());
        audioPrompt.setBackground(resources.getMenuPageSide());
        VerticalGroup info = new VerticalGroup();
        Label label = new Label("Do you wish to enable background music", resources.getSkin());
        HorizontalGroup buttons = new HorizontalGroup();
        TextButton yes = new TextButton("Yes", resources.getTextButtonStyle2());
        TextButton no = new TextButton("No", resources.getTextButtonStyle2());
        label.setFontScale(1.5f);
        label.setColor(Color.BLACK);
        buttons.addActor(yes);
        buttons.space(10);
        buttons.addActor(no);
        info.addActor(label);
        info.space(10);
        info.addActor(buttons);
        info.pad(15);
        audioPrompt.add(info);
        audioPrompt.show(stage);
        audioPrompt.setPosition((float)Gdx.graphics.getWidth()/ 2 - 200, (float)Gdx.graphics.getHeight() / 2);
        audioPrompt.setSize(info.getWidth(), info.getHeight());
        yes.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                long id = resources.getOst().play();
                resources.getOst().setLooping(id,true);
                game.setScreen(new MainMenu(game));
            }
        });
        no.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenu(game));
            }
        });
    }
}