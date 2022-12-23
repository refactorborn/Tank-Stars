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

import java.util.ArrayList;

public class Resources {
    private static Resources finalRes = null;
    //Skin Variables
    private TextureAtlas atlas,atlas2,atlas3;
    private Skin skin,skin2,skin3;
    private TextButton.TextButtonStyle textButtonStyle1,textButtonStyle2;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter1,parameter2;
    private BitmapFont font1,font2;

    //Game Variables
    private Texture texture, bg, abrams, frost, spectre, healthBar, ingameMenu,volley, verticalSlam, glider;
    private Texture fire, moveBtn, vs, indicate, attackBtn, crosshair;
    private Texture bullet1, bullet2, bullet3, bullet4, bullet5, bullet6, bullet7, bullet8, bullet9 , bullet10, nuke;
    private Texture tankSelectionBg, chooseYTank, abramsPreview, rightArrow, leftArrow;
    private Texture groundSlab, fireButton, abramsBanner, frostBanner, spectreBanner, fuel, fuel_100, fuel_90, fuel_80, fuel_70, fuel_60, fuel_50, fuel_40, fuel_30, fuel_20, fuel_10, fuel_0;
    private Texture health0, health1, health2, health3, health4, health5, health6, health7, health8, health9, health10;
    private TextureRegion ground,dropBox;
    private TextureRegionDrawable textureReg, chooseTank,MenuPage, MenuPageSide, MenuPageBackground, SmallLogo;
    private ArrayList<Texture> shotAnimation = new ArrayList<Texture>();
    private Image loading,abrams_prev,frost_prev,spectre_prev;
    //Sound Variables
    private Sound btnClickAudio, ost, tankFire;

