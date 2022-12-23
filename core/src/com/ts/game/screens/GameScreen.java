package com.ts.game.screens;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ts.game.*;

import java.util.ArrayList;
import java.util.Objects;
import com.ts.game.components.*; // for dhvanil
import com.ts.game.components.Tank;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

//import com.ts.game.components.Tank;
//import com.ts.game.components.Abrams;
//import com.ts.game.components.Frost;
//import com.ts.game.components.Spectre;
//import com.ts.game.components.Weapon; //for hanoon
class MutilationError extends Throwable {
    public MutilationError() {
        super("Error: Mutilation Not Possible Beyond This Point");
    }
}
class TankFactory{
    public static Tank createTank(int tankIndex) {
        switch (tankIndex) {
            case 0:
                return new Abrams();
            case 1:
                return new Frost();
            case 2:
                return new Spectre();
            default:
                return null;
        }
    }
}

class Testing{
    public static int runTest(double toCheck, double expected){
        UnitTesting.setExpected(expected);
        UnitTesting.setToCheck(toCheck);
        Result result = JUnitCore.runClasses(UnitTesting.class);
        return result.getFailureCount();
    }
}
public class GameScreen extends ScreenAdapter implements Gameplay{
    //Scene2D Variables
    private Stage stage;
    private Table mainTable;
    private Table headerTable;
    private Table footerTable;
    private Tank playerTank;
    private Tank enemyTank;
    private Label powerOfShot;
    private Label angleOfShot;
    private Label testResult;
    private Label player1Tag1;
    private Label player1Tag2;
    private Label player2Tag1;
    private Label player2Tag2;
    private Dialog wpnWindow;
    private ts game;
    private Image cross, player1Indicator, player2Indicator;
    private TextButton fire;
    private Image fuel0a, fuel0b, fuel1a, fuel1b, fuel2a, fuel2b, fuel3a, fuel3b, fuel4a, fuel4b, fuel5a, fuel5b, fuel6a, fuel6b, fuel7a, fuel7b, fuel8a, fuel8b, fuel9a, fuel9b, fuel10a, fuel10b;
    private Image health0a, health0b, health1a, health1b, health2a, health2b, health3a, health3b, health4a, health4b, health5a, health5b, health6a, health6b, health7a, health7b, health8a, health8b, health9a, health9b, health10a, health10b;
    //Resources
    private Resources res;
    //Collision Detection
    private CollisionDetection collisionDetection;

    //Box2D Variables
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private ShapeRenderer shapeRenderer;
    private OrthographicCamera camera;
    private Vector2 movement = new Vector2();
    private Body playerTankBody;
    private Body enemyTankBody;
    private Body DropBox;
    private Body glider;
    private Body GroundBody;
    private Body bulletBody;
    private Sprite DropBoxSprite;
    private Sprite playerTankSprite;
    private Sprite enemyTankSprite;
    private Sprite tempWeaponSprite;
    private Sprite gliderSprite;
    private ChainShape groundShape;
    private ArrayList<Vector2> groundCoordinates = new ArrayList<Vector2>();

    private final int speed = 40000;
    private int forceX = 100;
    private int forceY = 0;
    private boolean drawGlider = false;


