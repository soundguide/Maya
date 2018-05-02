package com.soundguide.maya;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.soundguide.maya.Maia;
import com.soundguide.maya.Screens.PlayScreen;

/**
 * Created by h on 06.08.2017.
 */

public class Controller {
    Viewport viewport;
    Stage stage;
    boolean upPressed, leftPressed, rightPressed;
    OrthographicCamera cam;

    public Controller(){
        cam = new OrthographicCamera();
        viewport = new FitViewport(Maia.V_WIDTH, Maia.V_HEIGHT, cam);
        stage = new Stage(viewport, Maia.batch);

        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.left().bottom();

        Image upImg = new Image (new Texture("up.png"));
        upImg.setSize(50, 50);
        upImg.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                upPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                upPressed = false;
            }
        });

        Image leftImg = new Image (new Texture("left.png"));
        leftImg.setSize(45, 45);
        leftImg.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed = false;
            }
        });

        Image rightImg = new Image (new Texture("right.png"));
        rightImg.setSize(45, 45);
        rightImg.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed = false;
            }
        });

        Image emptyImg = new Image (new Texture("empty.png"));
        emptyImg.setSize(10, 20);

        table.add(leftImg).size(leftImg.getWidth(), leftImg.getHeight());
        table.add(rightImg).size(rightImg.getWidth(), rightImg.getHeight());
        table.add(emptyImg).size(emptyImg.getWidth(), emptyImg.getHeight());
        table.add(emptyImg).size(emptyImg.getWidth(), emptyImg.getHeight());
        table.add(emptyImg).size(emptyImg.getWidth(), emptyImg.getHeight());
        table.add(emptyImg).size(emptyImg.getWidth(), emptyImg.getHeight());
        table.add(emptyImg).size(emptyImg.getWidth(), emptyImg.getHeight());
        table.add(emptyImg).size(emptyImg.getWidth(), emptyImg.getHeight());
        table.add(emptyImg).size(emptyImg.getWidth(), emptyImg.getHeight());
        table.add(emptyImg).size(emptyImg.getWidth(), emptyImg.getHeight());
        table.add(emptyImg).size(emptyImg.getWidth(), emptyImg.getHeight());
        table.add(emptyImg).size(emptyImg.getWidth(), emptyImg.getHeight());
        table.add(emptyImg).size(emptyImg.getWidth(), emptyImg.getHeight());
        table.add(emptyImg).size(emptyImg.getWidth(), emptyImg.getHeight());
        table.add(emptyImg).size(emptyImg.getWidth(), emptyImg.getHeight());
        table.add(emptyImg).size(emptyImg.getWidth(), emptyImg.getHeight());
        table.add(emptyImg).size(emptyImg.getWidth(), emptyImg.getHeight());
        table.add(emptyImg).size(emptyImg.getWidth(), emptyImg.getHeight());
        table.add(emptyImg).size(emptyImg.getWidth(), emptyImg.getHeight());
        table.add(emptyImg).size(emptyImg.getWidth(), emptyImg.getHeight());
        table.add(emptyImg).size(emptyImg.getWidth(), emptyImg.getHeight());
        table.add(emptyImg).size(emptyImg.getWidth(), emptyImg.getHeight());
        table.add(emptyImg).size(emptyImg.getWidth(), emptyImg.getHeight());
        table.add(emptyImg).size(emptyImg.getWidth(), emptyImg.getHeight());
        table.add(emptyImg).size(emptyImg.getWidth(), emptyImg.getHeight());
        table.add(emptyImg).size(emptyImg.getWidth(), emptyImg.getHeight());
        table.add(emptyImg).size(emptyImg.getWidth(), emptyImg.getHeight());
        table.add(emptyImg).size(emptyImg.getWidth(), emptyImg.getHeight());
        table.add(upImg).size(upImg.getWidth(), upImg.getHeight());
        table.row().pad(5, 5, 5, 5);
        table.row().padBottom(5);
        table.pack();

        stage.addActor(table);
    }
    public void draw(){
        stage.draw();
    }

    public boolean isUpPressed() {
        return upPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }
    public void resize(int width, int height){
        viewport.update(width, height);
    }
}
