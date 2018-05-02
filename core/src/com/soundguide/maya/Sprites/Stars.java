package com.soundguide.maya.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.soundguide.maya.Maia;
import com.soundguide.maya.Scenes.Hud;
import com.soundguide.maya.Screens.PlayScreen;

/**
 * Created by h on 18.04.2017.
 */

public class Stars extends InteractiveTileObject {
    public Stars(PlayScreen screen, Rectangle bounds){
        super(screen, bounds);
        fixture.setUserData(this);
        setCategoryFilter(Maia.STAR_BIT);
    }

    @Override
    public void onFeetStep() {
        Gdx.app.log("Star", "Collision");
        setCategoryFilter(Maia.DESTROYED_BIT);
        getCell().setTile(null);
        Hud.addScore(50);

        Maia.manager.get("audio/sounds/stargain.mp3", Sound.class).play();
    }
}
