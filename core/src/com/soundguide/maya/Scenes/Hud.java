package com.soundguide.maya.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.soundguide.maya.Maia;

/**
 * Created by h on 10.04.2017.
 */

public class Hud implements Disposable {
    public Stage stage;
    private Viewport viewport;

    public static Integer score;


    private static Label scoreLabel;
    private Label levelLabel;
    private Label worldLabel;
    private Label maiaLabel;

    public Hud(SpriteBatch sb) {
        score = 0;

        viewport = new FitViewport(Maia.V_WIDTH, Maia.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabel = new Label("1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        worldLabel = new Label("LEVEL", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        maiaLabel = new Label("MAYA", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(maiaLabel).expandX().padTop(10);
        table.add(worldLabel).expandX().padTop(10);
        table.row();
        table.add(scoreLabel).expandX();
        table.add(levelLabel).expandX();

        stage.addActor(table);
    }

    public static void addScore(int value){
        score += value;
        scoreLabel.setText(String.format("%06d", score));
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
