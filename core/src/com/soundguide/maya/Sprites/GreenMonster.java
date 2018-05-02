package com.soundguide.maya.Sprites;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.soundguide.maya.Maia;
import com.soundguide.maya.Scenes.Hud;
import com.soundguide.maya.Screens.PlayScreen;

/**
 * Created by h on 01.05.2017.
 */

public class GreenMonster extends Enemy {

    private float stateTime;
    private Animation<TextureRegion> walkAnimation;
    private Array<TextureRegion> frames;
    private boolean setToDestroy;
    private boolean destroyed;


    public GreenMonster(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        frames = new Array<TextureRegion>();
        for (int i = 0; i < 2; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("greenmonster"), i * 17, -5, 17, 20));
        walkAnimation = new Animation(0.2f, frames);
        stateTime = 0;
        setBounds(getX(), getY(), 17 / Maia.PPM, 20 / Maia.PPM);
        setToDestroy = false;
        destroyed = false;
    }

    public void update(float dt) {
        stateTime += dt;
        TextureRegion region;
        region = new TextureRegion();

        if (setToDestroy && !destroyed) {
            world.destroyBody(b2body);
            destroyed = true;
            setRegion(new TextureRegion(screen.getAtlas().findRegion("greenmonster"), 34, -5, 17, 20));
            stateTime = 0;
        } else if (!destroyed) {
            b2body.setLinearVelocity(velocity);
            setPosition(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y - getHeight()/2);
            setRegion(walkAnimation.getKeyFrame(stateTime, true));

            }
        if(velocity.x > 0 && region.isFlipX() == false){
            region.flip(true, false);
        }
        if(velocity.x < 0 && region.isFlipX() == true){
            region.flip(true, false);
        }
    }
        @Override
        protected void defineEnemy () {
            BodyDef bdef = new BodyDef();
            bdef.position.set(getX(), getY());
            bdef.type = BodyDef.BodyType.DynamicBody;
            b2body = world.createBody(bdef);

            FixtureDef fdef = new FixtureDef();
            CircleShape shape = new CircleShape();
            shape.setRadius(10 / Maia.PPM);
            fdef.filter.categoryBits = Maia.ENEMY_BIT;
            fdef.filter.maskBits = Maia.GROUND_BIT | Maia.STAR_BIT | Maia.ENEMY_BIT | Maia.OBJECT_BIT | Maia.MAYA_BIT;

            fdef.shape = shape;
            b2body.createFixture(fdef).setUserData(this);

            PolygonShape head = new PolygonShape();
            Vector2[] vertice = new Vector2[4];
            vertice[0] = new Vector2(-10, 12).scl(1 / Maia.PPM);
            vertice[1] = new Vector2(10, 12).scl(1 / Maia.PPM);
            vertice[2] = new Vector2(-7, 3).scl(1 / Maia.PPM);
            vertice[3] = new Vector2(7, 3).scl(1 / Maia.PPM);
            head.set(vertice);

            fdef.shape = head;
            fdef.restitution = 0.5f;
            fdef.filter.categoryBits = Maia.ENEMY_HEAD_BIT;
            b2body.createFixture(fdef).setUserData(this);
        }

    public void draw(Batch batch) {
        if (!destroyed || stateTime < 1)
            super.draw(batch);
    }

    @Override
    public void hitOnHead() {
        Maia.manager.get("audio/sounds/hittedbyarms.mp3", Sound.class).play();
        Maia.manager.get("audio/sounds/greenhit.mp3", Sound.class).play();
        setToDestroy = true;
        Hud.addScore(50);
    }
}