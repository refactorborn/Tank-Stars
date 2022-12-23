package com.ts.game.screens;

public interface Gameplay {
    public void createFuelImages();
    public void createHealthImages();
    public void handleShooting();
    public boolean handleMovement();
    public void handleHealth();
}
