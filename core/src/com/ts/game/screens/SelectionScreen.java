package com.ts.game.screens;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

import java.awt.*;

public interface SelectionScreen {
    public void createTable1();
    public void createTable2();
    public void handleInput(Label player1Label, Label player2Label);
}
