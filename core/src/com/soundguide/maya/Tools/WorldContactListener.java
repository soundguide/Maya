package com.soundguide.maya.Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.soundguide.maya.Maia;
import com.soundguide.maya.Sprites.Bottom;
import com.soundguide.maya.Sprites.Doors;
import com.soundguide.maya.Sprites.Enemy;
import com.soundguide.maya.Sprites.InteractiveTileObject;
import com.soundguide.maya.Sprites.Keybutton;
import com.soundguide.maya.Sprites.Maya;
import com.soundguide.maya.Sprites.Simplebutton;
import com.soundguide.maya.Sprites.Stars;
import com.sun.corba.se.impl.oa.poa.ActiveObjectMap;

import sun.java2d.pipe.SpanShapeRenderer;

/**
 * Created by h on 23.04.2017.
 */

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        if (fixA.getUserData() == this || fixB.getUserData() == this) { //feet2
            Fixture head = fixB.getUserData() == this ? fixB : fixA; //feet2
            Fixture object = head == fixA ? fixB : fixA;

            if (object.getUserData() instanceof InteractiveTileObject) {
                ((InteractiveTileObject) object.getUserData()).onFeetStep();
            }
        }

        switch (cDef) {
            case Maia.ENEMY_HEAD_BIT | Maia.MAYA_BIT:
                if (fixA.getFilterData().categoryBits == Maia.ENEMY_HEAD_BIT) {
                    ((Enemy) fixA.getUserData()).hitOnHead();
                } else {
                    ((Enemy) fixB.getUserData()).hitOnHead();
                }
                break;
            case Maia.ENEMY_BIT | Maia.OBJECT_BIT:
                if (fixA.getFilterData().categoryBits == Maia.ENEMY_BIT) {
                    ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                } else {
                    ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
                }
                break;
            case Maia.ENEMY_BIT | Maia.STAR_BIT:
                if (fixA.getFilterData().categoryBits == Maia.ENEMY_BIT)
                    ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
                break;
            case Maia.MAYA_BIT | Maia.ENEMY_BIT:
                if (fixA.getFilterData().categoryBits == Maia.MAYA_BIT) {
                    ((Maya) fixA.getUserData()).hit((Enemy) fixB.getUserData());
                } else {
                    ((Maya) fixB.getUserData()).hit((Enemy) fixA.getUserData());
                }
                break;
            case Maia.BOTTOM_BIT | Maia.MAYA_BIT:
                if (fixA.getFilterData().categoryBits == Maia.MAYA_BIT) {
                    ((Maya) fixA.getUserData()).hit((Bottom) fixB.getUserData());
                } else {
                    ((Maya) fixB.getUserData()).hit((Bottom) fixA.getUserData());
                }
                break;
            case Maia.DOOR_BIT | Maia.MAYA_BIT:
                if (fixA.getFilterData().categoryBits == Maia.MAYA_BIT) {
                    ((Maya) fixA.getUserData()).win((Doors) fixB.getUserData());
                } else {
                    ((Maya) fixB.getUserData()).win((Doors) fixA.getUserData());
                }
                break;
            case Maia.ENEMY_BIT | Maia.ENEMY_BIT:
                ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
                break;
            case Maia.STAR_BIT | Maia.MAYA_BIT:
                if (fixA.getFilterData().categoryBits == Maia.STAR_BIT)
                    ((Stars) fixA.getUserData()).onFeetStep();
                else
                    ((Stars) fixB.getUserData()).onFeetStep();
                break;
            case Maia.BUTTON_BIT | Maia.MAYA_BIT:
                if (fixA.getFilterData().categoryBits == Maia.BUTTON_BIT)
                    ((Simplebutton) fixA.getUserData()).onFeetStep();
                else
                    ((Simplebutton) fixB.getUserData()).onFeetStep();
                break;
            case Maia.KEYBUTTON_BIT | Maia.MAYA_BIT:
                if (fixA.getFilterData().categoryBits == Maia.KEYBUTTON_BIT)
                    ((Keybutton) fixA.getUserData()).onFeetStep();
                else
                    ((Keybutton) fixB.getUserData()).onFeetStep();
        }
    }
    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
