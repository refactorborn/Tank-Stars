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
import java.util.Objects;

public class Tank extends Actor {
    //Variables
    private Resources res = Resources.getGameResources();
    private Sprite sprite;
    private ArrayList<Weapon> tankWeapons = new ArrayList<Weapon>();
    private ArrayList<Weapon> specialWeapns = new ArrayList<Weapon>();
    private int specialWpn = 0;

    //Getters and Setters
    public int getSpecialWpn() {
        return specialWpn;
    }
    public Sprite getSprite() {
        return sprite;
    }
    public ArrayList<Weapon> getTankWeapons() {
        return tankWeapons;
    }
    public void setTankWeapons(ArrayList<Weapon> tankWeapons) {
        this.tankWeapons = tankWeapons;
    }
    public ArrayList<Weapon> getWeapons(){
        return tankWeapons;
    }
    public Texture getWpnTexture(int i){
        return tankWeapons.get(i).getTexture();
    }

    //Instantiation
    public Tank(Texture txt) {
        sprite = new Sprite(txt);
        sprite.setBounds(0,0,txt.getWidth(),txt.getHeight());
        setTouchable(Touchable.enabled);
    }

    //Class Methods
    public Dialog createWeaponWindow(final ts game, final TextButton changeBtn) {
        TextButton button;
        final Dialog window = new Dialog("", res.getSkin());
        window.setBackground(res.getMenuPageSide());
        final VerticalGroup group = new VerticalGroup();
        window.setMovable(false);
        window.setResizable(false);
        for( final Weapon w : tankWeapons){
            button = new TextButton(w.getName(), res.getTextButtonStyle2());
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if(game.controls.getCurrentPlayer()==0) {
                        game.controls.setPlayer1WeaponChoice(w);
                        changeBtn.setText(w.getName());
                        game.metadata.set(9, String.valueOf(tankWeapons.indexOf(w)));
                    }
                    else {
                        game.controls.setPlayer2WeaponChoice(w);
                        changeBtn.setText(w.getName());
                        game.metadata.set(10, String.valueOf(tankWeapons.indexOf(w)));
                    }
                    window.remove();
                    game.controls.setWpnWindowActive(false);
                }
            });
            if(game.controls.getCurrentPlayer()==0){
                if(game.metadata.get(11).charAt(tankWeapons.indexOf(w))=='1') {
                    group.addActor(button);
                    group.space(10);
                }
            }
            else{
                if(game.metadata.get(12).charAt(tankWeapons.indexOf(w))=='1') {
                    group.addActor(button);
                    group.space(10);
                }
            }

        }
        if(game.controls.getCurrentPlayer()==0){
            if(Objects.equals(game.metadata.get(13), "1")){
                button = new TextButton(returnSpecialWeapon().getName(), res.getTextButtonStyle2());
                button.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        game.controls.setPlayer1WeaponChoice(returnSpecialWeapon());
                        game.metadata.set(9, String.valueOf(-1));
                        changeBtn.setText(returnSpecialWeapon().getName());
                        window.remove();
                        game.controls.setWpnWindowActive(false);
                    }
                });
                group.addActor(button);
                group.space(10);
            }
        }
        else{
            if(Objects.equals(game.metadata.get(14), "1")){
                button = new TextButton(returnSpecialWeapon().getName(), res.getTextButtonStyle2());
                button.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        game.controls.setPlayer2WeaponChoice(returnSpecialWeapon());
                        game.metadata.set(10, String.valueOf(-1));
                        changeBtn.setText(returnSpecialWeapon().getName());
                        window.remove();
                        game.controls.setWpnWindowActive(false);
                    }
                });
                group.addActor(button);
                group.space(10);
            }
        }
        TextButton exitBtn = new TextButton("Exit", res.getTextButtonStyle2());
        group.addActor(exitBtn);
        group.space(10);
        exitBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                window.remove();
                game.controls.setWpnWindowActive(false);
            }
        });
        group.pad(50);
        window.add(group);
        return window;
    }
    public Weapon returnSpecialWeapon(){
        return new Weapon("Nuke", 10000, 100, res.getVolley());
    }

    //Extended Methods
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
}