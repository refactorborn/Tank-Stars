package com.ts.game.screens;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.sun.tools.javac.jvm.Code;
import com.ts.game.GameControls;
import com.ts.game.InputController;
import com.ts.game.Resources;
import com.ts.game.components.Abrams;
import com.ts.game.components.Frost;
import com.ts.game.components.Spectre;
import com.ts.game.components.tank;
import com.ts.game.ts;

import java.util.ArrayList;

public class GameScreen extends ScreenAdapter {
    //Scene2D Variables
    private Stage stage;
    private Table mainTable;
    private Table headerTable;
    private Table footerTable;
    private tank playerTank;
    private tank enemyTank;
    private Dialog wpnWindow;
    private ts game;

    //Resources
    private Resources res;

    //Box2D Variables
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera camera;
    private Vector2 movement = new Vector2();
    private Body playerTankBody;
    private Body enemyTankBody;
    private Body DropBox;
    private Sprite DropBoxSprite;
    private Sprite playerTankSprite;
    private Sprite enemyTankSprite;
    private final int speed = 50000;
    public GameScreen(final ts game) {
        //Overlaying Instantiations
        res = new Resources();
        this.game = game;
        stage = new Stage(new ScreenViewport());
        mainTable = new Table();

        mainTable.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        mainTable.setBackground(new TextureRegionDrawable(res.getBg()));
        mainTable.setPosition(0, 0);
        stage.addActor(mainTable);
//        DropBoxSprite = (Sprite) res.dropBox;
        playerTankSprite = getTank(Integer.parseInt(game.metadata.get(0))).getSprite();
        enemyTankSprite = getTank(Integer.parseInt(game.metadata.get(1))).getSprite();
        enemyTankSprite.flip(true, false);
        DropBoxSprite = new Sprite(res.getDropBox());

        Image groundSlab = new Image(res.getGroundSlab());
        groundSlab.setPosition(0, 0);
        stage.addActor(groundSlab);
        scene2dCreateHeader();
        scene2dCreateGround();
        scene2dCreateGame();
        stage.addActor(headerTable);
        stage.addActor(footerTable);
        game.controls.initiatePlayers(playerTank, enemyTank);

        //Box2D Instantiations
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(new InputController(){
            @Override
            public boolean keyDown(int keyCode) {
                if(keyCode== Input.Keys.ESCAPE){
                    game.setScreen(new InGameOptionsScreen(game));
                }
                else if(keyCode==Input.Keys.D || keyCode==Input.Keys.RIGHT){
                    System.out.println("D");
                    movement.x = speed;
                }
                else if(keyCode==Input.Keys.A || keyCode==Input.Keys.LEFT){
                    System.out.println("A");
                    movement.x = -speed;
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
        });
        Gdx.input.setInputProcessor(inputMultiplexer);
        box2dCreateWorld();
        box2dCreateTank(0);
        box2dCreateTank(1);
        box2dCreateDropBox();
    }
    public tank getTank(int tankIndex){
        if(tankIndex==0) return new Abrams();
        else if(tankIndex==1) return new Frost();
        else if(tankIndex==2) return new Spectre();
        else return null;
    }
    public void scene2dCreateHeader(){
        headerTable = new Table();
        headerTable.setSize(Gdx.graphics.getWidth(), (float)Gdx.graphics.getHeight()*2/10);
        headerTable.setPosition(-50, (float)Gdx.graphics.getHeight()*8/10);
        ImageButton pauseButton = new ImageButton(new TextureRegionDrawable(res.getIngameMenu()));
        Image healthBarPlayer = new Image(res.getHealthBar());
        Image healthBarEnemy = new Image(res.getHealthBar());
        Image vs = new Image(res.getVs());
        pauseButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new InGameOptionsScreen(game));
            }
        });
        headerTable.add(pauseButton).size(150,100).pad(20);
        headerTable.add(healthBarPlayer).pad(20);
        headerTable.add(vs).size(100,100).padLeft(0);
        headerTable.add(healthBarEnemy).pad(20);
    }
    public void scene2dCreateGround(){
        footerTable = new Table();
        footerTable.setSize(Gdx.graphics.getWidth(), (float)Gdx.graphics.getHeight()*2/10);
        footerTable.setPosition(0, 0);
        HorizontalGroup buttons = new HorizontalGroup();
        TextButton fire = new TextButton("Fire", res.getTextButtonStyle2());
        final TextButton changeWpn = new TextButton(getTank(Integer.parseInt(game.metadata.get(game.controls.getCurrentPlayer()))).getWeapons().get(0).getName(), res.getTextButtonStyle2());
        Image fuel1 = new Image(res.getFuel());
        Image fuel2 = new Image(res.getFuel());
        fuel1.setSize(100,50);
        fuel2.setSize(100,50);
        buttons.addActor(fuel1);
        buttons.space(50);
        buttons.addActor(fire);
        buttons.space(50);
        buttons.addActor(changeWpn);
        buttons.space(50);
        buttons.addActor(fuel2);
        footerTable.add(buttons);
        changeWpn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                wpnWindow = game.controls.getCurrentTank().createWeaponWindow(game, changeWpn);
                wpnWindow.show(stage);
                wpnWindow.setSize(200, 500);
                wpnWindow.align(Align.center);
                wpnWindow.setPosition((float)Gdx.graphics.getWidth()/2, (float)Gdx.graphics.getHeight()/2, Align.center);
            }
        });
        fire.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(game.controls.getCurrentPlayer()==1) game.controls.setCurrentPlayer(0);
                else game.controls.setCurrentPlayer(1);
                changeWpn.setText(getTank(Integer.parseInt(game.metadata.get(game.controls.getCurrentPlayer()))).getWeapons().get(0).getName());
            }
        });
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
        if(player==0) tankDef.position.set(-450,30);
        else tankDef.position.set(450,-100);
        PolygonShape tankShape = new PolygonShape();
        tankShape.setAsBox(20, 20);
        FixtureDef tankFixDef = new FixtureDef();
        tankFixDef.shape = tankShape;
        tankFixDef.density = 3;
        tankFixDef.friction = 2.5f;
        if(player==0) {
            playerTankBody = world.createBody(tankDef);
            playerTankBody.createFixture(tankFixDef);
        }
        else {
            enemyTankBody = world.createBody(tankDef);
            enemyTankBody.createFixture(tankFixDef);
        }
    }
    public void box2dCreateDropBox(){
        BodyDef dropBoxDef = new BodyDef();
        dropBoxDef.type = BodyDef.BodyType.DynamicBody;
        dropBoxDef.position.set(100, 400); //sets the position to middle centre of the screen
        PolygonShape dropBoxShape = new PolygonShape();
        dropBoxShape.setAsBox(20, 20);
        FixtureDef dropBoxFixDef = new FixtureDef();
        dropBoxFixDef.shape = dropBoxShape;
        dropBoxFixDef.density = 10;
        dropBoxFixDef.friction = 10.5f; //makes it impossible to move
        dropBoxFixDef.restitution = -0.5f; //makes it bounce
        DropBox = world.createBody(dropBoxDef);
        DropBox.createFixture(dropBoxFixDef);
    }
    public void box2dCreateWorld(){
        world = new World(new Vector2(0, -9.8f), true);
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        debugRenderer = new Box2DDebugRenderer();
        BodyDef groundDef = new BodyDef();
        groundDef.type = BodyDef.BodyType.StaticBody;
        groundDef.position.set(0, 0);
        ChainShape groundShape = new ChainShape();
        groundShape.createChain(new Vector2[]{new Vector2(-650,360),new Vector2(-650, -70),new Vector2(-500,27),new Vector2(-353,27),new Vector2(-178,-90),new Vector2(-40,-90),new Vector2(38,-145),new Vector2(227,-145),new Vector2(275,-112),new Vector2(508,-112),new Vector2(645, -13),new Vector2(645,360)});
        FixtureDef groundFixDef = new FixtureDef();
        groundFixDef.shape = groundShape;
        groundFixDef.density = 100;
        groundFixDef.friction = 0.2f;
        world.createBody(groundDef).createFixture(groundFixDef);
    }
    @Override
    public void show() {
        super.show();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();

        //Box2D stuff
//        debugRenderer.render(world, camera.combined);
        world.step(1/60f, 6, 2);
        if(game.controls.getCurrentPlayer()==0)playerTankBody.applyForceToCenter(movement, true);
        else enemyTankBody.applyForceToCenter(movement, true);

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        playerTankSprite.setPosition(playerTankBody.getPosition().x - playerTankSprite.getWidth()/2, playerTankBody.getPosition().y - (playerTankSprite.getHeight()/2));
        enemyTankSprite.setPosition(enemyTankBody.getPosition().x - enemyTankSprite.getWidth()/2, enemyTankBody.getPosition().y - (enemyTankSprite.getHeight()/2));
        DropBoxSprite.setPosition(DropBox.getPosition().x - DropBoxSprite.getWidth()/2, DropBox.getPosition().y - (DropBoxSprite.getHeight()/2));
        DropBoxSprite.draw(game.batch);
        enemyTankSprite.draw(game.batch);
        playerTankSprite.draw(game.batch);
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }
    @Override
    public void pause() {

    }
    @Override
    public void resume() {

    }
    @Override
    public void hide() {

    }
    @Override
    public void dispose() {
        stage.dispose();
    }
}
