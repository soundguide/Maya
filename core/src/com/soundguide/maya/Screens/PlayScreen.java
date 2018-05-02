package com.soundguide.maya.Screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.soundguide.maya.Maia;
import com.soundguide.maya.Controller;
import com.soundguide.maya.Scenes.Hud;
import com.soundguide.maya.Sprites.Enemy;
import com.soundguide.maya.Sprites.Maya;
import com.soundguide.maya.Tools.B2WorldCreator;
import com.soundguide.maya.Tools.WorldContactListener;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

/**
 * Created by h on 07.04.2017.
 */

public class PlayScreen implements Screen {
    private Maia game;

    private TextureAtlas atlas;

    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Hud hud;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private World world;
    private Box2DDebugRenderer b2dr;
    private B2WorldCreator creator;
    private Maya player;

    private Music music;

    Controller controller;

    public PlayScreen(Maia game) {
        atlas = new TextureAtlas("Maia_and_enemies.pack");

        this.game = game;
        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(Maia.V_WIDTH / Maia.PPM, Maia.V_HEIGHT / Maia.PPM, gamecam);
        hud = new Hud (game.batch);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("level1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / Maia.PPM);
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0, -10), true);
        //b2dr = new Box2DDebugRenderer();//////////

        creator = new B2WorldCreator(this);

        player = new Maya(this);

        world.setContactListener(new WorldContactListener());
        controller = new Controller();

        if(player.currentState != Maya.State.DEAD)
        {music = Maia.manager.get("audio/music/level2.mp3", Music.class);
        music.setLooping(true);
        music.play();}
    }

    public TextureAtlas getAtlas(){
        return atlas;
    }

    @Override
    public void show() {

    }

    public void handleInput(float dt) {
        if (player.currentState != Maya.State.DEAD) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && player.b2body.getLinearVelocity().y == 0)
            player.b2body.applyLinearImpulse(new Vector2(0, 4f), player.b2body.getWorldCenter(), true);
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2)
            player.b2body.applyLinearImpulse(new Vector2(0.08f, 0), player.b2body.getWorldCenter(), true);
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2)
            player.b2body.applyLinearImpulse(new Vector2(-0.08f, 0), player.b2body.getWorldCenter(), true);

            if (controller.isRightPressed() && player.b2body.getLinearVelocity().x <= 2) {
            player.b2body.applyLinearImpulse(new Vector2(0.08f, 0), player.b2body.getWorldCenter(), true);}
            if (controller.isLeftPressed() && player.b2body.getLinearVelocity().x >= -2) {
            player.b2body.applyLinearImpulse(new Vector2(-0.08f, 0), player.b2body.getWorldCenter(), true);
            }
            if (controller.isUpPressed() && player.b2body.getLinearVelocity().y == 0) {
            player.b2body.applyLinearImpulse(new Vector2(0, 4f), player.b2body.getWorldCenter(), true);
            }
        }
    }


    public void update(float dt)
    {
    handleInput(dt);

    world.step(1/60f, 6, 2);

    player.update(dt);
    for (Enemy enemy : creator.getEnemies()) {
        enemy.update(dt);
        if(enemy.getX() < player.getX() + 200 / Maia.PPM)
            enemy.b2body.setActive(true);
    }
    if(player.currentState != Maya.State.DEAD){
        gamecam.position.x = player.b2body.getPosition().x;
    //gamecam.position.x = (float) Math.round(player.b2body.getPosition().x*Maia.PPM)/Maia.PPM;
        gamecam.position.y = player.b2body.getPosition().y;
    //gamecam.position.y = (float) Math.round(player.b2body.getPosition().y*Maia.PPM)/Maia.PPM;
        //EDGE PADDING!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    }

    gamecam.update();
    renderer.setView(gamecam);

    }

    @Override
    public void render(float delta) {
    update(delta);

        Gdx.gl.glClearColor(0.86f, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.render();

        //b2dr.render(world, gamecam.combined);////////////

        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        player.draw(game.batch);
        for (Enemy enemy : creator.getEnemies())
            enemy.draw(game.batch);
        game.batch.end();

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

        if(Gdx.app.getType() == Application.ApplicationType.Android)
        controller.draw();

        if(gameOver()){
            game.setScreen(new GameOverScreen(game));
            dispose();
        }
        if(gameWon()){
            game.setScreen(new GameWonScreen(game));
            dispose();
        }
    }

    public boolean gameOver(){
        if(player.currentState == Maya.State.DEAD && player.getStateTimer() > 3){
            return true;
        }
        return false;
    }

    public boolean gameWon(){
        if(player.currentState == Maya.State.WON && player.getStateTimer() > 3){
            return true;
        }
        return false;
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width,height);
        controller.resize(width, height);
    }

    public TiledMap getMap(){
        return map;
    }

    public World getWorld(){
        return world;
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
        map.dispose();
        renderer.dispose();
        world.dispose();
        //b2dr.dispose();/////////////////
        hud.dispose();

    }
}