    public GameScreen(final ts game){
        //Overlaying Instantiations
        game.controls.resetGameControl();
        res = Resources.getGameResources();
        this.game = game;
        stage = new Stage(new ScreenViewport());
        mainTable = new Table();
        shapeRenderer = new ShapeRenderer();
        game.controls.initiateFuelImages();
        game.controls.initiateHealthImages();

        mainTable.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        mainTable.setBackground(new TextureRegionDrawable(res.getBg()));
        mainTable.setPosition(0, 0);
        stage.addActor(mainTable);
        playerTankSprite = getTank(Integer.parseInt(game.metadata.get(0))).getSprite();
        enemyTankSprite = getTank(Integer.parseInt(game.metadata.get(1))).getSprite();
        enemyTankSprite.flip(true, false);
        DropBoxSprite = new Sprite(res.getDropBox());
        gliderSprite = new Sprite(res.getGlider());

        Image groundSlab = new Image(res.getGroundSlab());
        groundSlab.setPosition(0, 0);
        stage.addActor(groundSlab);
        scene2dCreateHeader();
        scene2dCreateGround();
        scene2dCreateGame();
        stage.addActor(headerTable);
        stage.addActor(footerTable);
        game.controls.initiatePlayers(playerTank, enemyTank);
        player1Indicator = new Image(res.getIndicate());
        player2Indicator = new Image(res.getIndicate());
        stage.addActor(player1Indicator);
        stage.addActor(player2Indicator);

        //Box2D Instantiations
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(new InputController(){
            @Override
            public boolean keyDown(int keyCode) {
                if(!game.controls.isShotActive()) {
                    if (keyCode == Input.Keys.ESCAPE) {
                        game.setScreen(new InGameOptionsScreen(game));
                    } else if (keyCode == Input.Keys.D || keyCode == Input.Keys.RIGHT) {
                        if (handleMovement()) {
                            if(game.controls.getCurrentPlayer()==0) playerTankBody.applyLinearImpulse(speed, 0, playerTankBody.getWorldCenter().x, playerTankBody.getWorldCenter().y, true);
                            else enemyTankBody.applyLinearImpulse(speed, 0, enemyTankBody.getWorldCenter().x, enemyTankBody.getWorldCenter().y, true);
                            if(game.controls.getCurrentPlayer()==0 && game.controls.getPlayerTankDirection()==1) {
                                playerTankSprite.flip(true, false);
                                game.controls.setPlayerTankDirection(0);
                            }
                            else if(game.controls.getCurrentPlayer()==1 && game.controls.getPlayer2TankDirection()==1) {
                                enemyTankSprite.flip(true, false);
                                game.controls.setPlayer2TankDirection(0);
                            }
                        }
                    } else if (keyCode == Input.Keys.A || keyCode == Input.Keys.LEFT) {
                        if (handleMovement()) {
                            if(game.controls.getCurrentPlayer()==0) playerTankBody.applyLinearImpulse(-speed, 0, playerTankBody.getWorldCenter().x, playerTankBody.getWorldCenter().y, true);
                            else enemyTankBody.applyLinearImpulse(-speed, 0, enemyTankBody.getWorldCenter().x, enemyTankBody.getWorldCenter().y, true);
                            if(game.controls.getCurrentPlayer()==0 && game.controls.getPlayerTankDirection()==0) {
                                playerTankSprite.flip(true, false);
                                game.controls.setPlayerTankDirection(1);
                            }
                            else if(game.controls.getCurrentPlayer()==1 && game.controls.getPlayer2TankDirection()==0) {
                                enemyTankSprite.flip(true, false);
                                game.controls.setPlayer2TankDirection(1);
                            }
                        }
                    }
                }
                return true;
            }
            @Override
            public boolean keyUp(int keyCode) {
                if(keyCode==Input.Keys.D || keyCode==Input.Keys.A || keyCode==Input.Keys.LEFT || keyCode==Input.Keys.RIGHT){
                    movement.x = 0;
                }
                return true;
            }
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                int Xoffset = -640;
                int Yoffset = -360;
                if(game.controls.getCurrentPlayer()==0){
                    if(screenX>=playerTankBody.getPosition().x-100-Xoffset && screenX<=playerTankBody.getPosition().x+100-Xoffset && (720 - screenY)>=playerTankBody.getPosition().y-100-Yoffset && (720-screenY)<=playerTankBody.getPosition().y+100-Yoffset){
                        setCross(screenX, screenY);
                        forceX = (int) (screenX - playerTankBody.getPosition().x + Xoffset);
                        forceY = (int) (720- screenY - playerTankBody.getPosition().y + Yoffset);
                        System.out.println("X: " + forceX + " Y: " + forceY);
                    }
                }
                else{
                    if(screenX>=enemyTankBody.getPosition().x-100-Xoffset && screenX<=enemyTankBody.getPosition().x+100-Xoffset && (720-screenY)>=enemyTankBody.getPosition().y-100-Yoffset && (720-screenY)<=enemyTankBody.getPosition().y+100-Yoffset){
                        forceX = -(int) (screenX - enemyTankBody.getPosition().x + Xoffset);
                        forceY = (int) (720 - screenY - enemyTankBody.getPosition().y + Yoffset);
                        setCross(screenX, screenY);
                    }
                }
                return true;
            }
        });
        Gdx.input.setInputProcessor(inputMultiplexer);
        world = new World(new Vector2(0, -9.8f), true);
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        debugRenderer = new Box2DDebugRenderer();
        box2dCreateWorld();
        box2dCreateTank(0);
        box2dCreateTank(1);
        cross = new Image(res.getCrosshair());
        cross.setSize(25, 25);
        cross.setPosition(0, 0);
        stage.addActor(cross);
        cross.setVisible(false);
        //Collision Detection
        collisionDetection = new CollisionDetection(game);
        world.setContactListener(collisionDetection);
        if(Objects.equals(game.metadata.get(15), "0")) game.controls.setDropBoxPresent(false);
        else {
            game.controls.setDropBoxPresent(false);
            box2dCreateDropBox(true);
            game.controls.setDropBoxPresent(true);
        }
        if(Objects.equals(game.metadata.get(11), "0")) {
            StringBuilder player1WeaponInfo = new StringBuilder();
            for (Weapon w : playerTank.getTankWeapons()) {
                player1WeaponInfo.append("1");
            }
            game.metadata.set(11, player1WeaponInfo.toString());
        }
        if(Objects.equals(game.metadata.get(12), "0")) {
            StringBuilder player2WeaponInfo = new StringBuilder();
            for (Weapon w : enemyTank.getTankWeapons()) {
                player2WeaponInfo.append("1");
            }
            game.metadata.set(12, player2WeaponInfo.toString());
        }

        player1Tag1 = new Label("Player 1", res.getSkin());
        player1Tag1.setPosition(300, 600);
        player1Tag1.setFontScale(1.5f);
        player1Tag1.setColor(Color.BLACK);
        stage.addActor(player1Tag1);
        player2Tag1 = new Label("Player 2", res.getSkin());
        player2Tag1.setPosition(950, 600);
        player2Tag1.setFontScale(1.5f);
        player2Tag1.setColor(Color.BLACK);
        stage.addActor(player2Tag1);
        player1Tag2 = new Label("Player 1", res.getSkin());
        player1Tag2.setPosition(180, 120);
        player1Tag2.setFontScale(1.5f);
        player1Tag2.setColor(Color.BLACK);
        stage.addActor(player1Tag2);
        player2Tag2 = new Label("Player 2", res.getSkin());
        player2Tag2.setPosition(980, 120);
        player2Tag2.setFontScale(1.5f);
        player2Tag2.setColor(Color.BLACK);
        stage.addActor(player2Tag2);

