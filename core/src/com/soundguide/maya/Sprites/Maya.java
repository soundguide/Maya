package com.soundguide.maya.Sprites;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.soundguide.maya.Maia;
import com.soundguide.maya.Screens.PlayScreen;
import com.sun.corba.se.impl.oa.poa.ActiveObjectMap;

import static com.badlogic.gdx.physics.box2d.Filter.*;

/**
 * Created by h on 17.04.2017.
 */

public class Maya extends Sprite {
    public enum State { FALLING, JUMPING, STANDING, RUNNING, DEAD, WON }
    public State currentState;
    public State previousState;
    public World world;
    public Screen screen;
    public Body b2body;
    private TextureRegion mainStand;
    private TextureRegion mainDead;
    private Animation<TextureRegion> mainrun;
    private float stateTimer;
    private Animation<TextureRegion> mainjump;

    private boolean runningRight;
    private boolean mayaIsDead;
    private boolean mayaWon;

    public Maya(PlayScreen screen){
        this.world = screen.getWorld();
        this.screen = screen;
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;

        defineMaya();
        mainStand = new TextureRegion(screen.getAtlas().findRegion("main"), 0, 1, 17, 19);
        setBounds(0, 1, 17 / Maia.PPM, 20 / Maia.PPM);
        setRegion(mainStand);

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 1; i < 5; i++)
            frames.add(new TextureRegion(getTexture(), i*17, 1, 17, 19));
        mainrun = new Animation(0.1f, frames);
        frames.clear();

        for (int i = 5; i < 7; i++)
            frames.add(new TextureRegion(getTexture(), i*17, 1, 17, 19));
        mainjump = new Animation(0.1f, frames);

        mainDead = new TextureRegion(screen.getAtlas().findRegion("main"), 153, 1, 17, 19);
        setBounds(0, 1, 17 / Maia.PPM, 20 / Maia.PPM);
        setRegion(mainDead);
    }

    public void update(float dt){
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt));
    }

    public TextureRegion getFrame(float dt){
        currentState = getState();

        TextureRegion region;
        switch(currentState) {
            case DEAD:
                region = mainDead;
                break;
            case JUMPING:
                region = mainjump.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region = mainrun.getKeyFrame(stateTimer, true);
                break;
            case FALLING:
            case STANDING:
            case WON:
            default:
                region = mainStand;
                break;
        }
        if((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
        }
        else if((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()){
            region.flip(true, false);
            runningRight = true;
        }
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;
    }

    public State getState(){
        if(mayaIsDead)
            return State.DEAD;
        else if(b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING))
            return State.JUMPING;
        else if(b2body.getLinearVelocity().y < 0)
            return State.FALLING;
        else if(b2body.getLinearVelocity().x != 0)
            return State.RUNNING;
        else if(mayaWon)
            return State.WON;
        else
            return State.STANDING;
    }

    public boolean isDead(){
        return mayaIsDead;
    }
    public boolean isWon() {return mayaWon;}

    public float getStateTimer(){
        return stateTimer;
    }

    public void die() {

        if (!isDead()) {
            Maia.manager.get("audio/music/level2.mp3", Music.class).stop();
            Maia.manager.get("audio/sounds/lose.mp3", Sound.class).play();
            mayaIsDead = true;
            Filter filter = new Filter();
            filter.maskBits = Maia.NOTHING_BIT;
            for (Fixture fixture : b2body.getFixtureList()) {
                fixture.setFilterData(filter);
            }
            b2body.applyLinearImpulse(new Vector2(0, 4f), b2body.getWorldCenter(), true);
        }
    }

    public void won(){
        if(Keybutton.keytook ==true){
        Maia.manager.get("audio/music/level2.mp3", Music.class).stop();
        Maia.manager.get("audio/sounds/dooropen.mp3", Sound.class).play();
        Maia.manager.get("audio/sounds/win.mp3", Sound.class).play();
        mayaWon = true;}
    }

    public void hit(Enemy enemy){
        die();
    }
    public void hit(Bottom bottom){
        die();
    }
    public void win(Doors doors){
        won();
    }

    public void defineMaya(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(64 / Maia.PPM, 128 / Maia.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(10 / Maia.PPM);
        fdef.filter.categoryBits = Maia.MAYA_BIT;
        fdef.filter.maskBits = Maia.GROUND_BIT |
                Maia.STAR_BIT |
                Maia.ENEMY_BIT |
                Maia.OBJECT_BIT |
                Maia.ENEMY_HEAD_BIT |
                Maia.BOTTOM_BIT |
                Maia.DOOR_BIT |
                Maia.KEYBUTTON_BIT |
                Maia.BUTTON_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

        EdgeShape feet2 = new EdgeShape();
        feet2.set(new Vector2(-8 / Maia.PPM, -10/ Maia.PPM), new Vector2(6 / Maia.PPM, -10 / Maia.PPM));
        fdef.shape = feet2;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData(this);//feet2
    }
}