package com.ts.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.ts.game.GameControls;
import com.ts.game.Resources;
import com.ts.game.screens.LoadingScreen;

import java.util.ArrayList;

public class ts extends Game {
    public ArrayList<String> metadata;
    public GameControls controls;
    public SpriteBatch batch;
    public ShapeRenderer shapeRenderer;
    public BitmapFont font;
    public Resources resource;

    @Override
    public void create () {
        batch = new SpriteBatch();
        metadata = new ArrayList<String>();
        shapeRenderer = new ShapeRenderer();
        font = new BitmapFont();
        controls = new GameControls();
        resource = new Resources();
        setScreen(new LoadingScreen(this));
    }

    @Override
    public void dispose () {
        batch.dispose();
        shapeRenderer.dispose();
        font.dispose();
    }
}