package com.ts.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ts.game.Resources;
import com.ts.game.ts;

public class LoadingScreen extends ScreenAdapter {
    private Resources resources;
    private float deltaTime = 0;
    private float timeToWait = 3;

    private ts game;
    private Texture t1;
    private SpriteBatch batch;

    public LoadingScreen(ts game) {
        this.resources = new Resources();
        this.game = game;
        t1 = resources.getTexture();
        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        deltaTime += delta;
        if (deltaTime > timeToWait) {
            game.setScreen(new MainMenu(game));
            System.out.println(game.controls.isBgMusicOn());
            if(!game.controls.isBgMusicOn()){
                long id = resources.getOst().play();
                resources.getOst().setLooping(id,true);
                game.controls.setBgAudio(true);
            }
        }
        // draw the loading screen
        else{
            batch.begin();
            batch.draw(t1, 0, 0);
            batch.end();
        }
    }

    @Override
    public void dispose() {
        t1.dispose();
        batch.dispose();
    }
}