package com.ts.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ts.game.ts;

import java.util.ArrayList;

public class TankSelectionScreen extends ScreenAdapter implements SelectionScreen {
    private Stage stage;
    private Table table1;
    private Table table2;
    private ts game;
    private int current = 0;
    private ArrayList<Image> previewImages = new ArrayList<Image>();
    private ArrayList<Image> tankBanners = new ArrayList<Image>();
    private ArrayList<String> tankNames = new ArrayList<String>();
    private boolean player1Check = false, player2Check = false;
    public TankSelectionScreen(final ts game) {
        //Initiating
        this.game = game;
        stage = new Stage(new ScreenViewport());
        previewImages.add(game.resource.getAbrams_prev());
        previewImages.add(game.resource.getFrost_prev());
        previewImages.add(game.resource.getSpectre_prev());
        tankBanners.add(new Image(game.resource.getAbramsBanner()));
        tankBanners.add(new Image(game.resource.getFrostBanner()));
        tankBanners.add(new Image(game.resource.getSpectreBanner()));
        tankNames.add("Abrams");
        tankNames.add("Frost");
        tankNames.add("Spectre");

        //Creating Tables
        createTable1();//Table1
        createTable2();// Table2

        //Finalizing
        stage.addActor(table1);
        stage.addActor(table2);
        Gdx.input.setInputProcessor(stage);
    }
    @Override
    public void createTable1(){
        table1 = new Table();
        table1 = new Table();
        table1.setSize((float) (Gdx.graphics.getWidth()*0.67), Gdx.graphics.getHeight());
        table1.setBackground(game.resource.getChooseTank());
        table1.add(tankBanners.get(current)).width(previewImages.get(current).getWidth()-150).height(100).align(Align.center);
        table1.row();
        table1.add(previewImages.get(current)).width(previewImages.get(current).getWidth()).height(previewImages.get(current).getHeight()).align(Align.center);
        table1.padBottom(75);
    }
    @Override
    public void createTable2(){
        //Table Instatiation
        table2 = new Table();
        table2.setSize((float) (Gdx.graphics.getWidth()*0.33), Gdx.graphics.getHeight());
        table2.setBackground(new TextureRegionDrawable(game.resource.getTankSelectionBg()));
        table2.setPosition((float) (Gdx.graphics.getWidth()*0.67), 0);

        //Table Items
        VerticalGroup btnGroup = new VerticalGroup();
        HorizontalGroup selectionGroup = new HorizontalGroup();
        HorizontalGroup playerLabels = new HorizontalGroup();
        HorizontalGroup playerSelections = new HorizontalGroup();
        Image chooseYTank = new Image(game.resource.getChooseYTank());
        final TextButton slctBtn = new TextButton("Select Tank", game.resource.getTextButtonStyle1());
        final TextButton backBtn = new TextButton("Back", game.resource.getTextButtonStyle1());
        final ImageButton rightBtn = new ImageButton(new TextureRegionDrawable(game.resource.getRightArrow()));
        final ImageButton leftBtn = new ImageButton(new TextureRegionDrawable(game.resource.getLeftArrow()));
        final Label player1 = new Label("Player 1", game.resource.getSkin());
        final Label player2 = new Label("Player 2", game.resource.getSkin());
        player1.setFontScale(2);
        player2.setFontScale(2);
        player1.setColor(0,0,0,1);
        player2.setColor(0,0,0,1);

        //Table Handle Events
        rightBtn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.resource.getBtnClickAudio().play();
                if(current < previewImages.size()-1){
                    current++;
                    table1.clear();
                    table1.add(tankBanners.get(current)).width(previewImages.get(current).getWidth()-150).height(120).align(Align.center);
                    table1.row();
                    table1.add(previewImages.get(current)).width(previewImages.get(current).getWidth()).height(previewImages.get(current).getHeight()).align(Align.center);
                }
                return true;
            }
        });
        leftBtn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.resource.getBtnClickAudio().play();
                if(current > 0){
                    current--;
                    table1.clear();
                    table1.add(tankBanners.get(current)).width(previewImages.get(current).getWidth()-150).height(120).align(Align.center);
                    table1.row();
                    table1.add(previewImages.get(current)).width(previewImages.get(current).getWidth()).height(previewImages.get(current).getHeight()).align(Align.center);
                }
                return true;
            }
        });
        slctBtn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.resource.getBtnClickAudio().play();
                if(!player1Check || !player2Check) {
                    handleInput(player1, player2);
                    if(player1Check && player2Check){
                        slctBtn.setText("Start Game");
                        return true;
                    }
                }
                if(player1Check && player2Check){
                    game.gameMode= 0;
                    game.setScreen(new GameScreen(game));
                }
                backBtn.setText("Cancel Selection");
                return true;
            }
        });
        backBtn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.resource.getBtnClickAudio().play();
                if(!player1Check && !player2Check) game.setScreen(new MainMenu(game));
                else if(player2Check) {
                    player2Check = false;
                    slctBtn.setText("Select Tank");
                    player2.setText("Player 2");
                    player2.setColor(0,0,0,1);
                    player2.setFontScale(2);
                }
                else if(player1Check) {
                    player1Check = false;
                    slctBtn.setText("Select Tank");
                    player1.setText("Player 1");
                    player1.setColor(0,0,0,1);
                    player1.setFontScale(2);
                    backBtn.setText("Back");
                }
                return true;
            }
        });

        //Table Add Items
        table2.add(chooseYTank).width(chooseYTank.getWidth()).height(chooseYTank.getHeight()).align(Align.center);
        table2.row();
        rightBtn.padTop(5);
        selectionGroup.addActor(leftBtn);
        selectionGroup.addActor(rightBtn);
        table2.add(selectionGroup).align(Align.center);
        table2.row();
        playerLabels.addActor(player1);
        playerLabels.space(50);
        playerLabels.addActor(player2);
        playerLabels.padTop(15);
        playerLabels.padBottom(15);
        table2.add(playerLabels).align(Align.center);
        table2.row();
        btnGroup.addActor(slctBtn);
        btnGroup.space(20);
        btnGroup.addActor(backBtn);
        table2.add(btnGroup);
    }
    @Override
    public void handleInput(Label player1Label, Label player2Label){
        if(!this.player1Check) {
            this.player1Check = true;
            player1Label.setText(tankNames.get(current));
            game.metadata.set(0,String.valueOf(current));
        }
        else if(!this.player2Check){
            this.player2Check = true;
            player2Label.setText(tankNames.get(current));
            game.metadata.set(1,String.valueOf(current));
        }
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
