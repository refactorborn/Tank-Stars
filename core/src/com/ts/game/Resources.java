package com.ts.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Resources {
    //Skin Variables
    private TextureAtlas atlas,atlas2,atlas3;
    private Skin skin,skin2,skin3;
    private TextButton.TextButtonStyle textButtonStyle1,textButtonStyle2;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter1,parameter2;
    private BitmapFont font1,font2;

    //Game Variables
    private Image loading,abrams_prev,frost_prev,spectre_prev;
    private Texture texture, bg, abrams, frost, spectre, healthBar, ingameMenu;
    private Texture fire, moveBtn, vs, indicate, attackBtn;
    private Texture tankSelectionBg, chooseYTank, abramsPreview, rightArrow, leftArrow;
    private Texture groundSlab, fireButton, abramsBanner, frostBanner, spectreBanner, fuel;
    private TextureRegion ground,dropBox;
    private TextureRegionDrawable textureReg, chooseTank,MenuPage, MenuPageSide, MenuPageBackground, SmallLogo;

    public TextureAtlas getAtlas() {
        return atlas;
    }

    public TextureAtlas getAtlas2() {
        return atlas2;
    }

    public TextureAtlas getAtlas3() {
        return atlas3;
    }

    public Skin getSkin() {
        return skin;
    }

    public Skin getSkin2() {
        return skin2;
    }

    public Skin getSkin3() {
        return skin3;
    }

    public TextButton.TextButtonStyle getTextButtonStyle1() {
        return textButtonStyle1;
    }

    public TextButton.TextButtonStyle getTextButtonStyle2() {
        return textButtonStyle2;
    }

    public FreeTypeFontGenerator getGenerator() {
        return generator;
    }

    public FreeTypeFontGenerator.FreeTypeFontParameter getParameter1() {
        return parameter1;
    }

    public FreeTypeFontGenerator.FreeTypeFontParameter getParameter2() {
        return parameter2;
    }

    public BitmapFont getFont1() {
        return font1;
    }

    public BitmapFont getFont2() {
        return font2;
    }

    public Image getLoading() {
        return loading;
    }

    public Image getAbrams_prev() {
        return abrams_prev;
    }

    public Image getFrost_prev() {
        return frost_prev;
    }

    public Image getSpectre_prev() {
        return spectre_prev;
    }

    public Texture getTexture() {
        return texture;
    }

    public Texture getBg() {
        return bg;
    }

    public Texture getAbrams() {
        return abrams;
    }

    public Texture getFrost() {
        return frost;
    }

    public Texture getSpectre() {
        return spectre;
    }

    public Texture getHealthBar() {
        return healthBar;
    }

    public Texture getIngameMenu() {
        return ingameMenu;
    }

    public Texture getFire() {
        return fire;
    }

    public Texture getMoveBtn() {
        return moveBtn;
    }

    public Texture getVs() {
        return vs;
    }

    public Texture getIndicate() {
        return indicate;
    }

    public Texture getAttackBtn() {
        return attackBtn;
    }

    public Texture getTankSelectionBg() {
        return tankSelectionBg;
    }

    public Texture getChooseYTank() {
        return chooseYTank;
    }

    public Texture getAbramsPreview() {
        return abramsPreview;
    }

    public Texture getRightArrow() {
        return rightArrow;
    }

    public Texture getLeftArrow() {
        return leftArrow;
    }

    public Texture getGroundSlab() {
        return groundSlab;
    }

    public Texture getFireButton() {
        return fireButton;
    }

    public Texture getAbramsBanner() {
        return abramsBanner;
    }

    public Texture getFrostBanner() {
        return frostBanner;
    }

    public Texture getSpectreBanner() {
        return spectreBanner;
    }

    public Texture getFuel() {
        return fuel;
    }

    public TextureRegion getGround() {
        return ground;
    }

    public TextureRegion getDropBox() {
        return dropBox;
    }

    public TextureRegionDrawable getTextureReg() {
        return textureReg;
    }

    public TextureRegionDrawable getChooseTank() {
        return chooseTank;
    }

    public TextureRegionDrawable getMenuPage() {
        return MenuPage;
    }

    public TextureRegionDrawable getMenuPageSide() {
        return MenuPageSide;
    }

    public TextureRegionDrawable getMenuPageBackground() {
        return MenuPageBackground;
    }

    public TextureRegionDrawable getSmallLogo() {
        return SmallLogo;
    }

    public Sound getBtnClickAudio() {
        return btnClickAudio;
    }

    public Sound getOst() {
        return ost;
    }

    //Sound Variables
    private Sound btnClickAudio, ost;

    public Resources(){
        atlas = new TextureAtlas("ts-all.atlas");
        ground = atlas.findRegion("block");
        bg = new Texture("ts-bg.png");
        abrams = new Texture("Abrams.png");
        healthBar = new Texture("healthbar2.png");
        frost = new Texture("Frost.png");
        spectre = new Texture("Spectre.png");
        ingameMenu = new Texture("Ingame-menu.png");
        fire = new Texture("Fire.png");
        moveBtn = new Texture("move-btn.png");
        vs = new Texture("vs.png");
        indicate = new Texture("indicate.png");
        attackBtn = new Texture("attack-btn.png");
        loading = new Image(new Texture("Loading.png"));
        textureReg = new TextureRegionDrawable(new TextureRegion(new Texture("Loading.png")));
        texture = new Texture("Loading.png");
        skin = new Skin(Gdx.files.internal("skin/flat-earth-ui.json"));
        chooseTank = new TextureRegionDrawable(new TextureRegion(new Texture("MenuPageBackground.jpg")));
        tankSelectionBg = new Texture("MenuPageSide.jpg");
        chooseYTank = new Texture("Choose-your-tank.png");
        abramsPreview = new Texture("abrams-prev.png");
        rightArrow = new Texture("right-arrow.png");
        leftArrow = new Texture("left-arrow.png");
        groundSlab = new Texture("ground2.png");
        MenuPage = new TextureRegionDrawable(new TextureRegion(new Texture("MenuPage.jpg")));
        MenuPageSide = new TextureRegionDrawable(new TextureRegion(new Texture("MenuPageSide2.jpg")));
        MenuPageBackground = new TextureRegionDrawable(new TextureRegion(new Texture("MenuPageBackground.jpg")));
        SmallLogo = new TextureRegionDrawable(new TextureRegion(new Texture("ResizeLogo.png")));
        fireButton = new Texture("Fire.png");
        abramsBanner = new Texture("abrams-banner.png");
        frostBanner = new Texture("frost-banner.png");
        spectreBanner = new Texture("spectre-banner.png");
        fuel = new Texture("fuel.png");
        btnClickAudio = Gdx.audio.newSound(Gdx.files.internal("click-sound.wav"));
        ost = Gdx.audio.newSound(Gdx.files.internal("tank-stars-ost.wav"));
        atlas2 = new TextureAtlas("button.atlas");
        atlas3 = new TextureAtlas("button_small.atlas");
        skin2 = new Skin(atlas2);
        skin3 = new Skin(atlas3);
        textButtonStyle1 = new TextButton.TextButtonStyle();
        textButtonStyle1.up = skin2.getDrawable("button_up");
        textButtonStyle1.down = skin2.getDrawable("button_down");
        textButtonStyle1.pressedOffsetX = 1;
        textButtonStyle1.pressedOffsetY = -1;
        textButtonStyle2 = new TextButton.TextButtonStyle();
        textButtonStyle2.up = skin3.getDrawable("button_up_small");
        textButtonStyle2.down = skin3.getDrawable("button_down_small");
        textButtonStyle2.pressedOffsetX = 1;
        textButtonStyle2.pressedOffsetY = -1;
        generator = new FreeTypeFontGenerator(Gdx.files.internal("Nunito-Regular.ttf"));
        parameter1 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter1.size = 32;
        parameter1.color = com.badlogic.gdx.graphics.Color.BROWN;
        font1 = generator.generateFont(parameter1);
        textButtonStyle1.font = font1;
        parameter2 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter2.size = 16;
        parameter2.color = com.badlogic.gdx.graphics.Color.BROWN;
        font2 = generator.generateFont(parameter2);
        textButtonStyle2.font = font2;
        abrams_prev = new Image(new Texture("abrams-prev.png"));
        frost_prev = new Image(new Texture("frost-prev.png"));
        spectre_prev = new Image(new Texture("spectre-prev.png"));
        dropBox = new TextureRegion(new Texture("Dropbox.png"));
    }

}