        //HUD Labels
        testResult = new Label("", res.getSkin());
        testResult.setPosition(600, 500);
        testResult.setFontScale(1.5f);
        testResult.setColor(Color.GREEN);
        stage.addActor(testResult);
    }
    public Tank getTank(int tankIndex){
        return TankFactory.createTank(tankIndex);
    }
    public void resetImages(int player){
        if(player==0){
            fuel10a.setVisible(true);
            fuel9a.setVisible(false);
            fuel8a.setVisible(false);
            fuel7a.setVisible(false);
            fuel6a.setVisible(false);
            fuel5a.setVisible(false);
            fuel4a.setVisible(false);
            fuel3a.setVisible(false);
            fuel2a.setVisible(false);
            fuel1a.setVisible(false);
            fuel0a.setVisible(false);
        }
        else{
            fuel10b.setVisible(true);
            fuel9b.setVisible(false);
            fuel8b.setVisible(false);
            fuel7b.setVisible(false);
            fuel6b.setVisible(false);
            fuel5b.setVisible(false);
            fuel4b.setVisible(false);
            fuel3b.setVisible(false);
            fuel2b.setVisible(false);
            fuel1b.setVisible(false);
            fuel0b.setVisible(false);
        }
    }
    public void createFuelImages(){
        fuel10a = game.controls.getFuelImageAtIndex(0);
        fuel10b = game.controls.getFuelImageAtIndex(1);
        fuel9a = game.controls.getFuelImageAtIndex(2);
        fuel9b = game.controls.getFuelImageAtIndex(3);
        fuel8a = game.controls.getFuelImageAtIndex(4);
        fuel8b = game.controls.getFuelImageAtIndex(5);
        fuel7a = game.controls.getFuelImageAtIndex(6);
        fuel7b = game.controls.getFuelImageAtIndex(7);
        fuel6a = game.controls.getFuelImageAtIndex(8);
        fuel6b = game.controls.getFuelImageAtIndex(9);
        fuel5a = game.controls.getFuelImageAtIndex(10);
        fuel5b = game.controls.getFuelImageAtIndex(11);
        fuel4a = game.controls.getFuelImageAtIndex(12);
        fuel4b = game.controls.getFuelImageAtIndex(13);
        fuel3a = game.controls.getFuelImageAtIndex(14);
        fuel3b = game.controls.getFuelImageAtIndex(15);
        fuel2a = game.controls.getFuelImageAtIndex(16);
        fuel2b = game.controls.getFuelImageAtIndex(17);
        fuel1a = game.controls.getFuelImageAtIndex(18);
        fuel1b = game.controls.getFuelImageAtIndex(19);
        fuel0a = game.controls.getFuelImageAtIndex(20);
        fuel0b = game.controls.getFuelImageAtIndex(21);
        fuel0a.setPosition(100, 50);
        fuel1a.setPosition(100,50);
        fuel2a.setPosition(100,50);
        fuel3a.setPosition(100,50);
        fuel4a.setPosition(100,50);
        fuel5a.setPosition(100,50);
        fuel6a.setPosition(100,50);
        fuel7a.setPosition(100,50);
        fuel8a.setPosition(100,50);
        fuel9a.setPosition(100,50);
        fuel10a.setPosition(100,50);
        fuel0b.setPosition(100,50);
        fuel1b.setPosition(900,50);
        fuel2b.setPosition(900,50);
        fuel3b.setPosition(900,50);
        fuel4b.setPosition(900,50);
        fuel5b.setPosition(900,50);
        fuel6b.setPosition(900,50);
        fuel7b.setPosition(900,50);
        fuel8b.setPosition(900,50);
        fuel9b.setPosition(900,50);
        fuel10b.setPosition(900,50);
        stage.addActor(fuel0a);
        stage.addActor(fuel0b);
        stage.addActor(fuel1a);
        stage.addActor(fuel2a);
        stage.addActor(fuel3a);
        stage.addActor(fuel4a);
        stage.addActor(fuel5a);
        stage.addActor(fuel1b);
        stage.addActor(fuel2b);
        stage.addActor(fuel3b);
        stage.addActor(fuel4b);
        stage.addActor(fuel5b);
        stage.addActor(fuel6a);
        stage.addActor(fuel6b);
        stage.addActor(fuel7a);
        stage.addActor(fuel7b);
        stage.addActor(fuel8a);
        stage.addActor(fuel8b);
        stage.addActor(fuel9a);
        stage.addActor(fuel9b);
        stage.addActor(fuel10a);
        stage.addActor(fuel10b);
        resetImages(0);
        resetImages(1);
        System.out.println("PlayerFuel" + game.metadata.get(7));
        System.out.println("PlayerFuel" + game.metadata.get(8));
        if(!Objects.equals(game.metadata.get(7), "10")) {
            fuel10a.setVisible(false);
            game.controls.setPlayer1Fuel(Integer.parseInt(game.metadata.get(7))+1);
            handleMovement();
        }
        game.controls.setCurrentPlayer(1);
        if(!Objects.equals(game.metadata.get(8), "10")) {
            fuel10b.setVisible(false);
            game.controls.setPlayer2Fuel(Integer.parseInt(game.metadata.get(8))+1);
            handleMovement();
        }
        game.controls.setCurrentPlayer(0);
    }
    public void createHealthImages(){
        health0a = game.controls.getHealthImageAtIndex(0);
        health1a = game.controls.getHealthImageAtIndex(1);
        health2a = game.controls.getHealthImageAtIndex(2);
        health3a = game.controls.getHealthImageAtIndex(3);
        health4a = game.controls.getHealthImageAtIndex(4);
        health5a = game.controls.getHealthImageAtIndex(5);
        health6a = game.controls.getHealthImageAtIndex(6);
        health7a = game.controls.getHealthImageAtIndex(7);
        health8a = game.controls.getHealthImageAtIndex(8);
        health9a = game.controls.getHealthImageAtIndex(9);
        health10a = game.controls.getHealthImageAtIndex(10);
        health0b = game.controls.getHealthImageAtIndex(11);
        health1b = game.controls.getHealthImageAtIndex(12);
        health2b = game.controls.getHealthImageAtIndex(13);
        health3b = game.controls.getHealthImageAtIndex(14);
        health4b = game.controls.getHealthImageAtIndex(15);
        health5b = game.controls.getHealthImageAtIndex(16);
        health6b = game.controls.getHealthImageAtIndex(17);
        health7b = game.controls.getHealthImageAtIndex(18);
        health8b = game.controls.getHealthImageAtIndex(19);
        health9b = game.controls.getHealthImageAtIndex(20);
        health10b = game.controls.getHealthImageAtIndex(21);
        health0a.setSize(280,50);
        health0b.setSize(280,50);
        health1a.setSize(280,50);
        health1b.setSize(280,50);
        health2a.setSize(280,50);
        health2b.setSize(280,50);
        health3a.setSize(280,50);
        health3b.setSize(280,50);
        health4a.setSize(280,50);
        health4b.setSize(280,50);
        health5a.setSize(280,50);
        health5b.setSize(280,50);
        health6a.setSize(280,50);
        health6b.setSize(280,50);
        health7a.setSize(280,50);
        health7b.setSize(280,50);
        health8a.setSize(280,50);
        health8b.setSize(280,50);
        health9a.setSize(280,50);
        health9b.setSize(280,50);
        health10a.setSize(280,50);
        health10b.setSize(280,50);
        health0a.setPosition(200,625);
        health1a.setPosition(200,625);
        health2a.setPosition(200,625);
        health3a.setPosition(200,625);
        health4a.setPosition(200,625);
        health5a.setPosition(200,625);
        health7a.setPosition(200,625);
        health8a.setPosition(200,625);
        health9a.setPosition(200,625);
        health10a.setPosition(200,625);
        health0b.setPosition(850,625);
        health1b.setPosition(850,625);
        health2b.setPosition(850,625);
        health3b.setPosition(850,625);
        health4b.setPosition(850,625);
        health5b.setPosition(850,625);
        health6a.setPosition(850,625);
        health6b.setPosition(850,625);
        health7b.setPosition(850,625);
        health8b.setPosition(850,625);
        health9b.setPosition(850,625);
        health10b.setPosition(850,625);
        stage.addActor(health0a);
        stage.addActor(health1a);
        stage.addActor(health2a);
        stage.addActor(health3a);
        stage.addActor(health4a);
        stage.addActor(health5a);
        stage.addActor(health6a);
        stage.addActor(health7a);
        stage.addActor(health8a);
        stage.addActor(health9a);
        stage.addActor(health10a);
        stage.addActor(health0b);
        stage.addActor(health1b);
        stage.addActor(health2b);
        stage.addActor(health3b);
        stage.addActor(health4b);
        stage.addActor(health5b);
        stage.addActor(health6b);
        stage.addActor(health7b);
        stage.addActor(health8b);
        stage.addActor(health9b);
        stage.addActor(health10b);
        health0a.setVisible(false);
        health0b.setVisible(false);
        health1a.setVisible(false);
        health1b.setVisible(false);
        health2a.setVisible(false);
        health2b.setVisible(false);
        health3a.setVisible(false);
        health3b.setVisible(false);
        health4a.setVisible(false);
        health4b.setVisible(false);
        health5a.setVisible(false);
        health5b.setVisible(false);
        health6a.setVisible(false);
        health6b.setVisible(false);
        health7a.setVisible(false);
        health7b.setVisible(false);
        health8a.setVisible(false);
        health8b.setVisible(false);
        health9a.setVisible(false);
        health9b.setVisible(false);
        health10b.setVisible(false);
        health10a.setVisible(false);
        game.controls.setPlayer1Health(Integer.parseInt(game.metadata.get(2)));
        game.controls.setPlayer2Health(Integer.parseInt(game.metadata.get(3)));
        handleHealth();
    }
    public void scene2dCreateHeader(){
        headerTable = new Table();
        ImageButton pauseButton = new ImageButton(new TextureRegionDrawable(res.getIngameMenu()));
        Image vs = new Image(res.getVs());
        pauseButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new InGameOptionsScreen(game));
            }
        });
        pauseButton.setPosition(-10, 600);
        pauseButton.setSize(100, 100);
        vs.setPosition(640, 615);
        vs.setSize(60, 60);
        stage.addActor(vs);
        stage.addActor(pauseButton);
        createHealthImages();
    }
    public void scene2dCreateGround(){
        footerTable = new Table();
        footerTable.setSize(Gdx.graphics.getWidth(), (float)Gdx.graphics.getHeight()*2/10);
        footerTable.setPosition(0, 0);
        VerticalGroup footerGroup = new VerticalGroup();
        HorizontalGroup labels = new HorizontalGroup();
        angleOfShot = new Label("Angle: 0", res.getSkin());
        powerOfShot = new Label("Power: 0", res.getSkin());
        angleOfShot.setFontScale(1.5f);
        powerOfShot.setFontScale(1.5f);
        angleOfShot.setColor(Color.BLACK);
        powerOfShot.setColor(Color.BLACK);
        labels.addActor(angleOfShot);
        labels.space(50);
        labels.addActor(powerOfShot);
        HorizontalGroup buttons = new HorizontalGroup();
        fire = new TextButton("Fire", res.getTextButtonStyle2());
        String btnText = "";
        if(game.controls.getCurrentPlayer()==0) btnText = getTank(Integer.parseInt(game.metadata.get(0))).getWeapons().get(Integer.parseInt(game.metadata.get(9))).getName();
        else btnText = getTank(Integer.parseInt(game.metadata.get(1))).getWeapons().get(Integer.parseInt(game.metadata.get(10))).getName();
        final TextButton changeWpn = new TextButton(btnText, res.getTextButtonStyle2());
        buttons.addActor(fire);
        buttons.space(50);
        buttons.addActor(changeWpn);
        footerGroup.addActor(labels);
        footerGroup.space(20);
        footerGroup.addActor(buttons);
        footerTable.add(footerGroup);
        changeWpn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                wpnWindow = game.controls.getCurrentTank().createWeaponWindow(game, changeWpn);
                wpnWindow.show(stage);
                wpnWindow.setSize(200, 500);
                wpnWindow.align(Align.center);
                wpnWindow.setPosition((float)Gdx.graphics.getWidth()/2, (float)Gdx.graphics.getHeight()/2, Align.center);
                game.controls.setWpnWindowActive(true);
            }
        });
        fire.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.resource.getTankFire().play();

                // Resetting Fuel Values
                game.controls.setPlayer1Fuel(10);
                game.controls.setPlayer2Fuel(10);

                handleWeaponInfo(game.controls.getCurrentPlayer()); // Updating Metadata Information
                game.controls.setGameTurns(game.controls.getGameTurns()+1); // Updating Game Turns
                if(!game.controls.isDropBoxPresent()) if(game.controls.getGameTurns()%5==0) box2dCreateDropBox(false); // Creating Drop Box

                //Creating Weapon Sprite Image on Shoot
                if(game.controls.getCurrentPlayer()==1) {
                    tempWeaponSprite = new Sprite(getTank(Integer.parseInt(game.metadata.get(0))).getWeapons().get(Integer.parseInt(game.metadata.get(9))).getTexture());
                    resetImages(1);
                    game.controls.setCurrentPlayer(0);
                }
                else {
                    tempWeaponSprite = new Sprite(getTank(Integer.parseInt(game.metadata.get(1))).getWeapons().get(Integer.parseInt(game.metadata.get(10))).getTexture());
                    resetImages(0);
                    game.controls.setCurrentPlayer(1);
                }

                handleShooting(); // Creating Bullet

                // Updating Render Variables
                game.controls.setShotActive(true);
                cross.setVisible(false);
                if(game.controls.getCurrentPlayer()==0)changeWpn.setText(getTank(Integer.parseInt(game.metadata.get(0))).getWeapons().get(Integer.parseInt(game.metadata.get(9))).getName());
                else changeWpn.setText(getTank(Integer.parseInt(game.metadata.get(1))).getWeapons().get(Integer.parseInt(game.metadata.get(10))).getName());
            }
        });
        createFuelImages();
    }
    public void scene2dCreateGame(){
        playerTank = getTank(Integer.parseInt(game.metadata.get(0)));
        enemyTank = getTank(Integer.parseInt(game.metadata.get(1)));
        game.controls.setPlayer1WeaponChoice(playerTank.getWeapons().get(0));
        game.controls.setPlayer2WeaponChoice(enemyTank.getWeapons().get(0));
    }
    public void box2dCreateTank(int player){
        BodyDef tankDef = new BodyDef();
        tankDef.type = BodyDef.BodyType.DynamicBody;
        String[] coordinates;
        if(player==0) {
            coordinates = game.metadata.get(4).split(",");
        }
        else {
            coordinates = game.metadata.get(5).split(",");
        }
        tankDef.position.set(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]));
        PolygonShape tankShape = new PolygonShape();
        tankShape.setAsBox(20, 7);
        CircleShape tankWheel = new CircleShape();
        tankWheel.setRadius(5);
        FixtureDef tankTopFix = new FixtureDef();
        tankTopFix.shape = tankShape;
        tankTopFix.density = 8;
        tankTopFix.friction = 10;
        FixtureDef tankWheelFix = new FixtureDef();
        tankWheelFix.density = 3;
        if(player==0) {
            //Body
            playerTankBody = world.createBody(tankDef);
            playerTankBody.createFixture(tankTopFix);

            //Wheel1
            tankWheel.setPosition(new Vector2(-15, -15));
            tankWheelFix.shape = tankWheel;
            playerTankBody.createFixture(tankWheelFix);
            //Wheel2
            tankWheel.setPosition(new Vector2(15, -15));
            tankWheelFix.shape = tankWheel;
            playerTankBody.createFixture(tankWheelFix);

            //Data
            playerTankBody.setUserData("playerTank");
            playerTankBody.setFixedRotation(false);
            playerTankBody.setLinearDamping(1f);
            playerTankBody.setAngularDamping(1f);
        }
        else {
            enemyTankBody = world.createBody(tankDef);
            enemyTankBody.createFixture(tankTopFix);

            //Wheel1
            tankWheel.setPosition(new Vector2(-15, -15));
            tankWheelFix.shape = tankWheel;
            enemyTankBody.createFixture(tankWheelFix);
            //Wheel2
            tankWheel.setPosition(new Vector2(15, -15));
            tankWheelFix.shape = tankWheel;
            enemyTankBody.createFixture(tankWheelFix);

            enemyTankBody.setUserData("enemyTank");
            enemyTankBody.setFixedRotation(false);
            enemyTankBody.setLinearDamping(1f);
            enemyTankBody.setAngularDamping(1f);
        }
    }
    public void box2dCreateDropBox(boolean initial){
        if(initial){
            BodyDef dropBoxDef = new BodyDef();
            dropBoxDef.type = BodyDef.BodyType.DynamicBody;
            String[] coordinates = game.metadata.get(15).split(",");
            dropBoxDef.position.set(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]));
            PolygonShape dropBoxShape = new PolygonShape();
            dropBoxShape.setAsBox(10, 10);
            FixtureDef dropBoxFixDef = new FixtureDef();
            dropBoxFixDef.shape = dropBoxShape;
            dropBoxFixDef.density = 10;
            dropBoxFixDef.friction = 10.5f; //makes it impossible to move
            DropBox = world.createBody(dropBoxDef);
            DropBox.createFixture(dropBoxFixDef);
            DropBox.setUserData("dropBox");
        }
        else{
            if(!game.controls.isDropBoxPresent()) {
                BodyDef dropBoxDef = new BodyDef();
                dropBoxDef.type = BodyDef.BodyType.DynamicBody;
                dropBoxDef.position.set((float) Math.random() * 1000 - 500, 500);
                PolygonShape dropBoxShape = new PolygonShape();
                dropBoxShape.setAsBox(10, 10);
                FixtureDef dropBoxFixDef = new FixtureDef();
                dropBoxFixDef.shape = dropBoxShape;
                dropBoxFixDef.density = 10;
                dropBoxFixDef.friction = 10.5f; //makes it impossible to move
                DropBox = world.createBody(dropBoxDef);
                DropBox.createFixture(dropBoxFixDef);
                DropBox.setUserData("dropBox");
                game.controls.setDropBoxPresent(true);
                BodyDef gliderDef = new BodyDef();
                gliderDef.type = BodyDef.BodyType.DynamicBody;
                gliderDef.position.set(DropBox.getPosition().x, DropBox.getPosition().y+50);
                glider = world.createBody(gliderDef);
                drawGlider = true;
            }}
    }
    public void box2dCreateWorld(){
        BodyDef groundDef = new BodyDef();
        groundDef.type = BodyDef.BodyType.StaticBody;
        groundDef.position.set(0, 0);
        groundShape = new ChainShape();
        String[] coordinates = game.metadata.get(17).split(";");
        for (String s : coordinates) {
            String[] coordinate = s.split(",");
            groundCoordinates.add(new Vector2(Integer.parseInt(coordinate[0]), Integer.parseInt(coordinate[1])));
        }
        groundShape.createChain(groundCoordinates.toArray(new Vector2[0]));
        FixtureDef groundFixDef = new FixtureDef();
        groundFixDef.shape = groundShape;
        groundFixDef.density = 100;
        groundFixDef.friction = 0.2f;
        GroundBody = world.createBody(groundDef);
        GroundBody.createFixture(groundFixDef);
        GroundBody.setUserData("ground");
    }
    public void handleShooting(){
        if(game.controls.getCurrentPlayer()==0) {
            bulletBody = game.controls.getPlayer2WeaponChoice().shoot(world, enemyTankBody, game.controls.getPlayer2TankDirection(), forceX, forceY);
            game.controls.getPlayer2WeaponChoice().setShooting(false);
        }
        else {
            bulletBody = game.controls.getPlayer1WeaponChoice().shoot(world, playerTankBody, game.controls.getPlayerTankDirection(), forceX, forceY);
            game.controls.getPlayer1WeaponChoice().setShooting(false);
        }
        displayHUD("");
    }
    public void handleWeaponInfo(int player){
        boolean isEmpty = true;
        if(player==0){
            String currentWpn = game.metadata.get(9);
            if(Objects.equals(currentWpn, "-1")){
                game.metadata.set(9, "0");
                game.metadata.set(13, "0");
            }
            else {
                StringBuffer tempWpnInfo = new StringBuffer(game.metadata.get(11));
                tempWpnInfo.setCharAt(Integer.parseInt(currentWpn), '0');
                game.metadata.set(11, tempWpnInfo.toString());
            }
            for(int i=0; i<game.metadata.get(11).length(); i++){
                if(game.metadata.get(11).charAt(i)=='1') {
                    game.metadata.set(9, Integer.toString(i));
                    isEmpty = false;
                    break;
                }
            }
            if(isEmpty){
                String player1WeaponInfo = "";
                for (Weapon w : playerTank.getTankWeapons()) {
                    player1WeaponInfo += "1";
                }
                game.metadata.set(11, player1WeaponInfo);
                game.metadata.set(9, "0");
            }
        }
        else{
            String currentWpn = game.metadata.get(10);
            if(Objects.equals(currentWpn, "-1")){
                game.metadata.set(10, "0");
                game.metadata.set(14, "0");
            }
            else {
                StringBuffer tempWpnInfo = new StringBuffer(game.metadata.get(12));
                tempWpnInfo.setCharAt(Integer.parseInt(currentWpn), '0');
                game.metadata.set(12, tempWpnInfo.toString());
            }
            for(int i=0; i<game.metadata.get(12).length(); i++){
                if(game.metadata.get(12).charAt(i)=='1') {
                    game.metadata.set(10, Integer.toString(i));
                    isEmpty = false;
                    break;
                }
            }
            if(isEmpty){
                String player2WeaponInfo = "";
                for (Weapon w : enemyTank.getTankWeapons()) {
                    player2WeaponInfo += "1";
                }
                game.metadata.set(12, player2WeaponInfo);
                game.metadata.set(10, "0");
            }
        }
    }
    public boolean handleMovement(){
        if(game.controls.getCurrentPlayer()==0) {
            System.out.println("Checking Player 1");
            game.controls.setPlayer1Fuel(game.controls.getPlayer1Fuel()-1);
            if(game.controls.getPlayer1Fuel() >= 0){
                if(game.controls.getPlayer1Fuel() == 9){
                    fuel10a.setVisible(false);
                    fuel9a.setVisible(true);
                }
                else if(game.controls.getPlayer1Fuel() == 8){
                    fuel9a.setVisible(false);
                    fuel8a.setVisible(true);
                }
                else if(game.controls.getPlayer1Fuel() == 7){
                    fuel8a.setVisible(false);
                    fuel7a.setVisible(true);
                }
                else if(game.controls.getPlayer1Fuel()==6){
                    fuel7a.setVisible(false);
                    fuel6a.setVisible(true);
                }
                else if(game.controls.getPlayer1Fuel() == 5){
                    fuel6a.setVisible(false);
                    fuel5a.setVisible(true);
                }
                else if(game.controls.getPlayer1Fuel()==4){
                    fuel5a.setVisible(false);
                    fuel4a.setVisible(true);
                }
                else if(game.controls.getPlayer1Fuel()==3){
                    fuel4a.setVisible(false);
                    fuel3a.setVisible(true);
                }
                else if(game.controls.getPlayer1Fuel()==2){
                    fuel3a.setVisible(false);
                    fuel2a.setVisible(true);
                }
                else if(game.controls.getPlayer1Fuel()==1){
                    fuel2a.setVisible(false);
                    fuel1a.setVisible(true);
                }
                else if(game.controls.getPlayer1Fuel()==0){
                    fuel1a.setVisible(false);
                    fuel0a.setVisible(true);
                }
                return true;
            }
        }
        else{
            game.controls.setPlayer2Fuel(game.controls.getPlayer2Fuel()-1);
            if(game.controls.getPlayer2Fuel() >= 0){
                if(game.controls.getPlayer2Fuel() == 9){
                    fuel10b.setVisible(false);
                    fuel9b.setVisible(true);
                }
                else if(game.controls.getPlayer2Fuel() == 8){
                    fuel9b.setVisible(false);
                    fuel8b.setVisible(true);
                }
                else if(game.controls.getPlayer2Fuel() == 7){
                    fuel8b.setVisible(false);
                    fuel7b.setVisible(true);
                }
                else if(game.controls.getPlayer2Fuel()==6){
                    fuel7b.setVisible(false);
                    fuel6b.setVisible(true);
                }
                else if(game.controls.getPlayer2Fuel() == 5){
                    fuel6b.setVisible(false);
                    fuel5b.setVisible(true);
                }
                else if(game.controls.getPlayer2Fuel()==4){
                    fuel5b.setVisible(false);
                    fuel4b.setVisible(true);
                }
                else if(game.controls.getPlayer2Fuel()==3){
                    fuel4b.setVisible(false);
                    fuel3b.setVisible(true);
                }
                else if(game.controls.getPlayer2Fuel()==2){
                    fuel3b.setVisible(false);
                    fuel2b.setVisible(true);
                }
                else if(game.controls.getPlayer2Fuel()==1){
                    fuel2b.setVisible(false);
                    fuel1b.setVisible(true);
                }
                else if(game.controls.getPlayer2Fuel()==0){
                    fuel1b.setVisible(false);
                    fuel0b.setVisible(true);
                }
                return true;
            }
        }
        return false;
    }
    public void handleHealth(){
        if(game.controls.getPlayer1Health()==10){
            health10a.setVisible(true);
        }
        else if(game.controls.getPlayer1Health()==9){
            health10a.setVisible(false);
            health9a.setVisible(true);
        }
        else if(game.controls.getPlayer1Health()==8){
            health9a.setVisible(false);
            health8a.setVisible(true);
        }
        else if(game.controls.getPlayer1Health()==7){
            health8a.setVisible(false);
            health7a.setVisible(true);
        }
        else if(game.controls.getPlayer1Health()==6){
            health7a.setVisible(false);
            health6a.setVisible(true);
        }
        else if(game.controls.getPlayer1Health()==5){
            health6a.setVisible(false);
            health5a.setVisible(true);
        }
        else if(game.controls.getPlayer1Health()==4){
            health5a.setVisible(false);
            health4a.setVisible(true);
        }
        else if(game.controls.getPlayer1Health()==3){
            health4a.setVisible(false);
            health3a.setVisible(true);
        }
        else if(game.controls.getPlayer1Health()==2){
            health3a.setVisible(false);
            health2a.setVisible(true);
        }
        else if(game.controls.getPlayer1Health()==1){
            health2a.setVisible(false);
            health1a.setVisible(true);
        }
        else if(game.controls.getPlayer1Health()==0){
            health1a.setVisible(false);
            health0a.setVisible(true);
        }
        if(game.controls.getPlayer2Health()==10){
            health10b.setVisible(true);
        }
        else if(game.controls.getPlayer2Health()==9){
            health10b.setVisible(false);
            health9b.setVisible(true);
        }
        else if(game.controls.getPlayer2Health()==8){
            health9b.setVisible(false);
            health8b.setVisible(true);
        }
        else if(game.controls.getPlayer2Health()==7){
            health8b.setVisible(false);
            health7b.setVisible(true);
        }
        else if(game.controls.getPlayer2Health()==6){
            health7b.setVisible(false);
            health6b.setVisible(true);
        }
        else if(game.controls.getPlayer2Health()==5){
            health6b.setVisible(false);
            health5b.setVisible(true);
        }
        else if(game.controls.getPlayer2Health()==4){
            health5b.setVisible(false);
            health4b.setVisible(true);
        }
        else if(game.controls.getPlayer2Health()==3){
            health4b.setVisible(false);
            health3b.setVisible(true);
        }
        else if(game.controls.getPlayer2Health()==2){
            health3b.setVisible(false);
            health2b.setVisible(true);
        }
        else if(game.controls.getPlayer2Health()==1){
            health2b.setVisible(false);
            health1b.setVisible(true);
        }
        else if(game.controls.getPlayer2Health()==0){
            health1b.setVisible(false);
            health0b.setVisible(true);
        }
        game.metadata.set(2, String.valueOf(game.controls.getPlayer1Health()));
        game.metadata.set(3, String.valueOf(game.controls.getPlayer2Health()));
    }
    public void handleMutilation(int Xcoord, int YCoord) throws MutilationError {
        try{
            if(Xcoord>-600 && Xcoord <600) {
                int incline = 0;
                //Splitting groundCoordinate string
                String prevString = "";
                String afterString = "";
                String newPoints = "";
                String[] coordinates = game.metadata.get(17).split(";");
                for (String s : coordinates) {
                    String[] coordinate = s.split(",");
                    if (Integer.parseInt(coordinate[0]) > Xcoord) {
                        if(incline ==0 && Integer.parseInt(coordinate[1])>YCoord-10) incline = -1;
                        else if(incline ==0 && Integer.parseInt(coordinate[1])<YCoord-10) incline = 1;
                        else if(incline==0) incline = 2;
                        afterString += s + ";";
                    } else {
                        prevString += s + ";";
                    }
                }
                //Generate depth points
                if(incline==2) {
                    int LEFTMAX = Xcoord - 20;
                    int RIGHTMAX = Xcoord + 20;
                    int MAXDEPTH = YCoord - 10;
                    String leftTop = String.format("%d,%d", LEFTMAX, YCoord-10);//Left Top Point
                    String leftBottom = String.format("%d,%d", LEFTMAX+5, MAXDEPTH-3);//Left Bottom Point
                    String rightBottom = String.format("%d,%d", RIGHTMAX-5, MAXDEPTH-3);//Right Bottom Point
                    String rightTop = String.format("%d,%d", RIGHTMAX, YCoord-10);//Right Top Point
                    newPoints = leftTop + ";" + leftBottom + ";" + rightBottom + ";" + rightTop + ";";
                }
                else if(incline==1){
                    int DEPTH = 3;
                    String leftTop = String.format("%d,%d", Xcoord-DEPTH, YCoord+DEPTH);//Left Top Point
                    String left = String.format("%d,%d", Xcoord-DEPTH, YCoord);//Left Point
                    String bottom = String.format("%d,%d", Xcoord, YCoord-DEPTH);//Bottom Point
                    String bottomRight = String.format("%d,%d", Xcoord+DEPTH, YCoord-DEPTH);//Bottom Right Point
                    newPoints = leftTop + ";" + left + ";" + bottom + ";" + bottomRight + ";";
                }
                else if(incline == -1){
                    int DEPTH = 3;
                    String bottomLeft = String.format("%d,%d", Xcoord-DEPTH, YCoord-10-DEPTH);//Bottom Left Point
                    String bottom = String.format("%d,%d", Xcoord, YCoord-10-DEPTH);//Bottom Point
                    String right = String.format("%d,%d", Xcoord+DEPTH, YCoord-10);//Right Point
                    String rightTop = String.format("%d,%d", Xcoord+DEPTH, YCoord-10+DEPTH);//Right Top Point
                    newPoints = bottomLeft + ";" + bottom + ";" + right + ";" + rightTop + ";";
                }
                //Reset data and recreate ground
                game.controls.addBodyToDelete(GroundBody);
                groundCoordinates = new ArrayList<Vector2>();
                game.metadata.set(17, prevString + newPoints + afterString);
                box2dCreateWorld();
            }
        }
        catch(AssertionError e){
            throw new MutilationError();
        }
    }
    public void setCross(int X, int Y) {
        if(game.controls.getCurrentPlayer()==0){
            int distance = Math.abs((int)playerTankBody.getPosition().x+640-X);
            int height = Math.abs((int)playerTankBody.getPosition().y+360-Y);
            double angle = Math.round(Math.toDegrees(Math.atan2(height, distance)));
            powerOfShot.setText("Power: " + String.valueOf(distance-13));
            angleOfShot.setText("Angle: " + String.valueOf(angle-54));
        }
        else{
            int distance = Math.abs((int)enemyTankBody.getPosition().x+640-X);
            int height = Math.abs((int)enemyTankBody.getPosition().y+360-Y);
            double angle = Math.round(Math.toDegrees(Math.atan2(height, distance-5)));
            powerOfShot.setText("Power: " + String.valueOf(distance-5));
            angleOfShot.setText("Angle: " + String.valueOf(-1*(angle-70)));
        }
        cross.setPosition(X-13, 720-Y-10);
        cross.setVisible(true);
    }
    public void deleteBodySafely(){
        for(int i=0; i<game.controls.getBodyToDelete().size(); i++){
            world.destroyBody(game.controls.getBodyToDelete().get(i));
        }
        game.controls.setBodyToDelete(new ArrayList<Body>());
    }
    public float[] vector2float(ArrayList<Vector2> vector2, int Yoff){
        int Xoffset = -640;
        int Yoffset = -360+Yoff;
        float[] floatArray = new float[vector2.size()*2];
        for(int i=0; i<vector2.size(); i++){
            floatArray[i*2] = vector2.get(i).x - Xoffset;
            floatArray[i*2+1] = vector2.get(i).y - Yoffset;
        }
        return floatArray;
    }
    public void testing(){
        if(game.controls.getValueToCheck() != -1 && game.controls.getExpectedValue() != -1){
            int result = Testing.runTest(game.controls.getValueToCheck(), game.controls.getExpectedValue());
            System.out.println("Testing Result: " + result);
            if(result==0) {
                testResult.setColor(Color.GREEN);
                displayHUD("Test Passed");
            }
            else {
                testResult.setColor(Color.RED);
                displayHUD("Test Failed");
            }
        }
        game.controls.setValueToCheck(-1);
        game.controls.setExpectedValue(-1);
    }
    public void displayHUD(String result){
        testResult.setText(result);
    }
    @Override
    public void show() {
        super.show();
    }
    @Override
    public void render(float delta) {
        //Resetting the screen
        Gdx.gl.glLineWidth(5);
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Scene2D Drawing
        player1Indicator.setPosition(playerTankBody.getPosition().x+630, playerTankBody.getPosition().y+400);
        player2Indicator.setPosition(enemyTankBody.getPosition().x+630, enemyTankBody.getPosition().y+400);
        if(game.controls.getCurrentPlayer()==0) {
            player1Indicator.setVisible(true);
            player2Indicator.setVisible(false);
        }
        else{
            player1Indicator.setVisible(false);
            player2Indicator.setVisible(true);
        }
        handleHealth();
        stage.act();
        stage.draw();

        //Box2D Basic
        if(game.controls.isDevelopmentMode()) debugRenderer.render(world, camera.combined);
        world.step(1/60f, 6, 2);
        deleteBodySafely();

        //Generating Ground Layer
        if(!game.controls.isWpnWindowActive()) {
            if(game.controls.isCollisionDetection()){
                try {
                    if(game.controls.isMutableGround())handleMutilation(game.controls.getXcollisionCoord(),game.controls.getYcollisionCoord());
                } catch (MutilationError e) {
                    throw new RuntimeException(e);
                }
                game.controls.setCollisionDetection(false);
            }
            if(game.controls.isMutableGround()) {
                Gdx.gl.glLineWidth(25);
                shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                shapeRenderer.setColor(Color.BROWN);
                float[] groundCoordinatesArray1 = vector2float(groundCoordinates, 5);
                float[] groundCoordinatesArray2 = vector2float(groundCoordinates, 10);
                float[] groundCoordinatesArray3 = vector2float(groundCoordinates, 15);
                float[] groundCoordinatesArray4 = vector2float(groundCoordinates, 20);
                float[] groundCoordinatesArray5 = vector2float(groundCoordinates, 25);
                shapeRenderer.polygon(groundCoordinatesArray1);
                shapeRenderer.polygon(groundCoordinatesArray2);
                shapeRenderer.polygon(groundCoordinatesArray3);
                shapeRenderer.polygon(groundCoordinatesArray4);
                shapeRenderer.polygon(groundCoordinatesArray5);
                shapeRenderer.end();
            }
        }

        //Applying Momentum to Tanks
        Gdx.gl.glLineWidth(5);
        //Resetting Metadata
        game.metadata.set(4, String.format("%d,%d", (int)playerTankBody.getPosition().x, (int)playerTankBody.getPosition().y));
        game.metadata.set(5, String.format("%d,%d", (int)enemyTankBody.getPosition().x, (int)enemyTankBody.getPosition().y));
        game.metadata.set(6, String.valueOf(game.controls.getCurrentPlayer()));
        game.metadata.set(7, String.valueOf(game.controls.getPlayer1Fuel()));
        game.metadata.set(8, String.valueOf(game.controls.getPlayer2Fuel()));

        //SpriteBatch Drawing
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        playerTankSprite.setPosition(playerTankBody.getPosition().x - playerTankSprite.getWidth()/2, playerTankBody.getPosition().y - (playerTankSprite.getHeight()/2));
        playerTankSprite.setRotation((float) Math.toDegrees(playerTankBody.getAngle()));
        enemyTankSprite.setPosition(enemyTankBody.getPosition().x - enemyTankSprite.getWidth()/2, enemyTankBody.getPosition().y - (enemyTankSprite.getHeight()/2));
        enemyTankSprite.setRotation((float) Math.toDegrees(enemyTankBody.getAngle()));
        if(game.controls.isDropBoxPresent()) {
            DropBoxSprite.setPosition(DropBox.getPosition().x - DropBoxSprite.getWidth() / 2, DropBox.getPosition().y - (DropBoxSprite.getHeight() / 2));
            if(drawGlider) {
                gliderSprite.setPosition(glider.getPosition().x - gliderSprite.getWidth() / 2, glider.getPosition().y - (gliderSprite.getHeight() / 2));
                gliderSprite.draw(game.batch);
            }
            DropBoxSprite.draw(game.batch);
            game.metadata.set(15, String.format("%d,%d", (int)DropBox.getPosition().x, (int)DropBox.getPosition().y));
        }
        if(game.controls.isShotActive()) {
            tempWeaponSprite.setPosition(bulletBody.getPosition().x - tempWeaponSprite.getWidth() / 2, bulletBody.getPosition().y - (tempWeaponSprite.getHeight() / 2));
            tempWeaponSprite.draw(game.batch);
        }
        enemyTankSprite.draw(game.batch);
        playerTankSprite.draw(game.batch);
        game.batch.end();

        //Weapon Window Hovering Fix


        //Check Win
        if(game.controls.getPlayer1Health()==0 || game.controls.getPlayer2Health()==0){
            if(game.controls.getPlayer1Health()==0) game.controls.setWinner(1);
            else game.controls.setWinner(0);
            game.setScreen(new GameOver(game));
            dispose();
        }

        //Check play
        if(game.controls.isShotActive()) fire.setTouchable(Touchable.disabled);
        else fire.setTouchable(Touchable.enabled);
        if(game.controls.isTesting()) testing();
    }
}
