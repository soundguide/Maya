package com.soundguide.maya.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.soundguide.maya.AdHandler;
import com.soundguide.maya.Maia;
import com.soundguide.maya.Scenes.Hud;
import com.soundguide.maya.Sprites.Keybutton;
import com.soundguide.maya.Sprites.Maya;



/**
 * Created by h on 05.06.2017.
 */

public class GameWonScreen implements Screen {
    private Viewport viewport;
    private Stage stage;

    private Game game;
    private static Label scoreLabel;


    public GameWonScreen(Game game){
        this.game = game;
        viewport = new FitViewport(Maia.V_WIDTH, Maia.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, ((Maia) game).batch);

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.ORANGE);

        Table table = new Table();
        table.center();
        table.setFillParent(true);

        scoreLabel = new Label(String.format("SCORE %06d", Hud.score), new Label.LabelStyle(new BitmapFont(), Color.ORANGE));
        Label gameWonLabel = new Label("YOU WON", font);
        Label playAgainLabel = new Label("CLICK TO PLAY AGAIN", font);

        table.add(scoreLabel).expandX();
        table.row();
        table.add(gameWonLabel).expandX();
        table.row();
        table.add(playAgainLabel).expandX().padTop(10f);

        stage.addActor(table);
        Keybutton.keytook = false;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if(Gdx.input.justTouched()) {
            game.setScreen(new PlayScreen((Maia) game));
            dispose();
        }
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
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
