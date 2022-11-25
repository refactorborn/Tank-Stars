package com.ts.game.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.ts.game.Resources;
import com.ts.game.ts;

import java.util.ArrayList;

public class tank extends Actor {
    private Resources res = new Resources();
    private Sprite sprite;

    public Sprite getSprite() {
        return sprite;
    }

    private ArrayList<weapon> tankWeapons = new ArrayList<weapon>();
    public ArrayList<weapon> getTankWeapons() {
        return tankWeapons;
    }
    public void setTankWeapons(ArrayList<weapon> tankWeapons) {
        this.tankWeapons = tankWeapons;
    }
    public tank(Texture txt) {
        sprite = new Sprite(txt);
        sprite.setBounds(0,0,txt.getWidth(),txt.getHeight());
        setTouchable(Touchable.enabled);
    }
    public ArrayList<weapon> getWeapons(){
        return tankWeapons;
    }
    @Override
    public void setPosition(float x, float y) {
        sprite.setPosition(x,y);
        super.setPosition(x, y);
    }
    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
    }
    @Override
    public void act(float delta) {
        super.act(delta);
    }
    public void rotate() {
        sprite.flip(true, false);
    }
    public Dialog createWeaponWindow(final ts game, final TextButton changeBtn)
    {
        TextButton button;
        final Dialog window = new Dialog("", res.getSkin());
        window.setBackground(res.getMenuPageSide());
        final VerticalGroup group = new VerticalGroup();
        window.setMovable(false);
        window.setResizable(false);
        for( final weapon w : tankWeapons){
            button = new TextButton(w.getName(), res.getTextButtonStyle2());
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if(game.controls.getCurrentPlayer()==0) {
                        game.controls.setPlayer1WeaponChoice(w);
                        changeBtn.setText(w.getName());
                    }
                    else {
                        game.controls.setPlayer2WeaponChoice(w);
                        changeBtn.setText(w.getName());
                    }
                    window.remove();
                }
            });
            group.addActor(button);
            group.space(10);
        }
        TextButton exitBtn = new TextButton("Exit", res.getTextButtonStyle2());
        group.addActor(exitBtn);
        group.space(10);
        exitBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                window.remove();
            }
        });
        group.pad(50);
        window.add(group);
        return window;
    }
}