    //Getters
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
    public Texture getFuel_100() {
        return fuel_100;
    }
    public Texture getFuel_90() {
        return fuel_90;
    }
    public Texture getFuel_80() {
        return fuel_80;
    }
    public Texture getFuel_70() {
        return fuel_70;
    }
    public Texture getFuel_60() {
        return fuel_60;
    }
    public Texture getFuel_50() {
        return fuel_50;
    }
    public Texture getFuel_40() {
        return fuel_40;
    }
    public Texture getFuel_30() {
        return fuel_30;
    }
    public Texture getFuel_20() {
        return fuel_20;
    }
    public Texture getFuel_10() {
        return fuel_10;
    }
    public Texture getFuel_0() {
        return fuel_0;
    }
    public Texture getVolley() {
        return volley;
    }
    public Texture getVerticalSlam() {
        return verticalSlam;
    }
    public Texture getCrosshair() {
        return crosshair;
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
    public BitmapFont getFont1() {
        return font1;
    }
    public BitmapFont getFont2() {
        return font2;
    }
    public FreeTypeFontGenerator getGenerator() {
        return generator;
    }
    public TextButton.TextButtonStyle getTextButtonStyle1() {
        return textButtonStyle1;
    }
    public TextButton.TextButtonStyle getTextButtonStyle2() {
        return textButtonStyle2;
    }
    public FreeTypeFontGenerator.FreeTypeFontParameter getParameter1() {
        return parameter1;
    }
    public FreeTypeFontGenerator.FreeTypeFontParameter getParameter2() {
        return parameter2;
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
    public ArrayList<Texture> getShotAnimation() {
        return shotAnimation;
    }
    public Sound getBtnClickAudio() {
        return btnClickAudio;
    }
    public Sound getOst() {
        return ost;
    }
    public Sound getTankFire() {
        return tankFire;
    }
    public Texture getHealth0() {
        return health0;
    }
    public Texture getHealth1() {
        return health1;
    }
    public Texture getHealth2() {
        return health2;
    }
    public Texture getHealth3() {
        return health3;
    }
    public Texture getHealth4() {
        return health4;
    }
    public Texture getHealth5() {
        return health5;
    }
    public Texture getHealth6() {
        return health6;
    }
    public Texture getHealth7() {
        return health7;
    }
    public Texture getHealth8() {
        return health8;
    }
    public Texture getHealth9() {
        return health9;
    }
    public Texture getHealth10() {
        return health10;
    }
    public Texture getGlider() {
        return glider;
    }
    public Texture getBullet1() {
        return bullet1;
    }
    public Texture getBullet2() {
        return bullet2;
    }
    public Texture getBullet3() {
        return bullet3;
    }
    public Texture getBullet4() {
        return bullet4;
    }
    public Texture getBullet5() {
        return bullet5;
    }
    public Texture getBullet6() {
        return bullet6;
    }
    public Texture getBullet7() {
        return bullet7;
    }
    public Texture getBullet8() {
        return bullet8;
    }
    public Texture getBullet9() {
        return bullet9;
    }
    public Texture getBullet10() {
        return bullet10;
    }
    public Texture getNuke() {
        return nuke;
    }

    private Resources(){
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
        fuel_100 = new Texture("./FuelBar/fuel10.png");
        fuel_90 = new Texture("./FuelBar/fuel9.png");
        fuel_80 = new Texture("./FuelBar/fuel8.png");
        fuel_70 = new Texture("./FuelBar/fuel7.png");
        fuel_60 = new Texture("./FuelBar/fuel6.png");
        fuel_50 = new Texture("./FuelBar/fuel5.png");
        fuel_40 = new Texture("./FuelBar/fuel4.png");
        fuel_30 = new Texture("./FuelBar/fuel3.png");
        fuel_20 = new Texture("./FuelBar/fuel2.png");
        fuel_10 = new Texture("./FuelBar/fuel1.png");
        fuel_0 = new Texture("./FuelBar/fuel0.png");
        btnClickAudio = Gdx.audio.newSound(Gdx.files.internal("click-sound.wav"));
        ost = Gdx.audio.newSound(Gdx.files.internal("tank-stars-ost.wav"));
        atlas2 = new TextureAtlas("button.atlas");
        atlas3 = new TextureAtlas("button_small.atlas");
        skin2 = new Skin(atlas2);
        skin3 = new Skin(atlas3);
        bullet1 = new Texture("Bullet-1.png");
        bullet2 = new Texture("Bullet-2.png");
        bullet3 = new Texture("Bullet-3.png");
        bullet4 = new Texture("Bullet-4.png");
        bullet5 = new Texture("Bullet-5.png");
        bullet6 = new Texture("Bullet-6.png");
        bullet7 = new Texture("Bullet-7.png");
        bullet8 = new Texture("Bullet-8.png");
        bullet9 = new Texture("Bullet-9.png");
        bullet10 = new Texture("Bullet-10.png");
        nuke = new Texture("Nuke.png");
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
        tankFire = Gdx.audio.newSound(Gdx.files.internal("tank-shoot.wav"));
        volley = new Texture("volley.png");
        verticalSlam = new Texture("verticalSlam.png");
        shotAnimation.add(new Texture("FX_Coil_0.png"));
        shotAnimation.add(new Texture("FX_Coil_1.png"));
        shotAnimation.add(new Texture("FX_Coil_2.png"));
        crosshair = new Texture("crosshair.png");
        health0 = new Texture("./HealthBar/health-0.png");
        health1 = new Texture("./HealthBar/health-10.png");
        health2 = new Texture("./HealthBar/health-20.png");
        health3 = new Texture("./HealthBar/health-30.png");
        health4 = new Texture("./HealthBar/health-40.png");
        health5 = new Texture("./HealthBar/health-50.png");
        health6 = new Texture("./HealthBar/health-60.png");
        health7 = new Texture("./HealthBar/health-70.png");
        health8 = new Texture("./HealthBar/health-80.png");
        health9 = new Texture("./HealthBar/health-90.png");
        health10 = new Texture("./HealthBar/health-100.png");
        glider = new Texture("glider.png");
    }
    public static Resources getGameResources(){
        if(finalRes==null){
            finalRes = new Resources();
        }
        return finalRes;
    }
}
